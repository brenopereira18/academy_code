package com.AcademyCode.AcademyCode.modules.course.enums;

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
