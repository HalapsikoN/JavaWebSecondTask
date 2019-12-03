package by.epam.task02;

import by.epam.task02.dao.loader.CreatorCashDesk;
import by.epam.task02.dao.loader.CreatorClient;
import by.epam.task02.entity.CashDesk;
import by.epam.task02.entity.Client;
import by.epam.task02.entity.Manager;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        File file = new File("data/file.txt");
        CreatorCashDesk creatorCashDesk = CreatorCashDesk.getInstance();
        List<CashDesk> cashDeskList = creatorCashDesk.createCashDeskFromFile(file);
        CreatorClient creatorClient = CreatorClient.getInstance();
        List<Client> clientList = creatorClient.createClientFromFile(file);

        Manager manager = new Manager(cashDeskList, clientList);

        System.out.println("Wait the end of working...");
        manager.work();

        System.out.println("end");
        System.out.println("Result:");
        System.out.println("Client list:");
        System.out.println(clientList);
        System.out.println("Cash desk list:");
        System.out.println(cashDeskList);
    }
}
