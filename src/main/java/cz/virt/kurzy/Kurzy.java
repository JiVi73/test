package cz.virt.kurzy;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;


@XmlRootElement(name = "kurzy")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
public class Kurzy {

    @XmlElement(name = "tabulka")
    private Tabulka tabulka;

}
