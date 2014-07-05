package com.hermes.hermes.Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tutilizador")
public class TUtilizador {
	@DatabaseField (id=true)
	private int idUtilizador;
	
	@DatabaseField
	private String username;
	
	@DatabaseField
	private String nome;
	
	@DatabaseField
	private String email;
	
	@DatabaseField
	private int tipoUtilizador;
	
	@DatabaseField
	private int estado;
	
	@DatabaseField(canBeNull = false, foreign = true)
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
    
    public int getTipoUtilizador()
    {
    	return this.tipoUtilizador;
    }
    
    public void setTipoUtilizado(int tipoUtilizado)
    {
    	this.tipoUtilizador = tipoUtilizado;
    }
    
    public TEmpresa getEmpresa()
    {
    	return this.TEmpresa;
    }
    
    public void setEmpresa(TEmpresa TEmpresa)
    {
    	this.TEmpresa = TEmpresa;
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
