package cz.virt.kurzy;

import lombok.Getter;

import javax.xml.bind.annotation.*;

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
