package it.av.youeat.android;

import it.av.youeat.android.support.SerialID;
import it.av.youeat.ocm.model.Ristorante;

import java.util.List;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MapActivity extends com.google.android.maps.MapActivity {
    private GeoPoint point;
    private MapController mc;
    private Ristorante risto;

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Intent intent = getIntent();
        risto = (Ristorante) intent.getSerializableExtra(SerialID.RISTORANTE);

        setContentView(R.layout.map);
        MapView mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        List<Overlay> mapOverlays = mapView.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.androidmarker);
        MapItemizedOverlay itemizedoverlay = new MapItemizedOverlay(drawable, this);

        point = new GeoPoint((int) (risto.getLatitude() * 1E6), (int) (risto.getLongitude() * 1E6));
        OverlayItem overlayitem = new OverlayItem(point, risto.getName(), risto.getAddress());
        itemizedoverlay.addOverlay(overlayitem);
        mapOverlays.add(itemizedoverlay);
        mc = mapView.getController();
        mc.animateTo(point);
        mc.setZoom(17);
        mapView.invalidate();
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }

}