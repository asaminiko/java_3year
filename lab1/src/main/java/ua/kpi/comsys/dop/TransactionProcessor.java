package ua.kpi.comsys.dop;

public class TransactionProcessor {

    public String process(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }

        return switch (transaction) {
            case Deposit d -> """
                    Transaction Type: DEPOSIT
                    Account: %s
                    Amount: USD %s""".formatted(
                    d.accountId(),
                    d.amount().toPlainString());

            case Withdrawal w -> """
                    Transaction Type: WITHDRAWAL
                    Account: %s
                    Category: %s
                    Amount: USD %s""".formatted(
                    w.accountId(),
                    w.category(),
                    w.amount().toPlainString());

            case Transfer t -> """
                    Transaction Type: TRANSFER
                    From: %s
                    To: %s
                    Amount: USD %s""".formatted(
                    t.fromAccount(),
                    t.toAccount(),
                    t.amount().toPlainString());
        };
    }
}