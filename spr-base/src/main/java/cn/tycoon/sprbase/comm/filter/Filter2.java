package cn.tycoon.sprbase.comm.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * @description: 自定义filter过滤器 （实现servlet Filter接口）
 * @author: he.l
 * @create: 2019-04-12 14:52
 **/
public class Filter2 implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        System.out.println("过滤器-----------》filter2 生效");
        chain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
