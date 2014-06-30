package com.hermes.hermes.Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class TLinhaProduto {
	@DatabaseField
    private int idGuia;
	
	@DatabaseField
    private int idProduto;
	
	@DatabaseField
    private int quantidade;

    public int getIdGuia()
    {
    	return this.idGuia;
    }
    
    public void setIdGuia(int idGuia)
    {
    	this.idGuia = idGuia;
    }
    
    public int getIdProduto()
    {
    	return this.idProduto;
    }
    
    public void setIdProduto(int idProduto)
    {
    	this.idProduto = idProduto;
    }
    
    public int getQuantidadea()
    {
    	return this.quantidade;
    }
    
    public void setQuantidade(int quantidade)
    {
    	this.quantidade = quantidade;
    }
    
}
