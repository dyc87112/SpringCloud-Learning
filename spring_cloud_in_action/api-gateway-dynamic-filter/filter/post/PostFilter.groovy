package com.didispace.filter.post

import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext
import com.netflix.zuul.http.HttpServletResponseWrapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cloud.netflix.zuul.util.RequestUtils

import javax.servlet.http.HttpServletResponse

class PostFilter extends ZuulFilter{

  Logger log = LoggerFactory.getLogger(PostFilter.class)

  @Override
  String filterType() {
    return "post"
  }

  @Override
  int filterOrder() {
    return 2000
  }

  @Override
  boolean shouldFilter() {
    return true
  }

  @Override
  Object run() {
    log.info("debug request ： {}", RequestContext.getCurrentContext().getBoolean("debugRequest"))
    log.info("this is a post filter: Receive response")
    HttpServletResponse response = RequestContext.getCurrentContext().getResponse()
    response.getOutputStream().print(", I am zhaiyongchao")
    response.flushBuffer()
  }
}
