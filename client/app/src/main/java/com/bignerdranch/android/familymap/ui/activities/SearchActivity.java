package com.bignerdranch.android.familymap.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;

import com.bignerdranch.android.familymap.R;
import com.bignerdranch.android.familymap.model.Event;
import com.bignerdranch.android.familymap.model.Model;
import com.bignerdranch.android.familymap.model.Person;
import com.bignerdranch.android.familymap.ui.recycler.view.adapters.SearchResultAdapter;
import com.bignerdranch.android.familymap.ui.recycler.view.model.SearchResult;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mSearchResultAdapter;

    private List<SearchResult> mSearchResultList;

    private EditText mSearchBox;
    private ImageView mSearchIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setTitle("Family Map: Search");

        mRecyclerView = (RecyclerView) findViewById(R.id.search_recycler_view);
        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mSearchResultList = new ArrayList<>();

        mSearchBox = (EditText) findViewById(R.id.search_bar);
        mSearchIcon = (ImageView) findViewById(R.id.search_icon);
        mSearchIcon.setImageDrawable(new IconDrawable(this, FontAwesomeIcons.fa_search));

        mSearchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                search(mSearchBox.getText());
                StringBuilder str = new StringBuilder();
                str.append("SearchResults: ");
                for(SearchResult string : mSearchResultList){
                    str.append(" " + string.getInformation());
                }
                System.out.println(str.toString());
                mSearchResultAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        setAdapter();
    }

    private void search(CharSequence sequence){
        //Retrieve list of person and events
        Map<String, Person> persons = Model.getModel().getPersons();
        Map<String, Event> events = Model.getModel().getEvents();

        sequence = sequence.toString().toLowerCase();
        mSearchResultList.clear();
        //Search through persons
        for(Person person : persons.values()){
            String firstName = person.getFirstName().toLowerCase();
            String lastName = person.getLastName().toLowerCase();
            if(firstName.contains(sequence) ||
                    lastName.contains(sequence)){

                //Construct a person description string
                StringBuilder resultString = new StringBuilder();
                resultString.append(person.getFirstName() + " " + person.getLastName());

                //Create a SearchResult and add it to the searchResultList
                SearchResult result = new SearchResult(resultString.toString());
                result.setPerson(true);
                result.setEvent(false);
                result.setID(person.getPersonID());
                result.setGender(person.getGender());
                mSearchResultList.add(result);
            }
        }

        //Search through events
        for(Event event : events.values()){
            String country = event.getCountry().toLowerCase();
            String city = event.getCity().toLowerCase();
            String type = event.getEventType().toLowerCase();
            String year = event.getYear().toLowerCase();
            if(country.contains(sequence) ||
                    city.contains(sequence) ||
                    type.contains(sequence) ||
                    year.contains(sequence)){
                if(Model.getModel().getFilters().getEventFilter().get(type)) {
                    //Construct an event description string
                    Person person = persons.get(event.getPersonID());
                    StringBuilder resultString = new StringBuilder();
                    resultString.append(event.getEventType() + ": " + event.getCity() + "  "
                            + event.getCountry() + " (" + event.getYear() + ")\n"
                            + person.getFirstName() + " " + person.getLastName());

                    //Create a SearchResult and add it to the searchResultList
                    SearchResult result = new SearchResult(resultString.toString());
                    result.setEvent(true);
                    result.setPerson(false);
                    result.setID(event.getEventID());
                    mSearchResultList.add(result);
                }
            }
        }
    }

    public void setAdapter(){
        mSearchResultAdapter = new SearchResultAdapter(this, mSearchResultList);
        mRecyclerView.setAdapter(mSearchResultAdapter);
    }
}
