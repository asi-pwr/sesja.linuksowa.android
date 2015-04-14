package pl.wroclaw.asi.sesjalinuksowa.models;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import it.gmariotti.cardslib.library.internal.Card;
import pl.wroclaw.asi.sesjalinuksowa.R;
import pl.wroclaw.asi.sesjalinuksowa.fragments.FragmentPartners;

/**
 * Created by fares on 11.04.15.
 */
public class PartnerCard extends Card implements View.OnClickListener {

    private Partner partner;
    private String partnerType;

    public PartnerCard(Context context, Partner partner) {
        super(context, R.layout.list_item_partners);
        this.partner = partner;
    }

    public PartnerCard(Context context, Partner partner, String partnerType) {
        super(context, R.layout.list_item_partners);
        this.partner = partner;
        this.partnerType = partnerType;
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        ImageView logo = (ImageView) parent.findViewById(R.id.partnerCardImage);
        TextView desc = (TextView) parent.findViewById(R.id.partnerCardDesc);
        TextView partnerType = (TextView) parent.findViewById(R.id.partnerCardType);

        if (partnerType != null)
                partnerType.setText(this.partnerType);


        if(partner.getImgUrl() != null) {
            Picasso.with(getContext())
                    .load(partner.getImgUrl())
                    .into(logo);
            logo.setOnClickListener(this);
        }

        if (partner.getDescription() != null)
            desc.setText(partner.getDescription());
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(partner.getWebUrl()));
        getContext().startActivity(intent);
    }
}
