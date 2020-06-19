package AmazonS3.SignatureCalculation;
import AmazonS3.util.*;
public class StringToSignCalculation {

	public static String stringToSign(String algorithm, String credential_scope, String canonical_request)
			throws Exception {

		Dates dates = new Dates();

		String amz_date = dates.amz_date();

		String string_to_sign = algorithm + '\n' + amz_date + '\n' + credential_scope + '\n'
				+ ToHash.toHexString(canonical_request);

		return string_to_sign;

	}

}
