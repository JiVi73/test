package cz.virt.kurzy;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class KurzyMain {

	private static void getData2(String url) throws JAXBException, MalformedURLException {
		JAXBContext jc = JAXBContext.newInstance(Kurzy.class);

		Unmarshaller unmarshaller = jc.createUnmarshaller();
		// File xml = new File("src/forum21797000/input.xml");
		URL urls = new URL(url);
		Kurzy wr = (Kurzy) unmarshaller.unmarshal(urls);

		for (Radek radek : wr.getTabulka().getRadek()) {
			System.out.println(radek.getKod() + " " + radek.getKurz());
		}

	}

	public static void main(String[] args) throws JAXBException, MalformedURLException {
		// TODO Auto-generated method stub
		getData2("http://www.cnb.cz/cs/financni_trhy/devizovy_trh/kurzy_devizoveho_trhu/denni_kurz.xml");
		// KurzyPoradi kurzy = new KurzyPoradi();
	}

	// http://www.cnb.cz/cs/financni_trhy/devizovy_trh/kurzy_devizoveho_trhu/denni_kurz.xml

}
