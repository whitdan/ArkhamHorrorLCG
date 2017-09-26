package com.whitdan.arkhamhorrorlcgcampaignguide.A_Menus;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.whitdan.arkhamhorrorlcgcampaignguide.R;

/*
    TODO:   Custom chaos bag
    TODO:   Chaos token text
    TODO:   Tablet design
    TODO:   Samsung Galaxy S5 layout bug
    TODO:   Improve clicking +/- buttons
    TODO:   Translations
    TODO:   Edit previous scenario results
    TODO:   Every scenario in standalone mode
    TODO:   Prevent restart on orientation change

 */

/*
    MainMenuActivity - This is the main activity, and shows the title as well as buttons to take users to either
    start a new campaign, load an existing campaign, or select a standalone scenario; all of these buttons simply
    take the user to the relevant activity
 */

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_main_menu);

        // Get all of the button views
        Button NewCampaignButton = findViewById(R.id.new_campaign_button);
        Button LoadCampaignButton = findViewById(R.id.load_campaign_button);
        Button StandaloneButton = findViewById(R.id.standalone_scenario_button);
        Button ExpansionsButton = findViewById(R.id.expansions_owned_button);

        // Set correct font to all of the buttons
        Typeface teutonic = Typeface.createFromAsset(getAssets(), "fonts/teutonic.ttf");
        NewCampaignButton.setTypeface(teutonic);
        LoadCampaignButton.setTypeface(teutonic);
        StandaloneButton.setTypeface(teutonic);
        ExpansionsButton.setTypeface(teutonic);

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

        // Set onClickListener to the ExpansionsButton to open a dialog for selecting expansions owned
        ExpansionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment expansionsDialog = new ExpansionsDialog();
                expansionsDialog.show(getFragmentManager(), "expansions");
            }
        });
    }

    // Dialog which allows editing in the settings which expansions a player owns
    public static class ExpansionsDialog extends DialogFragment{

        boolean dunwichOwned;
        boolean carcosaOwned;
        SharedPreferences settings;
        String dunwichOwnedString;
        String carcosaOwnedString;
        String sharedPrefs;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Get the sharedprefs settings
            sharedPrefs = getActivity().getResources().getString(R.string.expansions_owned);
            dunwichOwnedString = getActivity().getResources().getString(R.string.dunwich_campaign);
            carcosaOwnedString = getActivity().getResources().getString(R.string.carcosa_campaign);
            settings = getActivity().getSharedPreferences(sharedPrefs, 0);
            dunwichOwned = settings.getBoolean(dunwichOwnedString, true);
            carcosaOwned = settings.getBoolean(carcosaOwnedString, true);

            // Get the layout inflater and inflate the view
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            View v = View.inflate(getActivity(), R.layout.a_dialog_expansions_owned, null);

            // Set the checkboxes using the previous settings
            final CheckBox dunwich = v.findViewById(R.id.dunwich_owned);
            final CheckBox carcosa = v.findViewById(R.id.carcosa_owned);
            dunwich.setChecked(dunwichOwned);
            carcosa.setChecked(carcosaOwned);

            // Set fonts
            Typeface arnopro = Typeface.createFromAsset(getActivity().getAssets(), "fonts/arnoprobold.otf");
            dunwich.setTypeface(arnopro);
            carcosa.setTypeface(arnopro);
            Typeface teutonic = Typeface.createFromAsset(getActivity().getAssets(), "fonts/teutonic.ttf");
            TextView title = v.findViewById(R.id.expansions_owned);
            Button cancelButton = v.findViewById(R.id.cancel_button);
            Button okayButton = v.findViewById(R.id.okay_button);
            title.setTypeface(teutonic);
            cancelButton.setTypeface(teutonic);
            okayButton.setTypeface(teutonic);

            // Save the settings on clicking okay
            okayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Save the settings per which checkboxes are checked
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putBoolean(dunwichOwnedString, dunwich.isChecked());
                    editor.putBoolean(carcosaOwnedString, carcosa.isChecked());
                    editor.apply();
                    dismiss();
                }
            });

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });

            builder.setView(v);
            return builder.create();
        }
    }
}
