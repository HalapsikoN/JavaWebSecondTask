package by.epam.task02.entity;

import java.util.Objects;
import java.util.UUID;

public class Order {
    private String id = UUID.randomUUID().toString();
    private boolean preOrder;
    private double amount;

    public Order(boolean preOrder, double amount) {
        this.preOrder = preOrder;
        this.amount = amount;
    }

    public boolean isPreOrder() {
        return preOrder;
    }

    public double getAmount() {
        return amount;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return preOrder == order.preOrder &&
                Double.compare(order.amount, amount) == 0 &&
                id.equals(order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, preOrder, amount);
    }

    @Override
    public String toString() {
        return "Order{" +
                "preOrder=" + preOrder +
                ", amount=" + amount +
                '}';
    }
}
