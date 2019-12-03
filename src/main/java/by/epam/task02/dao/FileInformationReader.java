package by.epam.task02.dao;

import by.epam.task02.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class FileInformationReader {

    private final static Logger logger = LogManager.getLogger(FileInformationReader.class);

    private static FileInformationReader instance;
    private static final ReentrantLock lock = new ReentrantLock();

    private FileInformationReader() {
    }

    public static FileInformationReader getInstance() {
        if (instance == null) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new FileInformationReader();
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public String getLine(File file, int number) throws DAOException {
        String result = "";
        try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
            while (number-- > 0) {
                result = fileReader.readLine();
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new DAOException(e);
        }
        return result;
    }

    public String getFirstLine(File file) throws DAOException {
        String result = "";
        try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
            result = fileReader.readLine();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new DAOException(e);
        }
        return result;
    }

    public List<String> getAllLinesWithString(File file, String searchString) throws DAOException {
        List<String> list = new ArrayList<>();
        try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
            while (fileReader.ready()) {
                String string = fileReader.readLine();
                if (string.contains(searchString)) {
                    list.add(string);
                } else {
                    logger.info("String (" + string + ") doesn't contains string (" + searchString + ")");
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new DAOException(e);
        }
        return list;
    }

    public List<String> readAllFile(File file) throws DAOException {
        List<String> list = new ArrayList<>();
        try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
            while (fileReader.ready()) {
                list.add(fileReader.readLine());
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new DAOException(e);
        }
        return list;
    }
}
