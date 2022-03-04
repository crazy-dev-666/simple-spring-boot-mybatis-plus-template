package cn.dev666.simple.template.config;

import cn.dev666.simple.template.annotation.Anonymous;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.session.Session;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private SpringSessionBackedSessionRegistry<? extends Session> registry;

    @Resource
    private RequestMappingHandlerMapping requestHandlerMapping;

    @Resource
    private CorsFilter corsFilter;

    @Value("${user.session.maxOnlineUser}")
    private Integer maxOnlineUser;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

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
                .authenticationEntryPoint((req,res,e)-> res.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage()))
                .accessDeniedHandler((req,res,e)-> res.sendError(HttpStatus.FORBIDDEN.value(), e.getMessage()))
                //session 管理
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .sessionAuthenticationStrategy(authenticationStrategy)
                .sessionAuthenticationFailureHandler((req,res,e)-> res.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage()))
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
