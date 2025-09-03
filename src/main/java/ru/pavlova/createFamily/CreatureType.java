package ru.pavlova.createFamily;

public enum CreatureType {
    HUMAN("Человек"),
    ANIMAL("Животное");

    private String typeOfCreature;

    CreatureType(String typeOfCreature) {
        this.typeOfCreature = typeOfCreature;
    }

    public String getTypeOfCreature() {
        return typeOfCreature;
    }
}
