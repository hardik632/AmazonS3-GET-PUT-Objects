package AmazonS3.API;

import AmazonS3.util.*;
import AmazonS3.SignatureCalculation.*;
import java.util.*;
import java.net.URL;

public class PutObject {
	private static Scanner sc;

	public static String putS3Object(String key, String bucket, String region, String access_key, String secret_key)
			throws Exception {
		sc = new Scanner(System.in);
		String method = "PUT";
		String request_parameters = "";
		String service = "s3";

		System.out.print("Enter the content of the object:");
		String objectContent = sc.nextLine();

		String host = bucket + ".s3." + region + ".amazonaws.com";
		String algorithm = "AWS4-HMAC-SHA256"; // algorithm for string to sign
		URL endpoint = new URL("https://" + host + "/" + key);
		String payload = ToHash.toHexString(objectContent);

		Dates dates = new Dates();
		String amz_date = dates.amz_date();
		String date = dates.today_Date();

		Map<String, String> headers = new HashMap<String, String>();
		headers.put("x-amz-content-sha256", payload);
		headers.put("content-length", "" + objectContent.length());
		headers.put("Host", host);
		headers.put("x-amz-date", amz_date);

		String signed_headers = "host;x-amz-content-sha256;x-amz-date";
		String canonical_headers = "host:" + host + '\n' + "x-amz-content-sha256:" + payload + '\n' + "x-amz-date:"
				+ amz_date + '\n';
		String scope = date + '/' + region + '/' + service + '/' + "aws4_request";

		String canonical_request = CanonicalRequestCalculation.canonicalRequest(method, key, bucket, region,
				request_parameters, payload, signed_headers, canonical_headers);

		String string_to_sign = StringToSignCalculation.stringToSign(algorithm, scope, canonical_request);

		String signature_hash = SignatureCalculation.signature(secret_key, region, service, string_to_sign);

		String authorization_header = algorithm + ' ' + "Credential=" + access_key + '/' + scope + ", "
				+ "SignedHeaders=" + signed_headers + ", " + "Signature=" + signature_hash;
		headers.put("Authorization", authorization_header);

		String response = HttpUtils.invokeHttpRequest(endpoint, "PUT", headers, objectContent);

		return response;

	}
}