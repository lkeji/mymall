package com.learn.mymall.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.UserDetails;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: mymall
 * @description:
 * JwtToken生成的工具类
 * JWT token的格式：header.payload.signature
 * header的格式（算法、token的类型）：
 * {"alg": "HS512","typ": "JWT"}
 * payload的格式（用户名、创建时间、生成时间）：
 * {"sub":"wang","created":1489079981393,"exp":1489684781}
 * signature的生成算法：
 * HMACSHA512(base64UrlEncode(header) + "." +base64UrlEncode(payload),secret)
 * @author: likj
 * @create: 2020-04-23 10:29
 */
@Component
public class JwtTokenUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);
    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";

    //JWT加解密使用的密钥
    @Value("${jwt.secret}")
    private String secret;
    //JWT的超期限时间
    @Value("${jwt.expiration}")
    private Long  expiration;

    /*
    * 验证token是否还有效果
     * @param token       客户端传入的token
     * @param userDetails 从数据库中查询出来的用户信息
    * */
    public boolean validateToken (String token,UserDetails userDetails){
        String userNameFromToken = getUserNameFromToken(token);
        return  userNameFromToken.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
    
    /*
    * token的过期时间
    * */
    private Date generateExpirationDate(){
        return new Date(System.currentTimeMillis() + expiration * 1000 );
    }
    /*
    * 从token中获取JWT的负载
    * */
    private Claims getClaimsFromToken(String token){
        Claims claims =null;
        try {
            claims=Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception ex){
            LOGGER.info("JWT格式验证失败:{}",token);
        }
        return claims;
    }
    /*
    * 从token中获取登录名
    * */
    public String getUserNameFromToken(String token){
        String userName=null;
        try {
            Claims claimsFromToken = getClaimsFromToken(token);
             userName = claimsFromToken.getSubject();
        }catch (Exception ex){
            userName=null;
        }
        return userName;
    }
    /*
    * 判断token是否已经失效
    * */
    private boolean isTokenExpired(String token){
        Date expiredDateFromToken = getExpiredDateFromToken(token);
        return  expiredDateFromToken.before(new Date());
    }
    /*
    * 从token中获取该token的过期时间
    * */
    private Date getExpiredDateFromToken(String token){
        Claims claimsFromToken = getClaimsFromToken(token);
        return  claimsFromToken.getExpiration();
    }
    /*
    * 根据用户信息生成token
    * */
    public String generateToken(UserDetails userDetails){
        Map<String,Object> claims=new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME,userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED,new Date());
        return generateToken(claims);
    }

    /*
    * 判断token是否可以被刷新
    * */
    public boolean canRefresh(String token){
        return !isTokenExpired(token);
    }

    /*
    * 刷新token(重新设置token的过期时间)
    * */
    public String refreshToken(String token){
        Claims claimsFromToken = getClaimsFromToken(token);
        claimsFromToken.put(CLAIM_KEY_CREATED,new Date());
        return generateToken(claimsFromToken);
    }



    /*
     * 生成JWT的token
     * */
    private String generateToken(Map<String,Object> claims){
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())//token的过期时间
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }













}
