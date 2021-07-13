package com.bignerdranch.android.familymap.ui.recycler.view.model;

/**
 * Created by jakeg on 4/15/2018.
 */

public class EventTypeFilter {

    /**PRIVATE DATA MEMBERS**/
    private String eventType;

    private String description;

    private boolean switchStatus;

    /**CONSTRUCTOR**/
    public EventTypeFilter(String eventType, boolean switchStatus){
        this.eventType = eventType;
        this.switchStatus = switchStatus;
    }

    /**GETTERS**/
    public String getEventType() {
        return eventType;
    }

    public String getDescription() {
        return description;
    }

    public boolean isSwitchStatus() {
        return switchStatus;
    }

    /**SETTERS**/
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSwitchStatus(boolean switchStatus) {
        this.switchStatus = switchStatus;
    }
}
