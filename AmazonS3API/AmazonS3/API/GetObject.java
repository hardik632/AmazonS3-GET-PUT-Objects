package AmazonS3.API;

import AmazonS3.util.*;
import java.net.URL;
import java.util.*;
import AmazonS3.SignatureCalculation.*;

public class GetObject {

	public static String getS3Object(String key, String bucket, String region, String access_key, String secret_key)
			throws Exception {

		String method = "GET";
		String request_parameters = "";
		String service = "s3";
		String payload = "";
		String host = bucket + ".s3." + region + ".amazonaws.com";
		URL endpoint = new URL("https://" + host + "/" + key);
		String algorithm = "AWS4-HMAC-SHA256"; // algorithm for string to sign

		Dates dates = new Dates();

		String amz_date = dates.amz_date();
		String date = dates.today_Date();

		String signed_headers = "host;x-amz-date";
		String payload_hash = ToHash.toHexString((payload));
		String credential_scope = date + '/' + region + '/' + service + '/' + "aws4_request";
		String canonical_headers = "host:" + host + '\n' + "x-amz-date:" + amz_date + '\n';

		String canonical_request = CanonicalRequestCalculation.canonicalRequest(method, key, bucket, region,
				request_parameters, payload_hash, signed_headers, canonical_headers);

		String string_to_sign = StringToSignCalculation.stringToSign(algorithm, credential_scope, canonical_request);

		String signature_hash = SignatureCalculation.signature(secret_key, region, service, string_to_sign);

		String authorization_header = algorithm + ' ' + "Credential=" + access_key + '/' + credential_scope + ", "
				+ "SignedHeaders=" + signed_headers + ", " + "Signature=" + signature_hash;

		Map<String, String> headers = new HashMap<String, String>();
		headers.put("x-amz-content-sha256", payload_hash);
		headers.put("Authorization", authorization_header);
		headers.put("x-amz-date", amz_date);

		String response = HttpUtils.invokeHttpRequest(endpoint, "GET", headers, null);
		return response;

	}

}
