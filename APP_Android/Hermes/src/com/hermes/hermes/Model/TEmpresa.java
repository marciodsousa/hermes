package com.hermes.hermes.Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class TEmpresa {
	@DatabaseField
	private int idEmpresa;
	
	@DatabaseField
	private String nome;
	
	@DatabaseField
	private String morada;
	
	@DatabaseField
	private String email;
	
	@DatabaseField
	private String nif;
	
	@DatabaseField
	private String contacto;

    public int getIdEmpresa()
    {
    	return this.idEmpresa;
    }
    
    public void setIdEmpresa(int ide)
    {
    	this.idEmpresa = ide;
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
    
    public String getEmail()
    {
    	return this.email;
    }
    
    public void setEmail(String email)
    {
    	this.email = email;
    }
    
    public String getNif()
    {
    	return this.nif;
    }
    
    public void setNif(String nif)
    {
    	this.nif = nif;
    }
    public String getContacto()
    {
    	return this.contacto;
    }
    
    public void setContacto(String contacto)
    {
    	this.contacto = contacto;
    }
}
