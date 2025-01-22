package com.AcademyCode.AcademyCode.enums;

public enum Status {
    ACTIVE("active"),
    DISABLED("disabled");

    private String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
