package com.hermes.hermes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;


public class Controller {
	
	private String host = "http://wvm100.dei.isep.ipp.pt/Hermes/";
	//private String host = "http://api.androidhive.info/contacts/";
	// contacts JSONArray

 
    // Hashmap for ListView
    ArrayList<HashMap<String, String>> contactList;
    
    public Controller()
    {
    
    }
	public HashMap<String, String> getUserData(int idUsr)
	{
	    JSONObject usr = null;
		HashMap<String, String> ret = new HashMap<String, String>();

        // Creating service handler class instance
        ServiceHandler sh = new ServiceHandler();

        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(host + "Utilizadores/"+idUsr, ServiceHandler.GET);

        Log.d("Response: ", "> " + jsonStr);

        if (jsonStr != null) {
            try {
            	usr = new JSONObject (jsonStr);

                String id = usr.getString("idUtilizador");
                String username = usr.getString("username");
                String email = usr.getString("email");
                String name = usr.getString("nome");
                String password = usr.getString("password");
                String passwordSalt = usr.getString("passwordSalt");
                String estado = usr.getString("estado");
                String serial = usr.getString("numSerieEquip");
                String tipoUsr = usr.getString("idTipoUtilizador");
                String idEmpresa = usr.getString("idEmpresa");

                // adding each child node to HashMap key => value
                ret.put("idUtilizador", id);
                ret.put("username", username);
                ret.put("email", email);
                ret.put("nome", name);
                ret.put("password", password);
                ret.put("passwordSalt", passwordSalt);
                ret.put("estado", estado);
                ret.put("serial", serial);
                ret.put("tipoUsr", tipoUsr);
                ret.put("idEmpresa", idEmpresa);
                    
                 
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
        }
        
        return ret;
        /*
		// Calling async task to get json
        new GetContacts().execute();*/
    }
 
	public int loginServer(String server, String user, String passwd)
	{
 
		int ret=0;

        // Creating service handler class instance
        ServiceHandler sh = new ServiceHandler();
        
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        
        list.add(new BasicNameValuePair("usr", user));
        list.add(new BasicNameValuePair("passwd", passwd));

        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(server+"/Licenca", ServiceHandler.POST,list);
        
        if (jsonStr != null)
        	ret=Integer.parseInt(jsonStr);
        
        return ret;
    }
	
	public int registerDevice(String server, String imei)
	{
 
		int ret=0;

        // Creating service handler class instance
        ServiceHandler sh = new ServiceHandler();
        
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        
        list.add(new BasicNameValuePair("imei", imei));

        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(server+"/Licencas", ServiceHandler.POST,list);
        
        if (jsonStr != null)
        	ret=Integer.parseInt(jsonStr);
        
        return ret;
    }

}
