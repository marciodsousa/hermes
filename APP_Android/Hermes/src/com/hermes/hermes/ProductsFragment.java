package com.hermes.hermes;


import java.util.ArrayList;
import java.util.List;

import com.hermes.hermes.Model.TProduto;
import com.hermes.hermes.db.DatabaseManager;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class ProductsFragment extends Fragment {
	 ListView listView;
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
	private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlacesFragment newInstance(int sectionNumber) {
    	PlacesFragment fragment = new PlacesFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        
        
        
        listView = (ListView) rootView.findViewById(R.id.list_view);
        
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
        
        setupListView(listView);
        
        
        return rootView;
        
        
        
    }
    
    private void setupListView(ListView lv) {
        final List<TProduto> products = DatabaseManager.getInstance().getAllProdutos();
        
        List<String> titles = new ArrayList<String>();
        for (TProduto p : products) {
            titles.add(p.getNome());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, titles);
        lv.setAdapter(adapter);

//        final Fragment fragment = this;
//        lv.setOnItemClickListener(new OnItemClickListener() {
//
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            	TProduto produto = products.get(position);
//                Intent intent = new Intent(fragment, WishItemListActivity.class);
//                intent.putExtra(Constants.keyWishListId, produto.getId());
//                startActivity(intent);
//            }
//        });
    }
    
    

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}