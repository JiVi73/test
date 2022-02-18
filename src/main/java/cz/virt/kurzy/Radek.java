package cz.virt.kurzy;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import lombok.Getter;



@XmlAccessorType(XmlAccessType.FIELD)
@Getter
public class Radek {

    @XmlAttribute(name = "zeme")
    private String zeme;
    @XmlAttribute(name = "kurz")
    private String kurz;
    @XmlAttribute(name = "kod")
    private String kod;


}
