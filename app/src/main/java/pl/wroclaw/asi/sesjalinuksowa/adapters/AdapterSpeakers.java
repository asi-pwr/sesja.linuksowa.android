package pl.wroclaw.asi.sesjalinuksowa.adapters;

import android.content.Context;

import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;

/**
 * Created by fares on 11.04.15.
 */
public class AdapterSpeakers extends CardArrayAdapter {

    public AdapterSpeakers(Context context, List<Card> cards) {
        super(context, cards);
    }
}
