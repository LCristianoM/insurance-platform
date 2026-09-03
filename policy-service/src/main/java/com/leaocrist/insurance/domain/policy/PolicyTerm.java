package com.leaocrist.insurance.domain.policy;

public enum PolicyTerm {
    SIX_MONTHS(6),
    ONE_YEAR(12);

    private final int months;

    PolicyTerm(int months) {
        this.months = months;
    }

    public int getMonths() {
        return months;
    }
}
