package ua.kpi.comsys.dop;

import java.math.BigDecimal;

public record Deposit(String accountId, BigDecimal amount) implements Transaction {

    public Deposit {
        Validators.requireText(accountId, "accountId");
        Validators.requirePositive(amount);
    }
}