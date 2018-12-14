package cz.virt.crypto;

public class TestTriple {

	private static final String keyValue = "2315218C9111AD402315218C9111AD41";

	
	private static String testEncrypt(String sData){
		String result = null;		
		try {
			byte[] data = TripleDES.encrypt(TripleDES.getVDSP(sData), keyValue);
			result = TripleDES.toHexString(data);					
		} catch (Exception e) {
			e.printStackTrace();		
		}	
		return result;
	}

	private static String testDecrypt(String sData){
		String result = null;		
		try {
			result = TripleDES.getFromVDSP(TripleDES.decrypt(TripleDES.toByteArray(sData), keyValue));							
		} catch (Exception e) {
			e.printStackTrace();		
		}	
		return result;
	}
	
	public static void main(String[] args) {
		String response = testEncrypt("Hello world!");
		System.out.println(response);
		System.out.println(testDecrypt(response));	
		
	}

}
