package ru.pavlova.createFamily;

import java.io.*;
import java.util.*;

public class FamilyProgram {
    private Scanner scanner = new Scanner(System.in);
    private FamilyData familyData = new FamilyData();
    private SaveAndLoadManager dataManager = new SaveAndLoadManager(familyData);

    public void manager() {
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
                familyData.humans.add(new Human(name, age));
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
        familyData.animals.add(new Animal(name, type));
    }

    /**
     * Привязка животного к человеку
     */
    private void assignment() {
        //проверка наличия животного
        if (familyData.animals.isEmpty()) {
            System.out.println("Нет животных для привязки к человеку!\n");
            return;
        }
        //проверка наличия людей
        if (familyData.humans.isEmpty()) {
            System.out.println("Нет людей для привязки животного!\n");
            return;
        }

        System.out.println("Выберите животное: ");
        listIteration(new ArrayList<Creature>(familyData.animals));
        System.out.println("0. Выход в главное меню");

        int animalChoice = getIntInput();

        //проверка выбора
        if (animalChoice == 0) {
            return;
        }
        if (animalChoice < 1 || animalChoice > familyData.animals.size()) {
            System.out.println("Неверный выбор!\n");
            return;
        }

        //пполучаем выбранное животное
        Animal selectedAnimal = familyData.animals.get(animalChoice - 1);

        System.out.println("Выберите хозяина: ");
        listIteration(new ArrayList<Creature>(familyData.humans));
        System.out.println("0. Выход в главное меню");

        int humanChoise = getIntInput();

        //проверка выбора
        if (humanChoise == 0) {
            return;
        }
        if (humanChoise <1 || humanChoise > familyData.humans.size()) {
            System.out.println("Неверный выбор!\n");
            return;
        }

        //получаем выбранного человека
        Human selectedHuman = familyData.humans.get(humanChoise - 1);
        selectedHuman.addAnimal(selectedAnimal);
        System.out.println("Животное привязано к человеку!\n");
    }

    /**
     * Отображение списка всех людей
     */
    private void allHumans() {
        System.out.println("Список всех людей: ");
        //проверка наличия людей
        if (familyData.humans.isEmpty()) {
            System.out.println("Люди не созданы.\n");
            return;
        }

        //выводим информацию о каждом человеке
        for (int i = 0; i < familyData.humans.size(); i++) {
            Human human = familyData.humans.get(i);
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
        if (familyData.animals.isEmpty()) {
            System.out.println("Животные не созданы.\n");
            return;
        }

        //выводим информацию о каждом животном
        for (int i = 0; i < familyData.animals.size(); i++) {
            System.out.println((i + 1) + ". " + familyData.animals.get(i));
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
     * Сохраненние данных семьи в файл
     */
    private void dataSaving() {
        System.out.println("Выберите формат сохранения:");
        System.out.println("1. TXT");
        System.out.println("2. XML");
        System.out.println("3. JSON");
        System.out.println("0. Отмена");
        int formatChoice = getIntInput();

        if (formatChoice == 0) {
            return;
        }
        String format = "";
        String defaultFileName = "";

        switch (formatChoice) {
            case 1:
                format = "txt";
                defaultFileName = "family_data.txt";
                break;
            case 2:
                format = "xml";
                defaultFileName = "family_data.xml";
                break;
            case 3:
                format = "json";
                defaultFileName = "family_data.json";
                break;
            default:
                System.out.println("Неверный выбор. Попробуйте снова!");
                return;
        }

        //пользовательно вводит путь
        scanner.nextLine();
        System.out.println("Введите путь для сохранения, включая название файла (или нажмите Enter, чтобы файл с названием family_data сохранился в папку проекта): ");
        String filePath = scanner.nextLine().trim();

        //если пользователь не ввел путь
        if (filePath.isBlank()) {
            filePath = defaultFileName;
        }
        //добавляем расширение к файлу, если его нет
        if (!filePath.toLowerCase().endsWith("." + format)) {
            filePath += "." + format;
        }

        try {
            dataManager.save(filePath, format);
        } catch (Exception e) {
            System.out.println("Ошибка сохранения: " + e.getMessage());
        }
    }

    /**
     * Загрузка данных семьи их текстового файла
     */
    private void loadingData() {
        System.out.println("Выберите формат загрузки:");
        System.out.println("1. TXT");
        System.out.println("2. XML");
        System.out.println("3. JSON");
        System.out.println("0. Отмена");
        int formatChoice = getIntInput();

        if (formatChoice == 0) {
            return;
        }

        String format = "";
        String defaultFileName = "";

        switch (formatChoice) {
            case 1:
                format = "txt";
                defaultFileName = "family_data.txt";
                break;
            case 2:
                format = "xml";
                defaultFileName = "family_data.xml";
                break;
            case 3:
                format = "json";
                defaultFileName = "family_data.json";
                break;
            default:
                System.out.println("Неверный выбор. Попробуйте снова!");
                return;
        }

        scanner.nextLine();
        System.out.println("Введите путь к файлу (или нажмите Enter для выбора файла из папки проекта, если он существует): ");
        String filePath = scanner.nextLine().trim();

        // если пользователь не ввел путь
        if (filePath.isEmpty()) {
            filePath = defaultFileName;
        }

        if (!filePath.toLowerCase().endsWith("." + format)) {
            filePath += "." + format;
        }

        // проверяем существование файла
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("Файл не найден: " + file.getAbsolutePath());
            return;
        }

        try {
            dataManager.load(filePath, format);
            dataManager.restoreRelationships();
        } catch (Exception e) {
            System.out.println("Ошибка загрузки: " + e.getMessage());
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