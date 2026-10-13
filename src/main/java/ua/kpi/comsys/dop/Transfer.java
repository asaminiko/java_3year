package ua.kpi.comsys.dop;

import java.math.BigDecimal;

public record Transfer(String fromAccount, String toAccount, BigDecimal amount) implements Transaction {

    public Transfer {
        Validators.requireText(fromAccount, "fromAccount");
        Validators.requireText(toAccount, "toAccount");
        Validators.requirePositive(amount);
    }
}