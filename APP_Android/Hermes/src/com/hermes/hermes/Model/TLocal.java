package com.hermes.hermes.model;

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
	
	@DatabaseField
	private int estado;
	
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
    
    public int getEstado()
    {
    	return this.estado;
    }
    
    public void setEstado(int estado)
    {
    	this.estado = estado;
    } 
    
    public static List<TLocal> diff(List<TLocal> list1, List<TLocal> list2)
    {
    	List<TLocal> ret = new ArrayList<TLocal>();
    	boolean exists = false;
    	
    	for (TLocal loc1 : list1) {
    		for (TLocal loc2 : list2) {
        		if (loc1.getIdLocal() == loc2.getIdLocal())
        		{
        			exists = true;
        			break;
        		}
            }
    		
    		if (!exists)
    			ret.add(loc1);
    		
			exists=false;
        }
    	
    	return ret;
    }
}
