package ru.pavlova.createFamily;

/**
 * Исключение ограничения возраста
 */
public class AgeLimitException extends Exception {
    public AgeLimitException(String message) {
        super(message);
    }
}
