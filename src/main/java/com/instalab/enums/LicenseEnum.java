package com.instalab.enums;

public enum LicenseEnum {
    FREE (1),
    PAID (2);

    private final int code;

    LicenseEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

//    public static String valueOf(int code) {
//        for (LicenseEnum value : LicenseEnum.values()) {
//            if (value.getCode() == code) {
//                return value.name();
//            }
//        }
//        throw new IllegalArgumentException("Invalid License Code");
//    }
}
