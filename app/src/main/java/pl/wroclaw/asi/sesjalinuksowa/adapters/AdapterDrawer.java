package pl.wroclaw.asi.sesjalinuksowa.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pl.wroclaw.asi.sesjalinuksowa.R;
import pl.wroclaw.asi.sesjalinuksowa.models.NavigationDrawerListItem;

/**
 * Created by fares on 08.04.15.
 */
public class AdapterDrawer extends RecyclerView.Adapter<AdapterDrawer.ViewHolder> {

    private List<NavigationDrawerListItem> items;

    private String headerName;        //String Resource for header View Name
    private int sesjaLogo;        //int Resource for header view headerCircleLogotype picture

    public AdapterDrawer(List<NavigationDrawerListItem> items) {
        this.items = items;
        this.headerName = items.get(0).getTitle();
        this.sesjaLogo = items.get(0).getImageID();
    }


    @Override
    public AdapterDrawer.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == NavigationDrawerListItem.TYPE_ITEM) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_navigation_drawer, parent, false);
            return new ViewHolder(v, viewType);

        } else if (viewType == NavigationDrawerListItem.TYPE_HEADER) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_navigation_drawer_header, parent, false);
            return new ViewHolder(v, viewType);

        } else if (viewType == NavigationDrawerListItem.TYPE_DIVIDER) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_navigation_drawer_divider, parent, false);
            return new ViewHolder(v, viewType);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(AdapterDrawer.ViewHolder holder, int position) {
        if (holder.holderType == NavigationDrawerListItem.TYPE_ITEM) {
            holder.textView.setText(items.get(position).getTitle());
            holder.imageView.setImageResource(items.get(position).getImageID());
        } else if (holder.holderType == NavigationDrawerListItem.TYPE_HEADER) {
            holder.sesjaLogo.setImageResource(sesjaLogo);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getItemType();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        int holderType;

        TextView textView;
        ImageView imageView;
        ImageView sesjaLogo;

        public ViewHolder(View itemView, int ViewType) {
            super(itemView);

            if (ViewType == NavigationDrawerListItem.TYPE_ITEM) {
                textView = (TextView) itemView.findViewById(R.id.rowText);
                imageView = (ImageView) itemView.findViewById(R.id.rowIcon);
                holderType = NavigationDrawerListItem.TYPE_ITEM;
            } else if (ViewType == NavigationDrawerListItem.TYPE_HEADER) {
                sesjaLogo = (ImageView) itemView.findViewById(R.id.navigationDrawerListHeaderImage);
                holderType = NavigationDrawerListItem.TYPE_HEADER;
            }
        }
    }
}
