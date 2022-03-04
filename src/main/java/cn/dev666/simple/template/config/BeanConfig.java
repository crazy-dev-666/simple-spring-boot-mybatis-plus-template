package cn.dev666.simple.template.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.session.Session;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class BeanConfig {

    @Value("${user.session.header}")
    private String header;

    /**
     * 定义 sessionId 在 http 请求头上的 key
     */
    @Bean
    public HttpSessionIdResolver httpSessionIdResolver(){
        return new HeaderHttpSessionIdResolver(header);
    }

    /**
     * 支持跨域
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addAllowedOriginPattern("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
    /**
     * 去除前缀
     */
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }

    /**
     * 密码加密方式
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * SpringSession 存取配置
     */
    @Bean
    public SpringSessionBackedSessionRegistry<? extends Session> springSessionBackedSessionRegistry(RedisIndexedSessionRepository repository) {
        return new SpringSessionBackedSessionRegistry<>(repository);
    }

    /**
     * 连接池
     */
    @Bean
    public LettuceConnectionFactory connectionFactory() {
        return new LettuceConnectionFactory();
    }

}
