package com.hermes.hermes.db;

import java.sql.SQLException;
import java.util.List;

import com.hermes.hermes.model.TEmpresa;
import com.j256.ormlite.table.TableUtils;

import android.content.Context;

public class EmpresaDBManager extends DatabaseManager {
	
	static private EmpresaDBManager instance;
	
	public EmpresaDBManager(Context ctx) {
		super(ctx);
	}	

	static public EmpresaDBManager getInstance(Context ctx) {
		if (null == instance) {
			instance = new EmpresaDBManager(ctx);
		}
		return instance;
	}
	
	// Empresa

		public TEmpresa getEmpresa() {
			List<TEmpresa> emps = null;
			TEmpresa ret = null;
			try {
				emps = getHelper().getEmpresaDao().queryForAll();
				if (emps.size() > 0)
					ret = emps.get(0);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return ret;
		}

		public TEmpresa getEmpresaById(int id) {
			TEmpresa ret = null;

			try {
				ret = getHelper().getEmpresaDao().queryForId(id);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return ret;
		}

		public boolean addEmpresa(TEmpresa emp) {
			int updatedRows = 0;
			boolean ret = true;

			try {
				updatedRows = getHelper().getEmpresaDao().create(emp);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (updatedRows != 1)
				ret = false;
			return ret;
		}

		public boolean updateEmpresa(TEmpresa emp) {
			int updatedRows = 0;
			boolean ret = true;

			try {
				updatedRows = getHelper().getEmpresaDao().update(emp);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (updatedRows != 1)
				ret = false;
			return ret;
		}

		public int removeAllEmpresas() {
			int ret = 0;

			try {
				ret = TableUtils.clearTable(getHelper().getConnectionSource(),
						TEmpresa.class);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return ret;
		}

		public int removeEmpresaById(int id) {
			int ret = 0;

			try {
				ret = getHelper().getEmpresaDao().deleteById(id);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return ret;
		}
}
