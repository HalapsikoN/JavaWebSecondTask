package by.epam.task02.entity;

import by.epam.task02.exception.NoSuchCashDeckException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Client implements Runnable {
    private static final Logger logger = LogManager.getLogger(Client.class);

    private String id = UUID.randomUUID().toString();
    private String cashDeskName;
    private CashDesk cashDesk;
    private double amountInPurse;
    private Order order;

    public Client(double amountInPurse, Order order, String cashDeskName) {
        this.amountInPurse = amountInPurse;
        this.order = order;
        this.cashDeskName = cashDeskName;
    }

    public Client(double amountInPurse, boolean preOrder, double amountOfOrder, String cashDeskName) {
        this.amountInPurse = amountInPurse;
        this.order = new Order(preOrder, amountOfOrder);
        this.cashDeskName = cashDeskName;
    }

    public void findCashDesk(List<CashDesk> cashDeskList) throws NoSuchCashDeckException {
        for (CashDesk cashDesk : cashDeskList) {
            if (cashDeskName.equals(cashDesk.getName())) {
                this.cashDesk = cashDesk;
                return;
            }
        }
        throw new NoSuchCashDeckException("for (" + id + ") client");
    }

    @Override
    public void run() {
        if (cashDesk == null) {
            return;
        }

        try {
            logger.info("Client (" + id + ") make an order at cash (" + cashDesk.getName() + ")...");
            Random random = new Random();
            TimeUnit.SECONDS.sleep(random.nextInt(3));

            logger.info("Client (" + id + ") pays off...");
            TimeUnit.SECONDS.sleep(random.nextInt(3));
        } catch (InterruptedException e) {
            logger.error("IN (" + id + ")", e);
        }
        if (order.getAmount() > amountInPurse) {
            logger.warn("Client (" + id + ") has not enough money.");
        } else {
            logger.info("Client (" + id + ") has successfully made an order.");
            amountInPurse -= order.getAmount();
            cashDesk.depositAmount(order.getAmount());
        }
    }

    public String getId() {
        return id;
    }

    public String getCashDeskName() {
        return cashDeskName;
    }

    public double getAmountInPurse() {
        return amountInPurse;
    }

    public Order getOrder() {
        return order;
    }

    public CashDesk getCashDesk() {
        return cashDesk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Double.compare(client.amountInPurse, amountInPurse) == 0 &&
                id.equals(client.id) &&
                cashDeskName.equals(client.cashDeskName) &&
                cashDesk.equals(client.cashDesk) &&
                order.equals(client.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cashDeskName, cashDesk, amountInPurse, order);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id='" + id + '\'' +
                ", cashDeskName='" + cashDeskName + '\'' +
                ", cashDesk=" + cashDesk +
                ", amountInPurse=" + amountInPurse +
                ", order=" + order +
                '}';
    }
}
