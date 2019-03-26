package com.tim.spi.api;

public interface Driver {

    String connect(String host, int port);

    String disConnect();
}
