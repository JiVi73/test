package cz.virt.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class StringsTest {

    private static final String WORDS_SEPARATOR = " ";


    @Test
    void testStrings__shouldOk() throws Exception {

        List<String> iStrings = new ArrayList<>();
        iStrings.add("ahoj nazdar neco");
        iStrings.add("nazdar nekdo nekym");

        List<String> oStrings = getRevertStrings(iStrings);
        Assertions.assertEquals(2, oStrings.size());
        Assertions.assertEquals("joha radzan ocen", oStrings.get(0));
        Assertions.assertEquals("radzan odken myken", oStrings.get(1));

    }

    private String revertString(String data) throws Exception {
        if (data.length() > 20) {
            throw new Exception("max length: 20");
        }
        return new StringBuilder(data).reverse().toString();
    }

    private String getLine(String[] data) throws Exception {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            if (i > 0) {
                result.append(WORDS_SEPARATOR);
            }
            result.append(revertString(data[i]));
        }
        return result.toString();
    }

    private List<String> getRevertStrings(List<String> strings) throws Exception {
        List<String> result = new ArrayList<>();
        for (String data : strings) {
            String[] string = data.split(WORDS_SEPARATOR);
            if (string.length > 100) {
                throw new Exception("max words: 100");
            }
            result.add(getLine(string));
        }
        return result;
    }

    private static Integer getCount(String data) throws Exception {
        int result = 0;
        try {
            result = Integer.parseInt(data);
        } catch (Exception e) {
            throw new Exception("input is not number");
        }
        if (result < 0 || result > 100) {
            throw new Exception("input must be in the range: 0-100");
        }
        return result;
    }


}
