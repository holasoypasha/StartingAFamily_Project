package ru.pavlova.createFamily;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;

public class SaveAndLoadJson implements SaveAndLoad {
    private Gson gson;

    public SaveAndLoadJson() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public void save(String filePath, FamilyData familyData) {
        try (FileWriter writer = new FileWriter(filePath)) {
            FamilyData dataToSave = new FamilyData();
            dataToSave.humans = familyData.humans;
            dataToSave.animals = familyData.animals;

            for (Animal animal : familyData.animals) {
                if (animal.getOwner() != null) {
                    animal.setOwnerId(animal.getOwner().getId());
                    animal.setOwner(null);
                }
            }

            gson.toJson(familyData, writer);
            System.out.println("Данные сохранены в файл " + filePath);
        } catch (Exception e) {
            System.out.println("Ошибка при сохранении: " + e.getMessage());
        }
    }

    @Override
    public void load(String filePath, FamilyData familyData) {
        try (FileReader reader = new FileReader(filePath)) {
            FamilyData loadedData = gson.fromJson(reader, FamilyData.class);

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