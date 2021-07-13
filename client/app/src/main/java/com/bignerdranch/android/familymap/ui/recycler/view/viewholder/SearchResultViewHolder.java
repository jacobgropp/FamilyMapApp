package com.bignerdranch.android.familymap.ui.recycler.view.viewholder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bignerdranch.android.familymap.R;
import com.bignerdranch.android.familymap.model.Model;
import com.bignerdranch.android.familymap.ui.activities.EventActivity;
import com.bignerdranch.android.familymap.ui.activities.PersonActivity;
import com.bignerdranch.android.familymap.ui.recycler.view.model.SearchResult;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jakeg on 4/16/2018.
 */

public class SearchResultViewHolder extends RecyclerView.ViewHolder {

    private TextView mSearchResultInformation;
    private ImageView mSearchResultIcon;

    private RelativeLayout mSearchResultLayout;

    private List<SearchResult> mSearchResultList = new ArrayList<>();

    private Context mContext;

    private String id;

    public SearchResultViewHolder(View itemView, Context context, List<SearchResult> searchResultList) {
        super(itemView);

        mSearchResultInformation = (TextView) itemView.findViewById(R.id.search_recycler_view);
        mSearchResultIcon = (ImageView) itemView.findViewById(R.id.search_recycler_view_image);
        mSearchResultLayout = (RelativeLayout) itemView.findViewById(R.id.search_item_layout);

        this.mSearchResultList = searchResultList;

        this.mContext = context;



        mSearchResultLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Switch to person or event activity depending on search result
                int position = getAdapterPosition();
                SearchResult searchResult = mSearchResultList.get(position);

                if(searchResult.getPerson()){
                    Model.getModel().setActivityPerson(Model.getModel().getPersons().get(id));
                    Intent intent = new Intent(mContext, PersonActivity.class);
                    mContext.startActivity(intent);
                }
                else if(searchResult.getEvent()){
                    Model.getModel().setActivityEvent(Model.getModel().getEvents().get(id));
                    Intent intent = new Intent(mContext, EventActivity.class);
                    mContext.startActivity(intent);
                }
            }
        });
    }

    public void bind(final SearchResult item){
        //adapter calls bind on viewholder to switch out data
        if(item.getEvent()){
            mSearchResultIcon.setImageDrawable(new IconDrawable(
                    mContext, FontAwesomeIcons.fa_map_marker));
            mSearchResultInformation.setText(item.getInformation());
        }
        else if(item.getPerson()){
            if(item.getGender().equals("m"))
                mSearchResultIcon.setImageResource(R.drawable.man);
            else
                mSearchResultIcon.setImageResource((R.drawable.woman));

            mSearchResultInformation.setText(item.getInformation());
        }

        id = item.getID();
    }
}
