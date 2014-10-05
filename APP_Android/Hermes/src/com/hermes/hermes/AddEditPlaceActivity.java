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

import com.hermes.hermes.Model.TLocal;
import com.hermes.hermes.db.*;

public class AddEditPlaceActivity extends Activity {

	private TextView mNameView;
	private TextView mAddressView;
	
	private Bundle bundle;
	private TLocal loc;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_edit_place);
		
		
		mNameView = (TextView) findViewById(R.id.editNomeLocal);
		mAddressView = (TextView) findViewById(R.id.editMoradaLocal);
		
		bundle = getIntent().getExtras();
		if (null != bundle && bundle.containsKey("posLocal")) {
            int posLoc = bundle.getInt("posLocal");
            List<TLocal> locs = DatabaseManager.getInstance(getApplicationContext()).getAllLocais();
            
            loc = locs.get(posLoc);
            
            mNameView.setText(loc.getNome());
            mAddressView.setText(loc.getMorada());
    		
        }
    }	
	
}
