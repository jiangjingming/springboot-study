package com.baili.springboot.study.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/** 
 * Created by jiangjingming on 2016/6/18.
 */
@Configuration
public class DruidConfig { 
  @Bean 
  public ServletRegistrationBean druidServlet() { 
    ServletRegistrationBean reg = new ServletRegistrationBean(); 
    reg.setServlet(new StatViewServlet()); 
    reg.addUrlMappings("/druid/*"); 
    //reg.addInitParameter("allow", "127.0.0.1"); //白名单 
    //reg.addInitParameter("deny",""); //黑名单 
    reg.addInitParameter("loginUsername", "admin"); 
    reg.addInitParameter("loginPassword", "admin"); 
    return reg; 
  } 

  @Bean public FilterRegistrationBean filterRegistrationBean() { 
    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(); 
    filterRegistrationBean.setFilter(new WebStatFilter()); 
    filterRegistrationBean.addUrlPatterns("/*"); 
    filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*"); 
    return filterRegistrationBean; 
   }
}