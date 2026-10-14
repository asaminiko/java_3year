package ua.kpi.comsys.dop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TransactionProcessorTest {

    private TransactionProcessor processor;

    @BeforeEach
    void setUp() {
        processor = new TransactionProcessor();
    }


    @Test
    @DisplayName("Deposit: формат Text Block")
    void processDeposit() {
        String expected = """
                Transaction Type: DEPOSIT
                Account: ACC-1
                Amount: USD 100.50""";

        assertEquals(expected,
                processor.process(new Deposit("ACC-1", new BigDecimal("100.50"))));
    }

    @Test
    @DisplayName("Withdrawal: формат Text Block")
    void processWithdrawal() {
        String expected = """
                Transaction Type: WITHDRAWAL
                Account: ACC-2
                Category: Food
                Amount: USD 25.00""";

        assertEquals(expected,
                processor.process(new Withdrawal("ACC-2", new BigDecimal("25.00"), "Food")));
    }

    @Test
    @DisplayName("Transfer: формат Text Block")
    void processTransfer() {
        String expected = """
                Transaction Type: TRANSFER
                From: ACC-3
                To: ACC-4
                Amount: USD 1000""";

        assertEquals(expected,
                processor.process(new Transfer("ACC-3", "ACC-4", new BigDecimal("1000"))));
    }

    @Test
    @DisplayName("Велика сума виводиться без експоненти")
    void processUsesPlainNotation() {
        BigDecimal amount = new BigDecimal("1E+3");

        String result = processor.process(new Deposit("ACC-1", amount));

        assertEquals("""
                Transaction Type: DEPOSIT
                Account: ACC-1
                Amount: USD 1000""", result);
    }


    @Test
    @DisplayName("process(null) кидає IllegalArgumentException з точним повідомленням")
    void processNullThrows() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> processor.process(null));

        assertEquals("Transaction cannot be null", ex.getMessage());
    }


    @Nested
    @DisplayName("Валідація Deposit")
    class DepositValidation {

        @Test
        void nullAccountId() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Deposit(null, BigDecimal.TEN));
        }

        @Test
        void blankAccountId() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Deposit("   ", BigDecimal.TEN));
        }

        @Test
        void emptyAccountId() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Deposit("", BigDecimal.TEN));
        }

        @Test
        void nullAmount() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Deposit("ACC-1", null));
        }

        @Test
        void zeroAmount() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Deposit("ACC-1", BigDecimal.ZERO));
        }

        @Test
        void negativeAmount() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Deposit("ACC-1", new BigDecimal("-1")));
        }

        @Test
        void validDeposit() {
            assertDoesNotThrow(() -> new Deposit("ACC-1", new BigDecimal("0.01")));
        }
    }


    @Nested
    @DisplayName("Валідація Withdrawal")
    class WithdrawalValidation {

        @Test
        void nullAccountId() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Withdrawal(null, BigDecimal.TEN, "Food"));
        }

        @Test
        void blankAccountId() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Withdrawal(" ", BigDecimal.TEN, "Food"));
        }

        @Test
        void nullAmount() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Withdrawal("ACC-1", null, "Food"));
        }

        @Test
        void zeroAmount() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Withdrawal("ACC-1", BigDecimal.ZERO, "Food"));
        }

        @Test
        void negativeAmount() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Withdrawal("ACC-1", new BigDecimal("-5"), "Food"));
        }

        @Test
        void nullCategory() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Withdrawal("ACC-1", BigDecimal.TEN, null));
        }

        @Test
        void blankCategory() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Withdrawal("ACC-1", BigDecimal.TEN, "  "));
        }

        @Test
        void validWithdrawal() {
            assertDoesNotThrow(() -> new Withdrawal("ACC-1", BigDecimal.TEN, "Food"));
        }
    }


    @Nested
    @DisplayName("Валідація Transfer")
    class TransferValidation {

        @Test
        void nullFromAccount() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Transfer(null, "ACC-2", BigDecimal.TEN));
        }

        @Test
        void blankFromAccount() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Transfer(" ", "ACC-2", BigDecimal.TEN));
        }

        @Test
        void nullToAccount() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Transfer("ACC-1", null, BigDecimal.TEN));
        }

        @Test
        void blankToAccount() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Transfer("ACC-1", "", BigDecimal.TEN));
        }

        @Test
        void nullAmount() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Transfer("ACC-1", "ACC-2", null));
        }

        @Test
        void zeroAmount() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Transfer("ACC-1", "ACC-2", BigDecimal.ZERO));
        }

        @Test
        void negativeAmount() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Transfer("ACC-1", "ACC-2", new BigDecimal("-10")));
        }

        @Test
        void validTransfer() {
            assertDoesNotThrow(() -> new Transfer("ACC-1", "ACC-2", BigDecimal.TEN));
        }
    }
}