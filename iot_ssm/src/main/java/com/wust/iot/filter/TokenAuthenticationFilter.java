package com.wust.iot.filter;

import com.wust.iot.dto.Result;
import com.wust.iot.enums.ResultEnums;
import com.wust.iot.exception.SimpleException;
import com.wust.iot.model.User;
import com.wust.iot.service.UserService;
import com.wust.iot.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private UserService userService;
    //是否允许创建session
    private static ThreadLocal<Boolean> allowSessionCreation = new ThreadLocal<Boolean>();


    protected TokenAuthenticationFilter() {
        super(new AntPathRequestMatcher("/user/login", "POST"));
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        //获取http头部token
        String token = request.getHeader("token");
        logger.info("取到的token为:" + token);

        //判断token
        int userId ;
        try {
            userId = JwtUtil.getUserIdFromToken(token);
        } catch (Exception e) {
            throw new SimpleException(ResultEnums.TOKEN_PARSER_ERROR);
        }
        ValueOperations<String,String> stringOperations = redisTemplate.opsForValue();

        String jToken = stringOperations.get(""+userId);
        if (!jToken.equals(token)) {
            //抛出 token不符异常
//            throw new SimpleException(ResultEnums.TOKEN_NOT_RIGHT);
        }

        // 使用 token 信息查找缓存中的登陆用户信息，下面为了测试方便直接写死一个
        User user = userService.findOneUserById(userId);
        return new UsernamePasswordAuthenticationToken(user, user.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
    }


    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Authentication authentication = null;

        //默认创建session
        allowSessionCreation.set(true);

        //如果header里面有 token时，则使用 token 查询用户数据进行登录验证
        if (request.getHeader("token") != null) {
            //1.尝试进行身份验证
            //2.如果用户无效，则返回401
            //3.如果用户有效，则保存到SecurityContext中，供本次方式后续使用
            authentication = attemptAuthentication(request, response);

            if (authentication == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is invalid, please apply again");
                return;
            }

            //保存认证信息到SecurityContext，禁止HttpSessionSecurityContextRepository创建session
            allowSessionCreation.set(false);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        //继续调用下一个filter
        chain.doFilter(request, response);
    }

    public static boolean isAllowSessionCreation() {
        return allowSessionCreation.get();
    }
}
