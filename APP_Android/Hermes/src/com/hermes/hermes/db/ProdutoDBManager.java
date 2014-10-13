package com.hermes.hermes.db;

import java.sql.SQLException;
import java.util.List;

import com.hermes.hermes.model.TProduto;
import com.j256.ormlite.table.TableUtils;

import android.content.Context;

public class ProdutoDBManager extends DatabaseManager {

	static private ProdutoDBManager instance;

	public ProdutoDBManager(Context ctx) {
		super(ctx);
	}

	static public ProdutoDBManager getInstance(Context ctx) {
		if (null == instance) {
			instance = new ProdutoDBManager(ctx);
		}
		return instance;
	}

	// Produtos

	public List<TProduto> getAllProdutos() {
		List<TProduto> prods = null;
		try {
			prods = getHelper().getProdutoDao().queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return prods;
	}

	public TProduto getProdutoById(int id) {
		TProduto ret = null;

		try {
			ret = getHelper().getProdutoDao().queryForId(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}

	public boolean addProdutos(List<TProduto> prods) {
		int updatedRows = 0;
		boolean ret = true;

		try {
			for (TProduto prod : prods) {
				if (getProdutoById(prod.getIdProduto()) != null)
					updatedRows += getHelper().getProdutoDao().update(prod);
				else
					updatedRows += getHelper().getProdutoDao().create(prod);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (updatedRows != prods.size())
			ret = false;
		return ret;
	}

	public boolean addProduto(TProduto prod) {
		int updatedRows = 0;
		boolean ret = true;

		try {
			updatedRows = getHelper().getProdutoDao().create(prod);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (updatedRows != 1)
			ret = false;
		return ret;
	}

	public boolean updateProduto(TProduto prod) {
		int updatedRows = 0;
		boolean ret = true;

		try {
			updatedRows = getHelper().getProdutoDao().update(prod);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (updatedRows != 1)
			ret = false;
		return ret;
	}

	public int removeAllProdutos() {
		int ret = 0;

		try {
			ret = TableUtils.clearTable(getHelper().getConnectionSource(),
					TProduto.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}

	public int removeProdutoById(int id) {
		int ret = 0;

		try {
			ret = getHelper().getProdutoDao().deleteById(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}

}
