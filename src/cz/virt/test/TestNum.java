package cz.virt.test;

 

import java.io.BufferedReader;

import java.io.InputStreamReader;

 

public class TestNum {

       

       

       //variant 1

       private static Boolean max = false;

 

       private static Integer convertToInteger(String data) throws Exception {

             try {

                    return Integer.valueOf(data);

             } catch (Exception e) {

                    throw new Exception("input is not integer number");

             }

       }

       

       // variant 1

       private static int[] search(int[] data, Integer index, Integer count) {

             int bufferPos = 0;

             int[] buffer = new int[count];

             for (int i = index; i < data.length; i++) {

                    if(bufferPos > 0) {

                           if(buffer[bufferPos-1] < data[i]) {                                     

                                  buffer[bufferPos] = data[i];

                                  bufferPos++;

                           }

                    } else {                                

                           buffer[bufferPos] = data[i];

                           bufferPos++;

                    }

                    if (bufferPos == count) { max = true; break;}

             }                         

             return buffer;

       }

       

       // variant 1

       private static int[] get(int[] data, Integer count) throws Exception {

             

             int[] result = new int[count];         

             for (int index = 0; index < data.length; index++) {

                    result = search(data, index, count);

                    if (max) break;                                      

             }                   

             return result;

       }

       

       //*******************************************

       // variant 2

       private static int[] get2(int[] data, Integer count) throws Exception {

             

             int[] buffer = new int[count];

             int bufferPos = 0;

             for (int index = 0; index < data.length; index++) {

                    bufferPos = 0;

                    buffer = new int[count];

                    for (int i = index; i < data.length; i++) {

                           if(bufferPos > 0) {

                                  if(buffer[bufferPos-1] < data[i]) {                                      

                                        buffer[bufferPos] = data[i];

                                        bufferPos++;

                                  }

                           } else {                                

                                  buffer[bufferPos] = data[i];

                                  bufferPos++;

                           }

                           if (bufferPos == count) {break;}

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

       

       

       

       

 

       private static void showData(int[] data) {

             for (int i = 0; i < data.length; i++) {

                    System.out.print(data[i]);

                    if (i < data.length - 1) {

                           System.out.print(",");

                    }

             }

             System.out.println();

       }

 

       private static int[] getIntArray(String[] data) throws Exception {

             int[] result = new int[data.length];

             for (int i = 0; i < data.length; i++) {

                    result[i] = convertToInteger(data[i]);

             }

             return result;

       }

 

       public static void main(String[] args) throws Exception {

             BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

             // prvni cislo

             Integer firstNumber = 3;

 

             //String[] a = "6 1 5 2 9".split(" "); //159

             //String[] a = "2 3 1 5 4 8 9".split(" "); 

             //String[] a = "7 8 1 3 4 5".split(" "); 

             String[] a = "6 7 1 3 2".split(" "); 

             

             int[] data = getIntArray(a);

             

             showData(get(data, firstNumber));

             showData(get2(data, firstNumber));

 

       }

 

}

 
