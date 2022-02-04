package cz.virt.crypto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TripleDESTest {

    private static final String keyValue = "2315218C9111AD402315218C9111AD41";
    private static final String sData = "Hello world!";


    @Test
    void testEncryptDecrypt__shouldOk() {
        String response = testEncrypt(sData);
        String result = testDecrypt(response);
        Assertions.assertEquals(sData, result);
    }

    private static String testEncrypt(String inputData) {
        String result = null;
        try {
            byte[] data = TripleDES.encrypt(TripleDES.getVDSP(inputData), keyValue);
            result = TripleDES.toHexString(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String testDecrypt(String inputData) {
        String result = null;
        try {
            result = TripleDES.getFromVDSP(TripleDES.decrypt(TripleDES.toByteArray(inputData), keyValue));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
