package com.hermes.hermes;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;


public class Controller {
	
	private String host = "http://wvm100.dei.isep.ipp.pt/Hermes/utilizadores";
	//private String host = "http://api.androidhive.info/contacts/";
	// contacts JSONArray
    JSONArray contacts = null;
 
    // Hashmap for ListView
    ArrayList<HashMap<String, String>> contactList;
    
    public Controller()
    {
    
    }
	public HashMap<String, String> getUserData(String user)
	{
 
		HashMap<String, String> ret = new HashMap<String, String>();

        // Creating service handler class instance
        ServiceHandler sh = new ServiceHandler();

        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(host, ServiceHandler.GET);

        Log.d("Response: ", "> " + jsonStr);

        if (jsonStr != null) {
            try {
            	contacts = new JSONArray (jsonStr);

                // looping through All Contacts
                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);
                     
                    String id = c.getString("idUtilizador");
                    String username = c.getString("username");
                    String email = c.getString("email");
                    String name = c.getString("nome");
                    String password = c.getString("password");
                    String passwordSalt = c.getString("passwordSalt");
                    String estado = c.getString("estado");
                    String serial = c.getString("numSerieEquip");
                    String tipoUsr = c.getString("idTipoUtilizador");
                    String idEmpresa = c.getString("idEmpresa");
                    
                    HashMap<String, String> userObj = new HashMap<String, String>();

                    // adding each child node to HashMap key => value
                    userObj.put("idUtilizador", id);
                    userObj.put("username", username);
                    userObj.put("email", email);
                    userObj.put("nome", name);
                    userObj.put("password", password);
                    userObj.put("passwordSalt", passwordSalt);
                    userObj.put("estado", estado);
                    userObj.put("serial", serial);
                    userObj.put("tipoUsr", tipoUsr);
                    userObj.put("idEmpresa", idEmpresa);
                    
                    if (user.equalsIgnoreCase(userObj.get("username").toString()))
                    	ret = userObj;
                }
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
 

}
