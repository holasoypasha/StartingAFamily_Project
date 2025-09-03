package ru.pavlova.createFamily;

public class SaveAndLoadManager {
    private final FamilyData familyData;
    private final SaveAndLoad txtHandler;
    private final SaveAndLoad xmlHandler;
    private final SaveAndLoad jsonHandler;

    public SaveAndLoadManager(FamilyData familyData) {
        this.familyData = familyData;
        this.txtHandler = new SaveAndLoadTxt();
        this.xmlHandler = new SaveAndLoadXml();
        this.jsonHandler = new SaveAndLoadJson();
    }

    public void save(String filePath, String format) {
        switch (format.toLowerCase()) {
            case "txt":
                txtHandler.save(filePath, familyData);
                break;
            case "xml":
                xmlHandler.save(filePath, familyData);
                break;
            case "json":
                jsonHandler.save(filePath, familyData);
                break;
            default:
                System.out.println("Неизвестный формат: " + format + ". Попробуйте снова!");
        }
    }

    public void load(String filePath, String format) {
        switch (format.toLowerCase()) {
            case "txt":
                txtHandler.load(filePath, familyData);
                break;
            case "xml":
                xmlHandler.load(filePath, familyData);
                break;
            case "json":
                jsonHandler.load(filePath, familyData);
                break;
            default:
                System.out.println("Неизвестный формат: " + format + ". Попробуйте снова!");
        }
    }

    /**
     * Привязка животного к человеку
     */
    public void restoreRelationships() {
        for (Animal animal : familyData.animals) {
            if (animal.getOwnerId() != null) {
                for (Human human : familyData.humans) {
                    if (human.getId() == animal.getOwnerId()) {
                        human.addAnimal(animal);
                        animal.setOwner(human);
                        break;
                    }
                }
            }
        }
        // очищаем временные ID после восстановления связей
        for (Animal animal : familyData.animals) {
            animal.setOwnerId(null);
        }
    }
}