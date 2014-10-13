package com.hermes.hermes.db;

import java.sql.SQLException;
import java.util.List;

import com.hermes.hermes.model.TCliente;
import com.j256.ormlite.table.TableUtils;

import android.content.Context;

public class ClienteDBManager extends DatabaseManager {

	static private ClienteDBManager instance;
	
	public ClienteDBManager(Context ctx) {
		super(ctx);
	}	

	static public ClienteDBManager getInstance(Context ctx) {
		if (null == instance) {
			instance = new ClienteDBManager(ctx);
		}
		return instance;
	}


	// Clientes

	public List<TCliente> getAllClientes() {
		List<TCliente> clis = null;
		try {
			clis = getHelper().getClienteDao().queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clis;
	}

	public TCliente getClienteById(int id) {
		TCliente ret = null;

		try {
			ret = getHelper().getClienteDao().queryForId(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}

	public boolean addClientes(List<TCliente> clis) {
		int updatedRows = 0;
		boolean ret = true;

		try {
			for (TCliente cli : clis) {
				if (getClienteById(cli.getIdCliente()) != null)
					updatedRows += getHelper().getClienteDao().update(cli);
				else
					updatedRows += getHelper().getClienteDao().create(cli);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (updatedRows != clis.size())
			ret = false;
		return ret;
	}

	public boolean addCliente(TCliente cli) {
		int updatedRows = 0;
		boolean ret = true;

		try {
			updatedRows = getHelper().getClienteDao().create(cli);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (updatedRows != 1)
			ret = false;
		return ret;
	}

	public boolean updateCliente(TCliente cli) {
		int updatedRows = 0;
		boolean ret = true;

		try {
			updatedRows = getHelper().getClienteDao().update(cli);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (updatedRows != 1)
			ret = false;
		return ret;
	}

	public int removeAllClientes() {
		int ret = 0;

		try {
			ret = TableUtils.clearTable(getHelper().getConnectionSource(),
					TCliente.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}

	public int removeClienteById(int id) {
		int ret = 0;

		try {
			ret = getHelper().getClienteDao().deleteById(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}
}
