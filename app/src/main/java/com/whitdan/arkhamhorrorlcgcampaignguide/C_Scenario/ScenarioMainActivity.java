package com.whitdan.arkhamhorrorlcgcampaignguide.C_Scenario;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.whitdan.arkhamhorrorlcgcampaignguide.A_Menus.MainMenuActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.D_Misc.CampaignLogActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.D_Misc.ChaosBagActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.D_Misc.EditTeamActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.R;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.ArkhamContract;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.ArkhamDbHelper;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.GlobalVariables;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.Investigator;

import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class ScenarioMainActivity extends AppCompatActivity {

    static GlobalVariables globalVariables;
    ArkhamDbHelper mDbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // If app is reopening after the process is killed, kick back to the main menu (stops the activity from
        // showing up unpopulated)
        if (savedInstanceState != null) {
            Intent intent = new Intent(ScenarioMainActivity.this, MainMenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_activity_scenario_main);
        globalVariables = (GlobalVariables) this.getApplication();

        mDbHelper = new ArkhamDbHelper(this);
        db = mDbHelper.getWritableDatabase();

        // Set title
        Typeface teutonic = Typeface.createFromAsset(getAssets(), "fonts/teutonic.ttf");
        TextView title = findViewById(R.id.current_scenario_name);
        title.setTypeface(teutonic);
        MainMenuActivity.setTitle(title);

        // Setup investigator views
        LinearLayout investigatorList = findViewById(R.id.investigators_list);
        InvestigatorListAdapter investigatorsAdapter = new InvestigatorListAdapter(this, globalVariables
                .Investigators);
        final int adapterCount = investigatorsAdapter.getCount();

        for (int i = 0; i < adapterCount; i++) {
            View item = investigatorsAdapter.getView(i, null, investigatorList);
            investigatorList.addView(item);
        }


        // Set buttons
        Button setup = findViewById(R.id.scenario_setup_button);
        setup.setTypeface(teutonic);
        setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (globalVariables.CurrentCampaign == 3 && globalVariables.CurrentScenario == 8) {
                    Toast toast = Toast.makeText(getBaseContext(), R.string.scenario_not_available, Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Intent intent = new Intent(ScenarioMainActivity.this, ScenarioIntroductionActivity.class);
                    startActivity(intent);
                }
            }
        });

        Button sideStory = findViewById(R.id.add_side_story_button);
        sideStory.setTypeface(teutonic);
        sideStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment sideStoryDialog = new SideStoryDialog();
                sideStoryDialog.show(getFragmentManager(), "side_story");
            }
        });

        Button log = findViewById(R.id.campaign_log_button);
        log.setTypeface(teutonic);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScenarioMainActivity.this, CampaignLogActivity.class);
                startActivity(intent);
            }
        });

        Button editTeam = findViewById(R.id.edit_team_button);
        editTeam.setTypeface(teutonic);
        editTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScenarioMainActivity.this, EditTeamActivity.class);
                startActivity(intent);
            }
        });

        Button chaosBag = findViewById(R.id.chaos_bag_button);
        chaosBag.setTypeface(teutonic);
        chaosBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScenarioMainActivity.this, ChaosBagActivity.class);
                startActivity(intent);
            }
        });

        Button backButton = findViewById(R.id.back_button);
        backButton.setTypeface(teutonic);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScenarioMainActivity.this, MainMenuActivity.class);
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
                // Check if setup is required or if any investigators are dead
                boolean setupRequired = false;
                boolean investigatorDead = false;
                switch (globalVariables.CurrentCampaign) {
                    case 2:
                        if (globalVariables.CurrentScenario == 8 && globalVariables.TownsfolkAction == 0) {
                            setupRequired = true;
                        }
                        break;
                    case 3:
                        if(globalVariables.CurrentScenario == 7 && globalVariables.DreamsAction == 0){
                            setupRequired = true;
                        }
                        break;
                }
                for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                    if (globalVariables.Investigators.get(i).Status == 2) {
                        investigatorDead = true;
                    }
                }
                if (globalVariables.CurrentCampaign == 3 && globalVariables.CurrentScenario == 8) {
                    Toast toast = Toast.makeText(getBaseContext(), R.string.scenario_not_available, Toast.LENGTH_SHORT);
                    toast.show();
                } else if (setupRequired) {
                    Toast toast = Toast.makeText(ScenarioMainActivity.this, R.string.must_scenario_setup, Toast
                            .LENGTH_SHORT);
                    toast.show();
                } else if (investigatorDead) {
                    Toast toast = Toast.makeText(ScenarioMainActivity.this, R.string.must_replace_investigators,
                            Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Intent intent = new Intent(ScenarioMainActivity.this, ScenarioResolutionActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    public static class SideStoryDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Get the layout inflater and inflate the view
            View v = View.inflate(getActivity(), R.layout.c_dialog_side_story, null);

            final RadioButton rougarou = v.findViewById(R.id.rougarou_scenario);
            final RadioButton carnevale = v.findViewById(R.id.carnevale_scenario);
            Typeface arnopro = Typeface.createFromAsset(getActivity().getAssets(), "fonts/arnoprobold.otf");
            rougarou.setTypeface(arnopro);
            carnevale.setTypeface(arnopro);

            Typeface teutonic = Typeface.createFromAsset(getActivity().getAssets(), "fonts/teutonic.ttf");
            TextView title = v.findViewById(R.id.add_side_story);
            Button cancelButton = v.findViewById(R.id.cancel_button);
            Button okayButton = v.findViewById(R.id.okay_button);
            title.setTypeface(teutonic);
            cancelButton.setTypeface(teutonic);
            okayButton.setTypeface(teutonic);

            okayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Work out lowest XP level
                    int XP = 999;
                    for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                        if (globalVariables.Investigators.get(i).AvailableXP < XP) {
                            XP = globalVariables.Investigators.get(i).AvailableXP;
                        }
                    }
                    if (rougarou.isChecked()) {
                        if (globalVariables.Rougarou > 0) {
                            Toast toast = Toast.makeText(getActivity(), R.string.already_completed_scenario, Toast
                                    .LENGTH_SHORT);
                            toast.show();
                        } else if (XP < 1) {
                            Toast toast = Toast.makeText(getActivity(), R.string.not_enough_xp_one, Toast.LENGTH_SHORT);
                            toast.show();
                        } else {
                            globalVariables.PreviousScenario = globalVariables.CurrentScenario;
                            globalVariables.CurrentScenario = 101;
                            for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                globalVariables.Investigators.get(i).AvailableXP += -1;
                            }
                            dismiss();
                            Intent intent = new Intent(getActivity(), ScenarioMainActivity.class);
                            startActivity(intent);
                        }
                    }
                    if (carnevale.isChecked()) {
                        if (globalVariables.Carnevale > 0) {
                            Toast toast = Toast.makeText(getActivity(), R.string.already_completed_scenario, Toast
                                    .LENGTH_SHORT);
                            toast.show();
                        } else if (XP < 3) {
                            Toast toast = Toast.makeText(getActivity(), R.string.not_enough_xp_three, Toast
                                    .LENGTH_SHORT);
                            toast.show();
                        } else {
                            globalVariables.PreviousScenario = globalVariables.CurrentScenario;
                            globalVariables.CurrentScenario = 102;
                            for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                                globalVariables.Investigators.get(i).AvailableXP += -3;
                            }
                            dismiss();
                            Intent intent = new Intent(getActivity(), ScenarioMainActivity.class);
                            startActivity(intent);
                        }
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

    // Adapter for the list of investigators
    private class InvestigatorListAdapter extends ArrayAdapter<Investigator> {

        private Context context;

        InvestigatorListAdapter(Context con, ArrayList<Investigator> investigators) {
            super(con, 0, investigators);
            context = con;
        }

        @Override
        @NonNull
        public View getView(final int pos, View convertView, @NonNull ViewGroup parent) {
            final Investigator currentInvestigator = getItem(pos);

            // Check if an existing view is being reused, otherwise inflate the view
            View listItemView = convertView;
            if (listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(
                        R.layout.c_item_investigator, parent, false);
            }

            if (currentInvestigator != null) {
                // Typefaces
                Typeface teutonic = Typeface.createFromAsset(context.getAssets(), "fonts/teutonic.ttf");
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

                // Set decklist
                String deckName = currentInvestigator.DeckName;
                TextView decklistView = listItemView.findViewById(R.id.decklist);
                decklistView.setTypeface(arnoprobold);
                final String decklist = currentInvestigator.Decklist;
                if (deckName == null && decklist == null) {
                    decklistView.setVisibility(GONE);
                } else if (deckName == null) {
                    if (decklist.length() == 0) {
                        decklistView.setVisibility(GONE);
                    }
                } else if (decklist == null) {
                    if (deckName.length() == 0) {
                        decklistView.setVisibility(GONE);
                    }
                } else if (decklist.length() == 0 && deckName.length() == 0) {
                    decklistView.setVisibility(GONE);
                }
                if (deckName != null) {
                    decklistView.setText(deckName);
                    decklistView.setPaintFlags(decklistView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                }
                if (decklist != null) {
                    if (decklist.length() > 0) {
                        decklistView.setTextColor(Color.BLUE);
                        decklistView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String deck;
                                if (!decklist.startsWith("http://") && !decklist.startsWith("https://")) {
                                    deck = "http://" + decklist;
                                } else {
                                    deck = decklist;
                                }
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(deck));
                                context.startActivity(browserIntent);
                            }
                        });
                    }
                }

                // Set typefaces
                TextView killed = listItemView.findViewById(R.id.killed);
                killed.setTypeface(arnoprobold);
                TextView phys = listItemView.findViewById(R.id.physical_trauma);
                phys.setTypeface(arnoprobold);
                TextView mental = listItemView.findViewById(R.id.mental_trauma);
                mental.setTypeface(arnoprobold);
                TextView totXP = listItemView.findViewById(R.id.total_xp);
                totXP.setTypeface(arnoprobold);
                TextView availXP = listItemView.findViewById(R.id.available_xp);
                availXP.setTypeface(arnoprobold);
                TextView spentXP = listItemView.findViewById(R.id.xp_spent);
                spentXP.setTypeface(arnoprobold);

                // Hide stats and show investigator killed if investigator is dead
                LinearLayout stats = listItemView.findViewById(R.id.investigator_stats);
                if (currentInvestigator.Status == 2) {
                    stats.setVisibility(GONE);
                    killed.setVisibility(VISIBLE);
                    if (currentInvestigator.Damage >= currentInvestigator.Health) {
                        killed.setText(R.string.killed);
                    } else if (currentInvestigator.Horror >= currentInvestigator.Sanity) {
                        killed.setText(R.string.insane);
                    } else {
                        killed.setText(R.string.killed);
                    }
                }

                // Set other values
                TextView physicalTrauma = listItemView.findViewById(R.id.physical_trauma_amount);
                physicalTrauma.setText(String.valueOf(currentInvestigator.Damage));
                physicalTrauma.setTypeface(wolgastbold);
                TextView mentalTrauma = listItemView.findViewById(R.id.mental_trauma_amount);
                mentalTrauma.setText(String.valueOf(currentInvestigator.Horror));
                mentalTrauma.setTypeface(wolgastbold);
                TextView totalXP = listItemView.findViewById(R.id.total_xp_amount);
                totalXP.setText(String.valueOf(currentInvestigator.TotalXP));
                totalXP.setTypeface(wolgastbold);
                TextView availableXP = listItemView.findViewById(R.id.available_xp_amount);
                availableXP.setText(String.valueOf(currentInvestigator.AvailableXP));
                availableXP.setTypeface(wolgastbold);

                // Hide total XP layout if not campaign version 2
                LinearLayout totalXPLayout = listItemView.findViewById(R.id.total_xp_layout);
                if(globalVariables.CampaignVersion < 2){
                    totalXPLayout.setVisibility(GONE);
                }

                // Setup spent XP views
                final TextView spentXPAmount = listItemView.findViewById(R.id.xp_spent_amount);
                spentXPAmount.setTypeface(wolgastbold);
                final ImageView xpDecrement = listItemView.findViewById(R.id.xp_spent_decrement);
                final ImageView xpIncrement = listItemView.findViewById(R.id.xp_spent_increment);

                // Set click listeners
                spentXPAmount.setText(String.valueOf(currentInvestigator.SpentXP));
                xpDecrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (currentInvestigator.SpentXP > 0) {
                            currentInvestigator.SpentXP += -1;
                            spentXPAmount.setText(String.valueOf(currentInvestigator.SpentXP));

                            // Save new amount
                            ContentValues values = new ContentValues();
                            values.put(ArkhamContract.InvestigatorEntry.COLUMN_INVESTIGATOR_SPENT_XP,
                                    currentInvestigator.SpentXP);
                            String selection = ArkhamContract.InvestigatorEntry.PARENT_ID + " = ? AND " +
                                ArkhamContract.InvestigatorEntry.INVESTIGATOR_ID + " = ?";
                            String[] selectionArgs = {Long.toString(globalVariables.CampaignID), Integer.toString(pos)};
                            db.update(ArkhamContract.InvestigatorEntry.TABLE_NAME, values, selection, selectionArgs);
                        }

                    }
                });
                xpIncrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (currentInvestigator.SpentXP < currentInvestigator.AvailableXP) {
                            currentInvestigator.SpentXP += 1;
                            spentXPAmount.setText(String.valueOf(currentInvestigator.SpentXP));

                            // Save new amount
                            ContentValues values = new ContentValues();
                            values.put(ArkhamContract.InvestigatorEntry.COLUMN_INVESTIGATOR_SPENT_XP,
                                    currentInvestigator.SpentXP);
                            String selection = ArkhamContract.InvestigatorEntry.PARENT_ID + " = ? AND " +
                                    ArkhamContract.InvestigatorEntry.INVESTIGATOR_ID + " = ?";
                            String[] selectionArgs = {Long.toString(globalVariables.CampaignID), Integer.toString(pos)};
                            db.update(ArkhamContract.InvestigatorEntry.TABLE_NAME, values, selection, selectionArgs);
                        }
                    }
                });
            }

            return listItemView;
        }
    }

    // Disables back button and displays a toast
    @Override
    public void onBackPressed() {
        Toast toast = Toast.makeText(ScenarioMainActivity.this, R.string.cannot_back, Toast.LENGTH_SHORT);
        toast.show();
    }

    // Close database
    @Override
    protected void onDestroy() {
        mDbHelper.close();
        super.onDestroy();
    }
}
