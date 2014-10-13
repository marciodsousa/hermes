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
import com.hermes.hermes.db.ClienteDBManager;
import com.hermes.hermes.model.TCliente;
import com.hermes.hermes.model.TLocal;

public class ClienteController {
	private ServiceHandler sh;
	private Gson gson = new GsonBuilder().create();
	private ClienteDBManager db;
	private SessionManager session;

	public ClienteController(Context ctx) {
		sh = new ServiceHandler();
		session = SessionManager.getInstance(ctx);
		db = ClienteDBManager.getInstance(ctx);
	}

	public boolean ImportClientes() {
		boolean ret = true;
		List<TCliente> clisServ, clisDB, clisToServRaw;

		// get server and db
		clisServ = getClientesServer();
		clisDB = db.getAllClientes();

		// se as tabela estiver vazia, adicionar todos os registos e parar
		// execução
		if (clisDB.size() == 0)
			return db.addClientes(clisServ);

		// obter itens que existem na DB local, mas não existem no servidor.
		clisToServRaw = TCliente.diff(clisDB, clisServ);

		// verificar quais os produtos inexistentes no servidor que foram
		// apagados (para não inserir itens apagados)
		for (TCliente cli : clisToServRaw) {
			db.removeClienteById(cli.getIdCliente());
		}

		// set db
		if (!db.addClientes(clisServ)) {
			ret = false;
		}

		return ret;
	}

	public List<TCliente> getClientesServer() {
		List<TCliente> ret = new ArrayList<TCliente>();
		HashMap<String, String> data = session.getUserDetails();

		// Making a request to url and getting response
		String jsonStr = sh.makeServiceCall(data.get(session.KEY_SERVER)
				+ "/Clientes/", ServiceHandler.GET);

		if (jsonStr.compareTo("400") != 0 && jsonStr.compareTo("404") != 0
				&& jsonStr.compareTo("409") != 0
				&& jsonStr.compareTo("500") != 0) {
			try {
				ret = Arrays.asList(gson.fromJson(jsonStr, TCliente[].class));

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return ret;
	}
	
	public List<TCliente> getAllActiveClients() {
		List<TCliente> ret = new ArrayList<TCliente>();
		List<TCliente> allClis = db.getAllClientes();
		
		for(TCliente cli: allClis)
		{
			if (cli.getEstado()==0)
			{
				ret.add(cli);
			}
		}
		return ret;
	}

}
