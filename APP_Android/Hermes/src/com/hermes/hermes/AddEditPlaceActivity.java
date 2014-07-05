package com.hermes.hermes;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hermes.hermes.Model.TLocal;
import com.hermes.hermes.db.*;

public class AddEditPlaceActivity extends Activity {

	private EditText mNameView;
	private EditText mAddressView;
	
	private Bundle bundle;
	private TLocal loc;
	private DatabaseManager db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_edit_place);
		
		db = DatabaseManager.getInstance(getApplicationContext());
		
		mNameView = (EditText) findViewById(R.id.editNomeLocal);
		mAddressView = (EditText) findViewById(R.id.editMoradaLocal);
		
		bundle = getIntent().getExtras();
        if (null!=bundle && bundle.containsKey("posLocal")) {
            int posLoc = bundle.getInt("posLocal");
            List<TLocal> locs = DatabaseManager.getInstance(getApplicationContext()).getAllLocais();
            
            loc = locs.get(posLoc);
            
            mNameView.setText(loc.getNome());
            mAddressView.setText(loc.getMorada());
    		
        }
        
        findViewById(R.id.btnSubmit).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						if (null!=bundle && bundle.containsKey("posLocal")) {
							if (validateFields())
							{
								loc.setNome(mNameView.getText().toString());
								loc.setMorada(mAddressView.getText().toString());
					            
					            if (db.updateLocal(loc))
					            	finish();

							}
				        }else{
				        	if (validateFields())
							{
				        		loc = new TLocal();
				        		loc.setIdLocal(0);
				        		loc.setNome(mNameView.getText().toString());
				        		loc.setMorada(mAddressView.getText().toString());
					            
					            if (db.addLocal(loc))
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
		
		if(mNameView.getText().toString().length()<200 && mNameView.getText().toString().length()>2 && 
				mAddressView.getText().toString().length()<200 && mAddressView.getText().toString().length()>3 )
			ret=true;
		
		return ret;
    }	
	
}
