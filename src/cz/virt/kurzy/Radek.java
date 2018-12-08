package cz.virt.kurzy;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Radek {

	@XmlAttribute(name = "zeme")
	private String zeme;
	@XmlAttribute(name = "kurz")
	private String kurz;
	@XmlAttribute(name = "kod")
	private String kod;

	public String getKod() {
		return kod;
	}

	public String getZeme() {
		return zeme;
	}

	public String getKurz() {
		return kurz;
	}

}
