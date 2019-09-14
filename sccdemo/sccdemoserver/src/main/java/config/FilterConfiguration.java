package config;

import filter.CrossDomainFilter;
import filter.LoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import service.UserService;

/**
 * @ClassName FilterConfiguration
 * @Description TODO
 * @Author 逝风无言
 * @Data 2019/9/12 17:46
 * @Version 1.0
 **/
@Configuration
public class FilterConfiguration {


    @Autowired
    private UserService userService;

    @Bean
    public FilterRegistrationBean loginFilterRegistration(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(
                new LoginFilter(userService)
        );
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("loginFilter");
        registrationBean.setOrder(2);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean crossDomainFilterRegistration(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(
                new CrossDomainFilter()
        );
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("crossDomainFilter");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
