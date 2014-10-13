package com.hermes.hermes.db;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;

import com.hermes.hermes.model.*;
import com.j256.ormlite.table.TableUtils;

//classe de interface com a Base de dados. Utiliza singleton de forma a ter uma única instância da BD em toda a aplicação.
public class DatabaseManager {

	static private DatabaseManager instance;

	static public DatabaseManager getInstance(Context ctx) {
		if (null == instance) {
			instance = new DatabaseManager(ctx);
		}
		return instance;
	}

	private DatabaseHelper helper;

	protected DatabaseManager(Context ctx) {
		helper = new DatabaseHelper(ctx);
	}

	// método para obter instância da BD
	protected DatabaseHelper getHelper() {
		return helper;
	}

	// métodos CRUD gerais

	

	
	



	

	

	

	

}