package com.hermes.hermes.db;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;

import com.hermes.hermes.Model.*;

//classe de interface com a Base de dados. Utiliza singleton de forma a ter uma única instância da BD em toda a aplicação.
public class DatabaseManager {

    static private DatabaseManager instance;

    static public void init(Context ctx) {
        if (null==instance) {
            instance = new DatabaseManager(ctx);
        }
    }

    static public DatabaseManager getInstance() {
        return instance;
    }

    private DatabaseHelper helper;
    private DatabaseManager(Context ctx) {
        helper = new DatabaseHelper(ctx);
    }

    //método para obter instância da BD
    private DatabaseHelper getHelper() {
        return helper;
    }

    //métodos CRUD gerais
    
    
    public List<TProduto> getAllProdutos() {
        List<TProduto> produtos = null;
        
        try {
        	produtos = getHelper().getProdutoDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produtos;
    }
    
    public boolean addProdutos(List<TProduto> prods) {
        int updatedRows = 0;
        boolean ret = true;
        
        try {
        	for (TProduto prod : prods) {
        		updatedRows+=getHelper().getProdutoDao().create(prod);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (updatedRows!=prods.size())
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
        if (updatedRows!=1)
        	ret = false;
        return ret;
    }
    

    public int removeAllProdutos() {
    	int ret = 0;
    	
        try {
        	ret = getHelper().getProdutoDao().delete(getAllProdutos());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return ret;
    }
    
    
    public List<TCliente> getAllClientes() {
        List<TCliente> clients = null;
        try {
        	clients = getHelper().getClienteDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }
    
    public List<TLocal> getAllLocais() {
        List<TLocal> locais = null;
        try {
        	locais = getHelper().getLocalDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return locais;
    }
    
	public TLocal getLocalById(TLocal loc) {
		TLocal ret = null;
		
		try {
			ret = getHelper().getLocalDao().queryForId(loc.getIdLocal());
		} catch (SQLException e) {
			e.printStackTrace();
		}

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
		if (updatedRows!=1)
			ret = false;
		return ret;
	}
    
    public List<TGuiaTransporte> getAllGuias() {
        List<TGuiaTransporte> guias = null;
        try {
        	guias = getHelper().getGuiaDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return guias;
    }
    
    public List<TLinhaProduto> getAllLinhasProd() {
        List<TLinhaProduto> linhasp = null;
        try {
        	linhasp = getHelper().getLinhaProdDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return linhasp;
    }
    
    public TUtilizador getUtilizador() {
    	TUtilizador user = null;
        try {
        	user = getHelper().getUtilizadorDao().queryForAll().get(0);
        	
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    
    public boolean addUtilizador(TUtilizador u) {
    	int updatedRows = 0;
        boolean ret = true;
        
        try {
        	updatedRows = getHelper().getUtilizadorDao().create(u);  
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (updatedRows!=1)
        	ret = false;
        return ret;
    }
    
    public int removeUtilizador() {
    	int ret = 0;
    	
        try {
        	ret = getHelper().getUtilizadorDao().delete(getUtilizador());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return ret;
    }
    
    public TEmpresa getEmpresa() {
    	TEmpresa empresa = null;
        try {
        	empresa = getHelper().getEmpresaDao().queryForAll().get(0);
        	
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empresa;
    }
    
    public TLicenca getLicenca() {
    	TLicenca licenca = null;
        try {
        	licenca = getHelper().getLicencaDao().queryForAll().get(0);
        	
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return licenca;
    }
    
    
}