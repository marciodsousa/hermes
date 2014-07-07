package com.hermes.hermes.Model;

import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class TProduto {
	@DatabaseField (id=true)
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
    
    public void setIdProduto(int idProduto)
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
    
    public static List<TProduto> diff(List<TProduto> list1, List<TProduto> list2)
    {
    	List<TProduto> ret = new ArrayList<TProduto>();
    	boolean exists = false;
    	
    	for (TProduto prod1 : list1) {
    		for (TProduto prod2 : list2) {
        		if (prod1.getIdProduto() == prod2.getIdProduto())
        		{
        			exists = true;
        			break;
        		}
            }
    		
    		if (!exists)
    			ret.add(prod1);
    		
			exists=false;
        }
    	
    	return ret;
    }
}