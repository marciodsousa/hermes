package com.hermes.hermes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hermes.hermes.Model.*;

import android.content.Context;
import com.hermes.hermes.db.DatabaseManager;


public class DataController {

	private ServiceHandler sh;
	private Gson gson = new GsonBuilder().create();
	private DatabaseManager db;
	private SessionManager session;
    
    public DataController(Context ctx)
    {
    	sh = new ServiceHandler();
    	session = SessionManager.getInstance(ctx);
    	db = DatabaseManager.getInstance(ctx);
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
        
        if (jsonStr.compareTo("400")!=0 && jsonStr.compareTo("404")!=0 && jsonStr.compareTo("409")!=0 && jsonStr.compareTo("500")!=0)
        {
        	TLicenca l = null;
            
            try{
            	Gson gson = new GsonBuilder().create();
                l = gson.fromJson(jsonStr, TLicenca.class);
                
                db.addLicenca(l);
                
                ret = l.getCodLicenca();
    			
            }catch(Exception ex)
            {
            	ex.printStackTrace();
            	return "500";
            }
        }else{
        	ret = jsonStr;
        }

        return ret;
      
    }
	
	public String syncAllData(SessionManager session)
	{
		SyncEmpresa();
		SyncLicenca();
		SyncUtilizador();
		
		SyncProdutos();
		SyncLocais();
		SyncClientes();
		//SyncGuias();
		
		String ret="";

        return ret;
    }
	
