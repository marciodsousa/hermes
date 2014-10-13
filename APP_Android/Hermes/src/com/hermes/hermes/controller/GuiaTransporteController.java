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
import com.hermes.hermes.db.GuiaTransporteDBManager;
import com.hermes.hermes.model.TGuiaTransporte;

public class GuiaTransporteController {

	private ServiceHandler sh;
	private Gson gson = new GsonBuilder().create();
	private GuiaTransporteDBManager db;
	private SessionManager session;

	public GuiaTransporteController(Context ctx) {
		sh = new ServiceHandler();
		session = SessionManager.getInstance(ctx);
		db = GuiaTransporteDBManager.getInstance(ctx);
	}

	public boolean ImportGuias() {

		boolean ret = true;
		List<TGuiaTransporte> guiasServ, guiasDB, guiasToServRaw;

		// get server and db
		guiasServ = getGuiaTransportesServer();
		guiasDB = db.getAllGuiasTransporte();

		// se as tabela estiver vazia, adicionar todos os registos e parar
		// execução
		if (guiasDB.size() == 0)
			return db.addGuiasTransporte(guiasServ);

		// set db
		if (!db.addGuiasTransporte(guiasServ)) {
			ret = false;
		}

		return ret;
	}

	public boolean ExportGuias() {
		boolean ret = true;
		List<TGuiaTransporte> guiasDB, guiasToServ;

		// get server and db
		guiasDB = db.getAllGuiasTransporte();

		guiasToServ = new ArrayList<TGuiaTransporte>();

		for (TGuiaTransporte guia : guiasDB) {
			if (guia.getIdGuia() < 0)
				guiasToServ.add(guia);
		}

		if (!addGuiasServer(guiasToServ)) {
			ret = false;
		}

		for (TGuiaTransporte guia : guiasToServ) {
			db.removeGuiaTransporteById(guia.getIdGuia());
		}

		if (!ImportGuias())
			ret = false;

		return ret;
	}

	public List<TGuiaTransporte> getGuiaTransportesServer() {
		List<TGuiaTransporte> ret = new ArrayList<TGuiaTransporte>();
		HashMap<String, String> data = session.getUserDetails();

		// Making a request to url and getting response
		String jsonStr = sh.makeServiceCall(data.get(session.KEY_SERVER)
				+ "/GuiasTransportes/" + data.get(session.KEY_USERID),
				ServiceHandler.GET);

		if (jsonStr.compareTo("400") != 0 && jsonStr.compareTo("404") != 0
				&& jsonStr.compareTo("409") != 0
				&& jsonStr.compareTo("500") != 0) {
			try {
				ret = Arrays.asList(gson.fromJson(jsonStr,
						TGuiaTransporte[].class));

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return ret;
	}

	public boolean addGuiasServer(List<TGuiaTransporte> guias) {
		boolean ret = false;
		String json, jsonStr;
		TGuiaTransporte c;
		int flag = 0;

		HashMap<String, String> data = session.getUserDetails();

		for (TGuiaTransporte guia : guias) {
			json = gson.toJson(guia);

			// Making a request to url and getting response
			jsonStr = sh.makeServiceCallJS(data.get(session.KEY_SERVER)
					+ "/GuiasTransportes/", ServiceHandler.POST, json);

			if (jsonStr.compareTo("400") != 0 && jsonStr.compareTo("404") != 0
					&& jsonStr.compareTo("409") != 0
					&& jsonStr.compareTo("500") != 0) {
				c = gson.fromJson(jsonStr, TGuiaTransporte.class);

				if (db.getGuiaTransporteById(c.getIdGuia()) == null) {
					if (db.addGuiaTransporte(c))
						flag++;
				}
			}
		}

		if (flag == guias.size())
			ret = true;

		return ret;
	}

	public boolean saveGuiaTransporte(TGuiaTransporte guia) {
		boolean ret = false;

		int id = db.getNewGuiaID();

		guia.setIdGuia(id);

		if (db.addGuiaTransporte(guia))
			ret = true;

		return ret;
	}
}
