package com.tim.wiremock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

/**
 * @author Kroos.luo
 * @since 2020-02-25 23:05
 */
public class Application {

    public static void main(String[] args) {
        WireMockServer wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(8099));
        wireMockServer.start();
    }
}
