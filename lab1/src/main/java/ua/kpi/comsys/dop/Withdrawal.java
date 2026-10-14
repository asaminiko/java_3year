package ua.kpi.comsys.dop;

import java.math.BigDecimal;

public record Withdrawal(String accountId, BigDecimal amount, String category) implements Transaction {

    public Withdrawal {
        Validators.requireText(accountId, "accountId");
        Validators.requirePositive(amount);
        Validators.requireText(category, "category");
    }
}