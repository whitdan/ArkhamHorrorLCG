package com.whitdan.arkhamhorrorlcgcampaignguide.B_CampaignSetup;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.whitdan.arkhamhorrorlcgcampaignguide.A_Menus.MainMenuActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.R;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.GlobalVariables;

import java.util.ArrayList;
import java.util.Collections;

import static android.view.View.GONE;

/*
    ChaosBagSetupActivity - Allows the selection of a difficulty and shows the makeup of the chaos bag relevant to
                            that difficulty
 */

public class ChaosBagSetupActivity extends AppCompatActivity {

    GlobalVariables globalVariables;
    ArrayList<Integer> chaosbag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // If app is reopening after the process is killed, kick back to the main menu (stops the activity from
        // showing up unpopulated)
        if (savedInstanceState != null) {
            Intent intent = new Intent(ChaosBagSetupActivity.this, MainMenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_activity_chaos_bag_setup);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        globalVariables = (GlobalVariables) this.getApplication();

        // Set campaign title and fonts
        Typeface teutonic = Typeface.createFromAsset(getAssets(), "fonts/teutonic.ttf");
        Typeface arnopro = Typeface.createFromAsset(getAssets(), "fonts/arnopro.otf");
        TextView title = (TextView) findViewById(R.id.campaign_name);
        title.setTypeface(teutonic);
        switch (globalVariables.CurrentCampaign) {
            case 1:
                title.setText(R.string.night_campaign);
                break;
            case 2:
                title.setText(R.string.dunwich_campaign);
                break;
            case 3:
                title.setText(R.string.carcosa_campaign);
                break;
        }
        TextView chaosBag = (TextView) findViewById(R.id.chaos_bag);
        chaosBag.setTypeface(teutonic);

        // Set fonts and listeners to radio buttons
        RadioGroup difficulty = (RadioGroup) findViewById(R.id.difficulty_selection);
        for (int i = 0; i < difficulty.getChildCount(); i++) {
            View view = difficulty.getChildAt(i);
            if (view instanceof RadioButton) {
                ((RadioButton) view).setTypeface(arnopro);
                view.setOnClickListener(new difficultySelectionListener());

                if (i == globalVariables.CurrentDifficulty) {
                    ((RadioButton) view).setChecked(true);
                    setupBag();
                }
            }
        }

        // Hide relevant radiobuttons on standalone scenarios
        RadioButton easy = (RadioButton) findViewById(R.id.easy_button);
        RadioButton expert = (RadioButton) findViewById(R.id.expert_button);
        if (globalVariables.CurrentCampaign == 999) {
            easy.setVisibility(GONE);
            expert.setVisibility(GONE);
        }

        // Back button
        Button backButton = (Button) findViewById(R.id.back_button);
        backButton.setTypeface(teutonic);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
            setupBag();
        }
    }

    private void setupBag() {
        // Setup chaos bag by adding relevant tokens
        chaosbag = new ArrayList<>();
        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < basebag()[i]; j++) {
                chaosbag.add(i);
            }
        }

        // Get relevant views
        Collections.sort(chaosbag);
        LinearLayout currentChaosBagOne = (LinearLayout) findViewById(R.id.current_chaos_bag_one);
        LinearLayout currentChaosBagTwo = (LinearLayout) findViewById(R.id.current_chaos_bag_two);
        LinearLayout currentChaosBagThree = (LinearLayout) findViewById(R.id.current_chaos_bag_three);
        LinearLayout currentChaosBagFour = (LinearLayout) findViewById(R.id.current_chaos_bag_four);
        LinearLayout currentChaosBagFive = (LinearLayout) findViewById(R.id.current_chaos_bag_five);
        LinearLayout currentChaosBagSix = (LinearLayout) findViewById(R.id.current_chaos_bag_six);
        currentChaosBagOne.removeAllViews();
        currentChaosBagTwo.removeAllViews();
        currentChaosBagThree.removeAllViews();
        currentChaosBagFour.removeAllViews();
        currentChaosBagFive.removeAllViews();
        currentChaosBagSix.removeAllViews();

        // Adds views for every chaos token to the relevant layouts
        for (int i = 0; i < chaosbag.size(); i++) {
            int currentToken = chaosbag.get(i);
            ImageView tokenView = new ImageView(this);
            int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources()
                    .getDisplayMetrics());
            int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources()
                    .getDisplayMetrics());
            tokenView.setLayoutParams(new ViewGroup.LayoutParams(width, height));
            int tokenId = getResources().getIdentifier("drawable/token_" + currentToken, null, getPackageName());
            tokenView.setImageResource(tokenId);

            if (currentChaosBagOne.getChildCount() < 5) {
                currentChaosBagOne.addView(tokenView);
            } else if (currentChaosBagTwo.getChildCount() < 5) {
                currentChaosBagTwo.addView(tokenView);
            } else if (currentChaosBagThree.getChildCount() < 5) {
                currentChaosBagThree.addView(tokenView);
            } else if (currentChaosBagFour.getChildCount() < 5) {
                currentChaosBagFour.addView(tokenView);
            } else if (currentChaosBagFive.getChildCount() < 5) {
                currentChaosBagFive.addView(tokenView);
            } else {
                currentChaosBagSix.addView(tokenView);
            }
        }
    }

    // Difficulties
    private int[] basebag() {
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
}
