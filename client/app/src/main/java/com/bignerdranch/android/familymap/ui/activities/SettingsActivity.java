package com.bignerdranch.android.familymap.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;

import com.bignerdranch.android.familymap.R;
import com.bignerdranch.android.familymap.server.async.ResyncTask;
import com.bignerdranch.android.familymap.model.Model;
import com.bignerdranch.android.familymap.model.Settings;

import request.LoginRequest;

public class SettingsActivity extends AppCompatActivity {

    private Settings mSettings;

    private Switch mLifeStorySwitch;
    private Switch mFamilyTreeSwitch;
    private Switch mSpouseSwitch;

    private Spinner mLifeStoryLineSpinner;
    private Spinner mFamilyTreeSpinner;
    private Spinner mSpouseLineSpinner;
    private Spinner mMapTypeSpinner;

    private ArrayAdapter<CharSequence> mColorMenu;
    private ArrayAdapter<CharSequence> mMapViewMenu;

    private RelativeLayout mResyncButton;
    private RelativeLayout mLogoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle("Family Map: Settings");

        //Initialize the switches
        mLifeStorySwitch = (Switch) findViewById(R.id.settings_activity_lifestory_switch);
        mFamilyTreeSwitch = (Switch) findViewById(R.id.settings_activity_family_switch);
        mSpouseSwitch = (Switch) findViewById(R.id.settings_activity_spouse_switch);

        //Intialize the Spinners
        mLifeStoryLineSpinner = (Spinner) findViewById(R.id.settings_activity_lifestory_spinner);
        mFamilyTreeSpinner = (Spinner) findViewById(R.id.settings_activity_family_spinner);
        mSpouseLineSpinner = (Spinner) findViewById(R.id.settings_activity_spouse_spinner);
        mMapTypeSpinner = (Spinner) findViewById(R.id.settings_activity_maptype_spinner);

        //Initialize the color menu
        mColorMenu = ArrayAdapter.createFromResource(this, R.array.color_values, android.R.layout.simple_spinner_item);
        mColorMenu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mLifeStoryLineSpinner.setAdapter(mColorMenu);
        mFamilyTreeSpinner.setAdapter(mColorMenu);
        mSpouseLineSpinner.setAdapter(mColorMenu);


        //Initialize the map menu
        mMapViewMenu = ArrayAdapter.createFromResource(this, R.array.map_menu, android.R.layout.simple_spinner_item);
        mMapViewMenu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mMapTypeSpinner.setAdapter(mMapViewMenu);

        //Initialize the RelativeLayouts
        mResyncButton = (RelativeLayout) findViewById(R.id.RESYNC);
        mLogoutButton = (RelativeLayout) findViewById(R.id.LOGIN);

        settingsListeners();
    }

    @Override
    public void onResume() {
        super.onResume();

        //Grab the current settings
        mSettings = Model.getModel().getSettings();

        //Set spinners to the correct locations
        String color = mSettings.getLifeStoryLineColor();
        mLifeStoryLineSpinner.setSelection(findPosition(color));
        color = mSettings.getFamilyTreeLineColor();
        mFamilyTreeSpinner.setSelection(findPosition(color));
        color = mSettings.getSpouseLineColor();
        mSpouseLineSpinner.setSelection(findPosition(color));
        String map = mSettings.getMapView();
        mMapTypeSpinner.setSelection(findPosition(map));

        //Set switched to the correct locations
        System.out.println("Settings: " + Model.getModel().getSettings().toString());
        boolean checked = mSettings.isLifeStoryLinesFilter();
        mLifeStorySwitch.setChecked(checked);
        checked = mSettings.isFamilyTreeLinesFilter();
        mFamilyTreeSwitch.setChecked(checked);
        checked = mSettings.isSpouseLinesFilter();
        mSpouseSwitch.setChecked(checked);

        settingsListeners();
    }

    @Override
    public void onBackPressed(){
        finish();
    }

    private void settingsListeners(){
        mLifeStoryLineSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String color = (String)parent.getItemAtPosition(position);
                Model.getModel().getSettings().setLifeStoryLineColor(color);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mLifeStorySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Model.getModel().getSettings().setLifeStoryLinesFilter(isChecked);
            }
        });

        mFamilyTreeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String color = (String) parent.getItemAtPosition(position);
                Model.getModel().getSettings().setFamilyTreeLineColor(color);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mFamilyTreeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Model.getModel().getSettings().setFamilyTreeLinesFilter(isChecked);
            }
        });

        mSpouseLineSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String color = (String)parent.getItemAtPosition(position);
                Model.getModel().getSettings().setSpouseLineColor(color);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSpouseSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Model.getModel().getSettings().setSpouseLinesFilter(isChecked);
            }
        });

        mMapTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String mapView = (String)parent.getItemAtPosition(position);
                Model.getModel().getSettings().setMapView(mapView);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        mResyncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resync();
            }
        });
    }

    private void resync(){
        //Get the login information from the model
        String serverHost = Model.getModel().getServerHost();
        String serverPort = Model.getModel().getServerPort();
        String username = Model.getModel().getUsername();
        String password = Model.getModel().getPassword();

        //Save the settings model
        Settings settings = Model.getModel().getSettings();

        //Clear the model class
        Model.getModel().clear();

        //Create a LoginRequest to send to the com.bignerdranch.android.familymap.server
        LoginRequest request = new LoginRequest(username, password);

        System.out.println("ServerPort: " + serverPort);
        //Create a new Async task to carry out the API request
        ResyncTask task = new ResyncTask(serverHost, serverPort, this);

        task.execute(request);

        //Restore previous models
        Model.getModel().setServerHost(serverHost);
        Model.getModel().setServerPort(serverPort);
        Model.getModel().setUsername(username);
        Model.getModel().setPassword(password);
        Model.getModel().setSettings(settings);

        //Set Re-sync to true to notify the map
        Model.getModel().setResync(true);
    }

    private void logout(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("finish", true);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
        finishAffinity();
        finish();
        Model.getModel().clear();
        startActivity(intent);
    }

    private int findPosition(String arg){
        int position;
        switch (arg){
            case "red": position = 0;
                break;
            case "blue": position = 1;
                break;
            case "cyan": position = 2;
                break;
            case "green": position = 3;
                break;
            case "magenta": position = 4;
                break;
            case "yellow": position =5;
                break;
            case "white": position = 6;
                break;
            case "black": position = 7;
                break;
            case "Normal": position = 0;
                break;
            case "Hybrid": position = 1;
                break;
            case "Satellite": position = 2;
                break;
            case "Terrain": position = 3;
                break;
            default: position = 0;
        }

        return position;
    }
}
