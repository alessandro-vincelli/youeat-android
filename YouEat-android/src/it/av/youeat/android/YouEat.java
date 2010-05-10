package it.av.youeat.android;

import it.av.youeat.android.support.SerialID;
import it.av.youeat.ocm.model.Ristorante;
import it.av.youeat.ocm.model.combined.RistoranteAndDistance;

import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TwoLineListItem;

public class YouEat extends ListActivity {

    private LocationManager locationManager;
    private RESTUtils restUtils = new RESTUtils();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<RistoranteAndDistance> ristos = restUtils.findRistoOnCurrentPosition(locationManager);
        RistoAdapter wordAdapter = new RistoAdapter(ristos);
        ListView lv = getListView();
        lv.setAdapter(wordAdapter);
        lv.setOnItemClickListener(wordAdapter);
    }

    class RistoAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {

        private final List<RistoranteAndDistance> ristos;
        private final LayoutInflater mInflater;

        public RistoAdapter(List<RistoranteAndDistance> ristos) {
            this.ristos = ristos;
            mInflater = (LayoutInflater) YouEat.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        private void bindView(TwoLineListItem view, RistoranteAndDistance risto) {
            view.getText1().setText(risto.getRistorante().getName());
            StringBuffer secondLine = new StringBuffer();
            secondLine.append(risto.getDistance());
            secondLine.append("m. ");
            secondLine.append(risto.getRistorante().getCity().getName());
            secondLine.append(" - ");
            secondLine.append(risto.getRistorante().getAddress());
            view.getText2().setText(secondLine.toString());
        }

        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            launchRisto(ristos.get(position).getRistorante());
        }
    }

    private void launchRisto(Ristorante theRisto) {
        Intent next = new Intent();
        next.setClass(this, RistoranteActivity.class);
        next.putExtra(SerialID.RISTORANTE, theRisto);
        startActivity(next);
    }

}