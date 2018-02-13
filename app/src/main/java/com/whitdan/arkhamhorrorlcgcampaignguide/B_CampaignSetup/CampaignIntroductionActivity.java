package com.whitdan.arkhamhorrorlcgcampaignguide.B_CampaignSetup;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.whitdan.arkhamhorrorlcgcampaignguide.A_Menus.MainMenuActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.C_Scenario.ScenarioInterludeActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.C_Scenario.ScenarioMainActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.C_Scenario.ScenarioResolutionActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.R;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.ArkhamContract;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.ArkhamDbHelper;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.GlobalVariables;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/*
    CampaignIntroductionActivity - Displays the introductory text for the campaign
 */

public class CampaignIntroductionActivity extends AppCompatActivity {

    static GlobalVariables globalVariables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // If app is reopening after the process is killed, kick back to the main menu (stops the activity from
        // showing up unpopulated)
        if (savedInstanceState != null) {
            Intent intent = new Intent(CampaignIntroductionActivity.this, MainMenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_activity_campaign_introduction);
        globalVariables = (GlobalVariables) this.getApplication();

        // Set campaign title and introduction text and fonts
        TextView title = findViewById(R.id.campaign_name);
        Typeface teutonic = Typeface.createFromAsset(getAssets(), "fonts/teutonic.ttf");
        title.setTypeface(teutonic);
        TextView introduction = findViewById(R.id.introduction_text);
        Typeface arnoproitalic = Typeface.createFromAsset(getAssets(), "fonts/arnoproitalic.otf");
        introduction.setTypeface(arnoproitalic);
        switch (globalVariables.CurrentCampaign) {
            case 1:
                title.setText(R.string.night_campaign);
                introduction.setText(R.string.night_introduction);
                break;
            case 2:
                title.setText(R.string.dunwich_campaign);
                introduction.setText(R.string.dunwich_introduction);
                break;
            case 3:
                title.setText(R.string.carcosa_campaign);
                introduction.setText(R.string.carcosa_introduction);
                break;
        }

        // Back button
        Button backButton = findViewById(R.id.back_button);
        backButton.setTypeface(teutonic);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Continue button
        Button continueButton = findViewById(R.id.continue_button);
        continueButton.setTypeface(teutonic);
        if (globalVariables.NightCompleted == 1 || globalVariables.DunwichCompleted == 1) {
            continueButton.setText(R.string.next_scenario);
            continueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (globalVariables.CurrentCampaign == 2) {
                        DialogFragment startCampaign = new StartCampaignDialog();
                        startCampaign.show(getFragmentManager(), "campaign");
                    } else if (globalVariables.CurrentScenario == 0) {
                        SetupCampaign(CampaignIntroductionActivity.this);
                        Intent intent = new Intent(CampaignIntroductionActivity.this, ScenarioInterludeActivity.class);
                        startActivity(intent);
                    } else {
                        SetupCampaign(CampaignIntroductionActivity.this);
                        Intent intent = new Intent(CampaignIntroductionActivity.this, ScenarioMainActivity.class);
                        startActivity(intent);
                    }
                }
            });
        } else {
            continueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(CampaignIntroductionActivity.this, CampaignInvestigatorsActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    // Dialog box for confirming the start of a campaign
    public static class StartCampaignDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Get the layout inflater and inflate the view
            View v = View.inflate(getActivity(), R.layout.b_dialog_start_campaign, null);

            // Find views and set fonts
            Typeface arnoprobold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/arnoprobold.otf");
            Typeface teutonic = Typeface.createFromAsset(getActivity().getAssets(), "fonts/teutonic.ttf");
            Typeface arnopro = Typeface.createFromAsset(getActivity().getAssets(), "fonts/arnopro.otf");
            Button cancelButton = v.findViewById(R.id.cancel_button);
            Button okayButton = v.findViewById(R.id.okay_button);
            TextView confirm = v.findViewById(R.id.confirm_start_campaign);
            cancelButton.setTypeface(teutonic);
            okayButton.setTypeface(teutonic);
            confirm.setTypeface(arnoprobold);

            // Set campaign title
            TextView campaignName = v.findViewById(R.id.campaign_name);
            campaignName.setTypeface(teutonic);
            campaignName.setAllCaps(true);
            campaignName.setTextScaleX((float) 1.2);
            campaignName.setText(R.string.dunwich_campaign);

            /*
                Show the right views for the number of investigators and set the right font to the name
             */
            LinearLayout investigatorOne = v.findViewById(R.id.investigator_one);
            LinearLayout investigatorTwo = v.findViewById(R.id.investigator_two);
            LinearLayout investigatorThree = v.findViewById(R.id.investigator_three);
            LinearLayout investigatorFour = v.findViewById(R.id.investigator_four);
            ImageView lineOne = v.findViewById(R.id.line_one);
            ImageView lineTwo = v.findViewById(R.id.line_two);
            investigatorOne.setVisibility(GONE);
            investigatorTwo.setVisibility(GONE);
            investigatorThree.setVisibility(GONE);
            investigatorFour.setVisibility(GONE);
            lineOne.setVisibility(GONE);
            lineTwo.setVisibility(GONE);

            /*
                Show options for which scenario to start with for the Dunwich Legacy campaign
             */
            if (globalVariables.CurrentCampaign == 2) {
                globalVariables.FirstScenario = 0;
                RadioGroup options = v.findViewById(R.id.start_campaign_options);
                RadioButton optionOne = v.findViewById(R.id.start_campaign_option_one);
                RadioButton optionTwo = v.findViewById(R.id.start_campaign_option_two);
                options.setVisibility(VISIBLE);
                optionOne.setText(R.string.dunwich_start_option_one);
                optionOne.setTypeface(arnopro);
                optionTwo.setText(R.string.dunwich_start_option_two);
                optionTwo.setTypeface(arnopro);
                options.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                        switch (i) {
                            case R.id.start_campaign_option_one:
                                globalVariables.FirstScenario = 1;
                                break;
                            case R.id.start_campaign_option_two:
                                globalVariables.FirstScenario = 2;
                                break;
                        }
                    }
                });
            }


            okayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // If on Dunwich and no option has been selected, show a toast
                    if (globalVariables.CurrentCampaign == 2 && globalVariables.FirstScenario == 0) {
                        Toast toast = Toast.makeText(getActivity(), R.string.must_option, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    // Save and start the campaign
                    else {
                        // Set current scenario to first scenario
                        if (globalVariables.CurrentCampaign == 2) {
                            globalVariables.CurrentScenario = globalVariables.FirstScenario;
                        } else {
                            globalVariables.CurrentScenario = 1;
                        }

                        SetupCampaign(getActivity());
                        ScenarioResolutionActivity.saveCampaign(getActivity(), globalVariables);

                        // Go to scenario setup
                        Intent intent = new Intent(getActivity(), ScenarioMainActivity
                                .class);
                        startActivity(intent);
                        dismiss();
                    }
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

    private static void SetupCampaign(Activity activity) {
        // Get a writable database
        ArkhamDbHelper dbHelper = new ArkhamDbHelper(activity);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Create campaign specific table
        int currentCampaign = globalVariables.CurrentCampaign;
        long newCampaignId = globalVariables.CampaignID;
        switch (currentCampaign) {
            // Night of the Zealot
            case 1:
                ContentValues nightValues = new ContentValues();
                nightValues.put(ArkhamContract.NightEntry.PARENT_ID, newCampaignId);
                db.insert(ArkhamContract.NightEntry.TABLE_NAME, null, nightValues);
                break;
            // The Dunwich Legacy
            case 2:
                ContentValues dunwichValues = new ContentValues();
                dunwichValues.put(ArkhamContract.DunwichEntry.PARENT_ID, newCampaignId);
                dunwichValues.put(ArkhamContract.DunwichEntry.COLUMN_FIRST_SCENARIO,
                        globalVariables.FirstScenario);
                db.insert(ArkhamContract.DunwichEntry.TABLE_NAME, null, dunwichValues);
                break;
            // Path to Carcosa
            case 3:
                ContentValues carcosaValues = new ContentValues();
                carcosaValues.put(ArkhamContract.CarcosaEntry.PARENT_ID, newCampaignId);
                carcosaValues.put(ArkhamContract.CarcosaEntry.COLUMN_DREAMS, 0);
                db.insert(ArkhamContract.CarcosaEntry.TABLE_NAME, null, carcosaValues);
                break;
        }
    }
}
