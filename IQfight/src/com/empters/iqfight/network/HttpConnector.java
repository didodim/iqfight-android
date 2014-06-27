package com.empters.iqfight.network;



import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpConnector {
	
	private static String url = "http://iqfight.empters.com/";
	private static HttpClient client=  new DefaultHttpClient();
	

	
	
	private static void isLogged() throws Exception {
 

		
		HttpGet isLogged = new HttpGet(url+"is_logged");
		
		HttpResponse resp = client.execute(isLogged);
		 
	


	
	}

}
