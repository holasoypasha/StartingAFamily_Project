package ru.pavlova.createFamily;

import java.io.*;
import java.util.*;

public class FamilyProgram {
    private Scanner scanner = new Scanner(System.in);
    /**
     * Список всех добавленных людей
     */
    private List<Human> humans = new ArrayList<>();
    /**
     * Список всех созданный животных
     */
    private List<Animal> animals = new ArrayList<>();

//    private Map<Integer, Human> humans = new HashMap<>();
//    private Map<Integer, Animal> animals = new HashMap<>();

    void manager() {
        boolean running = true;

        while (running) {
            printMenu();

            int choice = getIntInput();

            switch (choice) {
                case 1:
                    createHuman();
                    break;
                case 2:
                    createAnimal();
                    break;
                case 3:
                    assignment();
                    break;
                case 4:
                    allHumans();
                    break;
                case 5:
                    allAnimals();
                    break;
                case 6:
                    allHumansAndAnimals();
                    break;
                case 7:
                    dataSaving();
                    break;
                case 8:
                    loadingData();
                    break;
                case 0:
                    running = false;
                    System.out.println("Завершение программы");
                    break;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова!");
            }
        }
    }

    /**
     * Вывод главного меню
     */
    private void printMenu() {
        System.out.println("1. Создать человека\n" +
                "2. Создать животное\n" +
                "3. Привязать животное к человеку\n" +
                "4. Показать людей\n" +
                "5. Показать животных\n" +
                "6. Показать людей и животных\n" +
                "7. Сохранить данные\n" +
                "8. Загрузить данные\n" +
                "0. Выход\n" +
                "Выберите действие:");
    }

    /**
     * Получение числа, выбранного пользователем
     */
    private int getIntInput() {
        // Проверка, что введено именно число
        while (!scanner.hasNextInt()) {
            System.out.println("Пожалуйста, введите число!");
            scanner.next();
        }
        return scanner.nextInt();
    }

    /**
     * Создание человека
     */
    private void createHuman() {
        System.out.println("\nСоздание человека");
        scanner.nextLine();

        System.out.println("Введите имя человека: ");
        String name = scanner.nextLine();

        while (true) {
            try {
                System.out.println("Введите возраст человека: ");
                int age = scanner.nextInt();

                //создаем нового Human, если возраст не подходит, то выбросит исключение
                humans.add(new Human(name, age));
                break;
            } catch (AgeLimitException e) {
                //обработка исключения при неверном возрасте
                System.out.println("Ошибка:" + e.getMessage());
                scanner.nextLine();
            } catch (InputMismatchException e) {
                //обработка исключения при неправильном формате входных данных
                System.out.println("Ошибка: Введите целое число!");
                scanner.nextLine();
            }
        }
    }

    /**
     * Создание животного
     */
    private void createAnimal() {
        System.out.println("\nСоздание животного");
        System.out.println("Выберите тип животного:");

        //возвращает массив всех типов животных
        AnimalType[] types = AnimalType.values();

        for (int i = 0; i < types.length; i++) {
            System.out.println((i + 1) + ". " + types[i].getAnimalName());
        }
        System.out.println("0. Выход в главное меню");

        int typeChoice = getIntInput();

        //проверка выбора
        if (typeChoice == 0) {
            return;
        }
        if (typeChoice < 1 || typeChoice > types.length) {
            System.out.println("Неверный выбор!");
            return;
        }

        scanner.nextLine();
        System.out.println("Введите кличку животного: ");
        String name = scanner.nextLine();

        AnimalType type = types[typeChoice - 1];
        animals.add(new Animal(name, type));
    }

    /**
     * Привязка животного к человеку
     */
    private void assignment() {
        //проверка наличия животного
        if (animals.isEmpty()) {
            System.out.println("Нет животных для привязки к человеку!\n");
            return;
        }
        //проверка наличия людей
        if (humans.isEmpty()) {
            System.out.println("Нет людей для привязки животного!\n");
            return;
        }

        System.out.println("Выберите животное: ");
        listIteration(new ArrayList<Creature>(animals));
        System.out.println("0. Выход в главное меню");

        int animalChoice = getIntInput();

        //проверка выбора
        if (animalChoice == 0) {
            return;
        }
        if (animalChoice < 1 || animalChoice > animals.size()) {
            System.out.println("Неверный выбор!\n");
            return;
        }

        //пполучаем выбранное животное
        Animal selectedAnimal = animals.get(animalChoice - 1);

        System.out.println("Выберите хозяина: ");
        listIteration(new ArrayList<Creature>(humans));
        System.out.println("0. Выход в главное меню");

        int humanChoise = getIntInput();

        //проверка выбора
        if (humanChoise == 0) {
            return;
        }
        if (humanChoise <1 || humanChoise > humans.size()) {
            System.out.println("Неверный выбор!\n");
            return;
        }

        //получаем выбранного человека
        Human selectedHuman = humans.get(humanChoise - 1);
        selectedHuman.addAnimal(selectedAnimal);
        System.out.println("Животное привязано к человеку!\n");
    }

    /**
     * Отображение списка всех людей
     */
    private void allHumans() {
        System.out.println("Список всех людей: ");
        //проверка наличия людей
        if (humans.isEmpty()) {
            System.out.println("Люди не созданы.\n");
            return;
        }

        //выводим информацию о каждом человеке
        for (int i = 0; i < humans.size(); i++) {
            Human human = humans.get(i);
            System.out.println((i + 1) + ". " + human);

            //проверка, есть ли животное у человека
            if (!human.getAnimals().isEmpty()) {
                System.out.println("Животные: ");
                for (Creature animal : human.getAnimals()) {
                    System.out.println(" - " + animal);
                }
            }
        }
        System.out.println("\n");
    }

    /**
     * Отображение списка всех животных
     */
    private void allAnimals() {
        System.out.println("Список всех животных: ");
        //проверка наличия животных
        if (animals.isEmpty()) {
            System.out.println("Животные не созданы.\n");
            return;
        }

        //выводим информацию о каждом животном
        for (int i = 0; i < animals.size(); i++) {
            System.out.println((i + 1) + ". " + animals.get(i));
        }
        System.out.println("\n");
    }

    /**
     * Отображение списка всех людей и живыотных
     */
    private void allHumansAndAnimals() {
        allHumans();
        allAnimals();
    }

    /**
     * Сохраненние данных семьи в текстовый файл
     */
    private void dataSaving() {
        try (PrintWriter writer = new PrintWriter("family_data.txt")) {
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
            System.out.println("Данные сохранены в файл 'family_data.txt'\n");
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении: " + e.getMessage());
        }
    }

    /**
     * Загрузка данных семьи их текстового файла
     */
    private void loadingData() {
        //очищаем списки перед загрузкой данных из файла
        humans.clear();
        animals.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader("family_data.txt"))) {
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
            System.out.println("Данные загружены из файла 'family_data.txt'\n");
        } catch (IOException e) {
            System.out.println("Ошибка при загрузке: " + e.getMessage());
        }
    }

    /**
     * Выводит пронумерованный список
     * @param list список объектов для вывода
     */
    private void listIteration(List<Creature> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i));
        }
    }

}