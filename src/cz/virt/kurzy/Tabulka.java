package cz.virt.kurzy;

import java.util.List;
import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Tabulka {

	@XmlElement(name = "radek")
	private List<Radek> radek;

	public List<Radek> getRadek() {
		return radek;
	}
}
