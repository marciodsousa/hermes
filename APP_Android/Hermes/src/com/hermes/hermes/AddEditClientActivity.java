package com.hermes.hermes;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hermes.hermes.Model.TCliente;
import com.hermes.hermes.db.*;

public class AddEditClientActivity extends Activity {

	private EditText mNameView;
	private EditText mContactView;
	private EditText mNifView;
	private Bundle bundle;
	private TCliente cli;
	private DatabaseManager db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_edit_client);
		
		db = DatabaseManager.getInstance(getApplicationContext());
		
		mNameView = (EditText) findViewById(R.id.editNomeCli);
		mContactView = (EditText) findViewById(R.id.editContactoCli);
		mNifView = (EditText) findViewById(R.id.editNifCli);
		
		bundle = getIntent().getExtras();
        if (null!=bundle && bundle.containsKey("posCliente")) {
            int posCli = bundle.getInt("posCliente");
            List<TCliente> clis = DatabaseManager.getInstance(getApplicationContext()).getAllClientes();
            
            cli = clis.get(posCli);
            
            mNameView.setText(cli.getNome());
            mContactView.setText(cli.getContacto());
            mNifView.setText(cli.getNif());
    		
        }
        
        findViewById(R.id.btnSubmit).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						if (null!=bundle && bundle.containsKey("posCliente")) {
							if (validateFields())
							{
								cli.setNome(mNameView.getText().toString());
								cli.setContacto(mContactView.getText().toString());
								cli.setNif(mNifView.getText().toString());
					            
					            if (db.updateCliente(cli))
					            	finish();

							}
				        }else{
				        	if (validateFields())
							{
				        		cli = new TCliente();
				        		cli.setIdCliente(0);
				        		cli.setNome(mNameView.getText().toString());
				        		cli.setContacto(mContactView.getText().toString());
				        		cli.setNif(mNifView.getText().toString());
					            
					            if (db.addCliente(cli))
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
		
		if(mNameView.getText().toString().length()<200 && mNameView.getText().toString().length()>2)
		{
			if(mNifView.getText().toString().length()==9)
			{
				if(mContactView.getText().toString().length()>8 && mContactView.getText().toString().length()<15)
				{
					if (mNifView.getText().toString().matches("[0-9]+") && mContactView.getText().toString().matches("[0-9]+")){
						ret=true;
					}
				}
			}
		}

		return ret;
    }	
	
}
