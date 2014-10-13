package com.hermes.hermes.db;

import java.sql.SQLException;
import java.util.List;

import com.hermes.hermes.model.TLocal;
import com.j256.ormlite.table.TableUtils;

import android.content.Context;

public class LocalDBManager extends DatabaseManager {

	static private LocalDBManager instance;

	public LocalDBManager(Context ctx) {
		super(ctx);
	}

	static public LocalDBManager getInstance(Context ctx) {
		if (null == instance) {
			instance = new LocalDBManager(ctx);
		}
		return instance;
	}

	// Locais

	public List<TLocal> getAllLocais() {
		List<TLocal> locais = null;
		try {
			locais = getHelper().getLocalDao().queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return locais;
	}

	public TLocal getLocalById(int id) {
		TLocal ret = null;

		try {
			ret = getHelper().getLocalDao().queryForId(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}

	public boolean addLocais(List<TLocal> locs) {
		int updatedRows = 0;
		boolean ret = true;

		try {
			for (TLocal loc : locs) {
				if (getLocalById(loc.getIdLocal()) != null)
					updatedRows += getHelper().getLocalDao().update(loc);
				else
					updatedRows += getHelper().getLocalDao().create(loc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (updatedRows != locs.size())
			ret = false;
		return ret;
	}

	public boolean addLocal(TLocal loc) {
		int updatedRows = 0;
		boolean ret = true;

		try {
			updatedRows = getHelper().getLocalDao().create(loc);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (updatedRows != 1)
			ret = false;
		return ret;
	}

	public boolean updateLocal(TLocal loc) {
		int updatedRows = 0;
		boolean ret = true;

		try {
			updatedRows = getHelper().getLocalDao().update(loc);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (updatedRows != 1)
			ret = false;
		return ret;
	}

	public int removeAllLocais() {
		int ret = 0;

		try {
			ret = TableUtils.clearTable(getHelper().getConnectionSource(),
					TLocal.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}

	public int removeLocalById(int id) {
		int ret = 0;

		try {
			ret = getHelper().getLocalDao().deleteById(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}

}
