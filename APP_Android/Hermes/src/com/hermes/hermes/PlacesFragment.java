package com.hermes.hermes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.hermes.hermes.Model.TLocal;
import com.hermes.hermes.db.DatabaseManager;
import com.hermes.hermes.service.PlaceImportService;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlacesFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";
	private ArrayAdapter<String> listAdapter;

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static PlacesFragment newInstance(int sectionNumber) {
		PlacesFragment fragment = new PlacesFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public PlacesFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		
		setHasOptionsMenu(true);
		
		final ListView mainListView = (ListView) rootView
				.findViewById(R.id.list);

		/*Button button = (Button) rootView.findViewById(R.id.btnNew);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// user is not logged in redirect him to Login Activity
				Intent i = new Intent(getActivity().getApplicationContext(),
						AddEditPlaceActivity.class);

				// Closing all the Activities
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

				// Add new Flag to start new Activity
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

				// Staring Login Activity
				getActivity().getApplicationContext().startActivity(i);
			}
		});*/

		DatabaseManager db = DatabaseManager.getInstance(getActivity()
				.getApplicationContext());

		List<TLocal> prods = db.getAllLocais();
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
								AddEditPlaceActivity.class);
						i.putExtra("posLocal", position);

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

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class GetDataTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			DataController c = new DataController(getActivity()
					.getApplicationContext());

			// call method to fetch data from server before finishing activity
			c.syncAllData();

			return true;

		}

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
					PlaceImportService.class);
			mServiceIntent.setClass(getActivity().getApplicationContext(),
					PlaceImportService.class);
			// Starts the IntentService
			getActivity().startService(mServiceIntent);

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
