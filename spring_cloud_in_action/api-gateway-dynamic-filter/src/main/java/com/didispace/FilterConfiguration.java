package com.didispace;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 翟永超
 * @create 2016/12/11.
 * @blog http://blog.didispace.com
 */
@ConfigurationProperties("zuul.filter")
public class FilterConfiguration {

    private String root;
    private Integer interval;

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }
}
