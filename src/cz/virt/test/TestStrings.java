package cz.virt.test;

import java.util.ArrayList;
import java.util.List;

public class TestStrings {

	private static final String WORDS_SEPARATOR = " ";

	private static String revertString(String data) throws Exception {
		if (data.length() > 20) {
			throw new Exception("max length: 20");
		}
		return new StringBuilder(data).reverse().toString();
	}

	private static String getLine(String[] data) throws Exception {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < data.length; i++) {
			if (i > 0) {
				result.append(WORDS_SEPARATOR);
			}
			result.append(revertString(data[i]));
		}
		return result.toString();
	}

	private static List<String> getRevertStrings(List<String> strings) throws Exception {
		List<String> result = new ArrayList<String>();
		for (String data : strings) {
			String[] string = data.split(WORDS_SEPARATOR);
			if (string.length > 100) {
				throw new Exception("max words: 100");
			}
			result.add(getLine(string));
		}
		return result;
	}

	private static void showData(List<String> strings) {
		for (String string : strings) {
			System.out.println(string);
		}
	}

	private static Integer getCount(String data) throws Exception {
		Integer result = 0;
		try {
			result = Integer.valueOf(data);
		} catch (Exception e) {
			throw new Exception("input is not number");
		}
		if (result < 0 || result > 100) {
			throw new Exception("input must be in the range: 0-100");
		}
		return result;
	}

	public static void main(String[] args) throws Exception {
		List<String> iStrings = new ArrayList<String>();
		iStrings.add("ahoj nazdar neco");
		iStrings.add("nazdar nekdo nekym");

		List<String> oStrings = getRevertStrings(iStrings);
		showData(oStrings);		
	}

}
