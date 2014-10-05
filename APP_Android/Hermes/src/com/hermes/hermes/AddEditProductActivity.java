package com.hermes.hermes;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hermes.hermes.Model.TProduto;
import com.hermes.hermes.db.DatabaseManager;

public class AddEditProductActivity extends Activity {

	private TextView mNameView;
	private TextView mCodProdView;
	private TextView mDescView;
	private TextView mValUnitView;
	private Bundle bundle;
	private TProduto prod;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_edit_product);

		mNameView = (TextView) findViewById(R.id.editNomeProd);
		mCodProdView = (TextView) findViewById(R.id.editCodProd);
		mDescView = (TextView) findViewById(R.id.editDescProd);
		mValUnitView = (TextView) findViewById(R.id.editValUnit);

		bundle = getIntent().getExtras();
		if (null != bundle && bundle.containsKey("posProduto")) {

			int posProd = bundle.getInt("posProduto");
			List<TProduto> prods = DatabaseManager.getInstance(
					getApplicationContext()).getAllProdutos();

			prod = prods.get(posProd);

			mNameView.setText(prod.getNome());
			mCodProdView.setText(prod.getCodProduto());
			mDescView.setText(prod.getDescricao());
			mValUnitView.setText(prod.getValUnitario() + "");
		}

	}

}
