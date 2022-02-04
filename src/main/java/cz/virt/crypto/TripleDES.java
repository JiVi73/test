package cz.virt.crypto;

import java.math.BigInteger;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class TripleDES {

    private static final String algorithm = "DESede";//
    private static final String algorithmCipher = "DESede/CBC/NoPadding";
    private static final char[] hexArray = "0123456789ABCDEF".toCharArray();

    private static SecretKey getKey(String keyValue) {
        return new SecretKeySpec(getKey16bitArray(keyValue), algorithm);
    }

    /*
     * private static byte[] getKeyArray(String keyVal){ int len =
     * keyVal.length(); byte[] result = new byte[len/2]; for( int i= 0; i< len;
     * i+=2){ result[i/2] = (byte) ((Character.digit(keyVal.charAt(i), 16) << 4)
     * + Character.digit(keyVal.charAt(i+1), 16)); } return result; }
     */

    private static byte[] getKey16bitArray(String keyVal) {
        int len = keyVal.length();
        byte[] result = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            result[i / 2] = (byte) ((Character.digit(keyVal.charAt(i), 16) << 4) + Character.digit(keyVal.charAt(i + 1), 16));
        }
        byte[] key;
        if (result.length == 16) {
            key = new byte[24];
            System.arraycopy(result, 0, key, 0, 16);
            System.arraycopy(result, 0, key, 16, 8);
        } else {
            key = result;
        }

        return key;
    }

    public static byte[] encrypt(String data, String keyValue) throws Exception {
        byte[] dataBytes = data.getBytes();
        Cipher cipher = Cipher.getInstance(algorithmCipher);
        IvParameterSpec iv = new IvParameterSpec(new byte[8]);
        cipher.init(Cipher.ENCRYPT_MODE, getKey(keyValue), iv);
        return cipher.doFinal(dataBytes);
    }

    public static String decrypt(byte[] encryptionBytes, String keyValue) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithmCipher);
        IvParameterSpec iv = new IvParameterSpec(new byte[8]);
        cipher.init(Cipher.DECRYPT_MODE, getKey(keyValue), iv);
        byte[] recoveredBytes = cipher.doFinal(encryptionBytes);
        return new String(recoveredBytes);
    }

    public static String getVDSP(String data) {
        return fillString(" " + " " + getRandomChar(4) + data.length() + data, 8, '!'); //VISA format
    }

    public static String getFromVDSP(String data) {
        String trimSalt = data.substring(6);
        return trimSalt.substring(2, Integer.parseInt(trimSalt.substring(0, 2)) + 2);
    }

    private static String fillString(String data, int mod, char c) {
        String result = data;
        while ((result.length() % mod) != 0) {
            result += c;
        }
        return result;
    }

    private static String getRandomChar(int length) {
        String result = "";
        for (int i = 0; i < length; i++) {
            Random rnd = new Random();
            result += (char) (rnd.nextInt(26) + 'A');
        }
        return result;
    }

    public static byte[] getHexBinary(byte[] data) {
        return new BigInteger(1, data).toByteArray();
    }

    public static String toHexString(String data) {
        byte[] ba = data.getBytes();
        StringBuilder str = new StringBuilder();
		for (byte b : ba) str.append(String.format("%x", b));
        return str.toString().toUpperCase();
    }

    public static String fromHexString(String hex) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < hex.length(); i += 2) {
            str.append((char) Integer.parseInt(hex.substring(i, i + 2), 16));
        }
        return str.toString();
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }


    public static String toHexString(byte[] array) {
        return DatatypeConverter.printHexBinary(array);
    }

    public static byte[] toByteArray(String s) {
        return DatatypeConverter.parseHexBinary(s);
    }
}