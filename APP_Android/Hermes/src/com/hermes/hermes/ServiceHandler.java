package com.hermes.hermes;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.List;
 




import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
 
public class ServiceHandler {
 
    static String response = null;
    public final static int GET = 1;
    public final static int POST = 2;
    public final static int PUT = 3;
 
    public ServiceHandler() {
 
    }
 
    /**
     * Making service call
     * @url - url to make request
     * @method - http request method
     * */
    public String makeServiceCall(String url, int method) {
        return this.makeServiceCall(url, method, null);
    }
 
    /**
     * Making service call
     * @url - url to make request
     * @method - http request method
     * @params - http request params
     * */
    public String makeServiceCall(String url, int method,
            List<NameValuePair> params) {
        try {
            // http client
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;
             
            // Checking http request method type
            if (method == POST) {
                HttpPost httpPost = new HttpPost(url);
                // adding post params
                if (params != null) {
                    httpPost.setEntity(new UrlEncodedFormEntity(params));
                }
 
                httpResponse = httpClient.execute(httpPost);
 
            } else if (method == GET) {
                // appending params to url
                if (params != null) {
                    String paramString = URLEncodedUtils
                            .format(params, "utf-8");
                    url += "?" + paramString;
                }
                HttpGet httpGet = new HttpGet(url);
 
                httpResponse = httpClient.execute(httpGet);
 
            } else if (method == PUT) {
            	HttpPut httpPut = new HttpPut(url);
                // adding post params
                if (params != null) {
                	httpPut.setEntity(new UrlEncodedFormEntity(params));
                }
 
                httpResponse = httpClient.execute(httpPut);
            }
            
            switch (httpResponse.getStatusLine().getStatusCode()) {
	            case HttpURLConnection.HTTP_OK:  
					httpEntity = httpResponse.getEntity();
					response = EntityUtils.toString(httpEntity);
	        			break;
	        			
	            case HttpURLConnection.HTTP_NOT_FOUND:  
	            	response = "404";
					    break;
				
	            case HttpURLConnection.HTTP_INTERNAL_ERROR:  
	            	response = "500";
	                    break;
	                    
	            case HttpURLConnection.HTTP_CONFLICT:  
	            	response = "409";
	                    break;
	                    
	            case HttpURLConnection.HTTP_BAD_REQUEST:  
	            	response = "400";
	                    break;
			}
            
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
         
        return response;
 
    }
    
    /**
     * Making service call
     * @url - url to make request
     * @method - http request method
     * @params - http request params
     * */
    public String makeServiceCallJS(String url, int method,
            String json) {
        try {
            // http client
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;
             
            // Checking http request method type
            if (method == POST) {
                HttpPost httpPost = new HttpPost(url);
                // adding post params
                if (json.compareTo("") != 0) {
                	httpPost.setEntity(new ByteArrayEntity(
                		    json.toString().getBytes("UTF8")));
                }
 
                httpResponse = httpClient.execute(httpPost);
 
            } else if (method == PUT) {
            	HttpPut httpPut = new HttpPut(url);
                // adding post params
                if (json.compareTo("") != 0) {
                	httpPut.setEntity(new ByteArrayEntity(
                		    json.toString().getBytes("UTF8")));
                }
 
                httpResponse = httpClient.execute(httpPut);
 
            }
            
            
            switch (httpResponse.getStatusLine().getStatusCode()) {
	            case HttpURLConnection.HTTP_OK:  
					httpEntity = httpResponse.getEntity();
					response = EntityUtils.toString(httpEntity);
	        			break;
	        			
	            case HttpURLConnection.HTTP_NOT_FOUND:  
	            	response = "404";
					    break;
				
	            case HttpURLConnection.HTTP_INTERNAL_ERROR:  
	            	response = "500";
	                    break;
	                    
	            case HttpURLConnection.HTTP_CONFLICT:  
	            	response = "409";
	                    break;
	                    
	            case HttpURLConnection.HTTP_BAD_REQUEST:  
	            	response = "400";
	                    break;
			}
          
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
         
        return response;
 
    }
}