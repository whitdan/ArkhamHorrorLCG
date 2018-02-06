package com.whitdan.arkhamhorrorlcgcampaignguide.A_Menus;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.whitdan.arkhamhorrorlcgcampaignguide.C_Scenario.ScenarioIntroductionActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.R;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.GlobalVariables;

public class StandaloneActivity extends AppCompatActivity {

    GlobalVariables globalVariables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_standalone);
        globalVariables = (GlobalVariables) this.getApplication();

        // Get the button views for all of the campaigns
        Button rougarouButton = findViewById(R.id.rougarou_button);
        Button carnevaleButton = findViewById(R.id.carnevale_button);

        // Set correct font to all of the buttons
        Typeface teutonic = Typeface.createFromAsset(getAssets(), "fonts/teutonic.ttf");
        rougarouButton.setTypeface(teutonic);
        carnevaleButton.setTypeface(teutonic);

        // Attach click listener to each button
        rougarouButton.setOnClickListener(new StandaloneClickListener());
        carnevaleButton.setOnClickListener(new StandaloneClickListener());

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

    private class StandaloneClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            // Set the campaign number and chaos bag to default
            globalVariables.CurrentCampaign = 999;
            globalVariables.ChaosBagID = -1;
            globalVariables.CurrentDifficulty = 1;

            // Set the scenario number
            switch(view.getId()){
                case R.id.rougarou_button:
                    globalVariables.CurrentScenario = 101;
                    break;
                case R.id.carnevale_button:
                    globalVariables.CurrentScenario = 102;
                    break;
            }
            Intent intent = new Intent(StandaloneActivity.this, ScenarioIntroductionActivity.class);
            startActivity(intent);
        }
    }
}
