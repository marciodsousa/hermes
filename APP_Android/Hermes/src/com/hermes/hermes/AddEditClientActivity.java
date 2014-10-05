package com.hermes.hermes;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hermes.hermes.Model.TCliente;
import com.hermes.hermes.db.*;

public class AddEditClientActivity extends Activity {

	private TextView mNameView;
	private TextView mContactView;
	private TextView mNifView;
	private Bundle bundle;
	private TCliente cli;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_edit_client);

		mNameView = (TextView) findViewById(R.id.editNomeCli);
		mContactView = (TextView) findViewById(R.id.editContactoCli);
		mNifView = (TextView) findViewById(R.id.editNifCli);

		bundle = getIntent().getExtras();
		if (null != bundle && bundle.containsKey("posCliente")) {
			int posCli = bundle.getInt("posCliente");
			List<TCliente> clis = DatabaseManager.getInstance(
					getApplicationContext()).getAllClientes();

			cli = clis.get(posCli);

			mNameView.setText(cli.getNome());
			mContactView.setText(cli.getContacto());
			mNifView.setText(cli.getNif());

		}
	}

}
