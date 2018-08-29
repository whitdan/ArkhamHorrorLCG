package com.whitdan.arkhamhorrorlcgcampaignguide.D_Misc;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.whitdan.arkhamhorrorlcgcampaignguide.A_Menus.MainMenuActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.E_EditMisc.EditLogActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.R;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.GlobalVariables;

import static android.view.View.VISIBLE;

public class CampaignLogActivity extends AppCompatActivity {

    GlobalVariables globalVariables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // If app is reopening after the process is killed, kick back to the main menu (stops the activity from
        // showing up unpopulated)
        if (savedInstanceState != null) {
            Intent intent = new Intent(CampaignLogActivity.this, MainMenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.d_activity_campaign_log);
        globalVariables = (GlobalVariables) this.getApplication();

        // Set title
        Typeface teutonic = Typeface.createFromAsset(getAssets(), "fonts/teutonic.ttf");
        Typeface arnopro = Typeface.createFromAsset(getAssets(), "fonts/arnopro.otf");
        TextView title = findViewById(R.id.current_scenario_name);
        title.setTypeface(teutonic);
        globalVariables.setTitle(title);

        // Get views and set fonts
        TextView log = findViewById(R.id.campaign_log);
        log.setTypeface(teutonic);
        TextView nightHeading = findViewById(R.id.night_heading);
        nightHeading.setTypeface(teutonic);
        TextView dunwichHeading = findViewById(R.id.dunwich_heading);
        dunwichHeading.setTypeface(teutonic);
        TextView carcosaHeading = findViewById(R.id.carcosa_heading);
        carcosaHeading.setTypeface(teutonic);
        TextView forgottenHeading = findViewById(R.id.forgotten_heading);
        forgottenHeading.setTypeface(teutonic);
        TextView suppliesHeading = findViewById(R.id.supplies_heading);
        suppliesHeading.setTypeface(teutonic);
        TextView sideHeading = findViewById(R.id.side_heading);
        sideHeading.setTypeface(teutonic);
        TextView playerHeading = findViewById(R.id.player_heading);
        playerHeading.setTypeface(teutonic);
        TextView notesHeading = findViewById(R.id.notes_heading);
        notesHeading.setTypeface(teutonic);
        TextView nightLog = findViewById(R.id.night_log_text);
        nightLog.setTypeface(arnopro);
        TextView dunwichLog = findViewById(R.id.dunwich_log_text);
        dunwichLog.setTypeface(arnopro);
        TextView carcosaLog = findViewById(R.id.carcosa_log_text);
        carcosaLog.setTypeface(arnopro);
        TextView forgottenLog = findViewById(R.id.forgotten_log_text);
        forgottenLog.setTypeface(arnopro);
        TextView suppliesLog = findViewById(R.id.supplies_log_text);
        suppliesLog.setTypeface(arnopro);
        TextView sideLog = findViewById(R.id.side_log_text);
        sideLog.setTypeface(arnopro);
        TextView playerLog = findViewById(R.id.player_log_text);
        playerLog.setTypeface(arnopro);
        TextView notesLog = findViewById(R.id.notes_text);
        notesLog.setTypeface(arnopro);

        int scenario;
        if (globalVariables.CurrentScenario > 100) {
            scenario = globalVariables.PreviousScenario;
        } else {
            scenario = globalVariables.CurrentScenario;
        }

        String[] investigatorNames = getResources().getStringArray(R.array.investigators);

        /*
        Night of the Zealot log
         */
        if (globalVariables.CurrentCampaign == 1 || globalVariables.NightCompleted == 1) {
            nightHeading.setVisibility(VISIBLE);
            nightLog.setVisibility(VISIBLE);
            StringBuilder nightBuilder = new StringBuilder();
            if (scenario == 1 && globalVariables.NightCompleted != 1) {
                nightBuilder.append(getString(R.string.nothing));
            }
            // First scenario log
            if (scenario > 1 || globalVariables.NightCompleted == 1) {
                // Set house status
                if (globalVariables.HouseStanding == 1) {
                    nightBuilder.append(getString(R.string.house_standing));
                } else if (globalVariables.HouseStanding == 0) {
                    nightBuilder.append(getString(R.string.house_burned));
                }
                // Set ghoul priest status
                if (globalVariables.GhoulPriest == 1) {
                    nightBuilder.append(getString(R.string.ghoul_priest_alive));
                }

                // Set Lita status
                if (globalVariables.LitaChantler == 2) {
                    nightBuilder.append(getString(R.string.lita_forced));
                }
            }
            // Second scenario log
            if (scenario > 2 || globalVariables.NightCompleted == 1) {
                // Set midnight status
                if (globalVariables.PastMidnight == 1) {
                    nightBuilder.append(getString(R.string.past_midnight));
                }
                // Set cultists
                nightBuilder.append(getString(R.string.cultists_interrogated));
                int cultistsInterrogated = 0;
                if (globalVariables.DrewInterrogated == 1) {
                    nightBuilder.append(getString(R.string.drew_log));
                    cultistsInterrogated++;
                }
                if (globalVariables.PeterInterrogated == 1) {
                    nightBuilder.append(getString(R.string.peter_log));
                    cultistsInterrogated++;
                }
                if (globalVariables.HermanInterrogated == 1) {
                    nightBuilder.append(getString(R.string.herman_log));
                    cultistsInterrogated++;
                }
                if (globalVariables.RuthInterrogated == 1) {
                    nightBuilder.append(getString(R.string.ruth_log));
                    cultistsInterrogated++;
                }
                if (globalVariables.VictoriaInterrogated == 1) {
                    nightBuilder.append(getString(R.string.victoria_log));
                    cultistsInterrogated++;
                }
                if (globalVariables.MaskedInterrogated == 1) {
                    nightBuilder.append(getString(R.string.masked_hunter_log));
                    cultistsInterrogated++;
                }
                if (cultistsInterrogated == 0) {
                    nightBuilder.append(getString(R.string.no_cultist));
                }
                nightBuilder.append(getString(R.string.cultists_got_away));
                int cultistsAway = 0;
                if (globalVariables.DrewInterrogated == 0) {
                    nightBuilder.append(getString(R.string.drew_log));
                    cultistsAway++;
                }
                if (globalVariables.PeterInterrogated == 0) {
                    nightBuilder.append(getString(R.string.peter_log));
                    cultistsAway++;
                }
                if (globalVariables.HermanInterrogated == 0) {
                    nightBuilder.append(getString(R.string.herman_log));
                    cultistsAway++;
                }
                if (globalVariables.RuthInterrogated == 0) {
                    nightBuilder.append(getString(R.string.ruth_log));
                    cultistsAway++;
                }
                if (globalVariables.VictoriaInterrogated == 0) {
                    nightBuilder.append(getString(R.string.victoria_log));
                    cultistsAway++;
                }
                if (globalVariables.MaskedInterrogated == 0) {
                    nightBuilder.append(getString(R.string.masked_hunter_log));
                    cultistsAway++;
                }
                if (cultistsAway == 0) {
                    nightBuilder.append(getString(R.string.no_cultist));
                }
            }
            // Devourer Below log
            if (globalVariables.NightCompleted == 1) {
                switch (globalVariables.Umordhoth) {
                    case 0:
                        nightBuilder.append(getString(R.string.umordhoth_succumbed));
                        break;
                    case 1:
                        nightBuilder.append(getString(R.string.umordhoth_broken));
                        break;
                    case 2:
                        nightBuilder.append(getString(R.string.umordhoth_repelled));
                        break;
                    case 3:
                        nightBuilder.append(getString(R.string.umordhoth_sacrificed));
                        break;
                }
            }
            String nightLogText = nightBuilder.toString().trim();
            nightLog.setText(nightLogText);
        }

        /*
            Dunwich Legacy log
         */
        if (globalVariables.CurrentCampaign == 2 || globalVariables.DunwichCompleted == 1) {
            dunwichHeading.setVisibility(VISIBLE);
            dunwichLog.setVisibility(VISIBLE);
            StringBuilder dunwichBuilder = new StringBuilder();
            if ((scenario == 1 && globalVariables.FirstScenario == 1) ||
                    (scenario == 2 && globalVariables.FirstScenario == 2) && (globalVariables.DunwichCompleted != 1)) {
                dunwichBuilder.append(getString(R.string.nothing));
            }
            if (globalVariables.InvestigatorsUnconscious == 1) {
                dunwichBuilder.append(getString(R.string.investigators_unconscious));
            }
            // Extracurricular Activity log
            if ((scenario > 1 && globalVariables.FirstScenario == 1) ||
                    scenario > 2 || globalVariables.DunwichCompleted == 1) {
                if (globalVariables.WarrenRice == 0) {
                    dunwichBuilder.append(getString(R.string.warren_kidnapped));
                } else if (globalVariables.WarrenRice == 1) {
                    dunwichBuilder.append(getString(R.string.warren_rescued));
                } else if (globalVariables.WarrenRice == 2) {
                    dunwichBuilder.append(getString(R.string.warren_sacrificed));
                } else if (globalVariables.WarrenRice == 3) {
                    dunwichBuilder.append(getString(R.string.warren_survived));
                }
                if (globalVariables.Students == 0) {
                    dunwichBuilder.append(getString(R.string.students_failed));
                } else if (globalVariables.Students == 1) {
                    dunwichBuilder.append(getString(R.string.students_rescued));
                } else if (globalVariables.Students == 2) {
                    dunwichBuilder.append(getString(R.string.experiment_defeated));
                }
            }
            // The House Always Wins log
            if ((scenario == 1 && globalVariables.FirstScenario == 2) ||
                    scenario > 2 || globalVariables.DunwichCompleted == 1) {
                if (globalVariables.FrancisMorgan == 0) {
                    dunwichBuilder.append(getString(R.string.morgan_kidnapped));
                } else if (globalVariables.FrancisMorgan == 1) {
                    dunwichBuilder.append(getString(R.string.morgan_rescued));
                } else if (globalVariables.FrancisMorgan == 2) {
                    dunwichBuilder.append(getString(R.string.morgan_sacrificed));
                } else if (globalVariables.FrancisMorgan == 3) {
                    dunwichBuilder.append(getString(R.string.morgan_survived));
                }
                if (globalVariables.ObannionGang == 0) {
                    dunwichBuilder.append(getString(R.string.obannion_failed));
                } else if (globalVariables.ObannionGang == 1) {
                    dunwichBuilder.append(getString(R.string.obannion_succeeded));
                }
            }
            // Interlude log
            if (scenario > 3 || globalVariables.DunwichCompleted == 1) {
                if (globalVariables.HenryArmitage == 0) {
                    dunwichBuilder.append(getString(R.string.armitage_kidnapped));
                } else if (globalVariables.HenryArmitage == 1) {
                    dunwichBuilder.append(getString(R.string.armitage_rescued));
                } else if (globalVariables.HenryArmitage == 2) {
                    dunwichBuilder.append(getString(R.string.armitage_sacrificed));
                } else if (globalVariables.HenryArmitage == 3) {
                    dunwichBuilder.append(getString(R.string.armitage_survived));
                }
            }
            // The Miskatonic Museum log
            if (scenario > 4 || globalVariables.DunwichCompleted == 1) {
                if (globalVariables.Necronomicon == 0) {
                    dunwichBuilder.append(getString(R.string.necronomicon_failed));
                } else if (globalVariables.Necronomicon == 1) {
                    dunwichBuilder.append(getString(R.string.necronomicon_destroyed));
                } else if (globalVariables.Necronomicon == 2) {
                    dunwichBuilder.append(getString(R.string.necronomicon_taken));
                } else if (globalVariables.Necronomicon == 3) {
                    dunwichBuilder.append(getString(R.string.necronomicon_stolen));
                }
            }
            // The Essex County Express log
            if ((scenario > 5 || globalVariables.DunwichCompleted == 1) && globalVariables.InvestigatorsDelayed == 1) {
                dunwichBuilder.append(getString(R.string.investigators_delayed));
            }
            // Blood on the Altar log
            if (scenario > 6 || globalVariables.DunwichCompleted == 1) {
                switch (globalVariables.SilasBishop) {
                    case 0:
                        dunwichBuilder.append(getString(R.string.silas_ritual));
                        break;
                    case 1:
                        dunwichBuilder.append(getString(R.string.silas_misery));
                        break;
                    case 2:
                        dunwichBuilder.append(getString(R.string.silas_restored));
                        break;
                    case 3:
                        dunwichBuilder.append(getString(R.string.silas_banished));
                        break;
                }
                if (globalVariables.ZebulonWhateley == 1) {
                    dunwichBuilder.append(getString(R.string.zebulon_sacrificed));
                } else {
                    dunwichBuilder.append(getString(R.string.zebulon_survived));
                }
                if (globalVariables.EarlSawyer == 1) {
                    dunwichBuilder.append(getString(R.string.sawyer_sacrificed));
                } else {
                    dunwichBuilder.append(getString(R.string.sawyer_survived));
                }
                if (globalVariables.AllySacrificed == 1) {
                    dunwichBuilder.append(getString(R.string.ally_sacrificed));
                }
            }
            // Undimensioned and Unseen log
            if (scenario > 8 || globalVariables.DunwichCompleted == 1) {
                if (globalVariables.TownsfolkAction == 1) {
                    dunwichBuilder.append(getString(R.string.townsfolk_calmed));
                } else if (globalVariables.TownsfolkAction == 2) {
                    dunwichBuilder.append(getString(R.string.townsfolk_warned));
                }
                dunwichBuilder.append(globalVariables.BroodsEscaped);
                dunwichBuilder.append(" ");
                dunwichBuilder.append(getString(R.string.brood_escaped));
            }
            // Where Doom Awaits log
            if (scenario > 9 || globalVariables.DunwichCompleted == 1) {
                if (globalVariables.InvestigatorsGate == 1) {
                    dunwichBuilder.append(getString(R.string.investigators_entered_gate));
                } else {
                    dunwichBuilder.append(getString(R.string.yog_tore_barrier));
                }
            }
            // Lost in Time and Space log
            if (scenario > 10 || globalVariables.DunwichCompleted == 1) {
                switch (globalVariables.YogSothoth) {
                    case 1:
                        dunwichBuilder.append(getString(R.string.closed_tear));
                        break;
                    case 2:
                        dunwichBuilder.append(getString(R.string.yog_fled));
                        break;
                    case 3:
                        dunwichBuilder.append(getString(R.string.yog_tore_barrier));
                        break;
                }
            }
            String dunwichLogText = dunwichBuilder.toString().trim();
            dunwichLog.setText(dunwichLogText);
        }

        /*
            Path to Carcosa log
         */
        if (globalVariables.CurrentCampaign == 3 || globalVariables.CarcosaCompleted == 1) {
            carcosaHeading.setVisibility(VISIBLE);
            carcosaLog.setVisibility(VISIBLE);
            StringBuilder carcosaBuilder = new StringBuilder();
            String doubt = getString(R.string.doubt) + " " + Integer.toString(globalVariables.Doubt) + "\n\n";
            String conviction = getString(R.string.conviction) + " " + Integer.toString(globalVariables.Conviction) +
                    "\n\n";
            carcosaBuilder.append(doubt);
            carcosaBuilder.append(conviction);
            // First scenario log
            if (scenario > 1 || globalVariables.CarcosaCompleted == 1) {
                String stranger = getString(R.string.stranger_counter) + " " + Integer.toString(globalVariables
                        .ChasingStranger) +
                        "\n\n";
                carcosaBuilder.append(stranger);
                if (globalVariables.Stranger == 1) {
                    carcosaBuilder.append(getString(R.string.stranger));
                }
                if (globalVariables.Police == 1 || globalVariables.Police == 2) {
                    carcosaBuilder.append(getString(R.string.warned_police));
                } else if (globalVariables.Police == 3) {
                    carcosaBuilder.append(getString(R.string.not_police));
                }
                if (globalVariables.Police == 2) {
                    carcosaBuilder.append(getString(R.string.police_suspicious));
                }
            }
            // Second scenario log
            if (scenario > 2 || globalVariables.CarcosaCompleted == 1) {
                carcosaBuilder.append(getString(R.string.vips_interviewed_log));
                int vipsInterviewed = 0;
                if (globalVariables.Constance == 1 || globalVariables.Constance == 4) {
                    carcosaBuilder.append(getString(R.string.constance_log));
                    vipsInterviewed++;
                }
                if (globalVariables.Ishimaru == 1 || globalVariables.Ishimaru == 4) {
                    carcosaBuilder.append(getString(R.string.ishimaru_log));
                    vipsInterviewed++;
                }
                if (globalVariables.Jordan == 1 || globalVariables.Jordan == 4) {
                    carcosaBuilder.append(getString(R.string.jordan_log));
                    vipsInterviewed++;
                }
                if (globalVariables.Sebastien == 1 || globalVariables.Sebastien == 4) {
                    carcosaBuilder.append(getString(R.string.sebastien_log));
                    vipsInterviewed++;
                }
                if (globalVariables.Ashleigh == 1 || globalVariables.Ashleigh == 4) {
                    carcosaBuilder.append(getString(R.string.ashleigh_log));
                    vipsInterviewed++;
                }
                if (vipsInterviewed == 0) {
                    carcosaBuilder.append(getString(R.string.no_vips));
                }
                carcosaBuilder.append("\n");
                carcosaBuilder.append(getString(R.string.vips_slain_log));
                int vipsSlain = 0;
                if (globalVariables.Constance == 2 || globalVariables.Constance == 4 || globalVariables.Constance ==
                        5) {
                    carcosaBuilder.append(getString(R.string.constance_log));
                    vipsSlain++;
                }
                if (globalVariables.Ishimaru == 2 || globalVariables.Ishimaru == 4 || globalVariables.Ishimaru == 5) {
                    carcosaBuilder.append(getString(R.string.ishimaru_log));
                    vipsSlain++;
                }
                if (globalVariables.Jordan == 2 || globalVariables.Jordan == 4 || globalVariables.Jordan == 5) {
                    carcosaBuilder.append(getString(R.string.jordan_log));
                    vipsSlain++;
                }
                if (globalVariables.Sebastien == 2 || globalVariables.Sebastien == 4 || globalVariables.Sebastien ==
                        5) {
                    carcosaBuilder.append(getString(R.string.sebastien_log));
                    vipsSlain++;
                }
                if (globalVariables.Ashleigh == 2 || globalVariables.Ashleigh == 4 || globalVariables.Ashleigh == 5) {
                    carcosaBuilder.append(getString(R.string.ashleigh_log));
                    vipsSlain++;
                }
                if (vipsSlain == 0) {
                    carcosaBuilder.append(getString(R.string.no_vips));
                }
                carcosaBuilder.append("\n");
            }
            // Interlude log
            if (scenario > 3 || globalVariables.CarcosaCompleted == 1) {
                if (globalVariables.Party == 1) {
                    carcosaBuilder.append(getString(R.string.intruded));
                } else if (globalVariables.Party == 2) {
                    carcosaBuilder.append(getString(R.string.fled));
                } else if (globalVariables.Party == 3) {
                    carcosaBuilder.append(getString(R.string.slayed));
                }
            }
            // Echoes of the Past
            if (scenario > 4 || globalVariables.CarcosaCompleted == 1) {
                switch (globalVariables.Onyx) {
                    case 1:
                        carcosaBuilder.append(getString(R.string.onyx_taken));
                        break;
                    case 2:
                        carcosaBuilder.append(getString(R.string.onyx_left));
                        break;
                    case 3:
                        carcosaBuilder.append(getString(R.string.oathspeaker_destroyed));
                        break;
                    case 4:
                        carcosaBuilder.append(getString(R.string.followers_sign));
                        break;
                }
            }
            // Unspeakable Oath
            if (scenario > 5 || globalVariables.CarcosaCompleted == 1) {
                switch (globalVariables.Asylum) {
                    case 1:
                        carcosaBuilder.append(getString(R.string.king_victims));
                        break;
                    case 2:
                        carcosaBuilder.append(getString(R.string.attacked_asylum));
                        break;
                    case 3:
                        carcosaBuilder.append(getString(R.string.escaped_asylum));
                }
            }
            // Interlude 2
            if (scenario > 6 || globalVariables.CarcosaCompleted == 1) {
                switch (globalVariables.DanielsWarning) {
                    case 1:
                        carcosaBuilder.append(getString(R.string.warning_ignored));
                        break;
                    case 2:
                        carcosaBuilder.append(getString(R.string.warning_heeded));
                        break;
                }
            }
            // Phantom of Truth
            if (scenario > 7 || globalVariables.CarcosaCompleted == 1) {
                switch (globalVariables.Nigel) {
                    case 0:
                        carcosaBuilder.append(getString(R.string.not_escape_gaze));
                        break;
                    case 1:
                        carcosaBuilder.append(getString(R.string.found_nigel_home));
                        break;
                    case 2:
                        carcosaBuilder.append(getString(R.string.found_nigel_engram));
                        break;
                    case 3:
                        carcosaBuilder.append(getString(R.string.unable_find_nigel));
                        break;
                }
            }
            // Pallid Mask
            if (scenario > 8 || globalVariables.CarcosaCompleted == 1) {
                if (globalVariables.Nigel == 0 || globalVariables.Nigel == 3) {
                    carcosaBuilder.append(getString(R.string.awoke_catacombs));
                } else {
                    carcosaBuilder.append(getString(R.string.entered_catacombs));
                }
                carcosaBuilder.append(getString(R.string.know_site));
                if (globalVariables.InvOneReadAct > 0) {
                    if (globalVariables.InvOneReadAct == 999) {
                        carcosaBuilder.append(getString(R.string.inv_one_read_act));
                    } else {
                        carcosaBuilder.append(investigatorNames[globalVariables.InvOneReadAct] + " ");
                        carcosaBuilder.append(getString(R.string.read_act));
                    }
                }
                if (globalVariables.InvTwoReadAct > 0) {
                    if (globalVariables.InvTwoReadAct == 999) {
                        carcosaBuilder.append(getString(R.string.inv_two_read_act));
                    } else {
                        carcosaBuilder.append(investigatorNames[globalVariables.InvTwoReadAct] + " ");
                        carcosaBuilder.append(getString(R.string.read_act));
                    }
                }
                if (globalVariables.InvThreeReadAct > 0) {
                    if (globalVariables.InvThreeReadAct == 999) {
                        carcosaBuilder.append(getString(R.string.inv_three_read_act));
                    } else {
                        carcosaBuilder.append(investigatorNames[globalVariables.InvThreeReadAct] + " ");
                        carcosaBuilder.append(getString(R.string.read_act));
                    }
                }
                if (globalVariables.InvFourReadAct > 0) {
                    if (globalVariables.InvFourReadAct == 999) {
                        carcosaBuilder.append(getString(R.string.inv_four_read_act));
                    } else {
                        carcosaBuilder.append(investigatorNames[globalVariables.InvFourReadAct] + " ");
                        carcosaBuilder.append(getString(R.string.read_act));
                    }
                }
            }
            // Black Stars Rise
            if (scenario > 9 || globalVariables.CarcosaCompleted == 1) {
                switch (globalVariables.Path) {
                    case 0:
                        carcosaBuilder.append(getString(R.string.carcosa_merged));
                        break;
                    case 1:
                        carcosaBuilder.append(getString(R.string.path_below));
                        break;
                    case 2:
                        carcosaBuilder.append(getString(R.string.path_above));
                        break;
                }
            }
            // Dim Carcosa
            if ((scenario > 10 || globalVariables.CarcosaCompleted == 1) && globalVariables.Path != 0) {
                switch (globalVariables.Hastur) {
                    case 1:
                    case 2:
                    case 3:
                        carcosaBuilder.append(getString(R.string.prevented_hastur));
                        break;
                    case 4:
                        carcosaBuilder.append(getString(R.string.carcosa_merged));
                        break;
                    case 5:
                        carcosaBuilder.append(getString(R.string.hasturs_grasp));
                        break;
                }
                if (globalVariables.InvOnePossessed > 0) {
                    if (globalVariables.InvOnePossessed == 999) {
                        carcosaBuilder.append(getString(R.string.inv_one_possessed));
                    } else {
                        carcosaBuilder.append(investigatorNames[globalVariables.InvOnePossessed] + " ");
                        carcosaBuilder.append(getString(R.string.possessed));
                    }
                }
                if (globalVariables.InvTwoPossessed > 0) {
                    if (globalVariables.InvTwoPossessed == 999) {
                        carcosaBuilder.append(getString(R.string.inv_two_possessed));
                    } else {
                        carcosaBuilder.append(investigatorNames[globalVariables.InvTwoPossessed] + " ");
                        carcosaBuilder.append(getString(R.string.possessed));
                    }
                }
                if (globalVariables.InvThreePossessed > 0) {
                    if (globalVariables.InvThreePossessed == 999) {
                        carcosaBuilder.append(getString(R.string.inv_three_possessed));
                    } else {
                        carcosaBuilder.append(investigatorNames[globalVariables.InvThreePossessed] + " ");
                        carcosaBuilder.append(getString(R.string.possessed));
                    }
                }
                if (globalVariables.InvFourPossessed > 0) {
                    if (globalVariables.InvFourPossessed == 999) {
                        carcosaBuilder.append(getString(R.string.inv_four_possessed));
                    } else {
                        carcosaBuilder.append(investigatorNames[globalVariables.InvFourPossessed] + " ");
                        carcosaBuilder.append(getString(R.string.possessed));
                    }
                }
            }

            String carcosaLogText = carcosaBuilder.toString().trim();
            carcosaLog.setText(carcosaLogText);
        }

        /*
            Forgotten Age log
         */
        if (globalVariables.CurrentCampaign == 4) {
            forgottenHeading.setVisibility(VISIBLE);
            forgottenLog.setVisibility(VISIBLE);
            StringBuilder forgottenBuilder = new StringBuilder();

            if (scenario == 1 && globalVariables.ForgottenCompleted != 1) {
                forgottenBuilder.append(getString(R.string.nothing));
            }

            // First scenario log
            if (scenario > 1 || globalVariables.ForgottenCompleted == 1) {
                String yigsFury = getString(R.string.yigs_fury) + " " + Integer.toString(globalVariables
                        .YigsFury) +
                        "\n\n";
                forgottenBuilder.append(yigsFury);
                if (globalVariables.Ruins == 1) {
                    forgottenBuilder.append(getString(R.string.forced_wait));
                } else if (globalVariables.Ruins == 2) {
                    forgottenBuilder.append(getString(R.string.cleared_path));
                }
                if (globalVariables.Ichtaca == 0) {
                    forgottenBuilder.append(getString(R.string.ichtaca_observed));
                } else if (globalVariables.Ichtaca == 1) {
                    forgottenBuilder.append(getString(R.string.ichtaca_trust));
                } else if (globalVariables.Ichtaca == 2) {
                    forgottenBuilder.append(getString(R.string.ichtaca_wary));
                }
                if (globalVariables.Alejandro == 1) {
                    forgottenBuilder.append(getString(R.string.alejandro_remained));
                } else if (globalVariables.Alejandro == 2) {
                    forgottenBuilder.append(getString(R.string.alejandro_followed));
                }
            }

            // Second scenario log
            if (scenario > 6 || globalVariables.ForgottenCompleted == 1) {
                if (globalVariables.Relic == 1) {
                    forgottenBuilder.append(getString(R.string.investigators_recovered_relic));
                } else if (globalVariables.Relic == 2) {
                    forgottenBuilder.append(getString(R.string.alejandro_recovered_relic));
                }
                if (globalVariables.Harbinger > -1) {
                    forgottenBuilder.append(getString(R.string.harbinger_alive));
                    forgottenBuilder.append(" (");
                    forgottenBuilder.append(Integer.toString(globalVariables.Harbinger));
                    forgottenBuilder.append(")\n\n");
                }
            }

            // Second interlude log
            if (scenario > 7 || globalVariables.ForgottenCompleted == 1) {
                if (globalVariables.Custody == 1) {
                    forgottenBuilder.append(getString(R.string.custody_alejandro));
                } else if (globalVariables.Custody == 2) {
                    forgottenBuilder.append(getString(R.string.custody_harlan));
                }
                if (globalVariables.IchtacasTale == 4) {
                    forgottenBuilder.append(getString(R.string.forging_own_path));
                }
            }

            // Threads of Fate log
            if (scenario > 8 || globalVariables.ForgottenCompleted == 1) {
                if (globalVariables.MissingRelic == 1) {
                    forgottenBuilder.append(getString(R.string.relic_missing));
                } else if (globalVariables.MissingRelic == 2) {
                    forgottenBuilder.append(getString(R.string.found_relic));
                }
                if (globalVariables.MissingAlejandro == 1) {
                    forgottenBuilder.append(getString(R.string.alejandro_missing));
                } else if (globalVariables.MissingAlejandro == 2) {
                    forgottenBuilder.append(getString(R.string.rescued_alejandro));
                }
                if (globalVariables.MissingIchtaca == 1) {
                    forgottenBuilder.append(getString(R.string.ichtaca_dark));
                } else if (globalVariables.MissingIchtaca == 2) {
                    forgottenBuilder.append(getString(R.string.bond_ichtaca));
                }
            }

            // Boundary Beyond log
            if (scenario > 9 || globalVariables.ForgottenCompleted == 1) {
                String paths = Integer.toString(globalVariables.PathsKnown) + " " + getResources().getString(R.string
                        .paths_known);
                forgottenBuilder.append(paths);
                if (globalVariables.IchtacaConfidence == 1 && globalVariables.PathsKnown >= 3) {
                    forgottenBuilder.append(getString(R.string.ichtaca_confidence));
                } else if (globalVariables.IchtacaConfidence == 2) {
                    forgottenBuilder.append(getString(R.string.ichtaca_confidence));
                    forgottenBuilder.append(getString(R.string.ichtaca_faith));
                }
                boolean map = false;
                for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                    if (globalVariables.Investigators.get(i).Supplies % 13 == 0) {
                        map = true;
                    }
                }
                if(map){
                    forgottenBuilder.append(getString(R.string.mapped_forward));
                }
            }

            String forgottenLogText = forgottenBuilder.toString().trim();
            forgottenLog.setText(forgottenLogText);

            // Supplies
            suppliesHeading.setVisibility(VISIBLE);
            suppliesLog.setVisibility(VISIBLE);
            StringBuilder suppliesBuilder = new StringBuilder();
            for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                if (i > 0) {
                    suppliesBuilder.append("\n");
                }
                suppliesBuilder.append(investigatorNames[globalVariables.Investigators.get(i).Name]);
                suppliesBuilder.append("\n");

                boolean supplies = false;

                if (globalVariables.Investigators.get(i).Provisions > 0) {
                    suppliesBuilder.append("\t\t");
                    suppliesBuilder.append(Integer.toString(globalVariables.Investigators.get(i).Provisions));
                    suppliesBuilder.append(" ");
                    suppliesBuilder.append(getResources().getString(R.string.provisions));
                    suppliesBuilder.append("\n");
                    supplies = true;
                }

                if (globalVariables.Investigators.get(i).Medicine > 0) {
                    suppliesBuilder.append("\t\t");
                    suppliesBuilder.append(Integer.toString(globalVariables.Investigators.get(i).Medicine));
                    suppliesBuilder.append(" ");
                    suppliesBuilder.append(getResources().getString(R.string.medicine));
                    suppliesBuilder.append("\n");
                    supplies = true;
                }

                if (globalVariables.Investigators.get(i).Supplies % 2 == 0) {
                    suppliesBuilder.append("\t\t");
                    suppliesBuilder.append(getResources().getString(R.string.rope));
                    suppliesBuilder.append("\n");
                    supplies = true;
                }
                if (globalVariables.Investigators.get(i).Supplies % 3 == 0 ||
                        globalVariables.Investigators.get(i).ResuppliesOne % 3 == 0) {
                    suppliesBuilder.append("\t\t");
                    suppliesBuilder.append(getResources().getString(R.string.blanket));
                    suppliesBuilder.append("\n");
                    supplies = true;
                }
                if (globalVariables.Investigators.get(i).Supplies % 5 == 0 ||
                        globalVariables.Investigators.get(i).ResuppliesOne % 5 == 0) {
                    suppliesBuilder.append("\t\t");
                    suppliesBuilder.append(getResources().getString(R.string.canteen));
                    suppliesBuilder.append("\n");
                    supplies = true;
                }
                if (globalVariables.Investigators.get(i).Supplies % 7 == 0) {
                    suppliesBuilder.append("\t\t");
                    suppliesBuilder.append(getResources().getString(R.string.torches));
                    suppliesBuilder.append("\n");
                    supplies = true;
                }
                if (globalVariables.Investigators.get(i).Supplies % 11 == 0 ||
                        globalVariables.Investigators.get(i).ResuppliesOne % 11 == 0) {
                    suppliesBuilder.append("\t\t");
                    suppliesBuilder.append(getResources().getString(R.string.compass));
                    suppliesBuilder.append("\n");
                    supplies = true;
                }
                if (globalVariables.Investigators.get(i).Supplies % 13 == 0) {
                    suppliesBuilder.append("\t\t");
                    suppliesBuilder.append(getResources().getString(R.string.map));
                    suppliesBuilder.append("\n");
                    supplies = true;
                }
                if (globalVariables.Investigators.get(i).Supplies % 17 == 0 ||
                        globalVariables.Investigators.get(i).ResuppliesOne % 17 == 0) {
                    suppliesBuilder.append("\t\t");
                    suppliesBuilder.append(getResources().getString(R.string.binoculars));
                    suppliesBuilder.append("\n");
                    supplies = true;
                }
                if (globalVariables.Investigators.get(i).Supplies % 19 == 0 ||
                        globalVariables.Investigators.get(i).ResuppliesOne % 19 == 0) {
                    suppliesBuilder.append("\t\t");
                    suppliesBuilder.append(getResources().getString(R.string.chalk));
                    suppliesBuilder.append("\n");
                    supplies = true;
                }
                if (globalVariables.Investigators.get(i).Supplies % 23 == 0) {
                    suppliesBuilder.append("\t\t");
                    suppliesBuilder.append(getResources().getString(R.string.pendant));
                    suppliesBuilder.append("\n");
                    supplies = true;
                }
                if (globalVariables.Investigators.get(i).Supplies % 29 == 0 ||
                        globalVariables.Investigators.get(i).ResuppliesOne % 2 == 0) {
                    suppliesBuilder.append("\t\t");
                    suppliesBuilder.append(getResources().getString(R.string.gasoline));
                    suppliesBuilder.append("\n");
                    supplies = true;
                }
                if (globalVariables.Investigators.get(i).Supplies % 31 == 0 ||
                        globalVariables.Investigators.get(i).ResuppliesOne % 7 == 0) {
                    suppliesBuilder.append("\t\t");
                    suppliesBuilder.append(getResources().getString(R.string.pocketknife));
                    suppliesBuilder.append("\n");
                    supplies = true;
                }
                if (globalVariables.Investigators.get(i).Supplies % 37 == 0 ||
                        globalVariables.Investigators.get(i).ResuppliesOne % 13 == 0) {
                    suppliesBuilder.append("\t\t");
                    suppliesBuilder.append(getResources().getString(R.string.pickaxe));
                    suppliesBuilder.append("\n");
                    supplies = true;
                }
                if (!supplies) {
                    suppliesBuilder.append("\t\t");
                    suppliesBuilder.append(getResources().getString(R.string.no_supplies));
                    suppliesBuilder.append("\n");
                }
            }
            String suppliesLogText = suppliesBuilder.toString().trim();
            suppliesLog.setText(suppliesLogText);
        }

        /*
            Side story log
         */
        if (globalVariables.Rougarou > 0 || globalVariables.Carnevale > 0) {
            sideHeading.setVisibility(VISIBLE);
            sideLog.setVisibility(VISIBLE);
            StringBuilder sideBuilder = new StringBuilder();
            if (globalVariables.Rougarou == 1) {
                sideBuilder.append(getString(R.string.rougarou_alive));
            } else if (globalVariables.Rougarou == 2) {
                sideBuilder.append(getString(R.string.rougarou_destroyed));
            } else if (globalVariables.Rougarou == 3) {
                sideBuilder.append(getString(R.string.rougarou_escaped));
            }
            if (globalVariables.Carnevale == 1) {
                sideBuilder.append(getString(R.string.carnevale_many_sacrificed));
            } else if (globalVariables.Carnevale == 2) {
                sideBuilder.append(getString(R.string.carnevale_banished));
            } else if (globalVariables.Carnevale == 3) {
                sideBuilder.append(getString(R.string.carnevale_retreated));
            }
            if (globalVariables.CarnevaleReward == 1) {
                sideBuilder.append(getString(R.string.carnevale_sacrifice_made));
            } else if (globalVariables.CarnevaleReward == 2) {
                sideBuilder.append(getString(R.string.carnevale_abbess_satisfied));
            }
            String sideLogText = sideBuilder.toString().trim();
            sideLog.setText(sideLogText);
        }

        /*
            Player cards log
         */
        if (globalVariables.StrangeSolution == 1 || globalVariables.ArchaicGlyphs == 1 || globalVariables
                .AncientStone > 0 || globalVariables.Doomed > 0) {
            playerHeading.setVisibility(VISIBLE);
            playerLog.setVisibility(VISIBLE);
            StringBuilder playerBuilder = new StringBuilder();
            if (globalVariables.StrangeSolution == 1) {
                playerBuilder.append(getString(R.string.strange_solution));
            }
            if (globalVariables.ArchaicGlyphs == 1) {
                playerBuilder.append(getString(R.string.archaic_glyphs));
            }
            if (globalVariables.AncientStone > 0) {
                playerBuilder.append(getString(R.string.ancient_stone));
                playerBuilder.append(Integer.toString(globalVariables.AncientStone));
                playerBuilder.append(")\n\n");
            }
            if (globalVariables.Doomed > 0) {
                playerBuilder.append(getString(R.string.doomed_log));
            }
            if (globalVariables.Doomed > 1) {
                playerBuilder.append(getString(R.string.accursed_fate_log));
            }
            String playerLogText = playerBuilder.toString().trim();
            playerLog.setText(playerLogText);
        }

        /*
            Notes
         */
        if (globalVariables.Notes != null && globalVariables.Notes.length() > 0) {
            notesHeading.setVisibility(VISIBLE);
            notesLog.setVisibility(VISIBLE);
            notesLog.setText(globalVariables.Notes);
        }

        // Edit log button
        Button editButton = findViewById(R.id.edit_log_button);
        editButton.setTypeface(teutonic);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CampaignLogActivity.this, EditLogActivity.class);
                startActivity(intent);
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
}
