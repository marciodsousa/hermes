package com.hermes.hermes.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hermes.hermes.ServiceHandler;
import com.hermes.hermes.SessionManager;
import com.hermes.hermes.db.UtilizadorDBManager;
import com.hermes.hermes.model.TUtilizador;

public class UtilizadorController {

	private ServiceHandler sh;
	private Gson gson = new GsonBuilder().create();
	private UtilizadorDBManager db;
	private SessionManager session;

	public UtilizadorController(Context ctx) {
		sh = new ServiceHandler();
		session = SessionManager.getInstance(ctx);
		db = UtilizadorDBManager.getInstance(ctx);
	}

	public int loginServer(String server, String user, String passwd) {
		int ret = 0;

		List<NameValuePair> list = new ArrayList<NameValuePair>();

		list.add(new BasicNameValuePair("usr", user));
		list.add(new BasicNameValuePair("passwd", passwd));

		// Making a request to url and getting response
		String jsonStr = sh.makeServiceCall(server + "/Auths",
				ServiceHandler.POST, list);

		if (jsonStr != null)
			ret = Integer.parseInt(jsonStr);

		return ret;
	}
	
	public boolean SyncUtilizador()
	{
		boolean ret = false;
		HashMap <String,String> data = session.getUserDetails();
		
        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(data.get(session.KEY_SERVER)+"/Utilizadores/"+data.get(session.KEY_USERID), ServiceHandler.GET);
        
        if (jsonStr.compareTo("400")==0 || jsonStr.compareTo("404")==0 || jsonStr.compareTo("409")==0 || jsonStr.compareTo("500")==0)
        {
        	ret = false;      
        }else{
        	 try{
             	TUtilizador u = null;
                u = gson.fromJson(jsonStr, TUtilizador.class);
                 
                if(db.getUtilizadorById(u.getIdUtilizador())!=null)
                {
                	return db.updateUtilizador(u);
                }else
                {
                	return db.addUtilizador(u);
                }	

             }catch(Exception ex)
             {
             	ret = false;
             }
        }
        return ret;
    }
}
