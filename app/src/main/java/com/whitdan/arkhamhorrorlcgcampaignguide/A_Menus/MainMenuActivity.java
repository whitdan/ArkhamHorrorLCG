package com.whitdan.arkhamhorrorlcgcampaignguide.A_Menus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.whitdan.arkhamhorrorlcgcampaignguide.R;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.GlobalVariables;

import java.util.Locale;

/*
    TODO:   Custom chaos bag

 */

/*
    MainMenuActivity - This is the main activity, and shows the title as well as buttons to take users to either
    start a new campaign, load an existing campaign, or select a standalone scenario; all of these buttons simply
    take the user to the relevant activity
 */

public class MainMenuActivity extends AppCompatActivity {

    static GlobalVariables globalVariables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_main_menu);
        globalVariables = (GlobalVariables) this.getApplication();

        // Update settings
        String languageSetting = getResources().getString(R.string.language_setting);
        String sharedPrefs = getResources().getString(R.string.shared_prefs);
        SharedPreferences settings = getSharedPreferences(sharedPrefs, 0);
        String language = settings.getString(languageSetting, "sys");
        if (!language.equals("sys")) {
            setLocale(this, language);
        }

        // Get all of the button views
        Button NewCampaignButton = findViewById(R.id.new_campaign_button);
        Button LoadCampaignButton = findViewById(R.id.load_campaign_button);
        Button StandaloneButton = findViewById(R.id.standalone_scenario_button);
        Button SettingsButton = findViewById(R.id.settings_button);

        // Set correct font to all of the buttons
        Typeface teutonic = Typeface.createFromAsset(getAssets(), "fonts/teutonic.ttf");
        NewCampaignButton.setTypeface(teutonic);
        LoadCampaignButton.setTypeface(teutonic);
        StandaloneButton.setTypeface(teutonic);
        SettingsButton.setTypeface(teutonic);

        // Set onClickListener to the NewCampaignButton to go to the New Campaign screen
        NewCampaignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenuActivity.this, ChooseCampaignActivity.class);
                startActivity(intent);
            }
        });

        // Set onClickListener to the LoadCampaignButton to go to the Load Campaign screen
        LoadCampaignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), LoadCampaignActivity.class);
                startActivity(intent);
            }
        });

        // Set onClickListener to the StandaloneButton to go to the Standalone Scenario screen
        StandaloneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), StandaloneActivity.class);
                startActivity(intent);
            }
        });

        // Set onClickListener to the SettingsButton to go to the Settings screen
        SettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), SettingsMenuActivity.class);
                startActivity(intent);
            }
        });
    }

    public static void setLocale(Context con, String lang) {
        // Get configuration
        Locale myLocale = new Locale(lang);
        Resources res = con.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        String language = conf.locale.getLanguage();

        if (!language.equals(lang)) {
            // Update locale
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);

            // Refresh activity
            Intent refresh = new Intent(con, MainMenuActivity.class);
            con.startActivity(refresh);
            Activity activity = (Activity) con;
            activity.finish();
        }
    }

    public static void setTitle(TextView title){
        switch (globalVariables.CurrentCampaign) {
            case 1:
                switch (globalVariables.CurrentScenario) {
                    case 1:
                        title.setText(R.string.night_scenario_one);
                        break;
                    case 2:
                        title.setText(R.string.night_scenario_two);
                        break;
                    case 3:
                        title.setText(R.string.night_scenario_three);
                        break;
                }
                break;
            case 2:
                switch (globalVariables.CurrentScenario) {
                    case 1:
                        title.setText(R.string.dunwich_scenario_one);
                        break;
                    case 2:
                        title.setText(R.string.dunwich_scenario_two);
                        break;
                    case 3:
                        title.setText(R.string.dunwich_interlude_one);
                        break;
                    case 4:
                        title.setText(R.string.dunwich_scenario_three);
                        break;
                    case 5:
                        title.setText(R.string.dunwich_scenario_four);
                        break;
                    case 6:
                        title.setText(R.string.dunwich_scenario_five);
                        break;
                    case 7:
                        title.setText(R.string.dunwich_interlude_two);
                        break;
                    case 8:
                        title.setText(R.string.dunwich_scenario_six);
                        break;
                    case 9:
                        title.setText(R.string.dunwich_scenario_seven);
                        break;
                    case 10:
                        title.setText(R.string.dunwich_scenario_eight);
                        break;
                    case 11:
                        title.setText(R.string.dunwich_epilogue);
                        break;
                }
                break;
            case 3:
                switch (globalVariables.CurrentScenario) {
                    case 0:
                        title.setText(R.string.carcosa_lola_prologue);
                        break;
                    case 1:
                        title.setText(R.string.carcosa_scenario_one);
                        break;
                    case 2:
                        title.setText(R.string.carcosa_scenario_two);
                        break;
                    case 3:
                        title.setText(R.string.carcosa_interlude_one);
                        break;
                    case 4:
                        title.setText(R.string.carcosa_scenario_three);
                        break;
                    case 5:
                        title.setText(R.string.carcosa_scenario_four);
                        break;
                    case 6:
                        title.setText(R.string.carcosa_interlude_two);
                        break;
                    case 7:
                        title.setText(R.string.carcosa_scenario_five);
                        break;
                    case 8:
                        title.setText(R.string.carcosa_scenario_six);
                        break;
                    case 9:
                        title.setText(R.string.carcosa_scenario_seven);
                        break;
                    case 10:
                        title.setText(R.string.carcosa_scenario_eight);
                        break;
                    case 11:
                        title.setText(R.string.carcosa_epilogue);
                        break;
                    case 12:
                        title.setText(R.string.campaign_completed);
                        break;
                }
                break;
        }
        if (globalVariables.CurrentScenario > 100) {
            switch (globalVariables.CurrentScenario) {
                case 101:
                    title.setText(R.string.rougarou_scenario);
                    break;
                case 102:
                    title.setText(R.string.carnevale_scenario);
                    break;
            }
        }
    }
}
