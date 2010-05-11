/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.av.youeat.android;

import it.av.youeat.android.support.SerialID;
import it.av.youeat.ocm.model.Ristorante;

import java.util.List;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TwoLineListItem;

/**
 * The main activity for the dictionary.  Also displays search results triggered by the search
 * dialog.
 */
public class SearchRistoActivity extends Activity {

    private static final int MENU_SEARCH = 1;

    private TextView mTextView;
    private ListView mList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        setContentView(R.layout.search_risto);
        mTextView = (TextView) findViewById(R.id.textField);
        mList = (ListView) findViewById(R.id.list);

//        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
//            // from click on search results
//            Dictionary.getInstance().ensureLoaded(getResources());
//            String word = intent.getDataString();
//            Dictionary.Word theWord = Dictionary.getInstance().getMatches(word).get(0);
//            launchWord(theWord);
//            finish();
//        } else 
            if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            mTextView.setText(getString(R.string.search_results, query));
            RESTUtils restUtils = new RESTUtils();
            List<Ristorante> ristos = restUtils.findRisto(query);
            RistoAdapter ristoAdapter = new RistoAdapter(ristos);
            mList.setAdapter(new RistoAdapter(ristos));
            mList.setOnItemClickListener(ristoAdapter);
        }

        Log.d("dict", intent.toString());
        if (intent.getExtras() != null) {
            Log.d("dict", intent.getExtras().keySet().toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, MENU_SEARCH, 0, R.string.menu_search)
                .setIcon(android.R.drawable.ic_search_category_default)
                .setAlphabeticShortcut(SearchManager.MENU_KEY);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_SEARCH:
                onSearchRequested();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class RistoAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {

        private final List<Ristorante> ristos;
        private final LayoutInflater mInflater;

        public RistoAdapter(List<Ristorante> ristos) {
            this.ristos = ristos;
            mInflater = (LayoutInflater) SearchRistoActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return ristos.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            TwoLineListItem view = (convertView != null) ? (TwoLineListItem) convertView : createView(parent);
            bindView(view, ristos.get(position));
            return view;
        }

        private TwoLineListItem createView(ViewGroup parent) {
            TwoLineListItem item = (TwoLineListItem) mInflater.inflate(android.R.layout.simple_list_item_2, parent,
                    false);
            item.getText2().setSingleLine();
            item.getText2().setEllipsize(TextUtils.TruncateAt.END);
            return item;
        }

        private void bindView(TwoLineListItem view, Ristorante risto) {
            view.getText1().setText(risto.getName());
            StringBuffer secondLine = new StringBuffer();
            secondLine.append(risto.getCity().getName());
            secondLine.append(" - ");
            secondLine.append(risto.getAddress());
            view.getText2().setText(secondLine.toString());
        }

        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            launchRisto(ristos.get(position));
        }
    }

    private void launchRisto(Ristorante theRisto) {
        Intent next = new Intent();
        next.setClass(this, RistoranteActivity.class);
        next.putExtra(SerialID.RISTORANTE, theRisto);
        startActivity(next);
    }

}
