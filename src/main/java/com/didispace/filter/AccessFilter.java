package com.didispace.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class AccessFilter extends ZuulFilter  {

    private static Logger log = LoggerFactory.getLogger(AccessFilter.class);

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        log.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));

        Object accessToken = request.getParameter("accessToken");
        if(accessToken == null) {
            log.warn("Access Token is Empty");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            return null;
        }
        else if(!accessToken.toString().equals("123456"))
        {
            log.warn("Access Token is Error");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            return null;
        }
        else {
            log.info("Access Token is Succ");
            /**
             * 成功
             */
            //ctx.setSendZuulResponse(true);
            //ctx.setResponseStatusCode(200);

            /**
             * 失败
             */
            //ctx.setSendZuulResponse(false);
            //ctx.setResponseStatusCode(401);

            /**
             * 默认
             * 不设置ctx.setSendZuulResponse(false);与ctx.setResponseStatusCode(401);也是成功
             */
            return null;
        }
    }

}
