package com.hermes.hermes.Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class TGuiaTransporte {
	@DatabaseField (id=true)
	private int idGuia;
	
	@DatabaseField
    private int idEmissao;
	
//	@DatabaseField
//    private int idUtilizador;
    
	@DatabaseField
    private String matricula;
    
//	@DatabaseField
//    private int idCLiente;
    
	@DatabaseField
    private String dataTransporte;
    
//	@DatabaseField
//    private int idLocalCarga;
//    
//	@DatabaseField
//    private int idLocalDescarga;
    
	@DatabaseField
    private int estado;
	
	
	@DatabaseField(canBeNull = false, foreign = true)
    private TCliente TCliente;
	
	@DatabaseField(canBeNull = false, foreign = true)
    private TLocal TLocal;
	
	@DatabaseField(canBeNull = false, foreign = true)
    private TLocal TLocal1;
	
	@DatabaseField(canBeNull = false, foreign = true)
    private TUtilizador TUtilizador;
    
    @ForeignCollectionField(eager = true)
    private Collection<TLinhaProduto> TLinhaProduto = new ArrayList<TLinhaProduto>();
    
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
    
    public TUtilizador getUtilizador()
    {
    	return this.TUtilizador;
    }
    
    public void setUtilizador(TUtilizador TUtilizador)
    {
    	this.TUtilizador = TUtilizador;
    }
    
    public String getMatricula()
    {
    	return this.matricula;
    }
    
    public void setMatricula(String matricula)
    {
    	this.matricula = matricula;
    }
    
    public TCliente getCLiente()
    {
    	return this.TCliente;
    }
    
    public void setCLiente(TCliente TCliente)
    {
    	this.TCliente = TCliente;
    }
    
    public String getDataTransporte()
    {
    	return this.dataTransporte;
    }
    
    public void setDataTransporte(String dataTransporte)
    {
    	this.dataTransporte = dataTransporte;
    }
    
    public TLocal getLocalCarga()
    {
    	return this.TLocal;
    }
    
    public void setLocalCarga(TLocal TLocalCarga)
    {
    	this.TLocal = TLocalCarga;
    }
    
    public TLocal setIdLocalDescarga()
    {
    	return this.TLocal1;
    }
    
    public void setIdLocalDescarga(TLocal TLocalDescarga)
    {
    	this.TLocal1 = TLocalDescarga;
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
        this.TLinhaProduto = prods;
    }

/*    public List<TLinhaProduto> getItems() {
        ArrayList<TLinhaProduto> itemList = new ArrayList<TLinhaProduto>();
        for (TLinhaProduto item : TLinhaProduto) {
            itemList.add(item);
        }
        return itemList;
    }*/
    
    public List<TLinhaProduto> getItems() {
        return new ArrayList<TLinhaProduto>(TLinhaProduto) ;
    }
    
    public static List<TGuiaTransporte> diff(List<TGuiaTransporte> list1, List<TGuiaTransporte> list2)
    {
    	List<TGuiaTransporte> ret = new ArrayList<TGuiaTransporte>();
    	
    	for (TGuiaTransporte guia1 : list1) {
    		if(!list2.contains(guia1))
    			ret.add(guia1);
        }
    	
    	return ret;
    }
}
