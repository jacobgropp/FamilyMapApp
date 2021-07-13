package com.bignerdranch.android.familymap.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.android.familymap.R;
import com.bignerdranch.android.familymap.model.Event;
import com.bignerdranch.android.familymap.model.Model;
import com.bignerdranch.android.familymap.model.Person;
import com.bignerdranch.android.familymap.ui.expandable.list.adapters.PersonExpandableListAdapter;
import com.bignerdranch.android.familymap.ui.expandable.list.pumps.PersonExpandableListDataPump;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PersonActivity extends AppCompatActivity {

    ExpandableListView mExpandableListView;
    ExpandableListAdapter mExpandableListAdapter;
    List<String> mExpandableListHeaders;
    HashMap<String, List<String>> mExpandableListDetail;

    //TextViews
    private TextView mFirstName;
    private TextView mLastName;
    private TextView mGender;

    //ImageView
    private ImageView mGenderIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        setTitle("Family Map: Person Details");

        //Initialize text views and image view
        mFirstName = (TextView) findViewById(R.id.person_activity_first_name);
        mLastName = (TextView) findViewById(R.id.person_activity_last_name);
        mGender = (TextView) findViewById(R.id.person_activity_gender);
        mGenderIcon = (ImageView) findViewById(R.id.person_activity_gender_icon);

        setPersonDetails(Model.getModel().getActivityPerson());

        //Initialize the expandableListView
        mExpandableListView = (ExpandableListView) findViewById(R.id.expandableListView);

        //Find the person's data
        PersonExpandableListDataPump pump = new PersonExpandableListDataPump();
        pump.getData(Model.getModel().getActivityPerson());
        mExpandableListDetail = pump.getExpandableListDetail();

        //Create a list of the list headers
        mExpandableListHeaders = new ArrayList<String>(mExpandableListDetail.keySet());

        //Adapt the expandable list using the expandable list adapter class
        mExpandableListAdapter = new PersonExpandableListAdapter(this, mExpandableListHeaders, mExpandableListDetail);

        //Draws the expandable list to the view window
        mExpandableListView.setAdapter(mExpandableListAdapter);

        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                 HashMap<Integer, Person> mFamilyPersons =  Model.getModel().getFamilyPersons();
                 HashMap<Integer, Event> mPersonLifeEvents = Model.getModel().getPersonLifeEvents();

                //System.out.println("Family persons: " + mFamilyPersons.toString());
                //System.out.println("Life Events: " + mPersonLifeEvents.toString());

                if(groupPosition == 1){
                    Event event = (Event)Model.getModel().getPersonLifeEvents().get(childPosition);
                    Model.getModel().setActivityEvent(event);
                    startNewActivity(true);
                    System.out.println("Event you clicked on: " + event.toString());
                }
                else{
                    //User has selected a person. Create a new PersonActivity
                    Person person = (Person)Model.getModel().getFamilyPersons().get(childPosition);
                    Model.getModel().setActivityPerson(person);
                    startNewActivity(false);
                    System.out.println("Person you clicked on: " + person.toString());
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed(){
        finish();
    }

    private void setPersonDetails(Person person){
        mFirstName.setText(person.getFirstName());
        mLastName.setText(person.getLastName());
        if(person.getGender().equals("m")){
            mGender.setText("Male");
            mGenderIcon.setImageResource(R.drawable.man);
        }
        else{
            mGender.setText("Female");
            mGenderIcon.setImageResource(R.drawable.woman);
        }
    }

    public void startNewActivity(boolean eventActivity){
        if(eventActivity) {
            Intent intent = new Intent(this, EventActivity.class);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(this, PersonActivity.class);
            startActivity(intent);
        }
    }

}