package cz.virt.kurzy;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "kurzy")
@XmlAccessorType(XmlAccessType.FIELD)
public class Kurzy {

	@XmlElement(name = "tabulka")
	private Tabulka tabulka;

	public Tabulka getTabulka() {
		return tabulka;
	}
}
