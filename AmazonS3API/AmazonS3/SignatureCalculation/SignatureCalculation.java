package AmazonS3.SignatureCalculation;

import AmazonS3.util.*;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class SignatureCalculation {
	public static String signature(String secret_key, String region, String service, String string_to_sign)
			throws Exception {
		
		Dates dates = new Dates();
		String date = dates.today_Date();

		byte[] sign_key = getSignatureKey(secret_key, date, region, service);
		byte[] signature = HmacSHA256(string_to_sign, sign_key);
		StringBuilder signature_hex = new StringBuilder();
		for (byte b : signature) {
			signature_hex.append(String.format("%02x", b));
		}
		String signature_hash = signature_hex.toString();
		return signature_hash;
	}

	static byte[] HmacSHA256(String data, byte[] key) throws Exception {
		String algorithm = "HmacSHA256";
		Mac mac = Mac.getInstance(algorithm);
		mac.init(new SecretKeySpec(key, algorithm));
		return mac.doFinal(data.getBytes("UTF-8"));
	}

	static byte[] getSignatureKey(String key, String dateStamp, String regionName, String serviceName)
			throws Exception {
		byte[] kSecret = ("AWS4" + key).getBytes("UTF-8");
		byte[] kDate = HmacSHA256(dateStamp, kSecret);
		byte[] kRegion = HmacSHA256(regionName, kDate);
		byte[] kService = HmacSHA256(serviceName, kRegion);
		byte[] kSigning = HmacSHA256("aws4_request", kService);
		return kSigning;
	}
}
