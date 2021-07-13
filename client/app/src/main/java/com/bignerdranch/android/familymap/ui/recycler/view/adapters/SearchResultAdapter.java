package com.bignerdranch.android.familymap.ui.recycler.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.android.familymap.R;
import com.bignerdranch.android.familymap.ui.recycler.view.model.SearchResult;
import com.bignerdranch.android.familymap.ui.recycler.view.viewholder.SearchResultViewHolder;

import java.util.List;

/**
 * Created by jakeg on 4/16/2018.
 */

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultViewHolder> {

    private Context mContext;
    private List<SearchResult> mSearchResults;

    public SearchResultAdapter(Context context, List<SearchResult> searchResults) {
        mContext = context;

        mSearchResults = searchResults;
    }

    @Override
    public SearchResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View searchView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_search_result, parent, false);

        return new SearchResultViewHolder(searchView, mContext, mSearchResults);
    }

    @Override
    public void onBindViewHolder(SearchResultViewHolder holder, int position) {
        holder.bind(mSearchResults.get(position));
    }

    @Override
    public int getItemCount() {
        return mSearchResults.size();
    }
}
