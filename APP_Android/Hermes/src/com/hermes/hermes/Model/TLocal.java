package com.hermes.hermes.Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class TLocal {
	@DatabaseField
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
    
    public void setIdProduto(String nome)
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
}
