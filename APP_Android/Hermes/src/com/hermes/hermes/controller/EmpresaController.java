package com.hermes.hermes.controller;

import java.util.HashMap;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hermes.hermes.ServiceHandler;
import com.hermes.hermes.SessionManager;
import com.hermes.hermes.db.EmpresaDBManager;
import com.hermes.hermes.model.TEmpresa;

public class EmpresaController {
	private ServiceHandler sh;
	private Gson gson = new GsonBuilder().create();
	private EmpresaDBManager db;
	private SessionManager session;

	public EmpresaController(Context ctx) {
		sh = new ServiceHandler();
		session = SessionManager.getInstance(ctx);
		db = EmpresaDBManager.getInstance(ctx);
	}

	public boolean SyncEmpresa() {
		// get server´
		TEmpresa e = getEmpresaServer();

		// get db
		if (db.getEmpresaById(e.getIdEmpresa()) != null) {
			return db.updateEmpresa(e);
		} else {
			return db.addEmpresa(e);
		}

		// diff (sbserver)
		// diff(serverdb)

		// set server
		// set db
	}

	public TEmpresa getEmpresaServer() {
		TEmpresa ret = null;
		HashMap<String, String> data = session.getUserDetails();

		// Making a request to url and getting response
		String jsonStr = sh.makeServiceCall(data.get(session.KEY_SERVER)
				+ "/Empresas/", ServiceHandler.GET);

		if (jsonStr.compareTo("400") != 0 && jsonStr.compareTo("404") != 0
				&& jsonStr.compareTo("409") != 0
				&& jsonStr.compareTo("500") != 0) {
			try {
				ret = gson.fromJson(jsonStr, TEmpresa.class);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return ret;
	}
}
