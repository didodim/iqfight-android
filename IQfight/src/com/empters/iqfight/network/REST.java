 package com.empters.iqfight.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;


public class REST {
 public final static String ENCODING_CYRILLIC = "WINDOWS-1251";
 public final static String ENCODING_UTF = "UTF-8";
 
 public static HttpClient httpclient = new DefaultHttpClient();
 
 public static String queryRESTurl(String url, String encoding) {

  HttpGet httpget = new HttpGet(url);
  HttpResponse response;

  try {
   response = httpclient.execute(httpget);
   
   
   HttpEntity entity = response.getEntity();
   
   if (entity != null) {
    
    InputStream instream = entity.getContent();
    
    //String result = RestClient.convertStreamToString(instream);
    BufferedReader br = null;
    StringBuilder sb = new StringBuilder(16384);
    br = new BufferedReader(new InputStreamReader(instream, encoding));
    
    String line;
    while ((line = br.readLine()) != null) {
     sb.append(line);
     sb.append('\n');
    }
    
    String result = sb.toString();
    instream.close();
    
    return result;
   }
  } catch (ClientProtocolException e) {

  } catch (IOException e) {
 
  }
  
  return null;
 }
 
 
 public static String queryPostJSON(List<NameValuePair> nameValuePairs, String url, String encoding,Map<String, String>...headers) {
  
  HttpPost httpost = new HttpPost(url);
  HttpResponse response;
  
  try {

   httpost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

   for(Map<String,String> map : headers)
    for(Map.Entry<String, String> header : map.entrySet())
    {
     httpost.setHeader(header.getKey(),header.getValue());
    }
   response = httpclient.execute(httpost);
   
   
   HttpEntity entity = response.getEntity();
   
   if (entity != null) {
    
    InputStream instream = entity.getContent();
    
    //String result = RestClient.convertStreamToString(instream);
    BufferedReader br = null;
    StringBuilder sb = new StringBuilder(16384);
    br = new BufferedReader(new InputStreamReader(instream, encoding));
    
    String line;
    while ((line = br.readLine()) != null) {
     sb.append(line);
     sb.append('\n');
    }
    
    String result = sb.toString();
    instream.close();
    return result;
   }
  } catch (ClientProtocolException e) {

  } catch (IOException e) {
  
  }
  
  return null;
 }
 

}