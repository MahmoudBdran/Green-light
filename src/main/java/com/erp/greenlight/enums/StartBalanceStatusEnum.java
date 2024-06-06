package com.erp.greenlight.enums;

public enum StartBalanceStatusEnum {
    CREDIT("CREDIT",1),
    DEBIT("DEBIT",2),
    BALANCED("BALANCED",3);
    private final String key;
    private final Integer value;

    StartBalanceStatusEnum(String key, Integer value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }
    public Integer getValue() {
        return value;
    }
}
