package com.ugurkaya.vlrickmorty;

import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.tomergoldst.tooltips.ToolTip;
import com.tomergoldst.tooltips.ToolTipsManager;
import com.ugurkaya.vlrickmorty.model.EpisodeResponse;
import com.ugurkaya.vlrickmorty.model.LocationResponse;
import com.ugurkaya.vlrickmorty.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterDetailActivity extends AppCompatActivity  implements ToolTipsManager.TipListener{


    RelativeLayout relativeMainProfile, characterShapeView,parentRelative;
    RelativeLayout firstListRelative, lastListRelative;
    TextView characterName, characterStatus,characterGender,characterType,characterLastKnownLocation,characterOrigin,characterSpecies,toolTipText;
    ImageView characterGenderImage, firstListImage, lastListImage;
    TextView lastListDescription;
    CircleImageView profilePhoto;
    List<String> episodeLinks = new ArrayList<String>();
    String firstEpisodeName = "";
    String episode = "";
    int howManyEpisode = 0;
    int howManyResidents = 0;
    String locationUrl = "";
    String locationName = "";
    String name = "";
    String gender = "";
    String status = "";
    String type = "";
    String species = "";
    String image = "";
    String origin = "";
    EpisodeResponse episodeResponse;

    int genderImage;

    LocationResponse locationResponse;
    ToolTipsManager toolTipsManager;

    private static String episodeAPI = "https://rickandmortyapi.com/api/episode/";
    private static String locationAPI = "https://rickandmortyapi.com/api/location/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_detail);


        initScreen();

        getIntentValues();


        try {
            getEpisode(episode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            if (!locationUrl.equals(""))
                getLocation(locationUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        toolTipsManager = new ToolTipsManager(this);



    }

    private void initScreen() {
        relativeMainProfile = (RelativeLayout) findViewById(R.id.relativeMainProfile);
        characterShapeView = (RelativeLayout) findViewById(R.id.characterShapeView);
        parentRelative = (RelativeLayout) findViewById(R.id.parentRelative);
        characterName = (TextView) findViewById(R.id.characterName);
        characterStatus = (TextView) findViewById(R.id.characterStatus);
        characterType = (TextView) findViewById(R.id.characterType);
        characterLastKnownLocation = (TextView) findViewById(R.id.characterLastKnownLocation);
        characterOrigin = (TextView) findViewById(R.id.characterOrigin);
        characterGender = (TextView) findViewById(R.id.characterGender);
        characterSpecies = (TextView) findViewById(R.id.characterSpecies);
        toolTipText = (TextView) findViewById(R.id.toolTipText);
        characterGenderImage = (ImageView) findViewById(R.id.characterGenderImage);
        profilePhoto = (CircleImageView) findViewById(R.id.profilePhoto);

        firstListRelative = (RelativeLayout) findViewById(R.id.firstListRelative);
        firstListImage = (ImageView) findViewById(R.id.firstListImage);

        lastListRelative = (RelativeLayout) findViewById(R.id.lastListRelative);
        lastListImage = (ImageView) findViewById(R.id.lastListImage);
        lastListDescription = (TextView) findViewById(R.id.lastListDescription);
    }

    private void getIntentValues() {
        episodeLinks = (ArrayList<String>) getIntent().getSerializableExtra("episodes");

        image = getIntent().getStringExtra("image");


        try {
            Glide.with(CharacterDetailActivity.this).load(image).into(profilePhoto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        locationUrl = getIntent().getStringExtra("location").replace(locationAPI, "");

        name = getIntent().getStringExtra("name");
        gender = getIntent().getStringExtra("gender");
        status = getIntent().getStringExtra("status");
        type = getIntent().getStringExtra("type");

        species = getIntent().getStringExtra("species");

        origin = getIntent().getStringExtra("origin");
        howManyEpisode = episodeLinks.size();
        episode = episodeLinks.get(0).replace(episodeAPI, "");
        if (type.equals("")) {
            type = getResources().getString(R.string.noneText);
        }



        if(gender.equals(getResources().getString(R.string.maleText))){
            genderImage = R.drawable.malegender;
        }else if(gender.equals(getResources().getString(R.string.femaleText))){
            genderImage = R.drawable.femalegender;
        }else{
            genderImage = R.drawable.questiongender;
        }



        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        SpannableString spannableGender = new SpannableString(String.format(getResources().getString(R.string.genderText), gender));
        SpannableString spannableType = new SpannableString(String.format(getResources().getString(R.string.typeText), type));
        SpannableString spannableSpecies = new SpannableString(String.format(getResources().getString(R.string.speciesText), species));
        SpannableString spannableStatus = new SpannableString(String.format(getResources().getString(R.string.statusText), status));
       SpannableString spannableOrigin = new SpannableString(String.format(getResources().getString(R.string.originText), origin));
        try{
            spannableGender.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.gold)), 0,8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableGender.setSpan(boldSpan, 0, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableType.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.gold)), 0,6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableType.setSpan(boldSpan, 0, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableSpecies.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.gold)), 0,9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableSpecies.setSpan(boldSpan, 0, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableStatus.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.gold)), 0,8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableStatus.setSpan(boldSpan, 0, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableOrigin.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.gold)), 0,8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableOrigin.setSpan(boldSpan, 0, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        } catch (Exception e) {
            e.printStackTrace();
        }


        try{
            characterGender.setText(spannableGender);
        } catch (Exception e) {
            characterGender.setText(String.format(getResources().getString(R.string.genderText), gender));
            e.printStackTrace();
        }

        try{
            characterType.setText(spannableType);
        } catch (Exception e) {
            characterType.setText(String.format(getResources().getString(R.string.typeText), type));
            e.printStackTrace();
        }


       try{
           characterSpecies.setText(spannableSpecies);
       } catch (Exception e) {
           characterSpecies.setText(String.format(getResources().getString(R.string.speciesText), species));
           e.printStackTrace();
       }

       try{
            characterStatus.setText(spannableStatus);
       } catch (Exception e) {
           characterStatus.setText(String.format(getResources().getString(R.string.statusText), status));
           e.printStackTrace();
       }


        characterGenderImage.setImageResource(genderImage);
        characterName.setText(name);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Handler toolTipHandler = new Handler();
                toolTipHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                            try {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        displayToolTip(String.format(getResources().getString(R.string.tooltipMessage), name),toolTipText);

                                    }
                                }, 500);
                            } catch (Resources.NotFoundException e) {
                                e.printStackTrace();
                            }


                    }
                }, 500);
            }
        });


        try{
            characterOrigin.setText(spannableOrigin);
        } catch (Exception e) {
            characterOrigin.setText(String.format(getResources().getString(R.string.originText), origin));
            e.printStackTrace();
        }




        try {
            Glide.with(CharacterDetailActivity.this).load(image).into(profilePhoto);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void getEpisode(String episode) {
        RetrofitClient.getApi().getEpisode(episode).enqueue(new Callback<EpisodeResponse>() {
            @Override
            public void onResponse(Call<EpisodeResponse> call, Response<EpisodeResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        episodeResponse = new EpisodeResponse();
                        episodeResponse = response.body();




                        firstEpisodeName = episodeResponse.getName();


                        lastListDescription.setText(String.format(getResources().getString(R.string.episodes_description),
                                name, firstEpisodeName, name, String.valueOf(howManyEpisode)));






                    } else {

                    }

                } else {

                }
            }

            @Override
            public void onFailure(Call<EpisodeResponse> call, Throwable t) {

            }
        });
    }


    private void getLocation(String locationUrl) {
        RetrofitClient.getApi().getLocation(locationUrl).enqueue(new Callback<LocationResponse>() {
            @Override
            public void onResponse(Call<LocationResponse> call, Response<LocationResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        locationResponse = new LocationResponse();
                        locationResponse = response.body();


                        locationName = locationResponse.getName();
                        howManyResidents = locationResponse.getResidents().size();
                        characterLastKnownLocation.setText(String.format(getResources().getString(R.string.last_known_location_description),
                                name, locationName, String.valueOf(howManyResidents)));

                    } else {

                    }

                } else {

                }
            }

            @Override
            public void onFailure(Call<LocationResponse> call, Throwable t) {

            }
        });


    }


    private void displayToolTip(String tooltip_msg, TextView tooltip_txt) {
        toolTipsManager.dismissAll();
        int position = ToolTip.POSITION_BELOW;
        int align = ToolTip.ALIGN_CENTER;
        toolTipsManager.findAndDismiss(tooltip_txt);

        ToolTip.Builder builder = new ToolTip.Builder(CharacterDetailActivity.this, tooltip_txt, parentRelative, tooltip_msg, position);
        builder.setAlign(align);
        builder.setBackgroundColor(getResources().getColor(R.color.dark_purple));
        toolTipsManager.show(builder.build());

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toolTipsManager.dismissAll();
            }
        }, 2000);
    }

    @Override
    public void onTipDismissed(View view, int anchorViewId, boolean byUser) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}