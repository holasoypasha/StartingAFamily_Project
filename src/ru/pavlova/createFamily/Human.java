package ru.pavlova.createFamily;

import java.util.ArrayList;
import java.util.List;

public class Human {
    /**
     * Возраст человека
     */
    private int age;
    /**
     * Имя человека
     */
    private String name;
    /**
     * Список животных
     */
    private List<Animal> animals;

    public Human(String name, int age) {
        this.name = name;
        this.age = age;
        this.animals = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public void addAnimal(Animal animal) {
        animals.add(animal);
        animal.setOwner(this);
    }

    @Override
    public String toString() {
        return name + " (Возраст: " + age + ")";
    }
}
