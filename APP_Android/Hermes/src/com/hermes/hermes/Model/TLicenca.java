package com.hermes.hermes.Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class TLicenca {
	@DatabaseField
    private int idLicenca;

	@DatabaseField
    private String imei;
	
	@DatabaseField
    private int idEmpresa;
	
	@DatabaseField
    private String estado;
	
	@DatabaseField
    private String codLicenca;
    
    public int getIdLicenca()
    {
    	return this.idLicenca;
    }
    
    public void setIdLicenca(int idl)
    {
    	this.idLicenca = idl;
    }
    
    public String getImei()
    {
    	return this.imei;
    }
    
    public void setImei(String imei)
    {
    	this.imei = imei;
    }
    
    public int getIdEmpresa()
    {
    	return this.idEmpresa;
    }
    
    public void setIdEmpresa(int ide)
    {
    	this.idEmpresa = ide;
    }
    
    public String getEstado()
    {
    	return this.estado;
    }
    
    public void setEstado(String e)
    {
    	this.estado = e;
    }  
    
    public String getCodLicenca()
    {
    	return this.codLicenca;
    }
    
    public void setCodLicenca(String cl)
    {
    	this.codLicenca = cl;
    }
}
