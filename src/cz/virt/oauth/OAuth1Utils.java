package cz.virt.oauth;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.xml.bind.DatatypeConverter;

public class OAuth1Utils {
	public static final String UTF_8 = "UTF-8";
	public static final String OAUTH_START_STRING = "OAuth ";
	public static final String EQUALS = "=";
	public static final String AMP = "&";
	public static final String COLON_2X_BACKSLASH = "://";
	public static final String ONE_POINT_ZERO = "1.0";
	public static final String SHA1 = "SHA-1";
	public static final String RSA_SHA1 = "RSA-SHA1";
	public static final String SHA1_WITH_RSA = "SHA1withRSA";
	public static final String REALM = "realm";
	public static final String OAUTH_CONSUMER_KEY = "oauth_consumer_key";
	public static final String OAUTH_VERSION = "oauth_version";
	public static final String OAUTH_SIGNATURE = "oauth_signature";
	public static final String OAUTH_SIGNATURE_METHOD = "oauth_signature_method";
	public static final String OAUTH_TIMESTAMP = "oauth_timestamp";
	public static final String OAUTH_NONCE = "oauth_nonce";
	public static final String OAUTH_BODY_HASH = "oauth_body_hash";
	public static final String QUESTION_MARK = "?";
	public static final String COMMA = ",";
	public static final String DOUBLE_QOUTE = "\"";
	public static final String EMPTY_STRING = "";
	public static SecureRandom random = new SecureRandom();
	public static final String validChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private PrivateKey privateKey;
	private String consumerKey;
	private String body;

	public String buildAuthHeaderString(String httpsURL, String requestMethod) throws Exception {
		OAuthParameters params = generateOAuthParameters();
		if (body != null && !body.equals("")) {
			params = setOauthBodyHashParameter(params);
		}
		generateAndSignSignature(httpsURL, requestMethod, params);
		StringBuffer buffer = new StringBuffer();
		buffer.append(OAUTH_START_STRING);
		Map<String, SortedSet<String>> paramMap = params.getBaseParameters();
		buffer = parseParameters(buffer, paramMap);
		return buffer.toString();
	}

	private void generateAndSignSignature(String httpsURL, String requestMethod, OAuthParameters oparams) throws Exception {
		OAuthParameters sbsParams = new OAuthParameters();
		sbsParams.putAll(oparams.getBaseParameters());
		sbsParams.removeBaseParameter(REALM);
		String baseString;
		baseString = generateSignatureBaseString(httpsURL, requestMethod, sbsParams.getBaseParameters());
		// this.setSignatureBaseString(baseString);
		String signature;
		signature = sign(baseString);
		oparams.addParameter(OAUTH_SIGNATURE, signature);
	}

	/*
	 * private void setSignatureBaseString(String signatureBaseString) {
	 * 
	 * this.signatureBaseString = signatureBaseString;
	 * 
	 * }
	 */
	private String generateSignatureBaseString(String httpsURL, String requestMethod,
			Map<String, SortedSet<String>> baseParameters) throws Exception {
		URI requestUri = parseUrl(httpsURL);
		String encodedBaseString;
		encodedBaseString = encode(requestMethod.toUpperCase()) + AMP + encode(normalizeUrl(requestUri)) + AMP
				+ encode(normalizeParameters(httpsURL, baseParameters));
		return encodedBaseString;
	}

