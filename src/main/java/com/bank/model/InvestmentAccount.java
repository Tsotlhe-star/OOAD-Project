package com.bank.model;

public class InvestmentAccount extends Account {
    private static final double INTEREST_RATE = 0.05; // 5% monthly
    private static final double MINIMUM_DEPOSIT = 500.00;
    private static final double MINIMUM_BALANCE = 500.00; // NEW - minimum balance that must remain

    public InvestmentAccount(String accountNumber, Customer customer, double initialDeposit) {
        super(accountNumber, customer);
        if (initialDeposit >= MINIMUM_DEPOSIT) {
            deposit(initialDeposit);
        } else {
            throw new IllegalArgumentException("Initial deposit must be at least BWP " + MINIMUM_DEPOSIT);
        }
    }

    @Override
    public double calculateInterest() {
        return getBalance() * INTEREST_RATE;
    }

    @Override
    public boolean canWithdraw() {
        return true;
    }

    @Override
    public void withdraw(double amount) {
        if (!canWithdraw()) {
            System.out.println("Withdrawals are not allowed on this account type.");
            return;
        }

        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive.");
            return;
        }

        // Check if withdrawal would leave account below minimum balance
        double balanceAfterWithdrawal = getBalance() - amount;

        if (balanceAfterWithdrawal < MINIMUM_BALANCE) {
            System.out.println("Withdrawal denied: Investment account must maintain minimum balance of BWP " +
                    String.format("%.2f", MINIMUM_BALANCE));
            System.out.println("Current balance: BWP " + String.format("%.2f", getBalance()));
            System.out.println("Maximum you can withdraw: BWP " +
                    String.format("%.2f", getBalance() - MINIMUM_BALANCE));
            return;
        }

        // Call parent withdraw method which will process the withdrawal
        super.withdraw(amount);
    }

    // Helper method to get maximum withdrawable amount
    public double getMaxWithdrawableAmount() {
        double maxAmount = getBalance() - MINIMUM_BALANCE;
        return maxAmount > 0 ? maxAmount : 0;
    }

    public static double getMinimumBalance() {
        return MINIMUM_BALANCE;
    }
}