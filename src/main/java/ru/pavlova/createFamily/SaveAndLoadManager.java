package ru.pavlova.createFamily;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.xml.bind.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.*;
import java.util.List;

public class SaveAndLoadManager {
    private final List<Human> humans;
    private final List<Animal> animals;
    private Gson gson;

    public SaveAndLoadManager(List<Human> humans, List<Animal> animals) {
        this.humans = humans;
        this.animals = animals;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    void save(String filePath, String format) {
        switch (format.toLowerCase()) {
            case "txt":
                saveTxt(filePath);
                break;
            case "xml":
                saveXml(filePath);
                break;
            case "json":
                saveJson(filePath);
                break;
            default:
                System.out.println("Неизвестный формат: " + format + ". Попробуйте снова!");
        }
    }

    void load(String filePath, String format) {
        switch (format.toLowerCase()) {
            case "txt":
                loadTxt(filePath);
                break;
            case "xml":
                loadXml(filePath);
                break;
            case "json":
                loadJson(filePath);
                break;
            default:
                System.out.println("Неизвестный формат: " + format + ". Попробуйте снова!");
        }
    }

    /**
     * Сохранение в формат TXT
     * @param filePath путь к файлу
     */
    private void saveTxt(String filePath) {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            //добавляем людей в файл
            for (Human human : humans) {
                writer.println("Человек," + human.getId() + "," + human.getName() + "," + human.getAge());
            }

            //добавляем животных в файл
            for (Animal animal : animals) {
                int ownerId;
                if (animal.getOwner() != null) {
                    ownerId = animal.getOwner().getId();
                } else {
                    ownerId = -1;
                }

                writer.println("Животное," + animal.getId() + "," + animal.getType() + "," + animal.getName() + "," + ownerId);
            }
            System.out.println("Данные сохранены в файл " + filePath);
        } catch (Exception e) {
            System.out.println("Ошибка при сохранении: " + e.getMessage());
        }
    }

    /**
     * Сохранение в формат XML
     * @param filePath путь к файлу
     */
    private void saveXml(String filePath) {
        try {
            FamilyData familyData = new FamilyData();
            familyData.humans = humans;
            familyData.animals = animals;

            JAXBContext context = JAXBContext.newInstance(FamilyData.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(familyData, new File(filePath));

            System.out.println("Данные сохранены в файл " + filePath);
        } catch (JAXBException e) {
            System.out.println("Ошибка при сохранении: " + e.getMessage());
        }
    }

    /**
     * Сохранение в формат JSON
     * @param filePath путь к файлу
     */
    private void saveJson(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            FamilyData familyData = new FamilyData();
            familyData.humans = humans;
            familyData.animals = animals;
            gson.toJson(familyData, writer);

            System.out.println("Данные сохранены в файл " + filePath);
        } catch (Exception e) {
            System.out.println("Ошибка при сохранении: " + e.getMessage());
        }
    }

    /**
     * Загрузка из файла TXT
     * @param filePath путь к файлу
     */
    private void loadTxt(String filePath) {
        humans.clear();
        animals.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    String[] data = line.split(",");

                    //добавляем людей
                    if (data[0].equals("Человек")) {
                        try {
                            int id = Integer.parseInt(data[1]);
                            String name = data[2];
                            int age = Integer.parseInt(data[3]);
                            humans.add(new Human(id, name, age));
                        } catch (AgeLimitException e) {
                            System.out.println("Ошибка возраста: " + e.getMessage());
                            return;
                        } catch (NumberFormatException e) {
                            System.out.println("Ошибка формата: " + e.getMessage());
                            return;
                        } catch (Exception e) {
                            System.out.println("Ошибка: " + e.getMessage());
                            return;
                        }
                    }

                    //добавляем животных
                    else if (data[0].equals("Животное")) {
                        try {
                            int id = Integer.parseInt(data[1]);
                            AnimalType type = AnimalType.valueOf(data[2]);
                            String name = data[3];
                            int ownerId = Integer.parseInt(data[4]);
                            Animal newAnimal = new Animal(id, name, type);

                            if (ownerId > -1) {
                                for (Human human : humans) {
                                    if (human.getId() == ownerId) {
                                        human.addAnimal(newAnimal);
                                        newAnimal.setOwner(human);
                                        break;
                                    }
                                }
                            }
                            animals.add(newAnimal);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Ошибка: неизвестный тип. " + e.getMessage());
                            return;
                        } catch (Exception e) {
                            System.out.println("Ошибка: " + e.getMessage());
                            return;
                        }
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

    /**
     * Загрузка из файла XML
     * @param filePath путь к файлу
     */
    private void loadXml(String filePath) {
        try {
            JAXBContext context = JAXBContext.newInstance(FamilyData.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            FamilyData familyData = (FamilyData) unmarshaller.unmarshal(new File(filePath));

            humans.clear();
            animals.clear();
            humans.addAll(familyData.humans);
            animals.addAll(familyData.animals);
            restoreRelationships();

            System.out.println("Данные загружены из файла " + filePath);
        } catch (Exception e) {
            System.out.println("Ошибка при загрузке: " + e.getMessage());
        }
    }

    /**
     * Загрузка из файла JSON
     * @param filePath путь к файлу
     */
    private void loadJson(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            FamilyData familyData = gson.fromJson(reader, FamilyData.class);

            humans.clear();
            animals.clear();
            humans.addAll(familyData.humans);
            animals.addAll(familyData.animals);
            restoreRelationships();

            System.out.println("Данные загружены из файла " + filePath);
        } catch (Exception e) {
            System.out.println("Ошибка при загрузке: " + e.getMessage());
        }
    }

    /**
     * Привязка животного к человеку
     */
    private void restoreRelationships() {
        for (Animal animal : animals) {
            if (animal.getOwner() != null) {
                for (Human human : humans) {
                    if (human.getId() == animal.getOwner().getId()) {
                        human.addAnimal(animal);
                        animal.setOwner(human);
                        break;
                    }
                }
            }
        }
    }

    @XmlRootElement
    private class FamilyData {
        @XmlElement
        public List<Human> humans;

        @XmlElement
        public List<Animal> animals;

        public FamilyData () {}
    }
}
