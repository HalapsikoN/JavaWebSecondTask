package by.epam.task02.entity;

import by.epam.task02.exception.NoSuchCashDeckException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Manager {
    private final static Logger logger = LogManager.getLogger(Manager.class);

    private List<CashDesk> cashDeskList;
    private List<Client> clientList;

    public Manager(List<CashDesk> cashDeskList, List<Client> clientList) {
        this.cashDeskList = cashDeskList;
        this.clientList = clientList;
    }

    public void work() {
        for (Client client : clientList) {
            try {
                client.findCashDesk(cashDeskList);
            } catch (NoSuchCashDeckException e) {
                logger.warn(e.getMessage() + " problem is " + e.getClass().getSimpleName());
            }
        }

        for (Client client : clientList) {
            CashDesk clientCashDesk = client.getCashDesk();
            if (clientCashDesk == null) {
                continue;
            }
            if (client.getOrder().isPreOrder()) {
                clientCashDesk.addFirst(client);
            } else {
                clientCashDesk.addLast(client);
            }
        }

        Map<String, ExecutorService> mapThreads = new HashMap<>();

        for (CashDesk cashDesk : cashDeskList) {
            mapThreads.put(cashDesk.getId(), Executors.newSingleThreadExecutor());
        }

        for (CashDesk cashDesk : cashDeskList) {
            ExecutorService ex = mapThreads.get(cashDesk.getId());
            while (cashDesk.peek() != null) {
                Client client = cashDesk.poll();
                ex.execute(client);
            }
        }

        for (CashDesk cashDesk : cashDeskList) {
            ExecutorService ex = mapThreads.get(cashDesk.getId());
            ex.shutdown();
            try {
                ex.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }


}
