package com.suttanan.kok.purseflow.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.suttanan.kok.purseflow.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

//import android.support.v7.app.AppCompatActivity;

/**
 * Created by KOKKOK on 5/4/2016.
 */
public class LoginActivity extends FragmentActivity{

    //facebook
    CallbackManager callbackManager;
    AccessTokenTracker accessTokenTracker;
    AccessToken accessToken;
    private ProfileTracker profileTracker;
    private Profile profile;

    private TextView userNameTxt;
    private ImageView image;

    //butter knife
    @BindView(R.id.login_button) LoginButton loginButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        ButterKnife.bind(this);

        initComponents();

        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions("public_profile");

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                profile = Profile.getCurrentProfile();
                accessToken = AccessToken.getCurrentAccessToken();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                accessToken = currentAccessToken;
            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                profile = currentProfile;
                showUserProfile();
                showUserName();

            }
        };
    }

    private void initComponents() {
        userNameTxt  = (TextView) findViewById(R.id.login_name);
        image = (ImageView) findViewById(R.id.login_image);

        showUserProfile();
        showUserName();

    }

    private void showUserProfile() {
        if(Profile.getCurrentProfile() == null){
            image.setImageResource(R.drawable.anonymous_person);
        }  else {
            profile = Profile.getCurrentProfile();
            String imageUri = profile.getProfilePictureUri(200, 200).toString();
            Glide.with(this).load(imageUri).into(image);


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        profileTracker.stopTracking();
        accessTokenTracker.stopTracking();
    }

    private void showUserName() {
        String userName;
        if(Profile.getCurrentProfile() != null) {
            Profile profile = Profile.getCurrentProfile();
            userName = profile.getFirstName() + " " + profile.getLastName() ;
        } else {
            userName = "Unautherize";
        }
        userNameTxt.setText(userName);
    }
}
