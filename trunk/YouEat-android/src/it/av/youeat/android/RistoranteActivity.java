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
import it.av.youeat.ocm.model.RistoranteDescriptionI18n;
import it.av.youeat.ocm.model.Tag;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.maps.MapView;

/**
 * Displays a word and its definition.
 */
public class RistoranteActivity extends Activity {

    private TextView mName;
    private TextView mAddress;
    private TextView mTags;
    private TextView mWWW;
    private TextView mDescription;
    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ristorante);

        mName = (TextView) findViewById(R.id.name);
        mAddress = (TextView) findViewById(R.id.address);
        mTags = (TextView) findViewById(R.id.tags);
        mWWW = (TextView) findViewById(R.id.www);
        mDescription = (TextView) findViewById(R.id.description);

        Intent intent = getIntent();
        Ristorante risto = (Ristorante) intent.getSerializableExtra(SerialID.RISTORANTE);

        mName.setText(risto.getName());
        mAddress.setText(risto.getAddress());
        mWWW.setText(risto.getWww());
        mDescription.setText(extractDescriptions(risto));
        mTags.setText(extractTags(risto));
        //mapView = (MapView) findViewById(R.id.mapview);
    }

    private String extractDescriptions(Ristorante ristorante) {
        StringBuffer description = new StringBuffer();
        for (RistoranteDescriptionI18n desc : ristorante.getDescriptions()) {
            description.append(desc.getDescription());
            description.append("\n\n");
        }
        return description.toString();
    }

    private String extractTags(Ristorante ristorante) {
        StringBuffer tags = new StringBuffer();
        for (Tag tag : ristorante.getTags()) {
            tags.append(tag.getTag());
            tags.append(" ");
        }
        return tags.toString();
    }

//    @Override
//    protected boolean isRouteDisplayed() {
//        return false;
//    }
}