	protected OAuthParameters setOauthBodyHashParameter(OAuthParameters params) throws Exception {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance(SHA1);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception(e); // user exception
		}
		digest.reset();
		byte[] hash;
		try {
			byte[] byteArray = body.getBytes(UTF_8);
			hash = digest.digest(byteArray);
		} catch (UnsupportedEncodingException e) {
			throw new Exception(e);// user exception

		}
		String encodedHash = DatatypeConverter.printBase64Binary(hash);
		params.addParameter(OAUTH_BODY_HASH, encodedHash);
		return params;
	}

	private static String normalizeUrl(URI uri) {
		return uri.getScheme() + COLON_2X_BACKSLASH + uri.getHost() + uri.getRawPath();
	}

	private static String normalizeParameters(String httpUrl, Map<String, SortedSet<String>> requestParameters) throws Exception {
		// add the querystring to the base string (if one exists)
		if (httpUrl.indexOf(QUESTION_MARK) > 0) {
			Map<String, SortedSet<String>> queryParameters = parseQuerystring(
					httpUrl.substring(httpUrl.indexOf(QUESTION_MARK) + 1));
			for (Map.Entry<String, SortedSet<String>> e : queryParameters.entrySet()) {
				TreeSet<String> elementsToAddBack = new TreeSet<String>();
				for (String value : e.getValue()) {
					elementsToAddBack.add(encode(value));
				}
				queryParameters.put(e.getKey(), elementsToAddBack);
			}
			requestParameters.putAll(queryParameters);
		}
		// piece together the base string, encoding each key and value
		StringBuilder paramString = new StringBuilder();
		for (Map.Entry<String, SortedSet<String>> e : requestParameters.entrySet()) {
			if (e.getValue().size() == 0) {
				continue;
			}
			if (paramString.length() > 0) {
				paramString.append(AMP);
			}
			int tempCounter = 0;
			for (String value : e.getValue()) {
				paramString.append(encode(e.getKey())).append(EQUALS).append((value));
				if (tempCounter != e.getValue().size() - 1) {
					paramString.append(AMP);
				}
				tempCounter++;
			}
		}
		return paramString.toString();
	}

	private static Map<String, SortedSet<String>> parseQuerystring(String queryString) throws Exception {
		Map<String, SortedSet<String>> map = new TreeMap<String, SortedSet<String>>();
		if ((queryString == null) || (queryString.equals(EMPTY_STRING))) {
			return map;
		}
		String[] params = new String[20];
		if (queryString.contains(AMP))
			params = queryString.split(AMP);
		else if (queryString.length() > 0)
			params = new String[] { queryString };
		if (params != null) {
			for (String param : params) {
				try {
					String[] keyValuePair = param.split(EQUALS, 0);
					String name = URLDecoder.decode(keyValuePair[0], UTF_8);
					if (name == "") {
						continue;
					}
					String value = keyValuePair.length > 1 ? URLDecoder.decode(keyValuePair[1], UTF_8) : EMPTY_STRING;
					if (map.containsKey(name)) {
						SortedSet<String> tempSet = map.get(name);
						tempSet.add(value);
					} else {
						SortedSet<String> tmpSet = new TreeSet<String>();
						tmpSet.add(value);
						map.put(name, tmpSet);
					}
				} catch (UnsupportedEncodingException e) {
					throw new  Exception(e); // user exception
					// ignore this parameter if it can't be decoded
				}
			}
		}
		return map;
	}

	private URI parseUrl(String httpsURL) throws Exception {
		// validate the request url
		if ((httpsURL == null) || (httpsURL.length() == 0)) {
			throw new  Exception("OAuthUtils: Request Url cannot be empty");// user exception
		}
		// parse the url into its constituent parts.
		URI uri;
		try {
			uri = new URI(httpsURL);
		} catch (Exception e) {
			throw new  Exception(e); // user exception
		}
		return uri;
	}

	private static String encode(String stringToEncode) throws Exception {
		String encoded;
		try {
			encoded = URLEncoder.encode(stringToEncode, UTF_8);
		} catch (UnsupportedEncodingException e) {
			throw new  Exception(e); // user exception
		}
		encoded = encoded.replace("*", "%2A");
		encoded = encoded.replace("~", "%7E");
		encoded = encoded.replace("+", "%20");
		return encoded;
	}

	private String sign(String baseString) throws Exception {
		try {
			Signature signer = Signature.getInstance(SHA1_WITH_RSA);
			signer.initSign(privateKey);
			signer.update(baseString.getBytes(UTF_8));
			return DatatypeConverter.printBase64Binary(signer.sign());
		} catch (InvalidKeyException e) {
			throw new  Exception(e); // user exception
		} catch (SignatureException e) {
			throw new  Exception(e); // user exception
		} catch (UnsupportedEncodingException e) {
			throw new  Exception(e); // user exception
		} catch (NoSuchAlgorithmException e) {
			throw new Exception(e); // user exception
		}
	}

	protected OAuthParameters generateOAuthParameters() {
		OAuthParameters oparams = new OAuthParameters();
		oparams.addParameter(OAUTH_CONSUMER_KEY, consumerKey);
		oparams.addParameter(OAUTH_NONCE, generateNonce());
		oparams.addParameter(OAUTH_TIMESTAMP, generateTimeStamp());
		oparams.addParameter(OAUTH_SIGNATURE_METHOD, RSA_SHA1);
		oparams.addParameter(OAUTH_VERSION, ONE_POINT_ZERO);
		return oparams;
	}

	private StringBuffer parseParameters(StringBuffer buffer, Map<String, SortedSet <String>> paramMap) throws Exception {
		int cnt = 0;
		// loop thru extra parameters and append to OAuth HTTP Header value
		int namesLength = paramMap.keySet().size();
		for (String key : paramMap.keySet()) {
			parseSortedSetValues(buffer, key, paramMap);
			cnt++;
			if (namesLength > cnt) {
				buffer.append(COMMA);
			}
		}
		return buffer;
	}

	private StringBuffer parseSortedSetValues(StringBuffer buffer, String key, Map<String, SortedSet<String>> paramMap) throws Exception {
		for (String value : paramMap.get(key)) {
			buffer.append(key).append(EQUALS).append(DOUBLE_QOUTE).append(encode(value)).append(DOUBLE_QOUTE);
		}
		return buffer;
	}

	private String generateTimeStamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}

	private static String generateNonce() {
		return String.valueOf((int) (Math.random() * 100000000));
	}

	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}

	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	public void setBody(String body) {
		this.body = body;
	}
}
