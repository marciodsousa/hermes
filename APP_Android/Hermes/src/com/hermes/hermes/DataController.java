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
	
	public boolean syncAllData()
	{
		boolean ret = false;
		
		if(SyncEmpresa())
		{
			if(SyncLicenca())
			{
				if(SyncUtilizador())
				{
					if(ImportProdutos())
					{
						if(ImportLocais())
						{
							if(ImportClientes())
							{
								if(ImportGuias())
								{
									ret=true;
								}
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
		//get server´
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
	
	public boolean ImportProdutos()
	{
		boolean ret = true;
		List<TProduto> prodsServ, prodsDB, prodsToServRaw;
		
		//get server and db
		prodsServ = getProdutosServer();
		prodsDB = db.getAllProdutos();
		
		//se as tabela estiver vazia, adicionar todos os registos e parar execução
		if(prodsDB.size()==0)
			return db.addProdutos(prodsServ);
		
		//obter itens que existem na DB local, mas não existem no servidor.
		prodsToServRaw = TProduto.diff(prodsDB, prodsServ);
		
		
		//verificar quais os produtos inexistentes no servidor que foram apagados (para não inserir itens apagados)
		for (TProduto prod : prodsToServRaw) {
			db.removeProdutoById(prod.getIdProduto());
        }
		
		//set db
		if(!db.addProdutos(prodsServ))
		{
			ret = false;
		}
		
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
	
	/*public boolean addProdutosServer(List<TProduto> prods)
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
	}*/
	
	public boolean ImportLocais()
	{
		boolean ret = true;
		List<TLocal> locsServ, locsDB, locsToServRaw;
		
		//get server and db
		locsServ = getLocaisServer();
		locsDB = db.getAllLocais();
		
		//se as tabela estiver vazia, adicionar todos os registos e parar execução
		if(locsDB.size()==0)
			return db.addLocais(locsServ);
		
		//obter itens que existem na DB local, mas não existem no servidor.
		locsToServRaw = TLocal.diff(locsDB, locsServ);
		
		
		//verificar quais os produtos inexistentes no servidor que foram apagados (para não inserir itens apagados)
		for (TLocal loc : locsToServRaw) {
			db.removeLocalById(loc.getIdLocal());
        }
		
		//set db
		if(!db.addLocais(locsServ))
		{
			ret = false;
		}
		
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
	
	/*public boolean addLocaisServer(List<TLocal> locs)
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
	}*/
	
	public boolean ImportClientes()
	{
		boolean ret = true;
		List<TCliente> clisServ, clisDB, clisToServRaw;
		
		//get server and db
		clisServ = getClientesServer();
		clisDB = db.getAllClientes();
		
		//se as tabela estiver vazia, adicionar todos os registos e parar execução
		if(clisDB.size()==0)
			return db.addClientes(clisServ);
		
		//obter itens que existem na DB local, mas não existem no servidor.
		clisToServRaw = TCliente.diff(clisDB, clisServ);
		
		
		//verificar quais os produtos inexistentes no servidor que foram apagados (para não inserir itens apagados)
		for (TCliente cli : clisToServRaw) {
			db.removeLocalById(cli.getIdCliente());
        }
		
		//set db
		if(!db.addClientes(clisServ))
		{
			ret = false;
		}
		
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
	
	/*public boolean addClientesServer(List<TCliente> clis)
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
	}*/
	
	public boolean ImportGuias()
	{
		boolean ret = true;
		List<TGuiaTransporte> guiasServ, guiasDB, guiasToServRaw, guiasToDelete, guiasToDB, guiasToServ;
		
		//get server and db
		guiasServ = getGuiaTransportesServer();
		guiasDB = db.getAllGuiasTransporte();
		
		//se as tabela estiver vazia, adicionar todos os registos e parar execução
		if(guiasDB.size()==0)
			return db.addGuiasTransporte(guiasServ);
		
		//obter itens que existem na DB guiaal, mas não existem no servidor.
		guiasToServRaw = TGuiaTransporte.diff(guiasDB, guiasServ);
		
		guiasToDelete = new ArrayList<TGuiaTransporte>();
		
		//verificar quais os produtos inexistentes no servidor que foram apagados (para não inserir itens apagados)
		for (TGuiaTransporte guia : guiasToServRaw) {
    		if (guia.getIdGuia() > 1)
    		{
    			guiasToDelete.add(guia);
    		}
        }
		
		//apagar da DB os itens que foram apagados no servidor
		for (TGuiaTransporte guia : guiasToDelete) {
    		db.removeGuiaTransporteById(guia.getIdGuia());
        }
		
		guiasToDB = TGuiaTransporte.diff(guiasServ, guiasDB);
		
		//obter itens a enviar para o servidor, retirando os que já lá tinham sido apagados.
		guiasToServ = TGuiaTransporte.diff(guiasToServRaw, guiasToDelete);
		
		//set db
		if(guiasToDB.size()>0)
		{
			if(!db.addGuiasTransporte(guiasToDB))
			{
				ret = false;
			}
		}
		
		//set server
		if(guiasToServ.size()>0)
		{
			if(!addGuiasServer(guiasToServ))
			{
				ret = false;
			}
		}
		
		db.removeGuiaTransporteById(0);
		
		return ret;
    }
	
	public boolean ExportGuias()
	{
		boolean ret = true;
		List<TGuiaTransporte> guiasServ, guiasDB, guiasToServRaw, guiasToDelete, guiasToDB, guiasToServ;
		
		//get server and db
		guiasServ = getGuiaTransportesServer();
		guiasDB = db.getAllGuiasTransporte();
		
		//se as tabela estiver vazia, adicionar todos os registos e parar execução
		if(guiasDB.size()==0)
			return db.addGuiasTransporte(guiasServ);
		
		//obter itens que existem na DB guiaal, mas não existem no servidor.
		guiasToServRaw = TGuiaTransporte.diff(guiasDB, guiasServ);
		
		guiasToDelete = new ArrayList<TGuiaTransporte>();
		
		//verificar quais os produtos inexistentes no servidor que foram apagados (para não inserir itens apagados)
		for (TGuiaTransporte guia : guiasToServRaw) {
    		if (guia.getIdGuia() > 1)
    		{
    			guiasToDelete.add(guia);
    		}
        }
		
		//apagar da DB os itens que foram apagados no servidor
		for (TGuiaTransporte guia : guiasToDelete) {
    		db.removeGuiaTransporteById(guia.getIdGuia());
        }
		
		guiasToDB = TGuiaTransporte.diff(guiasServ, guiasDB);
		
		//obter itens a enviar para o servidor, retirando os que já lá tinham sido apagados.
		guiasToServ = TGuiaTransporte.diff(guiasToServRaw, guiasToDelete);
		
		//set db
		if(guiasToDB.size()>0)
		{
			if(!db.addGuiasTransporte(guiasToDB))
			{
				ret = false;
			}
		}
		
		//set server
		if(guiasToServ.size()>0)
		{
			if(!addGuiasServer(guiasToServ))
			{
				ret = false;
			}
		}
		
		db.removeGuiaTransporteById(0);
		
		return ret;
    }
	
	public List<TGuiaTransporte> getGuiaTransportesServer()
	{
		List<TGuiaTransporte> ret = new ArrayList<TGuiaTransporte>();
		HashMap <String,String> data = session.getUserDetails();

        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(data.get(session.KEY_SERVER)+"/GuiasTransportes/"+data.get(session.KEY_USERID), ServiceHandler.GET);
        
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
	
	public boolean addGuiasServer(List<TGuiaTransporte> guias)
	{
		boolean ret = false;
		String json, jsonStr;
		TGuiaTransporte c;
		int flag=0;
		
		HashMap <String,String> data = session.getUserDetails();

		for (TGuiaTransporte guia : guias) {
			json = gson.toJson(guia);
			
	        // Making a request to url and getting response
	        jsonStr = sh.makeServiceCallJS(data.get(session.KEY_SERVER)+"/GuiasTransportes/", ServiceHandler.POST, json);
	        
	        if (jsonStr.compareTo("400")!=0 && jsonStr.compareTo("404")!=0 && jsonStr.compareTo("409")!=0 && jsonStr.compareTo("500")!=0)
	        {
                c = gson.fromJson(jsonStr, TGuiaTransporte.class);
                 
                if(db.getGuiaTransporteById(c.getIdGuia())==null)
        		{
                	if(db.addGuiaTransporte(c))
                		flag++;
        		}
	        }
        }
		
		if(flag==guias.size())
			ret=true;
		
        return ret;
	}
	
	
}
