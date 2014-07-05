package com.hermes.hermes;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hermes.hermes.Model.TProduto;
import com.hermes.hermes.db.*;

public class AddEditProductActivity extends Activity {

	private EditText mNameView;
	private EditText mCodProdView;
	private EditText mDescView;
	private EditText mValUnitView;
	private Bundle bundle;
	private TProduto prod;
	private DatabaseManager db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_edit_product);
		
		db = DatabaseManager.getInstance(getApplicationContext());
		
		mNameView = (EditText) findViewById(R.id.editNomeProd);
        mCodProdView = (EditText) findViewById(R.id.editCodProd);
        mDescView = (EditText) findViewById(R.id.editDescProd);
        mValUnitView = (EditText) findViewById(R.id.editValUnit);
		
		bundle = getIntent().getExtras();
        if (null!=bundle && bundle.containsKey("posProduto")) {
            int posProd = bundle.getInt("posProduto");
            List<TProduto> prods = DatabaseManager.getInstance(getApplicationContext()).getAllProdutos();
            
            prod = prods.get(posProd);
            
            mNameView.setText(prod.getNome());
            mCodProdView.setText(prod.getCodProduto());
            mDescView.setText(prod.getDescricao());
            mValUnitView.setText(prod.getValUnitario() + "");
    		
        }
        
        findViewById(R.id.btnSubmit).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						if (null!=bundle && bundle.containsKey("posProduto")) {
							if (validateFields())
							{
					            prod.setNome(mNameView.getText().toString());
					            prod.setCodProduto(mCodProdView.getText().toString());
					            prod.setDescricao(mDescView.getText().toString());
					            prod.setValUnitario(Integer.parseInt(mValUnitView.getText().toString()));
					            
					            if (db.updateProduto(prod))
					            	finish();

							}
				        }else{
				        	if (validateFields())
							{
				        		prod = new TProduto();
				        		prod.setIdProduto(0);
					            prod.setNome(mNameView.getText().toString());
					            prod.setCodProduto(mCodProdView.getText().toString());
					            prod.setDescricao(mDescView.getText().toString());
					            prod.setValUnitario(Integer.parseInt(mValUnitView.getText().toString()));
					            
					            if (db.addProduto(prod))
					            	finish();

							}
				        }
					}
				});
        
        findViewById(R.id.btnCancel).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						finish();
					}
				});
        
        
	}
	
	private boolean validateFields() {
		boolean ret = false;
		
		if(mNameView.getText().toString().length()<50 && mNameView.getText().toString().length()>2)
		{
			if (mCodProdView.getText().toString().length()<20)
			{
				if(mDescView.getText().toString().length()<200)
				{
					if (mValUnitView.getText().toString().matches("[0-9]+"))
						ret=true;
				}
			}
		}
		
		return ret;
    }	
	
}
