package com.bignerdranch.android.familymap.ui.recycler.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.bignerdranch.android.familymap.R;
import com.bignerdranch.android.familymap.model.Filters;
import com.bignerdranch.android.familymap.model.Model;
import com.bignerdranch.android.familymap.ui.recycler.view.model.EventTypeFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jakeg on 4/14/2018.
 */

public class EventFilterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private TextView mHeader;
    private TextView mDescription;
    private Switch mFilterSwitch;

    private List<EventTypeFilter> mEventTypeFilterList = new ArrayList<>();

    public EventFilterViewHolder(View itemView, List<EventTypeFilter> eventTypeFilterList) {
        super(itemView);

        mHeader = (TextView) itemView.findViewById(R.id.filter_recycler_view_heading);
        mDescription = (TextView) itemView.findViewById(R.id.filter_recycler_view_description);
        mFilterSwitch = (Switch) itemView.findViewById(R.id.filter_recycler_view_switch);
        mFilterSwitch.setClickable(false);

        mEventTypeFilterList = eventTypeFilterList;

        itemView.setOnClickListener(this);
    }

    public void bind(final EventTypeFilter item){
        //adapter calls bind on viewholder to switch out data

        //Create the event header
        StringBuilder eventHead = new StringBuilder();
        eventHead.append(item.getEventType() + " Events\n");
        mHeader.setText(eventHead.toString());

        //Create the event description
        StringBuilder eventDescription = new StringBuilder();
        eventDescription.append("FILTER BY " + item.getEventType().toUpperCase()
                + " EVENTS");
        mDescription.setText(eventDescription.toString());

        //grab the boolean that says if filter is on or off
        boolean eventFilter = item.isSwitchStatus();
        mFilterSwitch.setChecked(eventFilter);
    }

    @Override
    public void onClick(View view){
        int position = getAdapterPosition();
        EventTypeFilter eventFilter = this.mEventTypeFilterList.get(position);

        //User selected filter, change switch
        if(eventFilter.isSwitchStatus()){
            eventFilter.setSwitchStatus(false);
            mFilterSwitch.setChecked(false);
        }
        else {
            eventFilter.setSwitchStatus(true);
            mFilterSwitch.setChecked(true);
        }

        String eventType = eventFilter.getEventType();

        Filters filters = Model.getModel().getFilters();

        Boolean filterStatus = eventFilter.isSwitchStatus();

        if(eventType.equals("Father's Side"))
            filters.setFatherSideFilter(filterStatus);
        else if(eventType.equals("Mother's Side"))
            filters.setMotherSideFilter(filterStatus);
        else if(eventType.equals("Male"))
            filters.setMaleEventFilter(filterStatus);
        else if(eventType.equals("Female"))
            filters.setFemaleEventFilter(filterStatus);
        else{
            //Change the bool for this event in the model class
            Map<String, Boolean> eventFilters = filters.getEventFilter();
            Model.getModel().getFilters().changeEventFilter(eventType);
        }
    }
}

