package cz.virt.kurzy;

import lombok.Getter;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@Getter
public class Tabulka {

    @XmlElement(name = "radek")
    private List<Radek> radek;

}
