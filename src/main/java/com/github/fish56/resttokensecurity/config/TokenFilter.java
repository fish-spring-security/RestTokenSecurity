package com.github.fish56.resttokensecurity.config;

import com.github.fish56.resttokensecurity.entity.User;
import com.github.fish56.resttokensecurity.repository.UserRepository;
import com.github.fish56.resttokensecurity.util.ServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * todo: 当前filter会触发两次？？
 * 监测请求头Authorization中的token，来验证用户的合法性
 * 验证通过的话就将相关信息添加到SecurityContextHolder.getContext()
 */
@Slf4j
@Component
public class TokenFilter extends GenericFilterBean {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException
    {

        log.info("正在处理 " + ((HttpServletRequest) req).getServletPath());
        User user = userRepository.findByToken(ServletUtil.parseTokenFromRequest((HttpServletRequest) req));

        if (user != null) {
            log.info("查询到用户：" + user.toString());
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    user.getUsername(), null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            log.info("token 没有用：" + user.getToken());
        }

        filterChain.doFilter(req, res);
    }

}
