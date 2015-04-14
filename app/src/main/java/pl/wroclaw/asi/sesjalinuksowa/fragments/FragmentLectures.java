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
import it.gmariotti.cardslib.library.internal.CardExpand;
import it.gmariotti.cardslib.library.internal.ViewToClickToExpand;
import it.gmariotti.cardslib.library.prototypes.CardSection;
import it.gmariotti.cardslib.library.prototypes.SectionedCardAdapter;
import it.gmariotti.cardslib.library.view.CardListView;
import pl.wroclaw.asi.sesjalinuksowa.R;
import pl.wroclaw.asi.sesjalinuksowa.activities.ActivityBase;
import pl.wroclaw.asi.sesjalinuksowa.models.EventDay;
import pl.wroclaw.asi.sesjalinuksowa.models.Lecture;
import pl.wroclaw.asi.sesjalinuksowa.models.LectureCard;
import pl.wroclaw.asi.sesjalinuksowa.models.Speaker;

/**
 * Created by fares on 10.04.15.
 */
public class FragmentLectures extends Fragment {

    private EventDay selectedDay;
    public String[] placeType;

    public static Fragment newInstance(EventDay selectedDay, String[] placeType){
        FragmentLectures f = new FragmentLectures();
        f.selectedDay = selectedDay;
        f.placeType = placeType;
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_lectures, container, false);
        List<Card> cardList = prepareCards();

        CardArrayAdapter cardArrayAdapter = new CardArrayAdapter(getActivity(), cardList);
        List<CardSection> cardSections = new ArrayList<>();

//        for (LectureSection sec : selectedDay.getLectureSections()){
//            cardSections.add(new CardSection(sec.getPosition(), sec.getHour()));
//        }

        SectionedCardAdapter sectionedCardAdapter = new SectionedCardAdapter(getActivity(), cardArrayAdapter);
        sectionedCardAdapter.setCardSections(cardSections.toArray(new CardSection[cardSections.size()]));

        CardListView listView = (CardListView) layout.findViewById(R.id.fragmentLecturesCardList);
        listView.setExternalAdapter(sectionedCardAdapter, cardArrayAdapter);

        return layout;
    }

    private List<Card> prepareCards() {
        List<Card> cardList = new ArrayList<>();

        for (Lecture l : selectedDay.getLectures()){
            List<Speaker> speakers = ActivityBase.getSpeakers(l.getSpeakersIds());
            LectureCard card;
            if (placeType.length > l.getPlace())
                card = new LectureCard(getActivity(), l, speakers, placeType[l.getPlace()]);
            else
                card = new LectureCard(getActivity(), l, speakers);

            CardExpand expandField = new CardExpand(getActivity());
            expandField.setTitle(l.getDescription());
            card.addCardExpand(expandField);

            ViewToClickToExpand viewToClickToExpand =
                    ViewToClickToExpand.builder()
                            .highlightView(false)
                            .setupCardElement(ViewToClickToExpand.CardElementUI.CARD);

            card.setViewToClickToExpand(viewToClickToExpand);

            cardList.add(card);
        }
        return cardList;
    }
}
