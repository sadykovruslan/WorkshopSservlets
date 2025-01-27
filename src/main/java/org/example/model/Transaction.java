package org.example.model;

public class Transaction {
    private String name;
    private int sum;

    public Transaction(String name, int sum) {
        this.name = name;
        this.sum = sum;
    }

    public String getName() {
        return name;
    }

    public int getSum() {
        return sum;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "name='" + name + '\'' +
                ", sum=" + sum +
                '}';
    }
}
