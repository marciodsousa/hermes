package com.hermes.hermes.Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class TProduto {
	@DatabaseField
	private int idProduto;
	
	@DatabaseField
	private String nome;
	
	@DatabaseField
	private int valUnitario;
	
	@DatabaseField
	private String codProduto;
	
	@DatabaseField
	private String descricao;
    
    public int getIdProduto()
    {
    	return this.idProduto;
    }
    
    public void setIdLicenca(int idProduto)
    {
    	this.idProduto = idProduto;
    }
    
    public String getNome()
    {
    	return this.nome;
    }
    
    public void setNome(String nome)
    {
    	this.nome = nome;
    }
    
    public int getValUnitario()
    {
    	return this.valUnitario;
    }
    
    public void setValUnitario(int valUnitario)
    {
    	this.valUnitario = valUnitario;
    }
    
    public String getCodProduto()
    {
    	return this.codProduto;
    }
    
    public void setCodProduto(String codProduto)
    {
    	this.codProduto = codProduto;
    }  
    
    public String getDescricao()
    {
    	return this.descricao;
    }
    
    public void setDescricao(String descricao)
    {
    	this.descricao = descricao;
    }
}