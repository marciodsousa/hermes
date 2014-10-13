package com.hermes.hermes.view;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.hermes.hermes.R;
import com.hermes.hermes.controller.ClienteController;
import com.hermes.hermes.model.TCliente;

public class ViewClientActivity extends Activity {

	private TextView mNameView;
	private TextView mContactView;
	private TextView mNifView;
	private Bundle bundle;
	private TCliente cli;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_client);

		mNameView = (TextView) findViewById(R.id.editNomeCli);
		mContactView = (TextView) findViewById(R.id.editContactoCli);
		mNifView = (TextView) findViewById(R.id.editNifCli);

		bundle = getIntent().getExtras();
		if (null != bundle && bundle.containsKey("posCliente")) {
			int posCli = bundle.getInt("posCliente");
			ClienteController c = new ClienteController(getApplicationContext());
			List<TCliente>clis = c.getAllActiveClients();

			cli = clis.get(posCli);

			mNameView.setText(cli.getNome());
			mContactView.setText(cli.getContacto());
			mNifView.setText(cli.getNif());

		}
	}

}
