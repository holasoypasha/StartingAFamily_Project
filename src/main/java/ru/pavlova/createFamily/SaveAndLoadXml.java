package ru.pavlova.createFamily;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class SaveAndLoadXml implements SaveAndLoad {
    @Override
    public void save(String filePath, FamilyData familyData) {
        try {
            FamilyData dataToSave = new FamilyData();
            dataToSave.humans = familyData.humans;
            dataToSave.animals = familyData.animals;

            for (Animal animal : familyData.animals) {
                if (animal.getOwner() != null) {
                    animal.setOwnerId(animal.getOwner().getId());
                    animal.setOwner(null);
                }
            }

            JAXBContext context = JAXBContext.newInstance(FamilyData.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(familyData, new File(filePath));

            System.out.println("Данные сохранены в файл " + filePath);
        } catch (JAXBException e) {
            System.out.println("Ошибка при сохранении: " + e.getMessage());
        }
    }

    @Override
    public void load(String filePath, FamilyData familyData) {
        try {
            JAXBContext context = JAXBContext.newInstance(FamilyData.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            FamilyData loadedData = (FamilyData) unmarshaller.unmarshal(new File(filePath));

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