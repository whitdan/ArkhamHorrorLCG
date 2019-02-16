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

public class LabyrinthsSelectActivity extends AppCompatActivity {

    GlobalVariables globalVariables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_labyrinths_select);
        globalVariables = (GlobalVariables) this.getApplication();

        // Get the button views for all of the campaigns
        Button aButton = findViewById(R.id.a_button);
        Button bButton = findViewById(R.id.b_button);
        Button cButton = findViewById(R.id.c_button);

        // Set correct font to all of the buttons
        Typeface teutonic = Typeface.createFromAsset(getAssets(), "fonts/teutonic.ttf");
        aButton.setTypeface(teutonic);
        bButton.setTypeface(teutonic);
        cButton.setTypeface(teutonic);

        // Attach click listener to each button
        aButton.setOnClickListener(new LabyrinthsClickListener());
        bButton.setOnClickListener(new LabyrinthsClickListener());
        cButton.setOnClickListener(new LabyrinthsClickListener());

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

    private class LabyrinthsClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            // Set the campaign number and chaos bag to default
            globalVariables.CurrentCampaign = 999;
            globalVariables.ChaosBagID = -1;
            globalVariables.CurrentDifficulty = 1;
            globalVariables.LabyrinthsCounter = 1;

            // Set the scenario number
            switch(view.getId()){
                case R.id.a_button:
                    globalVariables.CurrentScenario = 103;
                    break;
                case R.id.b_button:
                    globalVariables.CurrentScenario = 104;
                    break;
                case R.id.c_button:
                    globalVariables.CurrentScenario = 105;
                    break;
            }
            Intent intent = new Intent(LabyrinthsSelectActivity.this, ScenarioIntroductionActivity.class);
            startActivity(intent);
        }
    }
}
