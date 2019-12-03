package by.epam.task02.dao.loader;

import by.epam.task02.dao.FileInformationReader;
import by.epam.task02.entity.CashDesk;
import by.epam.task02.exception.DAOException;
import by.epam.task02.util.Impl.CashDeskParser;
import by.epam.task02.util.Parser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class CreatorCashDesk {
    private static final Logger logger = LogManager.getLogger(CreatorCashDesk.class);

    private static CreatorCashDesk instance;
    private static final ReentrantLock lock = new ReentrantLock();

    private CreatorCashDesk() {
    }

    public static CreatorCashDesk getInstance() {
        if (instance == null) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new CreatorCashDesk();
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public List<CashDesk> createCashDeskFromFile(File file) {
        List<CashDesk> cashDeskList = new ArrayList<>();

        List<String> stringList = new ArrayList<>();
        try {
            FileInformationReader fileInformationReader = FileInformationReader.getInstance();
            stringList = fileInformationReader.readAllFile(file);
        } catch (DAOException e) {
            logger.error(e.getMessage(), e);
        }

        Parser<String, String> parser = new CashDeskParser();
        for (String string : stringList) {
            String checked = parser.parse(string);
            if (checked.isEmpty()) {
                continue;
            }

            CashDesk cashDesk = new CashDesk(checked);
            cashDeskList.add(cashDesk);
        }

        return cashDeskList;
    }
}
