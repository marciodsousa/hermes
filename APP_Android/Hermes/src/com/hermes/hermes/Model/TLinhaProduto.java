package com.hermes.hermes.Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class TLinhaProduto {
	@DatabaseField
    private int quantidade;
	
	@DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private TGuiaTransporte TGuiaTransporte;
	
	@DatabaseField(canBeNull = false, foreign = true)
    private TProduto TProduto;

    public TGuiaTransporte getIdGuia()
    {
    	return this.TGuiaTransporte;
    }
    
    public void setIdGuia(TGuiaTransporte TGuiaTransporte)
    {
    	this.TGuiaTransporte = TGuiaTransporte;
    }
    
    public TProduto getIdProduto()
    {
    	return this.TProduto;
    }
    
    public void setIdProduto(TProduto TProduto)
    {
    	this.TProduto = TProduto;
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
