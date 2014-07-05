package com.hermes.hermes.Model;

import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class TLocal {
	@DatabaseField (id=true)
	private int idLocal;
	
	@DatabaseField
	private String nome;
	
	@DatabaseField
	private String morada;
	
	public int getIdLocal()
    {
    	return this.idLocal;
    }
	    
    public void setIdLocal(int idLocal)
    {
    	this.idLocal = idLocal;
    }
    
    public String getNome()
    {
    	return this.nome;
    }
    
    public void setNome(String nome)
    {
    	this.nome = nome;
    }
    
    public String getMorada()
    {
    	return this.morada;
    }
    
    public void setMorada(String morada)
    {
    	this.morada = morada;
    }
    
    public static List<TLocal> diff(List<TLocal> list1, List<TLocal> list2)
    {
    	List<TLocal> ret = new ArrayList<TLocal>();
    	
    	for (TLocal loc1 : list1) {
    		if(!list2.contains(loc1))
    			ret.add(loc1);
        }
    	
    	return ret;
    }
}
