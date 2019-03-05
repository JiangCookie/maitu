package com.ljc.maitu.config;

import com.ljc.maitu.common.utils.JwtUtil;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * JWT过滤器
 * @author JYH
 * 2019/3/2 12:46
 */
@Configuration
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final PathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            if (isProtectedUrl(request)) {
                String token = request.getHeader("Authorization");
                //检查jwt令牌, 如果令牌不合法或者过期, 里面会直接抛出异常, 下面的catch部分会直接返回
                 JwtUtil.validateToken(token);
            }
        }catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "没有登录权限");
            return;
        }

        //如果jwt令牌通过了检测, 那么就把request传递给后面的RESTful api
        filterChain.doFilter(request, response);
    }

    private boolean isProtectedUrl(HttpServletRequest request){
        boolean one = pathMatcher.match("/manage/user/**",request.getServletPath());
        boolean two = pathMatcher.match("/user/regist",request.getServletPath());
        boolean three = pathMatcher.match("/user/login",request.getServletPath());
        //判断是否静态资源路径
        boolean four = pathMatcher.match("/categoryImage/**",request.getServletPath());


        if(one ||  two || three || four) {
            return false;
        }

        return true;
    }


    @Bean
    public FilterRegistrationBean jwtFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(new JwtAuthenticationFilter());
        return registrationBean;
    }
}
