package com.academycode.academycode.modules.course.enums;

public enum Status {
    ACTIVE("active"),
    DISABLED("disabled");

    private String statusCourse;

    Status(String statusCourse) {
        this.statusCourse = statusCourse;
    }

    public String getStatus() {
        return statusCourse;
    }
}
