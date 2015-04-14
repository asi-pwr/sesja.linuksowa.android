package pl.wroclaw.asi.sesjalinuksowa.models;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import it.gmariotti.cardslib.library.internal.CardExpand;
import pl.wroclaw.asi.sesjalinuksowa.R;

/**
 * Created by fares on 10.04.15.
 */
public class SpeakerCardExp extends CardExpand {

    private Speaker speaker;

    public SpeakerCardExp(Context context, Speaker speaker) {
        super(context, R.layout.list_item_speakers_expand);
        this.speaker = speaker;
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        if (view == null) return;

        TextView desc = (TextView) view.findViewById(R.id.speakerCardExpandDesc);
        desc.setText(speaker.getDescription());

        super.setupInnerViewElements(parent, view);
    }
}
