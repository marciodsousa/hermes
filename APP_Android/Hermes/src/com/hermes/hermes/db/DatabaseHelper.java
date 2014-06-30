package com.hermes.hermes.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.hermes.hermes.Model.*;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    // name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = "HermesDB.sqlite";

    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 1;

    // the DAO object we use to access the SimpleData table
    private Dao<TCliente, Integer> clienteDao = null;
    private Dao<TEmpresa, Integer> empresaDao = null;
    private Dao<TGuiaTransporte, Integer> guiaDao = null;
    private Dao<TLicenca, Integer> licencaDao = null;
    private Dao<TLinhaProduto, Integer> linhaProdDao = null;
    private Dao<TLocal, Integer> localDao = null;
    private Dao<TProduto, Integer> produtoDao = null;
    private Dao<TUtilizador, Integer> utilizadorDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database,ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, TCliente.class);
            TableUtils.createTable(connectionSource, TEmpresa.class);
            TableUtils.createTable(connectionSource, TGuiaTransporte.class);
            TableUtils.createTable(connectionSource, TLicenca.class);
            TableUtils.createTable(connectionSource, TLinhaProduto.class);
            TableUtils.createTable(connectionSource, TLocal.class);
            TableUtils.createTable(connectionSource, TProduto.class);
            TableUtils.createTable(connectionSource, TUtilizador.class);
            
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            List<String> allSql = new ArrayList<String>(); 
            switch(oldVersion) 
            {
              case 1: 
                  //allSql.add("alter table AdData add column `new_col` VARCHAR");
                  //allSql.add("alter table AdData add column `new_col2` VARCHAR");
            }
            for (String sql : allSql) {
                db.execSQL(sql);
            }
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "exception during onUpgrade", e);
            throw new RuntimeException(e);
        }
        
    }

    public Dao<TCliente, Integer> getClienteDao() {
        if (null == clienteDao) {
            try {
            	clienteDao = getDao(TCliente.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return clienteDao;
    }

    public Dao<TEmpresa, Integer> getEmpresaDao() {
        if (null == empresaDao) {
            try {
            	empresaDao = getDao(TEmpresa.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return empresaDao;
    }
    public Dao<TGuiaTransporte, Integer> getGuiaDao() {
        if (null == guiaDao) {
            try {
            	guiaDao = getDao(TGuiaTransporte.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return guiaDao;
    }
    
    public Dao<TLicenca, Integer> getLicencaDao() {
        if (null == licencaDao) {
            try {
            	licencaDao = getDao(TLicenca.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return licencaDao;
    }
    
    public Dao<TLinhaProduto, Integer> getLinhaProdDao() {
        if (null == linhaProdDao) {
            try {
            	linhaProdDao = getDao(TLinhaProduto.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return linhaProdDao;
    }
    
    public Dao<TLocal, Integer> getLocalDao() {
        if (null == localDao) {
            try {
            	localDao = getDao(TLocal.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return localDao;
    }
    
    public Dao<TProduto, Integer> getProdutoDao() {
        if (null == produtoDao) {
            try {
            	produtoDao = getDao(TProduto.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return produtoDao;
    }
    
    public Dao<TUtilizador, Integer> getUtilizadorDao() {
        if (null == utilizadorDao) {
            try {
            	utilizadorDao = getDao(TUtilizador.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return utilizadorDao;
    }

}