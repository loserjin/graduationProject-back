//package com.canteen.config;
//
//
//import com.canteen.shiro.AccountRealm;
//import com.canteen.shiro.JwtFilter;
//import com.canteen.shiro.UserRealm;
//import org.apache.shiro.mgt.SecurityManager;
//import org.apache.shiro.realm.Realm;
//import org.apache.shiro.session.mgt.SessionManager;
//import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
//import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
//import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
//import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
//import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
//import org.crazycake.shiro.RedisCacheManager;
//import org.crazycake.shiro.RedisSessionDAO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.servlet.Filter;
//import java.util.*;
//
//@Configuration
//public class ShiroConfig {
//
//    @Autowired
//    JwtFilter jwtFilter;
//
//    @Bean
//    public UserRealm userRealm(){
//        UserRealm userRealm=new UserRealm();
//        return userRealm();
//    }
//    @Bean
//    public AccountRealm accountRealm(){
//        AccountRealm accountRealm=new AccountRealm();
//        return accountRealm();
//    }
//
//    @Bean
//    public SessionManager sessionManager(RedisSessionDAO redisSessionDAO) {
//        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
//
//        // inject redisSessionDAO
//        sessionManager.setSessionDAO(redisSessionDAO);
//        return sessionManager;
//    }
//
//    @Bean
//    public DefaultWebSecurityManager securityManager(
//                                                     SessionManager sessionManager,
//                                                     RedisCacheManager redisCacheManager) {
//
//        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//        Collection<Realm> realms=new ArrayList<>();
//        realms.add(userRealm());
//        realms.add(accountRealm());
//        securityManager.setRealms(realms);
//
//        //inject sessionManager
//        securityManager.setSessionManager(sessionManager);
//
//        // inject redisCacheManager
//        securityManager.setCacheManager(redisCacheManager);
//        return securityManager;
//    }
//
//
//    @Bean
//    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
//        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
//
//        Map<String, String> filterMap = new LinkedHashMap<>();
//
//        filterMap.put("/**", "jwt");
//        chainDefinition.addPathDefinitions(filterMap);
//        return chainDefinition;
//    }
//
//    @Bean("shiroFilterFactoryBean")
//    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager,
//                                                         ShiroFilterChainDefinition shiroFilterChainDefinition) {
//        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
//        shiroFilter.setSecurityManager(securityManager);
//
//        Map<String, Filter> filters = new HashMap<>();
//        filters.put("jwt", jwtFilter);
//        shiroFilter.setFilters(filters);
//
//        Map<String, String> filterMap = shiroFilterChainDefinition.getFilterChainMap();
//
//        shiroFilter.setFilterChainDefinitionMap(filterMap);
//        return shiroFilter;
//    }
//
//}
package com.canteen.config;


import com.canteen.entity.User;
import com.canteen.shiro.AccountRealm;
import com.canteen.shiro.JwtFilter;
import com.canteen.shiro.UserRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.*;

@Configuration
public class ShiroConfig {

    @Autowired
    JwtFilter jwtFilter;

    @Bean
    public UserRealm userRealm(){
        UserRealm userRealm=new UserRealm();
        return userRealm;
    }
    @Bean
    public AccountRealm accountRealm(){
        AccountRealm accountRealm=new AccountRealm();
        return accountRealm;
    }

    @Bean
    public SessionManager sessionManager(RedisSessionDAO redisSessionDAO) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();

        // inject redisSessionDAO
        sessionManager.setSessionDAO(redisSessionDAO);
        return sessionManager;
    }

    @Bean
    public DefaultWebSecurityManager securityManager(
                                                     SessionManager sessionManager,
                                                     RedisCacheManager redisCacheManager) {

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        Collection<Realm> realms =new ArrayList<>();
        realms.add(accountRealm());
        realms.add(userRealm());
        securityManager.setRealms(realms);

        //inject sessionManager
        securityManager.setSessionManager(sessionManager);

        redisCacheManager.setPrincipalIdFieldName("adminId");
        // inject redisCacheManager
        securityManager.setCacheManager(redisCacheManager);
        return securityManager;
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();

        Map<String, String> filterMap = new LinkedHashMap<>();

        filterMap.put("/**", "jwt");
        chainDefinition.addPathDefinitions(filterMap);
        return chainDefinition;
    }

    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager,
                                                         ShiroFilterChainDefinition shiroFilterChainDefinition) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);

        Map<String, Filter> filters = new HashMap<>();
        filters.put("jwt", jwtFilter);
        shiroFilter.setFilters(filters);

        Map<String, String> filterMap = shiroFilterChainDefinition.getFilterChainMap();

        shiroFilter.setFilterChainDefinitionMap(filterMap);
        return shiroFilter;
    }

}
