package by.epam.task02.exception;

public class NoSuchCashDeckException extends Exception {
    public NoSuchCashDeckException() {
        super();
    }

    public NoSuchCashDeckException(String message) {
        super(message);
    }

    public NoSuchCashDeckException(Exception e) {
        super(e);
    }

    public NoSuchCashDeckException(String message, Exception e) {
        super(message, e);
    }
}
