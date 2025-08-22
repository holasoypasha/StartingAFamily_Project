package ru.pavlova.createFamily;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "animal")
@XmlAccessorType(XmlAccessType.FIELD)
public class Animal implements Creature {
    /**
     * Тип животного
     */
    @XmlElement
    private AnimalType type;
    /**
     * Кличка животного
     */
    @XmlElement
    private String name;
    /**
     * Идентификатор животного
     */
    @XmlElement
    private int id;
    /**
     * Счетчик идентификатора
     */
    private static int increaseId = 1;
    /**
     * Хозяин животного
     */
    @XmlTransient
    private Human owner;
    /**
     * ID владельца для сериализации
     */
    @XmlElement(name = "ownerId")
    private int ownerId = -1;

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

    public Animal() {}

    public void setOwner(Human owner) {
        this.owner = owner;
    }

    public AnimalType getType() {
        return type;
    }

    @XmlTransient
    public Human getOwner() {
        return owner;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public int getId() {
        return id;
    }

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
