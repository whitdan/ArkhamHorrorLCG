package com.whitdan.arkhamhorrorlcgcampaignguide.E_EditMisc;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.TextView;

import com.whitdan.arkhamhorrorlcgcampaignguide.A_Menus.MainMenuActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.C_Scenario.ScenarioMainActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.C_Scenario.ScenarioResolutionActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.R;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.GlobalVariables;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class EditLogActivity extends AppCompatActivity {

    GlobalVariables globalVariables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // If app is reopening after the process is killed, kick back to the main menu (stops the activity from
        // showing up unpopulated)
        if (savedInstanceState != null) {
            Intent intent = new Intent(EditLogActivity.this, MainMenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.e_activity_edit_log);
        globalVariables = (GlobalVariables) this.getApplication();
        setupUI(findViewById(R.id.parent_layout), this);

        // Set title
        Typeface teutonic = Typeface.createFromAsset(getAssets(), "fonts/teutonic.ttf");
        Typeface arnopro = Typeface.createFromAsset(getAssets(), "fonts/arnopro.otf");
        TextView campaign = findViewById(R.id.current_campaign_name);
        campaign.setTypeface(teutonic);
        TextView title = findViewById(R.id.current_scenario_name);
        title.setTypeface(teutonic);
        globalVariables.setTitle(title);
        switch (globalVariables.CurrentCampaign) {
            case 1:
                campaign.setText(R.string.night_campaign);
                if (globalVariables.NightCompleted == 1) {
                    title.setVisibility(GONE);
                }
                break;
            case 2:
                campaign.setText(R.string.dunwich_campaign);
                if (globalVariables.DunwichCompleted == 1) {
                    title.setVisibility(GONE);
                }
                break;
            case 3:
                campaign.setText(R.string.carcosa_campaign);
                if (globalVariables.CarcosaCompleted == 1) {
                    title.setVisibility(GONE);
                }
                break;
        }

        // Setup notes section
        TextView notesHeading = findViewById(R.id.notes_heading);
        notesHeading.setTypeface(teutonic);
        final EditText notes = findViewById(R.id.edit_notes);
        notes.setTypeface(arnopro);
        if (globalVariables.Notes != null && globalVariables.Notes.length() > 0) {
            notes.setText(globalVariables.Notes);
        }

        // Get layout to add views and get scenario
        LinearLayout editLayout = findViewById(R.id.edit_log_layout);
        int scenario;
        if (globalVariables.CurrentScenario > 100) {
            scenario = globalVariables.PreviousScenario;
        } else {
            scenario = globalVariables.CurrentScenario;
        }
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        /*
        Night of the Zealot log
         */
        if (globalVariables.CurrentCampaign == 1) {

            // Nothing to show
            if (scenario == 1) {
                View nothing = View.inflate(this, R.layout.e_item_heading, null);
                TextView nothingText = nothing.findViewById(R.id.heading);
                nothingText.setText(R.string.nothing);
                nothingText.setTypeface(arnopro);
                nothingText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen
                        .arnopro_textsize));
                nothingText.setAllCaps(false);
                nothingText.setTextScaleX(1);
                lp.setMargins(16, 0, 16, 0);
                editLayout.addView(nothing, lp);
            }

            if (scenario > 1) {
                // Your House
                View houseHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView houseHeadingText = houseHeading.findViewById(R.id.heading);
                houseHeadingText.setText(R.string.house_heading);
                houseHeadingText.setTypeface(teutonic);
                editLayout.addView(houseHeading, lp);
                View houseOptions = View.inflate(this, R.layout.e_item_radiogroup, null);
                RadioButton houseOptionOne = houseOptions.findViewById(R.id.option_one);
                RadioButton houseOptionTwo = houseOptions.findViewById(R.id.option_two);
                houseOptionOne.setId(R.id.house_one);
                houseOptionTwo.setId(R.id.house_two);
                houseOptionOne.setText(getString(R.string.house_standing).trim());
                houseOptionTwo.setText(getString(R.string.house_burned).trim());
                houseOptionOne.setTypeface(arnopro);
                houseOptionTwo.setTypeface(arnopro);
                editLayout.addView(houseOptions, lp);
                if (globalVariables.HouseStanding == 1) {
                    houseOptionOne.setChecked(true);
                } else if (globalVariables.HouseStanding == 0) {
                    houseOptionTwo.setChecked(true);
                }

                // Ghoul Priest
                View ghoulHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView ghoulHeadingText = ghoulHeading.findViewById(R.id.heading);
                ghoulHeadingText.setText(R.string.ghoul_heading);
                ghoulHeadingText.setTypeface(teutonic);
                editLayout.addView(ghoulHeading, lp);
                View ghoulOptions = View.inflate(this, R.layout.e_item_radiogroup, null);
                RadioButton ghoulOptionOne = ghoulOptions.findViewById(R.id.option_one);
                RadioButton ghoulOptionTwo = ghoulOptions.findViewById(R.id.option_two);
                ghoulOptionOne.setId(R.id.ghoul_one);
                ghoulOptionTwo.setId(R.id.ghoul_two);
                ghoulOptionOne.setText(getString(R.string.ghoul_priest_dead).trim());
                ghoulOptionTwo.setText(getString(R.string.ghoul_priest_alive).trim());
                ghoulOptionOne.setTypeface(arnopro);
                ghoulOptionTwo.setTypeface(arnopro);
                editLayout.addView(ghoulOptions, lp);
                if (globalVariables.GhoulPriest == 0) {
                    ghoulOptionOne.setChecked(true);
                } else if (globalVariables.GhoulPriest == 1) {
                    ghoulOptionTwo.setChecked(true);
                }

                // Lita Chantler
                View litaHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView litaHeadingText = litaHeading.findViewById(R.id.heading);
                litaHeadingText.setText(R.string.lita_heading);
                litaHeadingText.setTypeface(teutonic);
                editLayout.addView(litaHeading, lp);
                View litaOptions = View.inflate(this, R.layout.e_item_radiogroup, null);
                RadioButton litaOptionOne = litaOptions.findViewById(R.id.option_one);
                RadioButton litaOptionTwo = litaOptions.findViewById(R.id.option_two);
                RadioButton litaOptionThree = litaOptions.findViewById(R.id.option_three);
                litaOptionThree.setVisibility(VISIBLE);
                litaOptionOne.setId(R.id.lita_one);
                litaOptionTwo.setId(R.id.lita_two);
                litaOptionThree.setId(R.id.lita_three);
                litaOptionOne.setText(getString(R.string.lita_not_obtained).trim());
                litaOptionTwo.setText(getString(R.string.lita_obtained).trim());
                litaOptionThree.setText(getString(R.string.lita_forced).trim());
                litaOptionOne.setTypeface(arnopro);
                litaOptionTwo.setTypeface(arnopro);
                litaOptionThree.setTypeface(arnopro);
                editLayout.addView(litaOptions, lp);
                if (globalVariables.LitaChantler == 0) {
                    litaOptionOne.setChecked(true);
                } else if (globalVariables.LitaChantler == 1) {
                    litaOptionTwo.setChecked(true);
                } else if (globalVariables.LitaChantler == 2) {
                    litaOptionThree.setChecked(true);
                }
            }

            if (scenario > 2) {
                // Past Midnight
                View midnightHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView midnightHeadingText = midnightHeading.findViewById(R.id.heading);
                midnightHeadingText.setText(R.string.midnight_heading);
                midnightHeadingText.setTypeface(teutonic);
                editLayout.addView(midnightHeading, lp);
                View midnightOptions = View.inflate(this, R.layout.e_item_radiogroup, null);
                RadioButton midnightOptionOne = midnightOptions.findViewById(R.id.option_one);
                RadioButton midnightOptionTwo = midnightOptions.findViewById(R.id.option_two);
                midnightOptionOne.setId(R.id.midnight_one);
                midnightOptionTwo.setId(R.id.midnight_two);
                midnightOptionOne.setText(getString(R.string.not_past_midnight).trim());
                midnightOptionTwo.setText(getString(R.string.past_midnight).trim());
                midnightOptionOne.setTypeface(arnopro);
                midnightOptionTwo.setTypeface(arnopro);
                editLayout.addView(midnightOptions, lp);
                if (globalVariables.PastMidnight == 0) {
                    midnightOptionOne.setChecked(true);
                } else if (globalVariables.PastMidnight == 1) {
                    midnightOptionTwo.setChecked(true);
                }

                // Cultists interrogated
                View cultistsHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView cultistsHeadingText = cultistsHeading.findViewById(R.id.heading);
                cultistsHeadingText.setText(R.string.cultists_interrogated_resolution);
                cultistsHeadingText.setTypeface(teutonic);
                editLayout.addView(cultistsHeading, lp);
                View drew = View.inflate(this, R.layout.e_item_checkbox, null);
                CheckBox drewBox = drew.findViewById(R.id.checkbox);
                drewBox.setId(R.id.drew);
                drewBox.setText(R.string.drew);
                drewBox.setTypeface(arnopro);
                editLayout.addView(drew, lp);
                if (globalVariables.DrewInterrogated == 1) {
                    drewBox.setChecked(true);
                }
                View herman = View.inflate(this, R.layout.e_item_checkbox, null);
                CheckBox hermanBox = herman.findViewById(R.id.checkbox);
                hermanBox.setId(R.id.herman);
                hermanBox.setText(R.string.herman);
                hermanBox.setTypeface(arnopro);
                editLayout.addView(herman, lp);
                if (globalVariables.HermanInterrogated == 1) {
                    hermanBox.setChecked(true);
                }
                View peter = View.inflate(this, R.layout.e_item_checkbox, null);
                CheckBox peterBox = peter.findViewById(R.id.checkbox);
                peterBox.setId(R.id.peter);
                peterBox.setText(R.string.peter);
                peterBox.setTypeface(arnopro);
                editLayout.addView(peter, lp);
                if (globalVariables.PeterInterrogated == 1) {
                    peterBox.setChecked(true);
                }
                View victoria = View.inflate(this, R.layout.e_item_checkbox, null);
                CheckBox victoriaBox = victoria.findViewById(R.id.checkbox);
                victoriaBox.setId(R.id.victoria);
                victoriaBox.setText(R.string.victoria);
                victoriaBox.setTypeface(arnopro);
                editLayout.addView(victoria, lp);
                if (globalVariables.VictoriaInterrogated == 1) {
                    victoriaBox.setChecked(true);
                }
                View ruth = View.inflate(this, R.layout.e_item_checkbox, null);
                CheckBox ruthBox = ruth.findViewById(R.id.checkbox);
                ruthBox.setId(R.id.ruth);
                ruthBox.setText(R.string.ruth);
                ruthBox.setTypeface(arnopro);
                editLayout.addView(ruth, lp);
                if (globalVariables.RuthInterrogated == 1) {
                    ruthBox.setChecked(true);
                }
                View masked = View.inflate(this, R.layout.e_item_checkbox, null);
                CheckBox maskedBox = masked.findViewById(R.id.checkbox);
                maskedBox.setId(R.id.masked);
                maskedBox.setText(R.string.masked_hunter);
                maskedBox.setTypeface(arnopro);
                editLayout.addView(masked, lp);
                if (globalVariables.MaskedInterrogated == 1) {
                    maskedBox.setChecked(true);
                }
            }

            if (globalVariables.NightCompleted == 1) {
                // Umordhoth
                View umordhothHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView umordhothHeadingText = umordhothHeading.findViewById(R.id.heading);
                umordhothHeadingText.setText(R.string.conclusion);
                umordhothHeadingText.setTypeface(teutonic);
                editLayout.addView(umordhothHeading, lp);
                View umordhothOptions = View.inflate(this, R.layout.e_item_radiogroup, null);
                RadioButton umordhothOptionOne = umordhothOptions.findViewById(R.id.option_one);
                RadioButton umordhothOptionTwo = umordhothOptions.findViewById(R.id.option_two);
                RadioButton umordhothOptionThree = umordhothOptions.findViewById(R.id.option_three);
                RadioButton umordhothOptionFour = umordhothOptions.findViewById(R.id.option_four);
                umordhothOptionThree.setVisibility(VISIBLE);
                umordhothOptionFour.setVisibility(VISIBLE);
                umordhothOptionOne.setId(R.id.umordhoth_one);
                umordhothOptionTwo.setId(R.id.umordhoth_two);
                umordhothOptionThree.setId(R.id.umordhoth_three);
                umordhothOptionFour.setId(R.id.umordhoth_four);
                umordhothOptionOne.setText(getString(R.string.umordhoth_succumbed).trim());
                umordhothOptionTwo.setText(getString(R.string.umordhoth_broken).trim());
                umordhothOptionThree.setText(getString(R.string.umordhoth_repelled).trim());
                umordhothOptionFour.setText(getString(R.string.umordhoth_sacrificed).trim());
                umordhothOptionOne.setTypeface(arnopro);
                umordhothOptionTwo.setTypeface(arnopro);
                umordhothOptionThree.setTypeface(arnopro);
                umordhothOptionFour.setTypeface(arnopro);
                editLayout.addView(umordhothOptions, lp);
                if (globalVariables.Umordhoth == 0) {
                    umordhothOptionOne.setChecked(true);
                } else if (globalVariables.Umordhoth == 1) {
                    umordhothOptionTwo.setChecked(true);
                } else if (globalVariables.Umordhoth == 2) {
                    umordhothOptionThree.setChecked(true);
                } else if (globalVariables.Umordhoth == 3) {
                    umordhothOptionFour.setChecked(true);
                }
            }
        }

        /*
            The Dunwich Legacy log
         */
        if (globalVariables.CurrentCampaign == 2) {
            // Nothing to show
            if ((scenario == 1 && globalVariables.FirstScenario == 1) ||
                    (scenario == 2 && globalVariables.FirstScenario == 2)) {
                View nothing = View.inflate(this, R.layout.e_item_heading, null);
                TextView nothingText = nothing.findViewById(R.id.heading);
                nothingText.setText(R.string.nothing);
                nothingText.setTypeface(arnopro);
                nothingText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen
                        .arnopro_textsize));
                nothingText.setAllCaps(false);
                nothingText.setTextScaleX(1);
                lp.setMargins(16, 0, 16, 0);
                editLayout.addView(nothing, lp);
            }

            if ((scenario > 1 && globalVariables.FirstScenario == 1) ||
                    (scenario != 2 && globalVariables.FirstScenario == 2)) {
                // Investigators unconscious
                View unconsciousHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView unconsciousHeadingText = unconsciousHeading.findViewById(R.id.heading);
                unconsciousHeadingText.setText(R.string.unconscious_heading);
                unconsciousHeadingText.setTypeface(teutonic);
                editLayout.addView(unconsciousHeading, lp);
                View unconsciousOptions = View.inflate(this, R.layout.e_item_radiogroup, null);
                RadioButton unconsciousOptionOne = unconsciousOptions.findViewById(R.id.option_one);
                RadioButton unconsciousOptionTwo = unconsciousOptions.findViewById(R.id.option_two);
                unconsciousOptionOne.setId(R.id.unconscious_one);
                unconsciousOptionTwo.setId(R.id.unconscious_two);
                unconsciousOptionOne.setText(getString(R.string.not_unconscious).trim());
                unconsciousOptionTwo.setText(getString(R.string.investigators_unconscious).trim());
                unconsciousOptionOne.setTypeface(arnopro);
                unconsciousOptionTwo.setTypeface(arnopro);
                editLayout.addView(unconsciousOptions, lp);
                if (globalVariables.InvestigatorsUnconscious == 0) {
                    unconsciousOptionOne.setChecked(true);
                } else if (globalVariables.InvestigatorsUnconscious == 1) {
                    unconsciousOptionTwo.setChecked(true);
                }
            }

            if ((scenario > 1 && globalVariables.FirstScenario == 1) ||
                    scenario > 2) {
                // Warren Rice
                View warrenHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView warrenHeadingText = warrenHeading.findViewById(R.id.heading);
                warrenHeadingText.setText(R.string.warren_rice);
                warrenHeadingText.setTypeface(teutonic);
                editLayout.addView(warrenHeading, lp);
                View warrenOptions = View.inflate(this, R.layout.e_item_radiogroup, null);
                RadioButton warrenOptionOne = warrenOptions.findViewById(R.id.option_one);
                RadioButton warrenOptionTwo = warrenOptions.findViewById(R.id.option_two);
                RadioButton warrenOptionThree = warrenOptions.findViewById(R.id.option_three);
                RadioButton warrenOptionFour = warrenOptions.findViewById(R.id.option_four);
                if (scenario > 6) {
                    warrenOptionOne.setVisibility(GONE);
                    warrenOptionTwo.setVisibility(GONE);
                    warrenOptionThree.setVisibility(VISIBLE);
                    warrenOptionFour.setVisibility(VISIBLE);
                }
                warrenOptionOne.setId(R.id.warren_one);
                warrenOptionTwo.setId(R.id.warren_two);
                warrenOptionThree.setId(R.id.warren_three);
                warrenOptionFour.setId(R.id.warren_four);
                warrenOptionOne.setText(getString(R.string.warren_kidnapped).trim());
                warrenOptionTwo.setText(getString(R.string.warren_rescued).trim());
                warrenOptionThree.setText(getString(R.string.warren_sacrificed).trim());
                warrenOptionFour.setText(getString(R.string.warren_survived).trim());
                warrenOptionOne.setTypeface(arnopro);
                warrenOptionTwo.setTypeface(arnopro);
                warrenOptionThree.setTypeface(arnopro);
                warrenOptionFour.setTypeface(arnopro);
                editLayout.addView(warrenOptions, lp);
                if (globalVariables.WarrenRice == 0) {
                    warrenOptionOne.setChecked(true);
                } else if (globalVariables.WarrenRice == 1) {
                    warrenOptionTwo.setChecked(true);
                } else if (globalVariables.WarrenRice == 2) {
                    warrenOptionThree.setChecked(true);
                } else if (globalVariables.WarrenRice == 3) {
                    warrenOptionFour.setChecked(true);
                }

                // Students
                View studentsHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView studentsHeadingText = studentsHeading.findViewById(R.id.heading);
                studentsHeadingText.setText(R.string.students_heading);
                studentsHeadingText.setTypeface(teutonic);
                editLayout.addView(studentsHeading, lp);
                View studentsOptions = View.inflate(this, R.layout.e_item_radiogroup, null);
                RadioButton studentsOptionsOne = studentsOptions.findViewById(R.id.option_one);
                RadioButton studentsOptionsTwo = studentsOptions.findViewById(R.id.option_two);
                RadioButton studentsOptionsThree = studentsOptions.findViewById(R.id.option_three);
                studentsOptionsThree.setVisibility(VISIBLE);
                studentsOptionsOne.setId(R.id.students_one);
                studentsOptionsTwo.setId(R.id.students_two);
                studentsOptionsThree.setId(R.id.students_three);
                studentsOptionsOne.setText(getString(R.string.students_failed).trim());
                studentsOptionsTwo.setText(getString(R.string.students_rescued).trim());
                studentsOptionsThree.setText(getString(R.string.experiment_defeated).trim());
                studentsOptionsOne.setTypeface(arnopro);
                studentsOptionsTwo.setTypeface(arnopro);
                studentsOptionsThree.setTypeface(arnopro);
                editLayout.addView(studentsOptions, lp);
                if (globalVariables.Students == 0) {
                    studentsOptionsOne.setChecked(true);
                } else if (globalVariables.Students == 1) {
                    studentsOptionsTwo.setChecked(true);
                } else if (globalVariables.Students == 2) {
                    studentsOptionsThree.setChecked(true);
                }
            }

            if ((scenario == 1 && globalVariables.FirstScenario == 2) ||
                    scenario > 2) {
                // Francis Morgan
                View francisHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView francisHeadingText = francisHeading.findViewById(R.id.heading);
                francisHeadingText.setText(R.string.francis_morgan);
                francisHeadingText.setTypeface(teutonic);
                editLayout.addView(francisHeading, lp);
                View francisOptions = View.inflate(this, R.layout.e_item_radiogroup, null);
                RadioButton francisOptionsOne = francisOptions.findViewById(R.id.option_one);
                RadioButton francisOptionsTwo = francisOptions.findViewById(R.id.option_two);
                RadioButton francisOptionsThree = francisOptions.findViewById(R.id.option_three);
                RadioButton francisOptionsFour = francisOptions.findViewById(R.id.option_four);
                if (scenario > 6) {
                    francisOptionsOne.setVisibility(GONE);
                    francisOptionsTwo.setVisibility(GONE);
                    francisOptionsThree.setVisibility(VISIBLE);
                    francisOptionsFour.setVisibility(VISIBLE);
                }
                francisOptionsOne.setId(R.id.francis_one);
                francisOptionsTwo.setId(R.id.francis_two);
                francisOptionsThree.setId(R.id.francis_three);
                francisOptionsFour.setId(R.id.francis_four);
                francisOptionsOne.setText(getString(R.string.morgan_kidnapped).trim());
                francisOptionsTwo.setText(getString(R.string.morgan_rescued).trim());
                francisOptionsThree.setText(getString(R.string.morgan_sacrificed).trim());
                francisOptionsFour.setText(getString(R.string.morgan_survived).trim());
                francisOptionsOne.setTypeface(arnopro);
                francisOptionsTwo.setTypeface(arnopro);
                francisOptionsThree.setTypeface(arnopro);
                francisOptionsFour.setTypeface(arnopro);
                editLayout.addView(francisOptions, lp);
                if (globalVariables.FrancisMorgan == 0) {
                    francisOptionsOne.setChecked(true);
                } else if (globalVariables.FrancisMorgan == 1) {
                    francisOptionsTwo.setChecked(true);
                } else if (globalVariables.FrancisMorgan == 2) {
                    francisOptionsThree.setChecked(true);
                } else if (globalVariables.FrancisMorgan == 3) {
                    francisOptionsFour.setChecked(true);
                }

                // O'Bannion Gang
                View obannionHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView obannionHeadingText = obannionHeading.findViewById(R.id.heading);
                obannionHeadingText.setText(R.string.obannion_heading);
                obannionHeadingText.setTypeface(teutonic);
                editLayout.addView(obannionHeading, lp);
                View obannionOptions = View.inflate(this, R.layout.e_item_radiogroup, null);
                RadioButton obannionOptionsOne = obannionOptions.findViewById(R.id.option_one);
                RadioButton obannionOptionsTwo = obannionOptions.findViewById(R.id.option_two);
                obannionOptionsOne.setId(R.id.obannion_one);
                obannionOptionsTwo.setId(R.id.obannion_two);
                obannionOptionsOne.setText(getString(R.string.obannion_failed).trim());
                obannionOptionsTwo.setText(getString(R.string.obannion_succeeded).trim());
                obannionOptionsOne.setTypeface(arnopro);
                obannionOptionsTwo.setTypeface(arnopro);
                editLayout.addView(obannionOptions, lp);
                if (globalVariables.ObannionGang == 0) {
                    obannionOptionsOne.setChecked(true);
                } else if (globalVariables.ObannionGang == 1) {
                    obannionOptionsTwo.setChecked(true);
                }

                // Investigators cheated
                View cheatedHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView cheatedHeadingText = cheatedHeading.findViewById(R.id.heading);
                cheatedHeadingText.setText(R.string.cheated_heading);
                cheatedHeadingText.setTypeface(teutonic);
                editLayout.addView(cheatedHeading, lp);
                View cheatedOptions = View.inflate(this, R.layout.e_item_radiogroup, null);
                RadioButton cheatedOptionOne = cheatedOptions.findViewById(R.id.option_one);
                RadioButton cheatedOptionTwo = cheatedOptions.findViewById(R.id.option_two);
                cheatedOptionOne.setId(R.id.cheated_one);
                cheatedOptionTwo.setId(R.id.cheated_two);
                cheatedOptionOne.setText(getString(R.string.not_cheated).trim());
                cheatedOptionTwo.setText(getString(R.string.cheated).trim());
                cheatedOptionOne.setTypeface(arnopro);
                cheatedOptionTwo.setTypeface(arnopro);
                editLayout.addView(cheatedOptions, lp);
                if (globalVariables.InvestigatorsCheated == 0) {
                    cheatedOptionOne.setChecked(true);
                } else if (globalVariables.InvestigatorsCheated == 1) {
                    cheatedOptionTwo.setChecked(true);
                }
            }

            if (scenario > 3) {
                // Henry Armitage
                View armitageHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView armitageHeadingText = armitageHeading.findViewById(R.id.heading);
                armitageHeadingText.setText(R.string.henry_armitage);
                armitageHeadingText.setTypeface(teutonic);
                editLayout.addView(armitageHeading, lp);
                View armitageOptions = View.inflate(this, R.layout.e_item_radiogroup, null);
                RadioButton armitageOptionOne = armitageOptions.findViewById(R.id.option_one);
                RadioButton armitageOptionTwo = armitageOptions.findViewById(R.id.option_two);
                RadioButton armitageOptionThree = armitageOptions.findViewById(R.id.option_three);
                RadioButton armitageOptionFour = armitageOptions.findViewById(R.id.option_four);
                if (scenario > 6) {
                    armitageOptionOne.setVisibility(GONE);
                    armitageOptionTwo.setVisibility(GONE);
                    armitageOptionThree.setVisibility(VISIBLE);
                    armitageOptionFour.setVisibility(VISIBLE);
                }
                armitageOptionOne.setId(R.id.armitage_one);
                armitageOptionTwo.setId(R.id.armitage_two);
                armitageOptionThree.setId(R.id.armitage_three);
                armitageOptionFour.setId(R.id.armitage_four);
                armitageOptionOne.setText(getString(R.string.armitage_kidnapped).trim());
                armitageOptionTwo.setText(getString(R.string.armitage_rescued).trim());
                armitageOptionThree.setText(getString(R.string.armitage_sacrificed).trim());
                armitageOptionFour.setText(getString(R.string.armitage_survived).trim());
                armitageOptionOne.setTypeface(arnopro);
                armitageOptionTwo.setTypeface(arnopro);
                armitageOptionThree.setTypeface(arnopro);
                armitageOptionFour.setTypeface(arnopro);
                editLayout.addView(armitageOptions, lp);
                if (globalVariables.HenryArmitage == 0) {
                    armitageOptionOne.setChecked(true);
                } else if (globalVariables.HenryArmitage == 1) {
                    armitageOptionTwo.setChecked(true);
                } else if (globalVariables.HenryArmitage == 2) {
                    armitageOptionThree.setChecked(true);
                } else if (globalVariables.HenryArmitage == 3) {
                    armitageOptionFour.setChecked(true);
                }
            }

            if (scenario > 4) {
                // Necronomicon
                View necronomiconHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView necronomiconHeadingText = necronomiconHeading.findViewById(R.id.heading);
                necronomiconHeadingText.setText(R.string.necronomicon_heading);
                necronomiconHeadingText.setTypeface(teutonic);
                editLayout.addView(necronomiconHeading, lp);
                View necronomiconOptions = View.inflate(this, R.layout.e_item_radiogroup, null);
                RadioButton necronomiconOptionOne = necronomiconOptions.findViewById(R.id.option_one);
                RadioButton necronomiconOptionTwo = necronomiconOptions.findViewById(R.id.option_two);
                RadioButton necronomiconOptionThree = necronomiconOptions.findViewById(R.id.option_three);
                RadioButton necronomiconOptionFour = necronomiconOptions.findViewById(R.id.option_four);
                necronomiconOptionThree.setVisibility(VISIBLE);
                if (scenario > 5) {
                    necronomiconOptionFour.setVisibility(VISIBLE);
                }
                necronomiconOptionOne.setId(R.id.necronomicon_one);
                necronomiconOptionTwo.setId(R.id.necronomicon_two);
                necronomiconOptionThree.setId(R.id.necronomicon_three);
                necronomiconOptionFour.setId(R.id.necronomicon_four);
                necronomiconOptionOne.setText(getString(R.string.necronomicon_failed).trim());
                necronomiconOptionTwo.setText(getString(R.string.necronomicon_destroyed).trim());
                necronomiconOptionThree.setText(getString(R.string.necronomicon_taken).trim());
                necronomiconOptionFour.setText(getString(R.string.necronomicon_stolen).trim());
                necronomiconOptionOne.setTypeface(arnopro);
                necronomiconOptionTwo.setTypeface(arnopro);
                necronomiconOptionThree.setTypeface(arnopro);
                necronomiconOptionFour.setTypeface(arnopro);
                editLayout.addView(necronomiconOptions, lp);
                if (globalVariables.Necronomicon == 0) {
                    necronomiconOptionOne.setChecked(true);
                } else if (globalVariables.Necronomicon == 1) {
                    necronomiconOptionTwo.setChecked(true);
                } else if (globalVariables.Necronomicon == 2) {
                    necronomiconOptionThree.setChecked(true);
                } else if (globalVariables.Necronomicon == 3) {
                    necronomiconOptionFour.setChecked(true);
                }

                // Adam Lynch/Harold Walsted
                View lynchHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView lynchHeadingText = lynchHeading.findViewById(R.id.heading);
                lynchHeadingText.setText(R.string.lynch_heading);
                lynchHeadingText.setTypeface(teutonic);
                editLayout.addView(lynchHeading, lp);
                View lynchOptions = View.inflate(this, R.layout.e_item_radiogroup, null);
                RadioButton lynchOptionOne = lynchOptions.findViewById(R.id.option_one);
                RadioButton lynchOptionTwo = lynchOptions.findViewById(R.id.option_two);
                lynchOptionOne.setId(R.id.lynch_one);
                lynchOptionTwo.setId(R.id.lynch_two);
                lynchOptionOne.setText(getString(R.string.not_lynch).trim());
                lynchOptionTwo.setText(getString(R.string.adam_lynch_harold_walsted).trim());
                lynchOptionOne.setTypeface(arnopro);
                lynchOptionTwo.setTypeface(arnopro);
                editLayout.addView(lynchOptions, lp);
                if (globalVariables.AdamLynchHaroldWalsted == 0) {
                    lynchOptionOne.setChecked(true);
                } else if (globalVariables.AdamLynchHaroldWalsted == 1) {
                    lynchOptionTwo.setChecked(true);
                }
            }

            if (scenario > 5) {
                // Investigators delayed
                View delayedHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView delayedHeadingText = delayedHeading.findViewById(R.id.heading);
                delayedHeadingText.setText(R.string.delayed_heading);
                delayedHeadingText.setTypeface(teutonic);
                editLayout.addView(delayedHeading, lp);
                View delayedOptions = View.inflate(this, R.layout.e_item_radiogroup, null);
                RadioButton delayedOptionOne = delayedOptions.findViewById(R.id.option_one);
                RadioButton delayedOptionTwo = delayedOptions.findViewById(R.id.option_two);
                delayedOptionOne.setId(R.id.delayed_one);
                delayedOptionTwo.setId(R.id.delayed_two);
                delayedOptionOne.setText(getString(R.string.not_delayed).trim());
                delayedOptionTwo.setText(getString(R.string.investigators_delayed).trim());
                delayedOptionOne.setTypeface(arnopro);
                delayedOptionTwo.setTypeface(arnopro);
                editLayout.addView(delayedOptions, lp);
                if (globalVariables.InvestigatorsDelayed == 0) {
                    delayedOptionOne.setChecked(true);
                } else if (globalVariables.InvestigatorsDelayed == 1) {
                    delayedOptionTwo.setChecked(true);
                }
            }

            if (scenario > 6) {
                // Silas Bishop
                View silasHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView silasHeadingText = silasHeading.findViewById(R.id.heading);
                silasHeadingText.setText(R.string.silas_heading);
                silasHeadingText.setTypeface(teutonic);
                editLayout.addView(silasHeading, lp);
                View silasOptions = View.inflate(this, R.layout.e_item_radiogroup, null);
                RadioButton silasOptionOne = silasOptions.findViewById(R.id.option_one);
                RadioButton silasOptionTwo = silasOptions.findViewById(R.id.option_two);
                RadioButton silasOptionThree = silasOptions.findViewById(R.id.option_three);
                RadioButton silasOptionFour = silasOptions.findViewById(R.id.option_four);
                silasOptionThree.setVisibility(VISIBLE);
                silasOptionFour.setVisibility(VISIBLE);
                silasOptionOne.setId(R.id.silas_one);
                silasOptionTwo.setId(R.id.silas_two);
                silasOptionThree.setId(R.id.silas_three);
                silasOptionFour.setId(R.id.silas_four);
                silasOptionOne.setText(getString(R.string.silas_ritual).trim());
                silasOptionTwo.setText(getString(R.string.silas_misery).trim());
                silasOptionThree.setText(getString(R.string.silas_restored).trim());
                silasOptionFour.setText(getString(R.string.silas_banished).trim());
                silasOptionOne.setTypeface(arnopro);
                silasOptionTwo.setTypeface(arnopro);
                silasOptionThree.setTypeface(arnopro);
                silasOptionFour.setTypeface(arnopro);
                editLayout.addView(silasOptions, lp);
                if (globalVariables.SilasBishop == 0) {
                    silasOptionOne.setChecked(true);
                } else if (globalVariables.SilasBishop == 1) {
                    silasOptionTwo.setChecked(true);
                } else if (globalVariables.SilasBishop == 2) {
                    silasOptionThree.setChecked(true);
                } else if (globalVariables.SilasBishop == 3) {
                    silasOptionFour.setChecked(true);
                }

                // Zebulon Whateley
                View zebulonHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView zebulonHeadingText = zebulonHeading.findViewById(R.id.heading);
                zebulonHeadingText.setText(R.string.zebulon_whateley);
                zebulonHeadingText.setTypeface(teutonic);
                editLayout.addView(zebulonHeading, lp);
                View zebulonOptions = View.inflate(this, R.layout.e_item_radiogroup, null);
                RadioButton zebulonOptionOne = zebulonOptions.findViewById(R.id.option_one);
                RadioButton zebulonOptionTwo = zebulonOptions.findViewById(R.id.option_two);
                zebulonOptionOne.setId(R.id.zebulon_one);
                zebulonOptionTwo.setId(R.id.zebulon_two);
                zebulonOptionOne.setText(getString(R.string.zebulon_survived).trim());
                zebulonOptionTwo.setText(getString(R.string.zebulon_sacrificed).trim());
                zebulonOptionOne.setTypeface(arnopro);
                zebulonOptionTwo.setTypeface(arnopro);
                editLayout.addView(zebulonOptions, lp);
                if (globalVariables.ZebulonWhateley == 0) {
                    zebulonOptionOne.setChecked(true);
                } else if (globalVariables.ZebulonWhateley == 1) {
                    zebulonOptionTwo.setChecked(true);
                }

                // Earl Sawyer
                View earlHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView earlHeadingText = earlHeading.findViewById(R.id.heading);
                earlHeadingText.setText(R.string.earl_sawyer);
                earlHeadingText.setTypeface(teutonic);
                editLayout.addView(earlHeading, lp);
                View earlOptions = View.inflate(this, R.layout.e_item_radiogroup, null);
                RadioButton earlOptionOne = earlOptions.findViewById(R.id.option_one);
                RadioButton earlOptionTwo = earlOptions.findViewById(R.id.option_two);
                earlOptionOne.setId(R.id.sawyer_one);
                earlOptionTwo.setId(R.id.sawyer_two);
                earlOptionOne.setText(getString(R.string.sawyer_survived).trim());
                earlOptionTwo.setText(getString(R.string.sawyer_sacrificed).trim());
                earlOptionOne.setTypeface(arnopro);
                earlOptionTwo.setTypeface(arnopro);
                editLayout.addView(earlOptions, lp);
                if (globalVariables.EarlSawyer == 0) {
                    earlOptionOne.setChecked(true);
                } else if (globalVariables.EarlSawyer == 1) {
                    earlOptionTwo.setChecked(true);
                }

                // Ally Sacrificed
                View allyHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView allyHeadingText = allyHeading.findViewById(R.id.heading);
                allyHeadingText.setText(R.string.ally_heading);
                allyHeadingText.setTypeface(teutonic);
                editLayout.addView(allyHeading, lp);
                View allyOptions = View.inflate(this, R.layout.e_item_radiogroup, null);
                RadioButton allyOptionOne = allyOptions.findViewById(R.id.option_one);
                RadioButton allyOptionTwo = allyOptions.findViewById(R.id.option_two);
                allyOptionOne.setId(R.id.ally_one);
                allyOptionTwo.setId(R.id.ally_two);
                allyOptionOne.setText(getString(R.string.ally_not_sacrificed).trim());
                allyOptionTwo.setText(getString(R.string.ally_sacrificed).trim());
                allyOptionOne.setTypeface(arnopro);
                allyOptionTwo.setTypeface(arnopro);
                editLayout.addView(allyOptions, lp);
                if (globalVariables.AllySacrificed == 0) {
                    allyOptionOne.setChecked(true);
                } else if (globalVariables.AllySacrificed == 1) {
                    allyOptionTwo.setChecked(true);
                }
            }

            if (scenario > 8) {
                // Townsfolk action
                View townsfolkHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView townsfolkHeadingText = townsfolkHeading.findViewById(R.id.heading);
                townsfolkHeadingText.setText(R.string.townsfolk_heading);
                townsfolkHeadingText.setTypeface(teutonic);
                editLayout.addView(townsfolkHeading, lp);
                View townsfolkOptions = View.inflate(this, R.layout.e_item_radiogroup, null);
                RadioButton townsfolkOptionOne = townsfolkOptions.findViewById(R.id.option_one);
                RadioButton townsfolkOptionTwo = townsfolkOptions.findViewById(R.id.option_two);
                townsfolkOptionOne.setId(R.id.townsfolk_one);
                townsfolkOptionTwo.setId(R.id.townsfolk_two);
                townsfolkOptionOne.setText(getString(R.string.townsfolk_calmed).trim());
                townsfolkOptionTwo.setText(getString(R.string.townsfolk_warned).trim());
                townsfolkOptionOne.setTypeface(arnopro);
                townsfolkOptionTwo.setTypeface(arnopro);
                editLayout.addView(townsfolkOptions, lp);
                if (globalVariables.TownsfolkAction == 1) {
                    townsfolkOptionOne.setChecked(true);
                } else if (globalVariables.TownsfolkAction == 2) {
                    townsfolkOptionTwo.setChecked(true);
                }

                // Broods escaped
                View broodHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView broodHeadingText = broodHeading.findViewById(R.id.heading);
                broodHeadingText.setText(R.string.brood_heading);
                broodHeadingText.setTypeface(teutonic);
                editLayout.addView(broodHeading, lp);
                View broodCounter = View.inflate(this, R.layout.e_item_counter, null);
                final TextView broodAmount = broodCounter.findViewById(R.id.amount);
                broodAmount.setId(R.id.broods);
                broodAmount.setText(String.valueOf(globalVariables.BroodsEscaped));
                broodAmount.setTypeface(arnopro);
                ImageView broodDecrement = broodCounter.findViewById(R.id.decrement);
                broodDecrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int amount = Integer.valueOf(broodAmount.getText().toString());
                        if (amount > 0) {
                            amount += -1;
                            broodAmount.setText(String.valueOf(amount));
                        }
                    }
                });
                ImageView broodIncrement = broodCounter.findViewById(R.id.increment);
                broodIncrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int amount = Integer.valueOf(broodAmount.getText().toString());
                        if (amount < 5) {
                            amount += 1;
                            broodAmount.setText(String.valueOf(amount));
                        }
                    }
                });
                editLayout.addView(broodCounter, lp);
            }

            if (scenario > 9) {
                // Investigators Gate
                View gateHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView gateHeadingText = gateHeading.findViewById(R.id.heading);
                gateHeadingText.setText(R.string.gate_heading);
                gateHeadingText.setTypeface(teutonic);
                editLayout.addView(gateHeading, lp);
                View gateOptions = View.inflate(this, R.layout.e_item_radiogroup, null);
                RadioButton gateOptionOne = gateOptions.findViewById(R.id.option_one);
                RadioButton gateOptionTwo = gateOptions.findViewById(R.id.option_two);
                gateOptionOne.setId(R.id.gate_one);
                gateOptionTwo.setId(R.id.gate_two);
                gateOptionOne.setText(getString(R.string.yog_tore_barrier).trim());
                gateOptionTwo.setText(getString(R.string.investigators_entered_gate).trim());
                gateOptionOne.setTypeface(arnopro);
                gateOptionTwo.setTypeface(arnopro);
                editLayout.addView(gateOptions, lp);
                if (globalVariables.InvestigatorsGate == 0) {
                    gateOptionOne.setChecked(true);
                } else if (globalVariables.InvestigatorsGate == 1) {
                    gateOptionTwo.setChecked(true);
                }
            }

            if (scenario > 10) {
                // Yog-Sothoth
                View yogHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView yogHeadingText = yogHeading.findViewById(R.id.heading);
                yogHeadingText.setText(R.string.conclusion);
                yogHeadingText.setTypeface(teutonic);
                editLayout.addView(yogHeading, lp);
                View yogOptions = View.inflate(this, R.layout.e_item_radiogroup, null);
                RadioButton yogOptionsOne = yogOptions.findViewById(R.id.option_one);
                RadioButton yogOptionsTwo = yogOptions.findViewById(R.id.option_two);
                RadioButton yogOptionsThree = yogOptions.findViewById(R.id.option_three);
                yogOptionsThree.setVisibility(VISIBLE);
                yogOptionsOne.setId(R.id.yog_one);
                yogOptionsTwo.setId(R.id.yog_two);
                yogOptionsThree.setId(R.id.yog_three);
                yogOptionsOne.setText(getString(R.string.closed_tear).trim());
                yogOptionsTwo.setText(getString(R.string.yog_fled).trim());
                yogOptionsThree.setText(getString(R.string.yog_tore_barrier).trim());
                yogOptionsOne.setTypeface(arnopro);
                yogOptionsTwo.setTypeface(arnopro);
                yogOptionsThree.setTypeface(arnopro);
                editLayout.addView(yogOptions, lp);
                if (globalVariables.YogSothoth == 1) {
                    yogOptionsOne.setChecked(true);
                } else if (globalVariables.YogSothoth == 2) {
                    yogOptionsTwo.setChecked(true);
                } else if (globalVariables.YogSothoth == 3) {
                    yogOptionsThree.setChecked(true);
                }
            }
        }

        /*
            Path to Carcosa log
         */
        if (globalVariables.CurrentCampaign == 3) {

            if (scenario == 1) {
                // Nothing to show
                View nothing = View.inflate(this, R.layout.e_item_heading, null);
                TextView nothingText = nothing.findViewById(R.id.heading);
                nothingText.setText(R.string.nothing);
                nothingText.setTypeface(arnopro);
                nothingText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen
                        .arnopro_textsize));
                nothingText.setAllCaps(false);
                nothingText.setTextScaleX(1);
                lp.setMargins(16, 0, 16, 0);
                editLayout.addView(nothing, lp);
            }

            if (scenario > 1) {
                // Doubt
                View doubtHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView doubtHeadingText = doubtHeading.findViewById(R.id.heading);
                doubtHeadingText.setText(R.string.doubt_heading);
                doubtHeadingText.setTypeface(teutonic);
                editLayout.addView(doubtHeading, lp);
                View doubtCounter = View.inflate(this, R.layout.e_item_counter, null);
                final TextView doubtAmount = doubtCounter.findViewById(R.id.amount);
                doubtAmount.setId(R.id.doubt);
                doubtAmount.setText(String.valueOf(globalVariables.Doubt));
                doubtAmount.setTypeface(arnopro);
                ImageView doubtDecrement = doubtCounter.findViewById(R.id.decrement);
                doubtDecrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int amount = Integer.valueOf(doubtAmount.getText().toString());
                        if (amount > 0) {
                            amount += -1;
                            doubtAmount.setText(String.valueOf(amount));
                        }
                    }
                });
                ImageView doubtIncrement = doubtCounter.findViewById(R.id.increment);
                doubtIncrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int amount = Integer.valueOf(doubtAmount.getText().toString());
                        if (amount < 99) {
                            amount += 1;
                            doubtAmount.setText(String.valueOf(amount));
                        }
                    }
                });
                editLayout.addView(doubtCounter, lp);

                // Conviction
                View convictionHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView convictionHeadingText = convictionHeading.findViewById(R.id.heading);
                convictionHeadingText.setText(R.string.conviction_heading);
                convictionHeadingText.setTypeface(teutonic);
                editLayout.addView(convictionHeading, lp);
                View convictionCounter = View.inflate(this, R.layout.e_item_counter, null);
                final TextView convictionAmount = convictionCounter.findViewById(R.id.amount);
                convictionAmount.setId(R.id.conviction);
                convictionAmount.setText(String.valueOf(globalVariables.Conviction));
                convictionAmount.setTypeface(arnopro);
                ImageView convictionDecrement = convictionCounter.findViewById(R.id.decrement);
                convictionDecrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int amount = Integer.valueOf(convictionAmount.getText().toString());
                        if (amount > 0) {
                            amount += -1;
                            convictionAmount.setText(String.valueOf(amount));
                        }
                    }
                });
                ImageView convictionIncrement = convictionCounter.findViewById(R.id.increment);
                convictionIncrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int amount = Integer.valueOf(convictionAmount.getText().toString());
                        if (amount < 99) {
                            amount += 1;
                            convictionAmount.setText(String.valueOf(amount));
                        }
                    }
                });
                editLayout.addView(convictionCounter, lp);

                // Chasing the Stranger
                View chasingStrangerHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView chasingStrangerHeadingText = chasingStrangerHeading.findViewById(R.id.heading);
                chasingStrangerHeadingText.setText(R.string.chasing_stranger_heading);
                chasingStrangerHeadingText.setTypeface(teutonic);
                editLayout.addView(chasingStrangerHeading, lp);
                View strangerCounter = View.inflate(this, R.layout.e_item_counter, null);
                final TextView strangerAmount = strangerCounter.findViewById(R.id.amount);
                strangerAmount.setId(R.id.chasing_stranger);
                strangerAmount.setText(String.valueOf(globalVariables.ChasingStranger));
                strangerAmount.setTypeface(arnopro);
                ImageView strangerDecrement = strangerCounter.findViewById(R.id.decrement);
                strangerDecrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int amount = Integer.valueOf(strangerAmount.getText().toString());
                        if (amount > 0) {
                            amount += -1;
                            strangerAmount.setText(String.valueOf(amount));
                        }
                    }
                });
                ImageView strangerIncrement = strangerCounter.findViewById(R.id.increment);
                strangerIncrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int amount = Integer.valueOf(strangerAmount.getText().toString());
                        if (amount < 99) {
                            amount += 1;
                            strangerAmount.setText(String.valueOf(amount));
                        }
                    }
                });
                editLayout.addView(strangerCounter, lp);

                // Theatre (Chaos Bag)
                View theatreHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView theatreHeadingText = theatreHeading.findViewById(R.id.heading);
                theatreHeadingText.setText(R.string.theatre_heading);
                theatreHeadingText.setTypeface(teutonic);
                editLayout.addView(theatreHeading, lp);
                View theatreOptions = View.inflate(this, R.layout.e_item_radiogroup, null);
                RadioButton theatreOptionOne = theatreOptions.findViewById(R.id.option_one);
                RadioButton theatreOptionTwo = theatreOptions.findViewById(R.id.option_two);
                RadioButton theatreOptionThree = theatreOptions.findViewById(R.id.option_three);
                RadioButton theatreOptionFour = theatreOptions.findViewById(R.id.option_four);
                theatreOptionThree.setVisibility(VISIBLE);
                theatreOptionFour.setVisibility(VISIBLE);
                theatreOptionOne.setId(R.id.theatre_one);
                theatreOptionTwo.setId(R.id.theatre_two);
                theatreOptionThree.setId(R.id.theatre_three);
                theatreOptionFour.setId(R.id.theatre_four);
                theatreOptionOne.setText(getString(R.string.theatre_one).trim());
                theatreOptionTwo.setText(getString(R.string.theatre_two).trim());
                theatreOptionThree.setText(getString(R.string.theatre_three).trim());
                theatreOptionFour.setText(getString(R.string.theatre_four).trim());
                theatreOptionOne.setTypeface(arnopro);
                theatreOptionTwo.setTypeface(arnopro);
                theatreOptionThree.setTypeface(arnopro);
                theatreOptionFour.setTypeface(arnopro);
                editLayout.addView(theatreOptions, lp);
                if (globalVariables.Theatre == 1) {
                    theatreOptionOne.setChecked(true);
                } else if (globalVariables.Theatre == 2) {
                    theatreOptionTwo.setChecked(true);
                } else if (globalVariables.Theatre == 3) {
                    theatreOptionThree.setChecked(true);
                } else if (globalVariables.Theatre == 4) {
                    theatreOptionFour.setChecked(true);
                }

                // Stranger
                View strangerHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView strangerHeadingText = strangerHeading.findViewById(R.id.heading);
                strangerHeadingText.setText(R.string.stranger_heading);
                strangerHeadingText.setTypeface(teutonic);
                editLayout.addView(strangerHeading, lp);
                View strangerOptions = View.inflate(this, R.layout.e_item_radiogroup, null);
                RadioButton strangerOptionOne = strangerOptions.findViewById(R.id.option_one);
                RadioButton strangerOptionTwo = strangerOptions.findViewById(R.id.option_two);
                strangerOptionOne.setId(R.id.stranger_one);
                strangerOptionTwo.setId(R.id.stranger_two);
                strangerOptionOne.setText(getString(R.string.not_stranger).trim());
                strangerOptionTwo.setText(getString(R.string.stranger).trim());
                strangerOptionOne.setTypeface(arnopro);
                strangerOptionTwo.setTypeface(arnopro);
                editLayout.addView(strangerOptions, lp);
                if (globalVariables.Stranger == 0) {
                    strangerOptionOne.setChecked(true);
                } else if (globalVariables.Stranger == 1) {
                    strangerOptionTwo.setChecked(true);
                }

                // Police
                View policeHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView policeHeadingText = policeHeading.findViewById(R.id.heading);
                policeHeadingText.setText(R.string.police_heading);
                policeHeadingText.setTypeface(teutonic);
                editLayout.addView(policeHeading, lp);
                View policeOptions = View.inflate(this, R.layout.e_item_radiogroup, null);
                RadioButton policeOptionOne = policeOptions.findViewById(R.id.option_one);
                RadioButton policeOptionTwo = policeOptions.findViewById(R.id.option_two);
                RadioButton policeOptionThree = policeOptions.findViewById(R.id.option_three);
                RadioButton policeOptionFour = policeOptions.findViewById(R.id.option_four);
                policeOptionThree.setVisibility(VISIBLE);
                policeOptionFour.setVisibility(VISIBLE);
                policeOptionOne.setId(R.id.police_one);
                policeOptionTwo.setId(R.id.police_two);
                policeOptionThree.setId(R.id.police_three);
                policeOptionFour.setId(R.id.police_four);
                policeOptionOne.setText(getString(R.string.police_none).trim());
                policeOptionTwo.setText(getString(R.string.warned_police).trim());
                policeOptionThree.setText(getString(R.string.police_warned_and_suspicious).trim());
                policeOptionFour.setText(getString(R.string.not_police).trim());
                policeOptionOne.setTypeface(arnopro);
                policeOptionTwo.setTypeface(arnopro);
                policeOptionThree.setTypeface(arnopro);
                policeOptionFour.setTypeface(arnopro);
                editLayout.addView(policeOptions, lp);
                if (globalVariables.Police == 0) {
                    policeOptionOne.setChecked(true);
                } else if (globalVariables.Police == 1) {
                    policeOptionTwo.setChecked(true);
                } else if (globalVariables.Police == 2) {
                    policeOptionThree.setChecked(true);
                } else if (globalVariables.Police == 3) {
                    policeOptionFour.setChecked(true);
                }
            }

            if (scenario > 2) {
                // VIPS interviewed
                View interviewedHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView interviewedHeadingText = interviewedHeading.findViewById(R.id.heading);
                interviewedHeadingText.setText(R.string.vips_interviewed);
                interviewedHeadingText.setTypeface(teutonic);
                editLayout.addView(interviewedHeading, lp);
                View constanceInterviewed = View.inflate(this, R.layout.e_item_checkbox, null);
                CheckBox constanceInterviewedBox = constanceInterviewed.findViewById(R.id.checkbox);
                constanceInterviewedBox.setId(R.id.constance_interviewed);
                constanceInterviewedBox.setText(R.string.constance);
                constanceInterviewedBox.setTypeface(arnopro);
                editLayout.addView(constanceInterviewed, lp);
                if (globalVariables.Constance == 1 || globalVariables.Constance == 4) {
                    constanceInterviewedBox.setChecked(true);
                }
                View jordanInterviewed = View.inflate(this, R.layout.e_item_checkbox, null);
                CheckBox jordanInterviewedBox = jordanInterviewed.findViewById(R.id.checkbox);
                jordanInterviewedBox.setId(R.id.jordan_interviewed);
                jordanInterviewedBox.setText(R.string.jordan);
                jordanInterviewedBox.setTypeface(arnopro);
                editLayout.addView(jordanInterviewed, lp);
                if (globalVariables.Jordan == 1 || globalVariables.Jordan == 4) {
                    jordanInterviewedBox.setChecked(true);
                }
                View ishimaruInterviewed = View.inflate(this, R.layout.e_item_checkbox, null);
                CheckBox ishimaruInterviewedBox = ishimaruInterviewed.findViewById(R.id.checkbox);
                ishimaruInterviewedBox.setId(R.id.ishimaru_interviewed);
                ishimaruInterviewedBox.setText(R.string.ishimaru);
                ishimaruInterviewedBox.setTypeface(arnopro);
                editLayout.addView(ishimaruInterviewed, lp);
                if (globalVariables.Ishimaru == 1 || globalVariables.Ishimaru == 4) {
                    ishimaruInterviewedBox.setChecked(true);
                }
                View sebastienInterviewed = View.inflate(this, R.layout.e_item_checkbox, null);
                CheckBox sebastienInterviewedBox = sebastienInterviewed.findViewById(R.id.checkbox);
                sebastienInterviewedBox.setId(R.id.sebastien_interviewed);
                sebastienInterviewedBox.setText(R.string.sebastien);
                sebastienInterviewedBox.setTypeface(arnopro);
                editLayout.addView(sebastienInterviewed, lp);
                if (globalVariables.Sebastien == 1 || globalVariables.Sebastien == 4) {
                    sebastienInterviewedBox.setChecked(true);
                }
                View ashleighInterviewed = View.inflate(this, R.layout.e_item_checkbox, null);
                CheckBox ashleighInterviewedBox = ashleighInterviewed.findViewById(R.id.checkbox);
                ashleighInterviewedBox.setId(R.id.ashleigh_interviewed);
                ashleighInterviewedBox.setText(R.string.ashleigh);
                ashleighInterviewedBox.setTypeface(arnopro);
                editLayout.addView(ashleighInterviewed, lp);
                if (globalVariables.Ashleigh == 1 || globalVariables.Ashleigh == 4) {
                    ashleighInterviewedBox.setChecked(true);
                }

                // VIPS slain
                View slainHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView slainHeadingText = slainHeading.findViewById(R.id.heading);
                slainHeadingText.setText(R.string.vips_slain);
                slainHeadingText.setTypeface(teutonic);
                editLayout.addView(slainHeading, lp);
                View constanceSlain = View.inflate(this, R.layout.e_item_checkbox, null);
                CheckBox constanceSlainBox = constanceSlain.findViewById(R.id.checkbox);
                constanceSlainBox.setId(R.id.constance_slain);
                constanceSlainBox.setText(R.string.constance);
                constanceSlainBox.setTypeface(arnopro);
                editLayout.addView(constanceSlain, lp);
                if (globalVariables.Constance == 2 || globalVariables.Constance == 4 || globalVariables.Constance ==
                        5) {
                    constanceSlainBox.setChecked(true);
                }
                View jordanSlain = View.inflate(this, R.layout.e_item_checkbox, null);
                CheckBox jordanSlainBox = jordanSlain.findViewById(R.id.checkbox);
                jordanSlainBox.setId(R.id.jordan_slain);
                jordanSlainBox.setText(R.string.jordan);
                jordanSlainBox.setTypeface(arnopro);
                editLayout.addView(jordanSlain, lp);
                if (globalVariables.Jordan == 2 || globalVariables.Jordan == 4 || globalVariables.Jordan == 5) {
                    jordanSlainBox.setChecked(true);
                }
                View ishimaruSlain = View.inflate(this, R.layout.e_item_checkbox, null);
                CheckBox ishimaruSlainBox = ishimaruSlain.findViewById(R.id.checkbox);
                ishimaruSlainBox.setId(R.id.ishimaru_slain);
                ishimaruSlainBox.setText(R.string.ishimaru);
                ishimaruSlainBox.setTypeface(arnopro);
                editLayout.addView(ishimaruSlain, lp);
                if (globalVariables.Ishimaru == 2 || globalVariables.Ishimaru == 4 || globalVariables.Ishimaru == 5) {
                    ishimaruSlainBox.setChecked(true);
                }
                View sebastienSlain = View.inflate(this, R.layout.e_item_checkbox, null);
                CheckBox sebastienSlainBox = sebastienSlain.findViewById(R.id.checkbox);
                sebastienSlainBox.setId(R.id.sebastien_slain);
                sebastienSlainBox.setText(R.string.sebastien);
                sebastienSlainBox.setTypeface(arnopro);
                editLayout.addView(sebastienSlain, lp);
                if (globalVariables.Sebastien == 2 || globalVariables.Sebastien == 4 || globalVariables.Sebastien ==
                        5) {
                    sebastienSlainBox.setChecked(true);
                }
                View ashleighSlain = View.inflate(this, R.layout.e_item_checkbox, null);
                CheckBox ashleighSlainBox = ashleighSlain.findViewById(R.id.checkbox);
                ashleighSlainBox.setId(R.id.ashleigh_slain);
                ashleighSlainBox.setText(R.string.ashleigh);
                ashleighSlainBox.setTypeface(arnopro);
                editLayout.addView(ashleighSlain, lp);
                if (globalVariables.Ashleigh == 2 || globalVariables.Ashleigh == 4 || globalVariables.Ashleigh == 5) {
                    ashleighSlainBox.setChecked(true);
                }
            }

            if (scenario > 3) {
                // Party
                View partyHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView partyHeadingText = partyHeading.findViewById(R.id.heading);
                partyHeadingText.setText(R.string.party_heading);
                partyHeadingText.setTypeface(teutonic);
                editLayout.addView(partyHeading, lp);
                View partyOptions = View.inflate(this, R.layout.e_item_radiogroup, null);
                RadioButton partyOptionOne = partyOptions.findViewById(R.id.option_one);
                RadioButton partyOptionTwo = partyOptions.findViewById(R.id.option_two);
                RadioButton partyOptionThree = partyOptions.findViewById(R.id.option_three);
                RadioButton partyOptionFour = partyOptions.findViewById(R.id.option_four);
                partyOptionThree.setVisibility(VISIBLE);
                partyOptionFour.setVisibility(VISIBLE);
                partyOptionOne.setId(R.id.party_one);
                partyOptionTwo.setId(R.id.party_two);
                partyOptionThree.setId(R.id.party_three);
                partyOptionFour.setId(R.id.party_four);
                partyOptionOne.setText(getString(R.string.no_party).trim());
                partyOptionTwo.setText(getString(R.string.intruded).trim());
                partyOptionThree.setText(getString(R.string.fled).trim());
                partyOptionFour.setText(getString(R.string.slayed).trim());
                partyOptionOne.setTypeface(arnopro);
                partyOptionTwo.setTypeface(arnopro);
                partyOptionThree.setTypeface(arnopro);
                partyOptionFour.setTypeface(arnopro);
                editLayout.addView(partyOptions, lp);
                if (globalVariables.Party == 0) {
                    partyOptionOne.setChecked(true);
                } else if (globalVariables.Party == 1) {
                    partyOptionTwo.setChecked(true);
                } else if (globalVariables.Party == 2) {
                    partyOptionThree.setChecked(true);
                } else if (globalVariables.Party == 3) {
                    partyOptionFour.setChecked(true);
                }
            }

            if (scenario > 4) {
                // Onyx
                View onyxHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView onyxHeadingText = onyxHeading.findViewById(R.id.heading);
                onyxHeadingText.setText(R.string.onyx_heading);
                onyxHeadingText.setTypeface(teutonic);
                editLayout.addView(onyxHeading, lp);
                View onyxOptions = View.inflate(this, R.layout.e_item_radiogroup, null);
                RadioButton onyxOptionOne = onyxOptions.findViewById(R.id.option_one);
                RadioButton onyxOptionTwo = onyxOptions.findViewById(R.id.option_two);
                RadioButton onyxOptionThree = onyxOptions.findViewById(R.id.option_three);
                RadioButton onyxOptionFour = onyxOptions.findViewById(R.id.option_four);
                onyxOptionThree.setVisibility(VISIBLE);
                onyxOptionFour.setVisibility(VISIBLE);
                onyxOptionOne.setId(R.id.onyx_one);
                onyxOptionTwo.setId(R.id.onyx_two);
                onyxOptionThree.setId(R.id.onyx_three);
                onyxOptionFour.setId(R.id.onyx_four);
                onyxOptionOne.setText(getString(R.string.onyx_taken).trim());
                onyxOptionTwo.setText(getString(R.string.onyx_left).trim());
                onyxOptionThree.setText(getString(R.string.oathspeaker_destroyed).trim());
                onyxOptionFour.setText(getString(R.string.followers_sign).trim());
                onyxOptionOne.setTypeface(arnopro);
                onyxOptionTwo.setTypeface(arnopro);
                onyxOptionThree.setTypeface(arnopro);
                onyxOptionFour.setTypeface(arnopro);
                editLayout.addView(onyxOptions, lp);
                if (globalVariables.Onyx == 1) {
                    onyxOptionOne.setChecked(true);
                } else if (globalVariables.Onyx == 2) {
                    onyxOptionTwo.setChecked(true);
                } else if (globalVariables.Onyx == 3) {
                    onyxOptionThree.setChecked(true);
                } else if (globalVariables.Onyx == 4) {
                    onyxOptionFour.setChecked(true);
                }
            }

            if (scenario > 5) {
                // Asylum
                View asylumHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView asylumHeadingText = asylumHeading.findViewById(R.id.heading);
                asylumHeadingText.setText(R.string.asylum_heading);
                asylumHeadingText.setTypeface(teutonic);
                editLayout.addView(asylumHeading, lp);
                View asylumOptions = View.inflate(this, R.layout.e_item_radiogroup, null);
                RadioButton asylumOptionOne = asylumOptions.findViewById(R.id.option_one);
                RadioButton asylumOptionTwo = asylumOptions.findViewById(R.id.option_two);
                RadioButton asylumOptionThree = asylumOptions.findViewById(R.id.option_three);
                asylumOptionThree.setVisibility(VISIBLE);
                asylumOptionOne.setId(R.id.asylum_one);
                asylumOptionTwo.setId(R.id.asylum_two);
                asylumOptionThree.setId(R.id.asylum_three);
                asylumOptionOne.setText(getString(R.string.king_victims).trim());
                asylumOptionTwo.setText(getString(R.string.attacked_asylum).trim());
                asylumOptionThree.setText(getString(R.string.escaped_asylum).trim());
                asylumOptionOne.setTypeface(arnopro);
                asylumOptionTwo.setTypeface(arnopro);
                asylumOptionThree.setTypeface(arnopro);
                editLayout.addView(asylumOptions, lp);
                if (globalVariables.Asylum == 1) {
                    asylumOptionOne.setChecked(true);
                } else if (globalVariables.Asylum == 2) {
                    asylumOptionTwo.setChecked(true);
                } else if (globalVariables.Asylum == 3) {
                    asylumOptionThree.setChecked(true);
                }
            }

            if (scenario > 6) {
                // Daniel's Warning
                View danielHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView danielHeadingText = danielHeading.findViewById(R.id.heading);
                danielHeadingText.setText(R.string.daniel_heading);
                danielHeadingText.setTypeface(teutonic);
                editLayout.addView(danielHeading, lp);
                View danielOptions = View.inflate(this, R.layout.e_item_radiogroup, null);
                RadioButton danielOptionOne = danielOptions.findViewById(R.id.option_one);
                RadioButton danielOptionTwo = danielOptions.findViewById(R.id.option_two);
                RadioButton danielOptionThree = danielOptions.findViewById(R.id.option_three);
                danielOptionThree.setVisibility(VISIBLE);
                danielOptionOne.setId(R.id.daniel_one);
                danielOptionTwo.setId(R.id.daniel_two);
                danielOptionThree.setId(R.id.daniel_three);
                danielOptionOne.setText(getString(R.string.no_daniel).trim());
                danielOptionTwo.setText(getString(R.string.warning_ignored).trim());
                danielOptionThree.setText(getString(R.string.warning_heeded).trim());
                danielOptionOne.setTypeface(arnopro);
                danielOptionTwo.setTypeface(arnopro);
                danielOptionThree.setTypeface(arnopro);
                editLayout.addView(danielOptions, lp);
                if (globalVariables.DanielsWarning == 0) {
                    danielOptionOne.setChecked(true);
                } else if (globalVariables.DanielsWarning == 1) {
                    danielOptionTwo.setChecked(true);
                } else if (globalVariables.DanielsWarning == 2) {
                    danielOptionThree.setChecked(true);
                }
            }

            if (scenario > 7) {
                // Nigel
                View nigelHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView nigelHeadingText = nigelHeading.findViewById(R.id.heading);
                nigelHeadingText.setText(R.string.nigel_heading);
                nigelHeadingText.setTypeface(teutonic);
                editLayout.addView(nigelHeading, lp);
                View nigelOptions = View.inflate(this, R.layout.e_item_radiogroup, null);
                RadioButton nigelOptionOne = nigelOptions.findViewById(R.id.option_one);
                RadioButton nigelOptionTwo = nigelOptions.findViewById(R.id.option_two);
                RadioButton nigelOptionThree = nigelOptions.findViewById(R.id.option_three);
                RadioButton nigelOptionFour = nigelOptions.findViewById(R.id.option_four);
                nigelOptionThree.setVisibility(VISIBLE);
                nigelOptionFour.setVisibility(VISIBLE);
                nigelOptionOne.setId(R.id.nigel_one);
                nigelOptionTwo.setId(R.id.nigel_two);
                nigelOptionThree.setId(R.id.nigel_three);
                nigelOptionFour.setId(R.id.nigel_four);
                nigelOptionOne.setText(getString(R.string.not_escape_gaze).trim());
                nigelOptionTwo.setText(getString(R.string.found_nigel_home).trim());
                nigelOptionThree.setText(getString(R.string.found_nigel_engram).trim());
                nigelOptionFour.setText(getString(R.string.unable_find_nigel).trim());
                nigelOptionOne.setTypeface(arnopro);
                nigelOptionTwo.setTypeface(arnopro);
                nigelOptionThree.setTypeface(arnopro);
                nigelOptionFour.setTypeface(arnopro);
                editLayout.addView(nigelOptions, lp);
                if (globalVariables.Nigel == 0) {
                    nigelOptionOne.setChecked(true);
                } else if (globalVariables.Nigel == 1) {
                    nigelOptionTwo.setChecked(true);
                } else if (globalVariables.Nigel == 2) {
                    nigelOptionThree.setChecked(true);
                } else if (globalVariables.Nigel == 3) {
                    nigelOptionFour.setChecked(true);
                }
            }

            if (scenario > 8) {
                // Investigators who read Act II
                View readActHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView readActHeadingText = readActHeading.findViewById(R.id.heading);
                readActHeadingText.setText(R.string.select_read_act);
                readActHeadingText.setTypeface(teutonic);
                editLayout.addView(readActHeading, lp);
                String[] investigatorNames = getResources().getStringArray(R.array.investigators);
                View investigatorOneReadAct = View.inflate(this, R.layout.e_item_checkbox, null);
                CheckBox invOneReadActBox = investigatorOneReadAct.findViewById(R.id.checkbox);
                invOneReadActBox.setId(R.id.inv_one_read_act);
                invOneReadActBox.setTypeface(arnopro);
                editLayout.addView(investigatorOneReadAct, lp);
                if (globalVariables.InvOneReadAct > 0) {
                    invOneReadActBox.setChecked(true);
                    if (globalVariables.InvOneReadAct == 999) {
                        invOneReadActBox.setText(getString(R.string.inv_one_read_act).trim());
                    } else {
                        invOneReadActBox.setText(investigatorNames[globalVariables.InvOneReadAct] + " " + getString(R
                                .string.read_act)
                                .trim());
                    }
                } else {
                    invOneReadActBox.setText(getString(R.string.inv_one_read_act).trim());
                }
                View investigatorTwoReadAct = View.inflate(this, R.layout.e_item_checkbox, null);
                CheckBox invTwoReadActBox = investigatorTwoReadAct.findViewById(R.id.checkbox);
                invTwoReadActBox.setId(R.id.inv_two_read_act);
                invTwoReadActBox.setTypeface(arnopro);
                editLayout.addView(investigatorTwoReadAct, lp);
                if (globalVariables.InvTwoReadAct > 0) {
                    invTwoReadActBox.setChecked(true);
                    if (globalVariables.InvTwoReadAct == 999) {
                        invTwoReadActBox.setText(getString(R.string.inv_two_read_act).trim());
                    } else {
                        invTwoReadActBox.setText(investigatorNames[globalVariables.InvTwoReadAct] + " " + getString(R
                                .string.read_act)
                                .trim());
                    }
                } else {
                    invTwoReadActBox.setText(getString(R.string.inv_two_read_act).trim());
                }
                View investigatorThreeReadAct = View.inflate(this, R.layout.e_item_checkbox, null);
                CheckBox invThreeReadActBox = investigatorThreeReadAct.findViewById(R.id.checkbox);
                invThreeReadActBox.setId(R.id.inv_three_read_act);
                invThreeReadActBox.setTypeface(arnopro);
                editLayout.addView(investigatorThreeReadAct, lp);
                if (globalVariables.InvThreeReadAct > 0) {
                    invThreeReadActBox.setChecked(true);
                    if (globalVariables.InvThreeReadAct == 999) {
                        invThreeReadActBox.setText(getString(R.string.inv_three_read_act).trim());
                    } else {
                        invThreeReadActBox.setText(investigatorNames[globalVariables.InvThreeReadAct] + " " +
                                getString(R
                                        .string
                                        .read_act)
                                        .trim());
                    }
                } else {
                    invThreeReadActBox.setText(getString(R.string.inv_three_read_act).trim());
                }
                View investigatorFourReadAct = View.inflate(this, R.layout.e_item_checkbox, null);
                CheckBox invFourReadActBox = investigatorFourReadAct.findViewById(R.id.checkbox);
                invFourReadActBox.setId(R.id.inv_four_read_act);
                invFourReadActBox.setTypeface(arnopro);
                editLayout.addView(investigatorFourReadAct, lp);
                if (globalVariables.InvFourReadAct > 0) {
                    invFourReadActBox.setChecked(true);
                    if (globalVariables.InvFourReadAct == 999) {
                        invFourReadActBox.setText(getString(R.string.inv_four_read_act).trim());
                    } else {
                        invFourReadActBox.setText(investigatorNames[globalVariables.InvFourReadAct] + " " + getString(R
                                .string.read_act)
                                .trim());
                    }
                } else {
                    invFourReadActBox.setText(getString(R.string.inv_four_read_act).trim());
                }
            }

            if (scenario > 9) {
                // Path to Carcosa
                View pathHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView pathHeadingText = pathHeading.findViewById(R.id.heading);
                pathHeadingText.setText(R.string.path_heading);
                pathHeadingText.setTypeface(teutonic);
                editLayout.addView(pathHeading, lp);
                View pathOptions = View.inflate(this, R.layout.e_item_radiogroup, null);
                RadioButton pathOptionOne = pathOptions.findViewById(R.id.option_one);
                RadioButton pathOptionTwo = pathOptions.findViewById(R.id.option_two);
                RadioButton pathOptionThree = pathOptions.findViewById(R.id.option_three);
                pathOptionThree.setVisibility(VISIBLE);
                pathOptionOne.setId(R.id.path_one);
                pathOptionTwo.setId(R.id.path_two);
                pathOptionThree.setId(R.id.path_three);
                pathOptionOne.setText(getString(R.string.path_below).trim());
                pathOptionTwo.setText(getString(R.string.path_above).trim());
                pathOptionThree.setText(getString(R.string.carcosa_merged).trim());
                pathOptionOne.setTypeface(arnopro);
                pathOptionTwo.setTypeface(arnopro);
                pathOptionThree.setTypeface(arnopro);
                editLayout.addView(pathOptions, lp);
                if (globalVariables.Path == 0) {
                    pathOptionThree.setChecked(true);
                } else if (globalVariables.Path == 1) {
                    pathOptionOne.setChecked(true);
                } else if (globalVariables.Path == 2) {
                    pathOptionTwo.setChecked(true);
                }
            }

            if (scenario > 10 && globalVariables.Path != 0) {
                // Hastur
                View hasturHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView hasturHeadingText = hasturHeading.findViewById(R.id.heading);
                hasturHeadingText.setText(R.string.hastur_heading);
                hasturHeadingText.setTypeface(teutonic);
                editLayout.addView(hasturHeading, lp);
                View hasturOptions = View.inflate(this, R.layout.e_item_radiogroup, null);
                RadioButton hasturOptionOne = hasturOptions.findViewById(R.id.option_one);
                RadioButton hasturOptionTwo = hasturOptions.findViewById(R.id.option_two);
                RadioButton hasturOptionThree = hasturOptions.findViewById(R.id.option_three);
                hasturOptionThree.setVisibility(VISIBLE);
                hasturOptionOne.setId(R.id.hastur_one);
                hasturOptionTwo.setId(R.id.hastur_two);
                hasturOptionThree.setId(R.id.hastur_three);
                hasturOptionOne.setText(getString(R.string.prevented_hastur).trim());
                hasturOptionTwo.setText(getString(R.string.carcosa_merged).trim());
                hasturOptionThree.setText(getString(R.string.hasturs_grasp).trim());
                hasturOptionOne.setTypeface(arnopro);
                hasturOptionTwo.setTypeface(arnopro);
                hasturOptionThree.setTypeface(arnopro);
                editLayout.addView(hasturOptions, lp);
                if (globalVariables.Hastur <= 3) {
                    hasturOptionOne.setChecked(true);
                } else if (globalVariables.Hastur == 4) {
                    hasturOptionTwo.setChecked(true);
                } else if (globalVariables.Hastur == 5) {
                    hasturOptionThree.setChecked(true);
                }

                // Investigators who were possessed
                View possessedHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView possessedHeadingText = possessedHeading.findViewById(R.id.heading);
                possessedHeadingText.setText(R.string.possessed_heading);
                possessedHeadingText.setTypeface(teutonic);
                editLayout.addView(possessedHeading, lp);
                String[] investigatorNames = getResources().getStringArray(R.array.investigators);
                View investigatorOnePossessed = View.inflate(this, R.layout.e_item_checkbox, null);
                CheckBox invOnePossessedBox = investigatorOnePossessed.findViewById(R.id.checkbox);
                invOnePossessedBox.setId(R.id.inv_one_possessed);
                invOnePossessedBox.setTypeface(arnopro);
                editLayout.addView(investigatorOnePossessed, lp);
                if (globalVariables.InvOnePossessed > 0) {
                    invOnePossessedBox.setChecked(true);
                    if (globalVariables.InvOnePossessed == 999) {
                        invOnePossessedBox.setText(getString(R.string.inv_one_possessed).trim());
                    } else {
                        invOnePossessedBox.setText(investigatorNames[globalVariables.InvOnePossessed] + " " +
                                getString(R
                                        .string.possessed)
                                        .trim());
                    }
                } else {
                    invOnePossessedBox.setText(getString(R.string.inv_one_possessed).trim());
                }
                View investigatorTwoPossessed = View.inflate(this, R.layout.e_item_checkbox, null);
                CheckBox invTwoPossessedBox = investigatorTwoPossessed.findViewById(R.id.checkbox);
                invTwoPossessedBox.setId(R.id.inv_two_possessed);
                invTwoPossessedBox.setTypeface(arnopro);
                editLayout.addView(investigatorTwoPossessed, lp);
                if (globalVariables.InvTwoPossessed > 0) {
                    invTwoPossessedBox.setChecked(true);
                    if (globalVariables.InvTwoPossessed == 999) {
                        invTwoPossessedBox.setText(getString(R.string.inv_two_possessed).trim());
                    } else {
                        invTwoPossessedBox.setText(investigatorNames[globalVariables.InvTwoPossessed] + " " +
                                getString(R
                                        .string.possessed)
                                        .trim());
                    }
                } else {
                    invTwoPossessedBox.setText(getString(R.string.inv_two_possessed).trim());
                }
                View investigatorThreePossessed = View.inflate(this, R.layout.e_item_checkbox, null);
                CheckBox invThreePossessedBox = investigatorThreePossessed.findViewById(R.id.checkbox);
                invThreePossessedBox.setId(R.id.inv_three_possessed);
                invThreePossessedBox.setTypeface(arnopro);
                editLayout.addView(investigatorThreePossessed, lp);
                if (globalVariables.InvThreePossessed > 0) {
                    invThreePossessedBox.setChecked(true);
                    if (globalVariables.InvThreePossessed == 999) {
                        invThreePossessedBox.setText(getString(R.string.inv_three_possessed).trim());
                    } else {
                        invThreePossessedBox.setText(investigatorNames[globalVariables.InvThreePossessed] + " " +
                                getString(R
                                        .string
                                        .possessed)
                                        .trim());
                    }
                } else {
                    invThreePossessedBox.setText(getString(R.string.inv_three_possessed).trim());
                }
                View investigatorFourPossessed = View.inflate(this, R.layout.e_item_checkbox, null);
                CheckBox invFourPossessedBox = investigatorFourPossessed.findViewById(R.id.checkbox);
                invFourPossessedBox.setId(R.id.inv_four_possessed);
                invFourPossessedBox.setTypeface(arnopro);
                editLayout.addView(investigatorFourPossessed, lp);
                if (globalVariables.InvFourPossessed > 0) {
                    invFourPossessedBox.setChecked(true);
                    if (globalVariables.InvFourPossessed == 999) {
                        invFourPossessedBox.setText(getString(R.string.inv_four_possessed).trim());
                    } else {
                        invFourPossessedBox.setText(investigatorNames[globalVariables.InvFourPossessed] + " " +
                                getString(R
                                        .string.possessed)
                                        .trim());
                    }
                } else {
                    invFourPossessedBox.setText(getString(R.string.inv_four_possessed).trim());
                }
            }
        }

        /*
            Forgotten Age log
         */
        if (globalVariables.CurrentCampaign == 4) {

            if (scenario == 1) {
                // Nothing to show
                View nothing = View.inflate(this, R.layout.e_item_heading, null);
                TextView nothingText = nothing.findViewById(R.id.heading);
                nothingText.setText(R.string.nothing);
                nothingText.setTypeface(arnopro);
                nothingText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen
                        .arnopro_textsize));
                nothingText.setAllCaps(false);
                nothingText.setTextScaleX(1);
                lp.setMargins(16, 0, 16, 0);
                editLayout.addView(nothing, lp);
            }

            if (scenario > 1) {
                // Yigs Fury
                View yigsFuryHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView yigsFuryHeadingText = yigsFuryHeading.findViewById(R.id.heading);
                yigsFuryHeadingText.setText(R.string.yigs_fury_heading);
                yigsFuryHeadingText.setTypeface(teutonic);
                editLayout.addView(yigsFuryHeading, lp);
                View yigsFuryCounter = View.inflate(this, R.layout.e_item_counter, null);
                final TextView yigsFuryAmount = yigsFuryCounter.findViewById(R.id.amount);
                yigsFuryAmount.setId(R.id.yigs_fury);
                yigsFuryAmount.setText(String.valueOf(globalVariables.YigsFury));
                yigsFuryAmount.setTypeface(arnopro);
                ImageView yigsFuryDecrement = yigsFuryCounter.findViewById(R.id.decrement);
                yigsFuryDecrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int amount = Integer.valueOf(yigsFuryAmount.getText().toString());
                        if (amount > 0) {
                            amount += -1;
                            yigsFuryAmount.setText(String.valueOf(amount));
                        }
                    }
                });
                ImageView yigsFuryIncrement = yigsFuryCounter.findViewById(R.id.increment);
                yigsFuryIncrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int amount = Integer.valueOf(yigsFuryAmount.getText().toString());
                        if (amount < 99) {
                            amount += 1;
                            yigsFuryAmount.setText(String.valueOf(amount));
                        }
                    }
                });
                editLayout.addView(yigsFuryCounter, lp);

                // Ruins
                View ruinsHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView ruinsHeadingText = ruinsHeading.findViewById(R.id.heading);
                ruinsHeadingText.setText(R.string.ruins_heading);
                ruinsHeadingText.setTypeface(teutonic);
                editLayout.addView(ruinsHeading, lp);
                View ruinsOptions = View.inflate(this, R.layout.e_item_radiogroup, null);
                RadioButton ruinsOptionOne = ruinsOptions.findViewById(R.id.option_one);
                RadioButton ruinsOptionTwo = ruinsOptions.findViewById(R.id.option_two);
                ruinsOptionOne.setId(R.id.ruins_one);
                ruinsOptionTwo.setId(R.id.ruins_two);
                ruinsOptionOne.setText(getString(R.string.forced_wait).trim());
                ruinsOptionTwo.setText(getString(R.string.cleared_path).trim());
                ruinsOptionOne.setTypeface(arnopro);
                ruinsOptionTwo.setTypeface(arnopro);
                editLayout.addView(ruinsOptions, lp);
                if (globalVariables.Ruins == 1) {
                    ruinsOptionOne.setChecked(true);
                } else if (globalVariables.Ruins == 2) {
                    ruinsOptionTwo.setChecked(true);
                }

                // Ichtaca
                View ichtacaHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView ichtacaHeadingText = ichtacaHeading.findViewById(R.id.heading);
                ichtacaHeadingText.setText(R.string.ichtaca_heading);
                ichtacaHeadingText.setTypeface(teutonic);
                editLayout.addView(ichtacaHeading, lp);
                View ichtacaOptions = View.inflate(this, R.layout.e_item_radiogroup, null);
                RadioButton ichtacaOptionOne = ichtacaOptions.findViewById(R.id.option_one);
                RadioButton ichtacaOptionTwo = ichtacaOptions.findViewById(R.id.option_two);
                RadioButton ichtacaOptionThree = ichtacaOptions.findViewById(R.id.option_three);
                ichtacaOptionThree.setVisibility(VISIBLE);
                ichtacaOptionOne.setId(R.id.ichtaca_one);
                ichtacaOptionTwo.setId(R.id.ichtaca_two);
                ichtacaOptionThree.setId(R.id.ichtaca_three);
                ichtacaOptionOne.setText(getString(R.string.ichtaca_observed).trim());
                ichtacaOptionTwo.setText(getString(R.string.ichtaca_trust).trim());
                ichtacaOptionThree.setText(getString(R.string.ichtaca_wary).trim());
                ichtacaOptionOne.setTypeface(arnopro);
                ichtacaOptionTwo.setTypeface(arnopro);
                ichtacaOptionThree.setTypeface(arnopro);
                editLayout.addView(ichtacaOptions, lp);
                if (globalVariables.Ichtaca == 1) {
                    ichtacaOptionOne.setChecked(true);
                } else if (globalVariables.Ichtaca == 2) {
                    ichtacaOptionTwo.setChecked(true);
                } else if (globalVariables.Ichtaca == 3) {
                    ichtacaOptionThree.setChecked(true);
                }

                // Alejandro
                View alejandroHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView alejandroHeadingText = alejandroHeading.findViewById(R.id.heading);
                alejandroHeadingText.setText(R.string.alejandro_heading);
                alejandroHeadingText.setTypeface(teutonic);
                editLayout.addView(alejandroHeading, lp);
                View alejandroOptions = View.inflate(this, R.layout.e_item_radiogroup, null);
                RadioButton alejandroOptionOne = alejandroOptions.findViewById(R.id.option_one);
                RadioButton alejandroOptionTwo = alejandroOptions.findViewById(R.id.option_two);
                alejandroOptionOne.setId(R.id.alejandro_one);
                alejandroOptionTwo.setId(R.id.alejandro_two);
                alejandroOptionOne.setText(getString(R.string.alejandro_remained).trim());
                alejandroOptionTwo.setText(getString(R.string.alejandro_followed).trim());
                alejandroOptionOne.setTypeface(arnopro);
                alejandroOptionTwo.setTypeface(arnopro);
                editLayout.addView(alejandroOptions, lp);
                if (globalVariables.Alejandro == 1) {
                    alejandroOptionOne.setChecked(true);
                } else if (globalVariables.Alejandro == 2) {
                    alejandroOptionTwo.setChecked(true);
                }
            }

            if (scenario > 6) {
                // Harbinger
                View harbingerHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView harbingerHeadingText = harbingerHeading.findViewById(R.id.heading);
                harbingerHeadingText.setText(R.string.harbinger_heading);
                harbingerHeadingText.setTypeface(teutonic);
                editLayout.addView(harbingerHeading, lp);
                View harbinger = View.inflate(this, R.layout.e_item_checkbox, null);
                CheckBox harbingerBox = harbinger.findViewById(R.id.checkbox);
                harbingerBox.setId(R.id.harbinger);
                harbingerBox.setText(R.string.harbinger_alive);
                harbingerBox.setTypeface(arnopro);
                editLayout.addView(harbinger, lp);
                if (globalVariables.Harbinger > -1) {
                    harbingerBox.setChecked(true);
                }
                final View harbingerCounter = View.inflate(this, R.layout.e_item_counter, null);
                final TextView harbingerAmount = harbingerCounter.findViewById(R.id.amount);
                harbingerAmount.setId(R.id.harbinger_damage);
                if (globalVariables.Harbinger == -1) {
                    harbingerAmount.setText("0");
                } else {
                    harbingerAmount.setText(String.valueOf(globalVariables.Harbinger));
                }
                harbingerAmount.setTypeface(arnopro);
                ImageView harbingerDecrement = harbingerCounter.findViewById(R.id.decrement);
                harbingerDecrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int amount = Integer.valueOf(harbingerAmount.getText().toString());
                        if (amount > 0) {
                            amount += -1;
                            harbingerAmount.setText(String.valueOf(amount));
                        }
                    }
                });
                ImageView harbingerIncrement = harbingerCounter.findViewById(R.id.increment);
                harbingerIncrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int amount = Integer.valueOf(harbingerAmount.getText().toString());
                        if (amount < 99) {
                            amount += 1;
                            harbingerAmount.setText(String.valueOf(amount));
                        }
                    }
                });
                editLayout.addView(harbingerCounter, lp);
                if (harbingerBox.isChecked()) {
                    harbingerCounter.setVisibility(VISIBLE);
                } else {
                    harbingerCounter.setVisibility(GONE);
                }
                harbingerBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            harbingerCounter.setVisibility(VISIBLE);
                        } else {
                            harbingerCounter.setVisibility(GONE);
                        }
                    }
                });

                // Relic
                View relicHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView relicHeadingText = relicHeading.findViewById(R.id.heading);
                relicHeadingText.setText(R.string.relic_heading);
                relicHeadingText.setTypeface(teutonic);
                editLayout.addView(relicHeading, lp);
                View relicOptions = View.inflate(this, R.layout.e_item_radiogroup, null);
                final RadioButton relicOptionOne = relicOptions.findViewById(R.id.option_one);
                final RadioButton relicOptionTwo = relicOptions.findViewById(R.id.option_two);
                relicOptionOne.setId(R.id.relic_one);
                relicOptionTwo.setId(R.id.relic_two);
                relicOptionOne.setText(getString(R.string.investigators_recovered_relic).trim());
                relicOptionTwo.setText(getString(R.string.alejandro_recovered_relic).trim());
                relicOptionOne.setTypeface(arnopro);
                relicOptionTwo.setTypeface(arnopro);
                editLayout.addView(relicOptions, lp);
                if (globalVariables.Relic == 1) {
                    relicOptionOne.setChecked(true);
                } else if (globalVariables.Relic == 2) {
                    relicOptionTwo.setChecked(true);
                }

                // Custody
                final View custodyHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView custodyHeadingText = custodyHeading.findViewById(R.id.heading);
                custodyHeadingText.setText(R.string.custody_heading);
                custodyHeadingText.setTypeface(teutonic);
                editLayout.addView(custodyHeading, lp);
                final View custodyOptions = View.inflate(this, R.layout.e_item_radiogroup, null);
                RadioButton custodyOptionOne = custodyOptions.findViewById(R.id.option_one);
                RadioButton custodyOptionTwo = custodyOptions.findViewById(R.id.option_two);
                custodyOptionOne.setId(R.id.custody_one);
                custodyOptionTwo.setId(R.id.custody_two);
                custodyOptionOne.setText(getString(R.string.custody_alejandro).trim());
                custodyOptionTwo.setText(getString(R.string.custody_harlan).trim());
                custodyOptionOne.setTypeface(arnopro);
                custodyOptionTwo.setTypeface(arnopro);
                editLayout.addView(custodyOptions, lp);
                if (globalVariables.Custody == 1) {
                    custodyOptionOne.setChecked(true);
                } else if (globalVariables.Custody == 2) {
                    custodyOptionTwo.setChecked(true);
                }
                if (relicOptionOne.isChecked()) {
                    custodyHeading.setVisibility(VISIBLE);
                    custodyOptions.setVisibility(VISIBLE);
                } else if (relicOptionTwo.isChecked()) {
                    custodyHeading.setVisibility(GONE);
                    custodyOptions.setVisibility(GONE);
                }
                relicOptionOne.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (relicOptionOne.isChecked()) {
                            custodyHeading.setVisibility(VISIBLE);
                            custodyOptions.setVisibility(VISIBLE);
                        } else if (relicOptionTwo.isChecked()) {
                            custodyHeading.setVisibility(GONE);
                            custodyOptions.setVisibility(GONE);
                        }
                    }
                });

                // Ichtaca's Tale
                View ichtacaTaleHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView ichtacaTaleHeadingText = ichtacaTaleHeading.findViewById(R.id.heading);
                ichtacaTaleHeadingText.setText(R.string.ichtaca_tale_heading);
                ichtacaTaleHeadingText.setTypeface(teutonic);
                editLayout.addView(ichtacaTaleHeading, lp);
                View ichtacaTaleOptions = View.inflate(this, R.layout.e_item_radiogroup, null);
                RadioButton ichtacaTaleOptionOne = ichtacaTaleOptions.findViewById(R.id.option_one);
                RadioButton ichtacaTaleOptionTwo = ichtacaTaleOptions.findViewById(R.id.option_two);
                RadioButton ichtacaTaleOptionThree = ichtacaTaleOptions.findViewById(R.id.option_three);
                ichtacaTaleOptionThree.setVisibility(VISIBLE);
                ichtacaTaleOptionOne.setId(R.id.ichtaca_tale_one);
                ichtacaTaleOptionTwo.setId(R.id.ichtaca_tale_two);
                ichtacaTaleOptionThree.setId(R.id.ichtaca_tale_three);
                ichtacaTaleOptionOne.setText(getString(R.string.listened_ichtaca).trim());
                ichtacaTaleOptionTwo.setText(getString(R.string.ichtaca_left).trim());
                ichtacaTaleOptionThree.setText(getString(R.string.forging_own_path).trim());
                ichtacaTaleOptionOne.setTypeface(arnopro);
                ichtacaTaleOptionTwo.setTypeface(arnopro);
                ichtacaTaleOptionThree.setTypeface(arnopro);
                editLayout.addView(ichtacaTaleOptions, lp);
                if (globalVariables.IchtacasTale == 1) {
                    ichtacaTaleOptionOne.setChecked(true);
                } else if (globalVariables.IchtacasTale == 2) {
                    ichtacaTaleOptionTwo.setChecked(true);
                } else if (globalVariables.IchtacasTale == 4) {
                    ichtacaTaleOptionThree.setChecked(true);
                }
            }

            // Threads of Fate
            if(scenario > 8){
                // Missing relic
                final View missingRelicHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView missingRelicHeadingText = missingRelicHeading.findViewById(R.id.heading);
                missingRelicHeadingText.setText(R.string.missing_relic_heading);
                missingRelicHeadingText.setTypeface(teutonic);
                editLayout.addView(missingRelicHeading, lp);
                final View missingRelicOptions = View.inflate(this, R.layout.e_item_radiogroup, null);
                RadioButton missingRelicOptionOne = missingRelicOptions.findViewById(R.id.option_one);
                RadioButton missingRelicOptionTwo = missingRelicOptions.findViewById(R.id.option_two);
                missingRelicOptionOne.setId(R.id.missing_relic_one);
                missingRelicOptionTwo.setId(R.id.missing_relic_two);
                missingRelicOptionOne.setText(getString(R.string.relic_missing).trim());
                missingRelicOptionTwo.setText(getString(R.string.found_relic).trim());
                missingRelicOptionOne.setTypeface(arnopro);
                missingRelicOptionTwo.setTypeface(arnopro);
                editLayout.addView(missingRelicOptions, lp);
                if (globalVariables.MissingRelic == 1) {
                    missingRelicOptionOne.setChecked(true);
                } else if (globalVariables.MissingRelic == 2) {
                    missingRelicOptionTwo.setChecked(true);
                }

                // Missing Alejandro
                final View missingAlejandroHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView missingAlejandroHeadingText = missingAlejandroHeading.findViewById(R.id.heading);
                missingAlejandroHeadingText.setText(R.string.missing_alejandro_heading);
                missingAlejandroHeadingText.setTypeface(teutonic);
                editLayout.addView(missingAlejandroHeading, lp);
                final View missingAlejandroOptions = View.inflate(this, R.layout.e_item_radiogroup, null);
                RadioButton missingAlejandroOptionOne = missingAlejandroOptions.findViewById(R.id.option_one);
                RadioButton missingAlejandroOptionTwo = missingAlejandroOptions.findViewById(R.id.option_two);
                missingAlejandroOptionOne.setId(R.id.missing_alejandro_one);
                missingAlejandroOptionTwo.setId(R.id.missing_alejandro_two);
                missingAlejandroOptionOne.setText(getString(R.string.alejandro_missing).trim());
                missingAlejandroOptionTwo.setText(getString(R.string.rescued_alejandro).trim());
                missingAlejandroOptionOne.setTypeface(arnopro);
                missingAlejandroOptionTwo.setTypeface(arnopro);
                editLayout.addView(missingAlejandroOptions, lp);
                if (globalVariables.MissingAlejandro == 1) {
                    missingAlejandroOptionOne.setChecked(true);
                } else if (globalVariables.MissingAlejandro == 2) {
                    missingAlejandroOptionTwo.setChecked(true);
                }

                // Missing Ichtaca
                final View missingIchtacaHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView missingIchtacaHeadingText = missingIchtacaHeading.findViewById(R.id.heading);
                missingIchtacaHeadingText.setText(R.string.missing_ichtaca_heading);
                missingIchtacaHeadingText.setTypeface(teutonic);
                editLayout.addView(missingIchtacaHeading, lp);
                final View missingIchtacaOptions = View.inflate(this, R.layout.e_item_radiogroup, null);
                RadioButton missingIchtacaOptionOne = missingIchtacaOptions.findViewById(R.id.option_one);
                RadioButton missingIchtacaOptionTwo = missingIchtacaOptions.findViewById(R.id.option_two);
                missingIchtacaOptionOne.setId(R.id.missing_ichtaca_one);
                missingIchtacaOptionTwo.setId(R.id.missing_ichtaca_two);
                missingIchtacaOptionOne.setText(getString(R.string.ichtaca_dark).trim());
                missingIchtacaOptionTwo.setText(getString(R.string.bond_ichtaca).trim());
                missingIchtacaOptionOne.setTypeface(arnopro);
                missingIchtacaOptionTwo.setTypeface(arnopro);
                editLayout.addView(missingIchtacaOptions, lp);
                if (globalVariables.MissingIchtaca == 1) {
                    missingIchtacaOptionOne.setChecked(true);
                } else if (globalVariables.MissingIchtaca == 2) {
                    missingIchtacaOptionTwo.setChecked(true);
                }
            }

            // Boundary Beyond
            if(scenario > 10){
                // Paths known
                View pathsHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView pathsHeadingText = pathsHeading.findViewById(R.id.heading);
                pathsHeadingText.setText(R.string.paths_heading);
                pathsHeadingText.setTypeface(teutonic);
                editLayout.addView(pathsHeading, lp);
                View pathsCounter = View.inflate(this, R.layout.e_item_counter, null);
                final TextView pathsAmount = pathsCounter.findViewById(R.id.amount);
                pathsAmount.setId(R.id.paths);
                pathsAmount.setText(String.valueOf(globalVariables.PathsKnown));
                pathsAmount.setTypeface(arnopro);
                ImageView pathsDecrement = pathsCounter.findViewById(R.id.decrement);
                pathsDecrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int amount = Integer.valueOf(pathsAmount.getText().toString());
                        if (amount > 0) {
                            amount += -1;
                            pathsAmount.setText(String.valueOf(amount));
                        }
                    }
                });
                ImageView pathsIncrement = pathsCounter.findViewById(R.id.increment);
                pathsIncrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int amount = Integer.valueOf(pathsAmount.getText().toString());
                        if (amount < 5) {
                            amount += 1;
                            pathsAmount.setText(String.valueOf(amount));
                        }
                    }
                });
                editLayout.addView(pathsCounter, lp);

                // Ichtaca's Confidence
                View confidenceHeading = View.inflate(this, R.layout.e_item_heading, null);
                TextView confidenceHeadingText = confidenceHeading.findViewById(R.id.heading);
                confidenceHeadingText.setText(R.string.confidence_heading);
                confidenceHeadingText.setTypeface(teutonic);
                editLayout.addView(confidenceHeading, lp);
                final View confidenceOptions = View.inflate(this, R.layout.e_item_radiogroup, null);
                RadioButton confidenceOptionOne = confidenceOptions.findViewById(R.id.option_two);
                RadioButton confidenceOptionTwo = confidenceOptions.findViewById(R.id.option_three);
                RadioButton confidenceOptionThree = confidenceOptions.findViewById(R.id.option_one);
                confidenceOptionTwo.setVisibility(VISIBLE);
                confidenceOptionOne.setId(R.id.confidence_one);
                confidenceOptionTwo.setId(R.id.confidence_two);
                confidenceOptionThree.setId(R.id.confidence_three);
                confidenceOptionOne.setText(getString(R.string.ichtaca_confidence).trim());
                confidenceOptionTwo.setText(getString(R.string.ichtaca_faith).trim());
                confidenceOptionThree.setText(getString(R.string.no_confidence));
                confidenceOptionOne.setTypeface(arnopro);
                confidenceOptionTwo.setTypeface(arnopro);
                confidenceOptionThree.setTypeface(arnopro);
                editLayout.addView(confidenceOptions, lp);
                if (globalVariables.IchtacaConfidence == 1) {
                    confidenceOptionOne.setChecked(true);
                } else if (globalVariables.IchtacaConfidence == 2) {
                    confidenceOptionTwo.setChecked(true);
                } else {
                    confidenceOptionThree.setChecked(true);
                }
            }
        }

        if (globalVariables.Rougarou > 0) {
            // Rougarou
            View rougarouHeading = View.inflate(this, R.layout.e_item_heading, null);
            TextView rougarouHeadingText = rougarouHeading.findViewById(R.id.heading);
            rougarouHeadingText.setText(R.string.rougarou_scenario);
            rougarouHeadingText.setTypeface(teutonic);
            editLayout.addView(rougarouHeading, lp);
            View rougarouOptions = View.inflate(this, R.layout.e_item_radiogroup, null);
            RadioButton rougarouOptionOne = rougarouOptions.findViewById(R.id.option_one);
            RadioButton rougarouOptionTwo = rougarouOptions.findViewById(R.id.option_two);
            RadioButton rougarouOptionThree = rougarouOptions.findViewById(R.id.option_three);
            rougarouOptionThree.setVisibility(VISIBLE);
            rougarouOptionOne.setId(R.id.rougarou_one);
            rougarouOptionTwo.setId(R.id.rougarou_two);
            rougarouOptionThree.setId(R.id.rougarou_three);
            rougarouOptionOne.setText(getString(R.string.rougarou_alive).trim());
            rougarouOptionTwo.setText(getString(R.string.rougarou_destroyed).trim());
            rougarouOptionThree.setText(getString(R.string.rougarou_escaped).trim());
            rougarouOptionOne.setTypeface(arnopro);
            rougarouOptionTwo.setTypeface(arnopro);
            rougarouOptionThree.setTypeface(arnopro);
            editLayout.addView(rougarouOptions, lp);
            if (globalVariables.Rougarou == 1) {
                rougarouOptionOne.setChecked(true);
            } else if (globalVariables.Rougarou == 2) {
                rougarouOptionTwo.setChecked(true);
            } else if (globalVariables.Rougarou == 3) {
                rougarouOptionThree.setChecked(true);
            }
        }

        if (globalVariables.Carnevale > 0) {
            // Carnevale
            View carnevaleHeading = View.inflate(this, R.layout.e_item_heading, null);
            TextView carnevaleHeadingText = carnevaleHeading.findViewById(R.id.heading);
            carnevaleHeadingText.setText(R.string.carnevale_scenario);
            carnevaleHeadingText.setTypeface(teutonic);
            editLayout.addView(carnevaleHeading, lp);
            View carnevaleOptions = View.inflate(this, R.layout.e_item_radiogroup, null);
            RadioButton carnevaleOptionOne = carnevaleOptions.findViewById(R.id.option_one);
            RadioButton carnevaleOptionTwo = carnevaleOptions.findViewById(R.id.option_two);
            RadioButton carnevaleOptionThree = carnevaleOptions.findViewById(R.id.option_three);
            carnevaleOptionThree.setVisibility(VISIBLE);
            carnevaleOptionOne.setId(R.id.carnevale_one);
            carnevaleOptionTwo.setId(R.id.carnevale_two);
            carnevaleOptionThree.setId(R.id.carnevale_three);
            carnevaleOptionOne.setText(getString(R.string.carnevale_many_sacrificed).trim());
            carnevaleOptionTwo.setText(getString(R.string.carnevale_banished).trim());
            carnevaleOptionThree.setText(getString(R.string.carnevale_retreated).trim());
            carnevaleOptionOne.setTypeface(arnopro);
            carnevaleOptionTwo.setTypeface(arnopro);
            carnevaleOptionThree.setTypeface(arnopro);
            editLayout.addView(carnevaleOptions, lp);
            if (globalVariables.Carnevale == 1) {
                carnevaleOptionOne.setChecked(true);
            } else if (globalVariables.Carnevale == 2) {
                carnevaleOptionTwo.setChecked(true);
            } else if (globalVariables.Carnevale == 3) {
                carnevaleOptionThree.setChecked(true);
            }

            // Carnevale reward
            View carnevaleRewardOptions = View.inflate(this, R.layout.e_item_radiogroup, null);
            RadioButton carnevaleRewardOptionOne = carnevaleRewardOptions.findViewById(R.id.option_one);
            RadioButton carnevaleRewardOptionTwo = carnevaleRewardOptions.findViewById(R.id.option_two);
            RadioButton carnevaleRewardOptionThree = carnevaleRewardOptions.findViewById(R.id.option_three);
            carnevaleRewardOptionThree.setVisibility(VISIBLE);
            carnevaleRewardOptionOne.setId(R.id.carnevale_reward_one);
            carnevaleRewardOptionTwo.setId(R.id.carnevale_reward_two);
            carnevaleRewardOptionThree.setId(R.id.carnevale_reward_three);
            carnevaleRewardOptionOne.setText(getString(R.string.no_reward).trim());
            carnevaleRewardOptionTwo.setText(getString(R.string.carnevale_sacrifice_made).trim());
            carnevaleRewardOptionThree.setText(getString(R.string.carnevale_abbess_satisfied).trim());
            carnevaleRewardOptionOne.setTypeface(arnopro);
            carnevaleRewardOptionTwo.setTypeface(arnopro);
            carnevaleRewardOptionThree.setTypeface(arnopro);
            editLayout.addView(carnevaleRewardOptions, lp);
            if (globalVariables.CarnevaleReward == 0) {
                carnevaleRewardOptionOne.setChecked(true);
            } else if (globalVariables.CarnevaleReward == 1) {
                carnevaleRewardOptionTwo.setChecked(true);
            } else if (globalVariables.CarnevaleReward == 2) {
                carnevaleRewardOptionThree.setChecked(true);
            }
        }

        // Continue button
        Button confirmButton = findViewById(R.id.continue_button);
        confirmButton.setTypeface(teutonic);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Set scenario number
                int scenario;
                if (globalVariables.CurrentScenario > 100) {
                    scenario = globalVariables.PreviousScenario;
                } else {
                    scenario = globalVariables.CurrentScenario;
                }

                /*
                    Rewrite all relevant variables
                  */

                // Night of the Zealot log
                if (globalVariables.CurrentCampaign == 1) {

                    if (scenario > 1) {
                        // Your House
                        RadioButton houseOne = findViewById(R.id.house_one);
                        RadioButton houseTwo = findViewById(R.id.house_two);
                        if (houseOne.isChecked()) {
                            globalVariables.HouseStanding = 1;
                        } else if (houseTwo.isChecked()) {
                            globalVariables.HouseStanding = 0;
                        }

                        // Ghoul Priest
                        RadioButton ghoulOne = findViewById(R.id.ghoul_one);
                        RadioButton ghoulTwo = findViewById(R.id.ghoul_two);
                        if (ghoulOne.isChecked()) {
                            globalVariables.GhoulPriest = 0;
                        } else if (ghoulTwo.isChecked()) {
                            globalVariables.GhoulPriest = 1;
                        }

                        // Lita Chantler
                        RadioButton litaOne = findViewById(R.id.lita_one);
                        RadioButton litaTwo = findViewById(R.id.lita_two);
                        RadioButton litaThree = findViewById(R.id.lita_three);
                        if (litaOne.isChecked()) {
                            globalVariables.LitaChantler = 0;
                        } else if (litaTwo.isChecked()) {
                            globalVariables.LitaChantler = 1;
                        } else if (litaThree.isChecked()) {
                            globalVariables.LitaChantler = 2;
                        }
                    }

                    if (scenario > 2) {
                        // Past Midnight
                        RadioButton midnightOne = findViewById(R.id.midnight_one);
                        RadioButton midnightTwo = findViewById(R.id.midnight_two);
                        if (midnightOne.isChecked()) {
                            globalVariables.PastMidnight = 0;
                        } else if (midnightTwo.isChecked()) {
                            globalVariables.PastMidnight = 1;
                        }

                        // Cultists
                        CheckBox drew = findViewById(R.id.drew);
                        CheckBox herman = findViewById(R.id.herman);
                        CheckBox peter = findViewById(R.id.peter);
                        CheckBox victoria = findViewById(R.id.victoria);
                        CheckBox ruth = findViewById(R.id.ruth);
                        CheckBox masked = findViewById(R.id.masked);
                        if (drew.isChecked()) {
                            globalVariables.DrewInterrogated = 1;
                        } else {
                            globalVariables.DrewInterrogated = 0;
                        }
                        if (herman.isChecked()) {
                            globalVariables.HermanInterrogated = 1;
                        } else {
                            globalVariables.HermanInterrogated = 0;
                        }
                        if (peter.isChecked()) {
                            globalVariables.PeterInterrogated = 1;
                        } else {
                            globalVariables.PeterInterrogated = 0;
                        }
                        if (victoria.isChecked()) {
                            globalVariables.VictoriaInterrogated = 1;
                        } else {
                            globalVariables.VictoriaInterrogated = 0;
                        }
                        if (ruth.isChecked()) {
                            globalVariables.RuthInterrogated = 1;
                        } else {
                            globalVariables.RuthInterrogated = 0;
                        }
                        if (masked.isChecked()) {
                            globalVariables.MaskedInterrogated = 1;
                        } else {
                            globalVariables.MaskedInterrogated = 0;
                        }
                    }

                    if (globalVariables.NightCompleted == 1) {
                        // Umordhoth
                        RadioButton umordhothOne = findViewById(R.id.umordhoth_one);
                        RadioButton umordhothTwo = findViewById(R.id.umordhoth_two);
                        RadioButton umordhothThree = findViewById(R.id.umordhoth_three);
                        RadioButton umordhothFour = findViewById(R.id.umordhoth_four);
                        if (umordhothOne.isChecked()) {
                            globalVariables.Umordhoth = 0;
                        } else if (umordhothTwo.isChecked()) {
                            globalVariables.Umordhoth = 1;
                        } else if (umordhothThree.isChecked()) {
                            globalVariables.Umordhoth = 2;
                        } else if (umordhothFour.isChecked()) {
                            globalVariables.Umordhoth = 3;
                        }
                    }
                }

                // The Dunwich Legacy log
                if (globalVariables.CurrentCampaign == 2) {
                    if ((scenario > 1 && globalVariables.FirstScenario == 1) ||
                            (scenario != 2 && globalVariables.FirstScenario == 2)) {
                        // Investigators unconscious
                        RadioButton unconsciousOne = findViewById(R.id.unconscious_one);
                        RadioButton unconsciousTwo = findViewById(R.id.unconscious_two);
                        if (unconsciousOne.isChecked()) {
                            globalVariables.InvestigatorsUnconscious = 0;
                        } else if (unconsciousTwo.isChecked()) {
                            globalVariables.InvestigatorsUnconscious = 1;
                        }
                    }

                    if ((scenario > 1 && globalVariables.FirstScenario == 1) ||
                            scenario > 2) {
                        // Warren Rice
                        RadioButton warrenOne = findViewById(R.id.warren_one);
                        RadioButton warrenTwo = findViewById(R.id.warren_two);
                        RadioButton warrenThree = findViewById(R.id.warren_three);
                        RadioButton warrenFour = findViewById(R.id.warren_four);
                        if (warrenOne.isChecked()) {
                            globalVariables.WarrenRice = 0;
                        } else if (warrenTwo.isChecked()) {
                            globalVariables.WarrenRice = 1;
                        } else if (warrenThree.isChecked()) {
                            globalVariables.WarrenRice = 2;
                        } else if (warrenFour.isChecked()) {
                            globalVariables.WarrenRice = 3;
                        }

                        // Students
                        RadioButton studentsOne = findViewById(R.id.students_one);
                        RadioButton studentsTwo = findViewById(R.id.students_two);
                        RadioButton studentsThree = findViewById(R.id.students_three);
                        if (studentsOne.isChecked()) {
                            globalVariables.Students = 0;
                        } else if (studentsTwo.isChecked()) {
                            globalVariables.Students = 1;
                        } else if (studentsThree.isChecked()) {
                            globalVariables.Students = 2;
                        }
                    }

                    if ((scenario == 1 && globalVariables.FirstScenario == 2) ||
                            scenario > 2) {
                        // Francis Morgan
                        RadioButton francisOne = findViewById(R.id.francis_one);
                        RadioButton francisTwo = findViewById(R.id.francis_two);
                        RadioButton francisThree = findViewById(R.id.francis_three);
                        RadioButton francisFour = findViewById(R.id.francis_four);
                        if (francisOne.isChecked()) {
                            globalVariables.FrancisMorgan = 0;
                        } else if (francisTwo.isChecked()) {
                            globalVariables.FrancisMorgan = 1;
                        } else if (francisThree.isChecked()) {
                            globalVariables.FrancisMorgan = 2;
                        } else if (francisFour.isChecked()) {
                            globalVariables.FrancisMorgan = 3;
                        }

                        // O'bannion gang
                        RadioButton obannionOne = findViewById(R.id.obannion_one);
                        RadioButton obannionTwo = findViewById(R.id.obannion_two);
                        if (obannionOne.isChecked()) {
                            globalVariables.ObannionGang = 0;
                        } else if (obannionTwo.isChecked()) {
                            globalVariables.ObannionGang = 1;
                        }

                        // Cheated
                        RadioButton cheatedOne = findViewById(R.id.cheated_one);
                        RadioButton cheatedTwo = findViewById(R.id.cheated_two);
                        if (cheatedOne.isChecked()) {
                            globalVariables.InvestigatorsCheated = 0;
                        } else if (cheatedTwo.isChecked()) {
                            globalVariables.InvestigatorsCheated = 1;
                        }
                    }

                    if (scenario > 3) {
                        // Henry Armitage
                        RadioButton armitageOne = findViewById(R.id.armitage_one);
                        RadioButton armitageTwo = findViewById(R.id.armitage_two);
                        RadioButton armitageThree = findViewById(R.id.armitage_three);
                        RadioButton armitageFour = findViewById(R.id.armitage_four);
                        if (armitageOne.isChecked()) {
                            globalVariables.HenryArmitage = 0;
                        } else if (armitageTwo.isChecked()) {
                            globalVariables.HenryArmitage = 1;
                        } else if (armitageThree.isChecked()) {
                            globalVariables.HenryArmitage = 2;
                        } else if (armitageFour.isChecked()) {
                            globalVariables.HenryArmitage = 3;
                        }
                    }

                    if (scenario > 4) {
                        // Necronomicon
                        RadioButton necronomiconOne = findViewById(R.id.necronomicon_one);
                        RadioButton necronomiconTwo = findViewById(R.id.necronomicon_two);
                        RadioButton necronomiconThree = findViewById(R.id.necronomicon_three);
                        RadioButton necronomiconFour = findViewById(R.id.necronomicon_four);
                        if (necronomiconOne.isChecked()) {
                            globalVariables.Necronomicon = 0;
                        } else if (necronomiconTwo.isChecked()) {
                            globalVariables.Necronomicon = 1;
                        } else if (necronomiconThree.isChecked()) {
                            globalVariables.Necronomicon = 2;
                        } else if (necronomiconFour.isChecked()) {
                            globalVariables.Necronomicon = 3;
                        }

                        // Adam Lynch/Harold Walsted
                        RadioButton lynchOne = findViewById(R.id.lynch_one);
                        RadioButton lynchTwo = findViewById(R.id.lynch_two);
                        if (lynchOne.isChecked()) {
                            globalVariables.AdamLynchHaroldWalsted = 0;
                        } else if (lynchTwo.isChecked()) {
                            globalVariables.AdamLynchHaroldWalsted = 1;
                        }
                    }

                    if (scenario > 5) {
                        // Delayed
                        RadioButton delayedOne = findViewById(R.id.delayed_one);
                        RadioButton delayedTwo = findViewById(R.id.delayed_two);
                        if (delayedOne.isChecked()) {
                            globalVariables.InvestigatorsDelayed = 0;
                        } else if (delayedTwo.isChecked()) {
                            globalVariables.InvestigatorsDelayed = 1;
                        }
                    }

                    if (scenario > 6) {
                        // Silas Bishop
                        RadioButton silasOne = findViewById(R.id.silas_one);
                        RadioButton silasTwo = findViewById(R.id.silas_two);
                        RadioButton silasThree = findViewById(R.id.silas_three);
                        RadioButton silasFour = findViewById(R.id.silas_four);
                        if (silasOne.isChecked()) {
                            globalVariables.SilasBishop = 0;
                        } else if (silasTwo.isChecked()) {
                            globalVariables.SilasBishop = 1;
                        } else if (silasThree.isChecked()) {
                            globalVariables.SilasBishop = 2;
                        } else if (silasFour.isChecked()) {
                            globalVariables.SilasBishop = 3;
                        }

                        // Zebulon Whateley
                        RadioButton zebulonOne = findViewById(R.id.zebulon_one);
                        RadioButton zebulonTwo = findViewById(R.id.zebulon_two);
                        if (zebulonOne.isChecked()) {
                            globalVariables.ZebulonWhateley = 0;
                        } else if (zebulonTwo.isChecked()) {
                            globalVariables.ZebulonWhateley = 1;
                        }

                        // Earl Sawyer
                        RadioButton earlOne = findViewById(R.id.sawyer_one);
                        RadioButton earlTwo = findViewById(R.id.sawyer_two);
                        if (earlOne.isChecked()) {
                            globalVariables.EarlSawyer = 0;
                        } else if (earlTwo.isChecked()) {
                            globalVariables.EarlSawyer = 1;
                        }

                        // Ally sacrificed
                        RadioButton allyOne = findViewById(R.id.ally_one);
                        RadioButton allyTwo = findViewById(R.id.ally_two);
                        if (allyOne.isChecked()) {
                            globalVariables.AllySacrificed = 0;
                        } else if (allyTwo.isChecked()) {
                            globalVariables.AllySacrificed = 1;
                        }
                    }

                    if (scenario > 8) {
                        // Townsfolk
                        RadioButton townsfolkOne = findViewById(R.id.townsfolk_one);
                        RadioButton townsfolkTwo = findViewById(R.id.townsfolk_two);
                        if (townsfolkOne.isChecked()) {
                            globalVariables.TownsfolkAction = 1;
                        } else if (townsfolkTwo.isChecked()) {
                            globalVariables.TownsfolkAction = 2;
                        }

                        // Broods
                        TextView broods = findViewById(R.id.broods);
                        globalVariables.BroodsEscaped = Integer.valueOf(broods.getText().toString());
                    }

                    if (scenario > 9) {
                        // Gate
                        RadioButton gateOne = findViewById(R.id.gate_one);
                        RadioButton gateTwo = findViewById(R.id.gate_two);
                        if (gateOne.isChecked()) {
                            globalVariables.InvestigatorsGate = 0;
                        } else if (gateTwo.isChecked()) {
                            globalVariables.InvestigatorsGate = 1;
                        }
                    }

                    if (scenario > 10) {
                        // Yog-Sothoth
                        RadioButton yogOne = findViewById(R.id.yog_one);
                        RadioButton yogTwo = findViewById(R.id.yog_two);
                        RadioButton yogThree = findViewById(R.id.yog_three);
                        if (yogOne.isChecked()) {
                            globalVariables.YogSothoth = 1;
                        } else if (yogTwo.isChecked()) {
                            globalVariables.YogSothoth = 2;
                        } else if (yogThree.isChecked()) {
                            globalVariables.YogSothoth = 3;
                        }
                    }
                }

                // Path to Carcosa log
                if (globalVariables.CurrentCampaign == 3) {
                    if (scenario > 1) {
                        // Doubt, Conviction, Stranger
                        TextView doubt = findViewById(R.id.doubt);
                        globalVariables.Doubt = Integer.valueOf(doubt.getText().toString());
                        TextView conviction = findViewById(R.id.conviction);
                        globalVariables.Conviction = Integer.valueOf(conviction.getText().toString());
                        TextView chasingStranger = findViewById(R.id.chasing_stranger);
                        globalVariables.ChasingStranger = Integer.valueOf(chasingStranger.getText().toString());

                        // Theatre
                        RadioButton theatreOne = findViewById(R.id.theatre_one);
                        RadioButton theatreTwo = findViewById(R.id.theatre_two);
                        RadioButton theatreThree = findViewById(R.id.theatre_three);
                        RadioButton theatreFour = findViewById(R.id.theatre_four);
                        if (theatreOne.isChecked()) {
                            globalVariables.Theatre = 1;
                        } else if (theatreTwo.isChecked()) {
                            globalVariables.Theatre = 2;
                        } else if (theatreThree.isChecked()) {
                            globalVariables.Theatre = 3;
                        } else if (theatreFour.isChecked()) {
                            globalVariables.Theatre = 4;
                        }

                        // Stranger
                        RadioButton strangerOne = findViewById(R.id.stranger_one);
                        RadioButton strangerTwo = findViewById(R.id.stranger_two);
                        if (strangerOne.isChecked()) {
                            globalVariables.Stranger = 0;
                        } else if (strangerTwo.isChecked()) {
                            globalVariables.Stranger = 1;
                        }

                        // Police
                        RadioButton policeOne = findViewById(R.id.police_one);
                        RadioButton policeTwo = findViewById(R.id.police_two);
                        RadioButton policeThree = findViewById(R.id.police_three);
                        RadioButton policeFour = findViewById(R.id.police_four);
                        if (policeOne.isChecked()) {
                            globalVariables.Police = 0;
                        } else if (policeTwo.isChecked()) {
                            globalVariables.Police = 1;
                        } else if (policeThree.isChecked()) {
                            globalVariables.Police = 2;
                        } else if (policeFour.isChecked()) {
                            globalVariables.Police = 3;
                        }
                    }

                    if (scenario > 2) {
                        // VIPS
                        CheckBox constanceInterviewed = findViewById(R.id.constance_interviewed);
                        CheckBox constanceSlain = findViewById(R.id.constance_slain);
                        if (constanceInterviewed.isChecked()) {
                            if (constanceSlain.isChecked()) {
                                globalVariables.Constance = 4;
                            } else {
                                globalVariables.Constance = 1;
                            }
                        } else {
                            if (constanceSlain.isChecked()) {
                                if (globalVariables.Constance == 3 || globalVariables.Constance == 5) {
                                    globalVariables.Constance = 5;
                                } else {
                                    globalVariables.Constance = 2;
                                }
                            } else {
                                if (globalVariables.Constance == 3 || globalVariables.Constance == 5) {
                                    globalVariables.Constance = 3;
                                } else {
                                    globalVariables.Constance = 0;
                                }
                            }
                        }
                        CheckBox jordanInterviewed = findViewById(R.id.jordan_interviewed);
                        CheckBox jordanSlain = findViewById(R.id.jordan_slain);
                        if (jordanInterviewed.isChecked()) {
                            if (jordanSlain.isChecked()) {
                                globalVariables.Jordan = 4;
                            } else {
                                globalVariables.Jordan = 1;
                            }
                        } else {
                            if (jordanSlain.isChecked()) {
                                if (globalVariables.Jordan == 3 || globalVariables.Jordan == 5) {
                                    globalVariables.Jordan = 5;
                                } else {
                                    globalVariables.Jordan = 2;
                                }
                            } else {
                                if (globalVariables.Jordan == 3 || globalVariables.Jordan == 5) {
                                    globalVariables.Jordan = 3;
                                } else {
                                    globalVariables.Jordan = 0;
                                }
                            }
                        }
                        CheckBox ishimaruInterviewed = findViewById(R.id.ishimaru_interviewed);
                        CheckBox ishimaruSlain = findViewById(R.id.ishimaru_slain);
                        if (ishimaruInterviewed.isChecked()) {
                            if (ishimaruSlain.isChecked()) {
                                globalVariables.Ishimaru = 4;
                            } else {
                                globalVariables.Ishimaru = 1;
                            }
                        } else {
                            if (ishimaruSlain.isChecked()) {
                                if (globalVariables.Ishimaru == 3 || globalVariables.Ishimaru == 5) {
                                    globalVariables.Ishimaru = 5;
                                } else {
                                    globalVariables.Ishimaru = 2;
                                }
                            } else {
                                if (globalVariables.Ishimaru == 3 || globalVariables.Ishimaru == 5) {
                                    globalVariables.Ishimaru = 3;
                                } else {
                                    globalVariables.Ishimaru = 0;
                                }
                            }
                        }
                        CheckBox sebastienInterviewed = findViewById(R.id.sebastien_interviewed);
                        CheckBox sebastienSlain = findViewById(R.id.sebastien_slain);
                        if (sebastienInterviewed.isChecked()) {
                            if (sebastienSlain.isChecked()) {
                                globalVariables.Sebastien = 4;
                            } else {
                                globalVariables.Sebastien = 1;
                            }
                        } else {
                            if (sebastienSlain.isChecked()) {
                                if (globalVariables.Sebastien == 3 || globalVariables.Sebastien == 5) {
                                    globalVariables.Sebastien = 5;
                                } else {
                                    globalVariables.Sebastien = 2;
                                }
                            } else {
                                if (globalVariables.Sebastien == 3 || globalVariables.Sebastien == 5) {
                                    globalVariables.Sebastien = 3;
                                } else {
                                    globalVariables.Sebastien = 0;
                                }
                            }
                        }
                        CheckBox ashleighInterviewed = findViewById(R.id.ashleigh_interviewed);
                        CheckBox ashleighSlain = findViewById(R.id.ashleigh_slain);
                        if (ashleighInterviewed.isChecked()) {
                            if (ashleighSlain.isChecked()) {
                                globalVariables.Ashleigh = 4;
                            } else {
                                globalVariables.Ashleigh = 1;
                            }
                        } else {
                            if (ashleighSlain.isChecked()) {
                                if (globalVariables.Ashleigh == 3 || globalVariables.Ashleigh == 5) {
                                    globalVariables.Ashleigh = 5;
                                } else {
                                    globalVariables.Ashleigh = 2;
                                }
                            } else {
                                if (globalVariables.Ashleigh == 3 || globalVariables.Ashleigh == 5) {
                                    globalVariables.Ashleigh = 3;
                                } else {
                                    globalVariables.Ashleigh = 0;
                                }
                            }
                        }
                    }

                    if (scenario > 3) {
                        // Party
                        RadioButton partyOne = findViewById(R.id.party_one);
                        RadioButton partyTwo = findViewById(R.id.party_two);
                        RadioButton partyThree = findViewById(R.id.party_three);
                        RadioButton partyFour = findViewById(R.id.party_four);
                        if (partyOne.isChecked()) {
                            globalVariables.Party = 0;
                        } else if (partyTwo.isChecked()) {
                            globalVariables.Party = 1;
                        } else if (partyThree.isChecked()) {
                            globalVariables.Party = 2;
                        } else if (partyFour.isChecked()) {
                            globalVariables.Party = 3;
                        }
                    }

                    if (scenario > 4) {
                        // Onyx
                        RadioButton onyxOne = findViewById(R.id.onyx_one);
                        RadioButton onyxTwo = findViewById(R.id.onyx_two);
                        RadioButton onyxThree = findViewById(R.id.onyx_three);
                        RadioButton onyxFour = findViewById(R.id.onyx_four);
                        if (onyxOne.isChecked()) {
                            globalVariables.Onyx = 1;
                        } else if (onyxTwo.isChecked()) {
                            globalVariables.Onyx = 2;
                        } else if (onyxThree.isChecked()) {
                            globalVariables.Onyx = 3;
                        } else if (onyxFour.isChecked()) {
                            globalVariables.Onyx = 4;
                        }
                    }

                    if (scenario > 5) {
                        // Asylum
                        RadioButton asylumOne = findViewById(R.id.asylum_one);
                        RadioButton asylumTwo = findViewById(R.id.asylum_two);
                        RadioButton asylumThree = findViewById(R.id.asylum_three);
                        if (asylumOne.isChecked()) {
                            globalVariables.Asylum = 1;
                        } else if (asylumTwo.isChecked()) {
                            globalVariables.Asylum = 2;
                        } else if (asylumThree.isChecked()) {
                            globalVariables.Asylum = 3;
                        }
                    }

                    if (scenario > 6) {
                        // Daniel's warning
                        RadioButton danielOne = findViewById(R.id.daniel_one);
                        RadioButton danielTwo = findViewById(R.id.daniel_two);
                        RadioButton danielThree = findViewById(R.id.daniel_three);
                        if (danielOne.isChecked()) {
                            globalVariables.DanielsWarning = 0;
                        } else if (danielTwo.isChecked()) {
                            globalVariables.DanielsWarning = 1;
                        } else if (danielThree.isChecked()) {
                            globalVariables.DanielsWarning = 2;
                        }
                    }

                    if (scenario > 7) {
                        // Nigel
                        RadioButton nigelOne = findViewById(R.id.nigel_one);
                        RadioButton nigelTwo = findViewById(R.id.nigel_two);
                        RadioButton nigelThree = findViewById(R.id.nigel_three);
                        RadioButton nigelFour = findViewById(R.id.nigel_four);
                        if (nigelOne.isChecked()) {
                            globalVariables.Nigel = 0;
                        } else if (nigelTwo.isChecked()) {
                            globalVariables.Nigel = 1;
                        } else if (nigelThree.isChecked()) {
                            globalVariables.Nigel = 2;
                        } else if (nigelFour.isChecked()) {
                            globalVariables.Nigel = 3;
                        }
                    }

                    if (scenario > 8) {
                        // InvReadAct
                        CheckBox invOneReadAct = findViewById(R.id.inv_one_read_act);
                        CheckBox invTwoReadAct = findViewById(R.id.inv_two_read_act);
                        CheckBox invThreeReadAct = findViewById(R.id.inv_three_read_act);
                        CheckBox invFourReadAct = findViewById(R.id.inv_four_read_act);
                        if (invOneReadAct.isChecked()) {
                            if (globalVariables.InvOneReadAct == 0) {
                                globalVariables.InvOneReadAct = 999;
                            }
                        } else {
                            globalVariables.InvOneReadAct = 0;
                        }
                        if (invTwoReadAct.isChecked()) {
                            if (globalVariables.InvTwoReadAct == 0) {
                                globalVariables.InvTwoReadAct = 999;
                            }
                        } else {
                            globalVariables.InvTwoReadAct = 0;
                        }
                        if (invThreeReadAct.isChecked()) {
                            if (globalVariables.InvThreeReadAct == 0) {
                                globalVariables.InvThreeReadAct = 999;
                            }
                        } else {
                            globalVariables.InvThreeReadAct = 0;
                        }
                        if (invFourReadAct.isChecked()) {
                            if (globalVariables.InvFourReadAct == 0) {
                                globalVariables.InvFourReadAct = 999;
                            }
                        } else {
                            globalVariables.InvFourReadAct = 0;
                        }
                    }

                    // Out of order so that a change to Path does not break things
                    if (scenario > 10 && globalVariables.Path != 0) {
                        // Hastur
                        RadioButton hasturOne = findViewById(R.id.hastur_one);
                        RadioButton hasturTwo = findViewById(R.id.hastur_two);
                        RadioButton hasturThree = findViewById(R.id.hastur_three);
                        if (hasturOne.isChecked()) {
                            globalVariables.Hastur = 1;
                        } else if (hasturTwo.isChecked()) {
                            globalVariables.Hastur = 4;
                        } else if (hasturThree.isChecked()) {
                            globalVariables.Hastur = 5;
                        }

                        // InvPossessed
                        CheckBox invOnePossessed = findViewById(R.id.inv_one_possessed);
                        CheckBox invTwoPossessed = findViewById(R.id.inv_two_possessed);
                        CheckBox invThreePossessed = findViewById(R.id.inv_three_possessed);
                        CheckBox invFourPossessed = findViewById(R.id.inv_four_possessed);
                        if (invOnePossessed.isChecked()) {
                            if (globalVariables.InvOnePossessed == 0) {
                                globalVariables.InvOnePossessed = 999;
                            }
                        } else {
                            globalVariables.InvOnePossessed = 0;
                        }
                        if (invTwoPossessed.isChecked()) {
                            if (globalVariables.InvTwoPossessed == 0) {
                                globalVariables.InvTwoPossessed = 999;
                            }
                        } else {
                            globalVariables.InvTwoPossessed = 0;
                        }
                        if (invThreePossessed.isChecked()) {
                            if (globalVariables.InvThreePossessed == 0) {
                                globalVariables.InvThreePossessed = 999;
                            }
                        } else {
                            globalVariables.InvThreePossessed = 0;
                        }
                        if (invFourPossessed.isChecked()) {
                            if (globalVariables.InvFourPossessed == 0) {
                                globalVariables.InvFourPossessed = 999;
                            }
                        } else {
                            globalVariables.InvFourPossessed = 0;
                        }
                    }

                    if (scenario > 9) {
                        RadioButton pathOne = findViewById(R.id.path_one);
                        RadioButton pathTwo = findViewById(R.id.path_two);
                        RadioButton pathThree = findViewById(R.id.path_three);
                        if (pathOne.isChecked()) {
                            globalVariables.Path = 1;
                        } else if (pathTwo.isChecked()) {
                            globalVariables.Path = 2;
                        } else if (pathThree.isChecked()) {
                            globalVariables.Path = 0;
                        }
                    }
                }

                // Forgotten Age
                if(globalVariables.CurrentCampaign == 4){
                    if(scenario > 1){
                        TextView yigsFury = findViewById(R.id.yigs_fury);
                        globalVariables.YigsFury = Integer.valueOf(yigsFury.getText().toString());

                        RadioButton ruinsOne = findViewById(R.id.ruins_one);
                        RadioButton ruinsTwo = findViewById(R.id.ruins_two);
                        if(ruinsOne.isChecked()){
                            globalVariables.Ruins = 1;
                        } else if (ruinsTwo.isChecked()){
                            globalVariables.Ruins = 2;
                        }

                        RadioButton ichtacaOne = findViewById(R.id.ichtaca_one);
                        RadioButton ichtacaTwo = findViewById(R.id.ichtaca_two);
                        RadioButton ichtacaThree = findViewById(R.id.ichtaca_three);
                        if(ichtacaOne.isChecked()){
                            globalVariables.Ichtaca = 1;
                        } else if(ichtacaTwo.isChecked()){
                            globalVariables.Ichtaca = 2;
                        } else if(ichtacaThree.isChecked()){
                            globalVariables.Ichtaca = 3;
                        }

                        RadioButton alejandroOne = findViewById(R.id.alejandro_one);
                        RadioButton alejandroTwo = findViewById(R.id.alejandro_two);
                        if(alejandroOne.isChecked()){
                            globalVariables.Alejandro = 1;
                        } else if(alejandroTwo.isChecked()){
                            globalVariables.Alejandro = 2;
                        }
                    }

                    if(scenario > 6){
                        CheckBox harbingerBox = findViewById(R.id.harbinger);
                        TextView harbingerAmount = findViewById(R.id.harbinger_damage);
                        if(!harbingerBox.isChecked()){
                            globalVariables.Harbinger = -1;
                        } else {
                            globalVariables.Harbinger = Integer.valueOf(harbingerAmount.getText().toString());
                        }

                        RadioButton relicOne = findViewById(R.id.relic_one);
                        RadioButton relicTwo = findViewById(R.id.relic_two);
                        if(relicOne.isChecked()){
                            globalVariables.Relic = 1;
                        } else if(relicTwo.isChecked()){
                            globalVariables.Relic = 2;
                            globalVariables.Custody = 0;
                        }

                        RadioButton custodyOne = findViewById(R.id.custody_one);
                        RadioButton custodyTwo = findViewById(R.id.custody_two);
                        if(custodyOne.isChecked()){
                            globalVariables.Custody = 1;
                        } else if(custodyTwo.isChecked()){
                            globalVariables.Custody = 2;
                        }

                        RadioButton ichtacasTaleOne = findViewById(R.id.ichtaca_tale_one);
                        RadioButton ichtacasTaleTwo = findViewById(R.id.ichtaca_tale_two);
                        RadioButton ichtacasTaleThree = findViewById(R.id.ichtaca_tale_three);
                        if(ichtacasTaleOne.isChecked()){
                            globalVariables.IchtacasTale = 1;
                        } else if(ichtacasTaleTwo.isChecked()){
                            globalVariables.IchtacasTale = 2;
                        } else if(ichtacasTaleThree.isChecked()){
                            globalVariables.IchtacasTale = 4;
                        }
                    }

                    if(scenario > 8){
                        RadioButton missingRelicOne = findViewById(R.id.missing_relic_one);
                        RadioButton missingRelicTwo = findViewById(R.id.missing_relic_two);
                        if(missingRelicOne.isChecked()){
                            globalVariables.MissingRelic = 1;
                        } else if(missingRelicTwo.isChecked()){
                            globalVariables.MissingRelic = 2;
                        }

                        RadioButton missingAlejandroOne = findViewById(R.id.missing_alejandro_one);
                        RadioButton missingAlejandroTwo = findViewById(R.id.missing_alejandro_two);
                        if(missingAlejandroOne.isChecked()){
                            globalVariables.MissingAlejandro = 1;
                        } else if(missingAlejandroTwo.isChecked()){
                            globalVariables.MissingAlejandro = 2;
                        }

                        RadioButton missingIchtacaOne = findViewById(R.id.missing_ichtaca_one);
                        RadioButton missingIchtacaTwo = findViewById(R.id.missing_ichtaca_two);
                        if(missingIchtacaOne.isChecked()){
                            globalVariables.MissingIchtaca = 1;
                        } else if(missingIchtacaTwo.isChecked()){
                            globalVariables.MissingIchtaca = 2;
                        }
                    }

                    if(scenario > 10){
                        TextView paths = findViewById(R.id.paths);
                        globalVariables.PathsKnown = Integer.valueOf(paths.getText().toString());

                        RadioButton confidenceOne = findViewById(R.id.confidence_one);
                        RadioButton confidenceTwo = findViewById(R.id.confidence_two);
                        RadioButton confidenceThree = findViewById(R.id.confidence_three);
                        if(confidenceOne.isChecked()){
                            globalVariables.IchtacaConfidence = 0;
                        } else if (confidenceTwo.isChecked()) {
                            globalVariables.IchtacaConfidence = 1;
                        } else if(confidenceThree.isChecked()){
                            globalVariables.IchtacaConfidence = 2;
                        }
                    }
                }

                if (globalVariables.Rougarou > 0) {
                    // Rougarou
                    RadioButton rougarouOne = findViewById(R.id.rougarou_one);
                    RadioButton rougarouTwo = findViewById(R.id.rougarou_two);
                    RadioButton rougarouThree = findViewById(R.id.rougarou_three);
                    if (rougarouOne.isChecked()) {
                        globalVariables.Rougarou = 1;
                    } else if (rougarouTwo.isChecked()) {
                        globalVariables.Rougarou = 2;
                    } else if (rougarouThree.isChecked()) {
                        globalVariables.Rougarou = 3;
                    }
                }

                if (globalVariables.Carnevale > 0) {
                    // Carnevale
                    RadioButton carnevaleOne = findViewById(R.id.carnevale_one);
                    RadioButton carnevaleTwo = findViewById(R.id.carnevale_two);
                    RadioButton carnevaleThree = findViewById(R.id.carnevale_three);
                    if (carnevaleOne.isChecked()) {
                        globalVariables.Carnevale = 1;
                    } else if (carnevaleTwo.isChecked()) {
                        globalVariables.Carnevale = 2;
                    } else if (carnevaleThree.isChecked()) {
                        globalVariables.Carnevale = 3;
                    }

                    // Carnevale Reward
                    RadioButton carnevaleRewardOne = findViewById(R.id.carnevale_reward_one);
                    RadioButton carnevaleRewardTwo = findViewById(R.id.carnevale_reward_two);
                    RadioButton carnevaleRewardThree = findViewById(R.id.carnevale_reward_three);
                    if (carnevaleRewardOne.isChecked()) {
                        globalVariables.CarnevaleReward = 0;
                    } else if (carnevaleRewardTwo.isChecked()) {
                        globalVariables.CarnevaleReward = 1;
                    } else if (carnevaleRewardThree.isChecked()) {
                        globalVariables.CarnevaleReward = 2;
                    }
                }

                globalVariables.Notes = notes.getText().toString().trim();

                // Save campaign
                ScenarioResolutionActivity.saveCampaign(EditLogActivity.this, globalVariables);

                // Go back to the main activity
                Intent intent = new Intent(EditLogActivity.this, ScenarioMainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Back button
        Button backButton = findViewById(R.id.back_button);
        backButton.setTypeface(teutonic);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    // Hides the soft keyboard when someone clicks outside the EditText
    public static void setupUI(final View view, final Activity activity) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    InputMethodManager inputMethodManager =
                            (InputMethodManager) activity.getSystemService(
                                    Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(
                            view.getWindowToken(), 0);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView, activity);
            }
        }
    }
}
