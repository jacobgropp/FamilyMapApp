package com.bignerdranch.android.familymap.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bignerdranch.android.familymap.R;

import com.bignerdranch.android.familymap.server.async.LoginTask;
import com.bignerdranch.android.familymap.server.async.RegisterTask;
import com.bignerdranch.android.familymap.model.Model;

import request.LoginRequest;
import request.RegisterRequest;

/**
 * Created by jakeg on 3/22/2018.
 */

public class LoginFragment extends Fragment {

    private EditText mServerPort;
    private EditText mServerHost;
    private EditText mUsername;
    private EditText mPassword;
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mEmail;
    private RadioGroup mGender;
    private RadioButton mMaleButton;
    private RadioButton mFemaleButton;

    private Button mLoginButton;
    private Button mRegisterButton;

    public LoginFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        //Initialize all the login fragment private variables
        mServerPort = (EditText) v.findViewById(R.id.port);
        mServerHost= (EditText) v.findViewById(R.id.host);
        mUsername = (EditText) v.findViewById(R.id.username);
        mPassword = (EditText) v.findViewById(R.id.password);
        mFirstName = (EditText) v.findViewById(R.id.firstname);
        mLastName = (EditText) v.findViewById(R.id.lastname);
        mEmail = (EditText) v.findViewById(R.id.email);
        mGender = (RadioGroup) v.findViewById(R.id.GenderButtons);
        mMaleButton = (RadioButton) v.findViewById(R.id.male_button);
        mFemaleButton = (RadioButton) v.findViewById(R.id.female_button);

        //Login Button Action
        mLoginButton = (Button) v.findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        //Register Button Action
        mRegisterButton = (Button) v.findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        return v;
    }

    private void login() {
        //Check to make sure serverPort, serverHost, username, and password are all filed in
        if(TextUtils.isEmpty(mServerHost.getText())){
            Toast.makeText(this.getContext(), "Please enter Server Host.", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(mServerPort.getText())){
            Toast.makeText(this.getContext(), "Please enter Server Port.", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(mUsername.getText())){
            Toast.makeText(this.getContext(), "Please enter Username.", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(mPassword.getText())) {
            Toast.makeText(this.getContext(), "Please enter password.", Toast.LENGTH_SHORT).show();
            return;
        }

        //Create a LoginRequest to send to the com.bignerdranch.android.familymap.server
        LoginRequest request = new LoginRequest(mUsername.getText().toString(), mPassword.getText().toString());

        //Disable the register and login button
        mRegisterButton.setEnabled(false);
        mLoginButton.setEnabled(false);

        //Save login information to the model
        Model.getModel().setServerHost(mServerHost.getText().toString());
        Model.getModel().setServerPort(mServerPort.getText().toString());
        Model.getModel().setUsername(mUsername.getText().toString());
        Model.getModel().setPassword(mPassword.getText().toString());

        System.out.println("Host: " + Model.getModel().getServerHost());
        System.out.println("Password: " + Model.getModel().getServerPort());

        //Create a new Async task to carry out the API request
        LoginTask task = new LoginTask(mServerHost.getText().toString(),
                mServerPort.getText().toString(), this);

        task.execute(request);

        //Enable the register and login button
        mRegisterButton.setEnabled(true);
        mLoginButton.setEnabled(true);

        System.out.println("Login was successful!");
    }

    private void register() {
        //Check to make sure serverPort, serverHost, username, password, firstName, lastName,
        // email, and gender are all filled in.
        if(TextUtils.isEmpty(mServerHost.getText())){
            System.out.println(mServerHost.getText());
            Toast.makeText(this.getContext(), "Please enter Server Host.", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(mServerPort.getText())){
            Toast.makeText(this.getContext(), "Please enter Server Port.", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(mUsername.getText())){
            Toast.makeText(this.getContext(), "Please enter Username.", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(mPassword.getText())){
            Toast.makeText(this.getContext(), "Please enter Password.", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(mFirstName.getText())){
            Toast.makeText(this.getContext(), "Please enter First Name.", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(mLastName.getText())){
            Toast.makeText(this.getContext(), "Please enter Last Name.", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(mEmail.getText())){
            Toast.makeText(this.getContext(), "Please enter Email.", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(mGender.getCheckedRadioButtonId() == -1){
            Toast.makeText(this.getContext(), "Please select Gender.", Toast.LENGTH_SHORT).show();
            return;
        }

        //Create a register request to send to the com.bignerdranch.android.familymap.server
        RegisterRequest request = new RegisterRequest(mUsername.getText().toString(),
                mPassword.getText().toString());
        request.setEmail(mEmail.getText().toString());
        request.setFirstName(mFirstName.getText().toString());
        request.setLastName(mLastName.getText().toString());

        //Determine the gender selected
        if(mMaleButton.isChecked()){
            request.setGender("m");
        }
        else{
            request.setGender("f");
        }

        //Disable the register and login button
        mRegisterButton.setEnabled(false);
        mLoginButton.setEnabled(false);

        //Create a new Async task to carry out the API request
        RegisterTask task = new RegisterTask(mServerHost.getText().toString(),
                mServerPort.getText().toString(), this);

        //Run the Async task to send a request
        task.execute(request);

        //Enable the register and login button
        mRegisterButton.setEnabled(true);
        mLoginButton.setEnabled(true);

        System.out.println("Registration was successful!");
    }
}
