package com.whitdan.arkhamhorrorlcgcampaignguide.D_Misc;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.whitdan.arkhamhorrorlcgcampaignguide.A_Menus.MainMenuActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.R;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.ArkhamContract;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.ArkhamDbHelper;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.GlobalVariables;

import java.util.ArrayList;

import static android.view.View.VISIBLE;

public class LoadChaosBagActivity extends AppCompatActivity {

    private static GlobalVariables globalVariables;
    private static ChaosBagListAdapter chaosBagListAdapter;
    Cursor chaosBags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // If app is reopening after the process is killed, kick back to the main menu (stops the activity from
        // showing up unpopulated)
        if (savedInstanceState != null) {
            Intent intent = new Intent(LoadChaosBagActivity.this, MainMenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.d_activity_load_chaos_bag);
        globalVariables = (GlobalVariables) this.getApplication();

        // Set title and heading
        TextView title = findViewById(R.id.select_chaos_bag);
        Typeface teutonic = Typeface.createFromAsset(getAssets(), "fonts/teutonic.ttf");
        title.setTypeface(teutonic);

        // Create a new dbHelper and get access to the SQLite Database
        ArkhamDbHelper dbHelper = new ArkhamDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Get a cursor from the database of all saved chaos bags (and all columns of that campaign)
        chaosBags = db.rawQuery("SELECT  * FROM " + ArkhamContract.ChaosBagEntry.TABLE_NAME, null);
        // Find saved campaigns ListView
        ListView chaosBagItems = findViewById(R.id.chaos_bag_list);
        // Setup and attach cursor adapter to the list to display all saved chaos bags
        chaosBagListAdapter = new ChaosBagListAdapter(this, chaosBags);
        chaosBagItems.setAdapter(chaosBagListAdapter);

        // Add header view
        LinearLayout default_view = new LinearLayout(this);
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources()
                .getDisplayMetrics());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                .LayoutParams.WRAP_CONTENT);
        default_view.setPadding(0, height, 0, height);
        default_view.setLayoutParams(lp);
        TextView default_chaos_bag = new TextView(this);
        default_chaos_bag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                .LayoutParams.WRAP_CONTENT));
        default_chaos_bag.setText(R.string.default_chaos_bag);
        default_chaos_bag.setAllCaps(true);
        default_chaos_bag.setTypeface(teutonic);
        default_chaos_bag.setTextColor(getResources().getColor(R.color.colorBlack));
        default_chaos_bag.setTextScaleX(1.2f);
        default_chaos_bag.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        default_chaos_bag.setGravity(Gravity.CENTER_VERTICAL);
        default_view.addView(default_chaos_bag);
        if (globalVariables.ChaosBagID == -1) {
            default_view.setBackgroundColor(getResources().getColor(R.color.colorBlackTint));
        } else {
            default_view.setBackgroundColor(getResources().getColor(R.color.colorClear));
        }
        default_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globalVariables.ChaosBagID = -1;

                // Get a writable database
                ArkhamDbHelper dbHelper = new ArkhamDbHelper(LoadChaosBagActivity.this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues campaignValues = new ContentValues();
                campaignValues.put(ArkhamContract.CampaignEntry.COLUMN_CHAOS_BAG, -1);
                String campaignSelection = ArkhamContract.CampaignEntry._ID + " LIKE ?";
                String[] campaignSelectionArgs = {Long.toString(globalVariables.CampaignID)};
                db.update(
                        ArkhamContract.CampaignEntry.TABLE_NAME,
                        campaignValues,
                        campaignSelection,
                        campaignSelectionArgs);

                Intent intent = new Intent(LoadChaosBagActivity.this, ChaosBagActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        chaosBagItems.addHeaderView(default_view);

        // Setup and attach onItemClickListener to the ListView to allow loading a chaos bag on click
        ChaosBagOnClickListener chaosBagOnClickListener = new ChaosBagOnClickListener(this);
        chaosBagItems.setOnItemClickListener(chaosBagOnClickListener);

        // Setup and attach onItemLongClickListener to the ListView to allow deleting a chaos bag on long click
        ChaosBagOnLongClickListener chaosBagOnLongClickListener = new ChaosBagOnLongClickListener();
        chaosBagItems.setOnItemLongClickListener(chaosBagOnLongClickListener);

        // Custom chaos bag button
        Button selectButton = findViewById(R.id.custom_chaos_bag);
        selectButton.setTypeface(teutonic);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoadChaosBagActivity.this, CustomChaosBagActivity.class);
                intent.putExtra("NEW_BAG", true);
                startActivity(intent);
            }
        });

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
    private class ChaosBagListAdapter extends CursorAdapter {

        ChaosBagListAdapter(Context context, Cursor cursor) {
            super(context, cursor, 0);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
            return LayoutInflater.from(context).inflate(R.layout.d_item_chaos_bag, viewGroup, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            // Find fields to populate in inflated template and set the right fonts
            Typeface teutonic = Typeface.createFromAsset(getAssets(), "fonts/teutonic.ttf");
            TextView chaosBagNameView = view.findViewById(R.id.chaos_bag_name);
            chaosBagNameView.setTypeface(teutonic);

            // Set title
            String chaosBagName = cursor.getString(cursor.getColumnIndexOrThrow(ArkhamContract.ChaosBagEntry
                    .COLUMN_CHAOS_BAG_NAME));
            chaosBagNameView.setText(chaosBagName);

            // Show chaos tokens
            int[] bag = new int[17];
            bag[1] = cursor.getInt(cursor.getColumnIndexOrThrow(ArkhamContract.ChaosBagEntry.COLUMN_ONE));
            bag[2] = cursor.getInt(cursor.getColumnIndexOrThrow(ArkhamContract.ChaosBagEntry.COLUMN_TWO));
            bag[3] = cursor.getInt(cursor.getColumnIndexOrThrow(ArkhamContract.ChaosBagEntry.COLUMN_THREE));
            bag[4] = cursor.getInt(cursor.getColumnIndexOrThrow(ArkhamContract.ChaosBagEntry.COLUMN_FOUR));
            bag[5] = cursor.getInt(cursor.getColumnIndexOrThrow(ArkhamContract.ChaosBagEntry.COLUMN_FIVE));
            bag[6] = cursor.getInt(cursor.getColumnIndexOrThrow(ArkhamContract.ChaosBagEntry.COLUMN_SIX));
            bag[7] = cursor.getInt(cursor.getColumnIndexOrThrow(ArkhamContract.ChaosBagEntry.COLUMN_SEVEN));
            bag[8] = cursor.getInt(cursor.getColumnIndexOrThrow(ArkhamContract.ChaosBagEntry.COLUMN_EIGHT));
            bag[9] = cursor.getInt(cursor.getColumnIndexOrThrow(ArkhamContract.ChaosBagEntry.COLUMN_NINE));
            bag[10] = cursor.getInt(cursor.getColumnIndexOrThrow(ArkhamContract.ChaosBagEntry.COLUMN_TEN));
            bag[11] = cursor.getInt(cursor.getColumnIndexOrThrow(ArkhamContract.ChaosBagEntry.COLUMN_ELEVEN));
            bag[12] = cursor.getInt(cursor.getColumnIndexOrThrow(ArkhamContract.ChaosBagEntry.COLUMN_TWELVE));
            bag[13] = cursor.getInt(cursor.getColumnIndexOrThrow(ArkhamContract.ChaosBagEntry.COLUMN_THIRTEEN));
            bag[14] = cursor.getInt(cursor.getColumnIndexOrThrow(ArkhamContract.ChaosBagEntry.COLUMN_FOURTEEN));
            bag[15] = cursor.getInt(cursor.getColumnIndexOrThrow(ArkhamContract.ChaosBagEntry.COLUMN_FIFTEEN));
            bag[16] = cursor.getInt(cursor.getColumnIndexOrThrow(ArkhamContract.ChaosBagEntry.COLUMN_SIXTEEN));
            Long bagId = cursor.getLong(cursor.getColumnIndexOrThrow(ArkhamContract.ChaosBagEntry._ID));
            if (globalVariables.ChaosBagID == bagId) {
                view.setBackgroundColor(getColor(R.color.colorBlackTint));
            } else {
                view.setBackgroundColor(getColor(R.color.colorClear));
            }

            // Setup chaos bag by adding relevant tokens
            ArrayList<Integer> chaosbag = new ArrayList<>();
            for (int i = 0; i < 17; i++) {
                for (int j = 0; j < bag[i]; j++) {
                    chaosbag.add(i);
                }
            }
            // Clears then adds views for every chaos token in the bag to the relevant layouts
            LinearLayout currentChaosBagOne = view.findViewById(R.id.chaos_tokens_one);
            LinearLayout currentChaosBagTwo = view.findViewById(R.id.chaos_tokens_two);
            LinearLayout currentChaosBagThree = view.findViewById(R.id.chaos_tokens_three);
            LinearLayout currentChaosBagFour = view.findViewById(R.id.chaos_tokens_four);
            LinearLayout currentChaosBagFive = view.findViewById(R.id.chaos_tokens_five);
            currentChaosBagOne.removeAllViews();
            currentChaosBagTwo.removeAllViews();
            currentChaosBagThree.removeAllViews();
            currentChaosBagFour.removeAllViews();
            currentChaosBagFive.removeAllViews();
            for (int i = 0; i < chaosbag.size(); i++) {
                int currentToken = chaosbag.get(i);
                ImageView tokenView = new ImageView(getBaseContext());
                int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, getResources()
                        .getDisplayMetrics());
                int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, getResources()
                        .getDisplayMetrics());
                tokenView.setLayoutParams(new ViewGroup.LayoutParams(width, height));
                int tokenId = getResources().getIdentifier("drawable/token_" + currentToken, null, getPackageName());
                tokenView.setImageResource(tokenId);

                if (currentChaosBagOne.getChildCount() < 10) {
                    currentChaosBagOne.addView(tokenView);
                } else if (currentChaosBagTwo.getChildCount() < 10) {
                    currentChaosBagTwo.addView(tokenView);
                } else if (currentChaosBagThree.getChildCount() < 10) {
                    currentChaosBagThree.addView(tokenView);
                } else if (currentChaosBagFour.getChildCount() < 10){
                    currentChaosBagFour.addView(tokenView);
                } else {
                    currentChaosBagFive.addView(tokenView);
                }
            }

            // Display relevant text if adding campaign tokens
            int campaignTokens = cursor.getInt(cursor.getColumnIndexOrThrow(ArkhamContract.ChaosBagEntry.COLUMN_CAMPAIGN_TOKENS));
            TextView campaignTokenView = view.findViewById(R.id.campaign_chaos_tokens);
            if(campaignTokens == 1){
                campaignTokenView.setVisibility(VISIBLE);
                Typeface arnopro = Typeface.createFromAsset(getAssets(), "fonts/arnopro.otf");
                campaignTokenView.setTypeface(arnopro);
            }
        }
    }

    private class ChaosBagOnClickListener implements AdapterView.OnItemClickListener {

        Context context;

        private ChaosBagOnClickListener(Context con) {
            context = con;
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int a, long id) {
            globalVariables.ChaosBagID = id;

            // Get a writable database
            ArkhamDbHelper dbHelper = new ArkhamDbHelper(LoadChaosBagActivity.this);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues campaignValues = new ContentValues();
            campaignValues.put(ArkhamContract.CampaignEntry.COLUMN_CHAOS_BAG, id);
            String campaignSelection = ArkhamContract.CampaignEntry._ID + " LIKE ?";
            String[] campaignSelectionArgs = {Long.toString(globalVariables.CampaignID)};
            db.update(
                    ArkhamContract.CampaignEntry.TABLE_NAME,
                    campaignValues,
                    campaignSelection,
                    campaignSelectionArgs);

            finish();
            startActivity(getIntent());
            Intent intent = new Intent(LoadChaosBagActivity.this, CustomChaosBagActivity.class);
            startActivity(intent);
        }
    }

    // Opens a dialog to delete a chaos bag when it is long clicked
    private class ChaosBagOnLongClickListener implements AdapterView.OnItemLongClickListener {

        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
            // Create new dialog fragment
            DeleteBagDialogFragment newFragment = new DeleteBagDialogFragment();
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

    // Dialog to confirm the deletion of a bag
    public static class DeleteBagDialogFragment extends DialogFragment {
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
            String[] bagProjection = {
                    ArkhamContract.ChaosBagEntry._ID,
                    ArkhamContract.ChaosBagEntry.COLUMN_CHAOS_BAG_NAME
            };
            String bagSelection = ArkhamContract.ChaosBagEntry._ID + " = ?";
            Cursor cursor = db.query(
                    ArkhamContract.ChaosBagEntry.TABLE_NAME,    // The table to query
                    bagProjection,                              // The columns to return
                    bagSelection,                               // The columns for the WHERE clause
                    selectionArgs,                              // The values for the WHERE clause
                    null,                                       // don't group the rows
                    null,                                       // don't filter by row groups
                    null                                        // The sort order
            );
            String bagName = "";
            while (cursor.moveToNext()) {
                bagName = cursor.getString(cursor.getColumnIndexOrThrow(ArkhamContract.ChaosBagEntry
                        .COLUMN_CHAOS_BAG_NAME));
            }
            cursor.close();

            // Get the layout inflater and inflate the view
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            View v = View.inflate(getActivity(), R.layout.d_dialog_delete_chaosbag, null);

            // Get the relevant views and set the right fonts
            Typeface arnopro = Typeface.createFromAsset(getActivity().getAssets(), "fonts/arnopro.otf");
            Typeface arnoprobold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/arnoprobold.otf");
            Typeface teutonic = Typeface.createFromAsset(getActivity().getAssets(), "fonts/teutonic.ttf");
            Button cancelButton = v.findViewById(R.id.cancel_button);
            Button okayButton = v.findViewById(R.id.okay_button);
            cancelButton.setTypeface(teutonic);
            okayButton.setTypeface(teutonic);
            TextView delete = v.findViewById(R.id.delete_bag);
            TextView confirm = v.findViewById(R.id.confirm_delete_bag);
            TextView bag = v.findViewById(R.id.bag_name);
            delete.setTypeface(teutonic);
            confirm.setTypeface(arnopro);
            bag.setTypeface(arnoprobold);
            bag.setText(bagName);

            // Delete the campaign on confirm
            okayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // Find all of the relevant rows of the database tables for the campaign clicked on
                    String bagSelection = ArkhamContract.ChaosBagEntry._ID + " = ?";
                    db.delete(ArkhamContract.ChaosBagEntry.TABLE_NAME, bagSelection, selectionArgs);

                    // Refresh the ListView by re-querying the cursor
                    Cursor bagCursor = db.rawQuery("SELECT  * FROM " + ArkhamContract.ChaosBagEntry.TABLE_NAME, null);
                    chaosBagListAdapter.swapCursor(bagCursor);
                    dismiss();
                    if(position == globalVariables.ChaosBagID){
                        globalVariables.ChaosBagID = -1;
                        ArkhamDbHelper dbHelper = new ArkhamDbHelper(getActivity());
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        ContentValues campaignValues = new ContentValues();
                        campaignValues.put(ArkhamContract.CampaignEntry.COLUMN_CHAOS_BAG, -1);
                        String campaignSelection = ArkhamContract.CampaignEntry._ID + " LIKE ?";
                        String[] campaignSelectionArgs = {Long.toString(globalVariables.CampaignID)};
                        db.update(
                                ArkhamContract.CampaignEntry.TABLE_NAME,
                                campaignValues,
                                campaignSelection,
                                campaignSelectionArgs);
                        getActivity().finish();
                        startActivity(getActivity().getIntent());
                    }
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
