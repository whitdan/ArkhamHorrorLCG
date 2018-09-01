package com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data;

import android.provider.BaseColumns;

/**
 * Contract for the layout of the SQL tables.
 * Currently there are three tables:
 *          campaigns - contains all global variables
 *          investigators - contains a row per investigator, with all relevant variables
 *          night - contains all variables specific to the Night of the Zealot campaign
 */

public class ArkhamContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private ArkhamContract() {}

    /* Inner class that defines the table contents */
    public static class CampaignEntry implements BaseColumns {
        public static final String TABLE_NAME = "campaigns";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_CAMPAIGN_VERSION = "version";
        public static final String COLUMN_CAMPAIGN_NAME = "name";
        public static final String COLUMN_CHAOS_BAG = "chaos_bag";
        public static final String COLUMN_CURRENT_CAMPAIGN = "campaign"; // Denotes which campaign
        public static final String COLUMN_CURRENT_SCENARIO = "scenario";
        public static final String COLUMN_DIFFICULTY = "difficulty";
        public static final String COLUMN_NOTES = "notes";
        public static final String COLUMN_NIGHT_COMPLETED = "night_completed";
        public static final String COLUMN_DUNWICH_COMPLETED = "dunwich_completed";
        public static final String COLUMN_CARCOSA_COMPLETED = "carcosa_completed";
        public static final String COLUMN_ROLAND_INUSE = "roland";
        public static final String COLUMN_DAISY_INUSE = "daisy";
        public static final String COLUMN_SKIDS_INUSE = "skids";
        public static final String COLUMN_AGNES_INUSE ="agnes";
        public static final String COLUMN_WENDY_INUSE = "wendy";
        public static final String COLUMN_ZOEY_INUSE = "zoey";
        public static final String COLUMN_REX_INUSE = "rex";
        public static final String COLUMN_JENNY_INUSE = "jenny";
        public static final String COLUMN_JIM_INUSE = "jim";
        public static final String COLUMN_PETE_INUSE = "pete";
        public static final String COLUMN_MARK_INUSE = "mark";
        public static final String COLUMN_MINH_INUSE = "minh";
        public static final String COLUMN_SEFINA_INUSE = "sefina";
        public static final String COLUMN_AKACHI_INUSE = "akachi";
        public static final String COLUMN_WILLIAM_INUSE = "william";
        public static final String COLUMN_LOLA_INUSE = "lola";
        public static final String COLUMN_MARIE_INUSE = "marie";
        public static final String COLUMN_NORMAN_INUSE = "norman";
        public static final String COLUMN_CAROLYN_INUSE = "carolyn";
        public static final String COLUMN_SILAS_INUSE = "silas";
        public static final String COLUMN_LEO_INUSE = "leo";
        public static final String COLUMN_URSULA_INUSE = "ursula";
        public static final String COLUMN_FINN_INUSE = "finn";
        public static final String COLUMN_MATEO_INUSE = "mateo";
        public static final String COLUMN_CALVIN_INUSE = "calvin";
        public static final String COLUMN_ROUGAROU_STATUS = "rougarou_status";
        public static final String COLUMN_STRANGE_SOLUTION = "strange_solution";
        public static final String COLUMN_ARCHAIC_GLYPHS = "archaic_glyphs";
        public static final String COLUMN_CHARONS_OBOL = "charons_obol";
        public static final String COLUMN_ANCIENT_STONE = "ancient_stone";
        public static final String COLUMN_DOOMED = "doomed";
        public static final String COLUMN_CARNEVALE_STATUS = "carnevale_status";
        public static final String COLUMN_CARNEVALE_REWARDS = "carnevale_rewards";
    }

    public static class InvestigatorEntry implements BaseColumns{
        public static final String TABLE_NAME = "investigators";
        static final String _ID = BaseColumns._ID;
        public static final String PARENT_ID = "parent_id";
        public static final String INVESTIGATOR_ID = "investigator_id";
        public static final String COLUMN_INVESTIGATOR_NAME = "name";
        public static final String COLUMN_INVESTIGATOR_STATUS = "status";
        public static final String COLUMN_INVESTIGATOR_DAMAGE = "damage";
        public static final String COLUMN_INVESTIGATOR_HORROR = "horror";
        public static final String COLUMN_INVESTIGATOR_TOTAL_XP = "total_xp";
        public static final String COLUMN_INVESTIGATOR_AVAILABLE_XP = "xp";
        public static final String COLUMN_INVESTIGATOR_SPENT_XP = "spent_xp";
        public static final String COLUMN_INVESTIGATOR_PLAYER = "player";
        public static final String COLUMN_INVESTIGATOR_DECKNAME = "deckname";
        public static final String COLUMN_INVESTIGATOR_DECKLIST = "decklist";
        public static final String COLUMN_INVESTIGATOR_PROVISIONS = "provisions";
        public static final String COLUMN_INVESTIGATOR_MEDICINE = "medicine";
        public static final String COLUMN_INVESTIGATOR_SUPPLIES = "supplies";
        public static final String COLUMN_INVESTIGATOR_RESUPPLIES_ONE = "resupplies_one";
    }

    public static class NightEntry implements BaseColumns{
        public static final String TABLE_NAME = "night";
        static final String _ID = BaseColumns._ID;
        public static final String PARENT_ID = "parent_id";
        public static final String COLUMN_HOUSE_STANDING = "house_standing";
        public static final String COLUMN_GHOUL_PRIEST = "ghoul_priest";
        public static final String COLUMN_LITA_STATUS = "lita_status";
        public static final String COLUMN_MIDNIGHT_STATUS = "midnight_status";
        public static final String COLUMN_CULTISTS_INTERROGATED = "cultists_interrogated";
        public static final String COLUMN_DREW_INTERROGATED = "drew_interrogated";
        public static final String COLUMN_PETER_INTERROGATED = "peter_interrogated";
        public static final String COLUMN_HERMAN_INTERROGATED = "herman_interrogated";
        public static final String COLUMN_VICTORIA_INTERROGATED = "victoria_interrogated";
        public static final String COLUMN_RUTH_INTERROGATED = "ruth_interrogated";
        public static final String COLUMN_MASKED_INTERROGATED = "masked_interrogated";
        public static final String COLUMN_UMORDHOTH_STATUS = "umordhoth_status";
    }

    public static class DunwichEntry implements BaseColumns{
        public static final String TABLE_NAME = "dunwich";
        static final String _ID = BaseColumns._ID;
        public static final String PARENT_ID = "parent_id";
        public static final String COLUMN_FIRST_SCENARIO = "first_scenario";
        public static final String COLUMN_INVESTIGATORS_UNCONSCIOUS = "investigators_unconscious";
        public static final String COLUMN_HENRY_ARMITAGE = "henry_armitage";
        public static final String COLUMN_WARREN_RICE = "warren_rice";
        public static final String COLUMN_STUDENTS = "students";
        public static final String COLUMN_OBANNION_GANG = "obannion_gang";
        public static final String COLUMN_FRANCIS_MORGAN = "francis_morgan";
        public static final String COLUMN_INVESTIGATORS_CHEATED = "investigators_cheated";
        public static final String COLUMN_NECRONOMICON = "necronomicon";
        public static final String COLUMN_ADAM_LYNCH_HAROLD_WALSTED = "adam_lynch_harold_walsted";
        public static final String COLUMN_DELAYED = "delayed";
        public static final String COLUMN_SILAS_BISHOP = "silas_bishop";
        public static final String COLUMN_ZEBULON_WHATELEY = "zebulon_whateley";
        public static final String COLUMN_EARL_SAWYER = "earl_sawyer";
        public static final String COLUMN_ALLY_SACRIFICED = "ally_sacrificed";
        public static final String COLUMN_TOWNSFOLK = "townsfolk";
        public static final String COLUMN_BROOD_ESCAPED = "brood_escaped";
        public static final String COLUMN_INVESTIGATORS_GATE = "investigators_gate";
        public static final String COLUMN_YOG_SOTHOTH = "yog_sothoth";
    }

    public static class CarcosaEntry implements BaseColumns{
        public static final String TABLE_NAME = "carcosa";
        static final String _ID = BaseColumns._ID;
        public static final String PARENT_ID = "parent_id";
        public static final String COLUMN_DOUBT = "doubt";
        public static final String COLUMN_CONVICTION = "conviction";
        public static final String COLUMN_CHASING_STRANGER = "chasing_stranger";
        public static final String COLUMN_STRANGER = "stranger";
        public static final String COLUMN_POLICE = "police";
        public static final String COLUMN_THEATRE = "theatre";
        public static final String COLUMN_CONSTANCE = "constance";
        public static final String COLUMN_JORDAN = "jordan";
        public static final String COLUMN_ISHIMARU = "ishimaru";
        public static final String COLUMN_SEBASTIEN = "sebastien";
        public static final String COLUMN_ASHLEIGH = "ashleigh";
        public static final String COLUMN_PARTY = "party";
        public static final String COLUMN_ONYX = "onyx";
        public static final String COLUMN_ASYLUM = "asylum";
        public static final String COLUMN_DANIEL = "daniel";
        public static final String COLUMN_DANIELS_WARNING = "daniels_warning";
        public static final String COLUMN_DREAMS = "dreams_action";
        public static final String COLUMN_NIGEL = "nigel";
        public static final String COLUMN_INV_ONE_READ_ACT = "inv_one_read_act";
        public static final String COLUMN_INV_TWO_READ_ACT = "inv_two_read_act";
        public static final String COLUMN_INV_THREE_READ_ACT = "inv_three_read_act";
        public static final String COLUMN_INV_FOUR_READ_ACT = "inv_four_read_act";
        public static final String COLUMN_PATH = "path";
        public static final String COLUMN_HASTUR = "hastur";
        public static final String COLUMN_INV_ONE_POSSESSED = "inv_one_possessed";
        public static final String COLUMN_INV_TWO_POSSESSED = "inv_two_possessed";
        public static final String COLUMN_INV_THREE_POSSESSED = "inv_three_possessed";
        public static final String COLUMN_INV_FOUR_POSSESSED = "inv_four_possessed";
    }

    public static class ForgottenEntry implements BaseColumns{
        public static final String TABLE_NAME = "forgotten";
        static final String _ID = BaseColumns._ID;
        public static final String PARENT_ID = "parent_id";
        public static final String COLUMN_YIGS_FURY = "yigs_fury";
        public static final String COLUMN_RUINS = "ruins";
        public static final String COLUMN_ICHTACA = "ichtaca";
        public static final String COLUMN_ALEJANDRO = "alejandro";
        public static final String COLUMN_LOW_RATIONS = "low_rations";
        public static final String COLUMN_RELIC = "relic";
        public static final String COLUMN_HARBINGER = "harbinger";
        public static final String COLUMN_EZTLI = "eztli";
        public static final String COLUMN_CUSTODY = "custody";
        public static final String COLUMN_ICHTACAS_TALE = "ichtacas_tale";
        public static final String COLUMN_MISSING_RELIC = "missing_relic";
        public static final String COLUMN_MISSING_ALEJANDRO = "missing_alejandro";
        public static final String COLUMN_MISSING_ICHTACA = "missing_ichtaca";
        public static final String COLUMN_GASOLINE_USED = "gasoline_used";
        public static final String COLUMN_PATHS_KNOWN = "paths_known";
        public static final String COLUMN_ICHTACA_CONFIDENCE = "ichtaca_confidence";
    }

    public static class ChaosBagEntry implements BaseColumns{
        public static final String TABLE_NAME = "chaos_bag";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_CHAOS_BAG_NAME = "name";
        public static final String COLUMN_CAMPAIGN_TOKENS = "campaign_tokens";
        public static final String COLUMN_ONE = "plus_one";
        public static final String COLUMN_TWO = "zero";
        public static final String COLUMN_THREE = "minus_one";
        public static final String COLUMN_FOUR = "minus_two";
        public static final String COLUMN_FIVE = "minus_three";
        public static final String COLUMN_SIX = "minus_four";
        public static final String COLUMN_SEVEN = "minus_five";
        public static final String COLUMN_EIGHT = "minus_six";
        public static final String COLUMN_NINE = "minus_seven";
        public static final String COLUMN_TEN = "minus_eight";
        public static final String COLUMN_ELEVEN = "skull";
        public static final String COLUMN_TWELVE = "cultist";
        public static final String COLUMN_THIRTEEN = "tablet";
        public static final String COLUMN_FOURTEEN = "elder_thing";
        public static final String COLUMN_FIFTEEN = "elder_sign";
        public static final String COLUMN_SIXTEEN = "tentacles";
    }
}
