package com.lghcode.briefbook.interceptor;

import com.lghcode.briefbook.config.JwtProperties;
import com.lghcode.briefbook.constant.Constant;
import com.lghcode.briefbook.util.JsonUtils;
import com.lghcode.briefbook.util.ResultJson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * @Author lgh
 * @Date 2020/8/17 17:25
 */
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 拦截请求，在controller调用之前
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authToken = request.getHeader(jwtProperties.getHeader());
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            return true;
        }
        //判断获取的token是否为空
        if (StringUtils.isEmpty(authToken)){
            log.info("-------------------------------------》》》》token不存在：token为空; token = {"+ authToken +"}《《《《《--------------------------------------");
            returnErrorResponse(response, ResultJson.errorTokenMsg("token为空"));
            return false;
        }
        //判断获取的token是否过期
        if (!redisTemplate.hasKey(Constant.REDIS_LOGIN_KEY+authToken)){
            log.info("-------------------------------------》》》》token不存在：token过期; token = {"+ authToken +"}《《《《《--------------------------------------");
            returnErrorResponse(response, ResultJson.errorTokenMsg("token过期"));
            return false;
        }
        return true;
    }

    private void returnErrorResponse(HttpServletResponse response, ResultJson result)
            throws IOException, UnsupportedEncodingException {
        OutputStream out=null;
        try{
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/json");
            out = response.getOutputStream();
            out.write(JsonUtils.objectToJson(result).getBytes(StandardCharsets.UTF_8));
            out.flush();
        } finally{
            if(out!=null){
                out.close();
            }
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
