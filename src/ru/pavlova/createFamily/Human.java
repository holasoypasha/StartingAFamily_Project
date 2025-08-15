package ru.pavlova.createFamily;

import java.util.ArrayList;
import java.util.List;

public class Human implements Creature {
    /**
     * Возраст человека
     */
    private int age;
    /**
     * Имя человека
     */
    private String name;
    /**
     * Идентификатор человека
     */
    private int id;
    /**
     * Счетчик идентификатора
     */
    private static int increaseId = 1;
    /**
     * Список животных
     */
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
