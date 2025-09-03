package ru.pavlova.createFamily;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class SaveAndLoadTxt implements SaveAndLoad {
    @Override
    public void save(String filePath, FamilyData familyData) {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            //добавляем людей в файл
            for (Human human : familyData.humans) {
                writer.println(CreatureType.HUMAN.getTypeOfCreature() + "," + human.getId() + "," + human.getName() + "," + human.getAge());
            }

            //добавляем животных в файл
            for (Animal animal : familyData.animals) {
                Integer ownerId;
                if (animal.getOwner() != null) {
                    ownerId = animal.getOwner().getId();
                } else {
                    ownerId = null;
                }

                writer.println(CreatureType.ANIMAL.getTypeOfCreature() + "," + animal.getId() + "," + animal.getTypeOfAnimal() + "," + animal.getName() + "," + ownerId);
            }
            System.out.println("Данные сохранены в файл " + filePath);
        } catch (Exception e) {
            System.out.println("Ошибка при сохранении: " + e.getMessage());
        }
    }

    @Override
    public void load(String filePath, FamilyData familyData) {
        familyData.humans.clear();
        familyData.animals.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    String[] data = line.split(",");

                    //добавляем людей
                    if (data[0].equals(CreatureType.HUMAN.getTypeOfCreature())) {
                        try {
                            int id = Integer.parseInt(data[1]);
                            String name = data[2];
                            int age = Integer.parseInt(data[3]);
                            familyData.humans.add(new Human(id, name, age));
                        } catch (AgeLimitException e) {
                            System.out.println("Ошибка возраста: " + e.getMessage());
                        } catch (NumberFormatException e) {
                            System.out.println("Ошибка формата: " + e.getMessage());
                        }
                    }

                    //добавляем животных
                    else if (data[0].equals(CreatureType.ANIMAL.getTypeOfCreature())) {
                        try {
                            int id = Integer.parseInt(data[1]);
                            AnimalType type = AnimalType.valueOf(data[2]);
                            String name = data[3];
                            Integer ownerId;
                            try {
                                ownerId = Integer.parseInt(data[4]);
                            } catch (NumberFormatException e) {
                                ownerId = null;
                            }
                            Animal newAnimal = new Animal(id, name, type);

                            if (ownerId != null) {
                                newAnimal.setOwnerId(ownerId);
                            }
                            familyData.animals.add(newAnimal);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Ошибка: неизвестный тип. " + e.getMessage());
                            return;
                        } catch (Exception e) {
                            System.out.println("Ошибка: " + e.getMessage());
                            return;
                        }
                    }

                    //неизвестный тип существа
                    else {
                        System.out.println("Обнаружен неизвестный тип существа: " + data[0] + ". Существо пропущено.");
                    }
                } catch (Exception e) {
                    System.out.println("Ошибка: " + e.getMessage());
                    return;
                }
            }
            System.out.println("Данные загружены из файла " + filePath);
        } catch (IOException e) {
            System.out.println("Ошибка при загрузке: " + e.getMessage());
        }
    }
}
