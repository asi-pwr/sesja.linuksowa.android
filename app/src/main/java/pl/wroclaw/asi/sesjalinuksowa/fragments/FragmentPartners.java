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
import it.gmariotti.cardslib.library.view.CardListView;
import pl.wroclaw.asi.sesjalinuksowa.R;
import pl.wroclaw.asi.sesjalinuksowa.models.Partner;
import pl.wroclaw.asi.sesjalinuksowa.models.PartnerCard;

/**
 * Created by fares on 11.04.15.
 */
public class FragmentPartners extends Fragment {

    private List<Partner> partners;
    public String[] partnerType;

    public static Fragment newInstance (List<Partner> partners, String[] partnerType){
        FragmentPartners fp = new FragmentPartners();
        fp.partners = partners;
        fp.partnerType = partnerType;
        return fp;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_partners, container, false);
        List<Card> cardList = prepareCards();

        CardArrayAdapter cardArrayAdapter = new CardArrayAdapter(getActivity(), cardList);
        CardListView listView = (CardListView) layout.findViewById(R.id.fragmentPartnersCardList);
        listView.setAdapter(cardArrayAdapter);

        return layout;
    }

    private List<Card> prepareCards() {
        List<Card> cardList = new ArrayList<>();

        for (Partner p : partners){
            PartnerCard card;
            if (p.getType() < partnerType.length)
                card = new PartnerCard(getActivity(), p, partnerType[p.getType()]);
            else
                card = new PartnerCard(getActivity(), p);
            cardList.add(card);
        }
        return cardList;
    }
}
