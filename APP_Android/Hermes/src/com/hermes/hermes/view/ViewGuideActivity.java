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

package com.hermes.hermes.view;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.hermes.hermes.R;
import com.hermes.hermes.db.ClienteDBManager;
import com.hermes.hermes.db.GuiaTransporteDBManager;
import com.hermes.hermes.db.LocalDBManager;
import com.hermes.hermes.db.ProdutoDBManager;
import com.hermes.hermes.model.TGuiaTransporte;
import com.hermes.hermes.model.TLinhaProduto;
import com.hermes.hermes.model.TProduto;


public class ViewGuideActivity extends Activity {

	private TextView mMatricula;
	private TextView mCliente;
	private TextView mData;
	private TextView mHora;
	private TextView mLocCarga;
	private TextView mLocDescarga;
	private TextView mProdutos;
	
	private Bundle bundle;
	private TGuiaTransporte guia;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_guide);
		
		GuiaTransporteDBManager dbGuias = GuiaTransporteDBManager.getInstance(getApplicationContext());
		ClienteDBManager dbClis = ClienteDBManager.getInstance(getApplicationContext());
		LocalDBManager dbLocs = LocalDBManager.getInstance(getApplicationContext());
		ProdutoDBManager dbProds = ProdutoDBManager.getInstance(getApplicationContext());
		
		
		mMatricula = (TextView) findViewById(R.id.viewMatricula);
		mCliente = (TextView) findViewById(R.id.viewCliente);
		mData = (TextView) findViewById(R.id.viewData);
		mHora = (TextView) findViewById(R.id.viewHora);
		mLocCarga = (TextView) findViewById(R.id.viewLocCarga);
		mLocDescarga = (TextView) findViewById(R.id.viewLocDescarga);
		mProdutos = (TextView) findViewById(R.id.viewProds);
		
		bundle = getIntent().getExtras();
		if (null != bundle && bundle.containsKey("posGuia")) {
			double totalValue = 0;
            int posLoc = bundle.getInt("posGuia");
            List<TGuiaTransporte> guias = dbGuias.getAllGuiasTransporte();
            
            guia = guias.get(posLoc);
            
            mMatricula.setText(guia.getMatricula());
            mCliente.setText(dbClis.getClienteById(guia.getCLiente().getIdCliente()).getNome());
            mData.setText(guia.getDataTransporte());
            mHora.setText(guia.getHoraTransporte());
            mLocCarga.setText(dbLocs.getLocalById(guia.getLocalCarga().getIdLocal()).getNome());
            mLocDescarga.setText(dbLocs.getLocalById(guia.getLocalDescarga().getIdLocal()).getNome());
            
            for (TLinhaProduto lprod : guia.getItems()){
            	TProduto prod = dbProds.getProdutoById(lprod.getProduto().getIdProduto());
            	double prodValue = lprod.getValorAtual()/lprod.getQuantidade();
            	
            	 mProdutos.setText(mProdutos.getText() + "" +
            			 lprod.getQuantidade() + " | " + prod.getNome() + 
            			 (prodValue/100) +"€ \n");
            	 
            	 totalValue += lprod.getValorAtual();
            }
    		
            mProdutos.setText(mProdutos.getText() + "Total: " + totalValue/100 + "€");
        }
    }	
}