package ua.kpi.comsys.dop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TransactionProcessorTest {

    private TransactionProcessor processor;

    @BeforeEach
    void setUp() {
        processor = new TransactionProcessor();
    }
    @Test
    @DisplayName("process(null) throws IllegalArgumentException")
    void processNullThrowsException() {
        var ex = assertThrows(IllegalArgumentException.class, () -> processor.process(null));
        assertEquals("Transaction cannot be null", ex.getMessage());
    }

    @Nested
    @DisplayName("Deposit processing")
    class DepositTests {

        @Test
        @DisplayName("process valid Deposit returns correct text block")
        void processValidDeposit() {
            var deposit = new Transaction.Deposit("ACC-001", new BigDecimal("250.50"));
            String result = processor.process(deposit);

            String expected = """
                    Transaction Type: DEPOSIT
                    Account: ACC-001
                    Amount: USD 250.50""";

            assertEquals(expected, result);
        }

        @Test
        @DisplayName("Deposit with null accountId throws IllegalArgumentException")
        void depositNullAccountId() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Transaction.Deposit(null, new BigDecimal("100")));
        }

        @Test
        @DisplayName("Deposit with blank accountId throws IllegalArgumentException")
        void depositBlankAccountId() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Transaction.Deposit("   ", new BigDecimal("100")));
        }

        @Test
        @DisplayName("Deposit with null amount throws IllegalArgumentException")
        void depositNullAmount() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Transaction.Deposit("ACC-001", null));
        }

        @Test
        @DisplayName("Deposit with zero amount throws IllegalArgumentException")
        void depositZeroAmount() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Transaction.Deposit("ACC-001", BigDecimal.ZERO));
        }

        @Test
        @DisplayName("Deposit with negative amount throws IllegalArgumentException")
        void depositNegativeAmount() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Transaction.Deposit("ACC-001", new BigDecimal("-10")));
        }
    }

    @Nested
    @DisplayName("Withdrawal processing")
    class WithdrawalTests {

        @Test
        @DisplayName("process valid Withdrawal returns correct text block")
        void processValidWithdrawal() {
            var withdrawal = new Transaction.Withdrawal("ACC-002", new BigDecimal("99.99"), "Groceries");
            String result = processor.process(withdrawal);

            String expected = """
                    Transaction Type: WITHDRAWAL
                    Account: ACC-002
                    Category: Groceries
                    Amount: USD 99.99""";

            assertEquals(expected, result);
        }

        @Test
        @DisplayName("Withdrawal with null accountId throws IllegalArgumentException")
        void withdrawalNullAccountId() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Transaction.Withdrawal(null, new BigDecimal("50"), "Food"));
        }

        @Test
        @DisplayName("Withdrawal with blank accountId throws IllegalArgumentException")
        void withdrawalBlankAccountId() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Transaction.Withdrawal("", new BigDecimal("50"), "Food"));
        }

        @Test
        @DisplayName("Withdrawal with null amount throws IllegalArgumentException")
        void withdrawalNullAmount() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Transaction.Withdrawal("ACC-002", null, "Food"));
        }

        @Test
        @DisplayName("Withdrawal with zero amount throws IllegalArgumentException")
        void withdrawalZeroAmount() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Transaction.Withdrawal("ACC-002", BigDecimal.ZERO, "Food"));
        }

        @Test
        @DisplayName("Withdrawal with negative amount throws IllegalArgumentException")
        void withdrawalNegativeAmount() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Transaction.Withdrawal("ACC-002", new BigDecimal("-1"), "Food"));
        }

        @Test
        @DisplayName("Withdrawal with null category throws IllegalArgumentException")
        void withdrawalNullCategory() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Transaction.Withdrawal("ACC-002", new BigDecimal("50"), null));
        }

        @Test
        @DisplayName("Withdrawal with blank category throws IllegalArgumentException")
        void withdrawalBlankCategory() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Transaction.Withdrawal("ACC-002", new BigDecimal("50"), "   "));
        }
    }

    @Nested
    @DisplayName("Transfer processing")
    class TransferTests {

        @Test
        @DisplayName("process valid Transfer returns correct text block")
        void processValidTransfer() {
            var transfer = new Transaction.Transfer("ACC-001", "ACC-003", new BigDecimal("1000"));
            String result = processor.process(transfer);

            String expected = """
                    Transaction Type: TRANSFER
                    From: ACC-001
                    To: ACC-003
                    Amount: USD 1000""";

            assertEquals(expected, result);
        }

        @Test
        @DisplayName("Transfer with null fromAccount throws IllegalArgumentException")
        void transferNullFromAccount() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Transaction.Transfer(null, "ACC-003", new BigDecimal("100")));
        }

        @Test
        @DisplayName("Transfer with blank fromAccount throws IllegalArgumentException")
        void transferBlankFromAccount() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Transaction.Transfer("  ", "ACC-003", new BigDecimal("100")));
        }

        @Test
        @DisplayName("Transfer with null toAccount throws IllegalArgumentException")
        void transferNullToAccount() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Transaction.Transfer("ACC-001", null, new BigDecimal("100")));
        }

        @Test
        @DisplayName("Transfer with blank toAccount throws IllegalArgumentException")
        void transferBlankToAccount() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Transaction.Transfer("ACC-001", "", new BigDecimal("100")));
        }

        @Test
        @DisplayName("Transfer with null amount throws IllegalArgumentException")
        void transferNullAmount() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Transaction.Transfer("ACC-001", "ACC-003", null));
        }

        @Test
        @DisplayName("Transfer with zero amount throws IllegalArgumentException")
        void transferZeroAmount() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Transaction.Transfer("ACC-001", "ACC-003", BigDecimal.ZERO));
        }

        @Test
        @DisplayName("Transfer with negative amount throws IllegalArgumentException")
        void transferNegativeAmount() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Transaction.Transfer("ACC-001", "ACC-003", new BigDecimal("-500")));
        }
    }
}
