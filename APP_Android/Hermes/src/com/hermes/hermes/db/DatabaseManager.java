package com.hermes.hermes.db;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;



import com.hermes.hermes.Model.*;
import com.j256.ormlite.table.TableUtils;

//classe de interface com a Base de dados. Utiliza singleton de forma a ter uma única instância da BD em toda a aplicação.
public class DatabaseManager {

    static private DatabaseManager instance;

    static public DatabaseManager getInstance(Context ctx) {
    	if (null==instance) {
            instance = new DatabaseManager(ctx);
        }
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
    
    //Utilizadores
    
    public TUtilizador getUtilizador() {
        List<TUtilizador> usrs = null;
        TUtilizador ret = null;
        try {
        	usrs = getHelper().getUtilizadorDao().queryForAll();
        	if(usrs.size()>0)
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
		if (updatedRows!=1)
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
		if (updatedRows!=1)
			ret = false;
		return ret;
	}
	
    public int removeAllUtilizadores() {
    	int ret = 0;
    	
        try {
        	ret = TableUtils.clearTable(helper.getConnectionSource(), TUtilizador.class);
        	
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
    
    //Produtos
    
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
	
	public boolean updateProduto(TProduto prod) {
		int updatedRows = 0;
		boolean ret = true;
		
		try {
			updatedRows = getHelper().getProdutoDao().update(prod);
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
        	ret = TableUtils.clearTable(helper.getConnectionSource(), TProduto.class);
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
    
    //Clientes
    
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
        		updatedRows+=getHelper().getClienteDao().create(cli);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (updatedRows!=clis.size())
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
		if (updatedRows!=1)
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
		if (updatedRows!=1)
			ret = false;
		return ret;
	}
	
    public int removeAllClientes() {
    	int ret = 0;
    	
        try {
        	ret = TableUtils.clearTable(helper.getConnectionSource(), TCliente.class);
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
    
    //Locais
    
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
        		updatedRows+=getHelper().getLocalDao().create(loc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (updatedRows!=locs.size())
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
		if (updatedRows!=1)
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
		if (updatedRows!=1)
			ret = false;
		return ret;
	}
	
    public int removeAllLocais() {
    	int ret = 0;
    	
        try {
        	ret = TableUtils.clearTable(helper.getConnectionSource(), TLocal.class);
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
    
    //Empresa
    
    public TEmpresa getEmpresa() {
        List<TEmpresa> emps = null;
        TEmpresa ret = null;
        try {
        	emps = getHelper().getEmpresaDao().queryForAll();
        	if(emps.size()>0)
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
		if (updatedRows!=1)
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
		if (updatedRows!=1)
			ret = false;
		return ret;
	}
	
    public int removeAllEmpresas() {
    	int ret = 0;
    	
        try {
        	ret = TableUtils.clearTable(helper.getConnectionSource(), TEmpresa.class);
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
    
 //Empresa
    
    public TLicenca getLicenca() {
        List<TLicenca> lics = null;
        TLicenca ret = null;
        try {
        	lics = getHelper().getLicencaDao().queryForAll();
        	if(lics.size()>0)
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
		if (updatedRows!=1)
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
		if (updatedRows!=1)
			ret = false;
		return ret;
	}
	
    public int removeAllLicencas() {
    	int ret = 0;
    	
        try {
        	ret = TableUtils.clearTable(helper.getConnectionSource(), TLicenca.class);
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
    
    //Guias Transporte
    
    public List<TGuiaTransporte> getAllGuiasTransporte() {
        List<TGuiaTransporte> guias = null;
        try {
        	guias = getHelper().getGuiaDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return guias;
    }
	
	public TGuiaTransporte getGuiaTransporteById(int id) {
		TGuiaTransporte ret = null;
		
		try {
			ret = getHelper().getGuiaDao().queryForId(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}

	public boolean addGuiasTransporte(List<TGuiaTransporte> guias) {
        int updatedRows = 0;
        boolean ret = true;
        
        try {
        	for (TGuiaTransporte guia : guias) {
        		updatedRows+=getHelper().getGuiaDao().create(guia);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (updatedRows!=guias.size())
        	ret = false;
        return ret;
    }
	
	public boolean addGuiaTransporte(TGuiaTransporte guia) {
		int updatedRows = 0;
		boolean ret = true;
		
		try {
			updatedRows = getHelper().getGuiaDao().create(guia);  
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (updatedRows!=1)
			ret = false;
		return ret;
	}
	
	public boolean updateGuiaTransporte(TGuiaTransporte guia) {
		int updatedRows = 0;
		boolean ret = true;
		
		try {
			updatedRows = getHelper().getGuiaDao().update(guia);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (updatedRows!=1)
			ret = false;
		return ret;
	}
	
    public int removeAllGuiasTransporte() {
    	int ret = 0;
    	
        try {
        	ret = TableUtils.clearTable(helper.getConnectionSource(), TGuiaTransporte.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return ret;
    }
	
    public int removeGuiaTransporteById(int id) {
    	int ret = 0;
    	
        try {
        	ret = getHelper().getGuiaDao().deleteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return ret;
    }
    
    //LinhaProduto
    
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

	public boolean addLinhasProduto(List<TLinhaProduto> linhas) {
        int updatedRows = 0;
        boolean ret = true;
        
        try {
        	for (TLinhaProduto linha : linhas) {
        		updatedRows+=getHelper().getLinhaProdDao().create(linha);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (updatedRows!=linhas.size())
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
		if (updatedRows!=1)
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
		if (updatedRows!=1)
			ret = false;
		return ret;
	}
	
    public int removeAllLinhaProduto() {
    	int ret = 0;
    	
        try {
        	ret = TableUtils.clearTable(helper.getConnectionSource(), TLinhaProduto.class);
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