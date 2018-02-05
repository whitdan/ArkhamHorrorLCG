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

        // Set title
        TextView title = findViewById(R.id.current_scenario_name);
        Typeface teutonic = Typeface.createFromAsset(getAssets(), "fonts/teutonic.ttf");
        title.setTypeface(teutonic);
        MainMenuActivity.setTitle(title);

        // Set typefaces
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
                        introduction.setText(R.string.carcosa_lola_prologue);
                        break;
                    case 3:
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
                    case 6:
                        switch (globalVariables.Daniel) {
                            case 1:
                                introduction.setText(R.string.lost_soul_one);
                                break;
                            case 2:
                                introduction.setText(R.string.lost_soul_two);
                                break;
                            case 3:
                                introduction.setText(R.string.lost_soul_three);
                                break;
                        }
                        introductionOptions.setVisibility(VISIBLE);
                        introductionOptionOne.setText(R.string.lost_soul_option_one);
                        introductionOptionTwo.setText(R.string.lost_soul_option_two);
                        introductionOptionThree.setVisibility(GONE);
                        resolution = 0;
                        introductionOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                                if (introductionOptionOne.isChecked()) {
                                    introductionOne.setVisibility(VISIBLE);
                                    introductionOne.setText(R.string.lost_soul_ignore_warning);
                                    resolution = 1;
                                } else if (introductionOptionTwo.isChecked()) {
                                    introductionOne.setVisibility(VISIBLE);
                                    introductionOne.setText(R.string.lost_soul_heed_warning);
                                    resolution = 2;
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
                    introduction.setText(R.string.rougarou_introduction);
                    break;
                case 102:
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
                } else if (globalVariables.CurrentCampaign == 3 && globalVariables.CurrentScenario == 6 &&
                        !introductionOptionOne.isChecked() && !introductionOptionTwo.isChecked()) {
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
    public void interludeResolutions() {
        switch (globalVariables.CurrentCampaign) {
            case 3:
                switch (globalVariables.CurrentScenario) {
                    case 3:
                        switch (resolution) {
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
                                // If not interviewed or killed, just record as killed
                                if (globalVariables.Constance == 0) {
                                    globalVariables.Constance = 2;
                                }
                                // If just interviewed, record as interviewed and killed
                                else if (globalVariables.Constance == 1) {
                                    globalVariables.Constance = 4;
                                }
                                // If interviewed (crossed out), record as interviewed (crossed out) and killed
                                else if (globalVariables.Constance == 3) {
                                    globalVariables.Constance = 5;
                                }
                                if (globalVariables.Jordan == 0) {
                                    globalVariables.Jordan = 2;
                                } else if (globalVariables.Jordan == 1) {
                                    globalVariables.Jordan = 4;
                                } else if (globalVariables.Jordan == 3) {
                                    globalVariables.Jordan = 5;
                                }
                                if (globalVariables.Ishimaru == 0) {
                                    globalVariables.Ishimaru = 2;
                                } else if (globalVariables.Ishimaru == 1) {
                                    globalVariables.Ishimaru = 4;
                                } else if (globalVariables.Ishimaru == 3) {
                                    globalVariables.Ishimaru = 5;
                                }
                                if (globalVariables.Sebastien == 0) {
                                    globalVariables.Sebastien = 2;
                                } else if (globalVariables.Sebastien == 1) {
                                    globalVariables.Sebastien = 4;
                                } else if (globalVariables.Sebastien == 3) {
                                    globalVariables.Sebastien = 5;
                                }
                                if (globalVariables.Ashleigh == 0) {
                                    globalVariables.Ashleigh = 2;
                                } else if (globalVariables.Ashleigh == 1) {
                                    globalVariables.Ashleigh = 4;
                                } else if (globalVariables.Ashleigh == 3) {
                                    globalVariables.Ashleigh = 5;
                                }
                                globalVariables.Theatre = 1;
                                break;
                        }
                        break;
                    case 6:
                        switch (resolution) {
                            case 1:
                                globalVariables.DanielsWarning = 1;
                                globalVariables.Doubt += 2;
                                break;
                            case 2:
                                globalVariables.DanielsWarning = 2;
                                globalVariables.Conviction += 2;
                                for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                    globalVariables.Investigators.get(i).AvailableXP += 1;
                                    globalVariables.Investigators.get(i).TotalXP += 1;
                                }
                                break;
                        }
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
