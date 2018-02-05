package com.whitdan.arkhamhorrorlcgcampaignguide.D_Misc;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.whitdan.arkhamhorrorlcgcampaignguide.A_Menus.MainMenuActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.C_Scenario.ScenarioMainActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.C_Scenario.ScenarioResolutionActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.R;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.ArkhamDbHelper;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.GlobalVariables;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.Investigator;

import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class EditStatsActivity extends AppCompatActivity {

    static GlobalVariables globalVariables;
    ArkhamDbHelper mDbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // If app is reopening after the process is killed, kick back to the main menu (stops the activity from
        // showing up unpopulated)
        if (savedInstanceState != null) {
            Intent intent = new Intent(EditStatsActivity.this, MainMenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.d_activity_edit_stats);
        globalVariables = (GlobalVariables) this.getApplication();

        mDbHelper = new ArkhamDbHelper(this);
        db = mDbHelper.getWritableDatabase();

        // Set title
        Typeface teutonic = Typeface.createFromAsset(getAssets(), "fonts/teutonic.ttf");
        TextView title = findViewById(R.id.current_scenario_name);
        title.setTypeface(teutonic);
        globalVariables.setTitle(title);

        // Setup titles
        TextView currentTeam = findViewById(R.id.current_team);
        currentTeam.setTypeface(teutonic);
        TextView savedInvestigators = findViewById(R.id.saved_investigators);
        savedInvestigators.setTypeface(teutonic);

        // Setup investigator views
        LinearLayout investigatorList = findViewById(R.id.investigators_list);
        InvestigatorListAdapter investigatorsAdapter = new InvestigatorListAdapter(this, globalVariables
                .Investigators, false);
        final int adapterCount = investigatorsAdapter.getCount();
        for (int i = 0; i < adapterCount; i++) {
            View item = investigatorsAdapter.getView(i, null, investigatorList);
            investigatorList.addView(item);
        }

        LinearLayout savedInvestigatorList = findViewById(R.id.saved_investigators_list);
        InvestigatorListAdapter savedInvestigatorsAdapter = new InvestigatorListAdapter(this, globalVariables
                .SavedInvestigators, true);
        final int savedAdapterCount = savedInvestigatorsAdapter.getCount();
        if (savedAdapterCount == 0) {
            savedInvestigators.setVisibility(GONE);
        }
        for (int i = 0; i < savedAdapterCount; i++) {
            View item = savedInvestigatorsAdapter.getView(i, null, savedInvestigatorList);
            savedInvestigatorList.addView(item);
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
        continueButton.setOnClickListener(new SaveChangesListener());
    }

    // Adapter for the list of investigators
    private class InvestigatorListAdapter extends ArrayAdapter<Investigator> {

        private Context context;
        private boolean saved;

        InvestigatorListAdapter(Context con, ArrayList<Investigator> investigators, boolean sav) {
            super(con, 0, investigators);
            context = con;
            saved = sav;
        }

        @Override
        @NonNull
        public View getView(final int pos, View convertView, @NonNull ViewGroup parent) {
            final Investigator currentInvestigator = getItem(pos);

            // Check if an existing view is being reused, otherwise inflate the view
            View listItemView = convertView;
            if (listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(
                        R.layout.d_item_edit_investigator, parent, false);
            }

            if (currentInvestigator != null) {
                // Typefaces
                Typeface teutonic = Typeface.createFromAsset(context.getAssets(), "fonts/teutonic.ttf");
                Typeface arnopro = Typeface.createFromAsset(context.getAssets(), "fonts/arnopro.otf");
                Typeface arnoprobold = Typeface.createFromAsset(context.getAssets(), "fonts/arnoprobold.otf");
                Typeface wolgast = Typeface.createFromAsset(context.getAssets(), "fonts/wolgast.otf");
                Typeface wolgastbold = Typeface.createFromAsset(context.getAssets(), "fonts/wolgastbold.otf");

                // Set investigator name
                TextView investigatorNameView = listItemView.findViewById(R.id.investigator_name);
                String[] investigatorNames = getContext().getResources().getStringArray(R.array.investigators);
                String name = investigatorNames[currentInvestigator.Name] + " ";
                investigatorNameView.setText(name);
                investigatorNameView.setTypeface(teutonic);
                String playerName = " ";
                if (currentInvestigator.PlayerName != null) {
                    playerName = currentInvestigator.PlayerName + " ";
                }
                TextView playerNameView = listItemView.findViewById(R.id.player_name);
                playerNameView.setText(playerName);
                playerNameView.setTypeface(wolgast);

                // Set typefaces
                TextView phys = listItemView.findViewById(R.id.physical_trauma);
                phys.setTypeface(arnoprobold);
                TextView mental = listItemView.findViewById(R.id.mental_trauma);
                mental.setTypeface(arnoprobold);
                TextView totXP = listItemView.findViewById(R.id.total_xp);
                totXP.setTypeface(arnoprobold);
                TextView availXP = listItemView.findViewById(R.id.available_xp);
                availXP.setTypeface(arnoprobold);

                // Set other values
                currentInvestigator.TempDamage = currentInvestigator.Damage;
                currentInvestigator.TempHorror = currentInvestigator.Horror;
                currentInvestigator.TempTotalXP = currentInvestigator.TotalXP;
                currentInvestigator.TempAvailableXP = currentInvestigator.AvailableXP;
                final TextView physicalTrauma = listItemView.findViewById(R.id.physical_trauma_amount);
                physicalTrauma.setText(String.valueOf(currentInvestigator.TempDamage));
                physicalTrauma.setTypeface(wolgastbold);
                final TextView mentalTrauma = listItemView.findViewById(R.id.mental_trauma_amount);
                mentalTrauma.setText(String.valueOf(currentInvestigator.TempHorror));
                mentalTrauma.setTypeface(wolgastbold);
                final TextView totalXP = listItemView.findViewById(R.id.total_xp_amount);
                totalXP.setText(String.valueOf(currentInvestigator.TempTotalXP));
                totalXP.setTypeface(wolgastbold);
                final TextView availableXP = listItemView.findViewById(R.id.available_xp_amount);
                availableXP.setText(String.valueOf(currentInvestigator.TempAvailableXP));
                availableXP.setTypeface(wolgastbold);

                // Setup damage views and listeners
                final ImageView damageDecrement = listItemView.findViewById(R.id.physical_trauma_decrement);
                final ImageView damageIncrement = listItemView.findViewById(R.id.physical_trauma_increment);
                damageDecrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (currentInvestigator.TempDamage > 0) {
                            currentInvestigator.TempDamage += -1;
                            physicalTrauma.setText(String.valueOf(currentInvestigator.TempDamage));
                        }
                    }
                });
                damageIncrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (currentInvestigator.TempDamage < currentInvestigator.Health) {
                            currentInvestigator.TempDamage += 1;
                            physicalTrauma.setText(String.valueOf(currentInvestigator.TempDamage));
                        }
                    }
                });

                // Setup horror views and listeners
                final ImageView horrorDecrement = listItemView.findViewById(R.id.mental_trauma_decrement);
                final ImageView horrorIncrement = listItemView.findViewById(R.id.mental_trauma_increment);
                horrorDecrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (currentInvestigator.TempHorror > 0) {
                            currentInvestigator.TempHorror += -1;
                            mentalTrauma.setText(String.valueOf(currentInvestigator.TempHorror));
                        }
                    }
                });
                horrorIncrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (currentInvestigator.TempHorror < currentInvestigator.Sanity) {
                            currentInvestigator.TempHorror += 1;
                            mentalTrauma.setText(String.valueOf(currentInvestigator.TempHorror));
                        }
                    }
                });

                // Hide total XP layout if not campaign version 2
                LinearLayout totalXPLayout = listItemView.findViewById(R.id.total_xp_layout);
                if (globalVariables.CampaignVersion < 2) {
                    totalXPLayout.setVisibility(GONE);
                }

                // Setup total XP views and listeners
                final ImageView totalDecrement = listItemView.findViewById(R.id.total_xp_decrement);
                final ImageView totalIncrement = listItemView.findViewById(R.id.total_xp_increment);
                totalDecrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (currentInvestigator.TempTotalXP > 0) {
                            currentInvestigator.TempTotalXP += -1;
                            totalXP.setText(String.valueOf(currentInvestigator.TempTotalXP));
                        }
                    }
                });
                totalIncrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (currentInvestigator.TempTotalXP < 99) {
                            currentInvestigator.TempTotalXP += 1;
                            totalXP.setText(String.valueOf(currentInvestigator.TempTotalXP));
                        }
                    }
                });

                // Setup available XP views and listeners
                final ImageView availableDecrement = listItemView.findViewById(R.id.available_xp_decrement);
                final ImageView availableIncrement = listItemView.findViewById(R.id.available_xp_increment);
                availableDecrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (currentInvestigator.TempAvailableXP > 0) {
                            currentInvestigator.TempAvailableXP += -1;
                            availableXP.setText(String.valueOf(currentInvestigator.TempAvailableXP));
                        }
                    }
                });
                availableIncrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (currentInvestigator.TempAvailableXP < 99) {
                            currentInvestigator.TempAvailableXP += 1;
                            availableXP.setText(String.valueOf(currentInvestigator.TempAvailableXP));
                        }
                    }
                });

                // Setup killed checkbox
                CheckBox killedCheckbox = listItemView.findViewById(R.id.killed_checkbox);
                killedCheckbox.setTypeface(arnoprobold);
                if (currentInvestigator.Status == 2) {
                    currentInvestigator.TempStatus = 2;
                    killedCheckbox.setVisibility(VISIBLE);
                    killedCheckbox.setChecked(true);
                    if (currentInvestigator.Damage >= currentInvestigator.Health) {
                        killedCheckbox.setText(R.string.killed);
                    } else if (currentInvestigator.Horror >= currentInvestigator.Sanity) {
                        killedCheckbox.setText(R.string.insane);
                    } else {
                        killedCheckbox.setText(R.string.killed);
                    }
                    killedCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton button, boolean b) {
                            if(currentInvestigator.TempDamage == currentInvestigator.Health || currentInvestigator
                                    .TempHorror == currentInvestigator.Sanity){
                                button.setChecked(true);
                            }
                            if (button.isChecked()) {
                                currentInvestigator.TempStatus = 2;
                            } else if (saved) {
                                currentInvestigator.TempStatus = 3;
                            } else {
                                currentInvestigator.TempStatus = 1;
                            }
                        }
                    });
                }
            }

            return listItemView;
        }
    }

    // Saves any changes made and advances back to the current team screen
    private class SaveChangesListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            // For each investigator, save the new values and reset temp status
            for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                globalVariables.Investigators.get(i).Damage = globalVariables.Investigators.get(i).TempDamage;
                globalVariables.Investigators.get(i).Horror = globalVariables.Investigators.get(i).TempHorror;
                globalVariables.Investigators.get(i).TotalXP = globalVariables.Investigators.get(i).TempTotalXP;
                globalVariables.Investigators.get(i).AvailableXP = globalVariables.Investigators.get(i).TempAvailableXP;
                if (globalVariables.Investigators.get(i).Status == 2) {
                    globalVariables.Investigators.get(i).Status = globalVariables.Investigators.get(i).TempStatus;
                }
                if (globalVariables.Investigators.get(i).Damage == globalVariables.Investigators.get(i).Health
                        || globalVariables.Investigators.get(i).Horror == globalVariables.Investigators.get(i).Sanity) {
                    globalVariables.Investigators.get(i).Status = 2;
                }
                globalVariables.Investigators.get(i).TempStatus = 0;
            }

            for (int i = 0; i < globalVariables.SavedInvestigators.size(); i++) {
                globalVariables.SavedInvestigators.get(i).Damage = globalVariables.SavedInvestigators.get(i).TempDamage;
                globalVariables.SavedInvestigators.get(i).Horror = globalVariables.SavedInvestigators.get(i).TempHorror;
                globalVariables.SavedInvestigators.get(i).TotalXP = globalVariables.SavedInvestigators.get(i)
                        .TempTotalXP;
                globalVariables.SavedInvestigators.get(i).AvailableXP = globalVariables.SavedInvestigators.get(i)
                        .TempAvailableXP;
                if (globalVariables.SavedInvestigators.get(i).Status == 2) {
                    globalVariables.SavedInvestigators.get(i).Status = globalVariables.SavedInvestigators.get(i)
                            .TempStatus;
                }
                if (globalVariables.SavedInvestigators.get(i).Damage == globalVariables.SavedInvestigators.get
                        (i).Health
                        || globalVariables.SavedInvestigators.get(i).Horror == globalVariables.SavedInvestigators.get
                        (i).Sanity) {
                    globalVariables.SavedInvestigators.get(i).Status = 2;
                }
                globalVariables.SavedInvestigators.get(i).TempStatus = 0;
            }

            // Save the campaign
            ScenarioResolutionActivity.saveCampaign(view.getContext(), globalVariables);

            // Go back to the main activity
            Intent intent = new Intent(EditStatsActivity.this, ScenarioMainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}