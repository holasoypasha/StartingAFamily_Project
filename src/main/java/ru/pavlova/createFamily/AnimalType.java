package ru.pavlova.createFamily;

/**
 * Перечисление возможных типов животных
 */
public enum AnimalType {
    CAT("Кот"),
    DOG("Собака"),
    BIRD("Птичка"),
    HAMSTER("Хомяк");

    private String animalName;

    AnimalType(String rusName) {
        this.animalName = rusName;
    }

    public String getAnimalName() {
        return animalName;
    }
}
