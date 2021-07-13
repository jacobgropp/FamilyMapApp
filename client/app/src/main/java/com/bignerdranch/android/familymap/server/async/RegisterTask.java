package com.bignerdranch.android.familymap.server.async;

import android.os.AsyncTask;
import android.widget.Toast;

import com.bignerdranch.android.familymap.ui.activities.MainActivity;

import com.bignerdranch.android.familymap.model.Model;
import request.EventsRequest;
import request.PersonRequest;
import request.PersonsRequest;
import request.RegisterRequest;
import response.EventsResponse;
import response.PersonResponse;
import response.PersonsResponse;
import response.RegisterResponse;
import com.bignerdranch.android.familymap.server.ServerProxy;
import com.bignerdranch.android.familymap.ui.fragments.LoginFragment;
import com.bignerdranch.android.familymap.ui.fragments.MapFragment;

/**
 * Created by jakeg on 3/23/2018.
 */

public class RegisterTask extends AsyncTask<RegisterRequest, Void, RegisterResponse> {

    /**PRIVATE VARAIBLES**/
    private ServerProxy mServerProxy;
    private LoginFragment mLoginFragment;

    private PersonResponse mPersonResponse;
    private PersonsResponse mPersonsResponse;

    private EventsResponse mEventsResponse;

    /**CONSTRUCTOR**/
    public RegisterTask(String serverHost, String serverPort, LoginFragment loginFragment){
        mServerProxy = new ServerProxy(serverHost,
                Integer.parseInt(serverPort));

        this.mLoginFragment = loginFragment;
    }

    @Override
    protected RegisterResponse doInBackground(RegisterRequest... request){
        if(request != null) {
            RegisterResponse response = mServerProxy.registerUser(request[0]);

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
            Toast.makeText(mLoginFragment.getContext(), "Error: Please enter all fields correctly.", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    @Override
    protected void onPostExecute(RegisterResponse response) {
        //Clear the model class
        Model.getModel().clear();
        if(response.getUsername() != null){
            //Store the personID created
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
            return;
        }
        else{
            //Registration was unsuccessful, make an error toast
            Toast.makeText(mLoginFragment.getContext(), response.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
