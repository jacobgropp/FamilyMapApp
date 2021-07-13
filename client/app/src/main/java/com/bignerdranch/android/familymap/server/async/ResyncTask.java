package com.bignerdranch.android.familymap.server.async;

import android.os.AsyncTask;
import android.widget.Toast;

import com.bignerdranch.android.familymap.model.Model;
import com.bignerdranch.android.familymap.server.ServerProxy;
import com.bignerdranch.android.familymap.ui.activities.SettingsActivity;

import request.EventsRequest;
import request.LoginRequest;
import request.PersonRequest;
import request.PersonsRequest;
import response.EventsResponse;
import response.LoginResponse;
import response.PersonResponse;
import response.PersonsResponse;

/**
 * Created by jakeg on 4/14/2018.
 */

public class ResyncTask extends AsyncTask<LoginRequest, Void, LoginResponse> {

    /**PRIVATE VARIABLES**/
    private ServerProxy mServerProxy;
    private SettingsActivity mSettingsActivity;

    private PersonResponse mPersonResponse;
    private PersonsResponse mPersonsResponse;

    private EventsResponse mEventsResponse;

    /**CONSTRUCTOR**/
    public ResyncTask(String serverHost, String serverPort, SettingsActivity activity){
        mServerProxy = new ServerProxy(serverHost,
                Integer.parseInt(serverPort));

        this.mSettingsActivity = activity;
    }

    @Override
    protected LoginResponse doInBackground(LoginRequest... request) {
        LoginResponse response = mServerProxy.loginUser(request[0]);

        //Find the user's person using their personID.
        PersonRequest personRequest = new PersonRequest(response.getPersonID(), response.getAuthToken());
        mPersonResponse = mServerProxy.findPerson(personRequest);

        //Find all the user's persons
        PersonsRequest personsRequest = new PersonsRequest(response.getAuthToken());
        mPersonsResponse = mServerProxy.findPersons(personsRequest);

        //Find all the user's events
        EventsRequest eventsRequest = new EventsRequest(response.getAuthToken());
        mEventsResponse = mServerProxy.findEvents(eventsRequest);

        return response;
    }

    @Override
    protected void onPostExecute(LoginResponse response) {
        if(response.getUsername() != null){

            System.out.println(mPersonResponse.toString());
            //Store the user's personID
            Model.getModel().storeUserPerson(mPersonResponse.convertToPerson());

            //Store all persons connected to the user
            Model.getModel().storePersons(mPersonsResponse.getPersons());

            //Store all events connected to the user
            Model.getModel().storeEvents(mEventsResponse.getEvents());

            //Store the user's events
            Model.getModel().storePersonsEvents();

            mSettingsActivity.finish();
            return;
        }
        else{
            //Login was unsuccessful, make an error toast
            Toast.makeText(mSettingsActivity, "Failed to re-sync.", Toast.LENGTH_SHORT).show();
            return;
        }
    }

}
