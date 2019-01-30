package com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data;

import android.app.Application;
import android.widget.TextView;

import com.whitdan.arkhamhorrorlcgcampaignguide.R;

import java.util.ArrayList;

/*
    GlobalVariables - Contains all the variables which need to persist between activities
 */

public class GlobalVariables extends Application {

    /*
        Campaign variables - All of the variables associated with the campaign generally
     */
    public String CampaignName;
    public long CampaignID;                        // Corresponds to the SQLite database
    public long ChaosBagID;                        // Corresponds to SQLite database for chaos bag (-1 = default)
    public int CampaignVersion;                    // 1 = Original, 2 = added total XP)
    public int CurrentCampaign;                    // 1 = Night, 2 = Dunwich, 3 = Carcosa, 999 = standalone,
                                                    // 1000 = standalone chaos bag
    public int CurrentScenario;                    // 0 = campaign setup, >100 = standalone, 1000 = between campaigns
    public int CurrentDifficulty;                  // 0 = easy, 1 = normal, 2 = hard, 3 = expert (except standalones)
    public String Notes;
    public int NightCompleted;
    public int DunwichCompleted;
    public int CarcosaCompleted;
    public int ForgottenCompleted;
    public ArrayList<Integer> seal;

    /*
        Scenario variables - All of the variables associated with the scenario generally
     */
    public int ScenarioResolution;                 // 0 = no resolution
    public int VictoryDisplay;

    /*
        Investigator variables - All of the variables associated with the investigators generally
     */
    public ArrayList<Investigator> Investigators = new ArrayList<>();
    public ArrayList<Investigator> SavedInvestigators = new ArrayList<>();
    public ArrayList<Integer> InvestigatorNames = new ArrayList<>();
    public String[] PlayerNames = new String[4];
    public String[] DeckNames = new String[4];
    public String[] DeckLists = new String[4];
    public int[] InvestigatorsInUse = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
                                                                        // Matches up to the names in the string array

    /*
        Night of the Zealot variables - All of the variables associated with the Night of the Zealot campaign
     */
    public int HouseStanding;       // 0 = burned, 1 = standing
    public int GhoulPriest;         // 0 = dead, 1 = alive
    public int LitaChantler;        // 0 = not obtained, 1 = obtained, 2 = forced to find others
    public int PastMidnight;
    public int Umordhoth;
    // All of the cultists
    public int CultistsInterrogated = 0;
    public int DrewInterrogated;
    public int HermanInterrogated;
    public int PeterInterrogated;
    public int VictoriaInterrogated;
    public int RuthInterrogated;
    public int MaskedInterrogated;

    /*
        The Dunwich Legacy variables - All of the variables associated with The Dunwich Legacy campaign
     */
    public int FirstScenario;
    public int InvestigatorsUnconscious;
    public int HenryArmitage;
    public int WarrenRice;
    public int Students;
    public int ObannionGang;
    public int FrancisMorgan;
    public int InvestigatorsCheated;
    public int Necronomicon;
    public int AdamLynchHaroldWalsted;
    public int InvestigatorsDelayed;
    public int EngineCar;
    public int SilasBishop;
    public int ZebulonWhateley;
    public int EarlSawyer;
    public int AllySacrificed;
    public int TownsfolkAction;     // 0 = not set, 1 = calmed, 2 = warned
    public int BroodsEscaped;
    public int InvestigatorsGate;
    public int YogSothoth;

    /*
        Path to Carcosa variables
     */
    public int Doubt;
    public int Conviction;
    public int ChasingStranger;
    public int Stranger;
    public int Police;
    public int Theatre;     // Only used for chaos bag (1 = 2 cultists, 2 = 2 tablets, 3 = 2 things, 4 = 1 each)
    public int Party;
    public int Onyx;
    public int Asylum;
    public int Daniel;
    public int DanielsWarning;
    public int DreamsAction;    // 0 = not set
    public int Nigel;
    public int InvOneReadAct;
    public int InvTwoReadAct;
    public int InvThreeReadAct;
    public int InvFourReadAct;
    public int Path;
    public int Hastur;
    public int InvOnePossessed;
    public int InvTwoPossessed;
    public int InvThreePossessed;
    public int InvFourPossessed;

