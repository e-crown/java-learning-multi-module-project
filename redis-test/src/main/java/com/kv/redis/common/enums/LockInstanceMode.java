package com.kv.redis.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Desc
 *
 * @Date 2020/10/12 16:16
 * @Version 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum LockInstanceMode {

    /**
     * redis单机模式
     */
    SINGLE("single", "SINGLE"),
    /**
     * redis主从模式
     */
    MASTERSLAVE("master-slave", "MASTER-SLAVE"),
    /**
     * redis哨兵模式
     */
    SENTINEL("sentinel", "SENTINEL"),
    /**
     * redis集群cluster模式
     */
    CLUSTER("cluster", "CLUSTER");

    private String model;
    private String desc;

    public static LockInstanceMode parse(final String name) {
        for (final LockInstanceMode modeIns : LockInstanceMode.values()) {
            if (modeIns.name().equals(name.toUpperCase())) {
                return modeIns;
            }
        }
        return null;
    }
}
