package com.whitdan.arkhamhorrorlcgcampaignguide.C_Scenario;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.whitdan.arkhamhorrorlcgcampaignguide.A_Menus.MainMenuActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.D_Misc.CampaignFinishedActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.D_Misc.CampaignLogActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.E_EditMisc.ChooseSuppliesActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.R;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.ArkhamContract.CampaignEntry;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.ArkhamContract.InvestigatorEntry;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.ArkhamDbHelper;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.GlobalVariables;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.Investigator;

import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.whitdan.arkhamhorrorlcgcampaignguide.R.array.investigators;
import static com.whitdan.arkhamhorrorlcgcampaignguide.R.string.defeated;
import static com.whitdan.arkhamhorrorlcgcampaignguide.R.string.drew;
import static com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.ArkhamContract.CarcosaEntry;
import static com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.ArkhamContract.DunwichEntry;
import static com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.ArkhamContract.ForgottenEntry;
import static com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.ArkhamContract.NightEntry;

public class ScenarioResolutionActivity extends AppCompatActivity {

    static GlobalVariables globalVariables;
    static int strangerCounter;
    static int vengeanceCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // If app is reopening after the process is killed, kick back to the main menu (stops the activity from
        // showing up unpopulated)
        if (savedInstanceState != null) {
            Intent intent = new Intent(ScenarioResolutionActivity.this, MainMenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_activity_scenario_resolution);
        globalVariables = (GlobalVariables) this.getApplication();
        globalVariables.ScenarioResolution = -1;
        globalVariables.VictoryDisplay = 0;

        // Set fonts
        Typeface teutonic = Typeface.createFromAsset(getAssets(), "fonts/teutonic.ttf");
        Typeface arnopro = Typeface.createFromAsset(getAssets(), "fonts/arnopro.otf");
        Typeface arnoprobold = Typeface.createFromAsset(getAssets(), "fonts/arnoprobold.otf");
        Typeface arnoproitalic = Typeface.createFromAsset(getAssets(), "fonts/arnoproitalic.otf");
        Typeface arnoprobolditalic = Typeface.createFromAsset(getAssets(), "fonts/arnoprobolditalic.otf");
        Typeface wolgastbold = Typeface.createFromAsset(getAssets(), "fonts/wolgastbold.otf");
        TextView title = findViewById(R.id.current_scenario_name);
        title.setTypeface(teutonic);
        TextView subTitle = findViewById(R.id.scenario_resolution);
        subTitle.setTypeface(teutonic);
        TextView defeatedHeading = findViewById(R.id.investigators_defeated_heading);
        defeatedHeading.setTypeface(teutonic);
        TextView selectHeading = findViewById(R.id.select_resolution_heading);
        selectHeading.setTypeface(teutonic);
        LinearLayout additionalCounterLayout = findViewById(R.id.additional_counter_layout);
        final TextView additionalCounter = findViewById(R.id.additional_counter);
        additionalCounter.setTypeface(arnoprobold);
        ImageView additionalDecrement = findViewById(R.id.additional_decrement);
        final TextView additionalAmount = findViewById(R.id.additional_amount);
        additionalAmount.setTypeface(wolgastbold);
        ImageView additionalIncrement = findViewById(R.id.additional_increment);
        TextView resolution = findViewById(R.id.resolution_text);
        resolution.setTypeface(arnoproitalic);
        final TextView resolutionAdditional = findViewById(R.id.resolution_text_additional);
        resolutionAdditional.setTypeface(arnoproitalic);

        // Victory display
        TextView victoryDisplay = findViewById(R.id.victory_display);
        victoryDisplay.setTypeface(arnoprobold);
        ImageView victoryDecrement = findViewById(R.id.victory_decrement);
        final TextView victoryAmount = findViewById(R.id.victory_amount);
        victoryAmount.setTypeface(wolgastbold);
        ImageView victoryIncrement = findViewById(R.id.victory_increment);
        victoryAmount.setText(String.valueOf(globalVariables.VictoryDisplay));
        victoryDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int current = globalVariables.VictoryDisplay;
                if (current > 0) {
                    globalVariables.VictoryDisplay += -1;
                    victoryAmount.setText(String.valueOf(globalVariables.VictoryDisplay));
                }
            }
        });
        victoryIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int current = globalVariables.VictoryDisplay;
                if (current < 99) {
                    globalVariables.VictoryDisplay += 1;
                    victoryAmount.setText(String.valueOf(globalVariables.VictoryDisplay));
                }
            }
        });

        // Set title
        globalVariables.setTitle(title);

        // Set fonts to the defeated layout
        TextView investigatorOneName = findViewById(R.id.investigator_one_name);
        investigatorOneName.setTypeface(arnoprobold);
        CheckBox resignedOne = findViewById(R.id.resigned_button_one);
        resignedOne.setTypeface(arnopro);
        TextView investigatorTwoName = findViewById(R.id.investigator_two_name);
        investigatorTwoName.setTypeface(arnoprobold);
        CheckBox resignedTwo = findViewById(R.id.resigned_button_two);
        resignedTwo.setTypeface(arnopro);
        TextView investigatorThreeName = findViewById(R.id.investigator_three_name);
        investigatorThreeName.setTypeface(arnoprobold);
        CheckBox resignedThree = findViewById(R.id.resigned_button_three);
        resignedThree.setTypeface(arnopro);
        TextView investigatorFourName = findViewById(R.id.investigator_four_name);
        investigatorFourName.setTypeface(arnoprobold);
        CheckBox resignedFour = findViewById(R.id.resigned_button_four);
        resignedFour.setTypeface(arnopro);

        // Show the relevant defeated layouts and set the right investigator names
        LinearLayout defeatedOne = findViewById(R.id.investigator_one_defeated_layout);
        LinearLayout defeatedTwo = findViewById(R.id.investigator_two_defeated_layout);
        LinearLayout defeatedThree = findViewById(R.id.investigator_three_defeated_layout);
        LinearLayout defeatedFour = findViewById(R.id.investigator_four_defeated_layout);
        LinearLayout defeatedOneSelection = findViewById(R.id.defeated_one_selection);
        LinearLayout defeatedTwoSelection = findViewById(R.id.defeated_two_selection);
        LinearLayout defeatedThreeSelection = findViewById(R.id.defeated_three_selection);
        LinearLayout defeatedFourSelection = findViewById(R.id.defeated_four_selection);
        final String[] investigatorNames = getResources().getStringArray(investigators);
        switch (globalVariables.Investigators.size()) {
            case 4:
                defeatedFour.setVisibility(VISIBLE);
                investigatorFourName.setText(investigatorNames[globalVariables.Investigators.get(3).Name]);
            case 3:
                defeatedThree.setVisibility(VISIBLE);
                investigatorThreeName.setText(investigatorNames[globalVariables.Investigators.get(2).Name]);
            case 2:
                defeatedTwo.setVisibility(VISIBLE);
                investigatorTwoName.setText(investigatorNames[globalVariables.Investigators.get(1).Name]);
            case 1:
                defeatedOne.setVisibility(VISIBLE);
                investigatorOneName.setText(investigatorNames[globalVariables.Investigators.get(0).Name]);
        }
        for (int i = 0; i < defeatedOneSelection.getChildCount(); i++) {
            View view = defeatedOneSelection.getChildAt(i);
            if (view instanceof CheckBox) {
                ((CheckBox) view).setOnCheckedChangeListener(new InvestigatorDefeatedCheckboxListener());
            }
            if (view instanceof ImageView) {
                view.setOnClickListener(new InvestigatorDefeatedImageListener());
            }
        }
        for (int i = 0; i < defeatedTwoSelection.getChildCount(); i++) {
            View view = defeatedTwoSelection.getChildAt(i);
            if (view instanceof CheckBox) {
                ((CheckBox) view).setOnCheckedChangeListener(new InvestigatorDefeatedCheckboxListener());
            }
            if (view instanceof ImageView) {
                view.setOnClickListener(new InvestigatorDefeatedImageListener());
            }
        }
        for (int i = 0; i < defeatedThreeSelection.getChildCount(); i++) {
            View view = defeatedThreeSelection.getChildAt(i);
            if (view instanceof CheckBox) {
                ((CheckBox) view).setOnCheckedChangeListener(new InvestigatorDefeatedCheckboxListener());
            }
            if (view instanceof ImageView) {
                view.setOnClickListener(new InvestigatorDefeatedImageListener());
            }
        }
        for (int i = 0; i < defeatedFourSelection.getChildCount(); i++) {
            View view = defeatedFourSelection.getChildAt(i);
            if (view instanceof CheckBox) {
                ((CheckBox) view).setOnCheckedChangeListener(new InvestigatorDefeatedCheckboxListener());
            }
            if (view instanceof ImageView) {
                view.setOnClickListener(new InvestigatorDefeatedImageListener());
            }
        }

        // Set fonts and listeners for select resolution
        final RadioGroup selectResolution = findViewById(R.id.select_resolution);
        for (int i = 0; i < selectResolution.getChildCount(); i++) {
            View view = selectResolution.getChildAt(i);
            if (view instanceof RadioButton) {
                ((RadioButton) view).setTypeface(arnoprobold);
            }
        }
        selectResolution.setOnCheckedChangeListener(new ResolutionCheckboxListener());

        // Show right number of checkboxes (default is two resolutions)
        RadioButton resolutionOne = findViewById(R.id.resolution_one);
        RadioButton resolutionTwo = findViewById(R.id.resolution_two);
        RadioButton resolutionThree = findViewById(R.id.resolution_three);
        RadioButton resolutionFour = findViewById(R.id.resolution_four);
        RadioButton resolutionFive = findViewById(R.id.resolution_five);
        switch (globalVariables.CurrentCampaign) {
            case 1:
                switch (globalVariables.CurrentScenario) {
                    case 1:
                    case 3:
                        resolutionThree.setVisibility(VISIBLE);
                        break;
                }
                break;
            case 2:
                switch (globalVariables.CurrentScenario) {
                    case 1:
                    case 2:
                        resolutionThree.setVisibility(VISIBLE);
                        resolutionFour.setVisibility(VISIBLE);
                        break;
                    case 6:
                        resolutionThree.setVisibility(VISIBLE);
                        break;
                    case 10:
                        resolutionTwo.setVisibility(GONE);
                        resolutionThree.setVisibility(VISIBLE);
                        break;
                }
                break;
            case 3:
                switch (globalVariables.CurrentScenario) {
                    case 2:
                        resolutionOne.setVisibility(GONE);
                        resolutionTwo.setVisibility(GONE);
                        resolutionThree.setVisibility(VISIBLE);
                        break;
                    case 4:
                        resolutionThree.setVisibility(VISIBLE);
                        resolutionFour.setVisibility(VISIBLE);
                        break;
                    case 5:
                    case 7:
                    case 9:
                    case 10:
                        resolutionThree.setVisibility(VISIBLE);
                        break;
                }
                break;
            case 4:
                switch (globalVariables.CurrentScenario) {
                    case 6:
                        resolutionTwo.setVisibility(GONE);
                        resolutionThree.setVisibility(VISIBLE);
                        break;
                    case 8:
                    case 18:
                    case 20:
                    case 35:
                        resolutionTwo.setVisibility(GONE);
                        break;
                    case 33:
                        resolutionThree.setVisibility(VISIBLE);
                        resolutionFour.setVisibility(VISIBLE);
                        resolutionFive.setVisibility(VISIBLE);
                        break;
                }
                break;
        }
        if (globalVariables.CurrentScenario > 100) {
            switch (globalVariables.CurrentScenario) {
                case 101:
                    resolutionThree.setVisibility(VISIBLE);
                    break;
                case 103:
                case 104:
                case 105:
                    resolutionThree.setVisibility(VISIBLE);
                    resolutionFour.setVisibility(VISIBLE);
                    break;
            }
        }

        // Setup all additional views (mostly additional checkboxes) if required for the scenario
        final CheckBox additionalCheckbox = findViewById(R.id.additional_checkbox_one);
        final CheckBox additionalCheckboxTwo = findViewById(R.id.additional_checkbox_two);
        final CheckBox additionalCheckboxThree = findViewById(R.id.additional_checkbox_three);
        additionalCheckbox.setTypeface(arnopro);
        additionalCheckboxTwo.setTypeface(arnopro);
        additionalCheckboxThree.setTypeface(arnopro);
        TextView additionalGroupHeading = findViewById(R.id.additional_group_heading);
        additionalGroupHeading.setTypeface(teutonic);
        final TextView selectInvestigatorHeading = findViewById(R.id.select_investigator_heading);
        selectInvestigatorHeading.setTypeface(teutonic);
        TextView countersHeading = findViewById(R.id.investigator_counters_heading);
        countersHeading.setTypeface(teutonic);
        final RadioGroup resolutionOptions = findViewById(R.id.resolution_options);
        final RadioButton resolutionOptionOne = findViewById(R.id.resolution_option_one);
        RadioButton resolutionOptionTwo = findViewById(R.id.resolution_option_two);
        resolutionOptionOne.setTypeface(arnopro);
        resolutionOptionTwo.setTypeface(arnopro);
        final LinearLayout selectInvestigator = findViewById(R.id.select_investigator);
        final LinearLayout selectInvestigatorRight = findViewById(R.id.select_investigator_right);
        final CheckBox selectInvestigatorOne = findViewById(R.id.select_investigator_one);
        final CheckBox selectInvestigatorTwo = findViewById(R.id.select_investigator_two);
        final CheckBox selectInvestigatorThree = findViewById(R.id.select_investigator_three);
        final CheckBox selectInvestigatorFour = findViewById(R.id.select_investigator_four);
        final CheckBox selectInvestigatorFive = findViewById(R.id.select_investigator_five);
        selectInvestigatorOne.setTypeface(arnopro);
        selectInvestigatorTwo.setTypeface(arnopro);
        selectInvestigatorThree.setTypeface(arnopro);
        selectInvestigatorFour.setTypeface(arnopro);
        selectInvestigatorFive.setTypeface(arnopro);
        LinearLayout additionalGroup = findViewById(R.id.additional_group);
        LinearLayout additionalGroupSetTwo = findViewById(R.id.additional_group_set_two);
        final CheckBox additionalGroupOne = findViewById(R.id.additional_group_one);
        final CheckBox additionalGroupTwo = findViewById(R.id.additional_group_two);
        CheckBox additionalGroupThree = findViewById(R.id.additional_group_three);
        CheckBox additionalGroupFour = findViewById(R.id.additional_group_four);
        CheckBox additionalGroupFive = findViewById(R.id.additional_group_five);
        CheckBox additionalGroupSix = findViewById(R.id.additional_group_six);
        CheckBox additionalGroupSeven = findViewById(R.id.additional_group_seven);
        additionalGroupOne.setTypeface(arnopro);
        additionalGroupTwo.setTypeface(arnopro);
        additionalGroupThree.setTypeface(arnopro);
        additionalGroupFour.setTypeface(arnopro);
        additionalGroupFive.setTypeface(arnopro);
        additionalGroupSix.setTypeface(arnopro);
        additionalGroupSeven.setTypeface(arnopro);

        // Setup additional counters
        final LinearLayout investigatorCounters = findViewById(R.id.investigator_counters);
        final LinearLayout counterOne = findViewById(R.id.investigator_counter_one);
        LinearLayout counterTwo = findViewById(R.id.investigator_counter_two);
        LinearLayout counterThree = findViewById(R.id.investigator_counter_three);
        LinearLayout counterFour = findViewById(R.id.investigator_counter_four);
        final TextView counterOneName = findViewById(R.id.investigator_counter_one_name);
        TextView counterTwoName = findViewById(R.id.investigator_counter_two_name);
        TextView counterThreeName = findViewById(R.id.investigator_counter_three_name);
        TextView counterFourName = findViewById(R.id.investigator_counter_four_name);
        ImageView counterOneDecrement = findViewById(R.id.investigator_counter_one_decrement);
        ImageView counterTwoDecrement = findViewById(R.id.investigator_counter_two_decrement);
        ImageView counterThreeDecrement = findViewById(R.id.investigator_counter_three_decrement);
        ImageView counterFourDecrement = findViewById(R.id.investigator_counter_four_decrement);
        final TextView counterOneAmount = findViewById(R.id.investigator_counter_one_amount);
        final TextView counterTwoAmount = findViewById(R.id.investigator_counter_two_amount);
        final TextView counterThreeAmount = findViewById(R.id.investigator_counter_three_amount);
        final TextView counterFourAmount = findViewById(R.id.investigator_counter_four_amount);
        ImageView counterOneIncrement = findViewById(R.id.investigator_counter_one_increment);
        ImageView counterTwoIncrement = findViewById(R.id.investigator_counter_two_increment);
        ImageView counterThreeIncrement = findViewById(R.id.investigator_counter_three_increment);
        ImageView counterFourIncrement = findViewById(R.id.investigator_counter_four_increment);
        counterOneName.setTypeface(arnoprobold);
        counterTwoName.setTypeface(arnoprobold);
        counterThreeName.setTypeface(arnoprobold);
        counterFourName.setTypeface(arnoprobold);
        counterOneAmount.setTypeface(wolgastbold);
        counterTwoAmount.setTypeface(wolgastbold);
        counterThreeAmount.setTypeface(wolgastbold);
        counterFourAmount.setTypeface(wolgastbold);
        counterOneDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = counterOneAmount.getText().toString();
                int amount = Integer.valueOf(number);
                if (amount > 0) {
                    amount += -1;
                    counterOneAmount.setText(String.valueOf(amount));
                }
            }
        });
        counterTwoDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = counterTwoAmount.getText().toString();
                int amount = Integer.valueOf(number);
                if (amount > 0) {
                    amount += -1;
                    counterTwoAmount.setText(String.valueOf(amount));
                }
            }
        });
        counterThreeDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = counterThreeAmount.getText().toString();
                int amount = Integer.valueOf(number);
                if (amount > 0) {
                    amount += -1;
                    counterThreeAmount.setText(String.valueOf(amount));
                }
            }
        });
        counterFourDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = counterFourAmount.getText().toString();
                int amount = Integer.valueOf(number);
                if (amount > 0) {
                    amount += -1;
                    counterFourAmount.setText(String.valueOf(amount));
                }
            }
        });
        counterOneIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = counterOneAmount.getText().toString();
                int amount = Integer.valueOf(number);
                if (amount < 99) {
                    amount += 1;
                    counterOneAmount.setText(String.valueOf(amount));
                }
            }
        });
        counterTwoIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = counterTwoAmount.getText().toString();
                int amount = Integer.valueOf(number);
                if (amount < 99) {
                    amount += 1;
                    counterTwoAmount.setText(String.valueOf(amount));
                }
            }
        });
        counterThreeIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = counterThreeAmount.getText().toString();
                int amount = Integer.valueOf(number);
                if (amount < 99) {
                    amount += 1;
                    counterThreeAmount.setText(String.valueOf(amount));
                }
            }
        });
        counterFourIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = counterFourAmount.getText().toString();
                int amount = Integer.valueOf(number);
                if (amount < 99) {
                    amount += 1;
                    counterFourAmount.setText(String.valueOf(amount));
                }
            }
        });

        switch (globalVariables.CurrentCampaign) {
            case 1:
                switch (globalVariables.CurrentScenario) {
                    // The Midnight Masks
                    case 2:
                        // Ghoul Priest checkbox
                        if (globalVariables.GhoulPriest == 1) {
                            additionalCheckbox.setVisibility(VISIBLE);
                            additionalCheckbox.setText(R.string.ghoul_priest_checkbox);
                        }
                        // Setup cultists interrogated view
                        additionalGroupHeading.setVisibility(VISIBLE);
                        additionalGroupHeading.setText(R.string.cultists_interrogated_resolution);
                        additionalGroup.setVisibility(VISIBLE);
                        additionalGroupOne.setText(drew);
                        additionalGroupTwo.setText(R.string.peter);
                        additionalGroupThree.setText(R.string.ruth);
                        additionalGroupFour.setText(R.string.herman);
                        additionalGroupFive.setText(R.string.victoria);
                        additionalGroupSix.setText(R.string.masked_hunter);
                        break;
                    case 3:
                        // Ghoul Priest checkbox
                        if (globalVariables.GhoulPriest == 1) {
                            additionalCheckbox.setVisibility(VISIBLE);
                            additionalCheckbox.setText(R.string.ghoul_priest_checkbox);
                        }
                        break;
                }
                break;
            case 2:
                switch (globalVariables.CurrentScenario) {
                    case 2:
                        // Cheated checkbox
                        additionalCheckbox.setVisibility(VISIBLE);
                        additionalCheckbox.setText(R.string.cheated_checkbox);
                        additionalCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (compoundButton.isChecked()) {
                                    resolutionAdditional.setVisibility(VISIBLE);
                                    resolutionAdditional.setText(R.string.cheated_text);
                                } else {
                                    resolutionAdditional.setVisibility(GONE);
                                }
                            }
                        });
                        break;
                    case 4:
                        // Adam Lynch and Harold Walsted checkbox
                        additionalCheckbox.setVisibility(VISIBLE);
                        additionalCheckbox.setText(R.string.adam_lynch_harold_walsted);
                        if (globalVariables.AdamLynchHaroldWalsted == 1) {
                            additionalCheckbox.setChecked(true);
                        } else {
                            additionalCheckbox.setChecked(false);
                        }
                        break;
                    case 5:
                        resignedOne.setText(defeated);
                        resignedTwo.setText(defeated);
                        resignedThree.setText(defeated);
                        resignedFour.setText(defeated);
                        // Engine car
                        globalVariables.EngineCar = 0;
                        additionalCheckbox.setVisibility(VISIBLE);
                        additionalCheckbox.setText(R.string.engine_car_dodge);
                        additionalCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (compoundButton.isChecked()) {
                                    additionalCheckboxTwo.setChecked(false);
                                    globalVariables.EngineCar = 1;
                                    selectInvestigatorHeading.setVisibility(VISIBLE);
                                    selectInvestigatorHeading.setText(R.string.engine_car_investigator);
                                    selectInvestigator.setVisibility(VISIBLE);
                                    switch (globalVariables.Investigators.size()) {
                                        case 4:
                                            selectInvestigatorFour.setVisibility(VISIBLE);
                                            selectInvestigatorFour.setText(investigatorNames[globalVariables
                                                    .Investigators.get(3)
                                                    .Name]);
                                            selectInvestigatorFour.setOnCheckedChangeListener(new CompoundButton
                                                    .OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                                    if (compoundButton.isChecked()) {
                                                        selectInvestigatorThree.setChecked(false);
                                                        selectInvestigatorTwo.setChecked(false);
                                                        selectInvestigatorOne.setChecked(false);
                                                    }
                                                }
                                            });
                                        case 3:
                                            selectInvestigatorThree.setVisibility(VISIBLE);
                                            selectInvestigatorThree.setText(investigatorNames[globalVariables
                                                    .Investigators.get
                                                            (2).Name]);
                                            selectInvestigatorThree.setOnCheckedChangeListener(new CompoundButton
                                                    .OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                                    if (compoundButton.isChecked()) {
                                                        selectInvestigatorFour.setChecked(false);
                                                        selectInvestigatorTwo.setChecked(false);
                                                        selectInvestigatorOne.setChecked(false);
                                                    }
                                                }
                                            });
                                        case 2:
                                            selectInvestigatorTwo.setVisibility(VISIBLE);
                                            selectInvestigatorTwo.setText(investigatorNames[globalVariables
                                                    .Investigators.get(1)
                                                    .Name]);
                                            selectInvestigatorTwo.setOnCheckedChangeListener(new CompoundButton
                                                    .OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                                    if (compoundButton.isChecked()) {
                                                        selectInvestigatorFour.setChecked(false);
                                                        selectInvestigatorThree.setChecked(false);
                                                        selectInvestigatorOne.setChecked(false);
                                                    }
                                                }
                                            });
                                        case 1:
                                            selectInvestigatorOne.setVisibility(VISIBLE);
                                            selectInvestigatorOne.setText(investigatorNames[globalVariables
                                                    .Investigators.get(0)
                                                    .Name]);
                                            selectInvestigatorOne.setOnCheckedChangeListener(new CompoundButton
                                                    .OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                                    if (compoundButton.isChecked()) {
                                                        selectInvestigatorFour.setChecked(false);
                                                        selectInvestigatorThree.setChecked(false);
                                                        selectInvestigatorTwo.setChecked(false);
                                                    }
                                                }
                                            });
                                    }
                                } else {
                                    globalVariables.EngineCar = 0;
                                    selectInvestigatorHeading.setVisibility(GONE);
                                    selectInvestigator.setVisibility(GONE);
                                }
                            }
                        });
                        additionalCheckboxTwo.setVisibility(VISIBLE);
                        additionalCheckboxTwo.setText(R.string.engine_car_endure);
                        additionalCheckboxTwo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (compoundButton.isChecked()) {
                                    additionalCheckbox.setChecked(false);
                                    globalVariables.EngineCar = 2;
                                    selectInvestigatorHeading.setVisibility(VISIBLE);
                                    selectInvestigatorHeading.setText(R.string.engine_car_investigator);
                                    selectInvestigator.setVisibility(VISIBLE);
                                    switch (globalVariables.Investigators.size()) {
                                        case 4:
                                            selectInvestigatorFour.setVisibility(VISIBLE);
                                            selectInvestigatorFour.setText(investigatorNames[globalVariables
                                                    .Investigators.get(3)
                                                    .Name]);
                                            selectInvestigatorFour.setOnCheckedChangeListener(new CompoundButton
                                                    .OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                                    if (compoundButton.isChecked()) {
                                                        selectInvestigatorThree.setChecked(false);
                                                        selectInvestigatorTwo.setChecked(false);
                                                        selectInvestigatorOne.setChecked(false);
                                                    }
                                                }
                                            });
                                        case 3:
                                            selectInvestigatorThree.setVisibility(VISIBLE);
                                            selectInvestigatorThree.setText(investigatorNames[globalVariables
                                                    .Investigators.get
                                                            (2).Name]);
                                            selectInvestigatorThree.setOnCheckedChangeListener(new CompoundButton
                                                    .OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                                    if (compoundButton.isChecked()) {
                                                        selectInvestigatorFour.setChecked(false);
                                                        selectInvestigatorTwo.setChecked(false);
                                                        selectInvestigatorOne.setChecked(false);
                                                    }
                                                }
                                            });
                                        case 2:
                                            selectInvestigatorTwo.setVisibility(VISIBLE);
                                            selectInvestigatorTwo.setText(investigatorNames[globalVariables
                                                    .Investigators.get(1)
                                                    .Name]);
                                            selectInvestigatorTwo.setOnCheckedChangeListener(new CompoundButton
                                                    .OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                                    if (compoundButton.isChecked()) {
                                                        selectInvestigatorFour.setChecked(false);
                                                        selectInvestigatorThree.setChecked(false);
                                                        selectInvestigatorOne.setChecked(false);
                                                    }
                                                }
                                            });
                                        case 1:
                                            selectInvestigatorOne.setVisibility(VISIBLE);
                                            selectInvestigatorOne.setText(investigatorNames[globalVariables
                                                    .Investigators.get(0)
                                                    .Name]);
                                            selectInvestigatorOne.setOnCheckedChangeListener(new CompoundButton
                                                    .OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                                    if (compoundButton.isChecked()) {
                                                        selectInvestigatorFour.setChecked(false);
                                                        selectInvestigatorThree.setChecked(false);
                                                        selectInvestigatorTwo.setChecked(false);
                                                    }
                                                }
                                            });
                                    }
                                } else {
                                    globalVariables.EngineCar = 0;
                                    selectInvestigatorHeading.setVisibility(GONE);
                                    selectInvestigator.setVisibility(GONE);
                                }
                            }
                        });
                        break;
                    case 6:
                        // Sacrificed view
                        additionalGroupHeading.setVisibility(VISIBLE);
                        additionalGroupHeading.setText(R.string.underneath_agenda_deck);
                        additionalGroup.setVisibility(VISIBLE);
                        additionalGroupOne.setText(R.string.henry_armitage);
                        additionalGroupTwo.setText(R.string.warren_rice);
                        additionalGroupThree.setText(R.string.francis_morgan);
                        additionalGroupFour.setText(R.string.zebulon_whateley);
                        additionalGroupFive.setText(R.string.earl_sawyer);
                        additionalGroupSix.setText(R.string.another_ally);
                        break;
                    case 8:
                        globalVariables.BroodsEscaped = 0;
                        additionalCounterLayout.setVisibility(VISIBLE);
                        additionalCounter.setText(R.string.brood_escaped_counter);
                        additionalAmount.setText(String.valueOf(globalVariables.BroodsEscaped));
                        additionalDecrement.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int current = globalVariables.BroodsEscaped;
                                if (current > 0) {
                                    globalVariables.BroodsEscaped += -1;
                                    additionalAmount.setText(String.valueOf(globalVariables.BroodsEscaped));
                                }
                            }
                        });
                        additionalIncrement.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int current = globalVariables.BroodsEscaped;
                                if (current < 5) {
                                    globalVariables.BroodsEscaped += 1;
                                    additionalAmount.setText(String.valueOf(globalVariables.BroodsEscaped));
                                }
                            }
                        });
                        break;
                }
                break;
            case 3:

                // Chasing the stranger view
                strangerCounter = 0;
                additionalCounterLayout.setVisibility(VISIBLE);
                additionalCounter.setText(R.string.stranger_counter);
                additionalAmount.setText(String.valueOf(strangerCounter));
                additionalDecrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (strangerCounter > 0) {
                            strangerCounter += -1;
                            additionalAmount.setText(String.valueOf(strangerCounter));
                        }
                    }
                });
                additionalIncrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (strangerCounter < 99) {
                            strangerCounter += 1;
                            additionalAmount.setText(String.valueOf(strangerCounter));
                        }
                    }
                });

                switch (globalVariables.CurrentScenario) {
                    case 1:
                        additionalCheckbox.setVisibility(VISIBLE);
                        additionalCheckbox.setText(R.string.box_office);
                        selectInvestigatorHeading.setVisibility(VISIBLE);
                        selectInvestigatorHeading.setText(R.string.advanced_2b);
                        selectInvestigator.setVisibility(VISIBLE);
                        selectInvestigatorOne.setVisibility(VISIBLE);
                        selectInvestigatorOne.setText(R.string.city_aflame);
                        selectInvestigatorOne.setOnCheckedChangeListener(new CompoundButton
                                .OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (compoundButton.isChecked()) {
                                    selectInvestigatorTwo.setChecked(false);
                                    selectInvestigatorThree.setChecked(false);
                                }
                            }
                        });
                        selectInvestigatorTwo.setVisibility(VISIBLE);
                        selectInvestigatorTwo.setText(R.string.path_mine);
                        selectInvestigatorTwo.setOnCheckedChangeListener(new CompoundButton
                                .OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (compoundButton.isChecked()) {
                                    selectInvestigatorOne.setChecked(false);
                                    selectInvestigatorThree.setChecked(false);
                                }
                            }
                        });
                        selectInvestigatorThree.setVisibility(VISIBLE);
                        selectInvestigatorThree.setText(R.string.shores_hail);
                        selectInvestigatorThree.setOnCheckedChangeListener(new CompoundButton
                                .OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (compoundButton.isChecked()) {
                                    selectInvestigatorTwo.setChecked(false);
                                    selectInvestigatorOne.setChecked(false);
                                }
                            }
                        });
                        switch (globalVariables.Theatre) {
                            case 1:
                                selectInvestigatorOne.setChecked(true);
                                break;
                            case 2:
                                selectInvestigatorTwo.setChecked(true);
                                break;
                            case 3:
                                selectInvestigatorThree.setChecked(true);
                                break;
                        }
                        break;
                    case 2:
                        selectInvestigatorHeading.setVisibility(VISIBLE);

                        selectInvestigatorHeading.setText(R.string.vips_interviewed);
                        selectInvestigator.setVisibility(VISIBLE);
                        selectInvestigator.setVisibility(VISIBLE);
                        selectInvestigatorOne.setVisibility(VISIBLE);
                        selectInvestigatorOne.setText(R.string.constance);
                        selectInvestigatorTwo.setVisibility(VISIBLE);
                        selectInvestigatorTwo.setText(R.string.jordan);
                        selectInvestigatorThree.setVisibility(VISIBLE);
                        selectInvestigatorThree.setText(R.string.sebastien);
                        selectInvestigatorFour.setVisibility(VISIBLE);
                        selectInvestigatorFour.setText(R.string.ashleigh);
                        selectInvestigatorFive.setVisibility(VISIBLE);
                        selectInvestigatorFive.setText(R.string.ishimaru);
                        additionalGroupHeading.setVisibility(VISIBLE);

                        additionalGroupHeading.setText(R.string.vips_slain);
                        additionalGroup.setVisibility(VISIBLE);
                        additionalGroupOne.setText(R.string.constance);
                        additionalGroupTwo.setText(R.string.jordan);
                        additionalGroupThree.setText(R.string.ishimaru);
                        additionalGroupFour.setText(R.string.sebastien);
                        additionalGroupFive.setText(R.string.ashleigh);
                        additionalGroupSix.setVisibility(GONE);

                        countersHeading.setVisibility(VISIBLE);
                        countersHeading.setText(R.string.allocated_experience);
                        investigatorCounters.setVisibility(VISIBLE);
                        switch (globalVariables.Investigators.size()) {
                            case 4:
                                counterFour.setVisibility(VISIBLE);
                                counterFourName.setText(investigatorNames[globalVariables
                                        .Investigators.get(3)
                                        .Name]);
                            case 3:
                                counterThree.setVisibility(VISIBLE);
                                counterThreeName.setText(investigatorNames[globalVariables
                                        .Investigators.get(2)
                                        .Name]);
                            case 2:
                                counterTwo.setVisibility(VISIBLE);
                                counterTwoName.setText(investigatorNames[globalVariables
                                        .Investigators.get(1)
                                        .Name]);
                            case 1:
                                counterOne.setVisibility(VISIBLE);
                                counterOneName.setText(investigatorNames[globalVariables
                                        .Investigators.get(0)
                                        .Name]);
                        }
                        break;
                    case 4:
                        if (globalVariables.Sebastien == 0 || globalVariables.Sebastien == 1 || globalVariables
                                .Sebastien == 3) {
                            additionalCheckbox.setVisibility(VISIBLE);
                            additionalCheckbox.setText(R.string.sebastien_victory);
                        }
                        break;
                    case 5:
                        if (globalVariables.Constance == 0 || globalVariables.Constance == 1 || globalVariables
                                .Sebastien == 3) {
                            additionalCheckbox.setVisibility(VISIBLE);
                            additionalCheckbox.setText(R.string.constance_victory);
                        }
                        additionalCheckboxTwo.setVisibility(VISIBLE);
                        additionalCheckboxTwo.setText(R.string.daniel_chesterfield_inplay);
                        break;
                    case 7:
                        if (globalVariables.Jordan == 0 || globalVariables.Jordan == 1 || globalVariables.Jordan == 3) {
                            additionalCheckbox.setVisibility(VISIBLE);
                            additionalCheckbox.setText(R.string.jordan_victory);
                        }
                        break;
                    case 8:
                        resignedOne.setText(defeated);
                        resignedTwo.setText(defeated);
                        resignedThree.setText(defeated);
                        resignedFour.setText(defeated);
                        if (globalVariables.Ishimaru == 0 || globalVariables.Ishimaru == 1 || globalVariables
                                .Ishimaru == 3) {
                            additionalCheckbox.setVisibility(VISIBLE);
                            additionalCheckbox.setText(R.string.ishimaru_victory);
                        }
                        additionalGroupHeading.setVisibility(VISIBLE);
                        additionalGroupHeading.setText(R.string.advanced_2b);
                        additionalGroup.setVisibility(VISIBLE);
                        additionalGroupOne.setVisibility(VISIBLE);
                        additionalGroupOne.setText(R.string.man_in_pallid_mask_defeated);
                        additionalGroupOne.setOnCheckedChangeListener(new CompoundButton
                                .OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (compoundButton.isChecked()) {
                                    additionalGroupTwo.setChecked(false);
                                }
                            }
                        });
                        additionalGroupTwo.setVisibility(VISIBLE);
                        additionalGroupTwo.setText(R.string.spent_clues_advance);
                        additionalGroupTwo.setOnCheckedChangeListener(new CompoundButton
                                .OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (compoundButton.isChecked()) {
                                    additionalGroupOne.setChecked(false);
                                }
                            }
                        });
                        additionalGroupThree.setVisibility(GONE);
                        additionalGroupSetTwo.setVisibility(GONE);
                        break;
                    case 9:
                        if (globalVariables.Ashleigh == 0 || globalVariables.Ashleigh == 1 || globalVariables
                                .Ashleigh == 3) {
                            additionalCheckbox.setVisibility(VISIBLE);
                            additionalCheckbox.setText(R.string.ashleigh_victory);
                        }
                        additionalGroupHeading.setVisibility(VISIBLE);
                        additionalGroupHeading.setText(R.string.advanced_agenda_one);
                        additionalGroup.setVisibility(VISIBLE);
                        additionalGroupOne.setVisibility(VISIBLE);
                        additionalGroupOne.setText(R.string.mark_one_conviction);
                        additionalGroupOne.setOnCheckedChangeListener(new CompoundButton
                                .OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (compoundButton.isChecked()) {
                                    additionalGroupTwo.setChecked(false);
                                }
                            }
                        });
                        additionalGroupTwo.setVisibility(VISIBLE);
                        additionalGroupTwo.setText(R.string.mark_one_doubt);
                        additionalGroupTwo.setOnCheckedChangeListener(new CompoundButton
                                .OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (compoundButton.isChecked()) {
                                    additionalGroupOne.setChecked(false);
                                }
                            }
                        });
                        additionalGroupThree.setVisibility(GONE);
                        additionalGroupSetTwo.setVisibility(GONE);
                        break;
                    case 10:
                        resignedOne.setText(R.string.killed);
                        resignedTwo.setText(R.string.killed);
                        resignedThree.setText(R.string.killed);
                        resignedFour.setText(R.string.killed);
                        selectInvestigatorHeading.setText(R.string.possession);
                        switch (globalVariables.Investigators.size()) {
                            case 4:
                                selectInvestigatorFour.setVisibility(VISIBLE);
                                selectInvestigatorFour.setText(investigatorNames[globalVariables
                                        .Investigators.get(3)
                                        .Name]);
                            case 3:
                                selectInvestigatorThree.setVisibility(VISIBLE);
                                selectInvestigatorThree.setText(investigatorNames[globalVariables
                                        .Investigators.get
                                                (2).Name]);
                            case 2:
                                selectInvestigatorTwo.setVisibility(VISIBLE);
                                selectInvestigatorTwo.setText(investigatorNames[globalVariables
                                        .Investigators.get(1)
                                        .Name]);
                            case 1:
                                selectInvestigatorOne.setVisibility(VISIBLE);
                                selectInvestigatorOne.setText(investigatorNames[globalVariables
                                        .Investigators.get(0)
                                        .Name]);
                                break;
                        }
                }
                break;
            case 4:
                switch (globalVariables.CurrentScenario) {
                    case 1:
                    case 6:
                    case 10:
                    case 19:
                        // Vengeance view
                        vengeanceCounter = 0;
                        additionalCounterLayout.setVisibility(VISIBLE);
                        additionalCounter.setText(R.string.vengeance_counter);
                        additionalAmount.setText(String.valueOf(vengeanceCounter));
                        additionalDecrement.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (vengeanceCounter > 0) {
                                    vengeanceCounter += -1;
                                    additionalAmount.setText(String.valueOf(vengeanceCounter));
                                }
                            }
                        });
                        additionalIncrement.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (vengeanceCounter < 99) {
                                    vengeanceCounter += 1;
                                    additionalAmount.setText(String.valueOf(vengeanceCounter));
                                }
                            }
                        });

                }

                switch (globalVariables.CurrentScenario) {
                    case 6:
                        selectInvestigator.setVisibility(VISIBLE);
                        selectInvestigatorRight.setVisibility(GONE);
                        selectInvestigatorOne.setVisibility(VISIBLE);
                        selectInvestigatorOne.setText(R.string.harbinger);
                        selectInvestigatorOne.setOnCheckedChangeListener(new CompoundButton
                                .OnCheckedChangeListener() {

                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean
                                    isChecked) {
                                if (isChecked) {
                                    selectInvestigator.setPadding(0, 0, 0, 0);
                                    investigatorCounters.setVisibility(VISIBLE);
                                    counterOne.setVisibility(VISIBLE);
                                    counterOneName.setText(R.string
                                            .harbinger_damage);
                                } else {
                                    selectInvestigator.setPadding(0, 0, 0, getResources().getDimensionPixelSize(R
                                            .dimen.activity_vertical_margin));
                                    investigatorCounters.setVisibility(GONE);
                                }
                            }
                        });
                        break;
                    case 8:
                        // Act ones completed view
                        vengeanceCounter = 0;
                        additionalCounterLayout.setVisibility(VISIBLE);
                        additionalCounter.setText(R.string.act_ones_completed);
                        additionalAmount.setText(String.valueOf(vengeanceCounter));
                        additionalDecrement.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (vengeanceCounter > 0) {
                                    vengeanceCounter += -1;
                                    additionalAmount.setText(String.valueOf(vengeanceCounter));
                                }
                            }
                        });
                        additionalIncrement.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (vengeanceCounter < 3) {
                                    vengeanceCounter += 1;
                                    additionalAmount.setText(String.valueOf(vengeanceCounter));
                                }
                            }
                        });

                        // Checkboxes
                        additionalCheckbox.setVisibility(VISIBLE);
                        additionalCheckbox.setText(R.string.act_3b_completed);
                        additionalCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                refreshResolution();
                            }
                        });
                        additionalCheckboxTwo.setVisibility(VISIBLE);
                        additionalCheckboxTwo.setText(R.string.act_3d_completed);
                        additionalCheckboxTwo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                refreshResolution();
                            }
                        });
                        additionalCheckboxThree.setVisibility(VISIBLE);
                        additionalCheckboxThree.setText(R.string.act_3f_completed);
                        additionalCheckboxThree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener
                                () {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                refreshResolution();
                            }
                        });

                        resolutionAdditional.setTypeface(arnoproitalic, Typeface.BOLD);
                        break;
                    case 10:
                        // Tenochtitlan locations
                        investigatorCounters.setVisibility(VISIBLE);
                        counterTwo.setVisibility(VISIBLE);
                        counterTwoName.setText(R.string.tenochtitlan_locations);

                        // Harbinger of Valusia
                        if (globalVariables.Harbinger != -1) {
                            selectInvestigator.setVisibility(VISIBLE);
                            selectInvestigatorRight.setVisibility(GONE);
                            selectInvestigatorOne.setVisibility(VISIBLE);
                            selectInvestigatorOne.setText(R.string.harbinger);
                            selectInvestigatorOne.setOnCheckedChangeListener(new CompoundButton
                                    .OnCheckedChangeListener() {

                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean
                                        isChecked) {
                                    if (isChecked) {
                                        selectInvestigator.setPadding(0, 0, 0, 0);
                                        counterOne.setVisibility(VISIBLE);
                                        counterOneName.setText(R.string
                                                .harbinger_damage);
                                        counterOneAmount.setText(Integer.toString(globalVariables.Harbinger));
                                    } else {
                                        selectInvestigator.setPadding(0, 0, 0, getResources().getDimensionPixelSize(R
                                                .dimen.activity_vertical_margin));
                                        counterOne.setVisibility(GONE);
                                    }
                                }
                            });
                        }
                        break;
                    case 19:
                        // Harbinger of Valusia
                        if (globalVariables.Harbinger != -1) {
                            selectInvestigator.setVisibility(VISIBLE);
                            selectInvestigatorRight.setVisibility(GONE);
                            selectInvestigatorOne.setVisibility(VISIBLE);
                            selectInvestigatorOne.setText(R.string.harbinger);
                            selectInvestigatorOne.setOnCheckedChangeListener(new CompoundButton
                                    .OnCheckedChangeListener() {

                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean
                                        isChecked) {
                                    if (isChecked) {
                                        selectInvestigator.setPadding(0, 0, 0, 0);
                                        counterOne.setVisibility(VISIBLE);
                                        counterOneName.setText(R.string
                                                .harbinger_damage);
                                        counterOneAmount.setText(Integer.toString(globalVariables.Harbinger));
                                    } else {
                                        selectInvestigator.setPadding(0, 0, 0, getResources().getDimensionPixelSize(R
                                                .dimen.activity_vertical_margin));
                                        counterOne.setVisibility(GONE);
                                    }
                                }
                            });
                        }
                        break;
                }
                break;
        }
        if (globalVariables.CurrentScenario > 100) {
            switch (globalVariables.CurrentScenario) {
                // Carnevale of Horrors
                case 102:
                    additionalCheckbox.setVisibility(VISIBLE);
                    additionalCheckboxTwo.setVisibility(VISIBLE);
                    additionalCheckbox.setText(R.string.carnevale_sacrifice);
                    additionalCheckboxTwo.setText(R.string.carnevale_abbess);
                    additionalCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            if (compoundButton.isChecked()) {
                                additionalCheckboxTwo.setChecked(false);
                                resolutionAdditional.setVisibility(VISIBLE);
                                resolutionAdditional.setText(R.string.carnevale_reward_sacrifice);
                            } else {
                                resolutionAdditional.setVisibility(GONE);
                            }
                        }
                    });
                    additionalCheckboxTwo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            if (compoundButton.isChecked()) {
                                additionalCheckbox.setChecked(false);
                                resolutionAdditional.setVisibility(VISIBLE);
                                resolutionAdditional.setText(R.string.carnevale_reward_abbess);
                            } else {
                                resolutionAdditional.setVisibility(GONE);
                            }
                        }
                    });
                    break;
            }
        }

        Button playerCardsButton = findViewById(R.id.player_cards_button);
        playerCardsButton.setTypeface(teutonic);
        playerCardsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment playerCardsDialog = new PlayerCardsDialog();
                playerCardsDialog.show(getFragmentManager(), "player_cards");
            }
        });

        Button weaknessButton = findViewById(R.id.investigator_weakness_button);
        weaknessButton.setTypeface(teutonic);
        boolean weakness = false;
        for (int i = 0; i < globalVariables.Investigators.size(); i++) {
            switch (globalVariables.Investigators.get(i).Name) {
                case Investigator.ROLAND_BANKS:
                case Investigator.SKIDS_OTOOLE:
                case Investigator.ZOEY_SAMARAS:
                case Investigator.JENNY_BARNES:
                case Investigator.AKACHI_ONYELE:
                case Investigator.CALVIN_WRIGHT:
                    weakness = true;
                    break;
            }
        }

        weaknessButton.setVisibility(VISIBLE);
        weaknessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment weaknessDialog = new WeaknessDialog();
                weaknessDialog.show(getFragmentManager(), "weakness");
            }
        });

        Button logButton = findViewById(R.id.campaign_log_button);
        logButton.setTypeface(teutonic);
        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScenarioResolutionActivity.this, CampaignLogActivity.class);
                startActivity(intent);
            }
        });

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
                // Check scenario requirements have been met
                boolean dialog = true;
                // Make sure a resolution has been selected
                if (globalVariables.ScenarioResolution == -1) {
                    dialog = false;
                    Toast toast = Toast.makeText(ScenarioResolutionActivity.this, R.string.must_resolution, Toast
                            .LENGTH_SHORT);
                    toast.show();
                }
                switch (globalVariables.CurrentCampaign) {
                    case 1:
                        switch (globalVariables.CurrentScenario) {
                            case 1:
                                // Make sure a lead investigator is selected
                                if (!selectInvestigatorOne.isChecked() && !selectInvestigatorTwo.isChecked()
                                        && !selectInvestigatorThree.isChecked() && !selectInvestigatorFour
                                        .isChecked() && (globalVariables.ScenarioResolution == 1 || globalVariables
                                        .ScenarioResolution == 2)) {
                                    dialog = false;
                                    Toast toast = Toast.makeText(ScenarioResolutionActivity.this, R.string
                                            .must_lead_investigator, Toast
                                            .LENGTH_SHORT);
                                    toast.show();
                                }
                                break;
                        }
                        break;
                    case 2:
                        switch (globalVariables.CurrentScenario) {
                            case 5:
                                if (globalVariables.EngineCar != 0 && !selectInvestigatorOne.isChecked() &&
                                        !selectInvestigatorTwo.isChecked()
                                        && !selectInvestigatorThree.isChecked() && !selectInvestigatorFour
                                        .isChecked()) {
                                    dialog = false;
                                    Toast toast = Toast.makeText(ScenarioResolutionActivity.this, R.string
                                            .must_engine_car, Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                                break;
                            case 10:
                                if (globalVariables.ScenarioResolution == 0) {
                                    dialog = false;
                                    Toast toast = Toast.makeText(ScenarioResolutionActivity.this, R.string
                                            .must_act_ended, Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                                break;
                        }
                        break;
                    case 3:
                        switch (globalVariables.CurrentScenario) {
                            case 8:
                                // Make sure someone read Act II
                                if (!selectInvestigatorOne.isChecked() && !selectInvestigatorTwo.isChecked()
                                        && !selectInvestigatorThree.isChecked() && !selectInvestigatorFour
                                        .isChecked() && globalVariables.ScenarioResolution == 0) {
                                    dialog = false;
                                    Toast toast = Toast.makeText(ScenarioResolutionActivity.this, R.string
                                            .must_read_act, Toast
                                            .LENGTH_SHORT);
                                    toast.show();
                                }
                                break;
                        }
                        break;
                    case 4:
                        switch (globalVariables.CurrentScenario) {
                            case 1:
                                // If no resolution, make sure an option was selected
                                if (globalVariables.ScenarioResolution == 0 && !selectInvestigatorOne.isChecked() &&
                                        !selectInvestigatorTwo.isChecked() && !selectInvestigatorFive.isChecked()) {
                                    dialog = false;
                                    Toast toast = Toast.makeText(ScenarioResolutionActivity.this, R.string
                                            .must_option, Toast
                                            .LENGTH_SHORT);
                                    toast.show();
                                }
                                break;
                            case 6:
                                // If resolution three, make sure an option was selected
                                if (globalVariables.ScenarioResolution == 3 && resolutionOptions
                                        .getCheckedRadioButtonId() == -1) {
                                    dialog = false;
                                    Toast toast = Toast.makeText(ScenarioResolutionActivity.this, R.string
                                            .must_option, Toast
                                            .LENGTH_SHORT);
                                    toast.show();
                                }
                                break;
                        }
                        break;
                }
                // Open dialog if requirements are met
                if (dialog) {
                    DialogFragment finishScenarioDialog = new FinishScenarioDialog();
                    finishScenarioDialog.show(getFragmentManager(), "finish_scenario");
                }
            }
        });

        // If standalone scenario, hide all relevant views and set continue button to go back to main menu
        if (globalVariables.CurrentCampaign == 999) {
            defeatedHeading.setVisibility(GONE);
            defeatedOne.setVisibility(GONE);
            defeatedTwo.setVisibility(GONE);
            defeatedThree.setVisibility(GONE);
            defeatedFour.setVisibility(GONE);
            LinearLayout buttonBar = findViewById(R.id.button_bar);
            buttonBar.setVisibility(GONE);
            logButton.setVisibility(GONE);
            continueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ScenarioResolutionActivity.this, MainMenuActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }

    /*
        Listener for the defeated checkboxes
     */
    private class InvestigatorDefeatedCheckboxListener implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton button, boolean b) {
            int investigator = -1;
            ViewGroup parent = (ViewGroup) button.getParent();

            switch (parent.getId()) {
                case R.id.defeated_one_selection:
                    investigator = 0;
                    break;
                case R.id.defeated_two_selection:
                    investigator = 1;
                    break;
                case R.id.defeated_three_selection:
                    investigator = 2;
                    break;
                case R.id.defeated_four_selection:
                    investigator = 3;
                    break;
            }

            if (!button.isChecked()) {
                globalVariables.Investigators.get(investigator).TempStatus = 0;
            } else {
                for (int i = 0; i < parent.getChildCount(); i++) {
                    View view = parent.getChildAt(i);
                    if (view instanceof CheckBox) {
                        CheckBox box = (CheckBox) view;
                        if (box != button) {
                            box.setChecked(false);
                        }
                    }
                }
                switch (button.getId()) {
                    case R.id.resigned_button_one:
                    case R.id.resigned_button_two:
                    case R.id.resigned_button_three:
                    case R.id.resigned_button_four:
                        globalVariables.Investigators.get(investigator).TempStatus = 1;
                        break;
                    case R.id.damage_button_one:
                    case R.id.damage_button_two:
                    case R.id.damage_button_three:
                    case R.id.damage_button_four:
                        globalVariables.Investigators.get(investigator).TempStatus = 2;
                        break;
                    case R.id.horror_button_one:
                    case R.id.horror_button_two:
                    case R.id.horror_button_three:
                    case R.id.horror_button_four:
                        globalVariables.Investigators.get(investigator).TempStatus = 3;
                        break;
                }
            }

            refreshResolution();
        }
    }

    /*
        Refresh resolution
     */
    private void refreshResolution() {
        // Clear and reset the select resolution checkbox to refresh the resolution text
        RadioGroup selectResolution = findViewById(R.id.select_resolution);
        RadioButton noResolution = findViewById(R.id.no_resolution);
        RadioButton resolutionOne = findViewById(R.id.resolution_one);
        RadioButton resolutionTwo = findViewById(R.id.resolution_two);
        RadioButton resolutionThree = findViewById(R.id.resolution_three);
        RadioButton resolutionFour = findViewById(R.id.resolution_four);
        if (globalVariables.ScenarioResolution != -1) {
            selectResolution.clearCheck();
            switch (globalVariables.ScenarioResolution) {
                case 0:
                    noResolution.setChecked(true);
                    break;
                case 1:
                    resolutionOne.setChecked(true);
                    break;
                case 2:
                    resolutionTwo.setChecked(true);
                    break;
                case 3:
                    resolutionThree.setChecked(true);
                    break;
                case 4:
                    resolutionFour.setChecked(true);
                    break;
            }
        }
    }

    /*
        Listener for the defeated images
     */
    private class InvestigatorDefeatedImageListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            int investigator = -1;
            int buttonId = -1;
            ViewGroup parent = (ViewGroup) view.getParent();
            for (int i = 0; i < parent.getChildCount(); i++) {
                View image = parent.getChildAt(i);
                if (view == image) {
                    buttonId = i - 1;
                }
            }
            CheckBox button = (CheckBox) parent.getChildAt(buttonId);

            switch (parent.getId()) {
                case R.id.defeated_one_selection:
                    investigator = 0;
                    break;
                case R.id.defeated_two_selection:
                    investigator = 1;
                    break;
                case R.id.defeated_three_selection:
                    investigator = 2;
                    break;
                case R.id.defeated_four_selection:
                    investigator = 3;
                    break;
            }

            if (button.isChecked()) {
                button.setChecked(false);
                globalVariables.Investigators.get(investigator).TempStatus = 0;
            } else {
                for (int i = 0; i < parent.getChildCount(); i++) {
                    View v = parent.getChildAt(i);
                    if (v instanceof CheckBox) {
                        CheckBox box = (CheckBox) v;
                        if (box != button) {
                            box.setChecked(false);
                        }
                    }
                }
                button.setChecked(true);
            }
        }
    }

    /*
        Listener to set the resolution text
     */
    private class ResolutionCheckboxListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            LinearLayout parent = (LinearLayout) radioGroup.getParent();
            final TextView resolutionTextView = parent.findViewById(R.id.resolution_text);
            resolutionTextView.setVisibility(VISIBLE);
            final TextView resolutionTextViewAdditional = parent.findViewById(R.id.resolution_text_additional);
            final CheckBox additional = findViewById(R.id.additional_checkbox_one);
            final CheckBox additionalTwo = findViewById(R.id.additional_checkbox_two);
            final CheckBox additionalThree = findViewById(R.id.additional_checkbox_three);
            final TextView selectInvestigatorHeading = findViewById(R.id.select_investigator_heading);
            final LinearLayout selectInvestigator = findViewById(R.id.select_investigator);
            final LinearLayout selectInvestigatorRight = findViewById(R.id.select_investigator_right);
            final CheckBox selectInvestigatorOne = findViewById(R.id.select_investigator_one);
            final CheckBox selectInvestigatorTwo = findViewById(R.id.select_investigator_two);
            final CheckBox selectInvestigatorThree = findViewById(R.id.select_investigator_three);
            final CheckBox selectInvestigatorFour = findViewById(R.id.select_investigator_four);
            final CheckBox selectInvestigatorFive = findViewById(R.id.select_investigator_five);
            final RadioGroup resolutionOptions = findViewById(R.id.resolution_options);
            final RadioButton resolutionOptionOne = findViewById(R.id.resolution_option_one);
            final RadioButton resolutionOptionTwo = findViewById(R.id.resolution_option_two);
            final LinearLayout additionalCounterLayout = findViewById(R.id.additional_counter_layout);
            final TextView additionalCounter = findViewById(R.id.additional_counter);
            final LinearLayout textBoxLayout = findViewById(R.id.box_layout);
            final TextView textBoxHeading = findViewById(R.id.box_heading);

            // Set resolution
            switch (i) {
                case R.id.no_resolution:
                    globalVariables.ScenarioResolution = 0;
                    break;
                case R.id.resolution_one:
                    globalVariables.ScenarioResolution = 1;
                    break;
                case R.id.resolution_two:
                    globalVariables.ScenarioResolution = 2;
                    break;
                case R.id.resolution_three:
                    globalVariables.ScenarioResolution = 3;
                    break;
                case R.id.resolution_four:
                    globalVariables.ScenarioResolution = 4;
                    break;
            }

            // Set correct resolution text
            switch (globalVariables.CurrentCampaign) {
                // Night of the Zealot
                case 1:
                    switch (globalVariables.CurrentScenario) {
                        // The Gathering
                        case 1:
                            switch (globalVariables.ScenarioResolution) {
                                case 0:
                                    resolutionTextView.setText(R.string.gathering_no_resolution);
                                    break;
                                case 1:
                                    resolutionTextView.setText(R.string.gathering_resolution_one);
                                    break;
                                case 2:
                                    resolutionTextView.setText(R.string.gathering_resolution_two);
                                    break;
                                case 3:
                                    resolutionTextView.setText(R.string.gathering_resolution_three);
                                    break;
                            }
                            // Set up checkboxes to select lead investigator
                            if (globalVariables.ScenarioResolution == 1 || globalVariables.ScenarioResolution == 2) {
                                selectInvestigatorHeading.setVisibility(VISIBLE);
                                selectInvestigatorHeading.setText(R.string.select_lead_investigator);
                                selectInvestigator.setVisibility(VISIBLE);
                                String[] investigatorNames = getResources().getStringArray(R.array.investigators);
                                switch (globalVariables.Investigators.size()) {
                                    case 4:
                                        selectInvestigatorFour.setVisibility(VISIBLE);
                                        selectInvestigatorFour.setText(investigatorNames[globalVariables
                                                .Investigators.get(3)
                                                .Name]);
                                        selectInvestigatorFour.setOnCheckedChangeListener(new CompoundButton
                                                .OnCheckedChangeListener() {
                                            @Override
                                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                                if (compoundButton.isChecked()) {
                                                    selectInvestigatorThree.setChecked(false);
                                                    selectInvestigatorTwo.setChecked(false);
                                                    selectInvestigatorOne.setChecked(false);
                                                }
                                            }
                                        });
                                    case 3:
                                        selectInvestigatorThree.setVisibility(VISIBLE);
                                        selectInvestigatorThree.setText(investigatorNames[globalVariables
                                                .Investigators.get
                                                        (2).Name]);
                                        selectInvestigatorThree.setOnCheckedChangeListener(new CompoundButton
                                                .OnCheckedChangeListener() {
                                            @Override
                                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                                if (compoundButton.isChecked()) {
                                                    selectInvestigatorFour.setChecked(false);
                                                    selectInvestigatorTwo.setChecked(false);
                                                    selectInvestigatorOne.setChecked(false);
                                                }
                                            }
                                        });
                                    case 2:
                                        selectInvestigatorTwo.setVisibility(VISIBLE);
                                        selectInvestigatorTwo.setText(investigatorNames[globalVariables
                                                .Investigators.get(1)
                                                .Name]);
                                        selectInvestigatorTwo.setOnCheckedChangeListener(new CompoundButton
                                                .OnCheckedChangeListener() {
                                            @Override
                                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                                if (compoundButton.isChecked()) {
                                                    selectInvestigatorFour.setChecked(false);
                                                    selectInvestigatorThree.setChecked(false);
                                                    selectInvestigatorOne.setChecked(false);
                                                }
                                            }
                                        });
                                    case 1:
                                        selectInvestigatorOne.setVisibility(VISIBLE);
                                        selectInvestigatorOne.setText(investigatorNames[globalVariables
                                                .Investigators.get(0)
                                                .Name]);
                                        selectInvestigatorOne.setOnCheckedChangeListener(new CompoundButton
                                                .OnCheckedChangeListener() {
                                            @Override
                                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                                if (compoundButton.isChecked()) {
                                                    selectInvestigatorFour.setChecked(false);
                                                    selectInvestigatorThree.setChecked(false);
                                                    selectInvestigatorTwo.setChecked(false);
                                                }
                                            }
                                        });
                                        break;
                                }
                            } else {
                                selectInvestigatorHeading.setVisibility(GONE);
                                selectInvestigator.setVisibility(GONE);
                            }
                            break;
                        // Midnight Masks
                        case 2:
                            switch (globalVariables.ScenarioResolution) {
                                case 0:
                                    resolutionTextView.setText(R.string.midnight_no_resolution);
                                    break;
                                case 1:
                                    resolutionTextView.setText(R.string.midnight_resolution_one);
                                    break;
                                case 2:
                                    resolutionTextView.setText(R.string.midnight_resolution_two);
                                    break;
                            }
                            break;
                        // The Devourer Below
                        case 3:
                            switch (globalVariables.ScenarioResolution) {
                                case 0:
                                    resolutionTextView.setText(R.string.devourer_no_resolution);
                                    break;
                                case 1:
                                    resolutionTextView.setText(R.string.devourer_resolution_one);
                                    break;
                                case 2:
                                    resolutionTextView.setText(R.string.devourer_resolution_two);
                                    break;
                                case 3:
                                    resolutionTextView.setText(R.string.devourer_resolution_three);
                                    break;
                            }
                            break;
                    }
                    break;
                // The Dunwich Legacy
                case 2:
                    switch (globalVariables.CurrentScenario) {
                        // Extracurricular Activity
                        case 1:
                            switch (globalVariables.ScenarioResolution) {
                                case 0:
                                    resolutionTextView.setText(R.string.extracurricular_no_resolution);
                                    break;
                                case 1:
                                    resolutionTextView.setText(R.string.extracurricular_resolution_one);
                                    break;
                                case 2:
                                    resolutionTextView.setText(R.string.extracurricular_resolution_two);
                                    break;
                                case 3:
                                    resolutionTextView.setText(R.string.extracurricular_resolution_three);
                                    break;
                                case 4:
                                    resolutionTextView.setText(R.string.extracurricular_resolution_four);
                                    break;
                            }
                            break;
                        // House Always Wins
                        case 2:
                            switch (globalVariables.ScenarioResolution) {
                                case 0:
                                    resolutionTextView.setText(R.string.house_no_resolution);
                                    break;
                                case 1:
                                    resolutionTextView.setText(R.string.house_resolution_one);
                                    break;
                                case 2:
                                    resolutionTextView.setText(R.string.house_resolution_two);
                                    break;
                                case 3:
                                    resolutionTextView.setText(R.string.house_resolution_three);
                                    break;
                                case 4:
                                    resolutionTextView.setText(R.string.house_resolution_four);
                                    break;
                            }
                            break;
                        // Miskatonic Museum
                        case 4:
                            switch (globalVariables.ScenarioResolution) {
                                case 0:
                                    resolutionTextView.setText(R.string.miskatonic_no_resolution);
                                    break;
                                case 1:
                                    resolutionTextView.setText(R.string.miskatonic_resolution_one);
                                    break;
                                case 2:
                                    resolutionTextView.setText(R.string.miskatonic_resolution_two);
                                    break;
                            }
                            break;
                        // Essex County Express
                        case 5:
                            boolean defeated = false;
                            for (int a = 0; a < globalVariables.Investigators.size(); a++) {
                                if (globalVariables.Investigators.get(a).TempStatus > 0) {
                                    defeated = true;
                                }
                            }
                            if ((defeated || globalVariables.ScenarioResolution == 0 || globalVariables
                                    .ScenarioResolution == 2) && (globalVariables.Necronomicon == 2 ||
                                    globalVariables.HenryArmitage == 1 || globalVariables.FrancisMorgan == 1 ||
                                    globalVariables.WarrenRice == 1)) {
                                TextView additionalHeading = findViewById(R.id
                                        .additional_group_heading);
                                additionalHeading.setVisibility(VISIBLE);
                                additionalHeading.setText(R.string.kidnapped_cards);
                                LinearLayout additionalGroup = findViewById(R.id
                                        .additional_group);
                                additionalGroup.setVisibility(VISIBLE);
                                LinearLayout additionalGroupSetTwo = findViewById(R.id
                                        .additional_group_set_two);
                                additionalGroupSetTwo.setVisibility(GONE);
                                final CheckBox additionalGroupOne = findViewById(R.id
                                        .additional_group_one);
                                final CheckBox additionalGroupTwo = findViewById(R.id
                                        .additional_group_two);
                                final CheckBox additionalGroupThree = findViewById(R.id
                                        .additional_group_three);
                                final CheckBox additionalGroupSeven = findViewById(R.id
                                        .additional_group_seven);
                                additionalGroupSeven.setVisibility(VISIBLE);
                                additionalGroupOne.setText(R.string.necronomicon_defeated);
                                additionalGroupTwo.setText(R.string.armitage_defeated);
                                additionalGroupThree.setText(R.string.rice_defeated);
                                additionalGroupSeven.setText(R.string.morgan_defeated);
                                class KidnappedCheckedChangeListener implements CompoundButton
                                        .OnCheckedChangeListener {
                                    @Override
                                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                        final SpannableStringBuilder defeatedStringBuilder = new
                                                SpannableStringBuilder();
                                        if (additionalGroupOne.isChecked()) {
                                            defeatedStringBuilder.append(getString(R.string
                                                    .remove_necronomicon));
                                        }
                                        if (additionalGroupTwo.isChecked()) {
                                            defeatedStringBuilder.append(getString(R.string.remove_armitage));
                                        }
                                        if (additionalGroupThree.isChecked()) {
                                            defeatedStringBuilder.append(getString(R.string.remove_rice));
                                        }
                                        if (additionalGroupSeven.isChecked()) {
                                            defeatedStringBuilder.append(getString(R.string.remove_morgan));
                                        }
                                        if (additionalGroupOne.isChecked() || additionalGroupTwo.isChecked() ||
                                                additionalGroupThree.isChecked() || additionalGroupSeven
                                                .isChecked()) {
                                            String defeatedString = defeatedStringBuilder.toString();
                                            TextView additionalResolution = findViewById(R.id
                                                    .resolution_text_additional);
                                            additionalResolution.setTypeface(additionalResolution.getTypeface(),
                                                    Typeface.BOLD);
                                            additionalResolution.setText(defeatedString.trim());
                                            additionalResolution.setVisibility(VISIBLE);
                                        } else {
                                            TextView additionalResolution = findViewById(R.id
                                                    .resolution_text_additional);
                                            additionalResolution.setVisibility(GONE);
                                        }
                                    }
                                }
                                additionalGroupOne.setOnCheckedChangeListener(new
                                        KidnappedCheckedChangeListener());
                                additionalGroupTwo.setOnCheckedChangeListener(new
                                        KidnappedCheckedChangeListener());
                                additionalGroupThree.setOnCheckedChangeListener(new
                                        KidnappedCheckedChangeListener());
                                additionalGroupSeven.setOnCheckedChangeListener(new
                                        KidnappedCheckedChangeListener());
                                if (globalVariables.Necronomicon != 2) {
                                    additionalGroupOne.setVisibility(GONE);
                                }
                                if (globalVariables.HenryArmitage != 1) {
                                    additionalGroupTwo.setVisibility(GONE);
                                }
                                if (globalVariables.WarrenRice != 1) {
                                    additionalGroupThree.setVisibility(GONE);
                                }
                                if (globalVariables.FrancisMorgan != 1) {
                                    additionalGroupSeven.setVisibility(GONE);
                                }
                            }
                            switch (globalVariables.ScenarioResolution) {
                                case 0:
                                    resolutionTextView.setText(R.string.essex_no_resolution);
                                    break;
                                case 1:
                                    if (defeated) {
                                        resolutionTextView.setText(R.string.essex_resolution_one_defeated);
                                    } else {
                                        resolutionTextView.setText(R.string.essex_resolution_one);
                                    }
                                    break;
                                case 2:
                                    resolutionTextView.setText(R.string.essex_resolution_two);
                                    break;
                            }
                            break;
                        // Blood on the Altar
                        case 6:
                            defeated = false;
                            for (int a = 0; a < globalVariables.Investigators.size(); a++) {
                                if (globalVariables.Investigators.get(a).TempStatus > 1) {
                                    defeated = true;
                                }
                            }
                            TextView resolution = findViewById(R.id.resolution_text_additional);
                            if (defeated && globalVariables.Necronomicon == 2 && globalVariables
                                    .ScenarioResolution
                                    != 2) {
                                additional.setVisibility(VISIBLE);
                                additional.setText(R.string.necronomicon_defeated);
                                resolution.setVisibility(VISIBLE);
                                resolution.setText(getString(R.string.remove_necronomicon).trim());
                            } else {
                                additional.setVisibility(GONE);
                            }
                            switch (globalVariables.ScenarioResolution) {
                                case 0:
                                    resolutionTextView.setText(R.string.blood_no_resolution);
                                    break;
                                case 1:
                                    resolutionTextView.setText(R.string.blood_resolution_one);
                                    break;
                                case 2:
                                    resolutionTextView.setText(R.string.blood_resolution_two);
                                    break;
                                case 3:
                                    resolutionTextView.setText(R.string.blood_resolution_three);
                                    break;
                            }
                            break;
                        // Undimensioned and Unseen
                        case 8:
                            switch (globalVariables.ScenarioResolution) {
                                case 0:
                                    resolutionTextView.setText(R.string.undimensioned_no_resolution);
                                    break;
                                case 1:
                                    resolutionTextView.setText(R.string.undimensioned_resolution_one);
                                    break;
                                case 2:
                                    resolutionTextView.setText(R.string.undimensioned_resolution_two);
                                    break;
                            }
                            if (globalVariables.FrancisMorgan == 3 || globalVariables.HenryArmitage == 3 ||
                                    globalVariables.WarrenRice == 3) {
                                resolutionTextViewAdditional.setVisibility(VISIBLE);
                                resolutionTextViewAdditional.setText(R.string.undimensioned_resolution_powder);
                            }
                            break;
                        // Where Doom Awaits
                        case 9:
                            switch (globalVariables.ScenarioResolution) {
                                case 0:
                                    resolutionTextView.setText(R.string.doom_no_resolution);
                                    break;
                                case 1:
                                    resolutionTextView.setText(R.string.doom_resolution_one);
                                    break;
                                case 2:
                                    resolutionTextView.setText(R.string.doom_resolution_two);
                                    break;
                            }
                            break;
                        // Lost in Time and Space
                        case 10:
                            defeated = false;
                            for (int a = 0; a < globalVariables.Investigators.size(); a++) {
                                if (globalVariables.Investigators.get(a).TempStatus > 1) {
                                    defeated = true;
                                }
                            }
                            switch (globalVariables.ScenarioResolution) {
                                case 0:
                                    resolutionTextView.setText(R.string.lost_no_resolution);
                                    additional.setVisibility(VISIBLE);
                                    additional.setText(R.string.act_one_two_three);
                                    additional.setOnCheckedChangeListener(new CompoundButton
                                            .OnCheckedChangeListener() {
                                        @Override
                                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                            if (compoundButton.isChecked()) {
                                                additionalTwo.setChecked(false);
                                                globalVariables.ScenarioResolution = 4;
                                                resolutionTextView.setText(R.string.lost_resolution_four);
                                            } else {
                                                globalVariables.ScenarioResolution = 0;
                                                resolutionTextView.setText(R.string.lost_no_resolution);
                                            }
                                        }
                                    });
                                    additionalTwo.setVisibility(VISIBLE);
                                    additionalTwo.setText(R.string.act_four);
                                    additionalTwo.setOnCheckedChangeListener(new CompoundButton
                                            .OnCheckedChangeListener() {
                                        @Override
                                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                            if (compoundButton.isChecked()) {
                                                additional.setChecked(false);
                                                globalVariables.ScenarioResolution = 2;
                                                resolutionTextView.setText(R.string.lost_resolution_two);
                                            } else {
                                                globalVariables.ScenarioResolution = 0;
                                                resolutionTextView.setText(R.string.lost_no_resolution);
                                            }
                                        }
                                    });
                                    break;
                                case 1:
                                    if (defeated) {
                                        resolutionTextView.setText(R.string.lost_resolution_one_defeated);
                                    } else {
                                        resolutionTextView.setText(R.string.lost_resolution_one);
                                    }
                                    break;
                                case 3:
                                    if (defeated) {
                                        resolutionTextView.setText(R.string.lost_resolution_three_defeated);
                                    } else {
                                        resolutionTextView.setText(R.string.lost_resolution_three);
                                    }
                                    break;
                            }
                            break;
                    }
                    break;
                // Path to Carcosa
                case 3:
                    switch (globalVariables.CurrentScenario) {
                        // Curtain Call
                        case 1:
                            switch (globalVariables.ScenarioResolution) {
                                case 0:
                                    resolutionTextView.setText(R.string.curtain_no_resolution);
                                    break;
                                case 1:
                                    resolutionTextView.setText(R.string.curtain_resolution_one);
                                    break;
                                case 2:
                                    resolutionTextView.setText(R.string.curtain_resolution_two);
                                    break;
                            }
                            break;
                        // Last King
                        case 2:
                            boolean resigned = false;
                            for (int a = 0; a < globalVariables.Investigators.size(); a++) {
                                if (globalVariables.Investigators.get(a).TempStatus == 1) {
                                    resigned = true;
                                }
                            }
                            switch (globalVariables.ScenarioResolution) {
                                case 0:
                                    if (resigned) {
                                        resolutionTextView.setText(R.string.king_resolution_one);
                                    } else {
                                        resolutionTextView.setText(R.string.king_resolution_two);
                                    }
                                    break;
                                case 3:
                                    resolutionTextView.setText(R.string.king_resolution_three);
                                    break;
                            }
                            break;
                        case 4:
                            switch (globalVariables.ScenarioResolution) {
                                case 0:
                                    resolutionTextView.setText(R.string.echoes_no_resolution);
                                    break;
                                case 1:
                                    resolutionTextView.setText(R.string.echoes_resolution_one);
                                    break;
                                case 2:
                                    resolutionTextView.setText(R.string.echoes_resolution_two);
                                    break;
                                case 3:
                                    resolutionTextView.setText(R.string.echoes_resolution_three);
                                    break;
                                case 4:
                                    resolutionTextView.setText(R.string.echoes_resolution_four);
                                    break;
                            }
                            break;
                        // Unspeakable Oath
                        case 5:
                            boolean defeated = false;
                            for (int a = 0; a < globalVariables.Investigators.size(); a++) {
                                if (globalVariables.Investigators.get(a).TempStatus > 1) {
                                    defeated = true;
                                }
                            }
                            switch (globalVariables.ScenarioResolution) {
                                case 0:
                                case 1:
                                    if (defeated) {
                                        resolutionTextView.setText(R.string.unspeakable_resolution_one_defeated);
                                    } else {
                                        resolutionTextView.setText(R.string.unspeakable_resolution_one);
                                    }
                                    if (globalVariables.Onyx == 1) {
                                        resolutionTextViewAdditional.setVisibility(VISIBLE);
                                        resolutionTextViewAdditional.setText(R.string.unspeakable_resolution_onyx);
                                    } else {
                                        resolutionTextViewAdditional.setVisibility(GONE);
                                    }
                                    break;
                                case 2:
                                    resolutionTextViewAdditional.setVisibility(GONE);
                                    if (defeated) {
                                        resolutionTextView.setText(R.string.unspeakable_resolution_two_defeated);
                                    } else {
                                        resolutionTextView.setText(R.string.unspeakable_resolution_two);
                                    }
                                    break;
                                case 3:
                                    resolutionTextViewAdditional.setVisibility(GONE);
                                    if (defeated) {
                                        resolutionTextView.setText(R.string.unspeakable_resolution_three_defeated);
                                    } else {
                                        resolutionTextView.setText(R.string.unspeakable_resolution_three);
                                    }
                                    break;
                            }
                            break;
                        // Phantom of Truth
                        case 7:
                            switch (globalVariables.ScenarioResolution) {
                                case 0:
                                    resolutionTextView.setText(R.string.phantom_no_resolution);
                                    break;
                                case 1:
                                    resolutionTextView.setText(R.string.phantom_resolution_one);
                                    break;
                                case 2:
                                    resolutionTextView.setText(R.string.phantom_resolution_two);
                                    break;
                                case 3:
                                    resolutionTextView.setText(R.string.phantom_resolution_three);
                                    break;
                            }
                            break;
                        case 8:
                            switch (globalVariables.ScenarioResolution) {
                                case 0:
                                    resolutionTextView.setText(R.string.pallid_no_resolution);
                                    break;
                                case 1:
                                    resolutionTextView.setText(R.string.pallid_resolution_one);
                                    break;
                                case 2:
                                    resolutionTextView.setText(R.string.pallid_resolution_two);
                                    break;
                            }
                            // Set up checkboxes to select who read Act II
                            if (globalVariables.ScenarioResolution == 0) {
                                selectInvestigatorHeading.setVisibility(VISIBLE);
                                selectInvestigatorHeading.setText(R.string.select_read_act);
                                selectInvestigator.setVisibility(VISIBLE);
                                String[] investigatorNames = getResources().getStringArray(R.array.investigators);
                                switch (globalVariables.Investigators.size()) {
                                    case 4:
                                        selectInvestigatorFour.setVisibility(VISIBLE);
                                        selectInvestigatorFour.setText(investigatorNames[globalVariables
                                                .Investigators.get(3)
                                                .Name]);
                                    case 3:
                                        selectInvestigatorThree.setVisibility(VISIBLE);
                                        selectInvestigatorThree.setText(investigatorNames[globalVariables
                                                .Investigators.get
                                                        (2).Name]);
                                    case 2:
                                        selectInvestigatorTwo.setVisibility(VISIBLE);
                                        selectInvestigatorTwo.setText(investigatorNames[globalVariables
                                                .Investigators.get(1)
                                                .Name]);
                                    case 1:
                                        selectInvestigatorOne.setVisibility(VISIBLE);
                                        selectInvestigatorOne.setText(investigatorNames[globalVariables
                                                .Investigators.get(0)
                                                .Name]);
                                        break;
                                }
                            } else {
                                selectInvestigatorHeading.setVisibility(GONE);
                                selectInvestigator.setVisibility(GONE);
                            }
                            break;
                        case 9:
                            switch (globalVariables.ScenarioResolution) {
                                case 0:
                                    resolutionTextView.setText(R.string.black_stars_resolution_three);
                                    break;
                                case 1:
                                    resolutionTextView.setText(R.string.black_stars_resolution_one);
                                    break;
                                case 2:
                                    resolutionTextView.setText(R.string.black_stars_resolution_two);
                                    break;
                                case 3:
                                    resolutionTextView.setText(R.string.black_stars_resolution_three);
                                    break;
                            }
                            break;
                        case 10:
                            switch (globalVariables.ScenarioResolution) {
                                case 0:
                                    if (globalVariables.Conviction >= globalVariables.Doubt) {
                                        resolutionTextView.setText(R.string.dim_resolution_four);
                                    } else if (globalVariables.Doubt > globalVariables.Conviction) {
                                        resolutionTextView.setText(R.string.dim_resolution_five);
                                    }
                                    selectInvestigatorHeading.setVisibility(GONE);
                                    selectInvestigator.setVisibility(GONE);
                                    break;
                                case 1:
                                    resolutionTextView.setText(R.string.dim_resolution_one);
                                    selectInvestigatorHeading.setVisibility(VISIBLE);
                                    selectInvestigator.setVisibility(VISIBLE);
                                    break;
                                case 2:
                                    resolutionTextView.setText(R.string.dim_resolution_two);
                                    selectInvestigatorHeading.setVisibility(VISIBLE);
                                    selectInvestigator.setVisibility(VISIBLE);
                                    break;
                                case 3:
                                    resolutionTextView.setText(R.string.dim_resolution_three);
                                    selectInvestigatorHeading.setVisibility(VISIBLE);
                                    selectInvestigator.setVisibility(VISIBLE);
                                    break;
                            }
                            break;
                    }
                    break;
                // The Forgotten Age
                case 4:
                    switch (globalVariables.CurrentScenario) {
                        // Untamed Wilds
                        case 1:
                            switch (globalVariables.ScenarioResolution) {
                                case 0:
                                    resolutionTextView.setText(R.string.untamed_no_resolution);
                                    selectInvestigator.setVisibility(VISIBLE);
                                    selectInvestigatorOne.setVisibility(VISIBLE);
                                    selectInvestigatorTwo.setVisibility(VISIBLE);
                                    selectInvestigatorFive.setVisibility(VISIBLE);
                                    selectInvestigatorRight.setVisibility(GONE);
                                    resolutionTextViewAdditional.setText(R.string.untamed_add_alejandro);
                                    selectInvestigatorFive.setOnCheckedChangeListener(new CompoundButton
                                            .OnCheckedChangeListener() {
                                        @Override
                                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                            if (compoundButton.isChecked()) {
                                                selectInvestigatorTwo.setChecked(false);
                                                selectInvestigatorOne.setChecked(false);
                                                resolutionTextViewAdditional.setVisibility(GONE);
                                            }
                                        }
                                    });
                                    selectInvestigatorTwo.setOnCheckedChangeListener(new CompoundButton
                                            .OnCheckedChangeListener() {
                                        @Override
                                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                            if (compoundButton.isChecked()) {
                                                selectInvestigatorFive.setChecked(false);
                                                selectInvestigatorOne.setChecked(false);
                                                resolutionTextViewAdditional.setVisibility(VISIBLE);
                                            }
                                        }
                                    });
                                    selectInvestigatorOne.setOnCheckedChangeListener(new CompoundButton
                                            .OnCheckedChangeListener() {
                                        @Override
                                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                            if (compoundButton.isChecked()) {
                                                selectInvestigatorFive.setChecked(false);
                                                selectInvestigatorTwo.setChecked(false);
                                                resolutionTextViewAdditional.setVisibility(VISIBLE);
                                            }
                                        }
                                    });
                                    selectInvestigatorOne.setText(R.string.untamed_ichtaca_one);
                                    selectInvestigatorTwo.setText(R.string.untamed_ichtaca_two);
                                    selectInvestigatorFive.setText(R.string.untamed_ichtaca_three);
                                    if (globalVariables.Ichtaca == 1) {
                                        selectInvestigatorFive.setChecked(true);
                                    } else if (globalVariables.Ichtaca == 2) {
                                        selectInvestigatorTwo.setChecked(true);
                                    }
                                    break;
                                case 1:
                                    resolutionTextView.setText(R.string.untamed_resolution_one);
                                    selectInvestigator.setVisibility(GONE);
                                    break;
                                case 2:
                                    resolutionTextView.setText(R.string.untamed_resolution_two);
                                    selectInvestigator.setVisibility(GONE);
                                    break;
                            }
                            break;
                        // Doom of Eztli
                        case 6:
                            boolean defeated = false;
                            for (int a = 0; a < globalVariables.Investigators.size(); a++) {
                                if (globalVariables.Investigators.get(a).TempStatus > 1) {
                                    defeated = true;
                                }
                            }
                            boolean resolutionThree = false;
                            switch (globalVariables.ScenarioResolution) {
                                case 0:
                                    if (defeated) {
                                        if (globalVariables.YigsFury >= 4) {
                                            resolutionTextView.setText(R.string.eztli_resolution_two);
                                        } else {
                                            resolutionTextView.setText(R.string.eztli_resolution_three_defeated_two);
                                            resolutionThree = true;
                                        }
                                    } else {
                                        resolutionTextView.setText(R.string.eztli_resolution_three);
                                        resolutionThree = true;
                                    }
                                    break;
                                case 1:
                                    if (defeated) {
                                        if (globalVariables.YigsFury >= 4) {
                                            resolutionTextView.setText(R.string.eztli_resolution_one_defeated_one);
                                        } else {
                                            resolutionTextView.setText(R.string.eztli_resolution_one_defeated_two);
                                        }
                                    } else {
                                        resolutionTextView.setText(R.string.eztli_resolution_one);
                                    }
                                    break;
                                case 3:
                                    if (defeated) {
                                        if (globalVariables.YigsFury >= 4) {
                                            resolutionTextView.setText(R.string.eztli_resolution_three_defeated_one);
                                        } else {
                                            resolutionTextView.setText(R.string.eztli_resolution_three_defeated_two);
                                        }
                                    } else {
                                        resolutionTextView.setText(R.string.eztli_resolution_three);
                                    }
                                    resolutionThree = true;
                                    break;
                            }
                            if (resolutionThree) {
                                resolutionOptions.setVisibility(VISIBLE);
                                resolutionOptionOne.setText(R.string.eztli_resolution_three_option_one);
                                resolutionOptionTwo.setText(R.string.eztli_resolution_three_option_two);
                                resolutionOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                                        resolutionTextViewAdditional.setVisibility(VISIBLE);
                                        switch (checkedId) {
                                            case R.id.resolution_option_one:
                                                resolutionTextViewAdditional.setText(R.string.eztli_resolution_four);
                                                break;
                                            case R.id.resolution_option_two:
                                                resolutionTextViewAdditional.setText(R.string.eztli_resolution_five);
                                                break;
                                        }
                                    }
                                });
                            } else {
                                resolutionOptions.setVisibility(GONE);
                                resolutionTextViewAdditional.setVisibility(GONE);
                            }
                            break;
                        // Threads of Fate
                        case 8:
                            resolutionTextView.setText(R.string.threads_resolution);
                            resolutionTextViewAdditional.setVisibility(VISIBLE);
                            StringBuilder threadsResolution = new StringBuilder();
                            if (additional.isChecked()) {
                                threadsResolution.append(getString(R.string.threads_resolution_one_one));
                            } else {
                                threadsResolution.append(getString(R.string.threads_resolution_one_two));
                            }
                            if (globalVariables.IchtacasTale != 4) {
                                if (additionalTwo.isChecked()) {
                                    threadsResolution.append(getString(R.string.threads_resolution_two_one));
                                } else {
                                    threadsResolution.append(getString(R.string.threads_resolution_two_two));
                                }
                                if (additionalThree.isChecked()) {
                                    threadsResolution.append(getString(R.string.threads_resolution_three_one));
                                }
                            }
                            String threads = threadsResolution.toString().trim();
                            resolutionTextViewAdditional.setText(threads);
                            break;
                        // The Boundary Beyond
                        case 10:
                            switch (globalVariables.ScenarioResolution) {
                                case 0:
                                    resolutionTextView.setText(R.string.boundary_no_resolution);
                                    break;
                                case 1:
                                    resolutionTextView.setText(R.string.boundary_resolution_one);
                                    break;
                                case 2:
                                    resolutionTextView.setText(R.string.boundary_resolution_two);
                                    break;
                            }
                            if (globalVariables.ScenarioResolution != 1) {
                                additional.setVisibility(VISIBLE);
                                additional.setText(R.string.act_two_finished);
                                additional.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        if (isChecked) {
                                            resolutionTextViewAdditional.setVisibility(VISIBLE);
                                            resolutionTextViewAdditional.setText(R.string
                                                    .boundary_resolution_additional);
                                        } else {
                                            resolutionTextViewAdditional.setVisibility(GONE);
                                        }
                                    }
                                });
                            } else {
                                additional.setVisibility(GONE);
                                resolutionTextViewAdditional.setVisibility(GONE);
                            }
                            break;
                        // Heart of the Elders A
                        case 18:
                            switch (globalVariables.ScenarioResolution) {
                                case 0:
                                    additionalCounterLayout.setVisibility(VISIBLE);
                                    additionalCounter.setText(R.string.heart_pillar_tokens);
                                    textBoxLayout.setVisibility(GONE);
                                    resolutionTextView.setText(R.string.heart_one_no_resolution);
                                    resolutionTextViewAdditional.setVisibility(GONE);
                                    break;
                                case 1:
                                    additionalCounterLayout.setVisibility(GONE);
                                    textBoxLayout.setVisibility(VISIBLE);
                                    textBoxHeading.setText(R.string.vengeance_enemies);
                                    resolutionTextView.setText(R.string.heart_one_resolution_one);
                                    resolutionTextViewAdditional.setVisibility(VISIBLE);
                                    resolutionTextViewAdditional.setText(R.string.heart_one_resolution_one_additional);
                                    break;
                            }
                            break;
                            // Heart of the Elders B
                       /* case 19:
                            switch(globalVariables.ScenarioResolution){
                                case 0:
                                    if(globalVariables.MissingAlejandro == 2){
                                        resolutionTextView.setText(R.string.heart_two_no_resolution_one);
                                    } else if (globalVariables.MissingAlejandro == 1){
                                        resolutionTextView.setText(R.string.heart_two_no_resolution_two);
                                    }
                                    break;
                                case 1:
                                    resolutionTextView.setText(R.string.heart_two_resolution_one);
                                    break;
                                case 2:
                                    resolutionTextView.setText(R.string.heart_two_resolution_two);
                                    break;
                            }
                            break;*/
                    }
                    break;
            }
            if (globalVariables.CurrentScenario > 100) {
                switch (globalVariables.CurrentScenario) {
                    // Curse of the Rougarou
                    case 101:
                        switch (globalVariables.ScenarioResolution) {
                            case 0:
                                resolutionTextView.setText(R.string.rougarou_no_resolution);
                                break;
                            case 1:
                                resolutionTextView.setText(R.string.rougarou_resolution_one);
                                break;
                            case 2:
                                resolutionTextView.setText(R.string.rougarou_resolution_two);
                                break;
                            case 3:
                                resolutionTextView.setText(R.string.rougarou_resolution_three);
                                break;
                        }
                        break;
                    // Carnevale of Horrors
                    case 102:
                        switch (globalVariables.ScenarioResolution) {
                            case 0:
                                resolutionTextView.setText(R.string.carnevale_no_resolution);
                                break;
                            case 1:
                                resolutionTextView.setText(R.string.carnevale_resolution_one);
                                break;
                            case 2:
                                resolutionTextView.setText(R.string.carnevale_resolution_two);
                                break;
                        }
                        break;
                        // Labyrinths of Lunacy
                    case 103:
                    case 104:
                    case 105:
                        switch(globalVariables.ScenarioResolution){
                            case 0:
                            case 1:
                                resolutionTextView.setText(R.string.labyrinths_resolution_one);
                                break;
                            case 2:
                                resolutionTextView.setText(R.string.labyrinths_resolution_two);
                                break;
                            case 3:
                                resolutionTextView.setText(R.string.labyrinths_resolution_three);
                                break;
                            case 4:
                                resolutionTextView.setText(R.string.labyrinths_resolution_four);
                                break;
                        }
                }
            }
        }
    }

    /*
        Dialog to finish the scenario
      */
    public static class FinishScenarioDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Get the layout inflater and inflate the view
            View v = View.inflate(getActivity(), R.layout.c_dialog_finish_scenario, null);

            Typeface teutonic = Typeface.createFromAsset(getActivity().getAssets(), "fonts/teutonic.ttf");
            Typeface arnopro = Typeface.createFromAsset(getActivity().getAssets(), "fonts/arnopro.otf");
            Typeface arnoprobold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/arnoprobold.otf");
            TextView title = v.findViewById(R.id.current_scenario_name);
            TextView confirm = v.findViewById(R.id.confirm_finish_scenario);
            Button cancelButton = v.findViewById(R.id.cancel_button);
            Button okayButton = v.findViewById(R.id.okay_button);
            title.setTypeface(teutonic);
            confirm.setTypeface(arnoprobold);
            cancelButton.setTypeface(teutonic);
            okayButton.setTypeface(teutonic);

            // Set title
            globalVariables.setTitle(title);

            // Set resolution number and victory display
            TextView resolution = v.findViewById(R.id.current_resolution);
            TextView victory = v.findViewById(R.id.current_victory_display);
            resolution.setTypeface(arnopro);
            victory.setTypeface(arnopro);
            switch (globalVariables.ScenarioResolution) {
                case 0:
                    resolution.setText(R.string.no_resolution);
                    break;
                case 1:
                    resolution.setText(R.string.resolution_one);
                    break;
                case 2:
                    resolution.setText(R.string.resolution_two);
                    break;
                case 3:
                    resolution.setText(R.string.resolution_three);
                    break;
                case 4:
                    resolution.setText(R.string.resolution_four);
                    break;
            }
            String victoryText = getString(R.string.victory_display) + " " + Integer.toString(globalVariables
                    .VictoryDisplay);
            victory.setText(victoryText);

            /*
                Show the right views for the number of investigators and set the right font to the name
             */
            LinearLayout investigatorOne = v.findViewById(R.id.investigator_one);
            LinearLayout investigatorTwo = v.findViewById(R.id.investigator_two);
            LinearLayout investigatorThree = v.findViewById(R.id.investigator_three);
            LinearLayout investigatorFour = v.findViewById(R.id.investigator_four);
            TextView investigatorOneName = v.findViewById(R.id.investigator_one_name);
            TextView investigatorTwoName = v.findViewById(R.id.investigator_two_name);
            TextView investigatorThreeName = v.findViewById(R.id.investigator_three_name);
            TextView investigatorFourName = v.findViewById(R.id.investigator_four_name);
            TextView investigatorOneStatus = v.findViewById(R.id.investigator_one_status);
            TextView investigatorTwoStatus = v.findViewById(R.id.investigator_two_status);
            TextView investigatorThreeStatus = v.findViewById(R.id.investigator_three_status);
            TextView investigatorFourStatus = v.findViewById(R.id.investigator_four_status);
            String[] investigatorNames = getResources().getStringArray(R.array.investigators);
            // For each investigator, set it visible or not, apply the right name to it, set the right typeface and
            // set a listener to the link
            switch (globalVariables.Investigators.size()) {
                case 4:
                    investigatorFour.setVisibility(VISIBLE);
                    investigatorFourName.setText(investigatorNames[globalVariables.Investigators.get(3).Name]);
                    investigatorFourName.setTypeface(arnoprobold);
                    investigatorFourStatus.setTypeface(arnopro);
                    switch (globalVariables.Investigators.get(3).TempStatus) {
                        case 0:
                            investigatorFourStatus.setText(R.string.not_defeated);
                            break;
                        case 1:
                            investigatorFourStatus.setText(R.string.resigned);
                            break;
                        case 2:
                            investigatorFourStatus.setText(R.string.defeated_damage);
                            break;
                        case 3:
                            investigatorFourStatus.setText(R.string.defeated_horror);
                            break;
                    }
                case 3:
                    investigatorThree.setVisibility(VISIBLE);
                    investigatorThreeName.setText(investigatorNames[globalVariables.Investigators.get(2).Name]);
                    investigatorThreeName.setTypeface(arnoprobold);
                    investigatorThreeStatus.setTypeface(arnopro);
                    switch (globalVariables.Investigators.get(2).TempStatus) {
                        case 0:
                            investigatorThreeStatus.setText(R.string.not_defeated);
                            break;
                        case 1:
                            investigatorThreeStatus.setText(R.string.resigned);
                            break;
                        case 2:
                            investigatorThreeStatus.setText(R.string.defeated_damage);
                            break;
                        case 3:
                            investigatorThreeStatus.setText(R.string.defeated_horror);
                            break;
                    }
                case 2:
                    investigatorTwo.setVisibility(VISIBLE);
                    investigatorTwoName.setText(investigatorNames[globalVariables.Investigators.get(1).Name]);
                    investigatorTwoName.setTypeface(arnoprobold);
                    investigatorTwoStatus.setTypeface(arnopro);
                    switch (globalVariables.Investigators.get(1).TempStatus) {
                        case 0:
                            investigatorTwoStatus.setText(R.string.not_defeated);
                            break;
                        case 1:
                            investigatorTwoStatus.setText(R.string.resigned);
                            break;
                        case 2:
                            investigatorTwoStatus.setText(R.string.defeated_damage);
                            break;
                        case 3:
                            investigatorTwoStatus.setText(R.string.defeated_horror);
                            break;
                    }
                case 1:
                    investigatorOne.setVisibility(VISIBLE);
                    investigatorOneName.setText(investigatorNames[globalVariables.Investigators.get(0).Name]);
                    investigatorOneName.setTypeface(arnoprobold);
                    investigatorOneStatus.setTypeface(arnopro);
                    switch (globalVariables.Investigators.get(0).TempStatus) {
                        case 0:
                            investigatorOneStatus.setText(R.string.not_defeated);
                            break;
                        case 1:
                            investigatorOneStatus.setText(R.string.resigned);
                            break;
                        case 2:
                            investigatorOneStatus.setText(R.string.defeated_damage);
                            break;
                        case 3:
                            investigatorOneStatus.setText(R.string.defeated_horror);
                            break;
                    }
            }

            okayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Apply scenario resolutions
                    scenarioResolutions(getActivity());

                    // Go to the right next screen (investigators, interlude, campaign finish)
                    Intent intent = new Intent(getActivity(), ScenarioMainActivity.class);
                    // If all investigators are dead, go to campaign finish screen
                    int dead = 0;
                    for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                        if (globalVariables.Investigators.get(i).Status == 2) {
                            dead++;
                        }
                    }
                    if (dead == globalVariables.Investigators.size()) {
                        String sharedPrefs = getActivity().getResources().getString(R.string.shared_prefs);
                        String dunwichOwnedString = getActivity().getResources().getString(R.string
                                .dunwich_campaign);
                        String carcosaOwnedString = getActivity().getResources().getString(R.string
                                .carcosa_campaign);
                        SharedPreferences settings = getActivity().getSharedPreferences(sharedPrefs, 0);
                        boolean dunwichOwned = settings.getBoolean(dunwichOwnedString, true);
                        boolean carcosaOwned = settings.getBoolean(carcosaOwnedString, true);
                        int investigators = 5;
                        for (int a = 1; a <= 5; a++) {
                            if (globalVariables.InvestigatorsInUse[a] == 1) {
                                investigators--;
                            }
                        }
                        if (dunwichOwned) {
                            investigators += 5;
                            for (int a = 6; a <= 10; a++) {
                                if (globalVariables.InvestigatorsInUse[a] == 1) {
                                    investigators--;
                                }
                            }
                        }
                        if (carcosaOwned) {
                            investigators += 6;
                            for (int a = 11; a <= 16; a++) {
                                if (globalVariables.InvestigatorsInUse[a] == 1) {
                                    investigators--;
                                }
                            }
                        }
                        if (investigators == 0) {
                            intent = new Intent(getActivity(), ScenarioMainActivity.class);
                            startActivity(intent);
                            dismiss();
                        }
                    }
                    // Otherwise go to the relevant screen for the scenario (default is next scenario)
                    switch (globalVariables.CurrentCampaign) {
                        case 1:
                            switch (globalVariables.CurrentScenario) {
                                case 4:
                                    intent = new Intent(getActivity(), CampaignFinishedActivity.class);
                                    break;
                            }
                            break;
                        case 2:
                            switch (globalVariables.CurrentScenario) {
                                case 3:
                                    intent = new Intent(getActivity(), ScenarioInterludeActivity.class);
                                    break;
                                case 7:
                                    intent = new Intent(getActivity(), ScenarioInterludeActivity.class);
                                    break;
                                case 11:
                                    intent = new Intent(getActivity(), ScenarioInterludeActivity.class);
                                    break;
                                case 12:
                                    intent = new Intent(getActivity(), CampaignFinishedActivity.class);
                                    break;
                            }
                            break;
                        case 3:
                            switch (globalVariables.CurrentScenario) {
                                case 3:
                                    intent = new Intent(getActivity(), ScenarioInterludeActivity.class);
                                    break;
                                case 6:
                                    intent = new Intent(getActivity(), ScenarioInterludeActivity.class);
                                    break;
                                case 11:
                                    intent = new Intent(getActivity(), ScenarioInterludeActivity.class);
                                    break;
                                case 12:
                                    intent = new Intent(getActivity(), CampaignFinishedActivity.class);
                                    break;
                            }
                            break;
                        case 4:
                            switch (globalVariables.CurrentScenario) {
                                case 2:
                                    intent = new Intent(getActivity(), ScenarioInterludeActivity.class);
                                    break;
                                case 7:
                                    intent = new Intent(getActivity(), ScenarioInterludeActivity.class);
                                    break;
                                case 9:
                                    intent = new Intent(getActivity(), ChooseSuppliesActivity.class);
                                    break;
                                case 11:
                                    intent = new Intent(getActivity(), ScenarioInterludeActivity.class);
                                    break;
                            }
                    }
                    startActivity(intent);
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

    /*
        Method on continuing to apply the scenario resolution
     */
    private static void scenarioResolutions(Activity activity) {
        // Get all the relevant checkboxes and views
        CheckBox additionalCheckbox = activity.findViewById(R.id.additional_checkbox_one);
        CheckBox additionalCheckboxTwo = activity.findViewById(R.id.additional_checkbox_two);
        CheckBox additionalCheckboxThree = activity.findViewById(R.id.additional_checkbox_three);
        CheckBox investigatorOne = activity.findViewById(R.id.select_investigator_one);
        CheckBox investigatorTwo = activity.findViewById(R.id.select_investigator_two);
        CheckBox investigatorThree = activity.findViewById(R.id.select_investigator_three);
        CheckBox investigatorFour = activity.findViewById(R.id.select_investigator_four);
        CheckBox investigatorFive = activity.findViewById(R.id.select_investigator_five);
        CheckBox additionalOne = activity.findViewById(R.id.additional_group_one);
        CheckBox additionalTwo = activity.findViewById(R.id.additional_group_two);
        CheckBox additionalThree = activity.findViewById(R.id.additional_group_three);
        CheckBox additionalFour = activity.findViewById(R.id.additional_group_four);
        CheckBox additionalFive = activity.findViewById(R.id.additional_group_five);
        CheckBox additionalSix = activity.findViewById(R.id.additional_group_six);
        CheckBox additionalSeven = activity.findViewById(R.id.additional_group_seven);
        TextView counterOne = activity.findViewById(R.id.investigator_counter_one_amount);
        TextView counterTwo = activity.findViewById(R.id.investigator_counter_two_amount);
        TextView counterThree = activity.findViewById(R.id.investigator_counter_three_amount);
        TextView counterFour = activity.findViewById(R.id.investigator_counter_four_amount);
        TextView additionalCounter = activity.findViewById(R.id.additional_amount);
        EditText textBox = activity.findViewById(R.id.text_box);
        RadioButton optionOne = activity.findViewById(R.id.resolution_option_one);
        RadioButton optionTwo = activity.findViewById(R.id.resolution_option_two);

        // Get current available XP for each investigator (necessary to work out totals later)
        int invOneXP = 0;
        int invTwoXP = 0;
        int invThreeXP = 0;
        int invFourXP = 0;
        switch (globalVariables.Investigators.size()) {
            case 4:
                invFourXP = globalVariables.Investigators.get(3).AvailableXP;
            case 3:
                invThreeXP = globalVariables.Investigators.get(2).AvailableXP;
            case 2:
                invTwoXP = globalVariables.Investigators.get(1).AvailableXP;
            case 1:
                invOneXP = globalVariables.Investigators.get(0).AvailableXP;
        }

        // Apply resolutions
        switch (globalVariables.CurrentCampaign) {

            // Night of the Zealot
            case 1:
                switch (globalVariables.CurrentScenario) {

                    // Night of the Zealot
                    case 1:
                        switch (globalVariables.ScenarioResolution) {
                            case 0:
                                globalVariables.HouseStanding = 1;
                                globalVariables.GhoulPriest = 1;
                                globalVariables.LitaChantler = 1;
                                for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                    Investigator current = globalVariables.Investigators.get(i);
                                    if (current.TempStatus == 0) {
                                        current.Damage += 1;
                                    }
                                    current.AvailableXP += (globalVariables.VictoryDisplay + 2);
                                }
                                break;
                            case 1:
                                globalVariables.HouseStanding = 0;
                                globalVariables.GhoulPriest = 0;
                                globalVariables.LitaChantler = 1;
                                if (investigatorOne.isChecked()) {
                                    globalVariables.Investigators.get(0).Horror += 1;
                                } else if (investigatorTwo.isChecked()) {
                                    globalVariables.Investigators.get(1).Horror += 1;
                                } else if (investigatorThree.isChecked()) {
                                    globalVariables.Investigators.get(2).Horror += 1;
                                } else if (investigatorFour.isChecked()) {
                                    globalVariables.Investigators.get(3).Horror += 1;
                                }
                                for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                    globalVariables.Investigators.get(i).AvailableXP += (globalVariables
                                            .VictoryDisplay + 2);
                                }
                                break;
                            case 2:
                                globalVariables.HouseStanding = 1;
                                globalVariables.GhoulPriest = 0;
                                globalVariables.LitaChantler = 0;
                                if (investigatorOne.isChecked()) {
                                    globalVariables.Investigators.get(0).AvailableXP += 1;
                                } else if (investigatorTwo.isChecked()) {
                                    globalVariables.Investigators.get(1).AvailableXP += 1;
                                } else if (investigatorThree.isChecked()) {
                                    globalVariables.Investigators.get(2).AvailableXP += 1;
                                } else if (investigatorFour.isChecked()) {
                                    globalVariables.Investigators.get(3).AvailableXP += 1;
                                }
                                for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                    globalVariables.Investigators.get(i).AvailableXP += (globalVariables
                                            .VictoryDisplay + 2);
                                }
                                break;
                            case 3:
                                globalVariables.HouseStanding = 1;
                                globalVariables.GhoulPriest = 1;
                                globalVariables.LitaChantler = 2;
                                for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                    if (globalVariables.Investigators.get(i).TempStatus != 1) {
                                        globalVariables.Investigators.get(i).Status = 2;
                                    }
                                }
                                break;
                        }
                        break;

                    // The Midnight Masks
                    case 2:
                        if (globalVariables.GhoulPriest == 1) {
                            if (additionalCheckbox.isChecked()) {
                                globalVariables.GhoulPriest = 0;
                            }
                        }
                        globalVariables.CultistsInterrogated = 0;
                        if (additionalOne.isChecked()) {
                            globalVariables.DrewInterrogated = 1;
                            globalVariables.CultistsInterrogated += 1;
                        } else {
                            globalVariables.DrewInterrogated = 0;
                        }
                        if (additionalTwo.isChecked()) {
                            globalVariables.PeterInterrogated = 1;
                            globalVariables.CultistsInterrogated += 1;
                        } else {
                            globalVariables.PeterInterrogated = 0;
                        }
                        if (additionalThree.isChecked()) {
                            globalVariables.RuthInterrogated = 1;
                            globalVariables.CultistsInterrogated += 1;
                        } else {
                            globalVariables.RuthInterrogated = 0;
                        }
                        if (additionalFour.isChecked()) {
                            globalVariables.HermanInterrogated = 1;
                            globalVariables.CultistsInterrogated += 1;
                        } else {
                            globalVariables.HermanInterrogated = 0;
                        }
                        if (additionalFive.isChecked()) {
                            globalVariables.VictoriaInterrogated = 1;
                            globalVariables.CultistsInterrogated += 1;
                        } else {
                            globalVariables.VictoriaInterrogated = 0;
                        }
                        if (additionalSix.isChecked()) {
                            globalVariables.MaskedInterrogated = 1;
                            globalVariables.CultistsInterrogated += 1;
                        } else {
                            globalVariables.MaskedInterrogated = 0;
                        }
                        switch (globalVariables.ScenarioResolution) {
                            case 0:
                            case 1:
                                globalVariables.PastMidnight = 0;
                                for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                    globalVariables.Investigators.get(i).AvailableXP += globalVariables
                                            .VictoryDisplay;
                                }
                                break;
                            case 2:
                                globalVariables.PastMidnight = 1;
                                for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                    globalVariables.Investigators.get(i).AvailableXP += globalVariables
                                            .VictoryDisplay;
                                }
                                break;
                        }
                        break;

                    // The Devourer Below
                    case 3:
                        switch (globalVariables.ScenarioResolution) {
                            case 0:
                                globalVariables.Umordhoth = 0;
                                for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                    globalVariables.Investigators.get(i).Status = 2;
                                }
                                break;
                            case 1:
                                globalVariables.Umordhoth = 1;
                                for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                    globalVariables.Investigators.get(i).Horror += 2;
                                    globalVariables.Investigators.get(i).AvailableXP += (globalVariables
                                            .VictoryDisplay + 5);
                                }
                                break;
                            case 2:
                                globalVariables.Umordhoth = 2;
                                for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                    globalVariables.Investigators.get(i).Damage += 2;
                                    globalVariables.Investigators.get(i).Horror += 2;
                                    globalVariables.Investigators.get(i).AvailableXP += (globalVariables
                                            .VictoryDisplay + 10);
                                }
                                break;
                            case 3:
                                globalVariables.Umordhoth = 3;
                                for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                    globalVariables.Investigators.get(i).Damage += 2;
                                    globalVariables.Investigators.get(i).Horror += 2;
                                    globalVariables.Investigators.get(i).AvailableXP += globalVariables
                                            .VictoryDisplay;
                                }
                                break;
                        }
                        globalVariables.NightCompleted = 1;
                        break;
                }
                break;
            // The Dunwich Legacy
            case 2:

                switch (globalVariables.CurrentScenario) {

                    // Extracurricular Activity
                    case 1:
                        switch (globalVariables.ScenarioResolution) {
                            case 0:
                                globalVariables.WarrenRice = 0;
                                globalVariables.Students = 0;
                                if (globalVariables.FirstScenario == 1) {
                                    globalVariables.InvestigatorsUnconscious = 0;
                                }
                                for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                    globalVariables.Investigators.get(i).AvailableXP += (globalVariables
                                            .VictoryDisplay + 1);
                                }
                                break;
                            case 1:
                                globalVariables.WarrenRice = 1;
                                globalVariables.Students = 0;
                                if (globalVariables.FirstScenario == 1) {
                                    globalVariables.InvestigatorsUnconscious = 0;
                                }
                                for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                    globalVariables.Investigators.get(i).AvailableXP += globalVariables
                                            .VictoryDisplay;
                                }
                                break;
                            case 2:
                                globalVariables.WarrenRice = 0;
                                globalVariables.Students = 1;
                                if (globalVariables.FirstScenario == 1) {
                                    globalVariables.InvestigatorsUnconscious = 0;
                                }
                                for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                    globalVariables.Investigators.get(i).AvailableXP += globalVariables
                                            .VictoryDisplay;
                                }
                                break;
                            case 3:
                                globalVariables.WarrenRice = 0;
                                globalVariables.Students = 2;
                                if (globalVariables.FirstScenario == 1) {
                                    globalVariables.InvestigatorsUnconscious = 0;
                                }
                                for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                    globalVariables.Investigators.get(i).AvailableXP += globalVariables
                                            .VictoryDisplay;
                                }
                                break;
                            case 4:
                                globalVariables.WarrenRice = 0;
                                globalVariables.Students = 0;
                                globalVariables.InvestigatorsUnconscious = 1;
                                for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                    globalVariables.Investigators.get(i).AvailableXP += (globalVariables
                                            .VictoryDisplay + 1);
                                }
                                break;
                        }
                        break;

                    // The House Always Wins
                    case 2:
                        if (additionalCheckbox.isChecked()) {
                            globalVariables.InvestigatorsCheated = 1;
                        } else {
                            globalVariables.InvestigatorsCheated = 0;
                        }
                        switch (globalVariables.ScenarioResolution) {
                            case 0:
                            case 1:
                                globalVariables.ObannionGang = 0;
                                globalVariables.FrancisMorgan = 0;
                                if (globalVariables.FirstScenario == 2) {
                                    globalVariables.InvestigatorsUnconscious = 0;
                                }
                                for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                    globalVariables.Investigators.get(i).AvailableXP += (globalVariables
                                            .VictoryDisplay + 1);
                                }
                                break;
                            case 2:
                                globalVariables.ObannionGang = 0;
                                globalVariables.FrancisMorgan = 1;
                                if (globalVariables.FirstScenario == 2) {
                                    globalVariables.InvestigatorsUnconscious = 0;
                                }
                                for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                    globalVariables.Investigators.get(i).AvailableXP += globalVariables
                                            .VictoryDisplay;
                                }
                                break;
                            case 3:
                                globalVariables.ObannionGang = 1;
                                globalVariables.FrancisMorgan = 0;
                                if (globalVariables.FirstScenario == 2) {
                                    globalVariables.InvestigatorsUnconscious = 0;
                                }
                                for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                    globalVariables.Investigators.get(i).AvailableXP += globalVariables
                                            .VictoryDisplay;
                                }
                                break;
                            case 4:
                                globalVariables.ObannionGang = 0;
                                globalVariables.FrancisMorgan = 0;
                                globalVariables.InvestigatorsUnconscious = 1;
                                for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                    globalVariables.Investigators.get(i).AvailableXP += (globalVariables
                                            .VictoryDisplay + 1);
                                    globalVariables.Investigators.get(i).Damage += 1;
                                }
                                break;
                        }
                        break;

                    // The Miskatonic Museum
                    case 4:
                        if (globalVariables.ScenarioResolution == 0) {
                            for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                if (globalVariables.Investigators.get(i).TempStatus == 0) {
                                    globalVariables.Investigators.get(i).Damage += 1;
                                }
                            }
                        }
                        globalVariables.Necronomicon = globalVariables.ScenarioResolution;
                        if (additionalCheckbox.isChecked()) {
                            globalVariables.AdamLynchHaroldWalsted = 1;
                        } else {
                            globalVariables.AdamLynchHaroldWalsted = 0;
                        }
                        for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                            globalVariables.Investigators.get(i).AvailableXP += globalVariables.VictoryDisplay;
                        }
                        break;

                    // The Essex County Express
                    case 5:
                        if (globalVariables.EngineCar == 1) {
                            if (investigatorOne.isChecked()) {
                                globalVariables.Investigators.get(0).Horror += 1;
                            } else if (investigatorTwo.isChecked()) {
                                globalVariables.Investigators.get(1).Horror += 1;
                            } else if (investigatorThree.isChecked()) {
                                globalVariables.Investigators.get(2).Horror += 1;
                            } else if (investigatorFour.isChecked()) {
                                globalVariables.Investigators.get(3).Horror += 1;
                            }
                        } else if (globalVariables.EngineCar == 2) {
                            if (investigatorOne.isChecked()) {
                                globalVariables.Investigators.get(0).Damage += 1;
                            } else if (investigatorTwo.isChecked()) {
                                globalVariables.Investigators.get(1).Damage += 1;
                            } else if (investigatorThree.isChecked()) {
                                globalVariables.Investigators.get(2).Damage += 1;
                            } else if (investigatorFour.isChecked()) {
                                globalVariables.Investigators.get(3).Damage += 1;
                            }
                        }
                        if (additionalOne.isChecked()) {
                            globalVariables.Necronomicon = 3;
                        }
                        if (additionalTwo.isChecked()) {
                            globalVariables.HenryArmitage = 0;
                        }
                        if (additionalThree.isChecked()) {
                            globalVariables.WarrenRice = 0;
                        }
                        if (additionalSeven.isChecked()) {
                            globalVariables.FrancisMorgan = 0;
                        }
                        switch (globalVariables.ScenarioResolution) {
                            case 0:
                                globalVariables.InvestigatorsDelayed = 1;
                                for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                    globalVariables.Investigators.get(i).AvailableXP += (globalVariables
                                            .VictoryDisplay + 1);
                                }
                                break;
                            case 1:
                                globalVariables.InvestigatorsDelayed = 0;
                                for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                    if (globalVariables.Investigators.get(i).TempStatus != 0) {
                                        globalVariables.Investigators.get(i).AvailableXP += (globalVariables
                                                .VictoryDisplay + 1);
                                    } else {
                                        globalVariables.Investigators.get(i).AvailableXP += (globalVariables
                                                .VictoryDisplay);
                                    }
                                }
                                break;
                            case 2:
                                globalVariables.InvestigatorsDelayed = 1;
                                for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                    globalVariables.Investigators.get(i).AvailableXP += (globalVariables
                                            .VictoryDisplay + 1);
                                    if (globalVariables.Investigators.get(i).TempStatus == 0) {
                                        globalVariables.Investigators.get(i).Horror += 1;
                                    }
                                }
                                break;
                        }
                        break;

                    // Blood on the Altar
                    case 6:
                        globalVariables.SilasBishop = globalVariables.ScenarioResolution;
                        for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                            globalVariables.Investigators.get(i).AvailableXP += (globalVariables.VictoryDisplay +
                                    2);
                        }
                        if (additionalCheckbox.isChecked()) {
                            globalVariables.Necronomicon = 3;
                        }
                        if (additionalOne.isChecked()) {
                            globalVariables.HenryArmitage = 2;
                        } else {
                            globalVariables.HenryArmitage = 3;
                        }
                        if (additionalTwo.isChecked()) {
                            globalVariables.WarrenRice = 2;
                        } else {
                            globalVariables.WarrenRice = 3;
                        }
                        if (additionalThree.isChecked()) {
                            globalVariables.FrancisMorgan = 2;
                        } else {
                            globalVariables.FrancisMorgan = 3;
                        }
                        if (additionalFour.isChecked()) {
                            globalVariables.ZebulonWhateley = 1;
                        } else {
                            globalVariables.ZebulonWhateley = 0;
                        }
                        if (additionalFive.isChecked()) {
                            globalVariables.EarlSawyer = 1;
                        } else {
                            globalVariables.EarlSawyer = 0;
                        }
                        if (additionalSix.isChecked()) {
                            globalVariables.AllySacrificed = 1;
                        } else {
                            globalVariables.AllySacrificed = 0;
                        }
                        break;

                    // Undimensioned and Unseen
                    case 8:
                        if (globalVariables.ScenarioResolution == 2) {
                            globalVariables.BroodsEscaped = 0;
                        }
                        for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                            globalVariables.Investigators.get(i).AvailableXP += globalVariables.VictoryDisplay;
                        }
                        break;

                    // Where Doom Awaits
                    case 9:
                        switch (globalVariables.ScenarioResolution) {
                            case 0:
                                globalVariables.InvestigatorsGate = 2;
                                globalVariables.DunwichCompleted = 1;
                                for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                    globalVariables.Investigators.get(i).Status = 2;
                                    globalVariables.Investigators.get(i).Horror = globalVariables
                                            .Investigators.get(i).Sanity;
                                }
                                break;
                            case 1:
                                globalVariables.InvestigatorsGate = 1;
                                for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                    globalVariables.Investigators.get(i).AvailableXP += globalVariables
                                            .VictoryDisplay;
                                }
                                break;
                            case 2:
                                globalVariables.InvestigatorsGate = 2;
                                globalVariables.DunwichCompleted = 1;
                                for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                    globalVariables.Investigators.get(i).Status = 2;
                                    globalVariables.Investigators.get(i).Horror = globalVariables
                                            .Investigators.get(i).Sanity;
                                }
                                break;
                        }
                        break;

                    // Lost in Time and Space
                    case 10:
                        for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                            if (globalVariables.Investigators.get(i).TempStatus > 1) {
                                globalVariables.Investigators.get(i).Status = 2;
                            }
                        }
                        switch (globalVariables.ScenarioResolution) {
                            case 1:
                                globalVariables.YogSothoth = 1;
                                for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                    globalVariables.Investigators.get(i).Damage += 2;
                                    globalVariables.Investigators.get(i).Horror += 2;
                                    globalVariables.Investigators.get(i).AvailableXP += (globalVariables
                                            .VictoryDisplay + 5);
                                }
                                break;
                            case 2:
                                globalVariables.YogSothoth = 0;
                                break;
                            case 3:
                                globalVariables.YogSothoth = 2;
                                for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                    globalVariables.Investigators.get(i).Status = 2;
                                }
                                break;
                            case 4:
                                globalVariables.YogSothoth = 3;
                                for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                    globalVariables.Investigators.get(i).Status = 2;
                                    globalVariables.Investigators.get(i).Horror = globalVariables
                                            .Investigators.get(i).Sanity;
                                }
                                globalVariables.DunwichCompleted = 1;
                                break;
                        }
                        break;
                }
                break;
            // The Path to Carcosa
            case 3:
                if (globalVariables.CurrentScenario > 1) {
                    globalVariables.ChasingStranger += strangerCounter;
                }
                switch (globalVariables.CurrentScenario) {
                    // Curtain Call
                    case 1:
                        for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                            globalVariables.Investigators.get(i).AvailableXP += globalVariables.VictoryDisplay;
                        }
                        globalVariables.Stranger = 1;
                        switch (globalVariables.ScenarioResolution) {
                            case 0:
                                globalVariables.ChasingStranger = strangerCounter;
                                globalVariables.Police = 0;
                                break;
                            case 1:
                                globalVariables.ChasingStranger = 2;
                                globalVariables.Police = 1;
                                if (additionalCheckbox.isChecked()) {
                                    globalVariables.Police = 2;
                                }
                                globalVariables.Conviction += 1;
                                break;
                            case 2:
                                globalVariables.ChasingStranger = 2;
                                globalVariables.Police = 3;
                                globalVariables.Doubt += 1;
                                break;
                        }
                        if (investigatorOne.isChecked()) {
                            globalVariables.Theatre = 1;
                        } else if (investigatorTwo.isChecked()) {
                            globalVariables.Theatre = 2;
                        } else if (investigatorThree.isChecked()) {
                            globalVariables.Theatre = 3;
                        } else {
                            globalVariables.Theatre = 0;
                        }
                        break;
                    // Last King
                    case 2:
                        boolean resigned = false;
                        for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                            globalVariables.Investigators.get(i).AvailableXP += globalVariables.VictoryDisplay;
                            if (globalVariables.Investigators.get(i).TempStatus == 1) {
                                resigned = true;
                            }
                        }

                        if (investigatorOne.isChecked() && additionalOne.isChecked()) {
                            globalVariables.Constance = 4;
                        } else if (investigatorOne.isChecked()) {
                            globalVariables.Constance = 1;
                        } else if (additionalOne.isChecked()) {
                            globalVariables.Constance = 2;
                        } else {
                            globalVariables.Constance = 0;
                        }

                        if (investigatorTwo.isChecked() && additionalTwo.isChecked()) {
                            globalVariables.Jordan = 4;
                        } else if (investigatorTwo.isChecked()) {
                            globalVariables.Jordan = 1;
                        } else if (additionalTwo.isChecked()) {
                            globalVariables.Jordan = 2;
                        } else {
                            globalVariables.Jordan = 0;
                        }

                        if (investigatorFive.isChecked() && additionalThree.isChecked()) {
                            globalVariables.Ishimaru = 4;
                        } else if (investigatorFive.isChecked()) {
                            globalVariables.Ishimaru = 1;
                        } else if (additionalThree.isChecked()) {
                            globalVariables.Ishimaru = 2;
                        } else {
                            globalVariables.Ishimaru = 0;
                        }

                        if (investigatorThree.isChecked() && additionalFour.isChecked()) {
                            globalVariables.Sebastien = 4;
                        } else if (investigatorThree.isChecked()) {
                            globalVariables.Sebastien = 1;
                        } else if (additionalFour.isChecked()) {
                            globalVariables.Sebastien = 2;
                        } else {
                            globalVariables.Sebastien = 0;
                        }

                        if (investigatorFour.isChecked() && additionalFive.isChecked()) {
                            globalVariables.Ashleigh = 4;
                        } else if (investigatorFour.isChecked()) {
                            globalVariables.Ashleigh = 1;
                        } else if (additionalFive.isChecked()) {
                            globalVariables.Ashleigh = 2;
                        } else {
                            globalVariables.Ashleigh = 0;
                        }

                        String amountOne = counterOne.getText().toString();
                        String amountTwo = counterTwo.getText().toString();
                        String amountThree = counterThree.getText().toString();
                        String amountFour = counterFour.getText().toString();
                        if (Integer.valueOf(amountOne) > 0) {
                            globalVariables.Investigators.get(0).AvailableXP += Integer.valueOf(amountOne);
                        }
                        if (Integer.valueOf(amountTwo) > 0) {
                            globalVariables.Investigators.get(1).AvailableXP += Integer.valueOf(amountTwo);
                        }
                        if (Integer.valueOf(amountThree) > 0) {
                            globalVariables.Investigators.get(2).AvailableXP += Integer.valueOf(amountThree);
                        }
                        if (Integer.valueOf(amountFour) > 0) {
                            globalVariables.Investigators.get(3).AvailableXP += Integer.valueOf(amountFour);
                        }

                        switch (globalVariables.ScenarioResolution) {
                            case 0:
                                if (resigned) {
                                    globalVariables.Theatre = 0;
                                } else {
                                    globalVariables.Theatre = 4;
                                    globalVariables.Party = 0;
                                }
                                break;
                            case 3:
                                globalVariables.Theatre = 4;
                                globalVariables.Party = 0;
                                if (globalVariables.Constance == 1) {
                                    globalVariables.Constance = 3;
                                } else if (globalVariables.Constance == 4) {
                                    globalVariables.Constance = 5;
                                }
                                if (globalVariables.Ishimaru == 1) {
                                    globalVariables.Ishimaru = 3;
                                } else if (globalVariables.Ishimaru == 4) {
                                    globalVariables.Ishimaru = 5;
                                }
                                if (globalVariables.Ashleigh == 1) {
                                    globalVariables.Ashleigh = 3;
                                } else if (globalVariables.Ashleigh == 4) {
                                    globalVariables.Ashleigh = 5;
                                }
                                if (globalVariables.Jordan == 1) {
                                    globalVariables.Jordan = 3;
                                } else if (globalVariables.Jordan == 4) {
                                    globalVariables.Jordan = 5;
                                }
                                if (globalVariables.Sebastien == 1) {
                                    globalVariables.Sebastien = 3;
                                } else if (globalVariables.Sebastien == 4) {
                                    globalVariables.Sebastien = 5;
                                }
                                break;
                        }
                        break;
                    // Echoes of the Past
                    case 4:
                        if (additionalCheckbox.isChecked()) {
                            if (globalVariables.Sebastien == 0) {
                                globalVariables.Sebastien = 1;
                            } else if (globalVariables.Sebastien == 1) {
                                globalVariables.Sebastien = 4;
                            } else if (globalVariables.Sebastien == 3) {
                                globalVariables.Sebastien = 5;
                            }
                        }
                        switch (globalVariables.ScenarioResolution) {
                            case 1:
                                globalVariables.Onyx = 1;
                                globalVariables.Conviction += 1;
                                globalVariables.Theatre = 1;
                                for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                    globalVariables.Investigators.get(i).AvailableXP += globalVariables.VictoryDisplay;
                                }
                                break;
                            case 2:
                                globalVariables.Onyx = 2;
                                globalVariables.Doubt += 1;
                                globalVariables.Theatre = 3;
                                for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                    globalVariables.Investigators.get(i).AvailableXP += globalVariables.VictoryDisplay;
                                }
                                break;
                            case 3:
                                globalVariables.Onyx = 3;
                                globalVariables.Theatre = 2;
                                for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                    globalVariables.Investigators.get(i).AvailableXP += globalVariables.VictoryDisplay;
                                }
                                break;
                            case 4:
                            case 0:
                                globalVariables.Onyx = 4;
                                globalVariables.Theatre = 4;
                                for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                    globalVariables.Investigators.get(i).AvailableXP += (globalVariables
                                            .VictoryDisplay + 1);
                                }
                                break;
                        }
                        break;
                    // Unspeakable Oath
                    case 5:
                        if (additionalCheckbox.isChecked()) {
                            if (globalVariables.Constance == 0) {
                                globalVariables.Constance = 1;
                            } else if (globalVariables.Constance == 1) {
                                globalVariables.Constance = 4;
                            } else if (globalVariables.Constance == 3) {
                                globalVariables.Constance = 5;
                            }
                        }
                        if (additionalCheckboxTwo.isChecked()) {
                            if (globalVariables.Onyx == 1) {
                                globalVariables.Daniel = 1;
                                for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                    globalVariables.Investigators.get(i).AvailableXP += 2;
                                }
                            } else {
                                globalVariables.Daniel = 2;
                            }
                        } else {
                            globalVariables.Daniel = 3;
                        }
                        for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                            globalVariables.Investigators.get(i).AvailableXP += globalVariables.VictoryDisplay;
                            if (globalVariables.Investigators.get(i).TempStatus > 1) {
                                globalVariables.Investigators.get(i).Status = 2;
                                globalVariables.Investigators.get(i).Horror = globalVariables
                                        .Investigators.get(i).Sanity;
                            }
                        }
                        switch (globalVariables.ScenarioResolution) {
                            case 0:
                            case 1:
                                globalVariables.Asylum = 1;
                                globalVariables.Theatre = 1;
                                globalVariables.DanielsWarning = 0;
                                break;
                            case 2:
                                globalVariables.Asylum = 2;
                                globalVariables.Theatre = 2;
                                for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                    globalVariables.Investigators.get(i).Damage += 1;
                                }
                                break;
                            case 3:
                                globalVariables.Asylum = 3;
                                globalVariables.Theatre = 3;
                                break;
                        }
                        break;
                    // Phantom of Truth
                    case 7:
                        if (additionalCheckbox.isChecked()) {
                            if (globalVariables.Jordan == 0) {
                                globalVariables.Jordan = 1;
                            } else if (globalVariables.Jordan == 1) {
                                globalVariables.Jordan = 4;
                            } else if (globalVariables.Jordan == 3) {
                                globalVariables.Jordan = 5;
                            }
                        }
                        globalVariables.Nigel = globalVariables.ScenarioResolution;
                        for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                            globalVariables.Investigators.get(i).AvailableXP += globalVariables.VictoryDisplay;
                        }
                        switch (globalVariables.ScenarioResolution) {
                            case 0:
                                globalVariables.Theatre = 3;
                                break;
                            case 1:
                                globalVariables.Theatre = 1;
                                break;
                            case 2:
                                globalVariables.Theatre = 2;
                                for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                    globalVariables.Investigators.get(i).Horror += 1;
                                    globalVariables.Investigators.get(i).AvailableXP += 2;
                                }
                                break;
                            case 3:
                                globalVariables.Theatre = 3;
                                break;
                        }
                        break;
                    // The Pallid Mask
                    case 8:
                        if (additionalCheckbox.isChecked()) {
                            if (globalVariables.Ishimaru == 0) {
                                globalVariables.Ishimaru = 1;
                            } else if (globalVariables.Ishimaru == 1) {
                                globalVariables.Ishimaru = 4;
                            } else if (globalVariables.Ishimaru == 3) {
                                globalVariables.Ishimaru = 5;
                            }
                        }
                        if (additionalOne.isChecked()) {
                            globalVariables.Conviction += 2;
                        } else if (additionalTwo.isChecked()) {
                            globalVariables.Doubt += 2;
                        }
                        for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                            globalVariables.Investigators.get(i).AvailableXP += globalVariables.VictoryDisplay;
                            if (globalVariables.Investigators.get(i).TempStatus == 1) {
                                globalVariables.Investigators.get(i).Damage += 1;
                            }
                        }
                        switch (globalVariables.ScenarioResolution) {
                            case 0:
                                globalVariables.Theatre = 3;
                                if (investigatorOne.isChecked()) {
                                    globalVariables.Investigators.get(0).AvailableXP += 2;
                                    globalVariables.InvOneReadAct = globalVariables.Investigators.get(0).Name;
                                } else {
                                    globalVariables.InvOneReadAct = 0;
                                }
                                if (investigatorTwo.isChecked()) {
                                    globalVariables.Investigators.get(1).AvailableXP += 2;
                                    globalVariables.InvTwoReadAct = globalVariables.Investigators.get(1).Name;
                                } else {
                                    globalVariables.InvTwoReadAct = 0;
                                }
                                if (investigatorThree.isChecked()) {
                                    globalVariables.Investigators.get(2).AvailableXP += 2;
                                    globalVariables.InvThreeReadAct = globalVariables.Investigators.get(2).Name;
                                } else {
                                    globalVariables.InvThreeReadAct = 0;
                                }
                                if (investigatorFour.isChecked()) {
                                    globalVariables.Investigators.get(3).AvailableXP += 2;
                                    globalVariables.InvFourReadAct = globalVariables.Investigators.get(3).Name;
                                } else {
                                    globalVariables.InvFourReadAct = 0;
                                }
                                break;
                            case 1:
                                globalVariables.Theatre = 1;
                                break;
                            case 2:
                                globalVariables.Theatre = 2;
                                globalVariables.ChasingStranger += 2;
                                break;
                        }
                        break;
                    // Black Stars Rise
                    case 9:
                        if (globalVariables.ScenarioResolution == 1 || globalVariables.ScenarioResolution == 2) {
                            if (additionalCheckbox.isChecked()) {
                                if (globalVariables.Ashleigh == 0) {
                                    globalVariables.Ashleigh = 1;
                                } else if (globalVariables.Ashleigh == 1) {
                                    globalVariables.Ashleigh = 4;
                                } else if (globalVariables.Ashleigh == 3) {
                                    globalVariables.Ashleigh = 5;
                                }
                            }
                            if (additionalOne.isChecked()) {
                                globalVariables.Conviction += 1;
                            } else if (additionalTwo.isChecked()) {
                                globalVariables.Doubt += 1;
                            }
                            for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                globalVariables.Investigators.get(i).AvailableXP += globalVariables.VictoryDisplay;
                            }
                        }
                        switch (globalVariables.ScenarioResolution) {
                            case 0:
                            case 3:
                                globalVariables.Path = 0;
                                for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                    globalVariables.Investigators.get(i).Status = 2;
                                    globalVariables.Investigators.get(i).Horror = globalVariables
                                            .Investigators.get(i).Sanity;
                                }
                                globalVariables.CarcosaCompleted = 1;
                                break;
                            case 1:
                                globalVariables.Theatre = 2;
                                globalVariables.Path = 1;
                                break;
                            case 2:
                                globalVariables.Theatre = 3;
                                globalVariables.Path = 2;
                                break;
                        }
                        globalVariables.Hastur = 0;
                        break;
                    // Dim Carcosa
                    case 10:
                        for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                            if (globalVariables.Investigators.get(i).TempStatus == 1) {
                                globalVariables.Investigators.get(i).Damage = globalVariables
                                        .Investigators.get(i).Health;
                            }
                        }
                        globalVariables.Hastur = globalVariables.ScenarioResolution;
                        switch (globalVariables.ScenarioResolution) {
                            case 0:
                                if (globalVariables.Conviction >= globalVariables.Doubt) {
                                    globalVariables.Hastur = 4;
                                } else if (globalVariables.Doubt > globalVariables.Conviction) {
                                    globalVariables.Hastur = 5;
                                }
                                for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                    globalVariables.Investigators.get(i).Status = 2;
                                    globalVariables.Investigators.get(i).Horror = globalVariables
                                            .Investigators.get(i).Sanity;
                                }
                                globalVariables.CarcosaCompleted = 1;
                                break;
                            case 1:
                                for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                    globalVariables.Investigators.get(i).Damage += 2;
                                    globalVariables.Investigators.get(i).Horror += 2;
                                    globalVariables.Investigators.get(i).AvailableXP += (globalVariables
                                            .VictoryDisplay + 5);
                                }
                                break;
                            case 2:
                                for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                    globalVariables.Investigators.get(i).Horror += 2;
                                    globalVariables.Investigators.get(i).AvailableXP += (globalVariables
                                            .VictoryDisplay + 5);
                                }
                                break;
                            case 3:
                                for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                    globalVariables.Investigators.get(i).Damage += 2;
                                    globalVariables.Investigators.get(i).AvailableXP += (globalVariables
                                            .VictoryDisplay + 5);
                                }
                                break;
                        }
                        if (globalVariables.ScenarioResolution != 0) {
                            if (investigatorOne.isChecked()) {
                                globalVariables.InvOnePossessed = globalVariables.Investigators.get(0).Name;
                            } else {
                                globalVariables.InvOnePossessed = 0;
                            }
                            if (investigatorTwo.isChecked()) {
                                globalVariables.InvTwoPossessed = globalVariables.Investigators.get(1).Name;
                            } else {
                                globalVariables.InvTwoPossessed = 0;
                            }
                            if (investigatorThree.isChecked()) {
                                globalVariables.InvThreePossessed = globalVariables.Investigators.get(2).Name;
                            } else {
                                globalVariables.InvThreePossessed = 0;
                            }
                            if (investigatorFour.isChecked()) {
                                globalVariables.InvFourPossessed = globalVariables.Investigators.get(3).Name;
                            } else {
                                globalVariables.InvFourPossessed = 0;
                            }
                            if (!investigatorOne.isChecked() && !investigatorTwo.isChecked() && !investigatorThree
                                    .isChecked() && !investigatorFour.isChecked()) {
                                globalVariables.CarcosaCompleted = 1;
                            }
                        }
                        break;
                }
                break;
            // The Forgotten Age
            case 4:
                switch (globalVariables.CurrentScenario) {
                    // Untamed Wilds
                    case 1:
                        globalVariables.YigsFury += vengeanceCounter;
                        for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                            globalVariables.Investigators.get(i).AvailableXP += globalVariables.VictoryDisplay;
                        }
                        switch (globalVariables.ScenarioResolution) {
                            case 0:
                                globalVariables.Ruins = 1;
                                if (investigatorOne.isChecked()) {
                                    globalVariables.Ichtaca = 0;
                                    globalVariables.Alejandro = 2;
                                } else if (investigatorTwo.isChecked()) {
                                    globalVariables.Ichtaca = 2;
                                    globalVariables.Alejandro = 2;
                                } else if (investigatorThree.isChecked()) {
                                    globalVariables.Ichtaca = 1;
                                    globalVariables.Alejandro = 1;
                                }
                                break;
                            case 1:
                                globalVariables.Ruins = 2;
                                globalVariables.Ichtaca = 1;
                                globalVariables.Alejandro = 1;
                                break;
                            case 2:
                                globalVariables.Ruins = 2;
                                globalVariables.Ichtaca = 2;
                                globalVariables.Alejandro = 2;
                                break;
                        }
                        break;
                    // Doom of Eztli
                    case 6:
                        boolean defeated = false;
                        for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                            if (globalVariables.Investigators.get(i).TempStatus > 1) {
                                defeated = true;
                                if (globalVariables.YigsFury >= 4) {
                                    globalVariables.Investigators.get(i).Damage = globalVariables
                                            .Investigators.get(i).Health;
                                }
                            }
                        }
                        boolean resolutionThree = false;
                        if (investigatorOne.isChecked()) {
                            globalVariables.Harbinger = Integer.valueOf(counterOne.getText().toString());
                        } else {
                            globalVariables.Harbinger = -1;
                        }
                        switch (globalVariables.ScenarioResolution) {
                            case 0:
                                if (defeated) {
                                    if (globalVariables.YigsFury >= 4) {
                                        globalVariables.YigsFury += vengeanceCounter;
                                        globalVariables.Relic = 2;
                                    } else {
                                        resolutionThree = true;
                                    }
                                } else {
                                    resolutionThree = true;
                                }
                                break;
                            case 1:
                                if (defeated && globalVariables.YigsFury < 4) {
                                    globalVariables.YigsFury = globalVariables.YigsFury + 3;
                                }
                                globalVariables.YigsFury += vengeanceCounter;
                                globalVariables.Relic = 1;
                                for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                    globalVariables.Investigators.get(i).AvailableXP += globalVariables.VictoryDisplay;
                                }
                                break;
                            case 3:
                                if (defeated && globalVariables.YigsFury < 4) {
                                    globalVariables.YigsFury = globalVariables.YigsFury + 3;
                                }
                                resolutionThree = true;
                                break;
                        }
                        if (resolutionThree) {
                            if (optionOne.isChecked()) {
                                globalVariables.CurrentScenario = globalVariables.CurrentScenario - 1;
                                globalVariables.Eztli = globalVariables.Eztli + 1;
                            } else if (optionTwo.isChecked()) {
                                globalVariables.Relic = 1;
                                globalVariables.YigsFury += (vengeanceCounter + 10);
                                for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                    globalVariables.Investigators.get(i).AvailableXP += globalVariables.VictoryDisplay;
                                }
                            }
                        }
                        break;
                    // Threads of Fate
                    case 8:
                        if (additionalCheckbox.isChecked()) {
                            globalVariables.MissingRelic = 2;
                        } else {
                            globalVariables.MissingRelic = 1;
                        }
                        if (additionalCheckboxTwo.isChecked()) {
                            globalVariables.MissingAlejandro = 2;
                        } else {
                            globalVariables.MissingAlejandro = 1;
                        }
                        if (additionalCheckboxThree.isChecked()) {
                            globalVariables.MissingIchtaca = 2;
                        } else {
                            globalVariables.MissingIchtaca = 1;
                        }
                        for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                            globalVariables.Investigators.get(i).AvailableXP += (globalVariables.VictoryDisplay +
                                    vengeanceCounter);
                            if (globalVariables.IchtacasTale == 4 && additionalCheckboxTwo.isChecked()) {
                                globalVariables.Investigators.get(i).AvailableXP += 2;
                            }
                            if (globalVariables.IchtacasTale == 4 && additionalCheckboxThree.isChecked()) {
                                globalVariables.Investigators.get(i).AvailableXP += 2;
                            }
                        }
                        break;
                    // Boundary Beyond
                    case 10:
                        globalVariables.PathsKnown = Integer.valueOf(counterTwo.getText().toString());
                        if (globalVariables.ScenarioResolution == 1) {
                            globalVariables.IchtacaConfidence = 1;
                        } else {
                            globalVariables.IchtacaConfidence = 0;
                        }
                        if (investigatorOne.isChecked()) {
                            globalVariables.Harbinger = Integer.valueOf(counterOne.getText().toString());
                        } else {
                            globalVariables.Harbinger = -1;
                        }
                        globalVariables.YigsFury += vengeanceCounter;
                        for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                            globalVariables.Investigators.get(i).AvailableXP += globalVariables.VictoryDisplay +
                                    Integer.valueOf(counterTwo.getText().toString());
                        }
                        break;
                    // Heart of the Elders A
                    case 18:
                        switch (globalVariables.ScenarioResolution) {
                            case 0:
                                int paths = Integer.valueOf(additionalCounter.getText().toString());
                                if (paths > globalVariables.PathsKnown) {
                                    globalVariables.PathsKnown = paths;
                                }
                                globalVariables.JungleWatches = "0";
                                globalVariables.CurrentScenario = globalVariables.CurrentScenario - 1;
                                break;
                            case 1:
                                globalVariables.JungleWatches = textBox.getText().toString().trim();
                                for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                    globalVariables.Investigators.get(i).AvailableXP += globalVariables.VictoryDisplay;
                                }
                                break;
                        }
                        break;
                        // Heart of the Elders B
                    case 19:
                        if (investigatorOne.isChecked()) {
                            globalVariables.Harbinger = Integer.valueOf(counterOne.getText().toString());
                        } else {
                            globalVariables.Harbinger = -1;
                        }
                        globalVariables.YigsFury += vengeanceCounter;
                        for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                            globalVariables.Investigators.get(i).AvailableXP += globalVariables.VictoryDisplay;
                        }
                        break;
                }
                break;
        }
        if (globalVariables.CurrentScenario > 100) {
            switch (globalVariables.CurrentScenario) {
                // Curse of the Rougarou
                case 101:
                    for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                        globalVariables.Investigators.get(i).AvailableXP += globalVariables.VictoryDisplay;
                    }
                    switch (globalVariables.ScenarioResolution) {
                        case 0:
                            globalVariables.Rougarou = 1;
                            break;
                        case 1:
                            globalVariables.Rougarou = 1;
                            break;
                        case 2:
                            globalVariables.Rougarou = 2;
                            break;
                        case 3:
                            globalVariables.Rougarou = 3;
                            break;
                    }
                    break;
                // Carnevale of Horrors
                case 102:
                    for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                        globalVariables.Investigators.get(i).AvailableXP += globalVariables.VictoryDisplay;
                    }
                    globalVariables.Carnevale = (globalVariables.ScenarioResolution + 1);
                    if (additionalCheckbox.isChecked()) {
                        globalVariables.CarnevaleReward = 1;
                    } else if (additionalCheckboxTwo.isChecked()) {
                        globalVariables.CarnevaleReward = 2;
                    } else {
                        globalVariables.CarnevaleReward = 0;
                    }
                    break;
            }
        }

        // Apply defeats from temp status and weaknesses and subtract spent XP
        for (int i = 0; i < globalVariables.Investigators.size(); i++) {
            Investigator currentInvestigator = globalVariables.Investigators.get(i);

            // Work out XP amounts
            int xpEarned = 0;
            switch (i) {
                case 0:
                    xpEarned += currentInvestigator.AvailableXP - invOneXP;
                    break;
                case 1:
                    xpEarned += currentInvestigator.AvailableXP - invTwoXP;
                    break;
                case 2:
                    xpEarned += currentInvestigator.AvailableXP - invThreeXP;
                    break;
                case 3:
                    xpEarned += currentInvestigator.AvailableXP - invFourXP;
                    break;
            }
            currentInvestigator.TotalXP += xpEarned;
            currentInvestigator.AvailableXP += -currentInvestigator.SpentXP;
            currentInvestigator.SpentXP = 0;

            int status = currentInvestigator.TempStatus;
            // Add to physical trauma
            if (status == 2) {
                currentInvestigator.Damage += 1;
            }
            // Add to mental trauma
            else if (status == 3) {
                currentInvestigator.Horror += 1;
            }

            // Reset temp status
            currentInvestigator.TempStatus = 0;

            // Apply any relevant weaknesses
            if (currentInvestigator.TempWeakness == 1) {
                switch (currentInvestigator.Name) {
                    case Investigator.AKACHI_ONYELE:
                        currentInvestigator.Damage += 1;
                        break;
                    case Investigator.ROLAND_BANKS:
                    case Investigator.ZOEY_SAMARAS:
                    case Investigator.JENNY_BARNES:
                    case Investigator.PRESTON_FAIRMONT:
                        currentInvestigator.Horror += 1;
                        break;
                    case Investigator.SKIDS_OTOOLE:
                    case Investigator.JOE_DIAMOND:
                        if (xpEarned >= 2) {
                            currentInvestigator.AvailableXP += -2;
                            currentInvestigator.TotalXP += -2;
                        } else if (xpEarned == 1) {
                            currentInvestigator.AvailableXP += -1;
                            currentInvestigator.TotalXP += -1;
                        }
                        break;
                    case Investigator.CALVIN_WRIGHT:
                        currentInvestigator.Damage += currentInvestigator.TempWeaknessOne;
                        currentInvestigator.Horror += currentInvestigator.TempWeaknessTwo;
                        break;
                }
                currentInvestigator.TempWeakness = 0;
            }

            // Check health and sanity
            if ((currentInvestigator.Damage >= currentInvestigator.Health) ||
                    (currentInvestigator.Horror >= currentInvestigator.Sanity)) {
                currentInvestigator.Status = 2;
            }

            // Charon's Obol
            if (globalVariables.CharonsObol > 0) {
                if (globalVariables.CharonsObol % 2 == 0) {
                    if (i == 0) {
                        if (status >= 2) {
                            currentInvestigator.Status = 2;
                            currentInvestigator.Damage = currentInvestigator.Health;
                            globalVariables.CharonsObol = 1;
                        } else {
                            currentInvestigator.AvailableXP += 2;
                            currentInvestigator.TotalXP += 2;
                        }
                    }
                }
                if (globalVariables.CharonsObol % 3 == 0) {
                    if (i == 1) {
                        if (status >= 2) {
                            currentInvestigator.Status = 2;
                            currentInvestigator.Damage = currentInvestigator.Health;
                            globalVariables.CharonsObol = 1;
                        } else {
                            currentInvestigator.AvailableXP += 2;
                            currentInvestigator.TotalXP += 2;
                        }
                    }
                }
                if (globalVariables.CharonsObol % 5 == 0) {
                    if (i == 2) {
                        if (status >= 2) {
                            currentInvestigator.Status = 2;
                            currentInvestigator.Damage = currentInvestigator.Health;
                            globalVariables.CharonsObol = 1;
                        } else {
                            currentInvestigator.AvailableXP += 2;
                            currentInvestigator.TotalXP += 2;
                        }
                    }
                }
                if (globalVariables.CharonsObol % 7 == 0) {
                    if (i == 3) {
                        if (status >= 2) {
                            currentInvestigator.Status = 2;
                            currentInvestigator.Damage = currentInvestigator.Health;
                            globalVariables.CharonsObol = 1;
                        } else {
                            currentInvestigator.AvailableXP += 2;
                            currentInvestigator.TotalXP += 2;
                        }
                    }
                }
            }
        }

        // Doomed
        if (globalVariables.Doomed > 2) {
            switch (globalVariables.Doomed) {
                case 3:
                    globalVariables.Investigators.get(0).Status = 2;
                    globalVariables.Investigators.get(0).Damage = globalVariables.Investigators.get(0).Health;
                    globalVariables.Doomed = 0;
                    break;
                case 4:
                    globalVariables.Investigators.get(1).Status = 2;
                    globalVariables.Investigators.get(1).Damage = globalVariables.Investigators.get(1).Health;
                    globalVariables.Doomed = 0;
                    break;
                case 5:
                    globalVariables.Investigators.get(2).Status = 2;
                    globalVariables.Investigators.get(2).Damage = globalVariables.Investigators.get(2).Health;
                    globalVariables.Doomed = 0;
                    break;
                case 6:
                    globalVariables.Investigators.get(3).Status = 2;
                    globalVariables.Investigators.get(3).Damage = globalVariables.Investigators.get(3).Health;
                    globalVariables.Doomed = 0;
                    break;
            }
        }

        // Increment current scenario
        int nextScenario;
        if (globalVariables.CurrentCampaign == 2 && globalVariables.FirstScenario == 2) {
            if (globalVariables.CurrentScenario == 1) {
                nextScenario = 3;
            } else if (globalVariables.CurrentScenario == 2) {
                nextScenario = 1;
            } else {
                nextScenario = globalVariables.CurrentScenario + 1;
            }
        } else if (globalVariables.CurrentCampaign == 2 && globalVariables.CurrentScenario == 6 && globalVariables
                .ScenarioResolution == 0) {
            nextScenario = globalVariables.CurrentScenario + 2;
        } else if (globalVariables.CurrentCampaign == 3 && globalVariables.CurrentScenario == 2 && globalVariables
                .Theatre ==
                4) {
            nextScenario = globalVariables.CurrentScenario + 2;
        } else if (globalVariables.CurrentCampaign == 3 && globalVariables.CurrentScenario == 5 && globalVariables
                .Asylum == 1) {
            nextScenario = globalVariables.CurrentScenario + 2;
        } else {
            nextScenario = globalVariables.CurrentScenario + 1;
        }
        if (globalVariables.CurrentScenario > 100) {
            nextScenario = globalVariables.PreviousScenario;
        }

        if (globalVariables.CurrentCampaign == 2 && globalVariables.DunwichCompleted == 1) {
            nextScenario = 12;
        }
        if (globalVariables.CurrentCampaign == 3 && globalVariables.CarcosaCompleted == 1) {
            nextScenario = 12;
        }
        globalVariables.CurrentScenario = nextScenario;

        // Reset victory display and seal
        globalVariables.VictoryDisplay = 0;
        globalVariables.seal = new ArrayList<>();

        saveCampaign(activity, globalVariables);
    }

    /*
        Save the campaign to the database
      */
    public static void saveCampaign(Context context, GlobalVariables globalVariables) {

        // Get a writable database
        ArkhamDbHelper mDbHelper = new ArkhamDbHelper(context);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Update campaign variables (currently only the scenario number and which investigators are in use)
        ContentValues campaignValues = new ContentValues();
        campaignValues.put(CampaignEntry.COLUMN_CURRENT_CAMPAIGN, globalVariables.CurrentCampaign);
        campaignValues.put(CampaignEntry.COLUMN_CURRENT_SCENARIO, globalVariables.CurrentScenario);
        campaignValues.put(CampaignEntry.COLUMN_DIFFICULTY, globalVariables.CurrentDifficulty);
        campaignValues.put(CampaignEntry.COLUMN_NOTES, globalVariables.Notes);
        campaignValues.put(CampaignEntry.COLUMN_NIGHT_COMPLETED, globalVariables.NightCompleted);
        campaignValues.put(CampaignEntry.COLUMN_DUNWICH_COMPLETED, globalVariables.DunwichCompleted);
        campaignValues.put(CampaignEntry.COLUMN_CARCOSA_COMPLETED, globalVariables.CarcosaCompleted);
        campaignValues.put(CampaignEntry.COLUMN_ROLAND_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .ROLAND_BANKS]);
        campaignValues.put(CampaignEntry.COLUMN_DAISY_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .DAISY_WALKER]);
        campaignValues.put(CampaignEntry.COLUMN_SKIDS_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .SKIDS_OTOOLE]);
        campaignValues.put(CampaignEntry.COLUMN_AGNES_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .AGNES_BAKER]);
        campaignValues.put(CampaignEntry.COLUMN_WENDY_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .WENDY_ADAMS]);
        campaignValues.put(CampaignEntry.COLUMN_ZOEY_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .ZOEY_SAMARAS]);
        campaignValues.put(CampaignEntry.COLUMN_REX_INUSE, globalVariables.InvestigatorsInUse[Investigator.REX_MURPHY]);
        campaignValues.put(CampaignEntry.COLUMN_JENNY_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .JENNY_BARNES]);
        campaignValues.put(CampaignEntry.COLUMN_JIM_INUSE, globalVariables.InvestigatorsInUse[Investigator.JIM_CULVER]);
        campaignValues.put(CampaignEntry.COLUMN_PETE_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .ASHCAN_PETE]);
        campaignValues.put(CampaignEntry.COLUMN_MARK_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .MARK_HARRIGAN]);
        campaignValues.put(CampaignEntry.COLUMN_MINH_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .MINH_THI_PHAN]);
        campaignValues.put(CampaignEntry.COLUMN_SEFINA_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .SEFINA_ROUSSEAU]);
        campaignValues.put(CampaignEntry.COLUMN_AKACHI_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .AKACHI_ONYELE]);
        campaignValues.put(CampaignEntry.COLUMN_WILLIAM_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .WILLIAM_YORICK]);
        campaignValues.put(CampaignEntry.COLUMN_LOLA_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .LOLA_HAYES]);
        campaignValues.put(CampaignEntry.COLUMN_MARIE_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .MARIE_LAMBEAU]);
        campaignValues.put(CampaignEntry.COLUMN_NORMAN_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .NORMAN_WITHERS]);
        campaignValues.put(CampaignEntry.COLUMN_CAROLYN_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .CAROLYN_FERN]);
        campaignValues.put(CampaignEntry.COLUMN_SILAS_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .SILAS_MARSH]);
        campaignValues.put(CampaignEntry.COLUMN_LEO_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .LEO_ANDERSON]);
        campaignValues.put(CampaignEntry.COLUMN_URSULA_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .URSULA_DOWNS]);
        campaignValues.put(CampaignEntry.COLUMN_FINN_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .FINN_EDWARDS]);
        campaignValues.put(CampaignEntry.COLUMN_MATEO_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .FATHER_MATEO]);
        campaignValues.put(CampaignEntry.COLUMN_CALVIN_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .CALVIN_WRIGHT]);
        campaignValues.put(CampaignEntry.COLUMN_JOE_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .JOE_DIAMOND]);
        campaignValues.put(CampaignEntry.COLUMN_PRESTON_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .PRESTON_FAIRMONT]);
        campaignValues.put(CampaignEntry.COLUMN_DIANA_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .DIANA_STANLEY]);
        campaignValues.put(CampaignEntry.COLUMN_RITA_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .RITA_YOUNG]);
        campaignValues.put(CampaignEntry.COLUMN_ROUGAROU_STATUS, globalVariables.Rougarou);
        campaignValues.put(CampaignEntry.COLUMN_STRANGE_SOLUTION, globalVariables.StrangeSolution);
        campaignValues.put(CampaignEntry.COLUMN_ARCHAIC_GLYPHS, globalVariables.ArchaicGlyphs);
        campaignValues.put(CampaignEntry.COLUMN_CHARONS_OBOL, globalVariables.CharonsObol);
        campaignValues.put(CampaignEntry.COLUMN_CARNEVALE_STATUS, globalVariables.Carnevale);
        campaignValues.put(CampaignEntry.COLUMN_CARNEVALE_REWARDS, globalVariables.CarnevaleReward);
        String campaignSelection = CampaignEntry._ID + " LIKE ?";
        String[] campaignSelectionArgs = {Long.toString(globalVariables.CampaignID)};
        db.update(
                CampaignEntry.TABLE_NAME,
                campaignValues,
                campaignSelection,
                campaignSelectionArgs);

        // Update all night variables
        if (globalVariables.CurrentCampaign == 1) {
            ContentValues nightValues = new ContentValues();
            nightValues.put(NightEntry.COLUMN_HOUSE_STANDING, globalVariables.HouseStanding);
            nightValues.put(NightEntry.COLUMN_GHOUL_PRIEST, globalVariables.GhoulPriest);
            nightValues.put(NightEntry.COLUMN_LITA_STATUS, globalVariables.LitaChantler);
            nightValues.put(NightEntry.COLUMN_CULTISTS_INTERROGATED, globalVariables
                    .CultistsInterrogated);
            nightValues.put(NightEntry.COLUMN_MIDNIGHT_STATUS, globalVariables.PastMidnight);
            nightValues.put(NightEntry.COLUMN_DREW_INTERROGATED, globalVariables.DrewInterrogated);
            nightValues.put(NightEntry.COLUMN_HERMAN_INTERROGATED, globalVariables
                    .HermanInterrogated);
            nightValues.put(NightEntry.COLUMN_PETER_INTERROGATED, globalVariables.PeterInterrogated);
            nightValues.put(NightEntry.COLUMN_RUTH_INTERROGATED, globalVariables.RuthInterrogated);
            nightValues.put(NightEntry.COLUMN_VICTORIA_INTERROGATED, globalVariables
                    .VictoriaInterrogated);
            nightValues.put(NightEntry.COLUMN_MASKED_INTERROGATED, globalVariables
                    .MaskedInterrogated);
            nightValues.put(NightEntry.COLUMN_UMORDHOTH_STATUS, globalVariables.Umordhoth);

            String nightSelection = NightEntry.PARENT_ID + " LIKE ?";
            String[] nightSelectionArgs = {Long.toString(globalVariables.CampaignID)};
            db.update(
                    NightEntry.TABLE_NAME,
                    nightValues,
                    nightSelection,
                    nightSelectionArgs);
        }

        // Update all Dunwich variables
        if (globalVariables.CurrentCampaign == 2) {
            ContentValues dunwichValues = new ContentValues();
            dunwichValues.put(DunwichEntry.COLUMN_FIRST_SCENARIO, globalVariables.FirstScenario);
            dunwichValues.put(DunwichEntry.COLUMN_INVESTIGATORS_UNCONSCIOUS, globalVariables
                    .InvestigatorsUnconscious);
            dunwichValues.put(DunwichEntry.COLUMN_HENRY_ARMITAGE, globalVariables.HenryArmitage);
            dunwichValues.put(DunwichEntry.COLUMN_WARREN_RICE, globalVariables.WarrenRice);
            dunwichValues.put(DunwichEntry.COLUMN_STUDENTS, globalVariables.Students);
            dunwichValues.put(DunwichEntry.COLUMN_OBANNION_GANG, globalVariables.ObannionGang);
            dunwichValues.put(DunwichEntry.COLUMN_FRANCIS_MORGAN, globalVariables.FrancisMorgan);
            dunwichValues.put(DunwichEntry.COLUMN_INVESTIGATORS_CHEATED, globalVariables
                    .InvestigatorsCheated);
            dunwichValues.put(DunwichEntry.COLUMN_NECRONOMICON, globalVariables.Necronomicon);
            dunwichValues.put(DunwichEntry.COLUMN_ADAM_LYNCH_HAROLD_WALSTED, globalVariables
                    .AdamLynchHaroldWalsted);
            dunwichValues.put(DunwichEntry.COLUMN_DELAYED, globalVariables.InvestigatorsDelayed);
            dunwichValues.put(DunwichEntry.COLUMN_SILAS_BISHOP, globalVariables.SilasBishop);
            dunwichValues.put(DunwichEntry.COLUMN_ZEBULON_WHATELEY, globalVariables.ZebulonWhateley);
            dunwichValues.put(DunwichEntry.COLUMN_EARL_SAWYER, globalVariables.EarlSawyer);
            dunwichValues.put(DunwichEntry.COLUMN_ALLY_SACRIFICED, globalVariables.AllySacrificed);
            dunwichValues.put(DunwichEntry.COLUMN_TOWNSFOLK, globalVariables.TownsfolkAction);
            dunwichValues.put(DunwichEntry.COLUMN_BROOD_ESCAPED, globalVariables.BroodsEscaped);
            dunwichValues.put(DunwichEntry.COLUMN_INVESTIGATORS_GATE, globalVariables
                    .InvestigatorsGate);
            dunwichValues.put(DunwichEntry.COLUMN_YOG_SOTHOTH, globalVariables.YogSothoth);

            String dunwichSelection = DunwichEntry.PARENT_ID + " LIKE ?";
            String[] dunwichSelectionArgs = {Long.toString(globalVariables.CampaignID)};
            db.update(
                    DunwichEntry.TABLE_NAME,
                    dunwichValues,
                    dunwichSelection,
                    dunwichSelectionArgs);
        }

        // Update all carcosa variables
        if (globalVariables.CurrentCampaign == 3) {
            ContentValues carcosaValues = new ContentValues();
            carcosaValues.put(CarcosaEntry.COLUMN_DOUBT, globalVariables.Doubt);
            carcosaValues.put(CarcosaEntry.COLUMN_CONVICTION, globalVariables.Conviction);
            carcosaValues.put(CarcosaEntry.COLUMN_CHASING_STRANGER, globalVariables.ChasingStranger);
            carcosaValues.put(CarcosaEntry.COLUMN_STRANGER, globalVariables.Stranger);
            carcosaValues.put(CarcosaEntry.COLUMN_POLICE, globalVariables.Police);
            carcosaValues.put(CarcosaEntry.COLUMN_THEATRE, globalVariables.Theatre);
            carcosaValues.put(CarcosaEntry.COLUMN_CONSTANCE, globalVariables.Constance);
            carcosaValues.put(CarcosaEntry.COLUMN_JORDAN, globalVariables.Jordan);
            carcosaValues.put(CarcosaEntry.COLUMN_ISHIMARU, globalVariables.Ishimaru);
            carcosaValues.put(CarcosaEntry.COLUMN_SEBASTIEN, globalVariables.Sebastien);
            carcosaValues.put(CarcosaEntry.COLUMN_ASHLEIGH, globalVariables.Ashleigh);
            carcosaValues.put(CarcosaEntry.COLUMN_PARTY, globalVariables.Party);
            carcosaValues.put(CarcosaEntry.COLUMN_ONYX, globalVariables.Onyx);
            carcosaValues.put(CarcosaEntry.COLUMN_ASYLUM, globalVariables.Asylum);
            carcosaValues.put(CarcosaEntry.COLUMN_DANIEL, globalVariables.Daniel);
            carcosaValues.put(CarcosaEntry.COLUMN_DANIELS_WARNING, globalVariables.DanielsWarning);
            carcosaValues.put(CarcosaEntry.COLUMN_DREAMS, globalVariables.DreamsAction);
            carcosaValues.put(CarcosaEntry.COLUMN_NIGEL, globalVariables.Nigel);
            carcosaValues.put(CarcosaEntry.COLUMN_INV_ONE_READ_ACT, globalVariables.InvOneReadAct);
            carcosaValues.put(CarcosaEntry.COLUMN_INV_TWO_READ_ACT, globalVariables.InvTwoReadAct);
            carcosaValues.put(CarcosaEntry.COLUMN_INV_THREE_READ_ACT, globalVariables.InvThreeReadAct);
            carcosaValues.put(CarcosaEntry.COLUMN_INV_FOUR_READ_ACT, globalVariables.InvFourReadAct);
            carcosaValues.put(CarcosaEntry.COLUMN_PATH, globalVariables.Path);
            carcosaValues.put(CarcosaEntry.COLUMN_HASTUR, globalVariables.Hastur);
            carcosaValues.put(CarcosaEntry.COLUMN_INV_ONE_POSSESSED, globalVariables.InvOnePossessed);
            carcosaValues.put(CarcosaEntry.COLUMN_INV_TWO_POSSESSED, globalVariables.InvTwoPossessed);
            carcosaValues.put(CarcosaEntry.COLUMN_INV_THREE_POSSESSED, globalVariables
                    .InvThreePossessed);
            carcosaValues.put(CarcosaEntry.COLUMN_INV_FOUR_POSSESSED, globalVariables.InvFourPossessed);

            String carcosaSelection = CarcosaEntry.PARENT_ID + " LIKE ?";
            String[] carcosaSelectionArgs = {Long.toString(globalVariables.CampaignID)};
            db.update(
                    CarcosaEntry.TABLE_NAME,
                    carcosaValues,
                    carcosaSelection,
                    carcosaSelectionArgs);
        }

        // Update all forgotten variables
        if (globalVariables.CurrentCampaign == 4) {
            ContentValues forgottenValues = new ContentValues();
            forgottenValues.put(ForgottenEntry.COLUMN_YIGS_FURY, globalVariables.YigsFury);
            forgottenValues.put(ForgottenEntry.COLUMN_RUINS, globalVariables.Ruins);
            forgottenValues.put(ForgottenEntry.COLUMN_ICHTACA, globalVariables.Ichtaca);
            forgottenValues.put(ForgottenEntry.COLUMN_ALEJANDRO, globalVariables.Alejandro);
            forgottenValues.put(ForgottenEntry.COLUMN_LOW_RATIONS, globalVariables.LowRations);
            forgottenValues.put(ForgottenEntry.COLUMN_RELIC, globalVariables.Relic);
            forgottenValues.put(ForgottenEntry.COLUMN_HARBINGER, globalVariables.Harbinger);
            forgottenValues.put(ForgottenEntry.COLUMN_EZTLI, globalVariables.Eztli);
            forgottenValues.put(ForgottenEntry.COLUMN_CUSTODY, globalVariables.Custody);
            forgottenValues.put(ForgottenEntry.COLUMN_ICHTACAS_TALE, globalVariables.IchtacasTale);
            forgottenValues.put(ForgottenEntry.COLUMN_MISSING_RELIC, globalVariables.MissingRelic);
            forgottenValues.put(ForgottenEntry.COLUMN_MISSING_ALEJANDRO, globalVariables.MissingAlejandro);
            forgottenValues.put(ForgottenEntry.COLUMN_MISSING_ICHTACA, globalVariables.MissingIchtaca);
            forgottenValues.put(ForgottenEntry.COLUMN_GASOLINE_USED, globalVariables.GasolineUsed);
            forgottenValues.put(ForgottenEntry.COLUMN_PATHS_KNOWN, globalVariables.PathsKnown);
            forgottenValues.put(ForgottenEntry.COLUMN_ICHTACA_CONFIDENCE, globalVariables.IchtacaConfidence);

            String forgottenSelection = ForgottenEntry.PARENT_ID + " LIKE ?";
            String[] forgottenSelectionArgs = {Long.toString(globalVariables.CampaignID)};
            db.update(
                    ForgottenEntry.TABLE_NAME,
                    forgottenValues,
                    forgottenSelection,
                    forgottenSelectionArgs
            );
        }

        // Update investigator entries
        String[] selectionArgs = {Long.toString(globalVariables.CampaignID)};
        String investigatorSelection = InvestigatorEntry.PARENT_ID + " = ?";
        db.delete(InvestigatorEntry.TABLE_NAME, investigatorSelection,
                selectionArgs);
        ContentValues investigatorValues = new ContentValues();
        for (int i = 0; i < globalVariables.Investigators.size(); i++) {
            investigatorValues.put(InvestigatorEntry.PARENT_ID, globalVariables.CampaignID);
            investigatorValues.put(InvestigatorEntry.INVESTIGATOR_ID, i);
            investigatorValues.put(InvestigatorEntry.COLUMN_INVESTIGATOR_NAME, globalVariables
                    .Investigators.get(i).Name);
            investigatorValues.put(InvestigatorEntry.COLUMN_INVESTIGATOR_STATUS, globalVariables
                    .Investigators.get(i).Status);
            investigatorValues.put(InvestigatorEntry.COLUMN_INVESTIGATOR_DAMAGE, globalVariables
                    .Investigators.get(i).Damage);
            investigatorValues.put(InvestigatorEntry.COLUMN_INVESTIGATOR_HORROR, globalVariables
                    .Investigators.get(i).Horror);
            investigatorValues.put(InvestigatorEntry.COLUMN_INVESTIGATOR_TOTAL_XP, globalVariables
                    .Investigators.get(i).TotalXP);
            investigatorValues.put(InvestigatorEntry.COLUMN_INVESTIGATOR_AVAILABLE_XP, globalVariables
                    .Investigators.get(i).AvailableXP);
            investigatorValues.put(InvestigatorEntry.COLUMN_INVESTIGATOR_SPENT_XP, 0);
            investigatorValues.put(InvestigatorEntry.COLUMN_INVESTIGATOR_PLAYER, globalVariables
                    .Investigators.get(i).PlayerName);
            investigatorValues.put(InvestigatorEntry.COLUMN_INVESTIGATOR_DECKNAME, globalVariables
                    .Investigators.get(i).DeckName);
            investigatorValues.put(InvestigatorEntry.COLUMN_INVESTIGATOR_DECKLIST, globalVariables
                    .Investigators.get(i).Decklist);
            investigatorValues.put(InvestigatorEntry.COLUMN_INVESTIGATOR_PROVISIONS, globalVariables.Investigators
                    .get(i).Provisions);
            investigatorValues.put(InvestigatorEntry.COLUMN_INVESTIGATOR_MEDICINE, globalVariables.Investigators.get
                    (i).Medicine);
            investigatorValues.put(InvestigatorEntry.COLUMN_INVESTIGATOR_SUPPLIES, globalVariables.Investigators
                    .get(i).Supplies);
            investigatorValues.put(InvestigatorEntry.COLUMN_INVESTIGATOR_RESUPPLIES_ONE, globalVariables
                    .Investigators.get(i).ResuppliesOne);
            db.insert(InvestigatorEntry.TABLE_NAME, null, investigatorValues);
        }
        for (int i = 0; i < globalVariables.SavedInvestigators.size(); i++) {
            investigatorValues.put(InvestigatorEntry.PARENT_ID, globalVariables.CampaignID);
            investigatorValues.put(InvestigatorEntry.INVESTIGATOR_ID, i + 100);
            investigatorValues.put(InvestigatorEntry.COLUMN_INVESTIGATOR_NAME, globalVariables
                    .SavedInvestigators.get(i).Name);
            investigatorValues.put(InvestigatorEntry.COLUMN_INVESTIGATOR_STATUS, globalVariables
                    .SavedInvestigators.get(i).Status);
            investigatorValues.put(InvestigatorEntry.COLUMN_INVESTIGATOR_DAMAGE, globalVariables
                    .SavedInvestigators.get(i).Damage);
            investigatorValues.put(InvestigatorEntry.COLUMN_INVESTIGATOR_HORROR, globalVariables
                    .SavedInvestigators.get(i).Horror);
            investigatorValues.put(InvestigatorEntry.COLUMN_INVESTIGATOR_TOTAL_XP, globalVariables
                    .SavedInvestigators.get(i).TotalXP);
            investigatorValues.put(InvestigatorEntry.COLUMN_INVESTIGATOR_AVAILABLE_XP, globalVariables
                    .SavedInvestigators.get(i).AvailableXP);
            investigatorValues.put(InvestigatorEntry.COLUMN_INVESTIGATOR_SPENT_XP, globalVariables
                    .SavedInvestigators.get(i).SpentXP);
            investigatorValues.put(InvestigatorEntry.COLUMN_INVESTIGATOR_PLAYER, globalVariables
                    .SavedInvestigators.get(i).PlayerName);
            investigatorValues.put(InvestigatorEntry.COLUMN_INVESTIGATOR_DECKNAME, globalVariables
                    .SavedInvestigators.get(i).DeckName);
            investigatorValues.put(InvestigatorEntry.COLUMN_INVESTIGATOR_DECKLIST, globalVariables
                    .SavedInvestigators.get(i).Decklist);
            investigatorValues.put(InvestigatorEntry.COLUMN_INVESTIGATOR_PROVISIONS, globalVariables.SavedInvestigators
                    .get(i).Provisions);
            investigatorValues.put(InvestigatorEntry.COLUMN_INVESTIGATOR_MEDICINE, globalVariables.SavedInvestigators
                    .get(i).Medicine);
            investigatorValues.put(InvestigatorEntry.COLUMN_INVESTIGATOR_SUPPLIES, globalVariables.SavedInvestigators
                    .get(i).Supplies);
            investigatorValues.put(InvestigatorEntry.COLUMN_INVESTIGATOR_RESUPPLIES_ONE, globalVariables
                    .SavedInvestigators.get(i).ResuppliesOne);
            db.insert(InvestigatorEntry.TABLE_NAME, null, investigatorValues);
        }
    }

    public static class PlayerCardsDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Get the layout inflater and inflate the view
            View v = View.inflate(getActivity(), R.layout.c_dialog_player_cards, null);

            Typeface arnopro = Typeface.createFromAsset(getActivity().getAssets(), "fonts/arnopro.otf");
            Typeface arnoprobold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/arnoprobold.otf");
            Typeface wolgastbold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/wolgastbold.otf");
            final CheckBox solution = v.findViewById(R.id.strange_solution);
            final CheckBox glyphs = v.findViewById(R.id.archaic_glyphs);
            final CheckBox obol = v.findViewById(R.id.charons_obol);
            final CheckBox stone = v.findViewById(R.id.ancient_stone);
            solution.setTypeface(arnopro);
            glyphs.setTypeface(arnopro);
            obol.setTypeface(arnopro);
            stone.setTypeface(arnopro);

            Typeface teutonic = Typeface.createFromAsset(getActivity().getAssets(), "fonts/teutonic.ttf");
            TextView title = v.findViewById(R.id.player_cards);
            Button cancelButton = v.findViewById(R.id.cancel_button);
            Button okayButton = v.findViewById(R.id.okay_button);
            title.setTypeface(teutonic);
            cancelButton.setTypeface(teutonic);
            okayButton.setTypeface(teutonic);

            final LinearLayout selectInvestigator = v.findViewById(R.id.select_investigator);
            final CheckBox selectInvestigatorOne = v.findViewById(R.id.select_investigator_one);
            final CheckBox selectInvestigatorTwo = v.findViewById(R.id.select_investigator_two);
            final CheckBox selectInvestigatorThree = v.findViewById(R.id.select_investigator_three);
            final CheckBox selectInvestigatorFour = v.findViewById(R.id.select_investigator_four);
            selectInvestigatorOne.setTypeface(arnopro);
            selectInvestigatorTwo.setTypeface(arnopro);
            selectInvestigatorThree.setTypeface(arnopro);
            selectInvestigatorFour.setTypeface(arnopro);
            String[] investigatorNames = getResources().getStringArray(investigators);
            switch (globalVariables.Investigators.size()) {
                case 4:
                    selectInvestigatorFour.setVisibility(VISIBLE);
                    selectInvestigatorFour.setText(investigatorNames[globalVariables
                            .Investigators.get(3)
                            .Name]);
                case 3:
                    selectInvestigatorThree.setVisibility(VISIBLE);
                    selectInvestigatorThree.setText(investigatorNames[globalVariables
                            .Investigators.get
                                    (2).Name]);
                case 2:
                    selectInvestigatorTwo.setVisibility(VISIBLE);
                    selectInvestigatorTwo.setText(investigatorNames[globalVariables
                            .Investigators.get(1)
                            .Name]);
                case 1:
                    selectInvestigatorOne.setVisibility(VISIBLE);
                    selectInvestigatorOne.setText(investigatorNames[globalVariables
                            .Investigators.get(0)
                            .Name]);
                    break;
            }

            obol.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (compoundButton.isChecked()) {
                        selectInvestigator.setVisibility(VISIBLE);
                    } else {
                        selectInvestigator.setVisibility(GONE);
                    }
                }
            });

            final LinearLayout stoneCounter = v.findViewById(R.id.ancient_stone_counter);
            TextView stoneCounterDifficulty = v.findViewById(R.id.test_difficulty);
            stoneCounterDifficulty.setTypeface(arnoprobold);
            ImageView testDecrement = v.findViewById(R.id.test_decrement);
            final TextView testAmount = v.findViewById(R.id.test_amount);
            testAmount.setTypeface(wolgastbold);
            if (globalVariables.AncientStone > 0) {
                testAmount.setText(String.valueOf(globalVariables.AncientStone));
            }
            ImageView testIncrement = v.findViewById(R.id.test_increment);
            testDecrement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int current = Integer.valueOf(testAmount.getText().toString());
                    if (current > 0) {
                        current += -1;
                        testAmount.setText(String.valueOf(current));
                    }
                }
            });
            testIncrement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int current = Integer.valueOf(testAmount.getText().toString());
                    if (current < 99) {
                        current += 1;
                        testAmount.setText(String.valueOf(current));
                    }
                }
            });

            stone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (compoundButton.isChecked()) {
                        stoneCounter.setVisibility(VISIBLE);
                    } else {
                        stoneCounter.setVisibility(GONE);
                    }
                }
            });

            if (globalVariables.StrangeSolution == 1) {
                solution.setChecked(true);
            }
            if (globalVariables.ArchaicGlyphs == 1) {
                glyphs.setChecked(true);
            }
            if (globalVariables.CharonsObol > 0) {
                obol.setChecked(true);
                selectInvestigator.setVisibility(VISIBLE);
                if (globalVariables.CharonsObol % 2 == 0) {
                    selectInvestigatorOne.setChecked(true);
                }
                if (globalVariables.CharonsObol % 3 == 0) {
                    selectInvestigatorTwo.setChecked(true);
                }
                if (globalVariables.CharonsObol % 5 == 0) {
                    selectInvestigatorThree.setChecked(true);
                }
                if (globalVariables.CharonsObol % 7 == 0) {
                    selectInvestigatorFour.setChecked(true);
                }
            }
            if (globalVariables.AncientStone > 0) {
                stone.setChecked(true);
                stoneCounter.setVisibility(VISIBLE);
            }

            okayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (solution.isChecked()) {
                        globalVariables.StrangeSolution = 1;
                    } else {
                        globalVariables.StrangeSolution = 0;
                    }
                    if (glyphs.isChecked()) {
                        globalVariables.ArchaicGlyphs = 1;
                    } else {
                        globalVariables.ArchaicGlyphs = 0;
                    }
                    if (obol.isChecked()) {
                        globalVariables.CharonsObol = 1;
                        if (selectInvestigatorOne.isChecked()) {
                            globalVariables.CharonsObol = globalVariables.CharonsObol * 2;
                        }
                        if (selectInvestigatorTwo.isChecked()) {
                            globalVariables.CharonsObol = globalVariables.CharonsObol * 3;
                        }
                        if (selectInvestigatorThree.isChecked()) {
                            globalVariables.CharonsObol = globalVariables.CharonsObol * 5;
                        }
                        if (selectInvestigatorFour.isChecked()) {
                            globalVariables.CharonsObol = globalVariables.CharonsObol * 7;
                        }
                    } else {
                        globalVariables.CharonsObol = 0;
                    }
                    if (stone.isChecked()) {
                        globalVariables.AncientStone = Integer.valueOf(testAmount.getText().toString());
                    } else {
                        globalVariables.AncientStone = 0;
                    }
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

    public static class WeaknessDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Get the layout inflater and inflate the view
            View v = View.inflate(getActivity(), R.layout.c_dialog_weaknesses, null);

            Typeface arnopro = Typeface.createFromAsset(getActivity().getAssets(), "fonts/arnopro.otf");

            final CheckBox doomed = v.findViewById(R.id.doomed);
            final CheckBox accursed = v.findViewById(R.id.accursed_fate);
            final CheckBox bell = v.findViewById(R.id.bell_tolls);
            doomed.setTypeface(arnopro);
            accursed.setTypeface(arnopro);
            bell.setTypeface(arnopro);

            final LinearLayout selectInvestigator = v.findViewById(R.id.select_investigator);
            final CheckBox selectInvestigatorOne = v.findViewById(R.id.select_investigator_one);
            final CheckBox selectInvestigatorTwo = v.findViewById(R.id.select_investigator_two);
            final CheckBox selectInvestigatorThree = v.findViewById(R.id.select_investigator_three);
            final CheckBox selectInvestigatorFour = v.findViewById(R.id.select_investigator_four);
            selectInvestigatorOne.setTypeface(arnopro);
            selectInvestigatorTwo.setTypeface(arnopro);
            selectInvestigatorThree.setTypeface(arnopro);
            selectInvestigatorFour.setTypeface(arnopro);

            doomed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (compoundButton.isChecked()) {
                        accursed.setVisibility(VISIBLE);
                    } else {
                        accursed.setVisibility(GONE);
                        bell.setVisibility(GONE);
                        selectInvestigator.setVisibility(GONE);
                    }
                }
            });
            accursed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (compoundButton.isChecked()) {
                        bell.setVisibility(VISIBLE);
                    } else {
                        bell.setVisibility(GONE);
                        selectInvestigator.setVisibility(GONE);
                    }
                }
            });
            bell.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (compoundButton.isChecked()) {
                        selectInvestigator.setVisibility(VISIBLE);
                    } else {
                        selectInvestigator.setVisibility(GONE);
                    }
                }
            });

            String[] investigatorNames = getResources().getStringArray(investigators);
            switch (globalVariables.Investigators.size()) {
                case 4:
                    selectInvestigatorFour.setVisibility(VISIBLE);
                    selectInvestigatorFour.setText(investigatorNames[globalVariables
                            .Investigators.get(3)
                            .Name]);
                    selectInvestigatorFour.setOnCheckedChangeListener(new CompoundButton
                            .OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            if (compoundButton.isChecked()) {
                                selectInvestigatorThree.setChecked(false);
                                selectInvestigatorTwo.setChecked(false);
                                selectInvestigatorOne.setChecked(false);
                            }
                        }
                    });
                case 3:
                    selectInvestigatorThree.setVisibility(VISIBLE);
                    selectInvestigatorThree.setText(investigatorNames[globalVariables
                            .Investigators.get
                                    (2).Name]);
                    selectInvestigatorThree.setOnCheckedChangeListener(new CompoundButton
                            .OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            if (compoundButton.isChecked()) {
                                selectInvestigatorFour.setChecked(false);
                                selectInvestigatorTwo.setChecked(false);
                                selectInvestigatorOne.setChecked(false);
                            }
                        }
                    });
                case 2:
                    selectInvestigatorTwo.setVisibility(VISIBLE);
                    selectInvestigatorTwo.setText(investigatorNames[globalVariables
                            .Investigators.get(1)
                            .Name]);
                    selectInvestigatorTwo.setOnCheckedChangeListener(new CompoundButton
                            .OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            if (compoundButton.isChecked()) {
                                selectInvestigatorFour.setChecked(false);
                                selectInvestigatorThree.setChecked(false);
                                selectInvestigatorOne.setChecked(false);
                            }
                        }
                    });
                case 1:
                    selectInvestigatorOne.setVisibility(VISIBLE);
                    selectInvestigatorOne.setText(investigatorNames[globalVariables
                            .Investigators.get(0)
                            .Name]);
                    selectInvestigatorOne.setOnCheckedChangeListener(new CompoundButton
                            .OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            if (compoundButton.isChecked()) {
                                selectInvestigatorFour.setChecked(false);
                                selectInvestigatorThree.setChecked(false);
                                selectInvestigatorTwo.setChecked(false);
                            }
                        }
                    });
            }

            if (globalVariables.Doomed > 0) {
                doomed.setChecked(true);
            }
            if (globalVariables.Doomed > 1) {
                accursed.setChecked(true);
            }

            final CheckBox investigatorOne = v.findViewById(R.id.investigator_one_weakness);
            final CheckBox investigatorTwo = v.findViewById(R.id.investigator_two_weakness);
            final CheckBox investigatorThree = v.findViewById(R.id.investigator_three_weakness);
            final CheckBox investigatorFour = v.findViewById(R.id.investigator_four_weakness);
            investigatorOne.setTypeface(arnopro);
            investigatorTwo.setTypeface(arnopro);
            investigatorThree.setTypeface(arnopro);
            investigatorFour.setTypeface(arnopro);

            Typeface arnoprobold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/arnoprobold.otf");
            Typeface teutonic = Typeface.createFromAsset(getActivity().getAssets(), "fonts/teutonic.ttf");
            Typeface wolgastbold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/wolgastbold.otf");
            TextView title = v.findViewById(R.id.investigator_weaknesses);
            Button cancelButton = v.findViewById(R.id.cancel_button);
            Button okayButton = v.findViewById(R.id.okay_button);
            title.setTypeface(teutonic);
            cancelButton.setTypeface(teutonic);
            okayButton.setTypeface(teutonic);

            switch (globalVariables.Investigators.size()) {
                case 4:
                    if (globalVariables.Investigators.get(3).TempWeakness == 1) {
                        investigatorFour.setChecked(true);
                    }
                case 3:
                    if (globalVariables.Investigators.get(2).TempWeakness == 1) {
                        investigatorThree.setChecked(true);
                    }
                case 2:
                    if (globalVariables.Investigators.get(1).TempWeakness == 1) {
                        investigatorTwo.setChecked(true);
                    }
                case 1:
                    if (globalVariables.Investigators.get(0).TempWeakness == 1) {
                        investigatorOne.setChecked(true);
                    }
                    break;
            }

            for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                CheckBox box = investigatorOne;
                switch (i) {
                    case 0:
                        box = investigatorOne;
                        break;
                    case 1:
                        box = investigatorTwo;
                        break;
                    case 2:
                        box = investigatorThree;
                        break;
                    case 3:
                        box = investigatorFour;
                        break;
                }
                switch (globalVariables.Investigators.get(i).Name) {
                    case Investigator.ROLAND_BANKS:
                        box.setVisibility(VISIBLE);
                        box.setText(R.string.cover_up);
                        break;
                    case Investigator.SKIDS_OTOOLE:
                        box.setVisibility(VISIBLE);
                        box.setText(R.string.hospital_debts);
                        break;
                    case Investigator.ZOEY_SAMARAS:
                        box.setVisibility(VISIBLE);
                        box.setText(R.string.smite_the_wicked);
                        break;
                    case Investigator.JENNY_BARNES:
                        box.setVisibility(VISIBLE);
                        box.setText(R.string.searching_for_izzie);
                        break;
                    case Investigator.AKACHI_ONYELE:
                        box.setVisibility(VISIBLE);
                        box.setText(R.string.angered_spirits);
                        break;
                    case Investigator.JOE_DIAMOND:
                        box.setVisibility(VISIBLE);
                        box.setText(R.string.unsolved_case);
                        break;
                    case Investigator.PRESTON_FAIRMONT:
                        box.setVisibility(VISIBLE);
                        box.setText(R.string.lodge_debts);
                        break;
                    case Investigator.CALVIN_WRIGHT:
                        box.setVisibility(VISIBLE);
                        box.setText(R.string.voice_of_the_messenger);
                        final int invNumber = i;
                        final LinearLayout calvin = v.findViewById(R.id.calvin_weakness);
                        TextView calvinHeading = v.findViewById(R.id.calvin_weakness_heading);
                        calvinHeading.setTypeface(arnoprobold);

                        if (box.isChecked()) {
                            calvin.setVisibility(VISIBLE);
                        } else {
                            calvin.setVisibility(GONE);
                        }
                        box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (compoundButton.isChecked()) {
                                    calvin.setVisibility(VISIBLE);
                                    globalVariables.Investigators.get(invNumber).TempWeaknessOne = 0;
                                    globalVariables.Investigators.get(invNumber).TempWeaknessTwo = 0;
                                } else {
                                    calvin.setVisibility(GONE);
                                }
                            }
                        });

                        TextView physicalTrauma = v.findViewById(R.id.physical_trauma);
                        physicalTrauma.setTypeface(arnoprobold);
                        ImageView physicalDecrement = v.findViewById(R.id.physical_decrement);
                        final TextView physicalAmount = v.findViewById(R.id.physical_amount);
                        physicalAmount.setTypeface(wolgastbold);
                        physicalAmount.setText(String.valueOf(globalVariables.Investigators.get(invNumber)
                                .TempWeaknessOne));
                        ImageView physicalIncrement = v.findViewById(R.id.physical_increment);
                        physicalDecrement.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int current = globalVariables.Investigators.get(invNumber).TempWeaknessOne;
                                if (current > 0) {
                                    current += -1;
                                    physicalAmount.setText(String.valueOf(current));
                                }
                                globalVariables.Investigators.get(invNumber).TempWeaknessOne = current;
                            }
                        });
                        physicalIncrement.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int current = globalVariables.Investigators.get(invNumber).TempWeaknessOne;
                                if (current < 99) {
                                    current += 1;
                                    physicalAmount.setText(String.valueOf(current));
                                }
                                globalVariables.Investigators.get(invNumber).TempWeaknessOne = current;
                            }
                        });

                        TextView mentalTrauma = v.findViewById(R.id.mental_trauma);
                        mentalTrauma.setTypeface(arnoprobold);
                        ImageView mentalDecrement = v.findViewById(R.id.mental_decrement);
                        final TextView mentalAmount = v.findViewById(R.id.mental_amount);
                        mentalAmount.setTypeface(wolgastbold);
                        mentalAmount.setText(String.valueOf(globalVariables.Investigators.get(invNumber)
                                .TempWeaknessTwo));
                        ImageView mentalIncrement = v.findViewById(R.id.mental_increment);
                        mentalDecrement.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int current = globalVariables.Investigators.get(invNumber).TempWeaknessTwo;
                                if (current > 0) {
                                    current += -1;
                                    mentalAmount.setText(String.valueOf(current));
                                }
                                globalVariables.Investigators.get(invNumber).TempWeaknessTwo = current;
                            }
                        });
                        mentalIncrement.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int current = globalVariables.Investigators.get(invNumber).TempWeaknessTwo;
                                if (current < 99) {
                                    current += 1;
                                    mentalAmount.setText(String.valueOf(current));
                                }
                                globalVariables.Investigators.get(invNumber).TempWeaknessTwo = current;
                            }
                        });
                        break;
                }
            }


            okayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (doomed.isChecked()) {
                        globalVariables.Doomed = 1;
                    }
                    if (accursed.isChecked()) {
                        globalVariables.Doomed = 2;
                    }
                    if (bell.isChecked()) {
                        if (selectInvestigatorOne.isChecked()) {
                            globalVariables.Doomed = 3;
                        } else if (selectInvestigatorTwo.isChecked()) {
                            globalVariables.Doomed = 4;
                        } else if (selectInvestigatorThree.isChecked()) {
                            globalVariables.Doomed = 5;
                        } else if (selectInvestigatorFour.isChecked()) {
                            globalVariables.Doomed = 6;
                        }
                    }

                    switch (globalVariables.Investigators.size()) {
                        case 4:
                            if (investigatorFour.isChecked()) {
                                globalVariables.Investigators.get(3).TempWeakness = 1;
                            } else {
                                globalVariables.Investigators.get(3).TempWeakness = 0;
                            }
                        case 3:
                            if (investigatorThree.isChecked()) {
                                globalVariables.Investigators.get(2).TempWeakness = 1;
                            } else {
                                globalVariables.Investigators.get(2).TempWeakness = 0;
                            }
                        case 2:
                            if (investigatorTwo.isChecked()) {
                                globalVariables.Investigators.get(1).TempWeakness = 1;
                            } else {
                                globalVariables.Investigators.get(1).TempWeakness = 0;
                            }
                        case 1:
                            if (investigatorOne.isChecked()) {
                                globalVariables.Investigators.get(0).TempWeakness = 1;
                            } else {
                                globalVariables.Investigators.get(0).TempWeakness = 0;
                            }
                            break;
                    }
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