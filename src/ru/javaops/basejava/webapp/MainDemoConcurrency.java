package ru.javaops.basejava.webapp;

public final class MainDemoConcurrency {
    private MainDemoConcurrency() {
    }

    private static final class Account {
        private double balance;

        Account(double balance) {
            this.balance = balance;
        }

        void deposit(double amount) {
            balance += amount;
        }

        void withdraw(double amount) {
            balance -= amount;
        }

        static void transfer(Account from, Account to, double amount) {
            synchronized (from) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {
                }
                synchronized (to) {
                    from.withdraw(amount);
                    to.deposit(amount);
                }
            }
        }
    }

    public static void main(String[] args) {
        Account johnAcc = new Account(700.0);
        Account maryAcc = new Account(300.0);
        new Thread(() -> Account.transfer(johnAcc, maryAcc, 300.0)).start();
        new Thread(() -> Account.transfer(maryAcc, johnAcc, 500.0)).start();
        System.out.println("Give me deadlock!");
    }
}
