package com.hermes.hermes.model;

import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class TCliente {
	@DatabaseField (id=true)
	private int idCliente;
	
	@DatabaseField
	private String nome;
	
	@DatabaseField
	private String nif;
	
	@DatabaseField
	private String contacto;
	
	@DatabaseField
	private int estado;
	
	public int getIdCliente()
    {
    	return this.idCliente;
    }
    
    public void setIdCliente(int idc)
    {
    	this.idCliente = idc;
    }
    
    public String getNome()
    {
    	return this.nome;
    }
    
    public void setNome(String nome)
    {
    	this.nome = nome;
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
    
    public int getEstado()
    {
    	return this.estado;
    }
    
    public void setEstado(int estado)
    {
    	this.estado = estado;
    } 
    
    public static List<TCliente> diff(List<TCliente> list1, List<TCliente> list2)
    {
    	List<TCliente> ret = new ArrayList<TCliente>();
    	boolean exists = false;
    	
    	for (TCliente cli1 : list1) {
    		for (TCliente cli2 : list2) {
        		if (cli1.getIdCliente() == cli2.getIdCliente())
        		{
        			exists = true;
        			break;
        		}
            }
    		
    		if (!exists)
    			ret.add(cli1);
    		
			exists=false;
        }
    	
    	return ret;
    }
    
}
