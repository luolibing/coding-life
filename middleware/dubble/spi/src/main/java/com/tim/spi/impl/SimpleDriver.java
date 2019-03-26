package com.tim.spi.impl;

import com.tim.spi.api.Driver;

public class SimpleDriver implements Driver {
    @Override
    public String connect(String host, int port) {
        return "simple driver connect";
    }

    @Override
    public String disConnect() {
        return "simple driver disConnect";
    }
}
