package com.learn.mymall.config;
import com.learn.mymall.component.AdminUserDetails;
import com.learn.mymall.entity.UmsAdmin;
import com.learn.mymall.entity.UmsPermission;
import com.learn.mymall.service.UmsAdminServiceI;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.learn.mymall.component.JwtAuthenticationTokenFilter;
import com.learn.mymall.component.RestAuthenticationEntryPoint;
import com.learn.mymall.component.RestfulAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

/**
 * @program: mymall
 * @description: SpringSecurity的配置
 * @author: likj
 * @create: 2020-04-23 11:07
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UmsAdminServiceI umsAdminServiceI;

    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;


    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf()
                .disable()
                .sessionManagement()//基于token,所以不需要session
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,
                        "/",
                        "/*.html",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/swagger-resources/**",
                        "/v2/api-docs/**"
                        )
                .permitAll()
                .antMatchers("/admin/login","admin/register")//对登录注册要允许匿名访问
                .permitAll()
                .antMatchers(HttpMethod.OPTIONS)//跨域请求会先进行一次options请求
                .permitAll()
//                .antMatchers("/**")
//                .permitAll()
                .anyRequest()//除上面外的所有请求全部需要进行鉴权认证
                .authenticated();
        //禁用缓存
        httpSecurity.headers().cacheControl();
        //添加JWT filter
        httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        httpSecurity.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());

    }

    @Bean
    public UserDetailsService userDetailsService(){
        //获取登录的用户信息
       return userName->{
           //根据用户名获取后台管理员
           UmsAdmin adminByUsername = umsAdminServiceI.getAdminByUsername(userName);
           if (adminByUsername!=null){
               //获取用户的权限
               List<UmsPermission> permissionList = umsAdminServiceI.getPermissionList(adminByUsername.getId());
               return new AdminUserDetails(adminByUsername,permissionList);
           }
           throw new UsernameNotFoundException("用户名或密码错误");
       };
    }


    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(){
        return new JwtAuthenticationTokenFilter();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