    // VIPS - 0 = not interviewed or killed, 1 = interviewed, 2 = killed, 3 = interviewed (crossed out), 4 =
    // interviewed and killed, 5 = interviewed (crossed out) and killed
    public int Constance;
    public int Jordan;
    public int Ishimaru;
    public int Sebastien;
    public int Ashleigh;

    /*
        Forgotten Age variables
     */
    public int YigsFury;
    public int Ruins;
    public int Ichtaca;
    public int Alejandro;
    public int LowRations;
    public int Relic;
    public int Harbinger;
    public int Eztli;
    public int Custody;
    public int IchtacasTale;
    public int MissingRelic;
    public int MissingAlejandro;
    public int MissingIchtaca;
    public int GasolineUsed;
    public int PathsKnown;
    public int IchtacaConfidence;

    public int Mapped;
    public String JungleWatches;

    /*
        Side story variables - All of the variables associated with side stories
     */
    public int PreviousScenario;
    public int Rougarou;
    public int Carnevale;
    public int CarnevaleReward;

    /*
        Player cards - All of the variables associated with specific player cards
     */
    public int StrangeSolution;
    public int ArchaicGlyphs;
    public int CharonsObol;
    public int AncientStone;
    public int Doomed;

    /*
        Method for each scenario to set its title
     */
    public void setTitle(TextView title){
        switch (CurrentCampaign) {
            case 1:
                switch (CurrentScenario) {
                    case 1:
                        title.setText(R.string.night_scenario_one);
                        break;
                    case 2:
                        title.setText(R.string.night_scenario_two);
                        break;
                    case 3:
                        title.setText(R.string.night_scenario_three);
                        break;
                    case 4:
                        title.setText(R.string.campaign_completed);
                        break;
                }
                break;
            case 2:
                switch (CurrentScenario) {
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
                    case 12:
                        title.setText(R.string.campaign_completed);
                        break;
                }
                break;
            case 3:
                switch (CurrentScenario) {
                    case 0:
                        title.setText(R.string.carcosa_interlude_zero);
                        break;
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
            case 4:
                switch(CurrentScenario){
                    case 1:
                        title.setText(R.string.forgotten_scenario_one);
                        break;
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                        title.setText(R.string.forgotten_interlude_one);
                        break;
                    case 6:
                        title.setText(R.string.forgotten_scenario_two);
                        break;
                    case 7:
                        title.setText(R.string.forgotten_interlude_two);
                        break;
                    case 8:
                        title.setText(R.string.forgotten_scenario_three);
                        break;
                    case 10:
                        title.setText(R.string.forgotten_scenario_four);
                        break;
                    case 11:
                    case 12:
                    case 13:
                    case 14:
                    case 15:
                    case 16:
                    case 17:
                        title.setText(R.string.forgotten_interlude_three);
                        break;
                    case 18:
                        title.setText(R.string.forgotten_scenario_five_a);
                        break;
                    case 19:
                        title.setText(R.string.forgotten_scenario_five_b);
                        break;
                    case 20:
                        title.setText(R.string.forgotten_scenario_six);
                        break;
                    case 21:
                    case 22:
                    case 23:
                    case 24:
                    case 25:
                    case 26:
                        title.setText(R.string.forgotten_interlude_four);
                        break;
                    case 27:
                        title.setText(R.string.forgotten_scenario_seven);
                        break;
                    case 28:
                    case 29:
                    case 30:
                    case 31:
                    case 32:
                        title.setText(R.string.forgotten_interlude_five);
                        break;
                    case 33:
                        title.setText(R.string.forgotten_scenario_eight);
                        break;
                    case 34:
                        title.setText(R.string.forgotten_epilogue);
                        break;
                    case 35:
                        title.setText(R.string.forgotten_secret_scenario);
                        break;
                }
        }
        if (CurrentScenario > 100) {
            switch (CurrentScenario) {
                case 101:
                    title.setText(R.string.rougarou_scenario);
                    break;
                case 102:
                    title.setText(R.string.carnevale_scenario);
                    break;
            }
        }
    }
}
