package ua.kpi.comsys.dop;

import java.math.BigDecimal;

public sealed interface Transaction {
    record Deposit(String accountId, BigDecimal amount) implements Transaction {
        public Deposit {
            if (accountId == null || accountId.isBlank()) {
                throw new IllegalArgumentException("accountId cannot be null or blank");
            }
            if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("amount must be greater than 0");
            }
        }
    }
    record Withdrawal(String accountId, BigDecimal amount, String category) implements Transaction {
        public Withdrawal {
            if (accountId == null || accountId.isBlank()) {
                throw new IllegalArgumentException("accountId cannot be null or blank");
            }
            if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("amount must be greater than 0");
            }
            if (category == null || category.isBlank()) {
                throw new IllegalArgumentException("category cannot be null or blank");
            }
        }
    }
    record Transfer(String fromAccount, String toAccount, BigDecimal amount) implements Transaction {
        public Transfer {
            if (fromAccount == null || fromAccount.isBlank()) {
                throw new IllegalArgumentException("fromAccount cannot be null or blank");
            }
            if (toAccount == null || toAccount.isBlank()) {
                throw new IllegalArgumentException("toAccount cannot be null or blank");
            }
            if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("amount must be greater than 0");
            }
        }
    }
}
