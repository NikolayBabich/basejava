package ru.javaops.basejava.webapp;

import java.util.logging.Logger;

public final class MainDemoConcurrency {
    private MainDemoConcurrency() {
    }

    private static final class Account {
        private static final Logger LOG = Logger.getLogger(Account.class.getName());

        private final String name;
        private double balance;

        Account(String name, double balance) {
            this.name = name;
            this.balance = balance;
        }

        void deposit(double amount) {
            balance += amount;
        }

        void withdraw(double amount) {
            if (balance >= amount) {
                balance -= amount;
            } else {
                LOG.warning(name + " Insufficient funds!");
            }
        }

        static void transfer(Account from, Account to, double amount) {
//            Account first = from;
//            Account second = to;
//            if (from.hashCode() > to.hashCode()) {
//                first = to;
//                second = from;
//            }

//            synchronized (first) {
            synchronized (from) {
                try {
                    LOG.info(String.format("Transferring %.2f from %s to %s", amount, from, to));
                    LOG.info(from.name + " Processing query to DB...");
                    Thread.sleep(1000);
                    LOG.info(from.name + " DB response acquired");
                } catch (InterruptedException e) {
                    LOG.warning(from.name + " DB is not responding " + e);
                }
//                synchronized (second) {
                synchronized (to) {
                    from.withdraw(amount);
                    to.deposit(amount);
                    LOG.info(String.format("Transfer from %s to %s is completed", from, to));
                }
            }
        }

        @Override
        public String toString() {
            return "Account{" +
                    "name='" + name + '\'' +
                    ", balance=" + balance +
                    '}';
        }
    }

    public static void main(String[] args) {
        Account johnAcc = new Account("John", 700.0);
        Account maryAcc = new Account("Mary", 300.0);

        new Thread(() -> {
            try {
                Thread.sleep((long) (1000 * Math.random()));
            } catch (InterruptedException ignored) {
            }
            Account.transfer(johnAcc, maryAcc, 300.0);
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep((long) (1000 * Math.random()));
            } catch (InterruptedException ignored) {
            }
            Account.transfer(maryAcc, johnAcc, 500.0);
        }).start();
    }
}
