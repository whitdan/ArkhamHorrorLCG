package com.whitdan.arkhamhorrorlcgcampaignguide.D_Misc;

import android.app.Activity;
import android.content.Intent;
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
        setupBag(this);

        // Set title and setup checkbox if necessary
        Typeface teutonic = Typeface.createFromAsset(getAssets(), "fonts/teutonic.ttf");
        Typeface arnopro = Typeface.createFromAsset(getAssets(), "fonts/arnopro.otf");
        TextView title = (TextView) findViewById(R.id.current_scenario_name);
        title.setTypeface(teutonic);
        CheckBox box = (CheckBox) findViewById(R.id.chaos_bag_checkbox);
        box.setTypeface(arnopro);
        final RadioGroup options = (RadioGroup) findViewById(R.id.chaos_bag_options);
        final RadioButton optionOne = (RadioButton) findViewById(R.id.chaos_bag_option_one);
        final RadioButton optionTwo = (RadioButton) findViewById(R.id.chaos_bag_option_two);
        final RadioButton optionThree = (RadioButton) findViewById(R.id.chaos_bag_option_three);
        optionOne.setTypeface(arnopro);
        optionTwo.setTypeface(arnopro);
        optionThree.setTypeface(arnopro);
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
                        box.setVisibility(VISIBLE);
                        box.setText(R.string.adam_lynch_harold_walsted);
                        if(globalVariables.AdamLynchHaroldWalsted == 1){
                            box.setChecked(true);
                        } else { box.setChecked(false); }
                        box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if(compoundButton.isChecked()){
                                    globalVariables.AdamLynchHaroldWalsted = 1;
                                } else {
                                    globalVariables.AdamLynchHaroldWalsted = 0;
                                }
                                setupBag(ChaosBagActivity.this);
                            }
                        });
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
                        if(globalVariables.Theatre > 0){
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
                                if(compoundButton.isChecked()){
                                    options.setVisibility(VISIBLE);
                                } else {
                                    options.setVisibility(GONE);
                                    options.clearCheck();
                                    globalVariables.Theatre = 0;
                                    setupBag(ChaosBagActivity.this);
                                }
                            }
                        });
                        switch(globalVariables.Theatre){
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
                                if(optionOne.isChecked()){
                                    globalVariables.Theatre = 1;
                                } else if (optionTwo.isChecked()){
                                    globalVariables.Theatre = 2;
                                } else if (optionThree.isChecked()){
                                    globalVariables.Theatre = 3;
                                }
                                setupBag(ChaosBagActivity.this);
                            }
                        });
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
        if(globalVariables.CurrentScenario > 100){
            switch(globalVariables.CurrentScenario){
                case 101:
                    title.setText(R.string.rougarou_scenario);
                    break;
                case 102:
                    title.setText(R.string.carnevale_scenario);
                    break;
            }
        }
        TextView chaosBag = (TextView) findViewById(R.id.chaos_bag);
        chaosBag.setTypeface(teutonic);
        TextView currentSetup = (TextView) findViewById(R.id.current_setup);
        currentSetup.setTypeface(teutonic);

        // Set fonts and listeners to radio buttons
        RadioGroup difficulty = (RadioGroup) findViewById(R.id.difficulty_selection);
        for (int i = 0; i < difficulty.getChildCount(); i++) {
            View view = difficulty.getChildAt(i);
            if (view instanceof RadioButton) {
                ((RadioButton) view).setTypeface(arnopro);
                view.setOnClickListener(new difficultySelectionListener());
            }
        }

        // Hide relevant radiobuttons on standalone scenarios
        RadioButton easy = (RadioButton) findViewById(R.id.easy_button);
        RadioButton standard = (RadioButton) findViewById(R.id.standard_button);
        RadioButton hard = (RadioButton) findViewById(R.id.hard_button);
        RadioButton expert = (RadioButton) findViewById(R.id.expert_button);
        if (globalVariables.CurrentCampaign == 999) {
            easy.setVisibility(GONE);
            expert.setVisibility(GONE);
        }
        switch(globalVariables.CurrentDifficulty){
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
        Button drawButton = (Button) findViewById(R.id.draw_token);
        drawButton.setTypeface(teutonic);
        drawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                draw = true;
                drawToken(view);
            }
        });
        LinearLayout currentTokenLayout = (LinearLayout) findViewById(R.id.current_token_layout);
        currentTokenLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                draw = true;
                drawToken(view);
            }
        });
        Button addButton = (Button) findViewById(R.id.add_token);
        addButton.setTypeface(teutonic);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                draw = false;
                drawToken(view);
            }
        });

        // Back button
        Button backButton = (Button) findViewById(R.id.back_button);
        backButton.setTypeface(teutonic);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button continueButton = (Button) findViewById(R.id.continue_button);
        continueButton.setTypeface(teutonic);
        if(globalVariables.CurrentCampaign == 999){
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
            setupBag(ChaosBagActivity.this);

            // Clear all current token views
            LinearLayout tokens = (LinearLayout) findViewById(R.id.token_layout);
            LinearLayout currentTokens = (LinearLayout) findViewById(R.id.current_token_layout);
            tokens.removeAllViews();
            currentTokens.removeAllViews();
        }
    }

    // Sets up the chaos bag with all of the relevant tokens
    private static void setupBag(Activity activity) {
        // Setup chaos bag by adding relevant tokens
        chaosbag = new ArrayList<>();
        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < basebag()[i]; j++) {
                chaosbag.add(i);
            }
        }

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
                switch(globalVariables.Theatre){
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
                break;
        }

        // Get relevant views
        Collections.sort(chaosbag);
        LinearLayout currentChaosBagOne = (LinearLayout) activity.findViewById(R.id.current_chaos_bag_one);
        LinearLayout currentChaosBagTwo = (LinearLayout) activity.findViewById(R.id.current_chaos_bag_two);
        LinearLayout currentChaosBagThree = (LinearLayout) activity.findViewById(R.id.current_chaos_bag_three);
        currentChaosBagOne.removeAllViews();
        currentChaosBagTwo.removeAllViews();
        currentChaosBagThree.removeAllViews();

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
            } else {
                currentChaosBagThree.addView(tokenView);
            }
        }
    }

    // Base difficulties
    private static int[] basebag() {
        int[] bag = new int[17];
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
        return bag;
    }

    // Method activated on presing of a button to draw token
    private void drawToken(View view) {
        // Get views
        LinearLayout tokens = (LinearLayout) findViewById(R.id.token_layout);
        LinearLayout currentTokens = (LinearLayout) findViewById(R.id.current_token_layout);
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
            if (numberOfViews < 5) {
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
        LinearLayout currentToken = (LinearLayout) activity.findViewById(R.id.current_token_layout);
        currentToken.removeAllViews();
        setupBag(activity);
    }
}
