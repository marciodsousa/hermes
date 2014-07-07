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
	
	public boolean syncAllData(SessionManager session)
	{
		boolean ret = false;
		
		if(SyncEmpresa())
		{
			if(SyncLicenca())
			{
				if(SyncUtilizador())
				{
					if(SyncProdutos())
					{
						if(SyncLocais())
						{
							if(SyncClientes())
							{
								//if(SyncGuias())
								//{
									ret=true;
								//}
							}
						}
					}
				}
			}
		}

        return ret;
    }
	
	public boolean SyncLicenca()
	{
		boolean ret = false;
		HashMap <String,String> data = session.getUserDetails();
		
		TLicenca lic = db.getLicenca();
		
        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(data.get(session.KEY_SERVER)+"/Licencas/"+lic.getIdLicenca(), ServiceHandler.GET);
        
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
	
	public boolean SyncEmpresa()
	{
		//get server�
		TEmpresa e = getEmpresaServer();
		 
		//get db
		if(db.getEmpresaById(e.getIdEmpresa())!=null)
		{
			return db.updateEmpresa(e);
		}else
		{
			return db.addEmpresa(e);
		}
			
		 
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
		boolean ret = true;
		List<TProduto> prodsServ, prodsDB, prodsToServRaw, prodsToDelete, prodsToDB, prodsToServ;
		
		//get server and db
		prodsServ = getProdutosServer();
		prodsDB = db.getAllProdutos();
		
		//se as tabela estiver vazia, adicionar todos os registos e parar execu��o
		if(prodsDB.size()==0)
			return db.addProdutos(prodsServ);
		
		//obter itens que existem na DB local, mas n�o existem no servidor.
		prodsToServRaw = TProduto.diff(prodsDB, prodsServ);
		
		prodsToDelete = new ArrayList<TProduto>();
		
		//verificar quais os produtos inexistentes no servidor que foram apagados (para n�o inserir itens apagados)
		for (TProduto prod : prodsToServRaw) {
    		if (prod.getIdProduto() > 1)
    		{
    			prodsToDelete.add(prod);
    		}
        }
		
		//apagar da DB os itens que foram apagados no servidor
		for (TProduto prod : prodsToDelete) {
    		db.removeProdutoById(prod.getIdProduto());
        }
		
		prodsToDB = TProduto.diff(prodsServ, prodsDB);
		
		//obter itens a enviar para o servidor, retirando os que j� l� tinham sido apagados.
		prodsToServ = TProduto.diff(prodsToServRaw, prodsToDelete);
		
		//set db
		if(prodsToDB.size()>0)
		{
			if(!db.addProdutos(prodsToDB))
			{
				ret = false;
			}
		}
		
		//set server
		if(prodsToServ.size()>0)
		{
			if(!addProdutosServer(prodsToServ))
			{
				ret = false;
			}
		}
		
		db.removeProdutoById(0);
		
		return ret;
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
		String json, jsonStr;
		TProduto p;
		int flag=0;
		
		HashMap <String,String> data = session.getUserDetails();

		for (TProduto prod : prods) {
			json = gson.toJson(prod);
			
	        // Making a request to url and getting response
	        jsonStr = sh.makeServiceCallJS(data.get(session.KEY_SERVER)+"/Produtos/", ServiceHandler.POST, json);
	        
	        if (jsonStr.compareTo("400")!=0 && jsonStr.compareTo("404")!=0 && jsonStr.compareTo("409")!=0 && jsonStr.compareTo("500")!=0)
	        {
                p = gson.fromJson(jsonStr, TProduto.class);
                 
                if(db.getProdutoById(p.getIdProduto())==null)
            	{
                	if(db.addProduto(p))
                		flag++;
            	}
	        }
        }
		if(flag==prods.size())
			ret=true;
			
        return ret;
	}
	
	public boolean SyncLocais()
	{
		boolean ret = true;
		List<TLocal> locsServ, locsDB, locsToServRaw, locsToDelete, locsToDB, locsToServ;
		
		//get server and db
		locsServ = getLocaisServer();
		locsDB = db.getAllLocais();
		
		//se as tabela estiver vazia, adicionar todos os registos e parar execu��o
		if(locsDB.size()==0)
			return db.addLocais(locsServ);
		
		//obter itens que existem na DB local, mas n�o existem no servidor.
		locsToServRaw = TLocal.diff(locsDB, locsServ);
		
		locsToDelete = new ArrayList<TLocal>();
		
		//verificar quais os produtos inexistentes no servidor que foram apagados (para n�o inserir itens apagados)
		for (TLocal loc : locsToServRaw) {
    		if (loc.getIdLocal() > 1)
    		{
    			locsToDelete.add(loc);
    		}
        }
		
		//apagar da DB os itens que foram apagados no servidor
		for (TLocal loc : locsToDelete) {
    		db.removeLocalById(loc.getIdLocal());
        }
		
		locsToDB = TLocal.diff(locsServ, locsDB);
		
		//obter itens a enviar para o servidor, retirando os que j� l� tinham sido apagados.
		locsToServ = TLocal.diff(locsToServRaw, locsToDelete);
		
		//set db
		if(locsToDB.size()>0)
		{
			if(!db.addLocais(locsToDB))
			{
				ret = false;
			}
		}
		
		//set server
		if(locsToServ.size()>0)
		{
			if(!addLocaisServer(locsToServ))
			{
				ret = false;
			}
		}
		
		db.removeLocalById(0);
		
		return ret;
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
		String json, jsonStr;
		TLocal l;
		int flag=0;
		
		HashMap <String,String> data = session.getUserDetails();

		for (TLocal loc : locs) {
			json = gson.toJson(loc);
			
	        // Making a request to url and getting response
	        jsonStr = sh.makeServiceCallJS(data.get(session.KEY_SERVER)+"/Locais/", ServiceHandler.POST, json);
	        
	        if (jsonStr.compareTo("400")!=0 && jsonStr.compareTo("404")!=0 && jsonStr.compareTo("409")!=0 && jsonStr.compareTo("500")!=0)
	        {
                l = gson.fromJson(jsonStr, TLocal.class);
                 
                if(db.getProdutoById(l.getIdLocal())==null)
                {
                	if(db.addLocal(l))
            			flag++;
                }
	        }
        }
		
		if(flag==locs.size())
			ret=true;
		
        return ret;
	}
	
	public boolean SyncClientes()
	{
		boolean ret = true;
		List<TCliente> clisServ, clisDB, clisToServRaw, clisToDelete, clisToDB, clisToServ;
		
		//get server and db
		clisServ = getClientesServer();
		clisDB = db.getAllClientes();
		
		//se as tabela estiver vazia, adicionar todos os registos e parar execu��o
		if(clisDB.size()==0)
			return db.addClientes(clisServ);
		
		//obter itens que existem na DB clial, mas n�o existem no servidor.
		clisToServRaw = TCliente.diff(clisDB, clisServ);
		
		clisToDelete = new ArrayList<TCliente>();
		
		//verificar quais os produtos inexistentes no servidor que foram apagados (para n�o inserir itens apagados)
		for (TCliente cli : clisToServRaw) {
    		if (cli.getIdCliente() > 1)
    		{
    			clisToDelete.add(cli);
    		}
        }
		
		//apagar da DB os itens que foram apagados no servidor
		for (TCliente cli : clisToDelete) {
    		db.removeClienteById(cli.getIdCliente());
        }
		
		clisToDB = TCliente.diff(clisServ, clisDB);
		
		//obter itens a enviar para o servidor, retirando os que j� l� tinham sido apagados.
		clisToServ = TCliente.diff(clisToServRaw, clisToDelete);
		
		//set db
		if(clisToDB.size()>0)
		{
			if(!db.addClientes(clisToDB))
			{
				ret = false;
			}
		}
		
		//set server
		if(clisToServ.size()>0)
		{
			if(!addClientesServer(clisToServ))
			{
				ret = false;
			}
		}
		
		db.removeClienteById(0);
		
		return ret;
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
	
	public boolean addClientesServer(List<TCliente> clis)
	{
		boolean ret = false;
		String json, jsonStr;
		TCliente c;
		int flag=0;
		
		HashMap <String,String> data = session.getUserDetails();

		for (TCliente cli : clis) {
			json = gson.toJson(cli);
			
	        // Making a request to url and getting response
	        jsonStr = sh.makeServiceCallJS(data.get(session.KEY_SERVER)+"/Clientes/", ServiceHandler.POST, json);
	        
	        if (jsonStr.compareTo("400")!=0 && jsonStr.compareTo("404")!=0 && jsonStr.compareTo("409")!=0 && jsonStr.compareTo("500")!=0)
	        {
                c = gson.fromJson(jsonStr, TCliente.class);
                 
                if(db.getClienteById(c.getIdCliente())==null)
        		{
                	if(db.addCliente(c))
                		flag++;
        		}
	        }
        }
		
		if(flag==clis.size())
			ret=true;
		
        return ret;
	}
	
	public boolean SyncGuias()
	{
		//get server and db
		List<TGuiaTransporte> guiasServ = getGuiasServer();
		List<TGuiaTransporte> guiasDB = db.getAllGuiasTransporte();
		
		//se as tabela estiver vazia, adicionar todos os registos e parar execu��o
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
