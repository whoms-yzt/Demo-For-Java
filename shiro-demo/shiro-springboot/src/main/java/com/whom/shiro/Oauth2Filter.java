package com.whom.shiro;

import com.whom.controller.MyController;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class Oauth2Filter extends AuthenticatingFilter {
    protected Logger log = LoggerFactory.getLogger(MyController.class);

    public Oauth2Filter() {
        log.info("===============执行了自定义的过滤器================");
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        log.info("===============如果允许请求正常通过过滤器，则返回 true；如果请求应该由 onAccessDenied(request,response,mappedValue) 方法处理，则返回 false================");
        return false;
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        log.info("===============创建token================");
        return new UsernamePasswordToken();
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        log.info("===============处理由 isAccessAllowed 方法确定的主题被拒绝访问的请求================");
        AuthenticationToken token = createToken(servletRequest, servletResponse);
        Subject subject = SecurityUtils.getSubject();
        onLoginSuccess(token,subject,servletRequest,servletResponse);
        return false;
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        log.info("===============登录成功================");
        return super.onLoginSuccess(token, subject, request, response);
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        log.info("===============登录失败================");
        return super.onLoginFailure(token, e, request, response);
    }
}
