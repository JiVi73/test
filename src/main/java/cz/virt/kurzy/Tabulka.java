package cz.virt.kurzy;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Getter;

import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@Getter
public class Tabulka {

    @XmlElement(name = "radek")
    private List<Radek> radek;

}
