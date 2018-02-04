package com.whitdan.arkhamhorrorlcgcampaignguide.D_Misc;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.whitdan.arkhamhorrorlcgcampaignguide.A_Menus.MainMenuActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.R;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.ArkhamContract;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.ArkhamContract.ChaosBagEntry;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.ArkhamDbHelper;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.GlobalVariables;

public class CustomChaosBagActivity extends AppCompatActivity {

    private static GlobalVariables globalVariables;
    private int count;
    private boolean newBag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // If app is reopening after the process is killed, kick back to the main menu (stops the activity from
        // showing up unpopulated)
        if (savedInstanceState != null) {
            Intent intent = new Intent(CustomChaosBagActivity.this, MainMenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.d_activity_custom_chaos_bag);
        globalVariables = (GlobalVariables) this.getApplication();
        count = 0;

        // Set title and heading
        TextView title = findViewById(R.id.custom_chaos_bag);
        Typeface teutonic = Typeface.createFromAsset(getAssets(), "fonts/teutonic.ttf");
        Typeface wolgast = Typeface.createFromAsset(getAssets(), "fonts/wolgast.otf");
        Typeface wolgastbold = Typeface.createFromAsset(getAssets(), "fonts/wolgastbold.otf");
        Typeface arnopro = Typeface.createFromAsset(getAssets(), "fonts/arnopro.otf");
        title.setTypeface(teutonic);

        // Setup edit text
        final EditText chaosBagName = findViewById(R.id.edit_name);
        chaosBagName.setTypeface(wolgast);
        setupUI(findViewById(R.id.parent_layout), this);

        // Set clicklisteners
        ImageView oneDecrement = findViewById(R.id.one_decrement);
        ImageView oneIncrement = findViewById(R.id.one_increment);
        ImageView twoDecrement = findViewById(R.id.two_decrement);
        ImageView twoIncrement = findViewById(R.id.two_increment);
        ImageView threeDecrement = findViewById(R.id.three_decrement);
        ImageView threeIncrement = findViewById(R.id.three_increment);
        ImageView fourDecrement = findViewById(R.id.four_decrement);
        ImageView fourIncrement = findViewById(R.id.four_increment);
        ImageView fiveDecrement = findViewById(R.id.five_decrement);
        ImageView fiveIncrement = findViewById(R.id.five_increment);
        ImageView sixDecrement = findViewById(R.id.six_decrement);
        ImageView sixIncrement = findViewById(R.id.six_increment);
        ImageView sevenDecrement = findViewById(R.id.seven_decrement);
        ImageView sevenIncrement = findViewById(R.id.seven_increment);
        ImageView eightDecrement = findViewById(R.id.eight_decrement);
        ImageView eightIncrement = findViewById(R.id.eight_increment);
        ImageView nineDecrement = findViewById(R.id.nine_decrement);
        ImageView nineIncrement = findViewById(R.id.nine_increment);
        ImageView tenDecrement = findViewById(R.id.ten_decrement);
        ImageView tenIncrement = findViewById(R.id.ten_increment);
        ImageView elevenDecrement = findViewById(R.id.eleven_decrement);
        ImageView elevenIncrement = findViewById(R.id.eleven_increment);
        ImageView twelveDecrement = findViewById(R.id.twelve_decrement);
        ImageView twelveIncrement = findViewById(R.id.twelve_increment);
        ImageView thirteenDecrement = findViewById(R.id.thirteen_decrement);
        ImageView thirteenIncrement = findViewById(R.id.thirteen_increment);
        ImageView fourteenDecrement = findViewById(R.id.fourteen_decrement);
        ImageView fourteenIncrement = findViewById(R.id.fourteen_increment);
        ImageView fifteenDecrement = findViewById(R.id.fifteen_decrement);
        ImageView fifteenIncrement = findViewById(R.id.fifteen_increment);
        ImageView sixteenDecrement = findViewById(R.id.sixteen_decrement);
        ImageView sixteenIncrement = findViewById(R.id.sixteen_increment);
        oneDecrement.setOnClickListener(new counterClickListener());
        oneIncrement.setOnClickListener(new counterClickListener());
        twoDecrement.setOnClickListener(new counterClickListener());
        twoIncrement.setOnClickListener(new counterClickListener());
        threeDecrement.setOnClickListener(new counterClickListener());
        threeIncrement.setOnClickListener(new counterClickListener());
        fourDecrement.setOnClickListener(new counterClickListener());
        fourIncrement.setOnClickListener(new counterClickListener());
        fiveDecrement.setOnClickListener(new counterClickListener());
        fiveIncrement.setOnClickListener(new counterClickListener());
        sixDecrement.setOnClickListener(new counterClickListener());
        sixIncrement.setOnClickListener(new counterClickListener());
        sevenDecrement.setOnClickListener(new counterClickListener());
        sevenIncrement.setOnClickListener(new counterClickListener());
        eightDecrement.setOnClickListener(new counterClickListener());
        eightIncrement.setOnClickListener(new counterClickListener());
        nineDecrement.setOnClickListener(new counterClickListener());
        nineIncrement.setOnClickListener(new counterClickListener());
        tenDecrement.setOnClickListener(new counterClickListener());
        tenIncrement.setOnClickListener(new counterClickListener());
        elevenDecrement.setOnClickListener(new counterClickListener());
        elevenIncrement.setOnClickListener(new counterClickListener());
        twelveDecrement.setOnClickListener(new counterClickListener());
        twelveIncrement.setOnClickListener(new counterClickListener());
        thirteenDecrement.setOnClickListener(new counterClickListener());
        thirteenIncrement.setOnClickListener(new counterClickListener());
        fourteenDecrement.setOnClickListener(new counterClickListener());
        fourteenIncrement.setOnClickListener(new counterClickListener());
        fifteenDecrement.setOnClickListener(new counterClickListener());
        fifteenIncrement.setOnClickListener(new counterClickListener());
        sixteenDecrement.setOnClickListener(new counterClickListener());
        sixteenIncrement.setOnClickListener(new counterClickListener());

        // Set typefaces
        final TextView oneAmount = findViewById(R.id.one_amount);
        final TextView twoAmount = findViewById(R.id.two_amount);
        final TextView threeAmount = findViewById(R.id.three_amount);
        final TextView fourAmount = findViewById(R.id.four_amount);
        final TextView fiveAmount = findViewById(R.id.five_amount);
        final TextView sixAmount = findViewById(R.id.six_amount);
        final TextView sevenAmount = findViewById(R.id.seven_amount);
        final TextView eightAmount = findViewById(R.id.eight_amount);
        final TextView nineAmount = findViewById(R.id.nine_amount);
        final TextView tenAmount = findViewById(R.id.ten_amount);
        final TextView elevenAmount = findViewById(R.id.eleven_amount);
        final TextView twelveAmount = findViewById(R.id.twelve_amount);
        final TextView thirteenAmount = findViewById(R.id.thirteen_amount);
        final TextView fourteenAmount = findViewById(R.id.fourteen_amount);
        final TextView fifteenAmount = findViewById(R.id.fifteen_amount);
        final TextView sixteenAmount = findViewById(R.id.sixteen_amount);
        oneAmount.setTypeface(wolgastbold);
        twoAmount.setTypeface(wolgastbold);
        threeAmount.setTypeface(wolgastbold);
        fourAmount.setTypeface(wolgastbold);
        fiveAmount.setTypeface(wolgastbold);
        sixAmount.setTypeface(wolgastbold);
        sevenAmount.setTypeface(wolgastbold);
        eightAmount.setTypeface(wolgastbold);
        nineAmount.setTypeface(wolgastbold);
        tenAmount.setTypeface(wolgastbold);
        elevenAmount.setTypeface(wolgastbold);
        twelveAmount.setTypeface(wolgastbold);
        thirteenAmount.setTypeface(wolgastbold);
        fourteenAmount.setTypeface(wolgastbold);
        fifteenAmount.setTypeface(wolgastbold);
        sixteenAmount.setTypeface(wolgastbold);
        final CheckBox checkbox = findViewById(R.id.campaign_chaos_tokens);
        checkbox.setTypeface(arnopro);

        Log.i("boo", "boo");
        // Set values if not new bag
        newBag = getIntent().getBooleanExtra("NEW_BAG", false);
        if (!newBag) {
            Log.i("blah", "blah");
            // Get access to a writable SQLite database
            ArkhamDbHelper dbHelper = new ArkhamDbHelper(this);
            final SQLiteDatabase db = dbHelper.getWritableDatabase();
            String[] bagProjection = {
                    ArkhamContract.ChaosBagEntry._ID,
                    ArkhamContract.ChaosBagEntry.COLUMN_CHAOS_BAG_NAME,
                    ChaosBagEntry.COLUMN_CAMPAIGN_TOKENS,
                    ChaosBagEntry.COLUMN_ONE,
                    ChaosBagEntry.COLUMN_TWO,
                    ChaosBagEntry.COLUMN_THREE,
                    ChaosBagEntry.COLUMN_FOUR,
                    ChaosBagEntry.COLUMN_FIVE,
                    ChaosBagEntry.COLUMN_SIX,
                    ChaosBagEntry.COLUMN_SEVEN,
                    ChaosBagEntry.COLUMN_EIGHT,
                    ChaosBagEntry.COLUMN_NINE,
                    ChaosBagEntry.COLUMN_TEN,
                    ChaosBagEntry.COLUMN_ELEVEN,
                    ChaosBagEntry.COLUMN_TWELVE,
                    ChaosBagEntry.COLUMN_THIRTEEN,
                    ChaosBagEntry.COLUMN_FOURTEEN,
                    ChaosBagEntry.COLUMN_FIFTEEN,
                    ChaosBagEntry.COLUMN_SIXTEEN
            };
            String bagSelection = ArkhamContract.ChaosBagEntry._ID + " = ?";
            String[] selectionArgs = {Long.toString(globalVariables.ChaosBagID)};
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
            int campaignTokens = 0;
            int[] bag = new int[17];
            while (cursor.moveToNext()) {
                bagName = cursor.getString(cursor.getColumnIndexOrThrow(ArkhamContract.ChaosBagEntry
                        .COLUMN_CHAOS_BAG_NAME));
                campaignTokens = cursor.getInt(cursor.getColumnIndexOrThrow(ChaosBagEntry.COLUMN_CAMPAIGN_TOKENS));
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
            }
            cursor.close();

            chaosBagName.setText(bagName);
            if (campaignTokens == 1) {
                checkbox.setChecked(true);
            } else {
                checkbox.setChecked(false);
            }
            oneAmount.setText(Integer.toString(bag[1]));
            twoAmount.setText(Integer.toString(bag[2]));
            threeAmount.setText(Integer.toString(bag[3]));
            fourAmount.setText(Integer.toString(bag[4]));
            fiveAmount.setText(Integer.toString(bag[5]));
            sixAmount.setText(Integer.toString(bag[6]));
            sevenAmount.setText(Integer.toString(bag[7]));
            eightAmount.setText(Integer.toString(bag[8]));
            nineAmount.setText(Integer.toString(bag[9]));
            tenAmount.setText(Integer.toString(bag[10]));
            elevenAmount.setText(Integer.toString(bag[11]));
            twelveAmount.setText(Integer.toString(bag[12]));
            thirteenAmount.setText(Integer.toString(bag[13]));
            fourteenAmount.setText(Integer.toString(bag[14]));
            fifteenAmount.setText(Integer.toString(bag[15]));
            sixteenAmount.setText(Integer.toString(bag[16]));
            for (int i : bag) {
                count += i;
            }
        }

        // Back button to finish the activity
        Button backButton = findViewById(R.id.back_button);
        backButton.setTypeface(teutonic);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Save bag button
        Button saveButton = findViewById(R.id.continue_button);
        saveButton.setTypeface(teutonic);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count == 0) {
                    Toast toast = Toast.makeText(getBaseContext(), R.string.must_token, Toast
                            .LENGTH_SHORT);
                    toast.show();
                } else if (chaosBagName.getText().toString().trim().length() == 0) {
                    Toast toast = Toast.makeText(getBaseContext(), R.string.must_bag, Toast
                            .LENGTH_SHORT);
                    toast.show();
                } else if (newBag) {

                    // Get a writable database
                    ArkhamDbHelper dbHelper = new ArkhamDbHelper(CustomChaosBagActivity.this);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                    // Create entry in chaos bag table
                    ContentValues bagValues = new ContentValues();
                    bagValues.put(ChaosBagEntry.COLUMN_CHAOS_BAG_NAME, chaosBagName.getText().toString()
                            .trim());
                    if (checkbox.isChecked()) {
                        bagValues.put(ChaosBagEntry.COLUMN_CAMPAIGN_TOKENS, 1);
                    } else {
                        bagValues.put(ChaosBagEntry.COLUMN_CAMPAIGN_TOKENS, 0);
                    }
                    bagValues.put(ChaosBagEntry.COLUMN_ONE, Integer.valueOf(oneAmount.getText().toString()));
                    bagValues.put(ChaosBagEntry.COLUMN_TWO, Integer.valueOf(twoAmount.getText().toString()));
                    bagValues.put(ChaosBagEntry.COLUMN_THREE, Integer.valueOf(threeAmount.getText().toString()));
                    bagValues.put(ChaosBagEntry.COLUMN_FOUR, Integer.valueOf(fourAmount.getText().toString()));
                    bagValues.put(ChaosBagEntry.COLUMN_FIVE, Integer.valueOf(fiveAmount.getText().toString()));
                    bagValues.put(ChaosBagEntry.COLUMN_SIX, Integer.valueOf(sixAmount.getText().toString()));
                    bagValues.put(ChaosBagEntry.COLUMN_SEVEN, Integer.valueOf(sevenAmount.getText().toString()));
                    bagValues.put(ChaosBagEntry.COLUMN_EIGHT, Integer.valueOf(eightAmount.getText().toString()));
                    bagValues.put(ChaosBagEntry.COLUMN_NINE, Integer.valueOf(nineAmount.getText().toString()));
                    bagValues.put(ChaosBagEntry.COLUMN_TEN, Integer.valueOf(tenAmount.getText().toString()));
                    bagValues.put(ChaosBagEntry.COLUMN_ELEVEN, Integer.valueOf(elevenAmount.getText().toString()));
                    bagValues.put(ChaosBagEntry.COLUMN_TWELVE, Integer.valueOf(twelveAmount.getText().toString()));
                    bagValues.put(ChaosBagEntry.COLUMN_THIRTEEN, Integer.valueOf(thirteenAmount.getText().toString()));
                    bagValues.put(ChaosBagEntry.COLUMN_FOURTEEN, Integer.valueOf(fourteenAmount.getText().toString()));
                    bagValues.put(ChaosBagEntry.COLUMN_FIFTEEN, Integer.valueOf(fifteenAmount.getText().toString()));
                    bagValues.put(ChaosBagEntry.COLUMN_SIXTEEN, Integer.valueOf(sixteenAmount.getText().toString()));
                    long newBagId = db.insert(ChaosBagEntry.TABLE_NAME, null, bagValues);

                    // Save chaos bag in use to campaign table
                    ContentValues campaignValues = new ContentValues();
                    campaignValues.put(ArkhamContract.CampaignEntry.COLUMN_CHAOS_BAG, newBagId);
                    globalVariables.ChaosBagID = newBagId;
                    String campaignSelection = ArkhamContract.CampaignEntry._ID + " LIKE ?";
                    String[] campaignSelectionArgs = {Long.toString(globalVariables.CampaignID)};
                    db.update(
                            ArkhamContract.CampaignEntry.TABLE_NAME,
                            campaignValues,
                            campaignSelection,
                            campaignSelectionArgs);

                    Intent intent = new Intent(CustomChaosBagActivity.this, ChaosBagActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    // Get a writable database
                    ArkhamDbHelper mDbHelper = new ArkhamDbHelper(CustomChaosBagActivity.this);
                    SQLiteDatabase db = mDbHelper.getWritableDatabase();

                    // Update chaos bag variables
                    ContentValues bagValues = new ContentValues();
                    bagValues.put(ChaosBagEntry.COLUMN_CHAOS_BAG_NAME, chaosBagName.getText().toString()
                            .trim());
                    if (checkbox.isChecked()) {
                        bagValues.put(ChaosBagEntry.COLUMN_CAMPAIGN_TOKENS, 1);
                    } else {
                        bagValues.put(ChaosBagEntry.COLUMN_CAMPAIGN_TOKENS, 0);
                    }
                    bagValues.put(ChaosBagEntry.COLUMN_ONE, Integer.valueOf(oneAmount.getText().toString()));
                    bagValues.put(ChaosBagEntry.COLUMN_TWO, Integer.valueOf(twoAmount.getText().toString()));
                    bagValues.put(ChaosBagEntry.COLUMN_THREE, Integer.valueOf(threeAmount.getText().toString()));
                    bagValues.put(ChaosBagEntry.COLUMN_FOUR, Integer.valueOf(fourAmount.getText().toString()));
                    bagValues.put(ChaosBagEntry.COLUMN_FIVE, Integer.valueOf(fiveAmount.getText().toString()));
                    bagValues.put(ChaosBagEntry.COLUMN_SIX, Integer.valueOf(sixAmount.getText().toString()));
                    bagValues.put(ChaosBagEntry.COLUMN_SEVEN, Integer.valueOf(sevenAmount.getText().toString()));
                    bagValues.put(ChaosBagEntry.COLUMN_EIGHT, Integer.valueOf(eightAmount.getText().toString()));
                    bagValues.put(ChaosBagEntry.COLUMN_NINE, Integer.valueOf(nineAmount.getText().toString()));
                    bagValues.put(ChaosBagEntry.COLUMN_TEN, Integer.valueOf(tenAmount.getText().toString()));
                    bagValues.put(ChaosBagEntry.COLUMN_ELEVEN, Integer.valueOf(elevenAmount.getText().toString()));
                    bagValues.put(ChaosBagEntry.COLUMN_TWELVE, Integer.valueOf(twelveAmount.getText().toString()));
                    bagValues.put(ChaosBagEntry.COLUMN_THIRTEEN, Integer.valueOf(thirteenAmount.getText().toString()));
                    bagValues.put(ChaosBagEntry.COLUMN_FOURTEEN, Integer.valueOf(fourteenAmount.getText().toString()));
                    bagValues.put(ChaosBagEntry.COLUMN_FIFTEEN, Integer.valueOf(fifteenAmount.getText().toString()));
                    bagValues.put(ChaosBagEntry.COLUMN_SIXTEEN, Integer.valueOf(sixteenAmount.getText().toString()));
                    String bagSelection = ArkhamContract.ChaosBagEntry._ID + " LIKE ?";
                    String[] bagSelectionArgs = {Long.toString(globalVariables.ChaosBagID)};
                    db.update(
                            ArkhamContract.ChaosBagEntry.TABLE_NAME,
                            bagValues,
                            bagSelection,
                            bagSelectionArgs);

                    Intent intent = new Intent(CustomChaosBagActivity.this, ChaosBagActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private class counterClickListener implements View.OnClickListener {

        String position = null;

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.one_decrement:
                case R.id.one_increment:
                    position = "one";
                    break;
                case R.id.two_decrement:
                case R.id.two_increment:
                    position = "two";
                    break;
                case R.id.three_decrement:
                case R.id.three_increment:
                    position = "three";
                    break;
                case R.id.four_decrement:
                case R.id.four_increment:
                    position = "four";
                    break;
                case R.id.five_decrement:
                case R.id.five_increment:
                    position = "five";
                    break;
                case R.id.six_decrement:
                case R.id.six_increment:
                    position = "six";
                    break;
                case R.id.seven_decrement:
                case R.id.seven_increment:
                    position = "seven";
                    break;
                case R.id.eight_decrement:
                case R.id.eight_increment:
                    position = "eight";
                    break;
                case R.id.nine_decrement:
                case R.id.nine_increment:
                    position = "nine";
                    break;
                case R.id.ten_decrement:
                case R.id.ten_increment:
                    position = "ten";
                    break;
                case R.id.eleven_decrement:
                case R.id.eleven_increment:
                    position = "eleven";
                    break;
                case R.id.twelve_decrement:
                case R.id.twelve_increment:
                    position = "twelve";
                    break;
                case R.id.thirteen_decrement:
                case R.id.thirteen_increment:
                    position = "thirteen";
                    break;
                case R.id.fourteen_decrement:
                case R.id.fourteen_increment:
                    position = "fourteen";
                    break;
                case R.id.fifteen_decrement:
                case R.id.fifteen_increment:
                    position = "fifteen";
                    break;
                case R.id.sixteen_decrement:
                case R.id.sixteen_increment:
                    position = "sixteen";
                    break;
            }

            int resId = getResources().getIdentifier(position + "_amount", "id", getPackageName());
            TextView counter = findViewById(resId);
            int amount = Integer.valueOf(counter.getText().toString());

            switch (view.getId()) {
                case R.id.one_decrement:
                case R.id.two_decrement:
                case R.id.three_decrement:
                case R.id.four_decrement:
                case R.id.five_decrement:
                case R.id.six_decrement:
                case R.id.seven_decrement:
                case R.id.eight_decrement:
                case R.id.nine_decrement:
                case R.id.ten_decrement:
                case R.id.eleven_decrement:
                case R.id.twelve_decrement:
                case R.id.thirteen_decrement:
                case R.id.fourteen_decrement:
                case R.id.fifteen_decrement:
                case R.id.sixteen_decrement:
                    if (amount > 0) {
                        amount--;
                        count--;
                        counter.setText(Integer.toString(amount));
                    }
                    break;
                case R.id.one_increment:
                case R.id.two_increment:
                case R.id.three_increment:
                case R.id.four_increment:
                case R.id.five_increment:
                case R.id.six_increment:
                case R.id.seven_increment:
                case R.id.eight_increment:
                case R.id.nine_increment:
                case R.id.ten_increment:
                case R.id.eleven_increment:
                case R.id.twelve_increment:
                case R.id.thirteen_increment:
                case R.id.fourteen_increment:
                case R.id.fifteen_increment:
                case R.id.sixteen_increment:
                    if (count == 50) {
                        Toast toast = Toast.makeText(CustomChaosBagActivity.this, R.string.maximum_chaos_bag, Toast
                                .LENGTH_SHORT);
                        toast.show();
                    }
                    if (amount < 5 && count < 50) {
                        amount++;
                        count++;
                        counter.setText(Integer.toString(amount));
                    }
                    break;
            }
        }
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
