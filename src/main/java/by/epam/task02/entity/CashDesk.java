package by.epam.task02.entity;

import com.google.common.util.concurrent.AtomicDouble;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CashDesk {
    private static final Logger logger = LogManager.getLogger(CashDesk.class);
    private Lock lock = new ReentrantLock();

    private String id = UUID.randomUUID().toString();
    private String name;
    private AtomicDouble revenue = new AtomicDouble(0);
    private ArrayDeque<Client> clientQueue;

    public CashDesk(String name) {
        this.name = name;
        this.clientQueue = new ArrayDeque<>();
    }

    public void addFirst(Client client) {
        lock.lock();
        clientQueue.addFirst(client);
        lock.unlock();
    }

    public void addLast(Client client) {
        lock.lock();
        clientQueue.addLast(client);
        lock.unlock();
    }

    public Client peek() {
        return clientQueue.peekFirst();
    }

    public Client poll() {
        lock.lock();
        Client client = clientQueue.pollFirst();
        lock.unlock();
        return client;
    }

    public void depositAmount(Double amount) {
        this.revenue.addAndGet(amount);
    }

    public Double closeCashDesk() {
        return this.revenue.getAndSet(0.0);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public AtomicDouble getRevenue() {
        return revenue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CashDesk cashDesk = (CashDesk) o;
        return id.equals(cashDesk.id) &&
                name.equals(cashDesk.name) &&
                revenue.equals(cashDesk.revenue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, revenue);
    }

    @Override
    public String toString() {
        return "CashDesk{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", revenue=" + revenue +
                '}';
    }
}
