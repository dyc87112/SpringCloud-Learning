package com.didispace;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/*网关服务的加入，外部客户端访问我们的系统已经有了统一入口，既然这些校验与具体业务无关，那何不在请求到达的时候就完成校验和过滤，而不是转发后再过滤而导致更长的请求延迟。
同时，通过在网关中完成校验和过滤，微服务应用端就可以去除各种复杂的过滤器和拦截器了，这使得微服务应用的接口开发和测试复杂度也得到了相应的降低。

为了在API网关中实现对客户端请求的校验，我们将需要使用到Spring Cloud Zuul的另外一个核心功能：过滤器。

Zuul允许开发者在API网关上通过定义过滤器来实现对请求的拦截与过滤，实现的方法非常简单，我们只需要继承ZuulFilter抽象类并实现它定义的四个抽象函数就可以完成对请求的拦截和过滤了。

可以根据自己的需要在服务网关上定义一些与业务无关的通用逻辑实现对请求的过滤和拦截，比如：签名校验、权限校验、请求限流等功能。
*/
public class AccessFilter extends ZuulFilter {
    private static Logger log = LoggerFactory.getLogger(AccessFilter.class);

    /*filterType：过滤器的类型，它决定过滤器在请求的哪个生命周期中执行。
    这里定义为pre，代表会在请求被路由之前执行。*/
    @Override
    public String filterType() {
        return "pre";
    }

    /*filterOrder：过滤器的执行顺序。
    当请求在一个阶段中存在多个过滤器时，需要根据该方法返回的值来依次执行。*/
    @Override
    public int filterOrder() {
        return 0;
    }

    /*shouldFilter：判断该过滤器是否需要被执行。
    这里我们直接返回了true，因此该过滤器对所有请求都会生效。
    实际运用中我们可以利用该函数来指定过滤器的有效范围。*/
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /*run：过滤器的具体逻辑。
    这里我们通过ctx.setSendZuulResponse(false)令zuul过滤该请求，不对其进行路由，
    然后通过ctx.setResponseStatusCode(401)设置了其返回的错误码，当然我们也可以进一步优化我们的返回，
    比如，通过ctx.setResponseBody(body)对返回body内容进行编辑等。*/
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        log.info("send {} request to {}", request.getMethod(), request.getRequestURL().toString());

        Object accessToken = request.getParameter("accessToken");
        if (accessToken == null) {
            log.warn("access token is empty");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            return null;
        }
        log.info("access token ok");
        return null;
    }

}