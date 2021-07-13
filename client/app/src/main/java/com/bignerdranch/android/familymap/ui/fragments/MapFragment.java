package com.bignerdranch.android.familymap.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bignerdranch.android.familymap.R;
import com.bignerdranch.android.familymap.model.Filters;
import com.bignerdranch.android.familymap.model.Settings;
import com.bignerdranch.android.familymap.ui.activities.EventActivity;
import com.bignerdranch.android.familymap.ui.activities.FilterActivity;
import com.bignerdranch.android.familymap.ui.activities.MainActivity;
import com.bignerdranch.android.familymap.ui.activities.PersonActivity;
import com.bignerdranch.android.familymap.ui.activities.SearchActivity;
import com.bignerdranch.android.familymap.ui.activities.SettingsActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import com.bignerdranch.android.familymap.model.Event;
import com.bignerdranch.android.familymap.model.Model;
import com.bignerdranch.android.familymap.model.Person;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

/**
 * Created by jakeg on 4/10/2018.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback {

    /**Map Variables**/
    private GoogleMap mMap;
    private List<Polyline> mPolylines = new ArrayList<>();
    private List<PolylineOptions> mPolylineOptions = new ArrayList<>();
    private Marker mLastMarkerClicked;
    private Map<Marker, Event> mEventMarkers = new HashMap<>();
    private Map<Event, Marker> mMarkerEvents = new HashMap<>();

    /**UI Variables**/
    private LinearLayout mEvent;
    private TextView mEventDetails;
    private ImageView mImageIcon;

    private Settings mSettings;
    private Filters mFilters;

    public MapFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mEvent = (LinearLayout) v.findViewById(R.id.personButton);
        mEventDetails = (TextView) v.findViewById(R.id.event);
        mImageIcon = (ImageView) v.findViewById(R.id.genderIcon);

        if(getActivity().getClass() == MainActivity.class) {
            setHasOptionsMenu(true);
        }
        else if(getActivity().getClass() == EventActivity.class){
            setHasOptionsMenu(false);
        }

        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Add event markers to the map
        addMarkers();

        //Re-draw polyline
        clearPolylines();
        buildPolylines();

        //Initial Camera Position
        String userPersonID = Model.getModel().getUsersPerson().getPersonID();
        if(getActivity().getClass() == MainActivity.class) {
            // Move the camera to the user's birth
            Event birthEvent = Model.getModel().getPersonEvents().get(userPersonID).first();
            mMap.moveCamera(CameraUpdateFactory.newLatLng(
                    new LatLng(birthEvent.getLatitude(), birthEvent.getLongitude())));
        }
        else if(getActivity().getClass() == EventActivity.class){
            //Move the camera to the selected event
            Event selectedEvent = Model.getModel().getActivityEvent();
            Marker eventMarker = mMarkerEvents.get(selectedEvent);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(eventMarker.getPosition(), 5.0f));

            if (mSettings.isLifeStoryLinesFilter()) addLifeStoryLine(eventMarker);
            if (mSettings.isFamilyTreeLinesFilter()) addFamilyLine(eventMarker);
            if (mSettings.isSpouseLinesFilter()) addSpouseLine(eventMarker);

            displayEventDetails(selectedEvent);

            mLastMarkerClicked = eventMarker;
        }

        //Set Map type
        setMapType();

        mapListeners();
    }

    @Override
    public void onResume() {
        super.onResume();

        if(Model.getModel().getResync()){
            mMap.clear();
            mFilters = Model.getModel().getFilters();
            mSettings = Model.getModel().getSettings();

            //Check if the lastMarker clicked is still in the database
            Event lastEvent = mEventMarkers.get(mLastMarkerClicked);
            boolean lastMarkerChanged = false;

            //Clear the markers
            mEventMarkers.clear();
            mMarkerEvents.clear();

            //Iterate through map of event filters (on/off)
            for(String eventType : mFilters.getEventFilter().keySet()) {
                //Check if given eventType is filtered on or off
                if(mFilters.getEventFilter().get(eventType)) {
                    //If on, generate markers of that type
                    for (Event event : Model.getModel().getEventsOfType().get(eventType)) {
                        //Add markers for the given eventType
                        Float color = Model.getModel().getEventColors().get(event.getEventType());
                        LatLng latLng = new LatLng(event.getLatitude(), event.getLongitude());
                        Marker marker = mMap.addMarker(new MarkerOptions()
                                .position(latLng)
                                .title(event.getEventType())
                                .icon(BitmapDescriptorFactory.defaultMarker(color)));
                        if(lastEvent != null && event.getEventID().equals(lastEvent.getEventID())){
                            //If lastMarkerClicked is found, make new marker last marker clicked
                            mLastMarkerClicked = marker;
                            lastMarkerChanged = true;
                        }
                        mEventMarkers.put(marker, event);
                        mMarkerEvents.put(event, marker);
                    }
                }
            }
            Model.getModel().setEventMarkers(mEventMarkers);

            if(!lastMarkerChanged){
                mLastMarkerClicked = null;
            }
            else{
                displayEventDetails(mEventMarkers.get(mLastMarkerClicked));
            }

            //Map Resync has been completed. Set Re-Sync status to false.
            Model.getModel().setResync(false);
        }

        if(mMap != null) {
            mSettings = Model.getModel().getSettings();
            mFilters = Model.getModel().getFilters();

            checkEventFilters();

            //Re-draw polyline
            clearPolylines();
            if(mLastMarkerClicked != null && mLastMarkerClicked.isVisible()){
                if(mSettings.isLifeStoryLinesFilter()) addLifeStoryLine(mLastMarkerClicked);
                if(mSettings.isFamilyTreeLinesFilter()) addFamilyLine(mLastMarkerClicked);
                if(mSettings.isSpouseLinesFilter()) addSpouseLine(mLastMarkerClicked);
            }

            //Set Map type
            setMapType();

            //Listen for marker clicks
            mapListeners();
        }
    }

    private void mapListeners(){
        // Set a listener for marker click.
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if(marker.isVisible()) {
                    mLastMarkerClicked = marker;
                    //Find the event connected to the chosen marker
                    Event event = mEventMarkers.get(marker);
                    //Display the event's details
                    displayEventDetails(event);
                    //Clear, if any, polylines on map
                    clearPolylines();
                    //Add the given lines
                    if (mSettings.isLifeStoryLinesFilter()) addLifeStoryLine(marker);
                    if (mSettings.isFamilyTreeLinesFilter()) addFamilyLine(marker);
                    if (mSettings.isSpouseLinesFilter()) addSpouseLine(marker);
                    return true;
                }
                else{
                    return false;
                }
            }
        });

        mEventDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mLastMarkerClicked != null) {
                    //Store models used in the map fragment
                    Event event = mEventMarkers.get(mLastMarkerClicked);
                    Person person = Model.getModel().getPersons().get(event.getPersonID());
                    Model.getModel().setActivityPerson(person);

                    Model.getModel().setEventMarkers(mEventMarkers);
                    Model.getModel().setPolylines(mPolylines);

                    //Create an intent to switch to the PersonActivity
                    Intent intent = new Intent(getActivity(), PersonActivity.class);
                    intent.putExtra("person", person.getPersonID());
                    intent.setFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME);

                    getActivity().startActivity(intent);
                }
            }
        });
    }

    /**MENU ITEMS**/
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.map_menu, menu);

        //Set search icon
        menu.findItem(R.id.search).setIcon(
                new IconDrawable(this.getActivity(), FontAwesomeIcons.fa_search).actionBarSize());
        //Set filter icon
        menu.findItem(R.id.filter).setIcon(
                new IconDrawable(this.getActivity(), FontAwesomeIcons.fa_filter).actionBarSize());
        //Set settings icon
        menu.findItem(R.id.settings).setIcon(
                new IconDrawable(this.getActivity(), FontAwesomeIcons.fa_gear).actionBarSize());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                getActivity().startActivity(intent);
                return true;
            case R.id.filter:
                intent = new Intent(getActivity(), FilterActivity.class);
                getActivity().startActivity(intent);
                return true;
            case R.id.settings:
                intent = new Intent(getActivity(), SettingsActivity.class);
                getActivity().startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**MAP MARKERS**/
    private void addMarkers(){
        //Find the correct filters and settings
        mFilters = Model.getModel().getFilters();
        mSettings = Model.getModel().getSettings();
        //Iterate through map of event filters (on/off)
        for(String eventType : mFilters.getEventFilter().keySet()) {
            //Check if given eventType is filtered on or off
            if(mFilters.getEventFilter().get(eventType)) {
                //If on, generate markers of that type
                for (Event event : Model.getModel().getEventsOfType().get(eventType)) {
                    //Add markers for the given eventType
                    Float color = Model.getModel().getEventColors().get(event.getEventType());
                    LatLng latLng = new LatLng(event.getLatitude(), event.getLongitude());
                    Marker marker = mMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title(event.getEventType())
                            .icon(BitmapDescriptorFactory.defaultMarker(color)));

                    mEventMarkers.put(marker, event);
                    mMarkerEvents.put(event, marker);
                }
            }
        }
        Model.getModel().setEventMarkers(mEventMarkers);
    }

    /**EVENT DETAILS**/
    private void displayEventDetails(Event event){
        //Find the person connected to the chosen event
        Person person = Model.getModel().getPersons().get(event.getPersonID());
        if(person.getGender().equals("m")) {
            mImageIcon.setImageResource(R.drawable.man);
        }
        else{
            mImageIcon.setImageResource(R.drawable.woman);
        }
        String eventDetails = person.getFirstName() + " " + person.getLastName() + "\n" +
                                event.getEventType() + ": " + event.getCity() + ", " +
                                event.getCountry();

        if(event.getYear().equals("") || event.getYear().equals(null)){
            eventDetails = eventDetails + " N/A";
        }
        else {
            eventDetails = eventDetails + " (" + event.getYear() + ")";
        }
        mEventDetails.setText(eventDetails);
    }

    /**POLYLINE CREATION**/
    private void addLifeStoryLine(Marker marker){
        //Find the event connected to the chosen marker
        Event markedEvent = mEventMarkers.get(marker);
        //Find the person connected to the chosen event
        Person person = Model.getModel().getPersons().get(markedEvent.getPersonID());
        //Get the list of this person's life events
        TreeSet<Event> events = Model.getModel().getPersonEvents().get(person.getPersonID());
        //Loop through all the life events chronologically
        lifeStoryLine(events);
    }

    private void addFamilyLine(Marker marker){
        //Find the event connected to the chosen marker
        Event event = mEventMarkers.get(marker);
        //Find the person connected to the chosen event
        Person person = Model.getModel().getPersons().get(event.getPersonID());
        //Draw the family tree
        float width = 12;
        familyTreeLine(event, person, width);
    }

    private void addSpouseLine(Marker marker){
        //Find the event connected to the chosen marker
        Event event = mEventMarkers.get(marker);
        //Find the person connected to the chosen event
        Person person = Model.getModel().getPersons().get(event.getPersonID());
        //Check if the person has a spouse
        Person spouse = Model.getModel().getPersons().get(person.getSpouseID());
        if(spouse != null){
            //Find the spouse's first event
            Event spouseEvent =  Model.getModel().getPersonEvents().get(spouse.getPersonID()).first();
            //Initialize the color of the line
            Integer color = findColor(mSettings.getSpouseLineColor());
            //Create a line from the chosen marker to the spouse's birth event... or earliest event
            float defaultWidth = 12;

            createPolyline(event, spouseEvent, color, defaultWidth);
        }
    }

    private void familyTreeLine(Event event, Person person, float width){
        if(mSettings.isFamilyTreeLinesFilter()) {
            //Initialize the line color
            Integer color = findColor(mSettings.getFamilyTreeLineColor());
            //Get the father
            Person father = Model.getModel().getPersons().get(person.getFatherID());
            if (father != null) {
                //Find the father's first event and create a line
                Event fatherEvent = Model.getModel().getPersonEvents().get(father.getPersonID()).first();
                createPolyline(event, fatherEvent, color, width);
                //Recurse through the father's line
                width = width - 3;
                familyTreeLine(fatherEvent, father, width);
            }
            width = width + 3;
            //Get the mother
            Person mother = Model.getModel().getPersons().get(person.getMotherID());
            if (mother != null) {
                //Find the mother's first event and create a line
                Event motherEvent = Model.getModel().getPersonEvents().get(mother.getPersonID()).first();
                createPolyline(event, motherEvent, color, width);
                //Recurse through the mothers' line
                familyTreeLine(motherEvent, mother, width);
            }
        }
    }

    private void lifeStoryLine(TreeSet<Event> events){
        if(mSettings.isLifeStoryLinesFilter()) {
            //Initialize the line color
            Integer color = findColor(mSettings.getLifeStoryLineColor());
            //Create a list of Latitude and longitudes from the events
            PolylineOptions lifeStory = new PolylineOptions();
            for (Event event : events) {
                if(mMarkerEvents.get(event).isVisible()) {
                    lifeStory.add(new LatLng(event.getLatitude(), event.getLongitude()))
                            .color(getContext().getResources().getColor(color))
                            .width(12);
                }
            }
            mPolylineOptions.add(lifeStory);
            mPolylines.add(mMap.addPolyline(lifeStory));
        }
    }

    private void createPolyline(Event firstEvent, Event secondEvent, Integer color, float width){
        if(mMarkerEvents.get(firstEvent).isVisible() && mMarkerEvents.get(secondEvent).isVisible()) {
            PolylineOptions polylineOptions = new PolylineOptions()
                    .color(getContext().getResources().getColor(color))
                    .add(new LatLng(firstEvent.getLatitude(), firstEvent.getLongitude()),
                            new LatLng(secondEvent.getLatitude(), secondEvent.getLongitude()))
                    .width(width);
            mPolylineOptions.add(polylineOptions);
            mPolylines.add(mMap.addPolyline(polylineOptions));
        }
    }

    private void buildPolylines(){
        for(PolylineOptions option : mPolylineOptions){
            mMap.addPolyline(option);
        }
    }

    private void clearPolylines(){
        if(mPolylines.size() > 0) {
            for (Polyline line : mPolylines) {
                line.remove();
            }
            mPolylines.clear();
        }
        mPolylineOptions.clear();
    }

    private int findColor(String color){
        int lineColor;
        switch (color){
            case "red": lineColor = R.color.red;
                break;
            case "blue": lineColor = R.color.blue;
                break;
            case "cyan": lineColor = R.color.cyan;
                break;
            case "green": lineColor = R.color.green;
                break;
            case "magenta": lineColor = R.color.magenta;
                break;
            case "yellow": lineColor = R.color.yellow;
                break;
            case "white": lineColor = R.color.white;
                break;
            case "black": lineColor = R.color.black;
                break;
            default: lineColor = 0;
        }
        return lineColor;
    }

    /**EVENT FILTERS**/
    private void checkEventFilters(){

        for(Marker eventMarker : mEventMarkers.keySet()){
            eventMarker.setVisible(true);
            Event event = mEventMarkers.get(eventMarker);
            if(!mFilters.getEventFilter().get(event.getEventType())){
                eventMarker.setVisible(false);
            }
            if(!mFilters.isFatherSideFilter() &&
                    Model.getModel().getFatherSide().contains(event.getPersonID())){
                eventMarker.setVisible(false);
            }
            if(!mFilters.isMotherSideFilter() &&
                    Model.getModel().getMotherSide().contains(event.getPersonID())){
                eventMarker.setVisible(false);
            }
            Person person = Model.getModel().getPersons().get(event.getPersonID());
            if(!mFilters.isMaleEventFilter() &&
                    person.getGender().equals("m")){
                eventMarker.setVisible(false);
            }
            if(!mFilters.isFemaleEventFilter() &&
                    person.getGender().equals("f")){
                eventMarker.setVisible(false);
            }
        }
    }

    /**MAP TYPE**/
    private void setMapType(){
        String mapType = mSettings.getMapView();
        switch (mapType){
            case "Normal": mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case "Hybrid": mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case "Satellite": mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case "Terrain": mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
        }
    }
}
