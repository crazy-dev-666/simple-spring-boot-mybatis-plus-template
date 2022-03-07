package cn.dev666.simple.template.config;

import cn.dev666.simple.template.annotation.Anonymous;
import cn.dev666.simple.template.enums.CommonErrorInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.session.Session;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private RedisIndexedSessionRepository repository;

    @Resource
    private RequestMappingHandlerMapping requestHandlerMapping;

    @Resource
    private CorsFilter corsFilter;

    @Value("${user.session.maxOnlineUser}")
    private Integer maxOnlineUser;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        SpringSessionBackedSessionRegistry<? extends Session> registry = new SpringSessionBackedSessionRegistry<>(repository);
        ConcurrentSessionControlAuthenticationStrategy authenticationStrategy = new ConcurrentSessionControlAuthenticationStrategy(registry);
        authenticationStrategy.setMaximumSessions(maxOnlineUser);
        authenticationStrategy.setExceptionIfMaximumExceeded(false);

        httpSecurity
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                //异常处理
                .exceptionHandling()
                .authenticationEntryPoint((req,res,e)-> authenticationEntryPoint(res, e))
                .accessDeniedHandler((req,res,e)-> res.sendError(CommonErrorInfo.ACCESS_DENIED.getStatus().value(),
                        CommonErrorInfo.ACCESS_DENIED.getMsg()))
                //session 管理
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .sessionAuthenticationStrategy(authenticationStrategy)
                .sessionAuthenticationFailureHandler((req,res,e)-> authenticationEntryPoint(res, e))
                //放行配置
                .and()
                .authorizeRequests()
                // swagger
                .antMatchers("/swagger-ui/","/swagger-resources/**", "/webjars/**", "/*/api-docs").permitAll()
                // OPTIONS
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // @Anonymous
                .antMatchers(anonymousUrlSet(requestHandlerMapping.getHandlerMethods())).permitAll()
                .anyRequest().authenticated();
    }

    private void authenticationEntryPoint(HttpServletResponse res, AuthenticationException e) throws IOException {
        String msg = e.getMessage();
        if (e instanceof InsufficientAuthenticationException){
            msg = "请登录后再进行此操作";
        }else if (e instanceof SessionAuthenticationException){
            msg = "账号异地登录，被挤出";
        }
        res.sendError(HttpStatus.UNAUTHORIZED.value(), msg);
    }


    private String[] anonymousUrlSet(Map<RequestMappingInfo, HandlerMethod> handlerMethodMap) {
        Set<String> urlSet = new HashSet<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethodMap.entrySet()) {
            HandlerMethod method = entry.getValue();
            Anonymous anonymous = method.getMethodAnnotation(Anonymous.class);
            if(anonymous == null){
                Class<?> beanType = method.getBeanType();
                anonymous = beanType.getAnnotation(Anonymous.class);
                if (anonymous == null) {
                    continue;
                }
            }
            PatternsRequestCondition patternsCondition = entry.getKey().getPatternsCondition();
            if (patternsCondition != null) {
                urlSet.addAll(patternsCondition.getPatterns());
            }
        }
        return urlSet.toArray(new String[0]);
    }
}
