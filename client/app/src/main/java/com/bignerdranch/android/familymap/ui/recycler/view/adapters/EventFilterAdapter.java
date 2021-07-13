package com.bignerdranch.android.familymap.ui.recycler.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.android.familymap.R;
import com.bignerdranch.android.familymap.ui.recycler.view.model.EventTypeFilter;
import com.bignerdranch.android.familymap.ui.recycler.view.viewholder.EventFilterViewHolder;

import java.util.List;

/**
 * Created by jakeg on 4/14/2018.
 */

public class EventFilterAdapter extends RecyclerView.Adapter<EventFilterViewHolder>{

    private Context mContext;
    private List<EventTypeFilter> mEventTypeFilters;

    public EventFilterAdapter(Context context, List<EventTypeFilter> eventTypeFilters) {
        mContext = context;

        mEventTypeFilters = eventTypeFilters;
    }

    @Override
    public EventFilterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View filterView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_event_filter, parent, false);

        return new EventFilterViewHolder(filterView, mEventTypeFilters);
    }

    @Override
    public void onBindViewHolder(EventFilterViewHolder holder, int position) {
        holder.bind(mEventTypeFilters.get(position));
    }

    @Override
    public int getItemCount() {
        return mEventTypeFilters.size();
    }

}

