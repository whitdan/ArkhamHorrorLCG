package com.whitdan.arkhamhorrorlcgcampaignguide.C_Scenario;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.TouchDelegate;
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
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.GlobalVariables;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.Investigator;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class ScenarioMainActivity extends AppCompatActivity {

    static GlobalVariables globalVariables;

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

        // Set title
        Typeface teutonic = Typeface.createFromAsset(getAssets(), "fonts/teutonic.ttf");
        TextView title = (TextView) findViewById(R.id.current_scenario_name);
        title.setTypeface(teutonic);
        switch (globalVariables.CurrentCampaign) {
            case 1:
                switch (globalVariables.CurrentScenario) {
                    case 1:
                        title.setText(R.string.night_scenario_one);
                        break;
                    case 2:
                        title.setText(R.string.night_scenario_two);
                        break;
                    case 3:
                        title.setText(R.string.night_scenario_three);
                        break;
                }
                break;
            case 2:
                switch (globalVariables.CurrentScenario) {
                    case 1:
                        title.setText(R.string.dunwich_scenario_one);
                        break;
                    case 2:
                        title.setText(R.string.dunwich_scenario_two);
                        break;
                    case 3:
                        title.setText(R.string.dunwich_interlude_one);
                        break;
                    case 4:
                        title.setText(R.string.dunwich_scenario_three);
                        break;
                    case 5:
                        title.setText(R.string.dunwich_scenario_four);
                        break;
                    case 6:
                        title.setText(R.string.dunwich_scenario_five);
                        break;
                    case 7:
                        title.setText(R.string.dunwich_interlude_two);
                        break;
                    case 8:
                        title.setText(R.string.dunwich_scenario_six);
                        break;
                    case 9:
                        title.setText(R.string.dunwich_scenario_seven);
                        break;
                    case 10:
                        title.setText(R.string.dunwich_scenario_eight);
                        break;
                    case 11:
                        title.setText(R.string.dunwich_epilogue);
                        break;
                }
                break;
            case 3:
                switch (globalVariables.CurrentScenario) {
                    case 1:
                        title.setText(R.string.carcosa_scenario_one);
                        break;
                    case 2:
                        title.setText(R.string.carcosa_scenario_two);
                        break;
                    case 3:
                        title.setText(R.string.carcosa_interlude_one);
                        break;
                    case 4:
                        title.setText(R.string.carcosa_scenario_three);
                        break;
                    case 5:
                        title.setText(R.string.carcosa_scenario_four);
                        break;
                    case 6:
                        title.setText(R.string.carcosa_interlude_two);
                        break;
                    case 7:
                        title.setText(R.string.carcosa_scenario_five);
                        break;
                    case 8:
                        title.setText(R.string.carcosa_scenario_six);
                        break;
                    case 9:
                        title.setText(R.string.carcosa_scenario_seven);
                        break;
                    case 10:
                        title.setText(R.string.carcosa_scenario_eight);
                        break;
                    case 11:
                        title.setText(R.string.carcosa_epilogue);
                        break;
                    case 12:
                        title.setText(R.string.campaign_completed);
                        break;
                }
                break;
        }
        if (globalVariables.CurrentScenario > 100) {
            switch (globalVariables.CurrentScenario) {
                case 101:
                    title.setText(R.string.rougarou_scenario);
                    break;
                case 102:
                    title.setText(R.string.carnevale_scenario);
                    break;
            }
        }

        // Setup investigator views
        LinearLayout investigatorList = (LinearLayout) findViewById(R.id.investigators_list);
        InvestigatorListAdapter investigatorsAdapter = new InvestigatorListAdapter(this, globalVariables
                .Investigators);
        final int adapterCount = investigatorsAdapter.getCount();

        for (int i = 0; i < adapterCount; i++) {
            View item = investigatorsAdapter.getView(i, null, investigatorList);
            investigatorList.addView(item);
        }


        // Set buttons
        Button setup = (Button) findViewById(R.id.scenario_setup_button);
        setup.setTypeface(teutonic);
        setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(globalVariables.CurrentCampaign == 3 && globalVariables.CurrentScenario == 4){
                    Toast toast = Toast.makeText(getBaseContext(), R.string.scenario_not_available, Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                Intent intent = new Intent(ScenarioMainActivity.this, ScenarioIntroductionActivity.class);
                startActivity(intent);}
            }
        });

        Button sideStory = (Button) findViewById(R.id.add_side_story_button);
        sideStory.setTypeface(teutonic);
        sideStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment sideStoryDialog = new SideStoryDialog();
                sideStoryDialog.show(getFragmentManager(), "side_story");
            }
        });

        Button log = (Button) findViewById(R.id.campaign_log_button);
        log.setTypeface(teutonic);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScenarioMainActivity.this, CampaignLogActivity.class);
                startActivity(intent);
            }
        });

        Button editTeam = (Button) findViewById(R.id.edit_team_button);
        editTeam.setTypeface(teutonic);
        editTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScenarioMainActivity.this, EditTeamActivity.class);
                startActivity(intent);
            }
        });

        Button chaosBag = (Button) findViewById(R.id.chaos_bag_button);
        chaosBag.setTypeface(teutonic);
        chaosBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScenarioMainActivity.this, ChaosBagActivity.class);
                startActivity(intent);
            }
        });

        Button backButton = (Button) findViewById(R.id.back_button);
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

        Button continueButton = (Button) findViewById(R.id.continue_button);
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
                }
                for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                    if (globalVariables.Investigators.get(i).Status == 2) {
                        investigatorDead = true;
                    }
                }
                if(globalVariables.CurrentCampaign == 3 && globalVariables.CurrentScenario == 4){
                    Toast toast = Toast.makeText(getBaseContext(), R.string.scenario_not_available, Toast.LENGTH_SHORT);
                    toast.show();
                } else if (setupRequired) {
                    Toast toast = Toast.makeText(ScenarioMainActivity.this, R.string.must_scenario_setup, Toast.LENGTH_SHORT);
                    toast.show();
                } else if (investigatorDead) {
                    Toast toast = Toast.makeText(ScenarioMainActivity.this, R.string.must_replace_investigators, Toast.LENGTH_SHORT);
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

            final RadioButton rougarou = (RadioButton) v.findViewById(R.id.rougarou_scenario);
            final RadioButton carnevale = (RadioButton) v.findViewById(R.id.carnevale_scenario);
            Typeface arnopro = Typeface.createFromAsset(getActivity().getAssets(), "fonts/arnoprobold.otf");
            rougarou.setTypeface(arnopro);
            carnevale.setTypeface(arnopro);

            Typeface teutonic = Typeface.createFromAsset(getActivity().getAssets(), "fonts/teutonic.ttf");
            TextView title = (TextView) v.findViewById(R.id.add_side_story);
            Button cancelButton = (Button) v.findViewById(R.id.cancel_button);
            Button okayButton = (Button) v.findViewById(R.id.okay_button);
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
                            Toast toast = Toast.makeText(getActivity(), R.string.already_completed_scenario, Toast.LENGTH_SHORT);
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
                            Toast toast = Toast.makeText(getActivity(), R.string.already_completed_scenario, Toast.LENGTH_SHORT);
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
                TextView investigatorNameView = (TextView) listItemView.findViewById(R.id.investigator_name);
                String[] investigatorNames = getContext().getResources().getStringArray(R.array.investigators);
                String name = investigatorNames[currentInvestigator.Name] + " ";
                investigatorNameView.setText(name);
                investigatorNameView.setTypeface(teutonic);
                String playerName = " ";
                if (currentInvestigator.PlayerName != null) {
                    playerName = currentInvestigator.PlayerName + " ";
                }
                TextView playerNameView = (TextView) listItemView.findViewById(R.id.player_name);
                playerNameView.setText(playerName);
                playerNameView.setTypeface(wolgast);

                // Set decklist
                String deckName = currentInvestigator.DeckName;
                TextView decklistView = (TextView) listItemView.findViewById(R.id.decklist);
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
                TextView killed = (TextView) listItemView.findViewById(R.id.killed);
                killed.setTypeface(arnoprobold);
                TextView phys = (TextView) listItemView.findViewById(R.id.physical_trauma);
                phys.setTypeface(arnoprobold);
                TextView mental = (TextView) listItemView.findViewById(R.id.mental_trauma);
                mental.setTypeface(arnoprobold);
                TextView availXP = (TextView) listItemView.findViewById(R.id.available_xp);
                availXP.setTypeface(arnoprobold);
                TextView spentXP = (TextView) listItemView.findViewById(R.id.xp_spent);
                spentXP.setTypeface(arnoprobold);

                // Hide stats and show investigator killed if investigator is dead
                LinearLayout stats = (LinearLayout) listItemView.findViewById(R.id.investigator_stats);
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
                TextView physicalTrauma = (TextView) listItemView.findViewById(R.id.physical_trauma_amount);
                physicalTrauma.setText(String.valueOf(currentInvestigator.Damage));
                physicalTrauma.setTypeface(wolgastbold);
                TextView mentalTrauma = (TextView) listItemView.findViewById(R.id.mental_trauma_amount);
                mentalTrauma.setText(String.valueOf(currentInvestigator.Horror));
                mentalTrauma.setTypeface(wolgastbold);
                TextView availableXP = (TextView) listItemView.findViewById(R.id.available_xp_amount);
                availableXP.setText(String.valueOf(currentInvestigator.AvailableXP));
                availableXP.setTypeface(wolgastbold);

                // Setup spent XP views
                final TextView spentXPAmount = (TextView) listItemView.findViewById(R.id.xp_spent_amount);
                spentXPAmount.setTypeface(wolgastbold);
                final ImageView xpDecrement = (ImageView) listItemView.findViewById(R.id.xp_spent_decrement);
                final ImageView xpIncrement = (ImageView) listItemView.findViewById(R.id.xp_spent_increment);

                // Set click listeners and increase touchable size of buttons by 70
                spentXPAmount.setText(String.valueOf(currentInvestigator.TempXP));
                xpDecrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (currentInvestigator.TempXP > 0) {
                            currentInvestigator.TempXP += -1;
                            spentXPAmount.setText(String.valueOf(currentInvestigator.TempXP));
                        }
                    }
                });
                xpIncrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (currentInvestigator.TempXP < currentInvestigator.AvailableXP) {
                            currentInvestigator.TempXP += 1;
                            spentXPAmount.setText(String.valueOf(currentInvestigator.TempXP));
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


    // Touch delegate for multiple views, allows the + and - buttons to be more easily clickable
    public class TouchDelegateComposite extends TouchDelegate {

        private final List<TouchDelegate> delegates = new ArrayList<TouchDelegate>();

        public TouchDelegateComposite(View view) {
            super(new Rect(), view);
        }

        public void addDelegate(TouchDelegate delegate) {
            if (delegate != null) {
                delegates.add(delegate);
            }
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            boolean res = false;
            float x = event.getX();
            float y = event.getY();
            for (TouchDelegate delegate : delegates) {
                event.setLocation(x, y);
                res = delegate.onTouchEvent(event) || res;
            }
            return res;
        }

    }
}
