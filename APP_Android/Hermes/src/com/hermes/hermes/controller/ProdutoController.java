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
import com.hermes.hermes.db.ProdutoDBManager;
import com.hermes.hermes.model.TProduto;

public class ProdutoController {

	private ServiceHandler sh;
	private Gson gson = new GsonBuilder().create();
	private ProdutoDBManager db;
	private SessionManager session;

	public ProdutoController(Context ctx) {
		sh = new ServiceHandler();
		session = SessionManager.getInstance(ctx);
		db = ProdutoDBManager.getInstance(ctx);
	}

	public boolean ImportProdutos() {
		boolean ret = true;
		List<TProduto> prodsServ, prodsDB, prodsToServRaw;

		// get server and db
		prodsServ = getProdutosServer();
		prodsDB = db.getAllProdutos();

		// se as tabela estiver vazia, adicionar todos os registos e parar
		// execução
		if (prodsDB.size() == 0)
			return db.addProdutos(prodsServ);

		// obter itens que existem na DB local, mas não existem no servidor.
		prodsToServRaw = TProduto.diff(prodsDB, prodsServ);

		// verificar quais os produtos inexistentes no servidor que foram
		// apagados (para não inserir itens apagados)
		for (TProduto prod : prodsToServRaw) {
			db.removeProdutoById(prod.getIdProduto());
		}

		// set db
		if (!db.addProdutos(prodsServ)) {
			ret = false;
		}

		return ret;
	}

	public List<TProduto> getProdutosServer() {
		List<TProduto> ret = new ArrayList<TProduto>();
		HashMap<String, String> data = session.getUserDetails();

		// Making a request to url and getting response
		String jsonStr = sh.makeServiceCall(data.get(session.KEY_SERVER)
				+ "/Produtos/", ServiceHandler.GET);

		if (jsonStr.compareTo("400") != 0 && jsonStr.compareTo("404") != 0
				&& jsonStr.compareTo("409") != 0
				&& jsonStr.compareTo("500") != 0) {
			try {
				ret = Arrays.asList(gson.fromJson(jsonStr, TProduto[].class));

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return ret;
	}
	
	public List<TProduto> getAllActiveProducts() {
		List<TProduto> ret = new ArrayList<TProduto>();
		List<TProduto> allProds = db.getAllProdutos();
		
		for(TProduto prod: allProds)
		{
			if (prod.getEstado()==0)
			{
				ret.add(prod);
			}
		}
		return ret;
	}

}