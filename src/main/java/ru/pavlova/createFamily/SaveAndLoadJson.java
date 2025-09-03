package ru.pavlova.createFamily;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.util.ArrayList;

public class SaveAndLoadJson implements SaveAndLoad {
    private ObjectMapper objectMapper;

    public SaveAndLoadJson() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Override
    public void save(String filePath, FamilyData familyData) {
        try {
            FamilyData dataToSave = new FamilyData();
            dataToSave.humans = new ArrayList<>();
            dataToSave.animals = new ArrayList<>();

            for (Human human : familyData.humans) {
                Human humanCopy = new Human(human.getId(), human.getName(), human.getAge());
                dataToSave.humans.add(humanCopy);
            }

            for (Animal animal : familyData.animals) {
                Animal animalCopy = new Animal(animal.getId(), animal.getName(), animal.getTypeOfAnimal());
                if (animal.getOwner() != null) {
                    animalCopy.setOwnerId(animal.getOwner().getId());
                }
                dataToSave.animals.add(animalCopy);
            }

            objectMapper.writeValue(new File(filePath), dataToSave);
            System.out.println("Данные сохранены в файл " + filePath);
        } catch (Exception e) {
            System.out.println("Ошибка при сохранении: " + e.getMessage());
        }
    }

    @Override
    public void load(String filePath, FamilyData familyData) {
        try {
            FamilyData loadedData = objectMapper.readValue(new File(filePath), FamilyData.class);

            familyData.humans.clear();
            familyData.animals.clear();

            familyData.humans.addAll(loadedData.humans);
            familyData.animals.addAll(loadedData.animals);

            System.out.println("Данные загружены из файла " + filePath);
        } catch (Exception e) {
            System.out.println("Ошибка при загрузке: " + e.getMessage());
        }
    }
}