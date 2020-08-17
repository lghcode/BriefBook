package com.lghcode.briefbook.util;


import cn.hutool.core.util.RandomUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lghcode.briefbook.config.JwtProperties;
import com.lghcode.briefbook.exception.BizException;
import com.lghcode.briefbook.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * jwt token工具类
 * </p>
 *
 * <pre>
 *     jwt的claim里一般包含以下几种数据:
 *         1. iss -- token的发行者
 *         2. sub -- 该JWT所面向的用户
 *         3. aud -- 接收该JWT的一方
 *         4. exp -- token的失效时间
 *         5. nbf -- 在此时间段之前,不会被处理
 *         6. iat -- jwt发布时间
 *         7. jti -- jwt唯一标识,防止重复使用
 * </pre>
 *
 * @author ZhangLinhong
 * @Date 2019/11/05
 */
@Component
public class JwtTokenUtil {

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 从token中获取userName
     */
    public String getUserMobieFromToken(String token) {
        DecodedJWT verifier = JWT.decode(token);
        Claim userName = verifier.getClaim("mobile");
        return userName.asString();
    }

    /**
     * 根据request获取userName
     */
    public String getUserMobileFromHeader(HttpServletRequest request) {
        String token = request.getHeader(jwtProperties.getHeader());
        DecodedJWT verifier = JWT.decode(token);
        Claim userName = verifier.getClaim("mobile");
        return userName.asString();
    }

    /**
     * 根据Token获取 userId
     */
    public Long getUserIdFromToken(String token) {
        DecodedJWT verifier = JWT.decode(token);
        Claim userId = verifier.getClaim("userId");
        return userId.asLong();
    }

    /**
     * 根据request获取userId
     */
    public Long getUserIdFromHeader(HttpServletRequest request) {
        String token = request.getHeader(jwtProperties.getHeader());
        DecodedJWT verifier = JWT.decode(token);
        Claim userId = verifier.getClaim("userId");
        return userId.asLong();
    }

    /**
     * 获取jwt发布时间
     */
    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token).getIssuedAt();
    }

    /**
     * 获取jwt失效时间
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token).getExpiration();
    }

    /**
     * 获取jwt接收者
     */
    public String getAudienceFromToken(String token) {
        return getClaimFromToken(token).getAudience();
    }

    /**
     * 获取私有的jwt claim
     */
    public String getPrivateClaimFromToken(String token, String key) {
        return getClaimFromToken(token).get(key).toString();
    }

    /**
     * 获取md5 key从token中
     */
    public String getMd5KeyFromToken(String token) {
        return getPrivateClaimFromToken(token, jwtProperties.getMd5Key());
    }

    /**
     * 获取jwt的payload部分
     */
    public Claims getClaimFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtProperties.getSecret()).parseClaimsJws(token).getBody();
    }

    /**
     * 解析token是否正确,不正确会报异常<br>
     */
    public void parseToken(String token) throws BizException {
        Jwts.parser().setSigningKey(jwtProperties.getSecret()).parseClaimsJws(token).getBody();
    }

    /**
     * <pre>
     *  验证token是否失效
     *  true:过期   false:没过期
     * </pre>
     */
    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * 生成token(通过用户名和签名时候用的随机数)
     *
     * @param user 用户
     *
     * @return
     */
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>(16);
        claims.put(jwtProperties.getMd5Key(), RandomUtil.randomString(8));
        claims.put("userId", user.getId());
        claims.put("mobile", user.getMobile());
        return doGenerateToken(claims, user.getMobile());
    }

    /**
     * 生成token
     */
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + jwtProperties.getExpiration() * 1000);

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(createdDate)
                .setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, jwtProperties.getSecret()).compact();
    }

}