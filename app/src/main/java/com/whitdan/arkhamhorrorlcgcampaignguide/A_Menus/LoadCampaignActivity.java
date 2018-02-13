package com.whitdan.arkhamhorrorlcgcampaignguide.A_Menus;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.whitdan.arkhamhorrorlcgcampaignguide.C_Scenario.ScenarioInterludeActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.C_Scenario.ScenarioMainActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.D_Misc.CampaignFinishedActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.R;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.ArkhamContract;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.ArkhamContract.CampaignEntry;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.ArkhamContract.InvestigatorEntry;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.ArkhamContract.NightEntry;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.ArkhamDbHelper;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.GlobalVariables;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.Investigator;

/*
    LoadCampaignActivity - Activity displaying a list of all saved campaigns, on clicking one of them it loads the
    campaign, long clicking allows the deletion of the campaign
 */

public class LoadCampaignActivity extends AppCompatActivity {

    private static GlobalVariables globalVariables;
    private static CampaignsListAdapter campaignsListAdapter;
    Cursor campaigns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_load_campaign);
        globalVariables = (GlobalVariables) this.getApplication();

        // Set font to heading
        Typeface teutonic = Typeface.createFromAsset(getAssets(), "fonts/teutonic.ttf");
        TextView saved = findViewById(R.id.saved_campaigns);
        saved.setTypeface(teutonic);

        // Create a new dbHelper and get access to the SQLite Database
        ArkhamDbHelper dbHelper = new ArkhamDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Get a cursor from the database of all saved campaigns (and all columns of that campaign)
        campaigns = db.rawQuery("SELECT  * FROM " + ArkhamContract.CampaignEntry.TABLE_NAME, null);
        // Find saved campaigns ListView
        ListView campaignItems = findViewById(R.id.saved_campaigns_list);
        // Setup and attach cursor adapter to the list to display all saved campaigns
        campaignsListAdapter = new CampaignsListAdapter(this, campaigns);
        campaignItems.setAdapter(campaignsListAdapter);

        // Setup and attach onItemClickListener to the ListView to allow resuming a campaign on click
        CampaignsOnClickListener campaignsOnClickListener = new CampaignsOnClickListener(this);
        campaignItems.setOnItemClickListener(campaignsOnClickListener);

        // Setup and attach onItemLongClickListener to the ListView to allow deleting campaigns on long click
        CampaignsOnLongClickListener campaignsOnLongClickListener = new CampaignsOnLongClickListener();
        campaignItems.setOnItemLongClickListener(campaignsOnLongClickListener);

        // Back button to finish the activity
        Button backButton = findViewById(R.id.back_button);
        backButton.setTypeface(teutonic);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    // CursorAdapter to show the list of saved campaigns
    private class CampaignsListAdapter extends CursorAdapter {

        CampaignsListAdapter(Context context, Cursor cursor) {
            super(context, cursor, 0);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
            return LayoutInflater.from(context).inflate(R.layout.a_item_campaign, viewGroup, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            // Find fields to populate in inflated template and set the right fonts
            Typeface teutonic = Typeface.createFromAsset(getAssets(), "fonts/teutonic.ttf");
            Typeface arnoprobold = Typeface.createFromAsset(getAssets(), "fonts/arnoprobold.otf");
            Typeface arnopro = Typeface.createFromAsset(getAssets(), "fonts/arnopro.otf");
            TextView campaignNameView = view.findViewById(R.id.campaign_name);
            campaignNameView.setTypeface(teutonic);
            TextView currentCampaignView = view.findViewById(R.id.current_campaign);
            TextView currentScenarioView = view.findViewById(R.id.current_scenario);
            currentCampaignView.setTypeface(arnoprobold);
            currentScenarioView.setTypeface(arnoprobold);
            TextView currentCampaignName = view.findViewById(R.id.current_campaign_name);
            TextView currentScenarioName = view.findViewById(R.id.current_scenario_name);
            currentCampaignName.setTypeface(arnopro);
            currentScenarioName.setTypeface(arnopro);

            // Extract properties from cursor
            String campaignName = cursor.getString(cursor.getColumnIndexOrThrow(ArkhamContract.CampaignEntry
                    .COLUMN_CAMPAIGN_NAME));
            int currentCampaign = cursor.getInt(cursor.getColumnIndexOrThrow(ArkhamContract.CampaignEntry
                    .COLUMN_CURRENT_CAMPAIGN));
            int currentScenario = cursor.getInt(cursor.getColumnIndexOrThrow(ArkhamContract.CampaignEntry
                    .COLUMN_CURRENT_SCENARIO));

            // Populate fields with extracted properties
            campaignNameView.setText(campaignName);
            switch (currentCampaign) {
                case 1:
                    currentCampaignName.setText(R.string.night_campaign);
                    switch (currentScenario) {
                        case 1:
                            currentScenarioName.setText(R.string.night_scenario_one);
                            break;
                        case 2:
                            currentScenarioName.setText(R.string.night_scenario_two);
                            break;
                        case 3:
                            currentScenarioName.setText(R.string.night_scenario_three);
                            break;
                        case 4:
                            currentScenarioName.setText(R.string.campaign_completed);
                            break;
                    }
                    break;
                case 2:
                    currentCampaignName.setText(R.string.dunwich_campaign);
                    switch (currentScenario) {
                        case 1:
                            currentScenarioName.setText(R.string.dunwich_scenario_one);
                            break;
                        case 2:
                            currentScenarioName.setText(R.string.dunwich_scenario_two);
                            break;
                        case 3:
                            currentScenarioName.setText(R.string.dunwich_interlude_one);
                            break;
                        case 4:
                            currentScenarioName.setText(R.string.dunwich_scenario_three);
                            break;
                        case 5:
                            currentScenarioName.setText(R.string.dunwich_scenario_four);
                            break;
                        case 6:
                            currentScenarioName.setText(R.string.dunwich_scenario_five);
                            break;
                        case 7:
                            currentScenarioName.setText(R.string.dunwich_interlude_two);
                            break;
                        case 8:
                            currentScenarioName.setText(R.string.dunwich_scenario_six);
                            break;
                        case 9:
                            currentScenarioName.setText(R.string.dunwich_scenario_seven);
                            break;
                        case 10:
                            currentScenarioName.setText(R.string.dunwich_scenario_eight);
                            break;
                        case 11:
                            currentScenarioName.setText(R.string.dunwich_epilogue);
                            break;
                        case 12:
                            currentScenarioName.setText(R.string.campaign_completed);
                            break;
                    }
                    break;
                case 3:
                    currentCampaignName.setText(R.string.carcosa_campaign);
                    switch (currentScenario) {
                        case 0:
                            currentScenarioName.setText(R.string.carcosa_interlude_zero);
                            break;
                        case 1:
                            currentScenarioName.setText(R.string.carcosa_scenario_one);
                            break;
                        case 2:
                            currentScenarioName.setText(R.string.carcosa_scenario_two);
                            break;
                        case 3:
                            currentScenarioName.setText(R.string.carcosa_interlude_one);
                            break;
                        case 4:
                            currentScenarioName.setText(R.string.carcosa_scenario_three);
                            break;
                        case 5:
                            currentScenarioName.setText(R.string.carcosa_scenario_four);
                            break;
                        case 6:
                            currentScenarioName.setText(R.string.carcosa_interlude_two);
                            break;
                        case 7:
                            currentScenarioName.setText(R.string.carcosa_scenario_five);
                            break;
                        case 8:
                            currentScenarioName.setText(R.string.carcosa_scenario_six);
                            break;
                        case 9:
                            currentScenarioName.setText(R.string.carcosa_scenario_seven);
                            break;
                        case 10:
                            currentScenarioName.setText(R.string.carcosa_scenario_eight);
                            break;
                        case 11:
                            currentScenarioName.setText(R.string.carcosa_epilogue);
                            break;
                        case 12:
                            currentScenarioName.setText(R.string.campaign_completed);
                            break;
                    }
                    break;
            }
        }
    }

    // Loads campaign on clicking it
    private class CampaignsOnClickListener implements AdapterView.OnItemClickListener {

        Context context;

        private CampaignsOnClickListener(Context con) {
            context = con;
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int a, long id) {
            // Get access to readable SQL database
            ArkhamDbHelper dbHelper = new ArkhamDbHelper(context);
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            boolean corrupt = false;

            // Set selectionArgs as the campaign id of the campaign clicked on
            String[] selectionArgs = {Long.toString(id)};

            // Set the GlobalVariable campaign variables to the relevant values in the SQL database
            String[] campaignProjection = {
                    ArkhamContract.CampaignEntry._ID,
                    CampaignEntry.COLUMN_CAMPAIGN_VERSION,
                    CampaignEntry.COLUMN_CHAOS_BAG,
                    CampaignEntry.COLUMN_CURRENT_CAMPAIGN,
                    CampaignEntry.COLUMN_CURRENT_SCENARIO,
                    CampaignEntry.COLUMN_DIFFICULTY,
                    CampaignEntry.COLUMN_NIGHT_COMPLETED,
                    CampaignEntry.COLUMN_DUNWICH_COMPLETED,
                    CampaignEntry.COLUMN_ROLAND_INUSE,
                    CampaignEntry.COLUMN_DAISY_INUSE,
                    CampaignEntry.COLUMN_SKIDS_INUSE,
                    CampaignEntry.COLUMN_AGNES_INUSE,
                    CampaignEntry.COLUMN_WENDY_INUSE,
                    CampaignEntry.COLUMN_ZOEY_INUSE,
                    CampaignEntry.COLUMN_REX_INUSE,
                    CampaignEntry.COLUMN_JENNY_INUSE,
                    CampaignEntry.COLUMN_JIM_INUSE,
                    CampaignEntry.COLUMN_PETE_INUSE,
                    CampaignEntry.COLUMN_MARK_INUSE,
                    CampaignEntry.COLUMN_MINH_INUSE,
                    CampaignEntry.COLUMN_SEFINA_INUSE,
                    CampaignEntry.COLUMN_AKACHI_INUSE,
                    CampaignEntry.COLUMN_WILLIAM_INUSE,
                    CampaignEntry.COLUMN_LOLA_INUSE,
                    CampaignEntry.COLUMN_MARIE_INUSE,
                    CampaignEntry.COLUMN_NORMAN_INUSE,
                    CampaignEntry.COLUMN_ROUGAROU_STATUS,
                    CampaignEntry.COLUMN_STRANGE_SOLUTION,
                    CampaignEntry.COLUMN_ARCHAIC_GLYPHS,
                    CampaignEntry.COLUMN_CARNEVALE_STATUS,
                    CampaignEntry.COLUMN_CARNEVALE_REWARDS
            };
            String campaignSelection = CampaignEntry._ID + " = ?";
            Cursor campaignCursor = db.query(
                    CampaignEntry.TABLE_NAME,  // The table to query
                    campaignProjection,                       // The columns to return
                    campaignSelection,                        // The columns for the WHERE clause
                    selectionArgs,                            // The values for the WHERE clause
                    null,                                     // don't group the rows
                    null,                                     // don't filter by row groups
                    null                                      // The sort order
            );
            while (campaignCursor.moveToNext()) {
                globalVariables.CampaignID = campaignCursor.getLong(campaignCursor
                        .getColumnIndexOrThrow(CampaignEntry._ID));
                globalVariables.ChaosBagID = campaignCursor.getInt(campaignCursor.getColumnIndexOrThrow(CampaignEntry
                        .COLUMN_CHAOS_BAG));
                globalVariables.CampaignVersion = campaignCursor.getInt(campaignCursor.getColumnIndexOrThrow
                        (CampaignEntry.COLUMN_CAMPAIGN_VERSION));
                globalVariables.CurrentCampaign = (campaignCursor.getInt(campaignCursor
                        .getColumnIndexOrThrow(CampaignEntry.COLUMN_CURRENT_CAMPAIGN)));
                globalVariables.CurrentScenario = (campaignCursor.getInt(campaignCursor
                        .getColumnIndexOrThrow(CampaignEntry.COLUMN_CURRENT_SCENARIO)));
                globalVariables.CurrentDifficulty = (campaignCursor.getInt(campaignCursor.getColumnIndexOrThrow
                        (CampaignEntry.COLUMN_DIFFICULTY)));
                globalVariables.NightCompleted = (campaignCursor.getInt(campaignCursor.getColumnIndexOrThrow
                        (CampaignEntry.COLUMN_NIGHT_COMPLETED)));
                globalVariables.DunwichCompleted = (campaignCursor.getInt(campaignCursor.getColumnIndexOrThrow
                        (CampaignEntry.COLUMN_DUNWICH_COMPLETED)));
                globalVariables.InvestigatorsInUse[Investigator.ROLAND_BANKS] = campaignCursor.getInt(campaignCursor
                        .getColumnIndexOrThrow(CampaignEntry.COLUMN_ROLAND_INUSE));
                globalVariables.InvestigatorsInUse[Investigator.DAISY_WALKER] = campaignCursor.getInt(campaignCursor
                        .getColumnIndexOrThrow(CampaignEntry.COLUMN_DAISY_INUSE));
                globalVariables.InvestigatorsInUse[Investigator.AGNES_BAKER] = campaignCursor.getInt(campaignCursor
                        .getColumnIndexOrThrow(CampaignEntry.COLUMN_AGNES_INUSE));
                globalVariables.InvestigatorsInUse[Investigator.SKIDS_OTOOLE] = campaignCursor.getInt(campaignCursor
                        .getColumnIndexOrThrow(CampaignEntry.COLUMN_SKIDS_INUSE));
                globalVariables.InvestigatorsInUse[Investigator.WENDY_ADAMS] = campaignCursor.getInt(campaignCursor
                        .getColumnIndexOrThrow(CampaignEntry.COLUMN_WENDY_INUSE));
                globalVariables.InvestigatorsInUse[Investigator.ZOEY_SAMARAS] = campaignCursor.getInt(campaignCursor
                        .getColumnIndexOrThrow(CampaignEntry.COLUMN_ZOEY_INUSE));
                globalVariables.InvestigatorsInUse[Investigator.REX_MURPHY] = campaignCursor.getInt(campaignCursor
                        .getColumnIndexOrThrow(CampaignEntry.COLUMN_REX_INUSE));
                globalVariables.InvestigatorsInUse[Investigator.JENNY_BARNES] = campaignCursor.getInt(campaignCursor
                        .getColumnIndexOrThrow(CampaignEntry.COLUMN_JENNY_INUSE));
                globalVariables.InvestigatorsInUse[Investigator.JIM_CULVER] = campaignCursor.getInt(campaignCursor
                        .getColumnIndexOrThrow(CampaignEntry.COLUMN_JIM_INUSE));
                globalVariables.InvestigatorsInUse[Investigator.ASHCAN_PETE] = campaignCursor.getInt(campaignCursor
                        .getColumnIndexOrThrow(CampaignEntry.COLUMN_PETE_INUSE));
                globalVariables.InvestigatorsInUse[Investigator.MARK_HARRIGAN] = campaignCursor.getInt(campaignCursor
                        .getColumnIndexOrThrow(CampaignEntry.COLUMN_MARK_INUSE));
                globalVariables.InvestigatorsInUse[Investigator.MINH_THI_PHAN] = campaignCursor.getInt(campaignCursor
                        .getColumnIndexOrThrow(CampaignEntry.COLUMN_MINH_INUSE));
                globalVariables.InvestigatorsInUse[Investigator.SEFINA_ROUSSEAU] = campaignCursor.getInt(campaignCursor
                        .getColumnIndexOrThrow(CampaignEntry.COLUMN_SEFINA_INUSE));
                globalVariables.InvestigatorsInUse[Investigator.AKACHI_ONYELE] = campaignCursor.getInt(campaignCursor
                        .getColumnIndexOrThrow(CampaignEntry.COLUMN_AKACHI_INUSE));
                globalVariables.InvestigatorsInUse[Investigator.WILLIAM_YORICK] = campaignCursor.getInt(campaignCursor
                        .getColumnIndexOrThrow(CampaignEntry.COLUMN_WILLIAM_INUSE));
                globalVariables.InvestigatorsInUse[Investigator.LOLA_HAYES] = campaignCursor.getInt(campaignCursor
                        .getColumnIndexOrThrow(CampaignEntry.COLUMN_LOLA_INUSE));
                globalVariables.InvestigatorsInUse[Investigator.MARIE_LAMBEAU] = campaignCursor.getInt(campaignCursor
                        .getColumnIndexOrThrow(CampaignEntry.COLUMN_MARIE_INUSE));
                globalVariables.InvestigatorsInUse[Investigator.NORMAN_WITHERS] = campaignCursor.getInt
                        (campaignCursor.getColumnIndexOrThrow(CampaignEntry.COLUMN_NORMAN_INUSE));
                globalVariables.Rougarou = (campaignCursor.getInt(campaignCursor.getColumnIndexOrThrow
                        (CampaignEntry.COLUMN_ROUGAROU_STATUS)));
                globalVariables.StrangeSolution = (campaignCursor.getInt(campaignCursor.getColumnIndexOrThrow
                        (CampaignEntry.COLUMN_STRANGE_SOLUTION)));
                globalVariables.ArchaicGlyphs = (campaignCursor.getInt(campaignCursor.getColumnIndexOrThrow
                        (CampaignEntry.COLUMN_ARCHAIC_GLYPHS)));
                globalVariables.Carnevale = (campaignCursor.getInt(campaignCursor.getColumnIndexOrThrow
                        (CampaignEntry.COLUMN_CARNEVALE_STATUS)));
                globalVariables.CarnevaleReward = (campaignCursor.getInt(campaignCursor.getColumnIndexOrThrow
                        (CampaignEntry.COLUMN_CARNEVALE_REWARDS)));
            }
            campaignCursor.close();

            // Set the relevant investigator variables from the SQL database
            String[] investigatorProjection = {
                    InvestigatorEntry.COLUMN_INVESTIGATOR_NAME,
                    InvestigatorEntry.COLUMN_INVESTIGATOR_STATUS,
                    InvestigatorEntry.COLUMN_INVESTIGATOR_DAMAGE,
                    InvestigatorEntry.COLUMN_INVESTIGATOR_HORROR,
                    InvestigatorEntry.COLUMN_INVESTIGATOR_TOTAL_XP,
                    InvestigatorEntry.COLUMN_INVESTIGATOR_AVAILABLE_XP,
                    InvestigatorEntry.COLUMN_INVESTIGATOR_SPENT_XP,
                    InvestigatorEntry.COLUMN_INVESTIGATOR_PLAYER,
                    InvestigatorEntry.COLUMN_INVESTIGATOR_DECKNAME,
                    InvestigatorEntry.COLUMN_INVESTIGATOR_DECKLIST
            };
            String investigatorSelection = InvestigatorEntry.PARENT_ID + " = ?";
            Cursor investigatorCursor = db.query(
                    InvestigatorEntry.TABLE_NAME,
                    investigatorProjection,
                    investigatorSelection,
                    selectionArgs,
                    null,
                    null,
                    null
            );
            globalVariables.Investigators.clear();
            globalVariables.SavedInvestigators.clear();
            int count = 0;
            for (int i = 0; investigatorCursor.moveToNext(); i++) {
                int status = investigatorCursor.getInt(investigatorCursor.getColumnIndexOrThrow(InvestigatorEntry
                        .COLUMN_INVESTIGATOR_STATUS));
                int name = investigatorCursor.getInt(investigatorCursor.getColumnIndexOrThrow(InvestigatorEntry
                        .COLUMN_INVESTIGATOR_NAME));
                String player = investigatorCursor.getString(investigatorCursor.getColumnIndexOrThrow(InvestigatorEntry
                        .COLUMN_INVESTIGATOR_PLAYER));
                String deckName = investigatorCursor.getString(investigatorCursor.getColumnIndex(InvestigatorEntry
                        .COLUMN_INVESTIGATOR_DECKNAME));
                String deck = investigatorCursor.getString(investigatorCursor.getColumnIndexOrThrow(InvestigatorEntry
                        .COLUMN_INVESTIGATOR_DECKLIST));
                if (status == 1 || status == 2) {
                    globalVariables.Investigators.add(new Investigator(name, player, deckName, deck));
                    globalVariables.Investigators.get(i).Status = (status);
                    globalVariables.Investigators.get(i).Damage = (investigatorCursor.getInt
                            (investigatorCursor.getColumnIndexOrThrow(InvestigatorEntry
                                    .COLUMN_INVESTIGATOR_DAMAGE)));
                    globalVariables.Investigators.get(i).Horror = (investigatorCursor.getInt
                            (investigatorCursor.getColumnIndexOrThrow(InvestigatorEntry
                                    .COLUMN_INVESTIGATOR_HORROR)));
                    globalVariables.Investigators.get(i).TotalXP = investigatorCursor.getInt(investigatorCursor
                            .getColumnIndexOrThrow(InvestigatorEntry.COLUMN_INVESTIGATOR_TOTAL_XP));
                    globalVariables.Investigators.get(i).AvailableXP = (investigatorCursor.getInt
                            (investigatorCursor.getColumnIndexOrThrow(InvestigatorEntry
                                    .COLUMN_INVESTIGATOR_AVAILABLE_XP)));
                    globalVariables.Investigators.get(i).SpentXP = (investigatorCursor.getInt
                            (investigatorCursor.getColumnIndexOrThrow(InvestigatorEntry
                                    .COLUMN_INVESTIGATOR_SPENT_XP)));
                    count++;
                } else if (status == 3) {
                    globalVariables.SavedInvestigators.add(new Investigator(name, player, deckName, deck));
                    globalVariables.SavedInvestigators.get(i - count).Status = (status);
                    globalVariables.SavedInvestigators.get(i - count).Damage = (investigatorCursor.getInt
                            (investigatorCursor.getColumnIndexOrThrow(InvestigatorEntry
                                    .COLUMN_INVESTIGATOR_DAMAGE)));
                    globalVariables.SavedInvestigators.get(i - count).Horror = (investigatorCursor.getInt
                            (investigatorCursor.getColumnIndexOrThrow(InvestigatorEntry
                                    .COLUMN_INVESTIGATOR_HORROR)));
                    globalVariables.SavedInvestigators.get(i - count).AvailableXP = (investigatorCursor.getInt
                            (investigatorCursor.getColumnIndexOrThrow(InvestigatorEntry
                                    .COLUMN_INVESTIGATOR_AVAILABLE_XP)));
                    globalVariables.SavedInvestigators.get(i - count).SpentXP = (investigatorCursor.getInt
                            (investigatorCursor.getColumnIndexOrThrow(InvestigatorEntry
                                    .COLUMN_INVESTIGATOR_SPENT_XP)));
                }
            }
            investigatorCursor.close();

            // Set the relevant Night of the Zealot variables from the SQL database
            if (globalVariables.CurrentCampaign == 1 || globalVariables.NightCompleted == 1) {
                String[] nightProjection = {
                        NightEntry.COLUMN_HOUSE_STANDING,
                        NightEntry.COLUMN_GHOUL_PRIEST,
                        NightEntry.COLUMN_LITA_STATUS,
                        NightEntry.COLUMN_MIDNIGHT_STATUS,
                        NightEntry.COLUMN_CULTISTS_INTERROGATED,
                        NightEntry.COLUMN_DREW_INTERROGATED,
                        NightEntry.COLUMN_PETER_INTERROGATED,
                        NightEntry.COLUMN_HERMAN_INTERROGATED,
                        NightEntry.COLUMN_VICTORIA_INTERROGATED,
                        NightEntry.COLUMN_RUTH_INTERROGATED,
                        NightEntry.COLUMN_MASKED_INTERROGATED,
                        NightEntry.COLUMN_UMORDHOTH_STATUS
                };
                String nightSelection = NightEntry.PARENT_ID + " = ?";
                Cursor nightCursor = db.query(
                        NightEntry.TABLE_NAME,
                        nightProjection,
                        nightSelection,
                        selectionArgs,
                        null,
                        null,
                        null
                );
                while (nightCursor.moveToNext()) {
                    globalVariables.HouseStanding = (nightCursor.getInt(nightCursor.getColumnIndexOrThrow(NightEntry
                            .COLUMN_HOUSE_STANDING)));
                    globalVariables.GhoulPriest = (nightCursor.getInt(nightCursor.getColumnIndexOrThrow(NightEntry
                            .COLUMN_GHOUL_PRIEST)));
                    globalVariables.LitaChantler = (nightCursor.getInt(nightCursor.getColumnIndexOrThrow(NightEntry
                            .COLUMN_LITA_STATUS)));
                    globalVariables.PastMidnight = (nightCursor.getInt(nightCursor.getColumnIndexOrThrow(NightEntry
                            .COLUMN_MIDNIGHT_STATUS)));
                    globalVariables.CultistsInterrogated = (nightCursor.getInt(nightCursor.getColumnIndexOrThrow
                            (NightEntry
                                    .COLUMN_CULTISTS_INTERROGATED)));
                    globalVariables.DrewInterrogated = (nightCursor.getInt(nightCursor.getColumnIndexOrThrow(NightEntry
                            .COLUMN_DREW_INTERROGATED)));
                    globalVariables.PeterInterrogated = (nightCursor.getInt(nightCursor.getColumnIndexOrThrow
                            (NightEntry
                                    .COLUMN_PETER_INTERROGATED)));
                    globalVariables.HermanInterrogated = (nightCursor.getInt(nightCursor.getColumnIndexOrThrow
                            (NightEntry
                            .COLUMN_HERMAN_INTERROGATED)));
                    globalVariables.VictoriaInterrogated = (nightCursor.getInt(nightCursor.getColumnIndexOrThrow
                            (NightEntry
                                    .COLUMN_VICTORIA_INTERROGATED)));
                    globalVariables.RuthInterrogated = (nightCursor.getInt(nightCursor.getColumnIndexOrThrow(NightEntry
                            .COLUMN_RUTH_INTERROGATED)));
                    globalVariables.MaskedInterrogated = (nightCursor.getInt(nightCursor.getColumnIndexOrThrow
                            (NightEntry
                            .COLUMN_MASKED_INTERROGATED)));
                    globalVariables.Umordhoth = (nightCursor.getInt(nightCursor.getColumnIndexOrThrow(NightEntry
                            .COLUMN_UMORDHOTH_STATUS)));
                }
                if (nightCursor.getCount() <= 0) {
                    corrupt = true;
                }
                nightCursor.close();
            }

            // Set the relevant Dunwich variables from the database
            if (globalVariables.CurrentCampaign == 2 || globalVariables.DunwichCompleted == 1) {
                String[] dunwichProjection = {
                        ArkhamContract.DunwichEntry.COLUMN_FIRST_SCENARIO,
                        ArkhamContract.DunwichEntry.COLUMN_INVESTIGATORS_UNCONSCIOUS,
                        ArkhamContract.DunwichEntry.COLUMN_HENRY_ARMITAGE,
                        ArkhamContract.DunwichEntry.COLUMN_WARREN_RICE,
                        ArkhamContract.DunwichEntry.COLUMN_STUDENTS,
                        ArkhamContract.DunwichEntry.COLUMN_OBANNION_GANG,
                        ArkhamContract.DunwichEntry.COLUMN_FRANCIS_MORGAN,
                        ArkhamContract.DunwichEntry.COLUMN_INVESTIGATORS_CHEATED,
                        ArkhamContract.DunwichEntry.COLUMN_NECRONOMICON,
                        ArkhamContract.DunwichEntry.COLUMN_ADAM_LYNCH_HAROLD_WALSTED,
                        ArkhamContract.DunwichEntry.COLUMN_DELAYED,
                        ArkhamContract.DunwichEntry.COLUMN_SILAS_BISHOP,
                        ArkhamContract.DunwichEntry.COLUMN_ZEBULON_WHATELEY,
                        ArkhamContract.DunwichEntry.COLUMN_EARL_SAWYER,
                        ArkhamContract.DunwichEntry.COLUMN_ALLY_SACRIFICED,
                        ArkhamContract.DunwichEntry.COLUMN_TOWNSFOLK,
                        ArkhamContract.DunwichEntry.COLUMN_BROOD_ESCAPED,
                        ArkhamContract.DunwichEntry.COLUMN_INVESTIGATORS_GATE,
                        ArkhamContract.DunwichEntry.COLUMN_YOG_SOTHOTH
                };
                String dunwichSelection = ArkhamContract.DunwichEntry.PARENT_ID + " = ?";
                Cursor dunwichCursor = db.query(
                        ArkhamContract.DunwichEntry.TABLE_NAME,
                        dunwichProjection,
                        dunwichSelection,
                        selectionArgs,
                        null,
                        null,
                        null
                );
                while (dunwichCursor.moveToNext()) {
                    globalVariables.FirstScenario = (dunwichCursor.getInt(dunwichCursor.getColumnIndexOrThrow
                            (ArkhamContract.DunwichEntry.COLUMN_FIRST_SCENARIO)));
                    globalVariables.InvestigatorsUnconscious = (dunwichCursor.getInt(dunwichCursor.getColumnIndexOrThrow
                            (ArkhamContract.DunwichEntry.COLUMN_INVESTIGATORS_UNCONSCIOUS)));
                    globalVariables.HenryArmitage = (dunwichCursor.getInt(dunwichCursor.getColumnIndexOrThrow
                            (ArkhamContract.DunwichEntry.COLUMN_HENRY_ARMITAGE)));
                    globalVariables.WarrenRice = (dunwichCursor.getInt(dunwichCursor.getColumnIndexOrThrow
                            (ArkhamContract.DunwichEntry.COLUMN_WARREN_RICE)));
                    globalVariables.Students = (dunwichCursor.getInt(dunwichCursor.getColumnIndexOrThrow
                            (ArkhamContract.DunwichEntry.COLUMN_STUDENTS)));
                    globalVariables.ObannionGang = (dunwichCursor.getInt(dunwichCursor.getColumnIndexOrThrow
                            (ArkhamContract.DunwichEntry.COLUMN_OBANNION_GANG)));
                    globalVariables.FrancisMorgan = (dunwichCursor.getInt(dunwichCursor.getColumnIndexOrThrow
                            (ArkhamContract.DunwichEntry.COLUMN_FRANCIS_MORGAN)));
                    globalVariables.InvestigatorsCheated = (dunwichCursor.getInt(dunwichCursor.getColumnIndexOrThrow
                            (ArkhamContract.DunwichEntry.COLUMN_INVESTIGATORS_CHEATED)));
                    globalVariables.Necronomicon = (dunwichCursor.getInt(dunwichCursor.getColumnIndexOrThrow
                            (ArkhamContract.DunwichEntry.COLUMN_NECRONOMICON)));
                    globalVariables.AdamLynchHaroldWalsted = (dunwichCursor.getInt(dunwichCursor.getColumnIndexOrThrow
                            (ArkhamContract
                                    .DunwichEntry.COLUMN_ADAM_LYNCH_HAROLD_WALSTED)));
                    globalVariables.InvestigatorsDelayed = (dunwichCursor.getInt(dunwichCursor.getColumnIndex
                            (ArkhamContract
                                    .DunwichEntry.COLUMN_DELAYED)));
                    globalVariables.SilasBishop = (dunwichCursor.getInt(dunwichCursor.getColumnIndexOrThrow
                            (ArkhamContract.DunwichEntry.COLUMN_SILAS_BISHOP)));
                    globalVariables.ZebulonWhateley = (dunwichCursor.getInt(dunwichCursor.getColumnIndexOrThrow
                            (ArkhamContract.DunwichEntry.COLUMN_ZEBULON_WHATELEY)));
                    globalVariables.EarlSawyer = (dunwichCursor.getInt(dunwichCursor.getColumnIndexOrThrow
                            (ArkhamContract
                            .DunwichEntry.COLUMN_EARL_SAWYER)));
                    globalVariables.AllySacrificed = (dunwichCursor.getInt(dunwichCursor.getColumnIndexOrThrow
                            (ArkhamContract.DunwichEntry.COLUMN_ALLY_SACRIFICED)));
                    globalVariables.TownsfolkAction = (dunwichCursor.getInt(dunwichCursor.getColumnIndexOrThrow
                            (ArkhamContract.DunwichEntry.COLUMN_TOWNSFOLK)));
                    globalVariables.BroodsEscaped = (dunwichCursor.getInt(dunwichCursor.getColumnIndexOrThrow
                            (ArkhamContract.DunwichEntry.COLUMN_BROOD_ESCAPED)));
                    globalVariables.InvestigatorsGate = (dunwichCursor.getInt(dunwichCursor.getColumnIndexOrThrow
                            (ArkhamContract.DunwichEntry.COLUMN_INVESTIGATORS_GATE)));
                    globalVariables.YogSothoth = (dunwichCursor.getInt(dunwichCursor.getColumnIndexOrThrow
                            (ArkhamContract
                            .DunwichEntry.COLUMN_YOG_SOTHOTH)));
                }
                if (dunwichCursor.getCount() <= 0) {
                    corrupt = true;
                }
                dunwichCursor.close();
            }

            // Set the relevant Carcosa variables from the database
            if (globalVariables.CurrentCampaign == 3) {
                String[] carcosaProjection = {
                        ArkhamContract.CarcosaEntry.COLUMN_DOUBT,
                        ArkhamContract.CarcosaEntry.COLUMN_CONVICTION,
                        ArkhamContract.CarcosaEntry.COLUMN_CHASING_STRANGER,
                        ArkhamContract.CarcosaEntry.COLUMN_STRANGER,
                        ArkhamContract.CarcosaEntry.COLUMN_POLICE,
                        ArkhamContract.CarcosaEntry.COLUMN_THEATRE,
                        ArkhamContract.CarcosaEntry.COLUMN_CONSTANCE,
                        ArkhamContract.CarcosaEntry.COLUMN_JORDAN,
                        ArkhamContract.CarcosaEntry.COLUMN_ISHIMARU,
                        ArkhamContract.CarcosaEntry.COLUMN_SEBASTIEN,
                        ArkhamContract.CarcosaEntry.COLUMN_ASHLEIGH,
                        ArkhamContract.CarcosaEntry.COLUMN_PARTY,
                        ArkhamContract.CarcosaEntry.COLUMN_ONYX,
                        ArkhamContract.CarcosaEntry.COLUMN_ASYLUM,
                        ArkhamContract.CarcosaEntry.COLUMN_DANIEL,
                        ArkhamContract.CarcosaEntry.COLUMN_DANIELS_WARNING,
                        ArkhamContract.CarcosaEntry.COLUMN_DREAMS,
                        ArkhamContract.CarcosaEntry.COLUMN_NIGEL,
                        ArkhamContract.CarcosaEntry.COLUMN_INV_ONE_READ_ACT,
                        ArkhamContract.CarcosaEntry.COLUMN_INV_TWO_READ_ACT,
                        ArkhamContract.CarcosaEntry.COLUMN_INV_THREE_READ_ACT,
                        ArkhamContract.CarcosaEntry.COLUMN_INV_FOUR_READ_ACT
                };
                String carcosaSelection = ArkhamContract.CarcosaEntry.PARENT_ID + " = ?";
                Cursor carcosaCursor = db.query(
                        ArkhamContract.CarcosaEntry.TABLE_NAME,
                        carcosaProjection,
                        carcosaSelection,
                        selectionArgs,
                        null,
                        null,
                        null
                );
                while (carcosaCursor.moveToNext()) {
                    globalVariables.Doubt = (carcosaCursor.getInt(carcosaCursor.getColumnIndexOrThrow
                            (ArkhamContract.CarcosaEntry.COLUMN_DOUBT)));
                    globalVariables.Conviction = (carcosaCursor.getInt(carcosaCursor.getColumnIndexOrThrow
                            (ArkhamContract.CarcosaEntry.COLUMN_CONVICTION)));
                    globalVariables.ChasingStranger = (carcosaCursor.getInt(carcosaCursor.getColumnIndexOrThrow
                            (ArkhamContract.CarcosaEntry.COLUMN_CHASING_STRANGER)));
                    globalVariables.Stranger = (carcosaCursor.getInt(carcosaCursor.getColumnIndexOrThrow
                            (ArkhamContract.CarcosaEntry.COLUMN_STRANGER)));
                    globalVariables.Police = (carcosaCursor.getInt(carcosaCursor.getColumnIndexOrThrow
                            (ArkhamContract.CarcosaEntry.COLUMN_POLICE)));
                    globalVariables.Theatre = (carcosaCursor.getInt(carcosaCursor.getColumnIndexOrThrow
                            (ArkhamContract.CarcosaEntry.COLUMN_THEATRE)));
                    globalVariables.Constance = (carcosaCursor.getInt(carcosaCursor.getColumnIndexOrThrow
                            (ArkhamContract.CarcosaEntry.COLUMN_CONSTANCE)));
                    globalVariables.Jordan = (carcosaCursor.getInt(carcosaCursor.getColumnIndexOrThrow
                            (ArkhamContract.CarcosaEntry.COLUMN_JORDAN)));
                    globalVariables.Ishimaru = (carcosaCursor.getInt(carcosaCursor.getColumnIndexOrThrow
                            (ArkhamContract.CarcosaEntry.COLUMN_ISHIMARU)));
                    globalVariables.Sebastien = (carcosaCursor.getInt(carcosaCursor.getColumnIndexOrThrow
                            (ArkhamContract.CarcosaEntry.COLUMN_SEBASTIEN)));
                    globalVariables.Ashleigh = (carcosaCursor.getInt(carcosaCursor.getColumnIndexOrThrow
                            (ArkhamContract.CarcosaEntry.COLUMN_ASHLEIGH)));
                    globalVariables.Party = (carcosaCursor.getInt(carcosaCursor.getColumnIndexOrThrow
                            (ArkhamContract.CarcosaEntry.COLUMN_PARTY)));
                    globalVariables.Onyx = (carcosaCursor.getInt(carcosaCursor.getColumnIndexOrThrow(ArkhamContract
                            .CarcosaEntry.COLUMN_ONYX)));
                    globalVariables.Asylum = (carcosaCursor.getInt(carcosaCursor.getColumnIndexOrThrow
                            (ArkhamContract.CarcosaEntry.COLUMN_ASYLUM)));
                    globalVariables.Daniel = (carcosaCursor.getInt(carcosaCursor.getColumnIndexOrThrow
                            (ArkhamContract.CarcosaEntry.COLUMN_DANIEL)));
                    globalVariables.DanielsWarning = (carcosaCursor.getInt(carcosaCursor.getColumnIndexOrThrow
                            (ArkhamContract
                            .CarcosaEntry.COLUMN_DANIELS_WARNING)));
                    globalVariables.DreamsAction = (carcosaCursor.getInt(carcosaCursor.getColumnIndexOrThrow
                            (ArkhamContract.CarcosaEntry.COLUMN_DREAMS)));
                    globalVariables.Nigel = (carcosaCursor.getInt(carcosaCursor.getColumnIndexOrThrow(ArkhamContract
                            .CarcosaEntry.COLUMN_NIGEL)));
                    globalVariables.InvOneReadAct = (carcosaCursor.getInt(carcosaCursor.getColumnIndexOrThrow
                            (ArkhamContract.CarcosaEntry.COLUMN_INV_ONE_READ_ACT)));
                    globalVariables.InvTwoReadAct = (carcosaCursor.getInt(carcosaCursor.getColumnIndexOrThrow
                            (ArkhamContract.CarcosaEntry.COLUMN_INV_TWO_READ_ACT)));
                    globalVariables.InvThreeReadAct = (carcosaCursor.getInt(carcosaCursor.getColumnIndexOrThrow
                            (ArkhamContract.CarcosaEntry.COLUMN_INV_THREE_READ_ACT)));
                    globalVariables.InvFourReadAct = (carcosaCursor.getInt(carcosaCursor.getColumnIndexOrThrow
                            (ArkhamContract.CarcosaEntry.COLUMN_INV_FOUR_READ_ACT)));
                }
                if (carcosaCursor.getCount() <= 0) {
                    corrupt = true;
                }
                carcosaCursor.close();
            }

            if (corrupt) {
                // Show dialog if campaign is corrupt (due to no underlying SQL table existing)
                CorruptCampaignDialogFragment newFragment = new CorruptCampaignDialogFragment();
                // Pass the position of the item long clicked to the fragment
                Bundle bundle = new Bundle();
                int pos = (int) id;
                bundle.putInt("pos", pos);
                newFragment.setArguments(bundle);
                // Show the dialog fragment
                newFragment.show(getFragmentManager(), "corrupt");
            } else {
                // Go to relevant screen
                Intent intent = new Intent(context, ScenarioMainActivity.class);
                switch (globalVariables.CurrentCampaign) {
                    case 1:
                        if (globalVariables.NightCompleted == 1) {
                            intent = new Intent(context, CampaignFinishedActivity.class);
                        }
                        break;
                    case 2:
                        switch (globalVariables.CurrentScenario) {
                            case 3:
                            case 7:
                            case 11:
                                intent = new Intent(context, ScenarioInterludeActivity.class);
                                break;
                        }
                        if (globalVariables.DunwichCompleted == 1) {
                            intent = new Intent(context, CampaignFinishedActivity.class);
                        }
                        break;
                    case 3:
                        switch (globalVariables.CurrentScenario) {
                            case 0:
                            case 3:
                            case 6:
                                intent = new Intent(context, ScenarioInterludeActivity.class);
                                break;
                        }
                        break;
                }
                context.startActivity(intent);
            }
        }
    }

    // Opens a dialog to delete a campaign when it is long clicked
    private class CampaignsOnLongClickListener implements AdapterView.OnItemLongClickListener {

        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
            // Create new dialog fragment
            DeleteCampaignDialogFragment newFragment = new DeleteCampaignDialogFragment();
            // Pass the position of the item long clicked to the fragment
            Bundle bundle = new Bundle();
            int pos = (int) id;
            bundle.putInt("pos", pos);
            newFragment.setArguments(bundle);
            // Show the dialog fragment
            newFragment.show(getFragmentManager(), "delete");
            return true;
        }
    }

    // Dialog to confirm the deletion of a campaign
    public static class DeleteCampaignDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Get access to a writable SQLite database
            ArkhamDbHelper dbHelper = new ArkhamDbHelper(getActivity());
            final SQLiteDatabase db = dbHelper.getWritableDatabase();

            // Get the position of the item which was long clicked from the bundle
            Bundle bundle = getArguments();
            final long position = bundle.getInt("pos");

            // Set selectionArgs as the _ID of the campaign clicked on
            final String[] selectionArgs = {Long.toString(position)};

            // Set the GlobalVariable campaign variables to the relevant values in the SQL database
            String[] campaignProjection = {
                    ArkhamContract.CampaignEntry._ID,
                    CampaignEntry.COLUMN_CAMPAIGN_NAME
            };
            String campaignSelection = CampaignEntry._ID + " = ?";
            Cursor cursor = db.query(
                    CampaignEntry.TABLE_NAME,  // The table to query
                    campaignProjection,                       // The columns to return
                    campaignSelection,                        // The columns for the WHERE clause
                    selectionArgs,                            // The values for the WHERE clause
                    null,                                     // don't group the rows
                    null,                                     // don't filter by row groups
                    null                                      // The sort order
            );
            String campaignName = "";
            while (cursor.moveToNext()) {
                campaignName = cursor.getString(cursor.getColumnIndexOrThrow(ArkhamContract.CampaignEntry
                        .COLUMN_CAMPAIGN_NAME));
            }
            cursor.close();

            // Get the layout inflater and inflate the view
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            View v = View.inflate(getActivity(), R.layout.a_dialog_delete_campaign, null);

            // Get the relevant views and set the right fonts
            Typeface arnopro = Typeface.createFromAsset(getActivity().getAssets(), "fonts/arnopro.otf");
            Typeface arnoprobold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/arnoprobold.otf");
            Typeface teutonic = Typeface.createFromAsset(getActivity().getAssets(), "fonts/teutonic.ttf");
            Button cancelButton = v.findViewById(R.id.cancel_button);
            Button okayButton = v.findViewById(R.id.okay_button);
            cancelButton.setTypeface(teutonic);
            okayButton.setTypeface(teutonic);
            TextView delete = v.findViewById(R.id.delete_campaign);
            TextView confirm = v.findViewById(R.id.confirm_delete_campaign);
            TextView campaign = v.findViewById(R.id.campaign_name);
            delete.setTypeface(teutonic);
            confirm.setTypeface(arnopro);
            campaign.setTypeface(arnoprobold);
            campaign.setText(campaignName);

            // Delete the campaign on confirm
            okayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // Find all of the relevant rows of the database tables for the campaign clicked on
                    String campaignSelection = ArkhamContract.CampaignEntry._ID + " = ?";
                    String investigatorSelection = ArkhamContract.InvestigatorEntry.PARENT_ID + " = ?";
                    String nightSelection = ArkhamContract.NightEntry.PARENT_ID + " = ?";
                    String dunwichSelection = ArkhamContract.DunwichEntry.PARENT_ID + " = ?";
                    String carcosaSelection = ArkhamContract.CarcosaEntry.PARENT_ID + " = ?";

                    // Delete the rows
                    db.delete(ArkhamContract.CampaignEntry.TABLE_NAME, campaignSelection, selectionArgs);
                    db.delete(ArkhamContract.InvestigatorEntry.TABLE_NAME, investigatorSelection, selectionArgs);
                    db.delete(ArkhamContract.NightEntry.TABLE_NAME, nightSelection, selectionArgs);
                    db.delete(ArkhamContract.DunwichEntry.TABLE_NAME, dunwichSelection, selectionArgs);
                    db.delete(ArkhamContract.CarcosaEntry.TABLE_NAME, carcosaSelection, selectionArgs);

                    // Refresh the ListView by re-querying the cursor
                    Cursor campaignsCursor = db.rawQuery("SELECT  * FROM " + ArkhamContract.CampaignEntry.TABLE_NAME,
                            null);
                    campaignsListAdapter.swapCursor(campaignsCursor);
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

    // Dialog to confirm the deletion of a corrupt campaign
    public static class CorruptCampaignDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Get access to a writable SQLite database
            ArkhamDbHelper dbHelper = new ArkhamDbHelper(getActivity());
            final SQLiteDatabase db = dbHelper.getWritableDatabase();

            // Get the position of the item which was long clicked from the bundle
            Bundle bundle = getArguments();
            final long position = bundle.getInt("pos");

            // Set selectionArgs as the _ID of the campaign clicked on
            final String[] selectionArgs = {Long.toString(position)};

            // Set the GlobalVariable campaign variables to the relevant values in the SQL database
            String[] campaignProjection = {
                    ArkhamContract.CampaignEntry._ID,
                    CampaignEntry.COLUMN_CAMPAIGN_NAME
            };
            String campaignSelection = CampaignEntry._ID + " = ?";
            Cursor cursor = db.query(
                    CampaignEntry.TABLE_NAME,  // The table to query
                    campaignProjection,                       // The columns to return
                    campaignSelection,                        // The columns for the WHERE clause
                    selectionArgs,                            // The values for the WHERE clause
                    null,                                     // don't group the rows
                    null,                                     // don't filter by row groups
                    null                                      // The sort order
            );
            String campaignName = "";
            while (cursor.moveToNext()) {
                campaignName = cursor.getString(cursor.getColumnIndexOrThrow(ArkhamContract.CampaignEntry
                        .COLUMN_CAMPAIGN_NAME));
            }
            cursor.close();

            // Get the layout inflater and inflate the view
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            View v = View.inflate(getActivity(), R.layout.a_dialog_corrupt_campaign, null);

            // Get the relevant views and set the right fonts
            Typeface arnopro = Typeface.createFromAsset(getActivity().getAssets(), "fonts/arnopro.otf");
            Typeface arnoprobold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/arnoprobold.otf");
            Typeface teutonic = Typeface.createFromAsset(getActivity().getAssets(), "fonts/teutonic.ttf");
            Button cancelButton = v.findViewById(R.id.cancel_button);
            Button okayButton = v.findViewById(R.id.okay_button);
            cancelButton.setTypeface(teutonic);
            okayButton.setTypeface(teutonic);
            TextView delete = v.findViewById(R.id.delete_campaign);
            TextView confirm = v.findViewById(R.id.confirm_delete_campaign);
            TextView campaign = v.findViewById(R.id.campaign_name);
            delete.setTypeface(teutonic);
            confirm.setTypeface(arnopro);
            campaign.setTypeface(arnoprobold);
            campaign.setText(campaignName);

            // Go to the relevant screen
            okayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // Go to relevant screen
                    Intent intent = new Intent(getActivity(), ScenarioMainActivity.class);
                    switch (globalVariables.CurrentCampaign) {
                        case 1:
                            if (globalVariables.NightCompleted == 1) {
                                intent = new Intent(getActivity(), CampaignFinishedActivity.class);
                            }
                            break;
                        case 2:
                            switch (globalVariables.CurrentScenario) {
                                case 3:
                                case 7:
                                case 11:
                                    intent = new Intent(getActivity(), ScenarioInterludeActivity.class);
                                    break;
                            }
                            if (globalVariables.DunwichCompleted == 1) {
                                intent = new Intent(getActivity(), CampaignFinishedActivity.class);
                            }
                            break;
                        case 3:
                            switch (globalVariables.CurrentScenario) {
                                case 0:
                                case 3:
                                case 6:
                                    intent = new Intent(getActivity(), ScenarioInterludeActivity.class);
                                    break;
                            }
                            break;
                    }
                    getActivity().startActivity(intent);
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
