package com.erp.greenlight.enums;

public enum AccountTypesEnum {
    RAS_EL_MAL("راس المال",1),
    SUPPLIER("مورد",2),
    CUSTOMER("عميل",3),
    DELEGATE("مندوب",4),
    EMPLOYEE("موظف",5),
    BANK("بنكي",6),
    EXPENSES("مصروفات",7),
    INTERNAL_DEPARTMENT("قسم داخلي",8),
    PUBLIC("عام",9);

    private final String key;
    private final Integer value;

    AccountTypesEnum(String key, Integer value) {
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
