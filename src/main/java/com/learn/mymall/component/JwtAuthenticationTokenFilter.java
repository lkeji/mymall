package com.learn.mymall.component;

import com.learn.mymall.common.utils.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.userdetails.UserDetailsService;
import springfox.documentation.spi.service.contexts.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: mymall
 * @description: JWT登录授权过滤器
 * @author: likj
 * @create: 2020-04-23 11:32
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = httpServletRequest.getHeader(this.tokenHeader);
        //判断请求头是否是指定的前缀
        if(authHeader !=null && authHeader.startsWith(this.tokenHead)){
          String authToken= authHeader.substring(this.tokenHead.length());
            String userName = jwtTokenUtil.getUserNameFromToken(authToken);
            LOGGER.info("checking username:{}", userName);
            if (userName !=null && SecurityContextHolder.getContext().getAuthentication()== null){
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
                //验证token是否有效
                if(jwtTokenUtil.validateToken(authToken,userDetails)){
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    LOGGER.info("authenticated user:{}", userName);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }



            }
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
