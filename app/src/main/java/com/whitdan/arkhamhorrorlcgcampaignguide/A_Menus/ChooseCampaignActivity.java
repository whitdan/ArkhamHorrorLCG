package com.whitdan.arkhamhorrorlcgcampaignguide.A_Menus;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.whitdan.arkhamhorrorlcgcampaignguide.B_CampaignSetup.CampaignIntroductionActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.R;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.GlobalVariables;

/*
    ChooseCampaignActivity - Shows buttons for each released campaign to start a new campaign; on button press sets the
    campaign to the relevant number, sets the scenario to 0 (campaign setup), resets the GlobalVariables, and then
    goes to CampaignSetup
 */

public class ChooseCampaignActivity extends AppCompatActivity {

    GlobalVariables globalVariables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_choose_campaign);
        globalVariables = (GlobalVariables) this.getApplication();

        // Get the button views for all of the campaigns
        Button NightButton = findViewById(R.id.night_button);
        Button DunwichButton = findViewById(R.id.dunwich_button);
        Button CarcosaButton = findViewById(R.id.carcosa_button);

        // Set correct font to all of the buttons
        Typeface teutonic = Typeface.createFromAsset(getAssets(), "fonts/teutonic.ttf");
        NightButton.setTypeface(teutonic);
        DunwichButton.setTypeface(teutonic);
        CarcosaButton.setTypeface(teutonic);

        // Attach click listener to each button
        NightButton.setOnClickListener(new NewCampaignClickListener());
        DunwichButton.setOnClickListener(new NewCampaignClickListener());
        CarcosaButton.setOnClickListener(new NewCampaignClickListener());

        // Back button
        Button backButton = findViewById(R.id.back_button);
        backButton.setTypeface(teutonic);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(0, 0);
            }
        });
    }

    // Resets variables, sets the campaign and scenario, then goes to CampaignSetup
    private class NewCampaignClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            resetVariables();

            // Set the right campaign number
            switch(view.getId()){
                case R.id.night_button:
                    globalVariables.CurrentCampaign = 1;
                    break;
                case R.id.dunwich_button:
                    globalVariables.CurrentCampaign = 2;
                    break;
                case R.id.carcosa_button:
                    globalVariables.CurrentCampaign = 3;
                    break;
            }

            // Set the scenario number to 0 to indicate campaign setup
            globalVariables.CurrentScenario = 0;
            Intent intent = new Intent(ChooseCampaignActivity.this, CampaignIntroductionActivity.class);
            startActivity(intent);
        }
    }

    // Resets all of the GlobalVariables which won't otherwise be written to - this prevents a previous campaign's
    // variables from carrying through to a new campaign
    private void resetVariables(){
        globalVariables.InvestigatorsInUse = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0,0,0,0,0,0};
        globalVariables.CurrentDifficulty = 1;
        globalVariables.NightCompleted = 0;
        globalVariables.DunwichCompleted = 0;
        globalVariables.AdamLynchHaroldWalsted = 0;
        globalVariables.TownsfolkAction = 0;
        globalVariables.Rougarou = 0;
        globalVariables.StrangeSolution = 0;
        globalVariables.ArchaicGlyphs = 0;
        globalVariables.Carnevale = 0;
        globalVariables.CarnevaleReward = 0;
        globalVariables.Doubt = 0;
        globalVariables.Conviction = 0;
        globalVariables.ChasingStranger = 0;
        globalVariables.Theatre = 0;
    }
}
