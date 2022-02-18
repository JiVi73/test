package cz.virt.kurzy;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.stream.Collectors;

class KurzyTest {

    @Test
    void kurzy__shouldOkReturn() throws JAXBException, MalformedURLException {
        // http://www.cnb.cz/cs/financni_trhy/devizovy_trh/kurzy_devizoveho_trhu/denni_kurz.xml
        Map<String, String> data = getData2("http://www.cnb.cz/cs/financni_trhy/devizovy_trh/kurzy_devizoveho_trhu/denni_kurz.xml");
        Assertions.assertNotNull(data);
        Assertions.assertFalse(data.isEmpty());
    }

    private static Map<String, String> getData2(String url) throws JAXBException, MalformedURLException {

        JAXBContext jc = JAXBContext.newInstance(Kurzy.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        URL urls = new URL(url);
        Kurzy wr = (Kurzy) unmarshaller.unmarshal(urls);

        return wr.getTabulka().getRadek().stream().collect(Collectors.toMap(Radek::getKod, Radek::getKurz));
    }
}
