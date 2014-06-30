package com.hermes.hermes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hermes.hermes.Model.*;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.hermes.hermes.db.DatabaseManager;


public class Controller {
	
	private String host = "http://wvm100.dei.isep.ipp.pt/Hermes/";
	//private String host = "http://api.androidhive.info/contacts/";
	// contacts JSONArray

 
    // Hashmap for ListView
    ArrayList<HashMap<String, String>> contactList;
    ServiceHandler sh;
    Gson gson = new GsonBuilder().create();
    DatabaseManager db;
    SessionManager session;
    
    public Controller(Context ctx)
    {
    	sh = new ServiceHandler();
    	session = SessionManager.getInstance(ctx);
    	db = DatabaseManager.getInstance();
    }
	public HashMap<String, String> getUserData(int idUsr)
	{
	    JSONObject usr = null;
		HashMap<String, String> ret = new HashMap<String, String>();

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
 
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        
        list.add(new BasicNameValuePair("usr", user));
        list.add(new BasicNameValuePair("passwd", passwd));

        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(server+"/Auths", ServiceHandler.POST,list);
        
        if (jsonStr != null)
        	ret=Integer.parseInt(jsonStr);
        
        return ret;
    }
	
	public String registerDevice(String server, String imei)
	{
		String ret="";
        
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        
        list.add(new BasicNameValuePair("imei", imei));
        list.add(new BasicNameValuePair("estado", "0"));

        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(server+"/Licencas", ServiceHandler.POST,list);
        
        if (jsonStr.compareTo("400")!=0 || jsonStr.compareTo("404")!=0 || jsonStr.compareTo("409")!=0 || jsonStr.compareTo("500")!=0)
        {
        	TLicenca l = null;
            
            try{
            	Gson gson = new GsonBuilder().create();
                l = gson.fromJson(jsonStr, TLicenca.class);
                ret = l.getCodLicenca();
    			
            }catch(Exception ex)
            {
            	return "500";
            }
        }else{
        	ret = jsonStr;
        }

        return ret;
    }
	
	public String syncAllData(SessionManager session)
	{

		SyncUserData();
//		SyncCompanyData();
		SyncProducts();
//		SyncPlaces();
//		SyncClients();
//		SyncTranportationGuides();
		
		String ret="";

       

        return ret;
    }
	
	public String SyncUserData()
	{
		String ret="";
		HashMap <String,String> data = session.getUserDetails();
		
        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(data.get(session.KEY_SERVER)+"/Utilizadores/"+data.get(session.KEY_USERID), ServiceHandler.GET);
        
        if (jsonStr.compareTo("400")==0 || jsonStr.compareTo("404")==0 || jsonStr.compareTo("409")==0 || jsonStr.compareTo("500")==0)
        {
        	ret = jsonStr;      
        }else{
        	 try{
             	TUtilizador u = null;
                 u = gson.fromJson(jsonStr, TUtilizador.class);
                 db.addUtilizador(u);
     			
             }catch(Exception ex)
             {
             	return "500";
             }
        }

        return ret;
	
    }
	
	public String SyncCompanyData()
	{
 
		String ret="";
		HashMap <String,String> data = session.getUserDetails();
		
        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(data.get(session.KEY_SERVER)+"/Empresas", ServiceHandler.GET);
        
        if (jsonStr.compareTo("400")!=0 || jsonStr.compareTo("404")!=0 || jsonStr.compareTo("409")!=0 || jsonStr.compareTo("500")!=0)
        {

            try{
            	TEmpresa e = null;
                e = gson.fromJson(jsonStr, TEmpresa.class);
                
            }catch(Exception ex)
            {
            	return "500";
            }
        }else{
        	ret = jsonStr;
        }

        return ret;
    }
	
