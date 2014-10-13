package com.hermes.hermes.db;

import java.sql.SQLException;
import java.util.List;

import com.hermes.hermes.model.TLinhaProduto;
import com.j256.ormlite.table.TableUtils;

import android.content.Context;

public class LinhaProdutoDBManager extends DatabaseManager {

	static private LinhaProdutoDBManager instance;

	public LinhaProdutoDBManager(Context ctx) {
		super(ctx);
	}

	static public LinhaProdutoDBManager getInstance(Context ctx) {
		if (null == instance) {
			instance = new LinhaProdutoDBManager(ctx);
		}
		return instance;
	}

	// LinhaProduto

	public List<TLinhaProduto> getAllLinhaProduto() {
		List<TLinhaProduto> linhas = null;
		try {
			linhas = getHelper().getLinhaProdDao().queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return linhas;
	}

	public TLinhaProduto getLinhaProdutoByGuiaId(int idguia) {
		TLinhaProduto ret = null;

		try {
			ret = getHelper().getLinhaProdDao().queryForId(idguia);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}

	public boolean linhaProdExists(TLinhaProduto lp) {
		boolean ret = false;
		List<TLinhaProduto> lps;

		try {
			lps = getHelper().getLinhaProdDao().queryForMatching(lp);
			if (lps.size() > 0)
				ret = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}

	public boolean addLinhasProduto(List<TLinhaProduto> linhas) {
		int updatedRows = 0;
		boolean ret = true;

		try {
			for (TLinhaProduto linha : linhas) {
				updatedRows += getHelper().getLinhaProdDao().create(linha);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (updatedRows != linhas.size())
			ret = false;
		return ret;
	}

	public boolean addLinhaProduto(TLinhaProduto linha) {
		int updatedRows = 0;
		boolean ret = true;

		try {
			updatedRows = getHelper().getLinhaProdDao().create(linha);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (updatedRows != 1)
			ret = false;
		return ret;
	}

	public boolean updateLinhaProduto(TLinhaProduto guia) {
		int updatedRows = 0;
		boolean ret = true;

		try {
			updatedRows = getHelper().getLinhaProdDao().update(guia);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (updatedRows != 1)
			ret = false;
		return ret;
	}

	public int removeAllLinhaProduto() {
		int ret = 0;

		try {
			ret = TableUtils.clearTable(getHelper().getConnectionSource(),
					TLinhaProduto.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}

	public int removeLinhaProdutoByGuiaId(int id) {
		int ret = 0;

		try {
			ret = getHelper().getLinhaProdDao().deleteById(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}

}
