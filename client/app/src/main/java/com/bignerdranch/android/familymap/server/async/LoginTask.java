package com.bignerdranch.android.familymap.server.async;

import android.os.AsyncTask;
import android.widget.Toast;

import com.bignerdranch.android.familymap.ui.activities.MainActivity;

import com.bignerdranch.android.familymap.model.Model;
import request.EventsRequest;
import request.LoginRequest;
import request.PersonRequest;
import request.PersonsRequest;
import response.EventsResponse;
import response.LoginResponse;
import response.PersonResponse;
import response.PersonsResponse;
import com.bignerdranch.android.familymap.server.ServerProxy;
import com.bignerdranch.android.familymap.ui.fragments.LoginFragment;
import com.bignerdranch.android.familymap.ui.fragments.MapFragment;

/**
 * Created by jakeg on 3/23/2018.
 */

public class LoginTask extends AsyncTask<LoginRequest, Void, LoginResponse> {

    /**PRIVATE VARIABLES**/
    private ServerProxy mServerProxy;
    private LoginFragment mLoginFragment;

    private PersonResponse mPersonResponse;
    private PersonsResponse mPersonsResponse;

    private EventsResponse mEventsResponse;

    /**CONSTRUCTOR**/
    public LoginTask(String serverHost, String serverPort, LoginFragment loginFragment){
        mServerProxy = new ServerProxy(serverHost,
                Integer.parseInt(serverPort));

        this.mLoginFragment = loginFragment;
    }

    @Override
    protected LoginResponse doInBackground(LoginRequest... request) {
        LoginResponse response = mServerProxy.loginUser(request[0]);

        if(response != null) {
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
        else{
            Toast.makeText(mLoginFragment.getContext(), "Error: Please enter all fields correctly.", Toast.LENGTH_LONG).show();
            return null;
        }
    }

    @Override
    protected void onPostExecute(LoginResponse response) {
        if(response.getUsername() != null){

            //Store the user's personID
            Model.getModel().storeUserPerson(mPersonResponse.convertToPerson());

            //Store all persons connected to the user
            Model.getModel().storePersons(mPersonsResponse.getPersons());

            //Store all events connected to the user
            Model.getModel().storeEvents(mEventsResponse.getEvents());

            //Store the user's events
            Model.getModel().storePersonsEvents();

            //Login was successful, make a success toast
            Toast.makeText(mLoginFragment.getContext(), "Welcome " +
                            mPersonResponse.getFirstName() + " " + mPersonResponse.getLastName() + "!",
                    Toast.LENGTH_SHORT).show();
            MapFragment mapFragment = new MapFragment();
            ((MainActivity) mLoginFragment.getActivity()).switchFragment(mapFragment);
        }
        else{
            //Registration was unsuccessful, make an error toast
            Toast.makeText(mLoginFragment.getContext(), "Error: Please enter all fields correctly.", Toast.LENGTH_SHORT).show();
        }
    }

}
