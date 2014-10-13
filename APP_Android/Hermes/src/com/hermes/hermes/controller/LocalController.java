package com.hermes.hermes.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hermes.hermes.ServiceHandler;
import com.hermes.hermes.SessionManager;
import com.hermes.hermes.db.LocalDBManager;
import com.hermes.hermes.model.TLocal;
import com.hermes.hermes.model.TProduto;

public class LocalController {

	private ServiceHandler sh;
	private Gson gson = new GsonBuilder().create();
	private LocalDBManager db;
	private SessionManager session;

	public LocalController(Context ctx) {
		sh = new ServiceHandler();
		session = SessionManager.getInstance(ctx);
		db = LocalDBManager.getInstance(ctx);
	}
	
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

	public List<TLocal> getAllActivePlaces() {
		List<TLocal> ret = new ArrayList<TLocal>();
		List<TLocal> allLocs = db.getAllLocais();
		
		for(TLocal loc: allLocs)
		{
			if (loc.getEstado()==0)
			{
				ret.add(loc);
			}
		}
		return ret;
	}
}
