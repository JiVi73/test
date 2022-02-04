package cz.virt.kurzy;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Tabulka {

    @XmlElement(name = "radek")
    private List<Radek> radek;

    public List<Radek> getRadek() {
        return radek;
    }
}
