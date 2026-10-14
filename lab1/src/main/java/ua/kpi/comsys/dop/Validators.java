package ua.kpi.comsys.dop;

import java.math.BigDecimal;

final class Validators {

    private Validators() {
    }

    static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be null or blank");
        }
        return value;
    }

    static BigDecimal requirePositive(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("amount must not be null and must be greater than 0");
        }
        return amount;
    }
}