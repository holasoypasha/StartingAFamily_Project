package ru.pavlova.createFamily;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "familyData")
@XmlAccessorType(XmlAccessType.FIELD)
public class FamilyData {
    @XmlElement(name = "human")
    public List<Human> humans;

    @XmlElement(name = "animal")
    public List<Animal> animals;

    public FamilyData() {}
}
