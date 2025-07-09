package ru.pavlova.createFamily;

public class Animal extends Creature {
    /**
     * Тип животного
     */
    private AnimalType type;
    /**
     * Кличка животного
     */
    private String name;
    /**
     * Хозяин животного
     */
    private Human owner;

    public Animal(String name, AnimalType type) {
        this.name = name;
        this.type = type;
    }

    public void setOwner(Human owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return type.getAnimalName() + " по кличке " + name +
                (owner != null ? " (хозяин: " + owner.getName() + ")" : " (без хозяина)");
    }
}
