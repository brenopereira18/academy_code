package com.AcademyCode.AcademyCode.enums;

public enum Categories {
    FRONT_END("Front-End"),
    BACK_END("Back-End"),
    DEVOPS("DevOps"),
    IA("IA"),
    BANCO_DE_DADOS("Banco de Dados"),
    FULL_STACK("Full Stack");

    private final String displayName;

    Categories(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
