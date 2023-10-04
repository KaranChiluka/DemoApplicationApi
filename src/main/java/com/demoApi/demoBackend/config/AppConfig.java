package com.demoApi.demoBackend.config;

import com.demoApi.demoBackend.util.CustomUserDetailsServices;
import com.demoApi.demoBackend.util.JwtTokenProvider;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
public class AppConfig {
    @Bean
    public DozerBeanMapper mapper() {
        DozerBeanMapper mapper = new DozerBeanMapper();
        mapper.setMappingFiles(List.of("dozer-config.xml"));
        return mapper;
    }

//    @Bean
//    public UserDetails userDetails(){
//        return CustomUserDetailsServices
//    }

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager();
    }

    @Deprecated
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,JwtTokenProvider jwtTokenProvider) throws Exception{
        http.csrf()
                .disable()
                .authorizeRequests()
                .requestMatchers("/actuator/**","/security/login")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
//                .httpBasic();
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new JwtAuthorizationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new CustomUserDetailsServices();
    }

    @Bean
    public FilterRegistrationBean<LogFilter> loggingFilter() {
        FilterRegistrationBean<LogFilter> registrationBean = new FilterRegistrationBean<>();

        LogFilter logFilter = new LogFilter();
        logFilter.setIncludeQueryString(true);
        logFilter.setIncludePayload(true);
        logFilter.setIncludeHeaders(true);
        logFilter.setMaxPayloadLength(10000);

        registrationBean.setFilter(logFilter);
        registrationBean.addUrlPatterns("/*");

        return registrationBean;
    }

}
