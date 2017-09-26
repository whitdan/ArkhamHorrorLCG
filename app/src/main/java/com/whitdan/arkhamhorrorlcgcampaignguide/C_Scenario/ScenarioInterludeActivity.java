package com.whitdan.arkhamhorrorlcgcampaignguide.C_Scenario;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.whitdan.arkhamhorrorlcgcampaignguide.A_Menus.MainMenuActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.D_Misc.CampaignFinishedActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.R;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.GlobalVariables;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class ScenarioInterludeActivity extends AppCompatActivity {

    static GlobalVariables globalVariables;
    int resolution;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // If app is reopening after the process is killed, kick back to the main menu (stops the activity from
        // showing up unpopulated)
        if (savedInstanceState != null) {
            Intent intent = new Intent(ScenarioInterludeActivity.this, MainMenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_activity_scenario_interlude);
        globalVariables = (GlobalVariables) this.getApplication();

        // Set typefaces
        TextView title = findViewById(R.id.current_scenario_name);
        Typeface teutonic = Typeface.createFromAsset(getAssets(), "fonts/teutonic.ttf");
        title.setTypeface(teutonic);
        final TextView introduction = findViewById(R.id.introduction_text);
        Typeface arnoproitalic = Typeface.createFromAsset(getAssets(), "fonts/arnoproitalic.otf");
        introduction.setTypeface(arnoproitalic);
        final TextView introductionOne = findViewById(R.id.introduction_text_additional_one);
        TextView introductionTwo = findViewById(R.id.introduction_text_additional_two);
        TextView introductionThree = findViewById(R.id.introduction_text_additional_three);
        TextView introductionFour = findViewById(R.id.introduction_text_additional_four);
        TextView introductionFive = findViewById(R.id.introduction_text_additional_five);
        TextView introductionSix = findViewById(R.id.introduction_text_additional_six);
        introductionOne.setTypeface(arnoproitalic);
        introductionTwo.setTypeface(arnoproitalic);
        introductionThree.setTypeface(arnoproitalic);
        introductionFour.setTypeface(arnoproitalic);
        introductionFive.setTypeface(arnoproitalic);
        introductionSix.setTypeface(arnoproitalic);
        RadioGroup introductionOptions = findViewById(R.id.introduction_options);
        final RadioButton introductionOptionOne = findViewById(R.id.introduction_option_one);
        final RadioButton introductionOptionTwo = findViewById(R.id.introduction_option_two);
        final RadioButton introductionOptionThree = findViewById(R.id.introduction_option_three);
        Typeface arnopro = Typeface.createFromAsset(getAssets(), "fonts/arnopro.otf");
        introductionOptionOne.setTypeface(arnopro);
        introductionOptionTwo.setTypeface(arnopro);
        introductionOptionThree.setTypeface(arnopro);

        // Set text and apply any resolutions
        switch (globalVariables.CurrentCampaign) {
            case 2:
                switch (globalVariables.CurrentScenario) {
                    case 3:
                        title.setText(R.string.dunwich_interlude_one);
                        if (globalVariables.InvestigatorsUnconscious == 1) {
                            introduction.setText(R.string.armitage_interlude_one);
                            globalVariables.HenryArmitage = 0;
                            for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                globalVariables.Investigators.get(i).AvailableXP += 2;
                            }
                        } else {
                            introduction.setText(R.string.armitage_interlude_two);
                            globalVariables.HenryArmitage = 1;
                        }
                        break;
                    case 7:
                        title.setText(R.string.dunwich_interlude_two);
                        boolean powder = false;
                        introduction.setText(R.string.survivors_interlude);
                        if (globalVariables.HenryArmitage == 3) {
                            introductionOne.setVisibility(VISIBLE);
                            introductionOne.setText(R.string.survivors_interlude_one);
                            powder = true;
                        }
                        if (globalVariables.WarrenRice == 3) {
                            introductionTwo.setVisibility(VISIBLE);
                            introductionTwo.setText(R.string.survivors_interlude_two);
                            powder = true;
                        }
                        if (globalVariables.FrancisMorgan == 3) {
                            introductionThree.setVisibility(VISIBLE);
                            introductionThree.setText(R.string.survivors_interlude_three);
                            powder = true;
                        }
                        if (powder) {
                            introductionFour.setVisibility(VISIBLE);
                            introductionFour.setText(R.string.survivors_interlude_powder);
                        }
                        if (globalVariables.ZebulonWhateley == 0) {
                            introductionFive.setVisibility(VISIBLE);
                            introductionFive.setText(R.string.survivors_interlude_four);
                        }
                        if (globalVariables.EarlSawyer == 0) {
                            introductionSix.setVisibility(VISIBLE);
                            introductionSix.setText(R.string.survivors_interlude_five);
                        }
                        break;
                    case 11:
                        title.setText(R.string.dunwich_epilogue);
                        if (globalVariables.TownsfolkAction == 1) {
                            introduction.setText(R.string.dunwich_epilogue_one);
                        } else if (globalVariables.TownsfolkAction == 2) {
                            introduction.setText(R.string.dunwich_epilogue_two);
                        }
                        globalVariables.DunwichCompleted = 1;
                        break;
                }
                break;
            case 3:
                switch (globalVariables.CurrentScenario) {
                    case 0:
                        title.setText(R.string.carcosa_interlude_zero);
                        introduction.setText(R.string.carcosa_lola_prologue);
                        break;
                    case 3:
                        title.setText(R.string.carcosa_interlude_one);
                        introduction.setVisibility(GONE);
                        introductionOptions.setVisibility(VISIBLE);
                        introductionOptionOne.setText(R.string.carcosa_interlude_one_option_one);
                        introductionOptionTwo.setText(R.string.carcosa_interlude_one_option_two);
                        introductionOptionThree.setText(R.string.carcosa_interlude_one_option_three);
                        resolution = 0;
                        introductionOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                                if (introductionOptionOne.isChecked()) {
                                    introductionOne.setVisibility(VISIBLE);
                                    introductionOne.setText(R.string.carcosa_interlude_one_one);
                                    resolution = 1;
                                } else if (introductionOptionTwo.isChecked()) {
                                    introductionOne.setVisibility(VISIBLE);
                                    introductionOne.setText(R.string.carcosa_interlude_one_two);
                                    resolution = 2;
                                } else if (introductionOptionThree.isChecked()) {
                                    introductionOne.setVisibility(VISIBLE);
                                    introductionOne.setText(R.string.carcosa_interlude_one_three);
                                    resolution = 3;
                                }
                            }
                        });
                        break;
                }
                break;
        }

        if (globalVariables.CurrentScenario > 100) {
            switch (globalVariables.CurrentScenario) {
                case 101:
                    title.setText(R.string.rougarou_scenario);
                    introduction.setText(R.string.rougarou_introduction);
                    break;
                case 102:
                    title.setText(R.string.carnevale_scenario);
                    introduction.setText(R.string.carnevale_introduction);
                    break;
            }
        }

        Button backButton = findViewById(R.id.back_button);
        backButton.setTypeface(teutonic);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScenarioInterludeActivity.this, MainMenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        Button continueButton = findViewById(R.id.continue_button);
        continueButton.setTypeface(teutonic);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (globalVariables.CurrentCampaign == 3 && globalVariables.CurrentScenario == 3 &&
                        !introductionOptionOne.isChecked() && !introductionOptionTwo.isChecked() &&
                        !introductionOptionThree.isChecked()) {
                    Toast toast = Toast.makeText(getBaseContext(), R.string.must_option, Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    interludeResolutions();
                    globalVariables.CurrentScenario += 1;
                    ScenarioResolutionActivity.saveCampaign(ScenarioInterludeActivity.this, globalVariables);
                    Intent intent = new Intent(ScenarioInterludeActivity.this, ScenarioMainActivity.class);
                    if (globalVariables.CurrentCampaign == 2 && globalVariables.DunwichCompleted == 1) {
                        intent = new Intent(ScenarioInterludeActivity.this, CampaignFinishedActivity.class);
                    }
                    startActivity(intent);
                }
            }
        });
    }

    // Any resolutions which require implementing
    public void interludeResolutions(){
        switch(globalVariables.CurrentCampaign){
            case 3:
                switch(globalVariables.CurrentScenario){
                    case 3:
                        switch(resolution){
                            case 1:
                                globalVariables.Party = 1;
                                globalVariables.Doubt += 1;
                                globalVariables.Theatre = 3;
                                break;
                            case 2:
                                globalVariables.Party = 2;
                                globalVariables.Theatre = 2;
                                break;
                            case 3:
                                globalVariables.Party = 3;
                                globalVariables.Conviction += 1;
                                globalVariables.Constance = 2;
                                globalVariables.Jordan = 2;
                                globalVariables.Ishimaru = 2;
                                globalVariables.Sebastien = 2;
                                globalVariables.Ashleigh = 2;
                                globalVariables.Theatre = 1;
                                break;
                        }
                        break;
                }
                break;
        }
    }

    // Makes back button go up (back to home page - SelectCampaignActivity)
    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
    }
}
