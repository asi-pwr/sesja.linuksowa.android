package pl.wroclaw.asi.sesjalinuksowa.models;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import pl.wroclaw.asi.sesjalinuksowa.R;

/**
 * Created by fares on 11.04.15.
 */
public class LectureCard extends Card {

    private Lecture lecture;
    private List<Speaker> speakersList;
    private String place;

    public LectureCard(Context context, Lecture lecture, List<Speaker> speakers) {
        super(context, R.layout.list_item_lectures);
        this.lecture = lecture;
        this.speakersList = speakers;
    }

    public LectureCard(Context context, Lecture lecture, List<Speaker> speakers, String place) {
        super(context, R.layout.list_item_lectures);
        this.lecture = lecture;
        this.speakersList = speakers;
        this.place = place;
    }


    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        TextView title = (TextView) parent.findViewById(R.id.lectureCardItemTitle);
        TextView startH = (TextView) parent.findViewById(R.id.lectureCardItemStartHour);
        TextView endH = (TextView) parent.findViewById(R.id.lectureCardItemEndHour);
        TextView place = (TextView) parent.findViewById(R.id.lectureCardItemPlace);
        TextView speakers = (TextView) parent.findViewById(R.id.lectureCardItemSpeakers);

        if (lecture.getTitle() != null)
            title.setText(lecture.getTitle());
        if (lecture.getStartHour() != null)
            startH.setText(lecture.getStartHour() + " ");
        if (lecture.getEndHour() != null)
            endH.setText(lecture.getEndHour());
        if (this.place != null)
            place.setText(this.place);
        if (speakersList != null){
            if (!speakersList.isEmpty())
                speakers.setText(speakersToString(speakersList));
        }
    }

    private String speakersToString(List<Speaker> speakersList) {
        StringBuilder sb = new StringBuilder();
        for (Speaker s : speakersList){
            sb.append(s.getName()).append(" ").append(s.getSurname());
            sb.append("\n");
        }
        return sb.toString();
    }
}
