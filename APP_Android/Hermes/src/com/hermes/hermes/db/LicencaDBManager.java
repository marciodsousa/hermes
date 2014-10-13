package com.hermes.hermes.db;

import java.sql.SQLException;
import java.util.List;

import com.hermes.hermes.model.TLicenca;
import com.j256.ormlite.table.TableUtils;

import android.content.Context;

public class LicencaDBManager extends DatabaseManager {

	static private LicencaDBManager instance;

	public LicencaDBManager(Context ctx) {
		super(ctx);
	}

	static public LicencaDBManager getInstance(Context ctx) {
		if (null == instance) {
			instance = new LicencaDBManager(ctx);
		}
		return instance;
	}

	// Empresa

	public TLicenca getLicenca() {
		List<TLicenca> lics = null;
		TLicenca ret = null;
		try {
			lics = getHelper().getLicencaDao().queryForAll();
			if (lics.size() > 0)
				ret = lics.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}

	public TLicenca getLicencaById(int id) {
		TLicenca ret = null;

		try {
			ret = getHelper().getLicencaDao().queryForId(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}

	public boolean addLicenca(TLicenca lic) {
		int updatedRows = 0;
		boolean ret = true;

		try {
			updatedRows = getHelper().getLicencaDao().create(lic);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (updatedRows != 1)
			ret = false;
		return ret;
	}

	public boolean updateLicenca(TLicenca lic) {
		int updatedRows = 0;
		boolean ret = true;

		try {
			updatedRows = getHelper().getLicencaDao().update(lic);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (updatedRows != 1)
			ret = false;
		return ret;
	}

	public int removeAllLicencas() {
		int ret = 0;

		try {
			ret = TableUtils.clearTable(getHelper().getConnectionSource(),
					TLicenca.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}

	public int removeLicencaById(int id) {
		int ret = 0;

		try {
			ret = getHelper().getLicencaDao().deleteById(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}
}
