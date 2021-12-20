package cn.design.patterns.states.simple3;

import java.util.HashMap;
import java.util.Map;

/**
 * desc: TODO
 *
 * @author Kroos.luo
 * @since 2020-06-09 13:55
 */
public class DealState extends BaseDealState {

    public DealState(String name, BaseDealState baseDealState) {
        super(name, baseDealState);
    }
}

class BaseDealState {
    static final Map<String, BaseDealState> dealMap = new HashMap<>();

    public BaseDealState(String name, BaseDealState baseDealState) {
        dealMap.put(name, baseDealState);
    }
}
