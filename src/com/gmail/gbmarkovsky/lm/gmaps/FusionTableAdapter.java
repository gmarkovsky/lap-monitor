package com.gmail.gbmarkovsky.lm.gmaps;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

import com.google.gdata.client.ClientLoginAccountType;
import com.google.gdata.client.GoogleService;
import com.google.gdata.client.Service.GDataRequest;
import com.google.gdata.client.Service.GDataRequest.RequestType;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ContentType;
import com.google.gdata.util.ServiceException;

public class FusionTableAdapter {
	
	/**
	 * URL сервиса Fusion Table.
	 */
	private static final String SERVICE_URL =
		"https://www.google.com/fusiontables/api/query";
	
	/**
	 * Шаблон для чтения CSV ответов сервиса.
	 */
	private static final Pattern CSV_VALUE_PATTERN =
		Pattern.compile("([^,\\r\\n\"]*|\"(([^\"]*\"\")*[^\"]*)\")(,|\\r?\\n)");

	/**
	 * Гугл-сервис.
	 */
	private GoogleService service;
	
	public FusionTableAdapter(String email, String password)	throws AuthenticationException {
		service = new GoogleService("fusiontables", "fusiontables.ApiExample");
		service.setUserCredentials(email, password, ClientLoginAccountType.GOOGLE);
	}

	public String createQuery(String query) throws IOException, ServiceException {
		URL url = null;
		try {
			url = new URL(SERVICE_URL);
		} catch (MalformedURLException e1) {
			throw new RuntimeException();
		}
		
		GDataRequest request = service.getRequestFactory().getRequest(
					RequestType.INSERT, url,
					new ContentType("application/x-www-form-urlencoded"));
		
		OutputStreamWriter writer = new OutputStreamWriter(request.getRequestStream());

		writer.append("sql=" + URLEncoder.encode(query, "UTF-8"));

		writer.flush();
		
		request.execute();
		
		Scanner scanner = new Scanner(request.getResponseStream(),"UTF-8");
		
		int id = 0;
		
	    while (scanner.hasNextLine()) {
	        scanner.findWithinHorizon(CSV_VALUE_PATTERN, 0);
	        MatchResult match = scanner.match();
	        String quotedString = match.group(2);
	        String decoded = quotedString == null ? match.group(1)
	            : quotedString.replaceAll("\"\"", "\"");
	        System.out.print("|" + decoded);
	        if (!match.group(4).equals(",")) {
	          System.out.println("|");
	        }
	        if (id == 1) {
	        	return decoded;
	        }
	        id++;
	      }
		return null;
	}
	
	public void insertQuery(String query) throws IOException, ServiceException {
		URL url = null;
		try {
			url = new URL(SERVICE_URL);
		} catch (MalformedURLException e1) {
			throw new RuntimeException();
		}
		
		GDataRequest request = service.getRequestFactory().getRequest(
					RequestType.INSERT, url,
					new ContentType("application/x-www-form-urlencoded"));
		
		OutputStreamWriter writer = new OutputStreamWriter(request.getRequestStream());

		writer.append("sql=" + URLEncoder.encode(query, "UTF-8"));

		writer.flush();
		
		request.execute();
	}
}
