package by.epam.task02.entity;

import com.google.common.util.concurrent.AtomicDouble;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    public CashDesk(String name) {
        lock.lock();
        this.name = name;
        lock.unlock();
    }

    public void depositAmount(Double amount) {
        lock.lock();
        this.revenue.addAndGet(amount);
        lock.unlock();
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

    public Lock getLock() {
        return lock;
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
