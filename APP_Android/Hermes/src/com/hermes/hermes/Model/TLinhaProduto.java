package com.hermes.hermes.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class TLinhaProduto {
	@DatabaseField
    private int quantidade;
	
	@DatabaseField
    private int valorAtual;
	
	@DatabaseField(canBeNull = false, foreignAutoRefresh = true, foreign = true)
    private TGuiaTransporte TGuiaTransporte;
	
	@DatabaseField(canBeNull = false, foreignAutoRefresh = true, foreign = true)
    private TProduto TProduto;

    public TGuiaTransporte getGuia()
    {
    	return this.TGuiaTransporte;
    }
    
    public void setGuia(TGuiaTransporte TGuiaTransporte)
    {
    	this.TGuiaTransporte = TGuiaTransporte;
    }
    
    public TProduto getProduto()
    {
    	return this.TProduto;
    }
    
    public void setProduto(TProduto TProduto)
    {
    	this.TProduto = TProduto;
    }
    
    public int getQuantidade()
    {
    	return this.quantidade;
    }
    
    public void setQuantidade(int quantidade)
    {
    	this.quantidade = quantidade;
    }
    
    public int getValorAtual()
    {
    	return this.valorAtual;
    }
    
    public void setValorAtual(int valorAtual)
    {
    	this.valorAtual = valorAtual;
    }
    
}
