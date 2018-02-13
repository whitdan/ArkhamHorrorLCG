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
        if (savedInstanceState != null) {
            Intent intent = new Intent(ScenarioIntroductionActivity.this, MainMenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_activity_scenario_introduction);
        globalVariables = (GlobalVariables) this.getApplication();

        // Set title
        TextView title = findViewById(R.id.current_scenario_name);
        Typeface teutonic = Typeface.createFromAsset(getAssets(), "fonts/teutonic.ttf");
        title.setTypeface(teutonic);
        globalVariables.setTitle(title);

        // Set typefaces
        TextView introduction = findViewById(R.id.introduction_text);
        TextView introductionA = findViewById(R.id.introduction_text_one);
        TextView introductionB = findViewById(R.id.introduction_text_two);
        TextView introductionC = findViewById(R.id.introduction_text_three);
        Typeface arnoproitalic = Typeface.createFromAsset(getAssets(), "fonts/arnoproitalic.otf");
        introduction.setTypeface(arnoproitalic);
        introductionA.setTypeface(arnoproitalic);
        introductionB.setTypeface(arnoproitalic);
        introductionC.setTypeface(arnoproitalic);
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
        RadioButton introductionOptionOne = findViewById(R.id.introduction_option_one);
        RadioButton introductionOptionTwo = findViewById(R.id.introduction_option_two);
        Typeface arnopro = Typeface.createFromAsset(getAssets(), "fonts/arnopro.otf");
        introductionOptionOne.setTypeface(arnopro);
        introductionOptionTwo.setTypeface(arnopro);

        // Set text
        switch (globalVariables.CurrentCampaign) {
            case 1:
                switch (globalVariables.CurrentScenario) {
                    case 1:
                        introduction.setText(R.string.gathering_introduction);
                        break;
                    case 2:
                        if (globalVariables.LitaChantler == 2) {
                            introduction.setText(R.string.midnight_introduction_one);
                        } else {
                            introduction.setText(R.string.midnight_introduction_two);
                        }
                        break;
                    case 3:
                        introduction.setText(R.string.devourer_introduction);
                        break;
                }
                break;
            case 2:
                switch (globalVariables.CurrentScenario) {
                    case 1:
                        introduction.setText(R.string.extracurricular_introduction);
                        break;
                    case 2:
                        introduction.setText(R.string.house_introduction);
                        break;
                    case 4:
                        if (globalVariables.HenryArmitage == 0) {
                            introduction.setText(R.string.miskatonic_introduction_one);
                        } else {
                            introduction.setText(R.string.miskatonic_introduction_two);
                        }
                        break;
                    case 5:
                        introduction.setText(R.string.essex_introduction);
                        break;
                    case 6:
                        introduction.setText(R.string.blood_introduction);
                        break;
                    case 8:
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
                        if (globalVariables.ObannionGang == 0) {
                            introduction.setText(R.string.doom_introduction_one);
                        } else {
                            introduction.setText(R.string.doom_introduction_two);
                        }
                        break;
                    case 10:
                        introduction.setText(R.string.lost_introduction);
                        break;
                    case 11:
                        if (globalVariables.TownsfolkAction == 1) {
                            introduction.setText(R.string.dunwich_epilogue_one);
                        } else if (globalVariables.TownsfolkAction == 2) {
                            introduction.setText(R.string.dunwich_epilogue_two);
                        }
                        break;
                }
                break;
            case 3:
                switch (globalVariables.CurrentScenario) {
                    case 1:
                        introduction.setText(R.string.curtain_introduction);
                        break;
                    case 2:
                        introduction.setText(R.string.king_introduction);
                        break;
                    case 4:
                        if (globalVariables.Sebastien == 1 || globalVariables.Sebastien == 4) {
                            introduction.setText(R.string.echoes_introduction_two);
                        } else {
                            introduction.setText(R.string.echoes_introduction);
                        }
                        break;
                    case 5:
                        if (globalVariables.Onyx == 4) {
                            introduction.setText(R.string.unspeakable_introduction_one);
                        } else {
                            introduction.setText(R.string.unspeakable_introduction_two);
                        }
                        if (globalVariables.Constance == 1 || globalVariables.Constance == 4) {
                            introductionOne.setVisibility(VISIBLE);
                            introductionOne.setText(R.string.unspeakable_introduction_constance);
                        }
                        break;
                    case 7:
                        if (globalVariables.Asylum == 1) {
                            introduction.setText(R.string.dream_one_one);
                        } else {
                            introduction.setText(R.string.dream_one_two);
                        }
                        if (globalVariables.Asylum == 1) {
                            introductionB.setVisibility(VISIBLE);
                            introductionB.setText(R.string.dream_eight);
                        } else {
                            introductionA.setVisibility(VISIBLE);
                            introductionB.setVisibility(VISIBLE);
                            if (globalVariables.Party == 1) {
                                introductionA.setText(R.string.dream_three_six);
                            } else if (globalVariables.Party == 3) {
                                introductionA.setText(R.string.dream_four_six);
                                if (globalVariables.DreamsAction == 0) {
                                    for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                        globalVariables.Investigators.get(i).Horror += 1;
                                    }
                                }
                            } else {
                                introductionA.setText(R.string.dream_six);
                            }
                            if (globalVariables.Police == 2) {
                                introductionB.setText(R.string.dream_seven_eight);
                            } else {
                                introductionB.setText(R.string.dream_eight);
                            }
                        }
                        introductionC.setVisibility(VISIBLE);
                        if (globalVariables.ChasingStranger < 4) {
                            introductionC.setText(R.string.dream_nine_awake);
                            globalVariables.DreamsAction = 1;
                        } else {
                            introductionC.setText(R.string.dream_ten);
                            introductionOptions.setVisibility(VISIBLE);
                            introductionOptionOne.setText(R.string.dream_ten_option_one);
                            introductionOptionTwo.setText(R.string.dream_ten_option_two);
                            introductionOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                                    introductionOne.setVisibility(VISIBLE);
                                    switch (i) {
                                        case R.id.introduction_option_one:
                                            introductionOne.setText(R.string.dream_eleven_awake);
                                            if (globalVariables.DreamsAction == 3) {
                                                globalVariables.Doubt += -1;
                                            }
                                            if (globalVariables.DreamsAction != 2) {
                                                globalVariables.DreamsAction = 2;
                                                globalVariables.Conviction += 1;
                                            }
                                            break;
                                        case R.id.introduction_option_two:
                                            introductionOne.setText(R.string.dream_twelve_awake);
                                            if (globalVariables.DreamsAction == 2) {
                                                globalVariables.Conviction += -1;
                                            }
                                            if (globalVariables.DreamsAction != 3) {
                                                globalVariables.DreamsAction = 3;
                                                globalVariables.Doubt += 1;
                                            }
                                            break;
                                    }
                                }
                            });
                        }
                        if (globalVariables.Jordan == 1 || globalVariables.Jordan == 4) {
                            introductionTwo.setVisibility(VISIBLE);
                            introductionTwo.setText(R.string.dream_jordan);
                        }
                        break;
                    case 8:
                        if(globalVariables.Nigel == 0 || globalVariables.Nigel == 3){
                            if(globalVariables.Ishimaru == 1 || globalVariables.Ishimaru == 4){
                                introduction.setText(R.string.pallid_introduction_one_two);
                            } else {
                                introduction.setText(R.string.pallid_introduction_one_one);
                            }
                        } else {
                            if(globalVariables.Ishimaru == 1 || globalVariables.Ishimaru == 4){
                                introduction.setText(R.string.pallid_introduction_two_two);
                            } else {
                                introduction.setText(R.string.pallid_introduction_two_one);
                            }
                        }
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
                finish();
            }
        });

        Button continueButton = findViewById(R.id.continue_button);
        continueButton.setTypeface(teutonic);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (globalVariables.CurrentCampaign == 2 && globalVariables.CurrentScenario == 8 && globalVariables
                        .TownsfolkAction == 0) {
                    Toast toast = Toast.makeText(ScenarioIntroductionActivity.this, R.string.must_option, Toast
                            .LENGTH_SHORT);
                    toast.show();
                } else if (globalVariables.CurrentCampaign == 3 && globalVariables.CurrentScenario == 7 &&
                        globalVariables.DreamsAction == 0) {
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
