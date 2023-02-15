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
    // 用volatile关键之修饰，保证在多线程环境下该变量的可见性
    private static volatile MyMMSCRouter instance = new MyMMSCRouter();
    // 维护映射关系
    private final Map<String, MyMMSCInfo> routerMap;

    public MyMMSCRouter() {
        // 讲数据库表中的数据加载到内存， 存为Map
        this.routerMap = MyMMSCRouter.retrieveRouterMapFromDB();
    }

    private static Map<String, MyMMSCInfo> retrieveRouterMapFromDB() {
        // 省略业务代码
        return new HashMap<>();
    }

    public static MyMMSCRouter getInstance() {
        return instance;
    }

    /**
     * 返回映射实体信息
     */
    public MyMMSCInfo getMMSC(String msisdnPrefix) {
        return routerMap.get(msisdnPrefix);
    }

    /**
     * 将当前的MyMMSCRouter实例更新为指定的新实例
     */
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
        // 进行防御性复制
        return Collections.unmodifiableMap(deepCopy(routerMap));
    }
}
