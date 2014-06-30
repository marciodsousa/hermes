package com.hermes.hermes.Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class TUtilizador {
	@DatabaseField
	private int idUtilizador;
	
	@DatabaseField
	private String username;
	
	@DatabaseField
	private String nome;
	
	@DatabaseField
	private String email;
	
	@DatabaseField
	private int tipoUtilizado;
	
	@DatabaseField
	private int estado;
	
	@DatabaseField
	private int idEmpresa;
	
	private TEmpresa TEmpresa;
	
	public int getIdUtilizador()
    {
    	return this.idUtilizador;
    }
    
    public void setIdUtilizador(int idUtilizador)
    {
    	this.idUtilizador = idUtilizador;
    }
    
    public String getUsername()
    {
    	return this.username;
    }
    
    public void setUsername(String username)
    {
    	this.username = username;
    }
    
    public String getNome()
    {
    	return this.nome;
    }
    
    public void setNome(String nome)
    {
    	this.nome = nome;
    }
    
    public String getEmail()
    {
    	return this.email;
    }
    
    public void setEmail(String email)
    {
    	this.email = email;
    }  
    
    public int getTipoUtilizado()
    {
    	return this.tipoUtilizado;
    }
    
    public void setTipoUtilizado(int tipoUtilizado)
    {
    	this.tipoUtilizado = tipoUtilizado;
    }
    
    public int getIdEmpresa()
    {
    	return this.idEmpresa;
    }
    
    public void setIdEmpresa(int ide)
    {
    	this.idEmpresa = ide;
    }
    
    public int getEstado()
    {
    	return this.estado;
    }
    
    public void setEstado(int e)
    {
    	this.estado = e;
    }  
    
}
