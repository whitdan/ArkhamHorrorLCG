package com.whitdan.arkhamhorrorlcgcampaignguide.A_Menus;

// TODO: export/import, standalones

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

import com.whitdan.arkhamhorrorlcgcampaignguide.D_Misc.ChaosBagActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.R;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.GlobalVariables;

import java.util.Locale;

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
        Button ChaosBagButton = findViewById(R.id.chaos_bag_button);
        Button SettingsButton = findViewById(R.id.settings_button);

        // Set correct font to all of the buttons
        Typeface teutonic = Typeface.createFromAsset(getAssets(), "fonts/teutonic.ttf");
        NewCampaignButton.setTypeface(teutonic);
        LoadCampaignButton.setTypeface(teutonic);
        StandaloneButton.setTypeface(teutonic);
        ChaosBagButton.setTypeface(teutonic);
        SettingsButton.setTypeface(teutonic);

        // Set onClickListener to the NewCampaignButton to go to the New Campaign screen
        NewCampaignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenuActivity.this, ChooseCampaignActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
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
                overridePendingTransition(0, 0);
            }
        });

        // Set onClickListener to the ChaosBagButton to go to the Chaos Bag screen
        ChaosBagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globalVariables.CurrentCampaign = 1000;
                Intent intent = new Intent(getBaseContext(), ChaosBagActivity.class);
                startActivity(intent);
            }
        });

        // Set onClickListener to the SettingsButton to go to the Settings screen
        SettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), SettingsMenuActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
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
}
