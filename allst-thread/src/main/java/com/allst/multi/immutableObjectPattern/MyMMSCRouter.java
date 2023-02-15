package com.allst.multi.immutableObjectPattern;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 业务路由规则管理器，模式角色：Immutable Object
 *
 * @author Hutu
 * @since 2023-02-15 下午 10:19
 */
public final class MyMMSCRouter {
    //
    private static volatile MyMMSCRouter instance = new MyMMSCRouter();
    //
    private final Map<String, MyMMSCInfo> routerMap;

    public MyMMSCRouter() {
        this.routerMap = MyMMSCRouter.retrieveRouterMapFromDB();
    }

    private static Map<String, MyMMSCInfo> retrieveRouterMapFromDB() {
        // 省略业务代码
        return new HashMap<>();
    }

    public static MyMMSCRouter getInstance() {
        return instance;
    }

    public MyMMSCInfo getMMSC(String msisdnPrefix) {
        return routerMap.get(msisdnPrefix);
    }

    public static void setInstance(MyMMSCRouter newInstance) {
        instance = newInstance;
    }

    public static Map<String, MyMMSCInfo> deepCopy(Map<String, MyMMSCInfo> m) {
        Map<String, MyMMSCInfo> result = new HashMap<>();
        for (String key : m.keySet()) {
            result.put(key, new MyMMSCInfo(m.get(key)));
        }
        return result;
    }

    public Map<String, MyMMSCInfo> getRouteMap() {
        return Collections.unmodifiableMap(deepCopy(routerMap));
    }
}
