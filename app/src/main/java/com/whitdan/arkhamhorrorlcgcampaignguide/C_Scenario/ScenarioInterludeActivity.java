package com.whitdan.arkhamhorrorlcgcampaignguide.C_Scenario;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
        globalVariables.setTitle(title);

        // Set typefaces
        final TextView introduction = findViewById(R.id.introduction_text);
        Typeface arnoproitalic = Typeface.createFromAsset(getAssets(), "fonts/arnoproitalic.otf");
        Typeface wolgastbold = Typeface.createFromAsset(getAssets(), "fonts/wolgastbold.otf");
        introduction.setTypeface(arnoproitalic);
        final TextView introductionOne = findViewById(R.id.introduction_text_additional_one);
        final TextView introductionTwo = findViewById(R.id.introduction_text_additional_two);
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
        final RadioButton introductionOptionFour = findViewById(R.id.introduction_option_four);
        Typeface arnopro = Typeface.createFromAsset(getAssets(), "fonts/arnopro.otf");
        final Typeface arnoprobold = Typeface.createFromAsset(getAssets(), "fonts/arnoprobold.otf");
        introductionOptionOne.setTypeface(arnopro);
        introductionOptionTwo.setTypeface(arnopro);
        introductionOptionThree.setTypeface(arnopro);
        introductionOptionFour.setTypeface(arnopro);
        LinearLayout interludeLayout = findViewById(R.id.interlude_layout);
        LinearLayout additionalLayout = findViewById(R.id.interlude_additional_layout);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout
                .LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

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
                    case 11:
                        introduction.setText(R.string.carcosa_epilogue_one);
                        globalVariables.CarcosaCompleted = 1;
                        break;
                }
                break;
            case 4:
                String[] investigatorNames = getResources().getStringArray(R.array.investigators);
                switch (globalVariables.CurrentScenario) {
                    case 2:
                        StringBuilder blanket = new StringBuilder();
                        blanket.append(getResources().getString(R.string.restless_blanket_one));
                        int blanketCount = 0;
                        StringBuilder noBlanket = new StringBuilder();
                        noBlanket.append(getResources().getString(R.string.restless_no_blanket_one));
                        int noBlanketCount = 0;
                        for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                            if (globalVariables.Investigators.get(i).Supplies % 3 == 0) {
                                blanket.append("\t");
                                blanket.append(investigatorNames[globalVariables.Investigators.get(i).Name]);
                                blanket.append("\n");
                                blanketCount++;
                            } else {
                                noBlanket.append("\t");
                                noBlanket.append(investigatorNames[globalVariables.Investigators.get(i).Name]);
                                noBlanket.append("\n");
                                noBlanketCount++;

                                View noBlanketHeading = View.inflate(this, R.layout.e_item_heading, null);
                                TextView noBlanketHeadingText = noBlanketHeading.findViewById(R.id.heading);
                                noBlanketHeadingText.setText(investigatorNames[globalVariables.Investigators.get(i)
                                        .Name]);
                                noBlanketHeadingText.setTypeface(teutonic);
                                interludeLayout.addView(noBlanketHeading, lp);

                                View noBlanketOptions = View.inflate(this, R.layout.e_item_radiogroup, null);
                                RadioButton noBlanketOptionOne = noBlanketOptions.findViewById(R.id.option_one);
                                RadioButton noBlanketOptionTwo = noBlanketOptions.findViewById(R.id.option_two);
                                noBlanketOptionOne.setText(R.string.restless_no_blanket_option_one);
                                noBlanketOptionTwo.setText(R.string.restless_no_blanket_option_two);
                                noBlanketOptionOne.setTypeface(arnopro);
                                noBlanketOptionTwo.setTypeface(arnopro);
                                noBlanketOptionOne.setId(100 + i);
                                noBlanketOptionTwo.setId(200 + i);
                                interludeLayout.addView(noBlanketOptions, lp);
                            }
                        }
                        blanket.append(getResources().getString(R.string.restless_blanket_two));
                        noBlanket.append(getResources().getString(R.string.restless_no_blanket_two));

                        if (blanketCount > 0) {
                            introduction.setTypeface(arnoprobold);
                            introduction.setText(blanket.toString());
                            introductionOne.setVisibility(VISIBLE);
                            introductionOne.setText(R.string.restless_restful_sleep);
                        } else {
                            introduction.setVisibility(GONE);
                        }

                        if (noBlanketCount > 0) {
                            introductionTwo.setVisibility(VISIBLE);
                            introductionTwo.setTypeface(arnoprobold);
                            introductionTwo.setText(noBlanket.toString());
                            introductionThree.setVisibility(VISIBLE);
                            introductionThree.setText(R.string.restless_tossing_turning);
                        }
                        break;
                    case 3:
                        int provisions = 0;
                        for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                            provisions += globalVariables.Investigators.get(i).Provisions;
                        }
                        introduction.setTypeface(arnoprobold);
                        if ((globalVariables.Investigators.size() - provisions) <= 0) {
                            introduction.setText(R.string.restless_not_low);
                            int investigators = globalVariables.Investigators.size();
                            for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                if (investigators > globalVariables.Investigators.get(i).Provisions) {
                                    investigators = investigators - globalVariables.Investigators.get(i).Provisions;
                                    globalVariables.Investigators.get(i).Provisions = 0;
                                } else if (investigators != 0) {
                                    globalVariables.Investigators.get(i).Provisions = globalVariables.Investigators
                                            .get(i).Provisions - investigators;
                                    investigators = 0;
                                }
                            }
                            globalVariables.LowRations = 0;
                        } else {
                            for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                globalVariables.Investigators.get(i).Provisions = 0;
                            }
                            int low = globalVariables.Investigators.size() - provisions;
                            globalVariables.LowRations = low;
                            StringBuilder lowSupplies = new StringBuilder();
                            lowSupplies.append(getResources().getString(R.string.restless_low_one));
                            lowSupplies.append(" ");
                            lowSupplies.append(Integer.toString(low));
                            lowSupplies.append(" ");
                            if (low == 1) {
                                lowSupplies.append(getResources().getString(R.string
                                        .restless_low_two_single_investigator));
                            } else {
                                lowSupplies.append(getResources().getString(R.string
                                        .restless_low_two_multiple_investigator));
                            }
                            introduction.setText(lowSupplies.toString());
                            introductionOne.setVisibility(VISIBLE);
                            introductionOne.setText(R.string.restless_low);
                            introductionTwo.setVisibility(VISIBLE);
                            introductionTwo.setTypeface(arnoprobold);
                            introductionTwo.setText(R.string.restless_low_effect);
                        }
                        break;
                    case 4:
                        introduction.setTypeface(arnoprobold);
                        introduction.setText(R.string.restless_choose_lookout);
                        introductionOptions.setVisibility(VISIBLE);
                        introductionOptionOne.setVisibility(GONE);
                        introductionOptionTwo.setVisibility(GONE);
                        introductionOptionThree.setVisibility(GONE);
                        introductionOptionFour.setVisibility(GONE);
                        switch (globalVariables.Investigators.size()) {
                            case 4:
                                introductionOptionFour.setVisibility(VISIBLE);
                                String investigatorFour = investigatorNames[globalVariables.Investigators.get(3)
                                        .Name];
                                if (globalVariables.Investigators.get(3).Supplies % 17 == 0) {
                                    investigatorFour = investigatorFour + " " + getResources().getString(R.string
                                            .restless_binoculars);
                                }
                                introductionOptionFour.setText(investigatorFour);
                            case 3:
                                introductionOptionThree.setVisibility(VISIBLE);
                                String investigatorThree = investigatorNames[globalVariables.Investigators.get(2)
                                        .Name];
                                if (globalVariables.Investigators.get(2).Supplies % 17 == 0) {
                                    investigatorThree = investigatorThree + " " + getResources().getString(R.string
                                            .restless_binoculars);
                                }
                                introductionOptionThree.setText(investigatorThree);
                            case 2:
                                introductionOptionTwo.setVisibility(VISIBLE);
                                String investigatorTwo = investigatorNames[globalVariables.Investigators.get(1)
                                        .Name];
                                if (globalVariables.Investigators.get(1).Supplies % 17 == 0) {
                                    investigatorTwo = investigatorTwo + " " + getResources().getString(R.string
                                            .restless_binoculars);
                                }
                                introductionOptionTwo.setText(investigatorTwo);
                            case 1:
                                introductionOptionOne.setVisibility(VISIBLE);
                                String investigatorOne = investigatorNames[globalVariables.Investigators.get(0)
                                        .Name];
                                if (globalVariables.Investigators.get(0).Supplies % 17 == 0) {
                                    investigatorOne = investigatorOne + " " + getResources().getString(R.string
                                            .restless_binoculars);
                                }
                                introductionOptionOne.setText(investigatorOne);
                        }
                        introductionOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                introductionOne.setVisibility(VISIBLE);
                                introductionOne.setTypeface(arnoprobold);
                                introductionOne.setText(R.string.restless_lookout_reads);
                                introductionTwo.setVisibility(VISIBLE);
                                switch (checkedId) {
                                    case R.id.introduction_option_one:
                                        if (globalVariables.Investigators.get(0).Supplies % 17 == 0) {
                                            introductionTwo.setText(R.string.restless_shapes_trees);
                                        } else {
                                            introductionTwo.setText(R.string.restless_eyes_dark);
                                        }
                                        break;
                                    case R.id.introduction_option_two:
                                        if (globalVariables.Investigators.get(1).Supplies % 17 == 0) {
                                            introductionTwo.setText(R.string.restless_shapes_trees);
                                        } else {
                                            introductionTwo.setText(R.string.restless_eyes_dark);
                                        }
                                        break;
                                    case R.id.introduction_option_three:
                                        if (globalVariables.Investigators.get(2).Supplies % 17 == 0) {
                                            introductionTwo.setText(R.string.restless_shapes_trees);
                                        } else {
                                            introductionTwo.setText(R.string.restless_eyes_dark);
                                        }
                                        break;
                                    case R.id.introduction_option_four:
                                        if (globalVariables.Investigators.get(3).Supplies % 17 == 0) {
                                            introductionTwo.setText(R.string.restless_shapes_trees);
                                        } else {
                                            introductionTwo.setText(R.string.restless_eyes_dark);
                                        }
                                        break;
                                }
                            }
                        });
                        break;
                    case 5:
                        int medicine = 0;
                        for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                            medicine += globalVariables.Investigators.get(i).Medicine;
                        }
                        introduction.setTypeface(arnoprobold);
                        if (medicine > 0) {
                            StringBuilder medicineString = new StringBuilder();
                            medicineString.append(Integer.toString(medicine));
                            medicineString.append(" ");
                            if (medicine == 1) {
                                medicineString.append(getResources().getString(R.string
                                        .restless_poison_one_single_investigator));
                            } else {
                                medicineString.append(getResources().getString(R.string
                                        .restless_poison_one_multiple_investigators));
                            }
                            introduction.setText(medicineString.toString());

                            View medicineHeading = View.inflate(this, R.layout.e_item_heading, null);
                            TextView medicineHeadingText = medicineHeading.findViewById(R.id.heading);
                            medicineHeadingText.setText(R.string.restless_poison_counter);
                            medicineHeadingText.setTypeface(teutonic);
                            additionalLayout.addView(medicineHeading, lp);
                            View medicineCounter = View.inflate(this, R.layout.e_item_counter, null);
                            final TextView amount = medicineCounter.findViewById(R.id.amount);
                            amount.setId(R.id.medicine_amount);
                            amount.setText("0");
                            amount.setTypeface(arnopro);
                            ImageView decrement = medicineCounter.findViewById(R.id.decrement);
                            ImageView increment = medicineCounter.findViewById(R.id.increment);
                            additionalLayout.addView(medicineCounter, lp);
                            decrement.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    int current = Integer.valueOf(amount.getText().toString());
                                    if (current > 0) {
                                        current--;
                                        amount.setText(Integer.toString(current));
                                    }
                                }
                            });
                            final int medicineAmount = medicine;
                            increment.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    int current = Integer.valueOf(amount.getText().toString());
                                    if (current < medicineAmount && current < globalVariables.Investigators.size()) {
                                        current++;
                                        amount.setText(Integer.toString(current));
                                    }
                                }
                            });
                        } else {
                            introduction.setText(R.string.restless_poison_two);
                        }

                        View poisonedHeading = View.inflate(this, R.layout.e_item_heading, null);
                        TextView poisonedHeadingText = poisonedHeading.findViewById(R.id.heading);
                        poisonedHeadingText.setText(R.string.restless_poisoned_heading);
                        poisonedHeadingText.setTypeface(teutonic);
                        additionalLayout.addView(poisonedHeading, lp);

                        View invOne = View.inflate(this, R.layout.e_item_checkbox, null);
                        final CheckBox invOneBox = invOne.findViewById(R.id.checkbox);
                        invOneBox.setTypeface(arnopro);
                        additionalLayout.addView(invOne, lp);
                        invOne.setVisibility(GONE);
                        View invTwo = View.inflate(this, R.layout.e_item_checkbox, null);
                        final CheckBox invTwoBox = invTwo.findViewById(R.id.checkbox);
                        invTwoBox.setTypeface(arnopro);
                        additionalLayout.addView(invTwo, lp);
                        invTwo.setVisibility(GONE);
                        View invThree = View.inflate(this, R.layout.e_item_checkbox, null);
                        final CheckBox invThreeBox = invThree.findViewById(R.id.checkbox);
                        invThreeBox.setTypeface(arnopro);
                        additionalLayout.addView(invThree, lp);
                        invThree.setVisibility(GONE);
                        View invFour = View.inflate(this, R.layout.e_item_checkbox, null);
                        final CheckBox invFourBox = invFour.findViewById(R.id.checkbox);
                        invFourBox.setTypeface(arnopro);
                        additionalLayout.addView(invFour, lp);
                        invFour.setVisibility(GONE);

                        switch (globalVariables.Investigators.size()) {
                            case 4:
                                invFour.setVisibility(VISIBLE);
                                invFourBox.setText(investigatorNames[globalVariables.Investigators.get(3).Name]);
                                invFourBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        if (isChecked) {
                                            globalVariables.Investigators.get(3).Damage += 1;
                                            introductionOne.setVisibility(VISIBLE);
                                            introductionTwo.setVisibility(VISIBLE);
                                        } else {
                                            globalVariables.Investigators.get(3).Damage += -1;
                                            if (!invOneBox.isChecked() && !invTwoBox.isChecked() && !invThreeBox
                                                    .isChecked()) {
                                                introductionOne.setVisibility(GONE);
                                                introductionTwo.setVisibility(GONE);
                                            }
                                        }
                                    }
                                });
                            case 3:
                                invThree.setVisibility(VISIBLE);
                                invThreeBox.setText(investigatorNames[globalVariables.Investigators.get(2).Name]);
                                invThreeBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        if (isChecked) {
                                            globalVariables.Investigators.get(2).Damage += 1;
                                            introductionOne.setVisibility(VISIBLE);
                                            introductionTwo.setVisibility(VISIBLE);
                                        } else {
                                            globalVariables.Investigators.get(2).Damage += -1;
                                            if (!invOneBox.isChecked() && !invTwoBox.isChecked() && !invFourBox
                                                    .isChecked()) {
                                                introductionOne.setVisibility(GONE);
                                                introductionTwo.setVisibility(GONE);
                                            }
                                        }
                                    }
                                });
                            case 2:
                                invTwo.setVisibility(VISIBLE);
                                invTwoBox.setText(investigatorNames[globalVariables.Investigators.get(1).Name]);
                                invTwoBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        if (isChecked) {
                                            globalVariables.Investigators.get(1).Damage += 1;
                                            introductionOne.setVisibility(VISIBLE);
                                            introductionTwo.setVisibility(VISIBLE);
                                        } else {
                                            globalVariables.Investigators.get(1).Damage += -1;
                                            if (!invOneBox.isChecked() && !invFourBox.isChecked() && !invThreeBox
                                                    .isChecked()) {
                                                introductionOne.setVisibility(GONE);
                                                introductionTwo.setVisibility(GONE);
                                            }
                                        }
                                    }
                                });
                            case 1:
                                invOne.setVisibility(VISIBLE);
                                invOneBox.setText(investigatorNames[globalVariables.Investigators.get(0).Name]);
                                invOneBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        if (isChecked) {
                                            globalVariables.Investigators.get(0).Damage += 1;
                                            introductionOne.setVisibility(VISIBLE);
                                            introductionTwo.setVisibility(VISIBLE);
                                        } else {
                                            globalVariables.Investigators.get(0).Damage += -1;
                                            if (!invFourBox.isChecked() && !invTwoBox.isChecked() && !invThreeBox
                                                    .isChecked
                                                            ()) {
                                                introductionOne.setVisibility(GONE);
                                                introductionTwo.setVisibility(GONE);
                                            }
                                        }
                                    }
                                });
                        }

                        introductionOne.setTypeface(arnoprobold);
                        introductionOne.setText(R.string.restless_poison_three);
                        introductionTwo.setText(R.string.restless_poison_spreads);
                        break;
                    case 7:
                        resolution = 0;
                        if (globalVariables.Relic == 1) {
                            introduction.setText(R.string.expeditions_end_one);
                            introductionOptions.setVisibility(VISIBLE);
                            introductionOptionOne.setText(R.string.expeditions_end_option_one);
                            introductionOptionTwo.setText(R.string.expeditions_end_option_two);
                            introductionOptionThree.setVisibility(GONE);
                            introductionOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                                    if (introductionOptionOne.isChecked()) {
                                        introductionOne.setVisibility(VISIBLE);
                                        introductionOne.setText(R.string.expeditions_end_two);
                                        resolution = 1;
                                    } else if (introductionOptionTwo.isChecked()) {
                                        introductionOne.setVisibility(VISIBLE);
                                        introductionOne.setText(R.string.expeditions_end_three);
                                        resolution = 2;
                                    }
                                }
                            });
                        } else if (globalVariables.Relic == 2) {
                            introduction.setText(R.string.expeditions_end_five);
                        }
                        break;
                    case 11:
                        introduction.setText(R.string.jungle_beckons_introduction);
                        break;
                    case 12:
                        for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                            if (globalVariables.GasolineUsed != 3) {
                                if (globalVariables.Investigators.get(i).Supplies % 29 == 0) {
                                    globalVariables.Investigators.get(i).Supplies = globalVariables.Investigators.get
                                            (i).Supplies / 29;
                                    globalVariables.GasolineUsed = 3;
                                } else if (globalVariables.Investigators.get(i).ResuppliesOne % 2 == 0) {
                                    globalVariables.Investigators.get(i).ResuppliesOne = globalVariables
                                            .Investigators.get
                                                    (i).ResuppliesOne / 2;
                                    globalVariables.GasolineUsed = 3;
                                } else {
                                    globalVariables.GasolineUsed = 4;
                                }
                            }
                        }
                        if (globalVariables.GasolineUsed == 3) {
                            introduction.setTypeface(arnoprobold);
                            introduction.setText(R.string.jungle_enough_gas);
                        } else if (globalVariables.GasolineUsed == 4) {
                            introduction.setText(R.string.jungle_out_gas);
                        }
                        break;
                    case 13:
                        boolean map = false;
                        for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                            if (globalVariables.Investigators.get(i).Supplies % 13 == 0) {
                                map = true;
                            }
                        }
                        if (map) {
                            introduction.setText(R.string.jungle_map);
                        } else {
                            introduction.setTypeface(arnoprobold);
                            introduction.setText(R.string.jungle_no_map);
                        }
                        break;
                    case 14:
                        provisions = 0;
                        for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                            provisions += globalVariables.Investigators.get(i).Provisions;
                        }
                        introduction.setTypeface(arnoprobold);
                        if ((globalVariables.Investigators.size() - provisions) <= 0) {
                            introduction.setText(R.string.restless_not_low);
                            int investigators = globalVariables.Investigators.size();
                            for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                if (investigators > globalVariables.Investigators.get(i).Provisions) {
                                    investigators = investigators - globalVariables.Investigators.get(i).Provisions;
                                    globalVariables.Investigators.get(i).Provisions = 0;
                                } else if (investigators != 0) {
                                    globalVariables.Investigators.get(i).Provisions = globalVariables.Investigators
                                            .get(i).Provisions - investigators;
                                    investigators = 0;
                                }
                            }
                            globalVariables.LowRations = 0;
                        } else {
                            for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                globalVariables.Investigators.get(i).Provisions = 0;
                            }
                            int low = globalVariables.Investigators.size() - provisions;
                            globalVariables.LowRations = low;
                            StringBuilder lowSupplies = new StringBuilder();
                            lowSupplies.append(getResources().getString(R.string.restless_low_one));
                            lowSupplies.append(" ");
                            lowSupplies.append(Integer.toString(low));
                            lowSupplies.append(" ");
                            if (low == 1) {
                                lowSupplies.append(getResources().getString(R.string
                                        .restless_low_two_single_investigator));
                            } else {
                                lowSupplies.append(getResources().getString(R.string
                                        .restless_low_two_multiple_investigator));
                            }
                            introduction.setText(lowSupplies.toString());
                            introductionOne.setVisibility(VISIBLE);
                            introductionOne.setText(R.string.jungle_low);
                            introductionTwo.setVisibility(VISIBLE);
                            introductionTwo.setTypeface(arnoprobold);
                            introductionTwo.setText(R.string.restless_low_effect);
                        }
                        break;
                    case 15:
                        medicine = 0;
                        for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                            medicine += globalVariables.Investigators.get(i).Medicine;
                        }
                        introduction.setTypeface(arnoprobold);
                        if (medicine > 0) {
                            StringBuilder medicineString = new StringBuilder();
                            medicineString.append(Integer.toString(medicine));
                            medicineString.append(" ");
                            if (medicine == 1) {
                                medicineString.append(getResources().getString(R.string
                                        .restless_poison_one_single_investigator));
                            } else {
                                medicineString.append(getResources().getString(R.string
                                        .restless_poison_one_multiple_investigators));
                            }
                            introduction.setText(medicineString.toString());

                            View medicineHeading = View.inflate(this, R.layout.e_item_heading, null);
                            TextView medicineHeadingText = medicineHeading.findViewById(R.id.heading);
                            medicineHeadingText.setText(R.string.restless_poison_counter);
                            medicineHeadingText.setTypeface(teutonic);
                            additionalLayout.addView(medicineHeading, lp);
                            View medicineCounter = View.inflate(this, R.layout.e_item_counter, null);
                            final TextView amount = medicineCounter.findViewById(R.id.amount);
                            amount.setId(R.id.medicine_amount);
                            amount.setText("0");
                            amount.setTypeface(arnopro);
                            ImageView decrement = medicineCounter.findViewById(R.id.decrement);
                            ImageView increment = medicineCounter.findViewById(R.id.increment);
                            additionalLayout.addView(medicineCounter, lp);
                            decrement.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    int current = Integer.valueOf(amount.getText().toString());
                                    if (current > 0) {
                                        current--;
                                        amount.setText(Integer.toString(current));
                                    }
                                }
                            });
                            final int medicineAmount = medicine;
                            increment.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    int current = Integer.valueOf(amount.getText().toString());
                                    if (current < medicineAmount && current < globalVariables.Investigators.size()) {
                                        current++;
                                        amount.setText(Integer.toString(current));
                                    }
                                }
                            });
                        } else {
                            introduction.setText(R.string.restless_poison_two);
                        }

                        poisonedHeading = View.inflate(this, R.layout.e_item_heading, null);
                        poisonedHeadingText = poisonedHeading.findViewById(R.id.heading);
                        poisonedHeadingText.setText(R.string.restless_poisoned_heading);
                        poisonedHeadingText.setTypeface(teutonic);
                        additionalLayout.addView(poisonedHeading, lp);

                        invOne = View.inflate(this, R.layout.e_item_checkbox, null);
                        final CheckBox invOneBoxTwo = invOne.findViewById(R.id.checkbox);
                        invOneBoxTwo.setTypeface(arnopro);
                        additionalLayout.addView(invOne, lp);
                        invOne.setVisibility(GONE);
                        invTwo = View.inflate(this, R.layout.e_item_checkbox, null);
                        final CheckBox invTwoBoxTwo = invTwo.findViewById(R.id.checkbox);
                        invTwoBoxTwo.setTypeface(arnopro);
                        additionalLayout.addView(invTwo, lp);
                        invTwo.setVisibility(GONE);
                        invThree = View.inflate(this, R.layout.e_item_checkbox, null);
                        final CheckBox invThreeBoxTwo = invThree.findViewById(R.id.checkbox);
                        invThreeBoxTwo.setTypeface(arnopro);
                        additionalLayout.addView(invThree, lp);
                        invThree.setVisibility(GONE);
                        invFour = View.inflate(this, R.layout.e_item_checkbox, null);
                        final CheckBox invFourBoxTwo = invFour.findViewById(R.id.checkbox);
                        invFourBoxTwo.setTypeface(arnopro);
                        additionalLayout.addView(invFour, lp);
                        invFour.setVisibility(GONE);

                        switch (globalVariables.Investigators.size()) {
                            case 4:
                                invFour.setVisibility(VISIBLE);
                                invFourBoxTwo.setText(investigatorNames[globalVariables.Investigators.get(3).Name]);
                                invFourBoxTwo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        if (isChecked) {
                                            globalVariables.Investigators.get(3).Damage += 1;
                                            introductionOne.setVisibility(VISIBLE);
                                            introductionTwo.setVisibility(VISIBLE);
                                        } else {
                                            globalVariables.Investigators.get(3).Damage += -1;
                                            if (!invOneBoxTwo.isChecked() && !invTwoBoxTwo.isChecked() &&
                                                    !invThreeBoxTwo
                                                    .isChecked()) {
                                                introductionOne.setVisibility(GONE);
                                                introductionTwo.setVisibility(GONE);
                                            }
                                        }
                                    }
                                });
                            case 3:
                                invThree.setVisibility(VISIBLE);
                                invThreeBoxTwo.setText(investigatorNames[globalVariables.Investigators.get(2).Name]);
                                invThreeBoxTwo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        if (isChecked) {
                                            globalVariables.Investigators.get(2).Damage += 1;
                                            introductionOne.setVisibility(VISIBLE);
                                            introductionTwo.setVisibility(VISIBLE);
                                        } else {
                                            globalVariables.Investigators.get(2).Damage += -1;
                                            if (!invOneBoxTwo.isChecked() && !invTwoBoxTwo.isChecked() && !invFourBoxTwo
                                                    .isChecked()) {
                                                introductionOne.setVisibility(GONE);
                                                introductionTwo.setVisibility(GONE);
                                            }
                                        }
                                    }
                                });
                            case 2:
                                invTwo.setVisibility(VISIBLE);
                                invTwoBoxTwo.setText(investigatorNames[globalVariables.Investigators.get(1).Name]);
                                invTwoBoxTwo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        if (isChecked) {
                                            globalVariables.Investigators.get(1).Damage += 1;
                                            introductionOne.setVisibility(VISIBLE);
                                            introductionTwo.setVisibility(VISIBLE);
                                        } else {
                                            globalVariables.Investigators.get(1).Damage += -1;
                                            if (!invOneBoxTwo.isChecked() && !invFourBoxTwo.isChecked() &&
                                                    !invThreeBoxTwo
                                                    .isChecked()) {
                                                introductionOne.setVisibility(GONE);
                                                introductionTwo.setVisibility(GONE);
                                            }
                                        }
                                    }
                                });
                            case 1:
                                invOne.setVisibility(VISIBLE);
                                invOneBoxTwo.setText(investigatorNames[globalVariables.Investigators.get(0).Name]);
                                invOneBoxTwo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        if (isChecked) {
                                            globalVariables.Investigators.get(0).Damage += 1;
                                            introductionOne.setVisibility(VISIBLE);
                                            introductionTwo.setVisibility(VISIBLE);
                                        } else {
                                            globalVariables.Investigators.get(0).Damage += -1;
                                            if (!invFourBoxTwo.isChecked() && !invTwoBoxTwo.isChecked() &&
                                                    !invThreeBoxTwo
                                                    .isChecked
                                                            ()) {
                                                introductionOne.setVisibility(GONE);
                                                introductionTwo.setVisibility(GONE);
                                            }
                                        }
                                    }
                                });
                        }

                        introductionOne.setTypeface(arnoprobold);
                        introductionOne.setText(R.string.restless_poison_three);
                        introductionTwo.setText(R.string.jungle_poison_spreads);
                        break;
                    case 16:
                        StringBuilder canteen = new StringBuilder();
                        canteen.append(getResources().getString(R.string.jungle_canteen_one));
                        int canteenCount = 0;
                        StringBuilder noCanteen = new StringBuilder();
                        noCanteen.append(getResources().getString(R.string.jungle_no_canteen_one));
                        int noCanteenCount = 0;
                        for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                            if (globalVariables.Investigators.get(i).Supplies % 5 == 0 ||
                                    globalVariables.Investigators.get(i).ResuppliesOne % 5 == 0) {
                                canteen.append("\t");
                                canteen.append(investigatorNames[globalVariables.Investigators.get(i).Name]);
                                canteen.append("\n");
                                canteenCount++;
                            } else {
                                noCanteen.append("\t");
                                noCanteen.append(investigatorNames[globalVariables.Investigators.get(i).Name]);
                                noCanteen.append("\n");
                                noCanteenCount++;
                            }
                        }
                        canteen.append(getResources().getString(R.string.jungle_canteen_two));
                        noCanteen.append(getResources().getString(R.string.jungle_no_canteen_two));

                        introduction.setText(R.string.jungle_trudge);

                        if (canteenCount > 0) {
                            introductionOne.setVisibility(VISIBLE);
                            introductionOne.setTypeface(arnoprobold);
                            introductionOne.setText(canteen.toString());
                            introductionTwo.setVisibility(VISIBLE);
                            introductionTwo.setText(R.string.jungle_patterns);
                        }

                        if (noCanteenCount > 0) {
                            introductionThree.setVisibility(VISIBLE);
                            introductionThree.setTypeface(arnoprobold);
                            introductionThree.setText(noCanteen.toString());
                            introductionFour.setVisibility(VISIBLE);
                            introductionFour.setText(R.string.jungle_secrets);
                        }
                        break;
                    case 17:
                        if(globalVariables.Ichtaca == 1 && globalVariables.IchtacasTale == 1 && globalVariables
                                .MissingIchtaca == 2 && globalVariables.IchtacaConfidence == 1){
                            introduction.setText(R.string.jungle_faith_restored);
                            globalVariables.IchtacaConfidence = 2;
                        } else {
                            introduction.setText(R.string.jungle_no_faith);
                        }
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
                boolean progress = true;
                if (globalVariables.CurrentCampaign == 3 && globalVariables.CurrentScenario == 3 &&
                        !introductionOptionOne.isChecked() && !introductionOptionTwo.isChecked() &&
                        !introductionOptionThree.isChecked()) {
                    progress = false;
                } else if (globalVariables.CurrentCampaign == 3 && globalVariables.CurrentScenario == 6 &&
                        !introductionOptionOne.isChecked() && !introductionOptionTwo.isChecked()) {
                    progress = false;
                } else if (globalVariables.CurrentCampaign == 4) {
                    switch (globalVariables.CurrentScenario) {
                        case 2:
                            for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                if (globalVariables.Investigators.get(i).Supplies % 3 != 0) {
                                    RadioButton optionOne = findViewById(100 + i);
                                    RadioButton optionTwo = findViewById(200 + i);
                                    if (!optionOne.isChecked() && !optionTwo.isChecked()) {
                                        progress = false;
                                    }
                                }
                            }
                            break;
                        case 4:
                            progress = false;
                            switch (globalVariables.Investigators.size()) {
                                case 4:
                                    if (introductionOptionFour.isChecked()) {
                                        progress = true;
                                    }
                                case 3:
                                    if (introductionOptionThree.isChecked()) {
                                        progress = true;
                                    }
                                case 2:
                                    if (introductionOptionTwo.isChecked()) {
                                        progress = true;
                                    }
                                case 1:
                                    if (introductionOptionOne.isChecked()) {
                                        progress = true;
                                    }
                            }
                            break;
                        case 7:
                            if (globalVariables.Relic == 1 && !introductionOptionOne.isChecked() &&
                                    !introductionOptionTwo.isChecked()) {
                                progress = false;
                            }
                            break;
                    }
                }

                if (progress) {
                    interludeResolutions();
                    globalVariables.CurrentScenario += 1;
                    ScenarioResolutionActivity.saveCampaign(ScenarioInterludeActivity.this, globalVariables);
                    Intent intent = new Intent(ScenarioInterludeActivity.this, ScenarioMainActivity.class);
                    if (globalVariables.CurrentCampaign == 2 && globalVariables.DunwichCompleted == 1) {
                        intent = new Intent(ScenarioInterludeActivity.this, CampaignFinishedActivity.class);
                    }
                    if (globalVariables.CurrentCampaign == 3 && globalVariables.CarcosaCompleted == 1) {
                        intent = new Intent(ScenarioInterludeActivity.this, CampaignFinishedActivity.class);
                    }
                    if (globalVariables.CurrentCampaign == 4) {
                        switch (globalVariables.CurrentScenario) {
                            case 3:
                            case 4:
                            case 5:
                            case 12:
                            case 13:
                            case 14:
                            case 15:
                            case 16:
                            case 17:
                                intent = new Intent(ScenarioInterludeActivity.this, ScenarioInterludeActivity
                                        .class);
                        }
                    }
                    startActivity(intent);
                } else

                {
                    Toast toast = Toast.makeText(getBaseContext(), R.string.must_option, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    // Any resolutions which require implementing
    public void interludeResolutions() {
        final RadioButton introductionOptionOne = findViewById(R.id.introduction_option_one);
        final RadioButton introductionOptionTwo = findViewById(R.id.introduction_option_two);
        final RadioButton introductionOptionThree = findViewById(R.id.introduction_option_three);
        final RadioButton introductionOptionFour = findViewById(R.id.introduction_option_four);
        final TextView amount = findViewById(R.id.medicine_amount);

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
            case 4:
                switch (globalVariables.CurrentScenario) {
                    case 2:
                        for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                            if (globalVariables.Investigators.get(i).Supplies % 3 != 0) {
                                RadioButton optionOne = findViewById(100 + i);
                                RadioButton optionTwo = findViewById(200 + i);
                                if (optionOne.isChecked()) {
                                    globalVariables.Investigators.get(i).Damage += 1;
                                } else if (optionTwo.isChecked()) {
                                    globalVariables.Investigators.get(i).Horror += 1;
                                }
                            }
                        }
                        break;
                    case 4:
                        switch (globalVariables.Investigators.size()) {
                            case 4:
                                if (introductionOptionFour.isChecked()) {
                                    if (globalVariables.Investigators.get(3).Supplies % 17 == 0) {
                                        globalVariables.Investigators.get(3).AvailableXP += 2;
                                        globalVariables.Investigators.get(3).TotalXP += 2;
                                    } else {
                                        globalVariables.Investigators.get(3).Horror += 1;
                                    }
                                    break;
                                }
                            case 3:
                                if (introductionOptionThree.isChecked()) {
                                    if (globalVariables.Investigators.get(2).Supplies % 17 == 0) {
                                        globalVariables.Investigators.get(2).AvailableXP += 2;
                                        globalVariables.Investigators.get(2).TotalXP += 2;
                                    } else {
                                        globalVariables.Investigators.get(2).Horror += 1;
                                    }
                                    break;
                                }
                            case 2:
                                if (introductionOptionTwo.isChecked()) {
                                    if (globalVariables.Investigators.get(1).Supplies % 17 == 0) {
                                        globalVariables.Investigators.get(1).AvailableXP += 2;
                                        globalVariables.Investigators.get(1).TotalXP += 2;
                                    } else {
                                        globalVariables.Investigators.get(1).Horror += 1;
                                    }
                                    break;
                                }
                            case 1:
                                if (introductionOptionOne.isChecked()) {
                                    if (globalVariables.Investigators.get(0).Supplies % 17 == 0) {
                                        globalVariables.Investigators.get(0).AvailableXP += 2;
                                        globalVariables.Investigators.get(0).TotalXP += 2;
                                    } else {
                                        globalVariables.Investigators.get(0).Horror += 1;
                                    }
                                    break;
                                }
                        }
                        break;
                    case 5:
                        int medicine = 0;
                        for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                            medicine += globalVariables.Investigators.get(i).Medicine;
                        }
                        if (medicine > 0) {
                            int medicineUsed = Integer.valueOf(amount.getText().toString());
                            for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                if (medicineUsed > globalVariables.Investigators.get(i).Medicine) {
                                    medicineUsed = medicineUsed - globalVariables.Investigators.get(i).Medicine;
                                    globalVariables.Investigators.get(i).Medicine = 0;
                                } else if (medicineUsed != 0) {
                                    globalVariables.Investigators.get(i).Medicine = globalVariables.Investigators
                                            .get(i).Medicine - medicineUsed;
                                    medicineUsed = 0;
                                }
                            }
                        }
                        break;
                    case 7:
                        globalVariables.Custody = resolution;
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
