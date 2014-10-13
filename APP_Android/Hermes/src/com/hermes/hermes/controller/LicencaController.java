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
import com.hermes.hermes.db.LicencaDBManager;
import com.hermes.hermes.model.TLicenca;

public class LicencaController {

	private ServiceHandler sh;
	private Gson gson = new GsonBuilder().create();
	private LicencaDBManager db;
	private SessionManager session;

	public LicencaController(Context ctx) {
		sh = new ServiceHandler();
		session = SessionManager.getInstance(ctx);
		db = LicencaDBManager.getInstance(ctx);
	}

	public String registerDevice(String server, String imei) {
		String ret = "";

		List<NameValuePair> list = new ArrayList<NameValuePair>();

		list.add(new BasicNameValuePair("imei", imei));
		list.add(new BasicNameValuePair("estado", "0"));

		// Making a request to url and getting response
		String jsonStr = sh.makeServiceCall(server + "/Licencas",
				ServiceHandler.POST, list);

		if (jsonStr.compareTo("400") != 0 && jsonStr.compareTo("404") != 0
				&& jsonStr.compareTo("409") != 0
				&& jsonStr.compareTo("500") != 0) {
			TLicenca l = null;

			try {
				Gson gson = new GsonBuilder().create();
				l = gson.fromJson(jsonStr, TLicenca.class);

				db.addLicenca(l);

				ret = l.getCodLicenca();

			} catch (Exception ex) {
				ex.printStackTrace();
				return "500";
			}
		} else {
			ret = jsonStr;
		}

		return ret;

	}

	public boolean SyncLicenca() {
		boolean ret = false;
		HashMap<String, String> data = session.getUserDetails();

		TLicenca lic = db.getLicenca();

		// Making a request to url and getting response
		String jsonStr = sh.makeServiceCall(data.get(session.KEY_SERVER)
				+ "/Licencas/" + lic.getIdLicenca(), ServiceHandler.GET);

		if (jsonStr.compareTo("400") == 0 || jsonStr.compareTo("404") == 0
				|| jsonStr.compareTo("409") == 0
				|| jsonStr.compareTo("500") == 0) {
			ret = false;
		} else {
			try {
				TLicenca l = null;
				l = gson.fromJson(jsonStr, TLicenca.class);

				if (db.getLicencaById(l.getIdLicenca()) != null)
					return db.updateLicenca(l);
				else
					return db.addLicenca(l);

			} catch (Exception ex) {
				ret = false;
			}
		}
		return ret;
	}
}
