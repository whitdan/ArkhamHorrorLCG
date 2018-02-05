package com.whitdan.arkhamhorrorlcgcampaignguide.D_Misc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.whitdan.arkhamhorrorlcgcampaignguide.A_Menus.MainMenuActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.C_Scenario.ScenarioResolutionActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.R;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.ArkhamContract;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.ArkhamContract.ChaosBagEntry;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.ArkhamDbHelper;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.GlobalVariables;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/*
    ChaosBagActivity - Allows selection of a difficulty and simulates the chaos bag, shows the makeup, allows the
    drawing of tokens, additional tokens, and shows the makeup of the chaos bag
 */

public class ChaosBagActivity extends AppCompatActivity {

    static GlobalVariables globalVariables;
    static ArrayList<Integer> chaosbag;
    static int basebagResult[];
    static int campaignTokens;
    int token;
    boolean draw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // If app is reopening after the process is killed, kick back to the main menu (stops the activity from
        // showing up unpopulated)
        if (savedInstanceState != null) {
            Intent intent = new Intent(ChaosBagActivity.this, MainMenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.d_activity_chaos_bag);
        globalVariables = (GlobalVariables) this.getApplication();
        token = -1;
        basebagResult = basebag(this);
        setupBag(this);

        // If custom bag, check if adding campaign tokens
        campaignTokens = 1;
        if (globalVariables.ChaosBagID != -1) {
            // Get access to a writable SQLite database
            ArkhamDbHelper dbHelper = new ArkhamDbHelper(this);
            final SQLiteDatabase db = dbHelper.getWritableDatabase();
            String[] bagProjection = {
                    ChaosBagEntry.COLUMN_CAMPAIGN_TOKENS,
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
            while (cursor.moveToNext()) {
                campaignTokens = cursor.getInt(cursor.getColumnIndexOrThrow(ChaosBagEntry.COLUMN_CAMPAIGN_TOKENS));
            }
            cursor.close();
        }

        // Set title and setup checkbox if necessary
        Typeface teutonic = Typeface.createFromAsset(getAssets(), "fonts/teutonic.ttf");
        Typeface arnopro = Typeface.createFromAsset(getAssets(), "fonts/arnopro.otf");
        TextView title = findViewById(R.id.current_scenario_name);
        title.setTypeface(teutonic);
        MainMenuActivity.setTitle(title);
        CheckBox box = findViewById(R.id.chaos_bag_checkbox);
        box.setTypeface(arnopro);
        final RadioGroup options = findViewById(R.id.chaos_bag_options);
        final RadioButton optionOne = findViewById(R.id.chaos_bag_option_one);
        final RadioButton optionTwo = findViewById(R.id.chaos_bag_option_two);
        final RadioButton optionThree = findViewById(R.id.chaos_bag_option_three);
        optionOne.setTypeface(arnopro);
        optionTwo.setTypeface(arnopro);
        optionThree.setTypeface(arnopro);
        switch (globalVariables.CurrentCampaign) {
            case 2:
                switch (globalVariables.CurrentScenario) {
                    case 4:
                        box.setVisibility(VISIBLE);
                        box.setText(R.string.adam_lynch_harold_walsted);
                        if (globalVariables.AdamLynchHaroldWalsted == 1) {
                            box.setChecked(true);
                        } else {
                            box.setChecked(false);
                        }
                        box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (compoundButton.isChecked()) {
                                    globalVariables.AdamLynchHaroldWalsted = 1;
                                } else {
                                    globalVariables.AdamLynchHaroldWalsted = 0;
                                }
                                setupBag(ChaosBagActivity.this);
                            }
                        });
                        break;
                }
                break;
            case 3:
                switch (globalVariables.CurrentScenario) {
                    case 1:
                        if (globalVariables.Theatre > 0) {
                            box.setChecked(true);
                            options.setVisibility(VISIBLE);
                        }
                        box.setVisibility(VISIBLE);
                        box.setText(R.string.advanced_2b);
                        optionOne.setText(R.string.city_aflame);
                        optionTwo.setText(R.string.path_mine);
                        optionThree.setText(R.string.shores_hail);
                        box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (compoundButton.isChecked()) {
                                    options.setVisibility(VISIBLE);
                                } else {
                                    options.setVisibility(GONE);
                                    options.clearCheck();
                                    globalVariables.Theatre = 0;
                                    setupBag(ChaosBagActivity.this);
                                }
                            }
                        });
                        switch (globalVariables.Theatre) {
                            case 1:
                                optionOne.setChecked(true);
                                break;
                            case 2:
                                optionTwo.setChecked(true);
                                break;
                            case 3:
                                optionThree.setChecked(true);
                                break;
                        }
                        options.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                                if (optionOne.isChecked()) {
                                    globalVariables.Theatre = 1;
                                } else if (optionTwo.isChecked()) {
                                    globalVariables.Theatre = 2;
                                } else if (optionThree.isChecked()) {
                                    globalVariables.Theatre = 3;
                                }
                                setupBag(ChaosBagActivity.this);
                            }
                        });
                        break;
                }
                break;
        }
        TextView chaosBag = findViewById(R.id.chaos_bag);
        chaosBag.setTypeface(teutonic);
        TextView scenarioCard = findViewById(R.id.scenario_card);
        scenarioCard.setTypeface(teutonic);
        TextView currentSetup = findViewById(R.id.current_setup);
        currentSetup.setTypeface(teutonic);

        // Set fonts and listeners to radio buttons
        RadioGroup difficulty = findViewById(R.id.difficulty_selection);
        for (int i = 0; i < difficulty.getChildCount(); i++) {
            View view = difficulty.getChildAt(i);
            if (view instanceof RadioButton) {
                ((RadioButton) view).setTypeface(arnopro);
                view.setOnClickListener(new difficultySelectionListener());
            }
        }
        setupScenarioCard(this);

        // Hide relevant radiobuttons on standalone scenarios
        RadioButton easy = findViewById(R.id.easy_button);
        RadioButton standard = findViewById(R.id.standard_button);
        RadioButton hard = findViewById(R.id.hard_button);
        RadioButton expert = findViewById(R.id.expert_button);
        if (globalVariables.CurrentCampaign == 999) {
            easy.setVisibility(GONE);
            expert.setVisibility(GONE);
        }
        switch (globalVariables.CurrentDifficulty) {
            case 0:
                easy.setChecked(true);
                break;
            case 1:
                standard.setChecked(true);
                break;
            case 2:
                hard.setChecked(true);
                break;
            case 3:
                expert.setChecked(true);
                break;
        }

        // Setup chaos drawing buttons
        Button drawButton = findViewById(R.id.draw_token);
        drawButton.setTypeface(teutonic);
        drawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                draw = true;
                drawToken(view);
            }
        });
        LinearLayout currentTokenLayout = findViewById(R.id.current_token_layout);
        currentTokenLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                draw = true;
                drawToken(view);
            }
        });
        Button addButton = findViewById(R.id.add_token);
        addButton.setTypeface(teutonic);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                draw = false;
                drawToken(view);
            }
        });

        // Select chaos bag button
        Button selectButton = findViewById(R.id.select_chaos_bag);
        selectButton.setTypeface(teutonic);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChaosBagActivity.this, LoadChaosBagActivity.class);
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

        Button continueButton = findViewById(R.id.continue_button);
        continueButton.setTypeface(teutonic);
        if (globalVariables.CurrentCampaign == 999) {
            globalVariables.CurrentDifficulty = 1;
            setupBag(this);
            standard.setChecked(true);
            continueButton.setVisibility(VISIBLE);
            continueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ChaosBagActivity.this, ScenarioResolutionActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    // Listener for the difficulty checkboxes to set the difficulty and refresh the chaosbag when done
    private class difficultySelectionListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.easy_button:
                    globalVariables.CurrentDifficulty = 0;
                    break;
                case R.id.standard_button:
                    globalVariables.CurrentDifficulty = 1;
                    break;
                case R.id.hard_button:
                    globalVariables.CurrentDifficulty = 2;
                    break;
                case R.id.expert_button:
                    globalVariables.CurrentDifficulty = 3;
                    break;
            }
            setupScenarioCard(ChaosBagActivity.this);
            setupBag(ChaosBagActivity.this);

            // Clear all current token views
            LinearLayout tokens = findViewById(R.id.token_layout);
            LinearLayout currentTokens = findViewById(R.id.current_token_layout);
            tokens.removeAllViews();
            currentTokens.removeAllViews();
        }
    }

    // Sets up the chaos bag with all of the relevant tokens
    private static void setupBag(Activity activity) {
        // Setup chaos bag by adding relevant tokens
        chaosbag = new ArrayList<>();
        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < basebagResult[i]; j++) {
                chaosbag.add(i);
            }
        }

        if (campaignTokens == 1) {
            // Add tokens to the bag depending on the scenario and resolutions
            int scenario;
            if (globalVariables.CurrentScenario > 100) {
                scenario = globalVariables.PreviousScenario;
            } else {
                scenario = globalVariables.CurrentScenario;
            }

            switch (globalVariables.CurrentCampaign) {
                // Night of the Zealot
                case 1:
                    if (scenario > 2) {
                        chaosbag.add(14);
                    }
                    break;
                // The Dunwich Legacy
                case 2:
                    if (((scenario > 1 && globalVariables.FirstScenario == 1) ||
                            scenario > 2) && globalVariables.Students == 0) {
                        chaosbag.add(13);
                    }
                    if (((scenario == 1 && globalVariables.FirstScenario == 2) ||
                            scenario > 2) && globalVariables.InvestigatorsCheated == 1) {
                        chaosbag.add(14);
                    }
                    if (scenario > 4 && globalVariables.Necronomicon == 2) {
                        chaosbag.add(14);
                    }
                    if (globalVariables.AdamLynchHaroldWalsted == 1) {
                        chaosbag.add(13);
                    }
                    if (scenario > 4) {
                        switch (globalVariables.CurrentDifficulty) {
                            case 0:
                                chaosbag.add(4);
                                break;
                            case 1:
                                chaosbag.add(5);
                                break;
                            case 2:
                                chaosbag.add(6);
                                break;
                            case 3:
                                chaosbag.add(7);
                                break;
                        }
                    }
                    if (scenario > 8) {
                        switch (globalVariables.CurrentDifficulty) {
                            case 0:
                                chaosbag.add(5);
                                break;
                            case 1:
                                chaosbag.add(7);
                                break;
                            case 2:
                                chaosbag.add(8);
                                break;
                            case 3:
                                chaosbag.add(9);
                                break;
                        }
                    }
                    break;
                // Path to Carcosa
                case 3:
                    switch (globalVariables.Theatre) {
                        case 1:
                            chaosbag.add(12);
                            chaosbag.add(12);
                            break;
                        case 2:
                            chaosbag.add(13);
                            chaosbag.add(13);
                            break;
                        case 3:
                            chaosbag.add(14);
                            chaosbag.add(14);
                            break;
                        case 4:
                            chaosbag.add(12);
                            chaosbag.add(13);
                            chaosbag.add(14);
                            break;
                    }
                    if (scenario > 4) {
                        switch (globalVariables.CurrentDifficulty) {
                            case 0:
                                chaosbag.add(4);
                                break;
                            case 1:
                                chaosbag.add(5);
                                break;
                            case 2:
                                chaosbag.add(6);
                                break;
                            case 3:
                                chaosbag.add(7);
                                break;
                        }
                    }
                    break;
            }
        }

        // Get relevant views
        Collections.sort(chaosbag);
        LinearLayout currentChaosBagOne = activity.findViewById(R.id.current_chaos_bag_one);
        LinearLayout currentChaosBagTwo = activity.findViewById(R.id.current_chaos_bag_two);
        LinearLayout currentChaosBagThree = activity.findViewById(R.id.current_chaos_bag_three);
        LinearLayout currentChaosBagFour = activity.findViewById(R.id.current_chaos_bag_four);
        LinearLayout currentChaosBagFive = activity.findViewById(R.id.current_chaos_bag_five);
        LinearLayout currentChaosBagSix = activity.findViewById(R.id.current_chaos_bag_six);
        currentChaosBagOne.removeAllViews();
        currentChaosBagTwo.removeAllViews();
        currentChaosBagThree.removeAllViews();
        currentChaosBagFour.removeAllViews();
        currentChaosBagFive.removeAllViews();
        currentChaosBagSix.removeAllViews();

        // Adds views for every chaos token in the bag to the relevant layouts
        for (int i = 0; i < chaosbag.size(); i++) {
            int currentToken = chaosbag.get(i);
            ImageView tokenView = new ImageView(activity);
            int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, activity.getResources()
                    .getDisplayMetrics());
            int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, activity.getResources()
                    .getDisplayMetrics());
            tokenView.setLayoutParams(new ViewGroup.LayoutParams(width, height));
            int tokenId = activity.getResources().getIdentifier("drawable/token_" + currentToken, null, activity
                    .getPackageName());
            tokenView.setImageResource(tokenId);

            if (currentChaosBagOne.getChildCount() < 10) {
                currentChaosBagOne.addView(tokenView);
            } else if (currentChaosBagTwo.getChildCount() < 10) {
                currentChaosBagTwo.addView(tokenView);
            } else if (currentChaosBagThree.getChildCount() < 10) {
                currentChaosBagThree.addView(tokenView);
            } else if (currentChaosBagFour.getChildCount() < 10){
                currentChaosBagFour.addView(tokenView);
            } else if (currentChaosBagFive.getChildCount() <10){
                currentChaosBagFive.addView(tokenView);
            } else {
                currentChaosBagSix.addView(tokenView);
            }
        }
    }

    // Base difficulties
    private static int[] basebag(Context context) {
        int[] bag = new int[17];
        if (globalVariables.ChaosBagID == -1) {
            switch (globalVariables.CurrentCampaign) {
                // Night of the Zealot
                case 1:
                    switch (globalVariables.CurrentDifficulty) {
                        case 0:
                            bag = new int[]{0, 2, 3, 3, 2, 0, 0, 0, 0, 0, 0, 2, 1, 1, 0, 1, 1};
                            break;
                        case 1:
                            bag = new int[]{0, 1, 2, 3, 2, 1, 1, 0, 0, 0, 0, 2, 1, 1, 0, 1, 1};
                            break;
                        case 2:
                            bag = new int[]{0, 0, 3, 2, 2, 2, 1, 1, 0, 0, 0, 2, 1, 1, 0, 1, 1};
                            break;
                        case 3:
                            bag = new int[]{0, 0, 1, 2, 2, 2, 2, 1, 1, 0, 1, 2, 1, 1, 0, 1, 1};
                    }
                    break;
                // Dunwich Legacy
                case 2:
                    switch (globalVariables.CurrentDifficulty) {
                        case 0:
                            bag = new int[]{0, 2, 3, 3, 2, 0, 0, 0, 0, 0, 0, 2, 1, 0, 0, 1, 1};
                            break;
                        case 1:
                            bag = new int[]{0, 1, 2, 3, 2, 1, 1, 0, 0, 0, 0, 2, 1, 0, 0, 1, 1};
                            break;
                        case 2:
                            bag = new int[]{0, 0, 3, 2, 2, 2, 1, 1, 0, 0, 0, 2, 1, 0, 0, 1, 1};
                            break;
                        case 3:
                            bag = new int[]{0, 0, 1, 2, 2, 2, 2, 1, 1, 0, 1, 2, 1, 0, 0, 1, 1};
                            break;
                    }
                    break;
                // Path to Carcosa
                case 3:
                    switch (globalVariables.CurrentDifficulty) {
                        case 0:
                            bag = new int[]{0, 2, 3, 3, 2, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 1, 1};
                            break;
                        case 1:
                            bag = new int[]{0, 1, 2, 3, 2, 1, 1, 0, 0, 0, 0, 3, 0, 0, 0, 1, 1};
                            break;
                        case 2:
                            bag = new int[]{0, 0, 3, 2, 2, 2, 1, 1, 0, 0, 0, 3, 0, 0, 0, 1, 1};
                            break;
                        case 3:
                            bag = new int[]{0, 0, 1, 2, 2, 2, 2, 1, 1, 0, 1, 3, 0, 0, 0, 1, 1};
                            break;
                    }
            }
            if (globalVariables.CurrentCampaign == 999) {
                switch (globalVariables.CurrentScenario) {
                    // Curse of the Rougarou
                    case 101:
                        switch (globalVariables.CurrentDifficulty) {
                            case 1:
                                bag = new int[]{0, 2, 3, 3, 2, 2, 2, 1, 1, 0, 0, 2, 2, 1, 1, 1, 1};
                                break;
                            case 2:
                                bag = new int[]{0, 1, 3, 3, 2, 2, 2, 2, 1, 0, 1, 3, 2, 1, 1, 1, 1};
                                break;
                        }
                        break;
                    // Carnevale of Horrors
                    case 102:
                        switch (globalVariables.CurrentDifficulty) {
                            case 1:
                                bag = new int[]{0, 1, 3, 3, 1, 1, 1, 0, 1, 0, 0, 3, 1, 1, 1, 1, 1};
                                break;
                            case 2:
                                bag = new int[]{0, 1, 3, 2, 0, 1, 1, 1, 1, 1, 0, 3, 1, 1, 1, 1, 1};
                                break;
                        }
                        break;
                }
            }
        } else {
            // Get access to a writable SQLite database
            ArkhamDbHelper dbHelper = new ArkhamDbHelper(context);
            final SQLiteDatabase db = dbHelper.getWritableDatabase();
            String[] bagProjection = {
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
            while (cursor.moveToNext()) {
                bag[0] = 0;
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
        }
        return bag;
    }

    // Method activated on presing of a button to draw token
    private void drawToken(View view) {
        // Get views
        LinearLayout tokens = findViewById(R.id.token_layout);
        LinearLayout currentTokens = findViewById(R.id.current_token_layout);
        int numberOfViews = currentTokens.getChildCount();

        // If not using the add button
        if (draw) {
            // Resets the bag
            resetBag(ChaosBagActivity.this);

            // Removes the last token in the token stream
            if (tokens.getChildCount() == 6) {
                tokens.removeViewAt(0);
            }
            // Adds the previous token to the tokens view
            if (token >= 0) {
                ImageView tokenView = new ImageView(view.getContext());
                int savedHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources()
                        .getDisplayMetrics());
                int savedWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources()
                        .getDisplayMetrics());
                tokenView.setLayoutParams(new ViewGroup.LayoutParams(savedWidth, savedHeight));
                int tokenId = view.getContext().getResources().getIdentifier("drawable/token_" + token, null, view
                        .getContext().getPackageName());
                tokenView.setImageResource(tokenId);
                tokens.addView(tokenView);
            }

            // Randomly pick the next token
            int chosen = new Random().nextInt(chaosbag.size());
            token = chaosbag.get(chosen);
            chaosbag.remove(chosen);

            // Adds the token to the main token view with the animation
            ImageView currentToken = new ImageView(view.getContext());
            int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 75, getResources()
                    .getDisplayMetrics());
            int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 75, getResources()
                    .getDisplayMetrics());
            currentToken.setLayoutParams(new ViewGroup.LayoutParams(width, height));
            int currentTokenId = view.getContext().getResources().getIdentifier("drawable/token_" + token, null, view
                    .getContext().getPackageName());
            currentToken.setImageResource(currentTokenId);
            currentTokens.addView(currentToken);
            Animation tokenAnimation = AnimationUtils.loadAnimation(this, R.anim.chaos_bag_animation);
            currentToken.startAnimation(tokenAnimation);
        }
        // If using the add button
        else {
            // Won't allow drawing more than 5 tokens at a time
            if (numberOfViews < 5 && numberOfViews < (chaosbag.size() + 1)) {
                // Removes the last token in the token stream
                if (tokens.getChildCount() == 6) {
                    tokens.removeViewAt(0);
                }
                // Adds the previously drawn token to the tokens views
                if (token >= 0) {
                    ImageView tokenView = new ImageView(view.getContext());
                    int savedHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources()
                            .getDisplayMetrics());
                    int savedWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources()
                            .getDisplayMetrics());
                    tokenView.setLayoutParams(new ViewGroup.LayoutParams(savedWidth, savedHeight));
                    int tokenId = view.getContext().getResources().getIdentifier("drawable/token_" + token, null, view
                            .getContext().getPackageName());
                    tokenView.setImageResource(tokenId);
                    tokens.addView(tokenView);
                }

                // Randomly pick the next token
                int chosen = new Random().nextInt(chaosbag.size());
                token = chaosbag.get(chosen);
                chaosbag.remove(chosen);

                // Adds the drawn token to the main token view
                ImageView currentToken = new ImageView(view.getContext());
                int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 75, getResources()
                        .getDisplayMetrics());
                int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 75, getResources()
                        .getDisplayMetrics());
                currentToken.setLayoutParams(new ViewGroup.LayoutParams(width, height));
                int currentTokenId = view.getContext().getResources().getIdentifier("drawable/token_" + token, null,
                        view
                                .getContext().getPackageName());
                currentToken.setImageResource(currentTokenId);
                currentTokens.addView(currentToken);
                Animation tokenAnimation = AnimationUtils.loadAnimation(this, R.anim.chaos_bag_animation);
                currentToken.startAnimation(tokenAnimation);
            }
        }
    }

    // Resets the bag by setting it up again
    private static void resetBag(Activity activity) {
        LinearLayout currentToken = activity.findViewById(R.id.current_token_layout);
        currentToken.removeAllViews();
        setupBag(activity);
    }

    private void setupScenarioCard(Activity activity) {
        LinearLayout scenarioLayout = activity.findViewById(R.id.scenario_card_layout);
        if (globalVariables.CurrentCampaign == 3 && globalVariables.CurrentScenario > 7) {
            scenarioLayout.setVisibility(GONE);
        }
        LinearLayout skullLayout = activity.findViewById(R.id.skull_layout);
        LinearLayout cultistLayout = activity.findViewById(R.id.cultist_layout);
        LinearLayout tabletLayout = activity.findViewById(R.id.tablet_layout);
        LinearLayout thingLayout = activity.findViewById(R.id.thing_layout);
        LinearLayout combinedLayout = activity.findViewById(R.id.combined_layout);
        TextView skull = activity.findViewById(R.id.skull_text);
        TextView cultist = activity.findViewById(R.id.cultist_text);
        TextView tablet = activity.findViewById(R.id.tablet_text);
        TextView thing = activity.findViewById(R.id.thing_text);
        TextView combined = activity.findViewById(R.id.combined_text);
        Typeface arnopro = Typeface.createFromAsset(getAssets(), "fonts/arnopro.otf");
        skull.setTypeface(arnopro);
        cultist.setTypeface(arnopro);
        tablet.setTypeface(arnopro);
        thing.setTypeface(arnopro);
        combined.setTypeface(arnopro);

        switch (globalVariables.CurrentCampaign) {
            case 1:
                switch (globalVariables.CurrentScenario) {
                    case 1:
                        thingLayout.setVisibility(GONE);
                        if (globalVariables.CurrentDifficulty == 0 || globalVariables.CurrentDifficulty == 1) {
                            skull.setText(R.string.gathering_skull_one);
                            cultist.setText(R.string.gathering_cultist_one);
                            tablet.setText(R.string.gathering_tablet_one);
                        } else if (globalVariables.CurrentDifficulty == 2 || globalVariables.CurrentDifficulty == 3) {
                            skull.setText(R.string.gathering_skull_two);
                            cultist.setText(R.string.gathering_cultist_two);
                            tablet.setText(R.string.gathering_tablet_two);
                        }
                        break;
                    case 2:
                        thingLayout.setVisibility(GONE);
                        if (globalVariables.CurrentDifficulty == 0 || globalVariables.CurrentDifficulty == 1) {
                            skull.setText(R.string.midnight_skull_one);
                            cultist.setText(R.string.midnight_cultist_one);
                            tablet.setText(R.string.midnight_tablet_one);
                        } else if (globalVariables.CurrentDifficulty == 2 || globalVariables.CurrentDifficulty == 3) {
                            skull.setText(R.string.midnight_skull_two);
                            cultist.setText(R.string.midnight_cultist_two);
                            tablet.setText(R.string.midnight_tablet_two);
                        }
                        break;
                    case 3:
                        if (globalVariables.CurrentDifficulty == 0 || globalVariables.CurrentDifficulty == 1) {
                            skull.setText(R.string.devourer_skull_one);
                            cultist.setText(R.string.devourer_cultist_one);
                            tablet.setText(R.string.devourer_tablet_one);
                            thing.setText(R.string.devourer_thing_one);
                        } else if (globalVariables.CurrentDifficulty == 2 || globalVariables.CurrentDifficulty == 3) {
                            skull.setText(R.string.devourer_skull_two);
                            cultist.setText(R.string.devourer_cultist_two);
                            tablet.setText(R.string.devourer_tablet_two);
                            thing.setText(R.string.devourer_thing_two);
                        }
                        break;
                }
                break;
            case 2:
                switch (globalVariables.CurrentScenario) {
                    case 1:
                        tabletLayout.setVisibility(GONE);
                        if (globalVariables.CurrentDifficulty == 0 || globalVariables.CurrentDifficulty == 1) {
                            skull.setText(R.string.extracurricular_skull_one);
                            cultist.setText(R.string.extracurricular_cultist_one);
                            thing.setText(R.string.extracurricular_thing_one);
                        } else if (globalVariables.CurrentDifficulty == 2 || globalVariables.CurrentDifficulty == 3) {
                            skull.setText(R.string.extracurricular_skull_two);
                            cultist.setText(R.string.extracurricular_cultist_two);
                            thing.setText(R.string.extracurricular_thing_two);
                        }
                        break;
                    case 2:
                        thingLayout.setVisibility(GONE);
                        if (globalVariables.CurrentDifficulty == 0 || globalVariables.CurrentDifficulty == 1) {
                            skull.setText(R.string.house_skull_one);
                            cultist.setText(R.string.house_cultist_one);
                            tablet.setText(R.string.house_tablet_one);
                        } else if (globalVariables.CurrentDifficulty == 2 || globalVariables.CurrentDifficulty == 3) {
                            skull.setText(R.string.house_skull_two);
                            cultist.setText(R.string.house_cultist_two);
                            tablet.setText(R.string.house_tablet_two);
                        }
                        break;
                    case 4:
                        if (globalVariables.CurrentDifficulty == 0 || globalVariables.CurrentDifficulty == 1) {
                            skull.setText(R.string.miskatonic_skull_one);
                            cultist.setText(R.string.miskatonic_cultist_one);
                            tablet.setText(R.string.miskatonic_tablet_one);
                            thing.setText(R.string.miskatonic_thing_one);
                        } else if (globalVariables.CurrentDifficulty == 2 || globalVariables.CurrentDifficulty == 3) {
                            skull.setText(R.string.miskatonic_skull_two);
                            cultist.setText(R.string.miskatonic_cultist_two);
                            tablet.setText(R.string.miskatonic_tablet_two);
                            thing.setText(R.string.miskatonic_thing_two);
                        }
                        break;
                    case 5:
                        if (globalVariables.CurrentDifficulty == 0 || globalVariables.CurrentDifficulty == 1) {
                            skull.setText(R.string.essex_skull_one);
                            cultist.setText(R.string.essex_cultist_one);
                            tablet.setText(R.string.essex_tablet_one);
                            thing.setText(R.string.essex_thing_one);
                        } else if (globalVariables.CurrentDifficulty == 2 || globalVariables.CurrentDifficulty == 3) {
                            skull.setText(R.string.essex_skull_two);
                            cultist.setText(R.string.essex_cultist_two);
                            tablet.setText(R.string.essex_tablet_two);
                            thing.setText(R.string.essex_thing_two);
                        }
                        break;
                    case 6:
                        if (globalVariables.CurrentDifficulty == 0 || globalVariables.CurrentDifficulty == 1) {
                            skull.setText(R.string.blood_skull_one);
                            cultist.setText(R.string.blood_cultist_one);
                            tablet.setText(R.string.blood_tablet_one);
                            thing.setText(R.string.blood_thing_one);
                        } else if (globalVariables.CurrentDifficulty == 2 || globalVariables.CurrentDifficulty == 3) {
                            skull.setText(R.string.blood_skull_two);
                            cultist.setText(R.string.blood_cultist_two);
                            tablet.setText(R.string.blood_tablet_two);
                            thing.setText(R.string.blood_thing_two);
                        }
                        break;
                    case 8:
                        if (globalVariables.CurrentDifficulty == 0 || globalVariables.CurrentDifficulty == 1) {
                            skull.setText(R.string.undimensioned_skull_one);
                            cultist.setText(R.string.undimensioned_cultist_one);
                            tablet.setText(R.string.undimensioned_tablet_one);
                            thing.setText(R.string.undimensioned_thing_one);
                        } else if (globalVariables.CurrentDifficulty == 2 || globalVariables.CurrentDifficulty == 3) {
                            skull.setText(R.string.undimensioned_skull_two);
                            cultist.setText(R.string.undimensioned_cultist_two);
                            tablet.setText(R.string.undimensioned_tablet_two);
                            thing.setText(R.string.undimensioned_thing_two);
                        }
                        break;
                    case 9:
                        if (globalVariables.CurrentDifficulty == 0 || globalVariables.CurrentDifficulty == 1) {
                            skull.setText(R.string.doom_skull_one);
                            cultist.setText(R.string.doom_cultist_one);
                            tablet.setText(R.string.doom_tablet_one);
                            thing.setText(R.string.doom_thing_one);
                        } else if (globalVariables.CurrentDifficulty == 2 || globalVariables.CurrentDifficulty == 3) {
                            skull.setText(R.string.doom_skull_two);
                            cultist.setText(R.string.doom_cultist_two);
                            tablet.setText(R.string.doom_tablet_two);
                            thing.setText(R.string.doom_thing_two);
                        }
                        break;
                    case 10:
                        if (globalVariables.CurrentDifficulty == 0 || globalVariables.CurrentDifficulty == 1) {
                            skull.setText(R.string.lost_skull_one);
                            cultist.setText(R.string.lost_cultist_one);
                            tablet.setText(R.string.lost_tablet_one);
                            thing.setText(R.string.lost_thing_one);
                        } else if (globalVariables.CurrentDifficulty == 2 || globalVariables.CurrentDifficulty == 3) {
                            skull.setText(R.string.lost_skull_two);
                            cultist.setText(R.string.lost_cultist_two);
                            tablet.setText(R.string.lost_tablet_two);
                            thing.setText(R.string.lost_thing_two);
                        }
                        break;
                }
                break;
            case 3:
                switch (globalVariables.CurrentScenario) {
                    case 1:
                        cultistLayout.setVisibility(GONE);
                        tabletLayout.setVisibility(GONE);
                        thingLayout.setVisibility(GONE);
                        combinedLayout.setVisibility(VISIBLE);
                        if (globalVariables.CurrentDifficulty == 0 || globalVariables.CurrentDifficulty == 1) {
                            skull.setText(R.string.curtain_skull_one);
                            combined.setText(R.string.curtain_combined_one);
                        } else if (globalVariables.CurrentDifficulty == 2 || globalVariables.CurrentDifficulty == 3) {
                            skull.setText(R.string.curtain_skull_two);
                            combined.setText(R.string.curtain_combined_two);
                        }
                        break;
                    case 2:
                        if (globalVariables.CurrentDifficulty == 0 || globalVariables.CurrentDifficulty == 1) {
                            skull.setText(R.string.king_skull_one);
                            cultist.setText(R.string.king_cultist_one);
                            tablet.setText(R.string.king_tablet_one);
                            thing.setText(R.string.king_thing_one);
                        } else if (globalVariables.CurrentDifficulty == 2 || globalVariables.CurrentDifficulty == 3) {
                            skull.setText(R.string.king_skull_two);
                            cultist.setText(R.string.king_cultist_two);
                            tablet.setText(R.string.king_tablet_two);
                            thing.setText(R.string.king_thing_two);
                        }
                        break;
                    case 4:
                        if (globalVariables.CurrentDifficulty == 0 || globalVariables.CurrentDifficulty == 1) {
                            skull.setText(R.string.echoes_skull_one);
                            cultist.setText(R.string.echoes_cultist_one);
                            tablet.setText(R.string.echoes_tablet_one);
                            thing.setText(R.string.echoes_thing_one);
                        } else if (globalVariables.CurrentDifficulty == 2 || globalVariables.CurrentDifficulty == 3) {
                            skull.setText(R.string.echoes_skull_two);
                            cultist.setText(R.string.echoes_cultist_two);
                            tablet.setText(R.string.echoes_tablet_two);
                            thing.setText(R.string.echoes_thing_two);
                        }
                        break;
                    case 5:
                        if (globalVariables.CurrentDifficulty == 0 || globalVariables.CurrentDifficulty == 1) {
                            skull.setText(R.string.unspeakable_skull_one);
                            cultist.setText(R.string.unspeakable_cultist_one);
                            tablet.setText(R.string.unspeakable_tablet_one);
                            thing.setText(R.string.unspeakable_thing_one);
                        } else if (globalVariables.CurrentDifficulty == 2 || globalVariables.CurrentDifficulty == 3) {
                            skull.setText(R.string.unspeakable_skull_two);
                            cultist.setText(R.string.unspeakable_cultist_two);
                            tablet.setText(R.string.unspeakable_tablet_two);
                            thing.setText(R.string.unspeakable_thing_two);
                        }
                        break;
                    case 7:
                        if (globalVariables.CurrentDifficulty == 0 || globalVariables.CurrentDifficulty == 1) {
                            skull.setText(R.string.phantom_skull_one);
                            cultist.setText(R.string.phantom_cultist_one);
                            tablet.setText(R.string.phantom_tablet_one);
                            thing.setText(R.string.phantom_thing_one);
                        } else if (globalVariables.CurrentDifficulty == 2 || globalVariables.CurrentDifficulty == 3) {
                            skull.setText(R.string.phantom_skull_two);
                            cultist.setText(R.string.phantom_cultist_two);
                            tablet.setText(R.string.phantom_tablet_two);
                            thing.setText(R.string.phantom_thing_two);
                        }
                        break;
                }
                break;
        }

        if (globalVariables.CurrentScenario > 100) {
            switch (globalVariables.CurrentScenario) {
                case 101:
                    if (globalVariables.CurrentDifficulty == 0 || globalVariables.CurrentDifficulty == 1) {
                        skull.setText(R.string.rougarou_skull_one);
                        cultist.setText(R.string.rougarou_cultist_one);
                        tablet.setText(R.string.rougarou_tablet_one);
                        thing.setText(R.string.rougarou_thing_one);
                    } else if (globalVariables.CurrentDifficulty == 2 || globalVariables.CurrentDifficulty == 3) {
                        skull.setText(R.string.rougarou_skull_two);
                        cultist.setText(R.string.rougarou_cultist_two);
                        tablet.setText(R.string.rougarou_tablet_two);
                        thing.setText(R.string.rougarou_thing_two);
                    }
                    break;
                case 102:
                    if (globalVariables.CurrentDifficulty == 0 || globalVariables.CurrentDifficulty == 1) {
                        skull.setText(R.string.carnevale_skull_one);
                        cultist.setText(R.string.carnevale_cultist_one);
                        tablet.setText(R.string.carnevale_tablet_one);
                        thing.setText(R.string.carnevale_thing_one);
                    } else if (globalVariables.CurrentDifficulty == 2 || globalVariables.CurrentDifficulty == 3) {
                        skull.setText(R.string.carnevale_skull_two);
                        cultist.setText(R.string.carnevale_cultist_two);
                        tablet.setText(R.string.carnevale_tablet_two);
                        thing.setText(R.string.carnevale_thing_two);
                    }
                    break;
            }
        }
    }
}
