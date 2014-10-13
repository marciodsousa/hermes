package com.hermes.hermes.db;

import java.sql.SQLException;
import java.util.List;

import com.hermes.hermes.model.TUtilizador;
import com.j256.ormlite.table.TableUtils;

import android.content.Context;

public class UtilizadorDBManager extends DatabaseManager {

	static private UtilizadorDBManager instance;

	public UtilizadorDBManager(Context ctx) {
		super(ctx);
	}

	static public UtilizadorDBManager getInstance(Context ctx) {
		if (null == instance) {
			instance = new UtilizadorDBManager(ctx);
		}
		return instance;
	}

	// Utilizadores

	public TUtilizador getUtilizador() {
		List<TUtilizador> usrs = null;
		TUtilizador ret = null;
		try {
			usrs = getHelper().getUtilizadorDao().queryForAll();
			if (usrs.size() > 0)
				ret = usrs.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}

	public TUtilizador getUtilizadorById(int id) {
		TUtilizador ret = null;

		try {
			ret = getHelper().getUtilizadorDao().queryForId(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}

	public boolean addUtilizador(TUtilizador usr) {
		int updatedRows = 0;
		boolean ret = true;

		try {
			updatedRows = getHelper().getUtilizadorDao().create(usr);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (updatedRows != 1)
			ret = false;
		return ret;
	}

	public boolean updateUtilizador(TUtilizador usr) {
		int updatedRows = 0;
		boolean ret = true;

		try {
			updatedRows = getHelper().getUtilizadorDao().update(usr);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (updatedRows != 1)
			ret = false;
		return ret;
	}

	public int removeAllUtilizadores() {
		int ret = 0;

		try {
			ret = TableUtils.clearTable(getHelper().getConnectionSource(),
					TUtilizador.class);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}

	public int removeUtilizadorById(int id) {
		int ret = 0;

		try {
			ret = getHelper().getUtilizadorDao().deleteById(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}
}
