package com.didispace.filter.post

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.netflix.zuul.ZuulFilter

class PostFilter extends ZuulFilter{

  Logger log = LoggerFactory.getLogger(PostFilter.class);

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
    log.info("this is a post filter aaaaaaa");
  }
}
