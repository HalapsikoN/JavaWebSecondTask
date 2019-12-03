package by.epam.task02.dao.loader;

import by.epam.task02.dao.FileInformationReader;
import by.epam.task02.entity.Client;
import by.epam.task02.exception.DAOException;
import by.epam.task02.util.Impl.ClientParser;
import by.epam.task02.util.Parser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class CreatorClient {
    private static final Logger logger = LogManager.getLogger(CreatorClient.class);

    private static CreatorClient instance;
    private static final ReentrantLock lock = new ReentrantLock();

    private CreatorClient() {
    }

    public static CreatorClient getInstance() {
        if (instance == null) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new CreatorClient();
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public List<Client> createClientFromFile(File file) {
        List<Client> cashDeskList = new ArrayList<>();

        List<String> stringList = new ArrayList<>();
        try {
            FileInformationReader fileInformationReader = FileInformationReader.getInstance();
            stringList = fileInformationReader.readAllFile(file);
        } catch (DAOException e) {
            logger.error(e.getMessage(), e);
        }

        Parser<List<String>, String> parser = new ClientParser();
        for (String string : stringList) {
            List<String> resultList = parser.parse(string);
            if (resultList.isEmpty()) {
                continue;
            }

            double amountInPurse = Double.valueOf(resultList.get(0));
            boolean preOrder = Boolean.valueOf(resultList.get(1));
            double amountOfOrder = Double.valueOf(resultList.get(2));
            String nameOfCash = resultList.get(3);

            Client client = new Client(amountInPurse, preOrder, amountOfOrder, nameOfCash);
            cashDeskList.add(client);
        }

        return cashDeskList;
    }
}
