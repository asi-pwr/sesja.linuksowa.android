package pl.wroclaw.asi.sesjalinuksowa.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import pl.wroclaw.asi.sesjalinuksowa.R;

/**
 * Created by fares on 10.04.15.
 */
public class SpeakerCard extends Card {

    private Speaker speaker;

    public SpeakerCard(Context context, Speaker s) {
        super(context, R.layout.list_item_speakers);
        this.speaker = s;
//        SpeakerCardImage thumbnail = new SpeakerCardImage(mContext);
//        thumbnail.setExternalUsage(true);
//        addCardThumbnail(thumbnail);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        TextView name = (TextView) parent.findViewById(R.id.speakerCardName);
        TextView surname = (TextView) parent.findViewById(R.id.speakerCardSurname);
        ImageView image = (ImageView) parent.findViewById(R.id.speakerCardImage);

        if (speaker.getName() != null)
            name.setText(speaker.getName() + ".");
        if (speaker.getSurname() != null)
            surname.setText(speaker.getSurname());
        if (speaker.getImgUrl() != null) {
            if (!speaker.getImgUrl().isEmpty()) {
                if (readFromDisk(image)) {
                    Picasso.with(getContext())
                            .load(speaker.getImgUrl())
                            .into(image);
                    Picasso.with(getContext())
                            .load(speaker.getImgUrl())
                            .into(target);
                }
            }
        }
    }

    private boolean readFromDisk(ImageView image) {
        try {
            Picasso.with(getContext())
                    .load(Environment.getExternalStorageDirectory().getPath()
                            + "/speakers/" + speaker.getName() + speaker.getSurname() + ".jpg")
                    .into(image);
        } catch (Exception e){
            return false;
        }
        return true;
    }


    private Target target = new Target() {
        @Override
        public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
            new Thread(new Runnable() {
                @Override
                public void run() {

                    File file = new File(Environment.getExternalStorageDirectory().getPath()
                            + "/speakers/" + speaker.getName() + speaker.getSurname() + ".jpg");
                    try {
                        file.createNewFile();
                        FileOutputStream ostream = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, ostream);
                        ostream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }).start();
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
            if (placeHolderDrawable != null) {
            }
        }
    };

}
