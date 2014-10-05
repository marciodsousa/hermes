/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hermes.hermes;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.hermes.hermes.Model.TGuiaTransporte;
import com.hermes.hermes.Model.TLinhaProduto;
import com.hermes.hermes.Model.TProduto;
import com.hermes.hermes.db.DatabaseManager;


public class ViewGuideActivity extends Activity {

	private TextView mMatricula;
	private TextView mCliente;
	private TextView mData;
	private TextView mLocCarga;
	private TextView mLocDescarga;
	private TextView mProdutos;
	
	private Bundle bundle;
	private TGuiaTransporte guia;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_guide);
		
		DatabaseManager db = DatabaseManager.getInstance(getApplicationContext());
		
		
		mMatricula = (TextView) findViewById(R.id.viewMatricula);
		mCliente = (TextView) findViewById(R.id.viewCliente);
		mData = (TextView) findViewById(R.id.viewData);
		mLocCarga = (TextView) findViewById(R.id.viewLocCarga);
		mLocDescarga = (TextView) findViewById(R.id.viewLocDescarga);
		mProdutos = (TextView) findViewById(R.id.viewProds);
		
		bundle = getIntent().getExtras();
		if (null != bundle && bundle.containsKey("posGuia")) {
			double totalValue = 0;
            int posLoc = bundle.getInt("posGuia");
            List<TGuiaTransporte> guias = DatabaseManager.getInstance(getApplicationContext()).getAllGuiasTransporte();
            
            guia = guias.get(posLoc);
            
            mMatricula.setText(guia.getMatricula());
            mCliente.setText(db.getClienteById(guia.getCLiente().getIdCliente()).getNome());
            mData.setText(guia.getDataTransporte());
            mLocCarga.setText(db.getLocalById(guia.getLocalCarga().getIdLocal()).getNome());
            mLocDescarga.setText(db.getLocalById(guia.getLocalDescarga().getIdLocal()).getNome());
            
            for (TLinhaProduto lprod : guia.getItems()){
            	TProduto prod = db.getProdutoById(lprod.getIdProduto().getIdProduto());
            	double prodValue = prod.getValUnitario();
            	
            	 mProdutos.setText(mProdutos.getText() + "" +
            			 lprod.getQuantidadea() + " | " + prod.getNome() + 
            			 (prodValue/100) +"€ \n");
            	 
            	 totalValue += lprod.getQuantidadea() * prod.getValUnitario();
            }
    		
            mProdutos.setText(mProdutos.getText() + "Total: " + totalValue/100 + "€");
        }
    }	
}