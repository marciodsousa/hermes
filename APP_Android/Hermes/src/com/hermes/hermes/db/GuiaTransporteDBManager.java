package com.hermes.hermes.db;

import java.sql.SQLException;
import java.util.List;

import com.hermes.hermes.model.TGuiaTransporte;
import com.hermes.hermes.model.TLinhaProduto;
import com.j256.ormlite.table.TableUtils;

import android.content.Context;

public class GuiaTransporteDBManager extends DatabaseManager {
	static private GuiaTransporteDBManager instance;
	private LinhaProdutoDBManager lpDBManager;

	public GuiaTransporteDBManager(Context ctx) {
		super(ctx);
		lpDBManager = new LinhaProdutoDBManager(ctx);
	}

	static public GuiaTransporteDBManager getInstance(Context ctx) {
		if (null == instance) {
			instance = new GuiaTransporteDBManager(ctx);
		}
		return instance;
	}

	// Guias Transporte

	public List<TGuiaTransporte> getAllGuiasTransporte() {
		List<TGuiaTransporte> guias = null;
		try {
			guias = getHelper().getGuiaDao().queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return guias;
	}

	public TGuiaTransporte getGuiaTransporteById(int id) {
		TGuiaTransporte ret = null;

		try {
			ret = getHelper().getGuiaDao().queryForId(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}

	public boolean addGuiasTransporte(List<TGuiaTransporte> guias) {
		int updatedRows = 0;
		boolean ret = true;

		for (TGuiaTransporte guia : guias) {
			
			if(getGuiaTransporteById(guia.getIdGuia())==null)
			{
				if (addGuiaTransporte(guia)==true)
					updatedRows ++;
			}
			else
			{
				if (updateGuiaTransporte(guia)==true)
					updatedRows ++;
			}
		}
		if (updatedRows != guias.size())
			ret = false;
		return ret;
	}

	public boolean addGuiaTransporte(TGuiaTransporte guia) {
		int updatedRows = 0;
		boolean ret = true;
		TGuiaTransporte guiaDB;

		try {
			updatedRows = getHelper().getGuiaDao().create(guia);
			guiaDB = getGuiaTransporteById(guia.getIdGuia());

			for (TLinhaProduto linha : guia.getItems()) {
				linha.setGuia(guiaDB);
				if (!lpDBManager.linhaProdExists(linha))
					lpDBManager.addLinhaProduto(linha);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (updatedRows != 1)
			ret = false;
		return ret;
	}

	public boolean updateGuiaTransporte(TGuiaTransporte guia) {
		int updatedRows = 0;
		boolean ret = true;

		try {
			updatedRows = getHelper().getGuiaDao().update(guia);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (updatedRows != 1)
			ret = false;
		return ret;
	}

	public int removeAllGuiasTransporte() {
		int ret = 0;

		try {
			ret = TableUtils.clearTable(getHelper().getConnectionSource(),
					TGuiaTransporte.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}

	public int removeGuiaTransporteById(int id) {
		int ret = 0;

		try {
			ret = getHelper().getGuiaDao().deleteById(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}

	public int getNewGuiaID() {
		int id = 0;
		List<TGuiaTransporte> guias = getAllGuiasTransporte();

		for (TGuiaTransporte guia : guias) {
			if (guia.getIdGuia() < id)
				id = guia.getIdGuia();
		}

		return id - 1;
	}

}
