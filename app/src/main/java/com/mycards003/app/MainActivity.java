package com.mycards003.app;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mycards.business.API;
import com.mycards.business.Bank;
import com.mycards.business.Card;
import com.mycards.business.Flag;
import com.mycards.business.Model;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private NavigationDrawerFragment mNavigationDrawerFragment;

    private CharSequence mTitle;

    private ListView lista_dados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        mNavigationDrawerFragment.setUp(R.id.navigation_drawer,(DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, PlaceholderFragment.newInstance(position + 1)).commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
            textView.setText("Selecionado " + Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
            textView.setVisibility(View.INVISIBLE);
            ListView lv = (ListView)rootView.findViewById(R.id.listView);

            final Integer posicao = getArguments().getInt(ARG_SECTION_NUMBER);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    selectItemLista(posicao, i);
                }
            });

            List<Model> lista = new ArrayList<Model>();
            DownloadWebPageTask task = new DownloadWebPageTask();
            switch (posicao) {
                case 1 : {
                    task.execute(lista, new Bank(), getActivity(), rootView);
                    break;
                }
                case 2: {
                    task.execute(lista, new Flag(), getActivity(), rootView);
                    break;
                }
                case 3: {
                    task.execute(lista, new Card(), getActivity(), rootView);
                    break;
                }
            }
            ArrayAdapter<Model> arrayAdapter = new ArrayAdapter<Model>(getActivity(), android.R.layout.simple_list_item_1, lista);

            lv.setAdapter(arrayAdapter);
            return rootView;
        }

        private void selectItemLista(int position, int position_list) {
            //Toast.makeText(getActivity(), "Position: " + position, Toast.LENGTH_SHORT).show();

            try {
                Intent intent = new Intent(getActivity(), CadActivity.class);
                ListView lv = (ListView)getActivity().findViewById(R.id.listView);
                String nome = lv.getItemAtPosition(position_list).toString();
                Parametros.getInstance().nm_banco = nome;
                switch (position) {
                    case 1: {
                        intent = new Intent(getActivity(), CadBancoActivity.class);
                        break;
                    }
                    case 2: {
                        intent = new Intent(getActivity(), CadBandeiraActivity.class);
                        break;
                    }
                    case 3: {
                        intent = new Intent(getActivity(), CadCartaoActivity.class);
                    }
                }
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                //e.printStackTrace();
            }

        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    private static class DownloadWebPageTask extends AsyncTask<Object, Void, Object> {
        private Activity activity;
        private View rootView;

        @Override
        protected Object doInBackground(Object... objects) {
            List<Model> lista = (List<Model>) objects[0];
            try {
                List<Model> models = (List<Model>) new API((Model) objects[1]).list();
                for (Model m : models) {
                    lista.add(m);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.activity = (Activity) objects[2];
            this.rootView = (View) objects[3];
            return lista;
        }

        @Override
        protected void onPostExecute(Object result) {
            ListView lv = (ListView)rootView.findViewById(R.id.listView);
            ArrayAdapter<Model> arrayAdapter = new ArrayAdapter<Model>(activity, android.R.layout.simple_list_item_1, (List<Model>) result);

            lv.setAdapter(arrayAdapter);
        }
    }

}
