package AmazonS3.SignatureCalculation;

public class CanonicalRequestCalculation {
	public static String canonicalRequest(String method, String key, String bucket, String region,
			String request_parameters, String payload_hash, String signed_headers, String canonical_headers)
			throws Exception {

		String canonical_uri = "/" + key;

		String canonical_querystring = request_parameters;

		String canonical_request = method + '\n' + canonical_uri + '\n' + canonical_querystring + '\n'
				+ canonical_headers + '\n' + signed_headers + '\n' + payload_hash;

		return canonical_request;
	}
}
