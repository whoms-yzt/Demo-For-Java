package com.whom.config;

import com.whom.controller.MyController;
import com.whom.shiro.MyRealm;
import com.whom.shiro.Oauth2Filter;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;

@Configuration
public class ShiroConfig {
    protected Logger log = LoggerFactory.getLogger(MyController.class);

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {

        log.info("==============shiro过滤器工厂================");
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);

        /*自定义过滤器*/
        HashMap<String, Filter> diyFilter = new HashMap<>(16);
        diyFilter.put("oauth2", new Oauth2Filter());
        factoryBean.setFilters(diyFilter);

        factoryBean.setUnauthorizedUrl("/unAuth");
        /*内置过滤器*/
        HashMap<String, String> defaultFilter = new HashMap<>(16);
        /*
        * anon：无需认证就能访问
        * authc：必须认证才能访问
        * user：必须拥有记住我才能访问
        * perms：拥有对某个资源的权限才能访问
        * role：拥有某个角色权限才能访问
        * */
        defaultFilter.put("/index/update", "authc");
        defaultFilter.put("/index/add", "perms[user:add]");
        defaultFilter.put("/oauth2/*", "oauth2");
        factoryBean.setFilterChainDefinitionMap(defaultFilter);

        factoryBean.setLoginUrl("/toLogin");
        return factoryBean;
    }

    @Bean
    public DefaultWebSecurityManager securityManager() {

        log.info("==============安全管理器================");
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myRealm());
        return securityManager;
    }

    @Bean
    public MyRealm myRealm() {
        log.info("==============realm================");
        return new MyRealm();
    }
}
