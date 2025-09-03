package ru.pavlova.createFamily;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "animal")
@XmlAccessorType(XmlAccessType.FIELD)
public class Animal implements Creature {
    /**
     * Счетчик идентификатора
     */
    private static int increaseId = 1;
    /**
     * Тип животного
     */
    @XmlElement
    private AnimalType typeOfAnimal;
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
     * Хозяин животного
     */
    @XmlTransient
    private Human owner;
    /**
     * ID владельца для сериализации
     */
    @XmlElement(name = "ownerId")
    private Integer ownerId = null;

    /**
     * Обычное создание
     * @param name кличка животного
     * @param typeOfAnimal тип животного
     */
    public Animal(String name, AnimalType typeOfAnimal) {
        this.id = increaseId++;
        this.name = name;
        this.typeOfAnimal = typeOfAnimal;
    }

    /**
     * СОздание для загрузки из файла
     * @param id идентификатор животного
     * @param name кличка животного
     * @param typeOfAnimal тип животного
     */
    public Animal(int id, String name, AnimalType typeOfAnimal) {
        this.id = id;
        this.name = name;
        this.typeOfAnimal = typeOfAnimal;
        if (id >= increaseId) {
            increaseId = id + 1;
        }
    }

    public Animal() {}

    public void setOwner(Human owner) {
        this.owner = owner;
        if (owner != null) {
            this.ownerId = owner.getId();
        } else {
            this.ownerId = null;
        }
    }

    public AnimalType getTypeOfAnimal() {
        return typeOfAnimal;
    }

    @XmlTransient
    public Human getOwner() {
        return owner;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
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
        return typeOfAnimal.getAnimalName() + " по кличке " + name +
                (owner != null ? " (хозяин: " + owner.getName() + ")" : " (без хозяина)");
    }
}
