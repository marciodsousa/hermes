package com.hermes.hermes.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.hermes.hermes.R;
import com.hermes.hermes.controller.ProdutoController;
import com.hermes.hermes.model.TProduto;
import com.hermes.hermes.service.ProductImportService;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProductsFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";
	private ArrayAdapter<String> listAdapter;

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static ProductsFragment newInstance(int sectionNumber) {
		ProductsFragment fragment = new ProductsFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public ProductsFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);

		setHasOptionsMenu(true);

		final ListView mainListView = (ListView) rootView
				.findViewById(R.id.list);

		ProdutoController c = new ProdutoController(getActivity()
				.getApplicationContext());

		List<TProduto> prods = c.getAllActiveProducts();
		
		String[] values = new String[prods.size()];

		for (int i = 0; i < prods.size(); i++)
			values[i] = prods.get(i).getNome();

		ArrayList<String> itemList = new ArrayList<String>();
		itemList.addAll(Arrays.asList(values));

		// Create ArrayAdapter using the planet list.
		listAdapter = new ArrayAdapter<String>(getActivity(),
				R.layout.simplerow, itemList);

		// Set the ArrayAdapter as the ListView's adapter.
		mainListView.setAdapter(listAdapter);

		mainListView.setClickable(true);
		mainListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {

						Object o = mainListView.getItemAtPosition(position);

						// user is not logged in redirect him to Login Activity
						Intent i = new Intent(getActivity()
								.getApplicationContext(),
								ViewProductActivity.class);
						i.putExtra("posProduto", position);

						// Closing all the Activities
						i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

						// Add new Flag to start new Activity
						i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

						// Staring Login Activity
						getActivity().getApplicationContext().startActivity(i);
					}
				});

		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MainActivity) activity).onSectionAttached(getArguments().getInt(
				ARG_SECTION_NUMBER));
	}


	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.datafragments, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// handle item selection
		switch (item.getItemId()) {
		case R.id.menu_import:
			Intent mServiceIntent = new Intent(getActivity(),
					ProductImportService.class);
			mServiceIntent.setClass(getActivity().getApplicationContext(),
					ProductImportService.class);
			// Starts the IntentService
			getActivity().startService(mServiceIntent);

			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	

}


