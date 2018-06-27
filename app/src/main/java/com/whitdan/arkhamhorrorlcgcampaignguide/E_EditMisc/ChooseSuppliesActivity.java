package com.whitdan.arkhamhorrorlcgcampaignguide.E_EditMisc;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.whitdan.arkhamhorrorlcgcampaignguide.A_Menus.MainMenuActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.B_CampaignSetup.CampaignInvestigatorsActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.C_Scenario.ScenarioMainActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.R;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.GlobalVariables;

import static android.view.View.GONE;

public class ChooseSuppliesActivity extends AppCompatActivity {

    static GlobalVariables globalVariables;
    int[] availableSupplies;
    int[] provisions;
    int[] medicine;
    int[] suppliesChosen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // If app is reopening after the process is killed, kick back to the main menu (stops the activity from
        // showing up unpopulated)
        if (savedInstanceState != null) {
            Intent intent = new Intent(ChooseSuppliesActivity.this, MainMenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.e_activity_choose_supplies);
        globalVariables = (GlobalVariables) this.getApplication();
        provisions = new int[]{0, 0, 0, 0};
        medicine = new int[]{0, 0, 0, 0};
        suppliesChosen = new int[]{1, 1, 1, 1};
        availableSupplies = new int[]{0, 0, 0, 0};

        // Set campaign title
        TextView title = findViewById(R.id.campaign_name);
        switch (globalVariables.CurrentCampaign) {
            case 1:
                title.setText(R.string.night_campaign);
                break;
            case 2:
                title.setText(R.string.dunwich_campaign);
                break;
            case 3:
                title.setText(R.string.carcosa_campaign);
                break;
            case 4:
                title.setText(R.string.forgotten_campaign);
                break;
        }

        // Set fonts to relevant elements
        Typeface teutonic = Typeface.createFromAsset(getAssets(), "fonts/teutonic.ttf");
        Typeface arnopro = Typeface.createFromAsset(getAssets(), "fonts/arnopro.otf");
        Typeface arnoprobold = Typeface.createFromAsset(getAssets(), "fonts/arnoprobold.otf");
        Typeface wolgast = Typeface.createFromAsset(getAssets(), "fonts/wolgast.otf");
        Typeface wolgastbold = Typeface.createFromAsset(getAssets(), "fonts/wolgastbold.otf");
        title.setTypeface(teutonic);
        TextView choose = findViewById(R.id.choose_supplies);
        choose.setTypeface(teutonic);

        // Setup then add selection layout for each investigator
        LinearLayout suppliesLayout = findViewById(R.id.supplies_layout);
        for (int i = 0; i < globalVariables.Investigators.size(); i++) {
            View selectSupplies = View.inflate(this, R.layout.e_item_choose_supplies, null);
            final int currentInvestigator = i;
            globalVariables.Investigators.get(i).Supplies = 1;

            // Investigator name
            TextView name = selectSupplies.findViewById(R.id.investigator_name);
            name.setTypeface(teutonic);
            String[] investigatorNames = getResources().getStringArray(R.array.investigators);
            String invName = investigatorNames[globalVariables.Investigators.get(i).Name] + " ";
            name.setText(invName);

            // Available supply points
            final TextView supplyPoints = selectSupplies.findViewById(R.id.supply_points);
            supplyPoints.setTypeface(arnoprobold);
            int points = 0;
            switch (globalVariables.Investigators.size()) {
                case 1:
                    points = 10;
                    break;
                case 2:
                    points = 7;
                    break;
                case 3:
                    points = 5;
                    break;
                case 4:
                    points = 4;
                    break;
            }
            String ptsAvailable = Integer.toString(points) + " " + getResources().getString(R.string.points_available);
            supplyPoints.setText(ptsAvailable);
            globalVariables.Investigators.get(i).SupplyPoints = points;

            // Provisions
            TextView provisions = selectSupplies.findViewById(R.id.provisions);
            provisions.setTypeface(arnopro);
            final TextView provisionsAmount = selectSupplies.findViewById(R.id.provisions_amount);
            provisionsAmount.setTypeface(wolgastbold);
            provisionsAmount.setText("0");
            ImageView provisionsDecrement = selectSupplies.findViewById(R.id.provisions_decrement);
            ImageView provisionsIncrement = selectSupplies.findViewById(R.id.provisions_increment);
            provisionsDecrement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int amount = Integer.valueOf(provisionsAmount.getText().toString());
                    if (amount > 0) {
                        amount--;
                        globalVariables.Investigators.get(currentInvestigator).Provisions = amount;
                        globalVariables.Investigators.get(currentInvestigator).SupplyPoints += 1;
                        provisionsAmount.setText(Integer.toString(amount));
                        int currentPoints = globalVariables.Investigators.get(currentInvestigator).SupplyPoints;
                        String ptsAvailable = Integer.toString(currentPoints) + " " + getResources().getString(R.string
                                .points_available);
                        supplyPoints.setText(ptsAvailable);
                    }
                }
            });
            provisionsIncrement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int amount = Integer.valueOf(provisionsAmount.getText().toString());
                    if (globalVariables.Investigators.get(currentInvestigator).SupplyPoints >= 1) {
                        amount++;
                        globalVariables.Investigators.get(currentInvestigator).Provisions = amount;
                        globalVariables.Investigators.get(currentInvestigator).SupplyPoints += -1;
                        provisionsAmount.setText(Integer.toString(amount));
                        int currentPoints = globalVariables.Investigators.get(currentInvestigator).SupplyPoints;
                        String ptsAvailable = Integer.toString(currentPoints) + " " + getResources().getString(R.string
                                .points_available);
                        supplyPoints.setText(ptsAvailable);
                    }
                }
            });

            // Medicine
            TextView medicine = selectSupplies.findViewById(R.id.medicine);
            medicine.setTypeface(arnopro);
            final TextView medicineAmount = selectSupplies.findViewById(R.id.medicine_amount);
            medicineAmount.setTypeface(wolgastbold);
            medicineAmount.setText("0");
            ImageView medicineDecrement = selectSupplies.findViewById(R.id.medicine_decrement);
            ImageView medicineIncrement = selectSupplies.findViewById(R.id.medicine_increment);
            medicineDecrement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int amount = Integer.valueOf(medicineAmount.getText().toString());
                    if (amount > 0) {
                        amount--;
                        globalVariables.Investigators.get(currentInvestigator).Medicine = amount;
                        globalVariables.Investigators.get(currentInvestigator).SupplyPoints += 2;
                        medicineAmount.setText(Integer.toString(amount));
                        int currentPoints = globalVariables.Investigators.get(currentInvestigator).SupplyPoints;
                        String ptsAvailable = Integer.toString(currentPoints) + " " + getResources().getString(R.string
                                .points_available);
                        supplyPoints.setText(ptsAvailable);
                    }
                }
            });
            medicineIncrement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int amount = Integer.valueOf(medicineAmount.getText().toString());
                    if (globalVariables.Investigators.get(currentInvestigator).SupplyPoints >= 2) {
                        amount++;
                        globalVariables.Investigators.get(currentInvestigator).Medicine = amount;
                        globalVariables.Investigators.get(currentInvestigator).SupplyPoints += -2;
                        medicineAmount.setText(Integer.toString(amount));
                        int currentPoints = globalVariables.Investigators.get(currentInvestigator).SupplyPoints;
                        String ptsAvailable = Integer.toString(currentPoints) + " " + getResources().getString(R.string
                                .points_available);
                        supplyPoints.setText(ptsAvailable);
                    }
                }
            });

            // Checkboxes
            CheckBox rope = selectSupplies.findViewById(R.id.rope);
            CheckBox blanket = selectSupplies.findViewById(R.id.blanket);
            CheckBox canteen = selectSupplies.findViewById(R.id.canteen);
            CheckBox torches = selectSupplies.findViewById(R.id.torches);
            CheckBox compass = selectSupplies.findViewById(R.id.compass);
            CheckBox map = selectSupplies.findViewById(R.id.map);
            CheckBox binoculars = selectSupplies.findViewById(R.id.binoculars);
            CheckBox chalk = selectSupplies.findViewById(R.id.chalk);
            CheckBox pendant = selectSupplies.findViewById(R.id.pendant);
            rope.setTypeface(arnopro);
            blanket.setTypeface(arnopro);
            canteen.setTypeface(arnopro);
            torches.setTypeface(arnopro);
            compass.setTypeface(arnopro);
            map.setTypeface(arnopro);
            binoculars.setTypeface(arnopro);
            chalk.setTypeface(arnopro);
            pendant.setTypeface(arnopro);
            rope.setOnCheckedChangeListener(new SuppliesCheckboxListener(i, supplyPoints));
            blanket.setOnCheckedChangeListener(new SuppliesCheckboxListener(i, supplyPoints));
            canteen.setOnCheckedChangeListener(new SuppliesCheckboxListener(i, supplyPoints));
            torches.setOnCheckedChangeListener(new SuppliesCheckboxListener(i, supplyPoints));
            compass.setOnCheckedChangeListener(new SuppliesCheckboxListener(i, supplyPoints));
            map.setOnCheckedChangeListener(new SuppliesCheckboxListener(i, supplyPoints));
            binoculars.setOnCheckedChangeListener(new SuppliesCheckboxListener(i, supplyPoints));
            chalk.setOnCheckedChangeListener(new SuppliesCheckboxListener(i, supplyPoints));
            pendant.setOnCheckedChangeListener(new SuppliesCheckboxListener(i, supplyPoints));

            suppliesLayout.addView(selectSupplies);
        }

        // Back button
        Button backButton = findViewById(R.id.back_button);
        backButton.setTypeface(teutonic);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(ChooseSuppliesActivity.this, CampaignInvestigatorsActivity.class);
                startActivity(intent);
            }
        });

        // Continue button
        Button continueButton = findViewById(R.id.continue_button);
        continueButton.setTypeface(teutonic);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment startCampaign = new StartCampaignDialog();
                Bundle bundle = new Bundle();
                bundle.putString("campaign", globalVariables.CampaignName);
                startCampaign.setArguments(bundle);
                startCampaign.show(getFragmentManager(), "campaign");
            }
        });
    }

    // OnCheckedChangeListener for supplies (except provisions and medicine)
    private class SuppliesCheckboxListener implements CompoundButton.OnCheckedChangeListener {
        private int currentInvestigator;
        TextView supplyPoints;

        public SuppliesCheckboxListener(int current, TextView supply) {
            currentInvestigator = current;
            supplyPoints = supply;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int points = -1;
            int array = -1;
            switch (buttonView.getId()) {
                case R.id.rope:
                    points = 3;
                    array = 2;
                    break;
                case R.id.blanket:
                    points = 2;
                    array = 3;
                    break;
                case R.id.canteen:
                    points = 2;
                    array = 5;
                    break;
                case R.id.torches:
                    points = 3;
                    array = 7;
                    break;
                case R.id.compass:
                    points = 2;
                    array = 11;
                    break;
                case R.id.map:
                    points = 3;
                    array = 13;
                    break;
                case R.id.binoculars:
                    points = 2;
                    array = 17;
                    break;
                case R.id.chalk:
                    points = 2;
                    array = 19;
                    break;
                case R.id.pendant:
                    points = 1;
                    array = 23;
                    break;
            }
            if (isChecked) {
                if (globalVariables.Investigators.get(currentInvestigator).SupplyPoints < points) {
                    buttonView.setChecked(false);
                } else {
                    globalVariables.Investigators.get(currentInvestigator).Supplies = globalVariables.Investigators
                            .get(currentInvestigator).Supplies * array;
                    globalVariables.Investigators.get(currentInvestigator).SupplyPoints += -points;
                    int currentPoints = globalVariables.Investigators.get(currentInvestigator).SupplyPoints;
                    String ptsAvailable = Integer.toString(currentPoints) + " " + getResources().getString(R.string
                            .points_available);
                    supplyPoints.setText(ptsAvailable);
                }
            } else {
                globalVariables.Investigators.get(currentInvestigator).Supplies = globalVariables.Investigators
                        .get(currentInvestigator).Supplies / array;
                globalVariables.Investigators.get(currentInvestigator).SupplyPoints += points;
                int currentPoints = globalVariables.Investigators.get(currentInvestigator).SupplyPoints;
                String ptsAvailable = Integer.toString(currentPoints) + " " + getResources().getString(R.string
                        .points_available);
                supplyPoints.setText(ptsAvailable);
            }
        }
    }

    // Dialog box for confirming the start of a campaign
    public static class StartCampaignDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Get the campaign name from the bundle
            Bundle bundle = this.getArguments();
            final String campaign = bundle.getString("campaign");

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Get the layout inflater and inflate the view
            View v = View.inflate(getActivity(), R.layout.b_dialog_start_campaign, null);

            // Find views and set fonts
            Typeface arnoprobold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/arnoprobold.otf");
            Typeface teutonic = Typeface.createFromAsset(getActivity().getAssets(), "fonts/teutonic.ttf");
            Typeface arnopro = Typeface.createFromAsset(getActivity().getAssets(), "fonts/arnopro.otf");
            Typeface wolgast = Typeface.createFromAsset(getActivity().getAssets(), "fonts/wolgast.otf");
            Button cancelButton = v.findViewById(R.id.cancel_button);
            Button okayButton = v.findViewById(R.id.okay_button);
            TextView confirm = v.findViewById(R.id.confirm_start_campaign);
            cancelButton.setTypeface(teutonic);
            okayButton.setTypeface(teutonic);
            confirm.setTypeface(arnoprobold);

            // Set campaign title
            TextView campaignName = v.findViewById(R.id.campaign_name);
            campaignName.setTypeface(wolgast);
            campaignName.setText(campaign);

            /*
                Show the right views for the number of investigators and set the right font to the name
             */
            ImageView lineOne = v.findViewById(R.id.line_one);
            ImageView lineTwo = v.findViewById(R.id.line_two);
            lineOne.setVisibility(GONE);
            lineTwo.setVisibility(GONE);

            okayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Set current scenario to first scenario
                    globalVariables.CurrentScenario = 1;

                    // Save the new campaign
                    CampaignInvestigatorsActivity.newCampaign(getActivity(), campaign);

                    // Go to scenario setup
                    Intent intent = new Intent(getActivity(), ScenarioMainActivity.class);
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
}

