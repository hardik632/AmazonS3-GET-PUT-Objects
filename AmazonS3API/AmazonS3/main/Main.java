package AmazonS3.main;

import java.util.Scanner;

import AmazonS3.API.GetObject;
import AmazonS3.API.PutObject;

public class Main {

	private static Scanner sc;

	public static void main(String[] args) throws Exception {
		sc = new Scanner(System.in);
		System.out.print("Enter the method name you want to perform (get/put):");
		String method = sc.nextLine();
		System.out.print("Enter object name you want to " + method + ":");
		String key = sc.nextLine();
		System.out.print("Enter Bucket:");
		String bucket = sc.nextLine();
		System.out.print("Enter Region:");
		String region = sc.nextLine();
		System.out.print("Enter access key:");
		String access_key = sc.nextLine();
		System.out.print("Enter secret key:");
		String secret_key = sc.nextLine();

		if (method.equals("get")) {

			String response = GetObject.getS3Object(key, bucket, region, access_key, secret_key);
			System.out.println("--------- Response content ---------");
			System.out.print(response);
			System.out.println("------------------------------------");
		}

		if (method.equals("put")) {

			String response = PutObject.putS3Object(key, bucket, region, access_key, secret_key);
			System.out.println("--------- Response content ---------");
			System.out.println("uploaded" + response);
			System.out.println("------------------------------------");
		}

	}

}
