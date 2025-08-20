package ru.pavlova.createFamily;

import javax.xml.bind.annotation.XmlElement;

public class Animal implements Creature {
    /**
     * Тип животного
     */
    private AnimalType type;
    /**
     * Кличка животного
     */
    private String name;
    /**
     * Идентификатор животного
     */
    private int id;
    /**
     * Счетчик идентификатора
     */
    private static int increaseId = 1;
    /**
     * Хозяин животного
     */
    private Human owner;

    /**
     * Обычное создание
     * @param name кличка животного
     * @param type тип животного
     */
    public Animal(String name, AnimalType type) {
        this.id = increaseId++;
        this.name = name;
        this.type = type;
    }

    /**
     * СОздание для загрузки из файла
     * @param id идентификатор животного
     * @param name кличка животного
     * @param type тип животного
     */
    public Animal(int id, String name, AnimalType type) {
        this.id = id;
        this.name = name;
        this.type = type;
        if (id >= increaseId) {
            increaseId = id + 1;
        }
    }

    public void setOwner(Human owner) {
        this.owner = owner;
    }

    @XmlElement
    public AnimalType getType() {
        return type;
    }

    @XmlElement
    public Human getOwner() {
        return owner;
    }

    @XmlElement
    @Override
    public int getId() {
        return id;
    }

    @XmlElement
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return type.getAnimalName() + " по кличке " + name +
                (owner != null ? " (хозяин: " + owner.getName() + ")" : " (без хозяина)");
    }
}
