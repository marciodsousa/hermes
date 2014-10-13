package com.hermes.hermes.view;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.hermes.hermes.R;
import com.hermes.hermes.controller.LocalController;
import com.hermes.hermes.model.TLocal;

public class ViewPlaceActivity extends Activity {

	private TextView mNameView;
	private TextView mAddressView;
	
	private Bundle bundle;
	private TLocal loc;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_place);
		
		
		mNameView = (TextView) findViewById(R.id.editNomeLocal);
		mAddressView = (TextView) findViewById(R.id.editMoradaLocal);
		
		bundle = getIntent().getExtras();
		if (null != bundle && bundle.containsKey("posLocal")) {
            int posLoc = bundle.getInt("posLocal");
            LocalController c = new LocalController(getApplicationContext());
			List<TLocal>locs = c.getAllActivePlaces();
            
            loc = locs.get(posLoc);
            
            mNameView.setText(loc.getNome());
            mAddressView.setText(loc.getMorada());
    		
        }
    }	
	
}
