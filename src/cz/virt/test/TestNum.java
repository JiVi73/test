package cz.virt.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TestNum {

	private static Integer convertToInteger(String data) throws Exception {
		try {
			return Integer.valueOf(data);
		} catch (Exception e) {
			throw new Exception("input is not integer number");
		}
	}

	private static int[] get(int[] data, Integer count) throws Exception {
		int[] result = new int[count];
		Integer lastMax = 0;
		Integer pos = 0;

		for (int i = 0; i < data.length; i++) {
			if (pos == count) {
				break;
			}
			Integer buf = data[i];
			for (int y = 1; y < count; y++) {
				if ((i + y) < data.length) {
					Integer res = data[i + y];
					if (buf > res) {
						if (lastMax < res) {
							buf = res;
						}
						break;
					} else {
						if (lastMax < res) {
							if (pos == 0) {
								result[pos] = buf;
								pos++;
							}
							result[pos] = res;
							lastMax = res;
							pos++;
						}

						if (pos == count) {
							break;
						}
						buf = res;
					}
				}
			}

		}
		return result;
	}

	private static void showData(int[] data) {
		for (int i = 0; i < data.length; i++) {
			System.out.print(data[i]);
			if (i < data.length - 1) {
				System.out.print(",");
			}
		}
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

		// String[] a = "6 1 5 2 9".split(" "); // 1,5,9
		String[] a = "2 3 1 5 4 8 9".split(" "); // 1,5,9
		
		int[] data = getIntArray(a);
		
		showData(get(data, firstNumber));

	}

}
