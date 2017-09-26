package com.whitdan.arkhamhorrorlcgcampaignguide.D_Misc;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
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
import com.whitdan.arkhamhorrorlcgcampaignguide.B_CampaignSetup.CampaignIntroductionActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.C_Scenario.ScenarioResolutionActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.R;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.ArkhamContract;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.ArkhamDbHelper;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.GlobalVariables;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.Investigator;

import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class CampaignFinishedActivity extends AppCompatActivity {

    static GlobalVariables globalVariables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // If app is reopening after the process is killed, kick back to the main menu (stops the activity from
        // showing up unpopulated)
        if (savedInstanceState != null) {
            Intent intent = new Intent(CampaignFinishedActivity.this, MainMenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.d_activity_campaign_finished);
        globalVariables = (GlobalVariables) this.getApplication();

        // Set title
        Typeface teutonic = Typeface.createFromAsset(getAssets(), "fonts/teutonic.ttf");
        TextView title = findViewById(R.id.current_campaign_name);
        title.setTypeface(teutonic);
        TextView subTitle = findViewById(R.id.campaign_finished);
        subTitle.setTypeface(teutonic);
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
        }

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
        Button log = findViewById(R.id.campaign_log_button);
        log.setTypeface(teutonic);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CampaignFinishedActivity.this, CampaignLogActivity.class);
                startActivity(intent);
            }
        });

        Button backButton = findViewById(R.id.back_button);
        backButton.setTypeface(teutonic);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CampaignFinishedActivity.this, MainMenuActivity.class);
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
                DialogFragment finishCampaignDialog = new FinishCampaignDialog();
                finishCampaignDialog.show(getFragmentManager(), "finish_campaign");
            }
        });
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
        public View getView(int pos, View convertView, @NonNull ViewGroup parent) {
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
                TextView availXP = listItemView.findViewById(R.id.available_xp);
                availXP.setTypeface(arnoprobold);

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
                TextView availableXP = listItemView.findViewById(R.id.available_xp_amount);
                availableXP.setText(String.valueOf(currentInvestigator.AvailableXP));
                availableXP.setTypeface(wolgastbold);

                // Hide spent XP views
                TextView spentXP = listItemView.findViewById(R.id.xp_spent);
                spentXP.setVisibility(GONE);
                final TextView spentXPAmount = listItemView.findViewById(R.id.xp_spent_amount);
                spentXPAmount.setVisibility(GONE);
                ImageView xpDecrement = listItemView.findViewById(R.id.xp_spent_decrement);
                xpDecrement.setVisibility(GONE);
                ImageView xpIncrement = listItemView.findViewById(R.id.xp_spent_increment);
                xpIncrement.setVisibility(GONE);

            }
            return listItemView;
        }
    }

    public static class FinishCampaignDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Get the layout inflater and inflate the view
            View v = View.inflate(getActivity(), R.layout.d_dialog_finish_campaign, null);

            final RadioButton delete = v.findViewById(R.id.finish_delete_campaign);
            final RadioButton save = v.findViewById(R.id.finish_save_campaign);
            final RadioButton night = v.findViewById(R.id.night_campaign);
            final RadioButton dunwich = v.findViewById(R.id.dunwich_campaign);
            final RadioButton carcosa = v.findViewById(R.id.carcosa_campaign);
            Typeface arnopro = Typeface.createFromAsset(getActivity().getAssets(), "fonts/arnoprobold.otf");
            delete.setTypeface(arnopro);
            save.setTypeface(arnopro);
            night.setTypeface(arnopro);
            dunwich.setTypeface(arnopro);
            carcosa.setTypeface(arnopro);

            // Hide completed campaigns
            if (globalVariables.NightCompleted == 1) {
                night.setVisibility(GONE);
            }
            if (globalVariables.DunwichCompleted == 1) {
                dunwich.setVisibility(GONE);
            }

            Typeface teutonic = Typeface.createFromAsset(getActivity().getAssets(), "fonts/teutonic.ttf");
            TextView title = v.findViewById(R.id.finish_campaign);
            Button cancelButton = v.findViewById(R.id.cancel_button);
            Button okayButton = v.findViewById(R.id.okay_button);
            title.setTypeface(teutonic);
            cancelButton.setTypeface(teutonic);
            okayButton.setTypeface(teutonic);

            okayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ScenarioResolutionActivity.saveCampaign(getActivity(), globalVariables);
                    if (delete.isChecked()) {
                        // Set selectionArgs as the _ID of the campaign clicked on
                        String[] selectionArgs = {Long.toString(globalVariables.CampaignID)};
                        ArkhamDbHelper dbHelper = new ArkhamDbHelper(getActivity());
                        final SQLiteDatabase db = dbHelper.getWritableDatabase();

                        // Find all of the relevant rows of the database tables for the campaign clicked on
                        String campaignSelection = ArkhamContract.CampaignEntry._ID + " = ?";
                        String investigatorSelection = ArkhamContract.InvestigatorEntry.PARENT_ID + " = ?";
                        String nightSelection = ArkhamContract.NightEntry.PARENT_ID + " = ?";
                        String dunwichSelection = ArkhamContract.DunwichEntry.PARENT_ID + " = ?";
                        String carcosaSelection = ArkhamContract.CarcosaEntry.PARENT_ID + " = ?";

                        // Delete the rows
                        db.delete(ArkhamContract.CampaignEntry.TABLE_NAME, campaignSelection,
                                selectionArgs);
                        db.delete(ArkhamContract.InvestigatorEntry.TABLE_NAME, investigatorSelection,
                                selectionArgs);
                        db.delete(ArkhamContract.NightEntry.TABLE_NAME, nightSelection, selectionArgs);
                        db.delete(ArkhamContract.DunwichEntry.TABLE_NAME, dunwichSelection, selectionArgs);
                        db.delete(ArkhamContract.CarcosaEntry.TABLE_NAME, carcosaSelection, selectionArgs);

                        Intent intent = new Intent(getActivity(), MainMenuActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        getActivity().finish();
                    } else if (save.isChecked()) {
                        Intent intent = new Intent(getActivity(), MainMenuActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        getActivity().finish();
                    } else if (night.isChecked()) {
                        if (globalVariables.NightCompleted == 1) {
                            Toast toast = Toast.makeText(getActivity(), R.string.already_completed_campaign,
                                    Toast.LENGTH_SHORT);
                            toast.show();
                        } else {
                            globalVariables.CurrentCampaign = 1;
                            globalVariables.CurrentScenario = 1;
                            Intent intent = new Intent(getActivity(), CampaignIntroductionActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                    } else if (dunwich.isChecked()) {
                        if (globalVariables.DunwichCompleted == 1) {
                            Toast toast = Toast.makeText(getActivity(), R.string.already_completed_campaign,
                                    Toast.LENGTH_SHORT);
                            toast.show();
                        } else {
                            globalVariables.CurrentCampaign = 2;
                            globalVariables.CurrentScenario = 1;
                            Intent intent = new Intent(getActivity(), CampaignIntroductionActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                    } else if (carcosa.isChecked()) {
                        globalVariables.CurrentCampaign = 3;
                        boolean lola = false;
                        for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                            if (globalVariables.Investigators.get(i).Name == Investigator.LOLA_HAYES) {
                                lola = true;
                            }
                        }
                        if (lola) {
                            globalVariables.CurrentScenario = 0;
                        } else {
                            globalVariables.CurrentScenario = 1;
                        }
                        Intent intent = new Intent(getActivity(), CampaignIntroductionActivity.class);
                        startActivity(intent);
                        getActivity().finish();
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

    // Makes back button go up (back to home page - SelectCampaignActivity)
    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
    }
}