	public boolean SyncLicenca()
	{
		boolean ret = false;
		HashMap <String,String> data = session.getUserDetails();
		
		TLicenca lic = db.getLicenca();
		
        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(data.get(session.KEY_SERVER)+"/Licenca/"+lic.getIdLicenca(), ServiceHandler.GET);
        
        if (jsonStr.compareTo("400")==0 || jsonStr.compareTo("404")==0 || jsonStr.compareTo("409")==0 || jsonStr.compareTo("500")==0)
        {
        	ret = false;      
        }else{
        	 try{
        		TLicenca l = null;
                l = gson.fromJson(jsonStr, TLicenca.class);
                 
                if(db.getLicencaById(l.getIdLicenca())!=null)
            		return db.updateLicenca(l);
                else
            		return db.addLicenca(l);

             }catch(Exception ex)
             {
             	ret = false;
             }
        }
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
            		return db.updateUtilizador(u);
                else
            		return db.addUtilizador(u);

             }catch(Exception ex)
             {
             	ret = false;
             }
        }
        return ret;
    }
	
	public boolean SyncEmpresa()
	{
		//get server´
		TEmpresa e = getEmpresaServer();
		 
		//get db
		if(db.getEmpresaById(e.getIdEmpresa())!=null)
			return db.updateEmpresa(e);
		else
			return db.addEmpresa(e);
		 
		//diff (sbserver)
		//diff(serverdb)
		 
		//set server
		//set db
    }
	
	public TEmpresa getEmpresaServer()
	{
		TEmpresa ret = null;
		HashMap <String,String> data = session.getUserDetails();
		
        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(data.get(session.KEY_SERVER)+"/Empresas/", ServiceHandler.GET);
        
        if (jsonStr.compareTo("400")!=0 && jsonStr.compareTo("404")!=0 && jsonStr.compareTo("409")!=0 && jsonStr.compareTo("500")!=0)
        {
        	try{
             	ret = gson.fromJson(jsonStr, TEmpresa.class);
             }catch(Exception ex)
             {
             	ex.printStackTrace();
             }  
        }
        return ret;
	}
	
	public boolean SyncProdutos()
	{
		//get server and db
		List<TProduto> prodsServ = getProdutosServer();
		List<TProduto> prodsDB = db.getAllProdutos();
		
		//se as tabela estiver vazia, adicionar todos os registos e parar execução
		if(prodsDB.size()==0)
			return db.addProdutos(prodsServ);
		
		//diff(db,server)
		List<TProduto> prodsToServ = TProduto.diff(prodsDB, prodsServ);
		List<TProduto> prodsToDB = TProduto.diff(prodsServ, prodsDB);
		
		//get db
		if(db.addProdutos(prodsToDB))
			return addProdutosServer(prodsToServ);
		else
			return false;
    }
	
	public List<TProduto> getProdutosServer()
	{
		List<TProduto> ret = new ArrayList<TProduto>();
		HashMap <String,String> data = session.getUserDetails();

        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(data.get(session.KEY_SERVER)+"/Produtos/", ServiceHandler.GET);
        
        if (jsonStr.compareTo("400")!=0 && jsonStr.compareTo("404")!=0 && jsonStr.compareTo("409")!=0 && jsonStr.compareTo("500")!=0)
        {
            try{        
            	ret = Arrays.asList(gson.fromJson(jsonStr, TProduto[].class));

            }catch(Exception ex)
            {
            	ex.printStackTrace();
            }
        }
        return ret;
	}
	
	public boolean addProdutosServer(List<TProduto> prods)
	{
		boolean ret = false;
		
		HashMap <String,String> data = session.getUserDetails();

		String json = gson.toJson(prods);
		
        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCallJS(data.get(session.KEY_SERVER)+"/Produtos/", ServiceHandler.POST, json);
        
        if (jsonStr.compareTo("400")!=0 && jsonStr.compareTo("404")!=0 && jsonStr.compareTo("409")!=0 && jsonStr.compareTo("500")!=0)
        {
            ret = true;
        }
        return ret;
	}
	
	public boolean SyncLocais()
	{
		//get server and db
		List<TLocal> locsServ = getLocaisServer();
		List<TLocal> locsDB = db.getAllLocais();
		
		//se as tabela estiver vazia, adicionar todos os registos e parar execução
		if(locsDB.size()==0)
			return db.addLocais(locsServ);
		
		//diff(db,server)
		List<TLocal> locsToServ = TLocal.diff(locsDB, locsServ);
		List<TLocal> locsToDB = TLocal.diff(locsServ, locsDB);
		
		//get db
		if(db.addLocais(locsToDB))
			return addLocaisServer(locsToServ);
		else
			return false;
    }
	
	public List<TLocal> getLocaisServer()
	{
		List<TLocal> ret = new ArrayList<TLocal>();
		HashMap <String,String> data = session.getUserDetails();

        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(data.get(session.KEY_SERVER)+"/Locais/", ServiceHandler.GET);
        
        if (jsonStr.compareTo("400")!=0 && jsonStr.compareTo("404")!=0 && jsonStr.compareTo("409")!=0 && jsonStr.compareTo("500")!=0)
        {
            try{        
            	ret = Arrays.asList(gson.fromJson(jsonStr, TLocal[].class));

            }catch(Exception ex)
            {
            	ex.printStackTrace();
            }
        }
        return ret;
	}
	
	public boolean addLocaisServer(List<TLocal> locs)
	{
		boolean ret = false;
		
		HashMap <String,String> data = session.getUserDetails();

		String json = gson.toJson(locs);
		
        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCallJS(data.get(session.KEY_SERVER)+"/Locais/", ServiceHandler.POST, json);
        
        if (jsonStr.compareTo("400")!=0 && jsonStr.compareTo("404")!=0 && jsonStr.compareTo("409")!=0 && jsonStr.compareTo("500")!=0)
        {
            ret = true;
        }
        return ret;
	}
	
	public boolean SyncClientes()
	{
		//get server and db
		List<TCliente> clisServ = getClientesServer();
		List<TCliente> clisDB = db.getAllClientes();
		
		//se as tabela estiver vazia, adicionar todos os registos e parar execução
		if(clisDB.size()==0)
			return db.addClientes(clisServ);
		
		//diff(db,server)
		List<TCliente> clisToServ = TCliente.diff(clisDB, clisServ);
		List<TCliente> clisToDB = TCliente.diff(clisServ, clisDB);
		
		//get db
		if(db.addClientes(clisToDB))
			return addClientesServer(clisToServ);
		else
			return false;
    }
	
	public List<TCliente> getClientesServer()
	{
		List<TCliente> ret = new ArrayList<TCliente>();
		HashMap <String,String> data = session.getUserDetails();

        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(data.get(session.KEY_SERVER)+"/Clientes/", ServiceHandler.GET);
        
        if (jsonStr.compareTo("400")!=0 && jsonStr.compareTo("404")!=0 && jsonStr.compareTo("409")!=0 && jsonStr.compareTo("500")!=0)
        {
            try{        
            	ret = Arrays.asList(gson.fromJson(jsonStr, TCliente[].class));

            }catch(Exception ex)
            {
            	ex.printStackTrace();
            }
        }
        return ret;
	}
	
	public boolean addClientesServer(List<TCliente> locs)
	{
		boolean ret = false;
		
		HashMap <String,String> data = session.getUserDetails();

		String json = gson.toJson(locs);
		
        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCallJS(data.get(session.KEY_SERVER)+"/Clientes/", ServiceHandler.POST, json);
        
        if (jsonStr.compareTo("400")!=0 && jsonStr.compareTo("404")!=0 && jsonStr.compareTo("409")!=0 && jsonStr.compareTo("500")!=0)
        {
            ret = true;
        }
        return ret;
	}
	
	public boolean SyncGuias()
	{
		//get server and db
		List<TGuiaTransporte> guiasServ = getGuiasServer();
		List<TGuiaTransporte> guiasDB = db.getAllGuiasTransporte();
		
		//se as tabela estiver vazia, adicionar todos os registos e parar execução
		if(guiasDB.size()==0)
			return db.addGuiasTransporte(guiasServ);
		
		//diff(db,server)
		List<TGuiaTransporte> guiasToServ = TGuiaTransporte.diff(guiasDB, guiasServ);
		List<TGuiaTransporte> guiasToDB = TGuiaTransporte.diff(guiasServ, guiasDB);
		
		//get db
		if(db.addGuiasTransporte(guiasToDB))
			return addGuiasServer(guiasToServ);
		else
			return false;
    }
	
	public List<TGuiaTransporte> getGuiasServer()
	{
		List<TGuiaTransporte> ret = new ArrayList<TGuiaTransporte>();
		HashMap <String,String> data = session.getUserDetails();

        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(data.get(session.KEY_SERVER)+"/GuiasTransportes/"+ data.get(session.KEY_USERID), ServiceHandler.GET);
        
        if (jsonStr.compareTo("400")!=0 && jsonStr.compareTo("404")!=0 && jsonStr.compareTo("409")!=0 && jsonStr.compareTo("500")!=0)
        {
            try{        
            	ret = Arrays.asList(gson.fromJson(jsonStr, TGuiaTransporte[].class));

            }catch(Exception ex)
            {
            	ex.printStackTrace();
            }
        }
        return ret;
	}
	
	public boolean addGuiasServer(List<TGuiaTransporte> prods)
	{
		boolean ret = false;
		
		HashMap <String,String> data = session.getUserDetails();

		String json = gson.toJson(prods);
		
        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCallJS(data.get(session.KEY_SERVER)+"/Produtos/", ServiceHandler.POST, json);
        
        if (jsonStr.compareTo("400")!=0 && jsonStr.compareTo("404")!=0 && jsonStr.compareTo("409")!=0 && jsonStr.compareTo("500")!=0)
        {
            ret = true;
        }
        return ret;
	}
	
}
