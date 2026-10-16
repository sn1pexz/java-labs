package ua.kpi.comsys.dop;

public class TransactionProcessor {
    public String process(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }

        return switch (transaction) {
            case Transaction.Deposit(var accountId, var amount) -> """
                    Transaction Type: DEPOSIT
                    Account: %s
                    Amount: USD %s""".formatted(accountId, amount);

            case Transaction.Withdrawal(var accountId, var amount, var category) -> """
                    Transaction Type: WITHDRAWAL
                    Account: %s
                    Category: %s
                    Amount: USD %s""".formatted(accountId, category, amount);

            case Transaction.Transfer(var fromAccount, var toAccount, var amount) -> """
                    Transaction Type: TRANSFER
                    From: %s
                    To: %s
                    Amount: USD %s""".formatted(fromAccount, toAccount, amount);
        };
    }
}
