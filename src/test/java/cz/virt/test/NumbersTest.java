package cz.virt.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class NumbersTest {

    @Test
    void testSearch3OrderNumbers235() throws Exception {
        Integer firstNumber = 3;
        String[] a = "2 3 1 5 4 8 9".split(" ");
        int[] data = getIntArray(a);
        Assertions.assertEquals("[2, 3, 5]", Arrays.toString(get(data, firstNumber)));
    }

    @Test
    void testSearch3OrderNumbers159() throws Exception {
        Integer firstNumber = 3;
        String[] a = "6 1 5 2 9".split(" "); //159
        int[] data = getIntArray(a);
        Assertions.assertEquals("[1, 5, 9]", Arrays.toString(get(data, firstNumber)));
    }

    @Test
    void testSearch3OrderNumbers134() throws Exception {

        Integer firstNumber = 3;
        String[] a = "7 8 1 3 4 5".split(" ");
        //String[] a = "6 7 1 3 2".split(" ");
        int[] data = getIntArray(a);
        Assertions.assertEquals("[1, 3, 4]", Arrays.toString(get(data, firstNumber)));
    }

    private static Integer convertToInteger(String data) throws Exception {
        try {
            return Integer.valueOf(data);
        } catch (Exception e) {
            throw new Exception("input is not integer number");
        }
    }

    private static int[] get(int[] data, Integer count) throws Exception {
        int[] buffer = new int[count];
        int bufferPos = 0;
        for (int index = 0; index < data.length; index++) {
            bufferPos = 0;
            buffer = new int[count];
            for (int i = index; i < data.length; i++) {
                if (bufferPos > 0) {
                    if (buffer[bufferPos - 1] < data[i]) {
                        buffer[bufferPos] = data[i];
                        bufferPos++;
                    }
                } else {
                    buffer[bufferPos] = data[i];
                    bufferPos++;
                }
                if (bufferPos == count) {
                    break;
                }
            }
            if (bufferPos == count) {
                break;
            }
        }
        if (bufferPos < count) {
            throw new Exception("not foud " + count + " numbers");
        }
        return buffer;
    }

    private static int[] getIntArray(String[] data) throws Exception {
        int[] result = new int[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = convertToInteger(data[i]);
        }
        return result;
    }
}

 
