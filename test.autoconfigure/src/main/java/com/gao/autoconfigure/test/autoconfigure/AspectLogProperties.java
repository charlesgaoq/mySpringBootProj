package com.gao.autoconfigure.test.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("aspectLog")
public class AspectLogProperties {
    private boolean enable;
    public boolean isEnable() {
        return enable;
    }
    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}