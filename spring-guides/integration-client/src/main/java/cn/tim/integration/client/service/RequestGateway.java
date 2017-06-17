package cn.tim.integration.client.service;

import java.util.List;
import java.util.Map;

/**
 * Created by LuoLiBing on 16/1/26.
 */
public interface RequestGateway {

    Object attack(List<Map<String, String>> request);
}
