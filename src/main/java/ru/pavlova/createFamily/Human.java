package ru.pavlova.createFamily;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "human")
@XmlAccessorType(XmlAccessType.FIELD)
public class Human implements Creature {
    /**
     * Возраст человека
     */
    @XmlElement
    private int age;
    /**
     * Имя человека
     */
    @XmlElement
    private String name;
    /**
     * Идентификатор человека
     */
    @XmlElement
    private int id;
    /**
     * Счетчик идентификатора
     */
    private static int increaseId = 1;
    /**
     * Список животных
     */
    @XmlTransient
    private List<Creature> animals;

    /**
     * Обычное создание
     * @param name имя человека
     * @param age возраст человека
     * @throws AgeLimitException исключение ошибки возраста
     */
    public Human(String name, int age) throws AgeLimitException {
        //если возраст не входит в диапазон от 0 до 120, то выбросится исключение
        if (age < 0 || age > 120) {
            throw new AgeLimitException("Возраст может быть от 0 до 120!");
        }
        this.id = increaseId++;
        this.name = name;
        this.age = age;
        this.animals = new ArrayList<>();
    }

    /**
     * Создание для загрузки из файла
     * @param id идентификатор человека
     * @param name имя человека
     * @param age возраст человека
     * @throws AgeLimitException исключение ошибки возраста
     */
    public Human(int id, String name, int age) throws AgeLimitException {
        //если возраст не входит в диапазон от 0 до 120, то выбросится исключение
        if (age < 0 || age > 120) {
            throw new AgeLimitException("Возраст может быть от 0 до 120!");
        }
        this.id = id;
        this.name = name;
        this.age = age;
        this.animals = new ArrayList<>();
        if (id >= increaseId) {
            increaseId = id + 1;
        }
    }

    public Human() {
        this.animals = new ArrayList<>();
    }

    @XmlTransient
    public List<Creature> getAnimals() {
        return animals;
    }

    public void addAnimal(Animal animal) {
        animals.add(animal);
        animal.setOwner(this);
    }

    public int getAge() {
        return age;
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
        return name + " (Возраст: " + age + ", ID: " + id + ")";
    }
}
