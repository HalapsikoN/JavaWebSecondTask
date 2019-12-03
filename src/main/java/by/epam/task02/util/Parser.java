package by.epam.task02.util;

public interface Parser<R, E> {
    R parse(E string);
}
