package AmazonS3.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpUtils {

	public static String invokeHttpRequest(URL endpointUrl, String httpMethod, Map<String, String> headers,
			String requestBody) {
		HttpURLConnection connection = createHttpConnection(endpointUrl, httpMethod, headers);
		try {
			if (requestBody != null) {
				DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
				wr.writeBytes(requestBody);
				wr.flush();
				wr.close();
			}
		} catch (Exception e) {
			throw new RuntimeException("Request failed. " + e.getMessage(), e);
		}
		return executeHttpRequest(connection);
	}

	public static String executeHttpRequest(HttpURLConnection connection) {
		try {
			// Get Response
			InputStream is;
			try {
				is = connection.getInputStream();
			} catch (IOException e) {
				is = connection.getErrorStream();
			}

			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuffer response = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			return response.toString();
		} catch (Exception e) {
			throw new RuntimeException("Request failed. " + e.getMessage(), e);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	public static HttpURLConnection createHttpConnection(URL endpointUrl, String httpMethod,
			Map<String, String> headers) {
		try {
			HttpURLConnection connection = (HttpURLConnection) endpointUrl.openConnection();
			connection.setRequestMethod(httpMethod);

			if (headers != null) {
				for (String headerKey : headers.keySet()) {
					// System.out.println(headerKey + ": " + headers.get(headerKey));
					connection.setRequestProperty(headerKey, headers.get(headerKey));
				}
			}

			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			return connection;
		} catch (Exception e) {
			throw new RuntimeException("Cannot create connection. " + e.getMessage(), e);
		}
	}
}
