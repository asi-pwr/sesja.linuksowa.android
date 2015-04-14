package pl.wroclaw.asi.sesjalinuksowa.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.ViewToClickToExpand;
import it.gmariotti.cardslib.library.view.CardListView;
import pl.wroclaw.asi.sesjalinuksowa.R;
import pl.wroclaw.asi.sesjalinuksowa.models.Speaker;
import pl.wroclaw.asi.sesjalinuksowa.models.SpeakerCard;
import pl.wroclaw.asi.sesjalinuksowa.models.SpeakerCardExp;

/**
 * Created by fares on 10.04.15.
 */
public class FragmentSpeakers extends Fragment {
    private List<Speaker> speakers;
    private List<Card> cardList;

    public static Fragment newInstance(List<Speaker> speakers){
        FragmentSpeakers f = new FragmentSpeakers();
        f.speakers = speakers;
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_speakers, container, false);

        prepareCards();
        CardArrayAdapter cardArrayAdapter = new CardArrayAdapter(getActivity(), cardList);
        CardListView listView = (CardListView) layout.findViewById(R.id.listSpeakers);
        listView.setAdapter(cardArrayAdapter);

        return layout;
    }

    private void prepareCards() {
        cardList = new ArrayList<>();

        for (Speaker s : speakers)
            cardList.add(createCard(s));
    }

    private Card createCard(Speaker speaker){
        SpeakerCard speakerCard = new SpeakerCard(getActivity(), speaker);

        ViewToClickToExpand viewToClickToExpand =
                ViewToClickToExpand.builder()
                        .highlightView(false)
                        .setupCardElement(ViewToClickToExpand.CardElementUI.CARD);
        SpeakerCardExp expandField = new SpeakerCardExp(getActivity(), speaker);

        speakerCard.addCardExpand(expandField);
        speakerCard.setViewToClickToExpand(viewToClickToExpand);
        return speakerCard;
    }
}
