package com.bignerdranch.android.familymap.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jakeg on 4/13/2018.
 */

public class Filters {

    /**PRIVATE VARIABLEs**/
    private Map<String, Boolean> eventFilter;

    private boolean fatherSideFilter;

    private boolean motherSideFilter;

    private boolean maleEventFilter;

    private boolean femaleEventFilter;

    public Filters(){
        fatherSideFilter = true;
        motherSideFilter = true;
        maleEventFilter = true;
        femaleEventFilter = true;
        eventFilter = new HashMap<String, Boolean>();
        for(String eventType : Model.getModel().getEventTypes()){
            eventFilter.put(eventType, true);
        }
    }

    /**GETTERS**/
    public Map<String, Boolean> getEventFilter() {
        return eventFilter;
    }

    public boolean isFatherSideFilter() {
        return fatherSideFilter;
    }

    public boolean isMotherSideFilter() {
        return motherSideFilter;
    }

    public boolean isMaleEventFilter() {
        return maleEventFilter;
    }

    public boolean isFemaleEventFilter() {
        return femaleEventFilter;
    }

    /**SETTERS**/

    public void setFatherSideFilter(boolean fatherSideFilter) {
        this.fatherSideFilter = fatherSideFilter;
    }

    public void setMotherSideFilter(boolean motherSideFilter) {
        this.motherSideFilter = motherSideFilter;
    }

    public void setMaleEventFilter(boolean maleEventFilter) {
        this.maleEventFilter = maleEventFilter;
    }

    public void setFemaleEventFilter(boolean femaleEventFilter) {
        this.femaleEventFilter = femaleEventFilter;
    }

    /**METHODS**/

    public void changeEventFilter(String eventType){
        Map<String, Boolean> newEventFilters = new HashMap<>();
        for(String type : Model.getModel().getEventTypes()){
            if(eventType.equals(type)){
                if(eventFilter.get(type))
                    newEventFilters.put(type, false);
                else
                    newEventFilters.put(type, true);
            }
            else{
                newEventFilters.put(type, eventFilter.get(type));
            }
        }
        eventFilter = newEventFilters;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("FatherSideFilter: " + fatherSideFilter);
        str.append("\nMotherSideFilter: " + motherSideFilter);
        str.append("\nMaleEventFilter: " + maleEventFilter);
        str.append("\nFemaleEventFilter: " + femaleEventFilter);
        str.append("\nEventFilter:");
        for(String string : eventFilter.keySet()){
            str.append("\n\t" + string + "->" + eventFilter.get(string));
        }
        return str.toString();
    }
}
