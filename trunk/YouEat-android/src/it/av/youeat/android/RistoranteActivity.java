package it.av.youeat.android;

import it.av.youeat.android.support.SerialID;
import it.av.youeat.ocm.model.Ristorante;
import it.av.youeat.ocm.model.RistoranteDescriptionI18n;
import it.av.youeat.ocm.model.Tag;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RistoranteActivity extends Activity {

    private TextView mName;
    private TextView mAddress;
    private TextView mTags;
    private TextView mWWW;
    private TextView mDescription;
    private Ristorante risto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        risto = (Ristorante) intent.getSerializableExtra(SerialID.RISTORANTE);

        setContentView(R.layout.ristorante);

        mName = (TextView) findViewById(R.id.name);
        mAddress = (TextView) findViewById(R.id.address);
        mTags = (TextView) findViewById(R.id.tags);
        mWWW = (TextView) findViewById(R.id.www);
        mDescription = (TextView) findViewById(R.id.description);
        Button button = (Button) findViewById(R.id.showsMap);
        button.setText("Shows Map");
        button.setClickable(true);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                launchMap(risto);
            }
        });

        mName.setText(risto.getName());
        mAddress.setText(risto.getAddress());
        mWWW.setText(risto.getWww());
        mDescription.setText(extractDescriptions(risto));
        mTags.setText(extractTags(risto));
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

    private void launchMap(Ristorante theRisto) {
        Intent next = new Intent();
        next.setClass(this, MapActivity.class);
        next.putExtra(SerialID.RISTORANTE, theRisto);
        startActivity(next);
    }

}
