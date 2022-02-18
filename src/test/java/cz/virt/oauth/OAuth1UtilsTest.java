package cz.virt.oauth;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.net.ssl.X509ExtendedKeyManager;
import java.security.PrivateKey;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OAuth1UtilsTest {

    final PrivateKey privateKey = mock(PrivateKey.class);

    @Test
    void testBuildAuthHeaderString__shouldOk() throws Exception {

        X509ExtendedKeyManager mock = mock(X509ExtendedKeyManager.class);
        when(mock.getPrivateKey(any(String.class))).thenReturn(privateKey);

        OAuth1Utils oauth = new OAuth1Utils();
        oauth.setConsumerKey("test");
        oauth.setBody("telo");
        oauth.setPrivateKey(privateKey);
        /**
         String result = oauth.buildAuthHeaderString("https://www.test.cz/", "GET");
         System.out.println(result);
         */
        Assertions.assertTrue(true); // zatim nic
    }
}
