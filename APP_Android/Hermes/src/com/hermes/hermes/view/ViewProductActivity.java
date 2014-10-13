package com.hermes.hermes.view;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.hermes.hermes.R;
import com.hermes.hermes.controller.ProdutoController;
import com.hermes.hermes.db.ProdutoDBManager;
import com.hermes.hermes.model.TProduto;

public class ViewProductActivity extends Activity {

	private TextView mNameView;
	private TextView mCodProdView;
	private TextView mDescView;
	private TextView mValUnitView;
	private Bundle bundle;
	private TProduto prod;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_product);

		mNameView = (TextView) findViewById(R.id.editNomeProd);
		mCodProdView = (TextView) findViewById(R.id.editCodProd);
		mDescView = (TextView) findViewById(R.id.editDescProd);
		mValUnitView = (TextView) findViewById(R.id.editValUnit);

		bundle = getIntent().getExtras();
		if (null != bundle && bundle.containsKey("posProduto")) {

			int posProd = bundle.getInt("posProduto");
			
			ProdutoController c = new ProdutoController(getApplicationContext());
			List<TProduto> prods = c.getAllActiveProducts();
			

			prod = prods.get(posProd);

			mNameView.setText(prod.getNome());
			mCodProdView.setText(prod.getCodProduto());
			mDescView.setText(prod.getDescricao());
			mValUnitView.setText(prod.getValUnitario() + "");
		}

	}

}
