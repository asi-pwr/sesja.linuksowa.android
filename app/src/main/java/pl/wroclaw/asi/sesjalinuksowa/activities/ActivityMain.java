package pl.wroclaw.asi.sesjalinuksowa.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import pl.wroclaw.asi.sesjalinuksowa.R;
import pl.wroclaw.asi.sesjalinuksowa.adapters.AdapterDrawer;
import pl.wroclaw.asi.sesjalinuksowa.fragments.FragmentLectures;
import pl.wroclaw.asi.sesjalinuksowa.fragments.FragmentPartners;
import pl.wroclaw.asi.sesjalinuksowa.fragments.FragmentSpeakers;
import pl.wroclaw.asi.sesjalinuksowa.models.NavigationDrawerListItem;

/**
 * Created by fares on 08.04.15.
 */
public class ActivityMain extends ActivityBase {

    // Toolbar with navigtaion drawer
    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DrawerLayout Drawer;
    private ActionBarDrawerToggle mDrawerToggle;

    // fragments ids
//    public static final int HEADER            = 0;
    public static final int FRAGMENT_SPEAKERS   = 1;
//    public static final int DIV               = 2;
    public static final int FRAGMENT_DAY_1      = 3;
    public static final int FRAGMENT_DAY_2      = 4;
    //    public static final int DIV           = 5;
    public static final int FRAGMENT_PARTNERS    = 6;
    public static final int FRAGMENT_ORGANIZERS = 7;

    // purpose: if try to open same fragment as is currently display -> do nothing
    private static int activeFragment = -1;

    // Drawer titles and images
    private List<NavigationDrawerListItem> drawerItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.navigationDrawerList);
        mRecyclerView.setHasFixedSize(true);


        final GestureDetector mGestureDetector = new GestureDetector(this,
                new GestureDetector.SimpleOnGestureListener() {
                    @Override public boolean onSingleTapUp(MotionEvent e) {
                        return true;
                    }
                });


        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(),motionEvent.getY());

                if(child!=null && mGestureDetector.onTouchEvent(motionEvent)){
                    Drawer.closeDrawers();
                    fragmentTransaction(recyclerView.getChildPosition(child));
                    return true;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            }
        });


        if (drawerItems == null)
            drawerItems = prepareDrawerList();
        mAdapter = new AdapterDrawer(drawerItems);
        // And passing the titles,icons,header view name, header view email,
        // and header view profile picture

        mRecyclerView.setAdapter(mAdapter);                              // Setting the adapter to RecyclerView
        mLayoutManager = new LinearLayoutManager(this);                 // Creating a layout Manager
        mRecyclerView.setLayoutManager(mLayoutManager);                 // Setting the layout Manager


        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);
        mDrawerToggle = new ActionBarDrawerToggle(this, Drawer,toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }

        }; // Drawer Toggle Object Made
        Drawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();


        fragmentTransaction(FRAGMENT_DAY_2);
    }

    private List<NavigationDrawerListItem> prepareDrawerList(){
        String[] titles = getResources().getStringArray(R.array.navigation_drawer_items);
        int[] imagesID = new int[]{
                R.mipmap.ic_speaker,
                0, // divider
                R.mipmap.ic_lecture,
                R.mipmap.ic_lecture,
                0, // divider
                R.mipmap.ic_patron,
                R.mipmap.ic_organizer};

        List<NavigationDrawerListItem> items = new ArrayList<>();
        items.add(createDrawerHeader());

        for (int i = 0; i < titles.length; i++){
            NavigationDrawerListItem newItem = new NavigationDrawerListItem();
            newItem.setTitle(titles[i]);
            newItem.setImageID(imagesID[i]);

            if (i == 1 || i == 4)
                newItem.setItemType(NavigationDrawerListItem.TYPE_DIVIDER);
            else
                newItem.setItemType(NavigationDrawerListItem.TYPE_ITEM);

            items.add(newItem);
        }
        return items;
    }
    private NavigationDrawerListItem createDrawerHeader(){
        NavigationDrawerListItem drawerHeader = new NavigationDrawerListItem();
        drawerHeader.setItemType(NavigationDrawerListItem.TYPE_HEADER);
        drawerHeader.setImageID(R.drawable.sesja_logo);
        drawerHeader.setBackground(R.color.ColorPrimaryDark);
        return drawerHeader;
    }

    public void fragmentTransaction(int fragmentType) {

        // start transaction when want to display different fragment then current
        if (!(activeFragment == fragmentType)) {

//                ((fragmentType == FragmentNavigationDrawer.FRAGMENT_NO_CONNECTION) && !isSecondFragmentActive)) {
            activeFragment = fragmentType;
            // if setting up different fragment then FRAGMENT_NEWS set isNewsFragmentActive to false

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            // only if newsFragment is active add second fragment to back stack
            addFragmentToTransaction(fragmentType, fragmentTransaction);
            fragmentTransaction.commit();
        }
    }

    private void addFragmentToTransaction(int fragmentType, FragmentTransaction fragmentTransaction) {
        String[] placeType;
        switch (fragmentType) {
            case FRAGMENT_SPEAKERS:

                Fragment fragmentSpeakers = FragmentSpeakers.newInstance(speakers);
                fragmentTransaction.replace(R.id.main_screen_container, fragmentSpeakers);
                break;

            case FRAGMENT_DAY_1:
                if (eventDays != null) {
                    if(!eventDays.isEmpty()) {
                        placeType = getResources().getStringArray(R.array.place_type);
                        Fragment fragmentLectures0 = FragmentLectures.newInstance(eventDays.get(0), placeType);
                        fragmentTransaction.replace(R.id.main_screen_container, fragmentLectures0);
                    }
                }
                break;

            case FRAGMENT_DAY_2:
                if (eventDays != null) {
                    if(!eventDays.isEmpty()) {
                        placeType = getResources().getStringArray(R.array.place_type);
                        Fragment fragmentLectures1 = FragmentLectures.newInstance(eventDays.get(1), placeType);
                        fragmentTransaction.replace(R.id.main_screen_container, fragmentLectures1);
                    }
                }
                break;

            case FRAGMENT_PARTNERS:
                String[] partnerTypes = getResources().getStringArray(R.array.partners_type);
                Fragment fragmentPartners = FragmentPartners.newInstance(partners, partnerTypes);
                fragmentTransaction.replace(R.id.main_screen_container, fragmentPartners);
                break;

            case FRAGMENT_ORGANIZERS:
                break;

            default:
                Log.d("test", "I cant find fragment for transaction");
                break;
        }
    }


}
