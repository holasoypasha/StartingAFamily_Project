package ru.pavlova.createFamily;

import java.util.List;

public interface SaveAndLoad {
    void save(String filePath, FamilyData familyData);
    void load(String filePath, FamilyData familyData);
}
