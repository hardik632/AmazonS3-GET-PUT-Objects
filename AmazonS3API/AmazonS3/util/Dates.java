package AmazonS3.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Dates {

	public String today_Date() {
		// creating date for credential scope
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		TimeZone utc = TimeZone.getTimeZone("UTC");
		sdf.setTimeZone(utc);
		String date = (sdf.format(today)).toString();
		return date;
	}

	public String amz_date() {
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
		TimeZone utc = TimeZone.getTimeZone("UTC");
		sdf.setTimeZone(utc);
		String date = (sdf.format(today)).toString();
		return date;
	}

}