	public boolean SyncProducts()
	{
		boolean ret = true;
		HashMap <String,String> data = session.getUserDetails();

        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(data.get(session.KEY_SERVER)+"/Produtos/", ServiceHandler.GET);
        
        if (jsonStr.compareTo("400")!=0 || jsonStr.compareTo("404")!=0 || jsonStr.compareTo("409")!=0 || jsonStr.compareTo("500")!=0)
        {
            try{        
                List<TProduto> prodsServer = new ArrayList<TProduto>();
                prodsServer = Arrays.asList(gson.fromJson(jsonStr, TProduto[].class));
				
                db.removeAllProdutos();
                
                if (!db.addProdutos(prodsServer))
                	return false;
                
                //ir buscar todos do server
                //ir buscar todos da bd
                //apagar todos da bd e inserir todos do server?
                
                
            }catch(Exception ex)
            {
            	ret = false;
            }
        }else{
        	ret = false;
        }

        return ret;
    }
	
	public boolean SyncPlaces()
	{
		boolean ret = true;
		List<TLocal> locaisServer, locaisDB, locaisToAddDB, locaisToAddServer;
		HashMap <String,String> data = session.getUserDetails();

        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(data.get(session.KEY_SERVER)+"/Locais/", ServiceHandler.GET);
        
        if (jsonStr.compareTo("400")!=0 || jsonStr.compareTo("404")!=0 || jsonStr.compareTo("409")!=0 || jsonStr.compareTo("500")!=0)
        {
            try{
                locaisServer = new ArrayList<TLocal>();
                locaisServer = Arrays.asList(gson.fromJson(jsonStr, TLocal[].class));
				
                locaisDB = db.getAllLocais();
                
                locaisToAddDB = cropLocais(locaisServer,locaisDB);
                
                locaisToAddServer = cropLocais(locaisDB,locaisServer);
                
                //ver que locais nao existem na db, e que locais nao existem no servidor.
                //adicionar à db local, e adicionar ao servidor remoto os que faltam.
                
//                if (!db(locaisServer))
//                	return false;
                
                //ir buscar todos do server
                //ir buscar todos da bd
                //apagar todos da bd e inserir todos do server?
                
                
            }catch(Exception ex)
            {
            	ret = false;
            }
        }else{
        	ret = false;
        }

        return ret;
    }
	
	public String SyncClients()
	{
 
		String ret="";
		HashMap <String,String> data = session.getUserDetails();

        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(data.get(session.KEY_SERVER)+"/Utilizadores/"+session.KEY_USERID, ServiceHandler.GET);
        
        if (jsonStr.compareTo("400")!=0 || jsonStr.compareTo("404")!=0 || jsonStr.compareTo("409")!=0 || jsonStr.compareTo("500")!=0)
        {

            try{
            	TUtilizador u = null;
            	Gson gson = new GsonBuilder().create();
                u = gson.fromJson(jsonStr, TUtilizador.class);
    			
            }catch(Exception ex)
            {
            	return "500";
            }
        }else{
        	ret = jsonStr;
        }

        return ret;
    }
	
	public String SyncTranportationGuides()
	{
 
		String ret="";
		HashMap <String,String> data = session.getUserDetails();

        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(data.get(session.KEY_SERVER)+"/Utilizadores/"+session.KEY_USERID, ServiceHandler.GET);
        
        if (jsonStr.compareTo("400")!=0 || jsonStr.compareTo("404")!=0 || jsonStr.compareTo("409")!=0 || jsonStr.compareTo("500")!=0)
        {

            try{
            	TUtilizador u = null;
            	Gson gson = new GsonBuilder().create();
                u = gson.fromJson(jsonStr, TUtilizador.class);
    			
            }catch(Exception ex)
            {
            	return "500";
            }
        }else{
        	ret = jsonStr;
        }

        return ret;
    }

	public List<TLocal> cropLocais (List<TLocal> list1, List<TLocal> list2)
	{
		List<TLocal> ret = new ArrayList<TLocal>();
		
		//compare list elements.
		return ret;
	}
	
}
