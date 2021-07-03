package com.kv.wc.common.enums;

public enum TimeUnitEnums {
    S("S", "秒"), M("M", "分钟"), H("H", "小时"), D("D", "天"), WEEK("WEEK", "周"), MON("MON", "月"), YEAR("YEAR", "年");

    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    TimeUnitEnums(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
