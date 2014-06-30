package com.hermes.hermes.Model;

import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class TGuiaTransporte {
	@DatabaseField
	private int idGuia;
	
	@DatabaseField
    private int idEmissao;
	
	@DatabaseField
    private int idUtilizador;
    
	@DatabaseField
    private String matricula;
    
	@DatabaseField
    private int idCLiente;
    
	@DatabaseField
    private String dataTransporte;
    
	@DatabaseField
    private int idLocalCarga;
    
	@DatabaseField
    private int idLocalDescarga;
    
	@DatabaseField
    private int estado;

    private TCliente TCliente;
    private TLocal TLocal;
    private TLocal TLocal1;
    private TUtilizador TUtilizador;
    
    @ForeignCollectionField
    private ForeignCollection<TLinhaProduto> prods;
    
    public int getIdGuia()
    {
    	return this.idGuia;
    }
    
    public void setIdGuia(int idGuia)
    {
    	this.idGuia = idGuia;
    }
    
    public int getIdEmissao()
    {
    	return this.idEmissao;
    }
    
    public void setIdEmissao(int idEmissao)
    {
    	this.idEmissao = idEmissao;
    }
    
    public int getIdUtilizador()
    {
    	return this.idUtilizador;
    }
    
    public void setIdUtilizador(int idUtilizador)
    {
    	this.idUtilizador = idUtilizador;
    }
    
    public String getMatricula()
    {
    	return this.matricula;
    }
    
    public void setMatricula(String matricula)
    {
    	this.matricula = matricula;
    }
    
    public int getIdCLiente()
    {
    	return this.idCLiente;
    }
    
    public void setIdCLiente(int idCLiente)
    {
    	this.idCLiente = idCLiente;
    }
    
    public String getDataTransporte()
    {
    	return this.dataTransporte;
    }
    
    public void setDataTransporte(String dataTransporte)
    {
    	this.dataTransporte = dataTransporte;
    }
    
    public int getIdLocalCarga()
    {
    	return this.idLocalCarga;
    }
    
    public void setIdLocalCarga(int idLocalCarga)
    {
    	this.idLocalCarga = idLocalCarga;
    }
    
    public int setIdLocalDescarga()
    {
    	return this.idLocalDescarga;
    }
    
    public void setIdLocalDescarga(int idLocalDescarga)
    {
    	this.idLocalDescarga = idLocalDescarga;
    }
    
    public int setEstado()
    {
    	return this.estado;
    }
    
    public void setEstado(int estado)
    {
    	this.estado = estado;
    }
    
    public void setProds(ForeignCollection<TLinhaProduto> prods) {
        this.prods = prods;
    }

    public List<TLinhaProduto> getItems() {
        ArrayList<TLinhaProduto> itemList = new ArrayList<TLinhaProduto>();
        for (TLinhaProduto item : prods) {
            itemList.add(item);
        }
        return itemList;
    }
}
