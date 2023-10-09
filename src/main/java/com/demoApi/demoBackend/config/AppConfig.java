package com.demoApi.demoBackend.config;

import org.dozer.DozerBeanMapper;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Configuration
@EnableWebSecurity
public class AppConfig {
    @Bean
    BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }


//    @Bean
//    public FilterRegistrationBean<LogFilter> loggingFilter() {
//        FilterRegistrationBean<LogFilter> registrationBean = new FilterRegistrationBean<>();
//
//        LogFilter logFilter = new LogFilter();
//        logFilter.setIncludeQueryString(true);
//        logFilter.setIncludePayload(true);
//        logFilter.setIncludeHeaders(true);
//        logFilter.setMaxPayloadLength(10000);
//
//        registrationBean.setFilter(logFilter);
//        registrationBean.addUrlPatterns("/*");
//
//        return registrationBean;
//    }

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager();
    }

    @Bean
    public DozerBeanMapper mapper() {
        DozerBeanMapper mapper = new DozerBeanMapper();
        mapper.setMappingFiles(List.of("dozer-config.xml"));
        return mapper;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)throws Exception{
        return configuration.getAuthenticationManager();
    }

}
