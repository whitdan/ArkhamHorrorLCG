package com.whitdan.arkhamhorrorlcgcampaignguide.C_Scenario;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.whitdan.arkhamhorrorlcgcampaignguide.A_Menus.MainMenuActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.R;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.GlobalVariables;

import static android.view.View.VISIBLE;

public class ScenarioIntroductionActivity extends AppCompatActivity {

    static GlobalVariables globalVariables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // If app is reopening after the process is killed, kick back to the main menu (stops the activity from
        // showing up unpopulated)
        if(savedInstanceState != null){
            Intent intent = new Intent(ScenarioIntroductionActivity.this, MainMenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_activity_scenario_introduction);
        globalVariables = (GlobalVariables) this.getApplication();

        // Set typefaces
        TextView title = (TextView) findViewById(R.id.current_scenario_name);
        Typeface teutonic = Typeface.createFromAsset(getAssets(), "fonts/teutonic.ttf");
        title.setTypeface(teutonic);
        TextView introduction = (TextView) findViewById(R.id.introduction_text);
        Typeface arnoproitalic = Typeface.createFromAsset(getAssets(), "fonts/arnoproitalic.otf");
        introduction.setTypeface(arnoproitalic);
        final TextView introductionOne = (TextView) findViewById(R.id.introduction_text_additional_one);
        TextView introductionTwo = (TextView) findViewById(R.id.introduction_text_additional_two);
        TextView introductionThree = (TextView) findViewById(R.id.introduction_text_additional_three);
        TextView introductionFour = (TextView) findViewById(R.id.introduction_text_additional_four);
        TextView introductionFive = (TextView) findViewById(R.id.introduction_text_additional_five);
        TextView introductionSix = (TextView) findViewById(R.id.introduction_text_additional_six);
        introductionOne.setTypeface(arnoproitalic);
        introductionTwo.setTypeface(arnoproitalic);
        introductionThree.setTypeface(arnoproitalic);
        introductionFour.setTypeface(arnoproitalic);
        introductionFive.setTypeface(arnoproitalic);
        introductionSix.setTypeface(arnoproitalic);
        RadioGroup introductionOptions = (RadioGroup) findViewById(R.id.introduction_options);
        RadioButton introductionOptionOne = (RadioButton) findViewById(R.id.introduction_option_one);
        RadioButton introductionOptionTwo = (RadioButton) findViewById(R.id.introduction_option_two);
        Typeface arnopro = Typeface.createFromAsset(getAssets(), "fonts/arnopro.otf");
        introductionOptionOne.setTypeface(arnopro);
        introductionOptionTwo.setTypeface(arnopro);

        // Set text
        switch (globalVariables.CurrentCampaign) {
            case 1:
                switch (globalVariables.CurrentScenario) {
                    case 1:
                        title.setText(R.string.night_scenario_one);
                        introduction.setText(R.string.gathering_introduction);
                        break;
                    case 2:
                        title.setText(R.string.night_scenario_two);
                        if (globalVariables.LitaChantler == 2) {
                            introduction.setText(R.string.midnight_introduction_one);
                        } else {
                            introduction.setText(R.string.midnight_introduction_two);
                        }
                        break;
                    case 3:
                        title.setText(R.string.night_scenario_three);
                        introduction.setText(R.string.devourer_introduction);
                        break;
                }
                break;
            case 2:
                switch (globalVariables.CurrentScenario) {
                    case 1:
                        title.setText(R.string.dunwich_scenario_one);
                        introduction.setText(R.string.extracurricular_introduction);
                        break;
                    case 2:
                        title.setText(R.string.dunwich_scenario_two);
                        introduction.setText(R.string.house_introduction);
                        break;
                    case 3:
                        title.setText(R.string.dunwich_interlude_one);
                        if (globalVariables.InvestigatorsUnconscious == 1) {
                            introduction.setText(R.string.armitage_interlude_one);
                        } else {
                            introduction.setText(R.string.armitage_interlude_two);
                        }
                        break;
                    case 4:
                        title.setText(R.string.dunwich_scenario_three);
                        if (globalVariables.HenryArmitage == 0) {
                            introduction.setText(R.string.miskatonic_introduction_one);
                        } else {
                            introduction.setText(R.string.miskatonic_introduction_two);
                        }
                        break;
                    case 5:
                        title.setText(R.string.dunwich_scenario_four);
                        introduction.setText(R.string.essex_introduction);
                        break;
                    case 6:
                        title.setText(R.string.dunwich_scenario_five);
                        introduction.setText(R.string.blood_introduction);
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
                    case 8:
                        title.setText(R.string.dunwich_scenario_six);
                        introduction.setText(R.string.undimensioned_introduction);
                        introductionOptions.setVisibility(VISIBLE);
                        introductionOptionOne.setText(R.string.undimensioned_introduction_option_one);
                        introductionOptionTwo.setText(R.string.undimensoned_introduction_option_two);
                        globalVariables.TownsfolkAction = 0;
                        introductionOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                                introductionOne.setVisibility(VISIBLE);
                                switch (i) {
                                    case R.id.introduction_option_one:
                                        globalVariables.TownsfolkAction = 1;
                                        introductionOne.setText(R.string.undimensioned_introduction_one);
                                        break;
                                    case R.id.introduction_option_two:
                                        globalVariables.TownsfolkAction = 2;
                                        introductionOne.setText(R.string.undimensioned_introduction_two);
                                        break;
                                }
                            }
                        });
                        break;
                    case 9:
                        title.setText(R.string.dunwich_scenario_seven);
                        if (globalVariables.ObannionGang == 0) {
                            introduction.setText(R.string.doom_introduction_one);
                        } else {
                            introduction.setText(R.string.doom_introduction_two);
                        }
                        break;
                    case 10:
                        title.setText(R.string.dunwich_scenario_eight);
                        introduction.setText(R.string.lost_introduction);
                        break;
                    case 11:
                        title.setText(R.string.dunwich_epilogue);
                        if (globalVariables.TownsfolkAction == 1) {
                            introduction.setText(R.string.dunwich_epilogue_one);
                        } else if (globalVariables.TownsfolkAction == 2) {
                            introduction.setText(R.string.dunwich_epilogue_two);
                        }
                        break;
                }
                break;
            case 3:
                switch(globalVariables.CurrentScenario){
                    case 1:
                        title.setText(R.string.carcosa_scenario_one);
                        introduction.setText(R.string.curtain_introduction);
                        break;
                    case 2:
                        title.setText(R.string.carcosa_scenario_two);
                        introduction.setText(R.string.king_introduction);
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

        Button backButton = (Button) findViewById(R.id.back_button);
        backButton.setTypeface(teutonic);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button continueButton = (Button) findViewById(R.id.continue_button);
        continueButton.setTypeface(teutonic);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (globalVariables.CurrentCampaign == 2 && globalVariables.CurrentScenario == 8 && globalVariables
                        .TownsfolkAction == 0) {
                    Toast toast = Toast.makeText(ScenarioIntroductionActivity.this, R.string.must_option, Toast
                            .LENGTH_SHORT);
                    toast.show();
                } else {
                    Intent intent = new Intent(ScenarioIntroductionActivity.this, ScenarioSetupActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
