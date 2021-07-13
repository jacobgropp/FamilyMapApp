package com.bignerdranch.android.familymap.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bignerdranch.android.familymap.R;
import com.bignerdranch.android.familymap.model.Filters;
import com.bignerdranch.android.familymap.model.Model;
import com.bignerdranch.android.familymap.ui.recycler.view.adapters.EventFilterAdapter;
import com.bignerdranch.android.familymap.ui.recycler.view.model.EventTypeFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FilterActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mEventFilterAdapter;

    private List<EventTypeFilter> mEventTypeFilterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        setTitle("Family Map: Filters");

        mRecyclerView = (RecyclerView) findViewById(R.id.filter_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mEventTypeFilterList = new ArrayList<>();

        //Retrieve filters from the model class
        Filters fetchedFilters = Model.getModel().getFilters();

        //Retrieve event filters from the model class
        Map<String, Boolean> fetchedEventFilters = fetchedFilters.getEventFilter();

        for(String eventType : fetchedEventFilters.keySet()){
            EventTypeFilter newFilter = new EventTypeFilter(eventType,
                    fetchedEventFilters.get(eventType));

            mEventTypeFilterList.add(newFilter);
        }

        //Retrieve father and mother side filters from the model class
        boolean fatherSideFilter = fetchedFilters.isFatherSideFilter();
        boolean motherSideFilter = fetchedFilters.isMotherSideFilter();

        EventTypeFilter fatherSideItem = new EventTypeFilter("Father's Side",
                fatherSideFilter);
        EventTypeFilter motherSideItem = new EventTypeFilter("Mother's Side",
                motherSideFilter);

        //Add father and mother event filters to the list
        mEventTypeFilterList.add(fatherSideItem);
        mEventTypeFilterList.add(motherSideItem);

        //Retrieve male and female filters
        boolean maleEventFilter = fetchedFilters.isMaleEventFilter();
        boolean femaleEventFilter = fetchedFilters.isFemaleEventFilter();

        EventTypeFilter maleEventItem = new EventTypeFilter("Male", maleEventFilter);
        EventTypeFilter femaleEventItem = new EventTypeFilter("Female", femaleEventFilter);

        //Add male and female event filter to the list
        mEventTypeFilterList.add(maleEventItem);
        mEventTypeFilterList.add(femaleEventItem);

        mEventFilterAdapter = new EventFilterAdapter(this, mEventTypeFilterList);

        mRecyclerView.setAdapter(mEventFilterAdapter);

    }
}
