package cz.virt.kurzy;

import lombok.Getter;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "kurzy")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
public class Kurzy {

    @XmlElement(name = "tabulka")
    private Tabulka tabulka;

}
