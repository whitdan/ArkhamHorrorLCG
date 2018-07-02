package com.whitdan.arkhamhorrorlcgcampaignguide.C_Scenario;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.whitdan.arkhamhorrorlcgcampaignguide.A_Menus.MainMenuActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.D_Misc.CampaignLogActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.D_Misc.ChaosBagActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.R;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.GlobalVariables;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.Investigator;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static java.lang.Boolean.TRUE;

public class ScenarioSetupActivity extends AppCompatActivity {

    static GlobalVariables globalVariables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // If app is reopening after the process is killed, kick back to the main menu (stops the activity from
        // showing up unpopulated)
        if (savedInstanceState != null) {
            Intent intent = new Intent(ScenarioSetupActivity.this, MainMenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_activity_scenario_setup);
        globalVariables = (GlobalVariables) this.getApplication();

        Typeface teutonic = Typeface.createFromAsset(getAssets(), "fonts/teutonic.ttf");
        Typeface arnopro = Typeface.createFromAsset(getAssets(), "fonts/arnopro.otf");

        TextView title = findViewById(R.id.current_scenario_name);
        title.setTypeface(teutonic);
        globalVariables.setTitle(title);
        TextView subTitle = findViewById(R.id.scenario_setup);
        subTitle.setTypeface(teutonic);
        TextView setsHeading = findViewById(R.id.sets_heading);
        setsHeading.setTypeface(teutonic);
        TextView locationsHeading = findViewById(R.id.locations_heading);
        locationsHeading.setTypeface(teutonic);
        TextView setAsideHeading = findViewById(R.id.set_aside_heading);
        setAsideHeading.setTypeface(teutonic);
        TextView additionalHeading = findViewById(R.id.additional_heading);
        additionalHeading.setTypeface(teutonic);
        TextView rulesHeading = findViewById(R.id.rules_heading);
        rulesHeading.setTypeface(teutonic);
        LinearLayout rulesLayout = findViewById(R.id.rules_layout);

        TextView sets = findViewById(R.id.sets_one);
        TextView setsTwo = findViewById(R.id.sets_two);
        TextView setsThree = findViewById(R.id.sets_three);
        ImageView setsImage = findViewById(R.id.sets_one_image);
        ImageView setsTwoImage = findViewById(R.id.sets_two_image);
        TextView locations = findViewById(R.id.locations);
        ImageView locationPlacement = findViewById(R.id.locations_image);
        TextView setAside = findViewById(R.id.set_aside);
        TextView setAsideTwo = findViewById(R.id.set_aside_two);
        ImageView setAsideImage = findViewById(R.id.set_aside_image);
        TextView additional = findViewById(R.id.additional);
        TextView additionalTwo = findViewById(R.id.additional_two);
        TextView rules = findViewById(R.id.rules);

        sets.setTypeface(arnopro);
        setsTwo.setTypeface(arnopro);
        setsThree.setTypeface(arnopro);
        locations.setTypeface(arnopro);
        setAside.setTypeface(arnopro);
        setAsideTwo.setTypeface(arnopro);
        additional.setTypeface(arnopro);
        additionalTwo.setTypeface(arnopro);
        rules.setTypeface(arnopro);

        // Set instructions
        switch (globalVariables.CurrentCampaign) {
            case 1:
                switch (globalVariables.CurrentScenario) {
                    case 1:
                        sets.setText(R.string.gathering_sets);
                        setsImage.setImageResource(R.drawable.gathering_sets);
                        locations.setText(R.string.gathering_locations);
                        setAside.setText(R.string.gathering_set_aside);
                        additional.setText(R.string.no_additional);
                        break;
                    case 2:
                        sets.setText(R.string.midnight_sets);
                        setsImage.setImageResource(R.drawable.midnight_sets);
                        setAside.setText(R.string.midnight_set_aside);
                        setAsideImage.setImageResource(R.drawable.midnight_set_aside);
                        setAsideImage.setVisibility(VISIBLE);
                        // Check if house is standing
                        if (globalVariables.HouseStanding == 1) {
                            locations.setText(R.string.midnight_locations);
                        } else {
                            locations.setText(R.string.midnight_locations_no_house);
                        }
                        locationPlacement.setVisibility(VISIBLE);
                        locationPlacement.setImageResource(R.drawable.midnight_locations);
                        // StringBuilder for the additional instructions
                        StringBuilder midnightAdditionalBuilder = new StringBuilder();
                        // Check how many players
                        switch (globalVariables.Investigators.size()) {
                            case 1:
                                break;
                            case 2:
                                midnightAdditionalBuilder.append(getString(R.string.midnight_additional_two));
                                break;
                            case 3:
                                midnightAdditionalBuilder.append(getString(R.string.midnight_additional_three));
                                break;
                            case 4:
                                midnightAdditionalBuilder.append(getString(R.string.midnight_additional_four));
                                break;
                        }
                        // Check if Ghoul Priest is alive
                        if (globalVariables.GhoulPriest == 1) {
                            midnightAdditionalBuilder.append(getString(R.string.ghoul_priest_additional));
                        }
                        if (midnightAdditionalBuilder.length() == 0) {
                            midnightAdditionalBuilder.append(getString(R.string.no_additional));
                        }
                        // Show additional instructions
                        String midnightAdditional = midnightAdditionalBuilder.toString();
                        additional.setText(midnightAdditional.trim());
                        break;
                    case 3:
                        sets.setText(R.string.devourer_sets);
                        setsImage.setImageResource(R.drawable.devourer_sets);
                        setsTwo.setText(R.string.devourer_sets_two);
                        setsTwoImage.setImageResource(R.drawable.devourer_sets_two);
                        setsTwo.setVisibility(VISIBLE);
                        setsTwoImage.setVisibility(VISIBLE);
                        setAside.setText(R.string.devourer_set_aside);
                        locations.setText(R.string.devourer_locations);
                        // StringBuilder for the additional instructions
                        StringBuilder devouringAdditionalBuilder = new StringBuilder();
                        devouringAdditionalBuilder.append(getString(R.string.devourer_additional));
                        // Check how many cultists were interrogated
                        switch (globalVariables.CultistsInterrogated) {
                            case 6:
                                break;
                            case 5:
                            case 4:
                                devouringAdditionalBuilder.append(getString(R.string.devourer_cultists_one));
                                break;
                            case 3:
                            case 2:
                                devouringAdditionalBuilder.append(getString(R.string.devourer_cultists_two));
                                break;
                            case 1:
                            case 0:
                                devouringAdditionalBuilder.append(getString(R.string.devourer_cultists_three));
                                break;
                        }
                        // Check if it is past midnight
                        if (globalVariables.PastMidnight == 1) {
                            devouringAdditionalBuilder.append(getString(R.string.devourer_additional_midnight));
                        }
                        // Check if the Ghoul Priest is alive
                        if (globalVariables.GhoulPriest == 1) {
                            devouringAdditionalBuilder.append(getString(R.string.ghoul_priest_additional));
                        }
                        if (devouringAdditionalBuilder.length() == 0) {
                            devouringAdditionalBuilder.append(getString(R.string.no_additional));
                        }
                        // Show the additional text
                        String devouringAdditional = devouringAdditionalBuilder.toString();
                        additional.setText(devouringAdditional.trim());
                        break;
                }
                break;
            case 2:
                switch (globalVariables.CurrentScenario) {
                    case 1:
                        sets.setText(R.string.extracurricular_sets);
                        setsImage.setImageResource(R.drawable.extracurricular_sets);
                        // Larger image so set layoutparams
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams
                                .WRAP_CONTENT,
                                (int) getResources().getDimension(R.dimen.large_image_height));
                        params.gravity = Gravity.CENTER;
                        params.topMargin = (int) getResources().getDimension(R.dimen.activity_vertical_margin);
                        params.bottomMargin = (int) getResources().getDimension(R.dimen.activity_vertical_margin);
                        setsImage.setLayoutParams(params);
                        setsImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        setsImage.setAdjustViewBounds(TRUE);
                        if (globalVariables.FirstScenario == 1) {
                            setAside.setText(R.string.extracurricular_set_aside_one);
                        } else {
                            setAside.setText(R.string.extracurricular_set_aside_two);
                        }
                        locations.setText(R.string.extracurricular_locations);
                        locationPlacement.setVisibility(VISIBLE);
                        locationPlacement.setImageResource(R.drawable.extracurricular_locations);
                        additional.setText(R.string.no_additional);
                        break;
                    case 2:
                        sets.setText(R.string.house_sets);
                        setsImage.setImageResource(R.drawable.house_sets);
                        setAside.setText(R.string.house_set_aside);
                        setAsideImage.setVisibility(VISIBLE);
                        setAsideImage.setImageResource(R.drawable.house_set_aside);
                        setAsideTwo.setVisibility(VISIBLE);
                        setAsideTwo.setText(R.string.house_set_aside_two);
                        locations.setText(R.string.house_locations);
                        additional.setText(R.string.house_additional);
                        break;
                    case 4:
                        sets.setText(R.string.miskatonic_sets);
                        setsImage.setImageResource(R.drawable.miskatonic_sets);
                        setAside.setText(R.string.miskatonic_set_aside);
                        locations.setText(R.string.miskatonic_locations);
                        additional.setText(R.string.miskatonic_additional);
                        rulesLayout.setVisibility(VISIBLE);
                        rulesHeading.setText(R.string.miskatonic_rules_heading);
                        rules.setText(R.string.miskatonic_rules);
                        break;
                    case 5:
                        sets.setText(R.string.essex_sets);
                        setsImage.setImageResource(R.drawable.essex_sets);
                        setAside.setText(R.string.essex_set_aside);
                        locations.setText(R.string.essex_locations);
                        additional.setText(R.string.essex_additional);
                        rulesLayout.setVisibility(VISIBLE);
                        rulesHeading.setText(R.string.essex_rules_heading);
                        rules.setText(R.string.essex_rules);
                        break;
                    case 6:
                        sets.setText(R.string.blood_sets);
                        setsImage.setImageResource(R.drawable.blood_sets);
                        if (globalVariables.ObannionGang == 0) {
                            setsTwo.setVisibility(VISIBLE);
                            setsTwoImage.setVisibility(VISIBLE);
                            setsTwoImage.getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;
                            setsTwo.setText(R.string.blood_sets_two);
                            setsTwoImage.setImageResource(R.drawable.blood_sets_two);
                        }
                        locations.setText(R.string.blood_locations);
                        locationPlacement.setVisibility(VISIBLE);
                        locationPlacement.setImageResource(R.drawable.blood_locations);
                        setAside.setText(R.string.blood_set_aside);
                        StringBuilder bloodAdditionalBuilder = new StringBuilder();
                        bloodAdditionalBuilder.append(getString(R.string.blood_additional));
                        bloodAdditionalBuilder.append(getString(R.string.blood_additional_sacrifice_one));
                        bloodAdditionalBuilder.append(" ");
                        if (globalVariables.HenryArmitage == 0) {
                            bloodAdditionalBuilder.append(getString(R.string.blood_additional_sacrifice_armitage));
                            bloodAdditionalBuilder.append(" ");
                        }
                        if (globalVariables.FrancisMorgan == 0) {
                            bloodAdditionalBuilder.append(getString(R.string.blood_additional_sacrifice_morgan));
                            bloodAdditionalBuilder.append(" ");
                        }
                        if (globalVariables.WarrenRice == 0) {
                            bloodAdditionalBuilder.append(getString(R.string.blood_additional_sacrifice_rice));
                            bloodAdditionalBuilder.append(" ");
                        }
                        bloodAdditionalBuilder.append(getString(R.string.blood_additional_sacrifice_two));
                        if (globalVariables.InvestigatorsDelayed == 1) {
                            bloodAdditionalBuilder.append(getString(R.string.blood_additional_two));
                        }
                        String bloodAdditional = bloodAdditionalBuilder.toString().trim();
                        bloodAdditional += "\n";
                        additional.setText(bloodAdditional);
                        rulesLayout.setVisibility(VISIBLE);
                        rulesHeading.setText(R.string.blood_rules_heading);
                        rules.setText(R.string.blood_rules);
                        break;
                    case 8:
                        sets.setText(R.string.undimensioned_sets);
                        setsImage.setImageResource(R.drawable.undimensioned_sets);
                        setAside.setText(R.string.undimensioned_set_aside);
                        locations.setText(R.string.undimensioned_locations);
                        locationPlacement.setVisibility(VISIBLE);
                        locationPlacement.setImageResource(R.drawable.undimensioned_locations);
                        StringBuilder undimensionedAdditionalBuilder = new StringBuilder();
                        int sacrificed = 0;
                        if (globalVariables.HenryArmitage == 2) {
                            sacrificed++;
                        }
                        if (globalVariables.WarrenRice == 2) {
                            sacrificed++;
                        }
                        if (globalVariables.FrancisMorgan == 2) {
                            sacrificed++;
                        }
                        if (globalVariables.ZebulonWhateley == 1) {
                            sacrificed++;
                        }
                        if (globalVariables.EarlSawyer == 1) {
                            sacrificed++;
                        }
                        if (globalVariables.AllySacrificed == 1) {
                            sacrificed++;
                        }
                        switch (sacrificed) {
                            case 4:
                            case 5:
                            case 6:
                                undimensionedAdditionalBuilder.append(getString(R.string
                                        .undimensioned_additional_brood_one));
                                break;
                            case 3:
                                undimensionedAdditionalBuilder.append(getString(R.string
                                        .undimensioned_additional_brood_two));
                                break;
                            case 2:
                                undimensionedAdditionalBuilder.append(getString(R.string
                                        .undimensioned_additional_brood_three));
                                break;
                            case 1:
                            case 0:
                                undimensionedAdditionalBuilder.append(getString(R.string
                                        .undimensioned_additional_brood_four));
                                break;
                        }
                        if (globalVariables.FrancisMorgan == 3 || globalVariables.HenryArmitage == 3 ||
                                globalVariables.WarrenRice == 3) {
                            undimensionedAdditionalBuilder.append(getString(R.string
                                    .undimensioned_additional_powder));
                        }
                        undimensionedAdditionalBuilder.append(getString(R.string.undimensioned_additional));
                        String undimensionedAdditional = undimensionedAdditionalBuilder.toString().trim();
                        undimensionedAdditional += "\n";
                        additional.setText(undimensionedAdditional);
                        rulesLayout.setVisibility(VISIBLE);
                        rulesHeading.setText(R.string.undimensioned_rules_heading);
                        rules.setText(R.string.undimensioned_rules);
                        break;
                    case 9:
                        if (globalVariables.SilasBishop == 1) {
                            sets.setText(R.string.doom_sets_two);
                            setsImage.setImageResource(R.drawable.doom_sets_two);
                        } else {
                            sets.setText(R.string.doom_sets);
                            setsImage.setImageResource(R.drawable.doom_sets);
                        }
                        setAside.setText(R.string.doom_set_aside);
                        locations.setText(R.string.doom_locations);
                        additionalTwo.setVisibility(VISIBLE);
                        additionalTwo.setText(R.string.doom_additional);
                        StringBuilder doomAdditionalBuilder = new StringBuilder();
                        if (globalVariables.SilasBishop == 2) {
                            doomAdditionalBuilder.append(getString(R.string.doom_additional_act_one));
                        } else if (globalVariables.Necronomicon == 0 || globalVariables.Necronomicon ==
                                3) {
                            doomAdditionalBuilder.append(getString(R.string.doom_additional_act_two));
                        } else {
                            doomAdditionalBuilder.append(getString(R.string.doom_additional_act_three));
                        }
                        int investigators = 0;
                        for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                            if (globalVariables.Investigators.get(i).Status == 1) {
                                investigators++;
                            }
                        }
                        if (globalVariables.ObannionGang == 1) {
                            if (investigators < 3) {
                                doomAdditionalBuilder.append(getString(R.string.doom_additional_obannion_one));
                            } else {
                                doomAdditionalBuilder.append(getString(R.string.doom_additional_obannion_two));
                            }
                        }
                        if (globalVariables.BroodsEscaped > 0) {
                            doomAdditionalBuilder.append(getString(R.string.add_token));
                            doomAdditionalBuilder.append(" ");
                            doomAdditionalBuilder.append(Integer.toString(globalVariables.BroodsEscaped));
                            doomAdditionalBuilder.append(" ");
                            doomAdditionalBuilder.append(getString(R.string.doom_additional_doom));
                        }
                        if (globalVariables.SilasBishop == 1) {
                            doomAdditionalBuilder.append(getString(R.string.doom_additional_bishop));
                        }
                        String doomAdditional = doomAdditionalBuilder.toString();
                        additional.setText(doomAdditional.trim());
                        break;
                    case 10:
                        sets.setText(R.string.lost_sets);
                        setsImage.setImageResource(R.drawable.lost_sets);
                        setAside.setText(R.string.lost_set_aside);
                        locations.setText(R.string.lost_locations);
                        String lostAdditional = getResources().getString(R.string.no_additional) + "\n";
                        additional.setText(lostAdditional);
                        rulesLayout.setVisibility(VISIBLE);
                        rulesHeading.setText(R.string.lost_rules_heading);
                        rules.setText(R.string.lost_rules);
                        break;
                }
                break;
            case 3:
                switch (globalVariables.CurrentScenario) {
                    case 1:
                        sets.setText(R.string.curtain_sets);
                        setsImage.setImageResource(R.drawable.curtain_sets);
                        boolean lola = false;
                        for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                            if (globalVariables.Investigators.get(i).Name == Investigator.LOLA_HAYES) {
                                lola = true;
                            }
                        }
                        if (lola) {
                            locations.setText(R.string.curtain_locations_lola);
                        } else {
                            locations.setText(R.string.curtain_locations);
                        }
                        locationPlacement.setVisibility(VISIBLE);
                        locationPlacement.setImageResource(R.drawable.curtain_locations);
                        setAside.setText(R.string.curtain_set_aside);
                        additional.setText(R.string.no_additional);
                        break;
                    case 2:
                        sets.setText(R.string.king_sets);
                        setsImage.setImageResource(R.drawable.king_sets);
                        locations.setText(R.string.king_locations);
                        locationPlacement.setVisibility(VISIBLE);
                        locationPlacement.setImageResource(R.drawable.king_locations);
                        setAside.setText(R.string.king_set_aside);
                        switch (globalVariables.Investigators.size()) {
                            case 1:
                                additional.setText(R.string.king_additional_one);
                                break;
                            case 2:
                                additional.setText(R.string.king_additional_two);
                                break;
                            case 3:
                                additional.setText(R.string.king_additional_three);
                                break;
                            case 4:
                                additional.setText(R.string.king_additional_four);
                                break;
                        }
                        break;
                    case 4:
                        sets.setText(R.string.echoes_sets);
                        setsImage.setImageResource(R.drawable.echoes_sets);
                        locations.setText(R.string.echoes_locations);
                        setAside.setText(R.string.echoes_set_aside);
                        // StringBuilder for the additional instructions
                        StringBuilder echoesAdditional = new StringBuilder();
                        switch (globalVariables.Investigators.size()) {
                            case 1:
                                break;
                            case 2:
                                echoesAdditional.append(getString(R.string.echoes_additional_two_players));
                                break;
                            case 3:
                                echoesAdditional.append(getString(R.string.echoes_additional_three_players));
                                break;
                            case 4:
                                echoesAdditional.append(getString(R.string.echoes_additional_four_players));
                                break;
                        }
                        if (globalVariables.Sebastien == 1 || globalVariables.Sebastien == 4) {
                            echoesAdditional.append(getString(R.string.echoes_additional_sebastien));
                        }
                        if (globalVariables.Party == 2) {
                            echoesAdditional.append(getString(R.string.echoes_additional_dinner));
                        }
                        if (echoesAdditional.length() == 0) {
                            echoesAdditional.append(getString(R.string.no_additional));
                        }
                        // Show the additional text
                        String echoesAdditionalString = echoesAdditional.toString();
                        additional.setText(echoesAdditionalString.trim());
                        break;
                    case 5:
                        sets.setText(R.string.unspeakable_sets);
                        setsImage.setImageResource(R.drawable.unspeakable_sets);
                        locations.setText(R.string.unspeakable_locations);
                        locationPlacement.setVisibility(VISIBLE);
                        locationPlacement.setImageResource(R.drawable.unspeakable_locations);
                        setAside.setText(R.string.unspeakable_set_aside);
                        if (globalVariables.Onyx == 1) {
                            if (globalVariables.Constance == 1 || globalVariables.Constance == 4) {
                                additional.setText(R.string.unspeakable_additional_one_constance);
                            } else {
                                additional.setText(R.string.unspeakable_additional_one);
                            }
                        } else {
                            if (globalVariables.Constance == 1 || globalVariables.Constance == 4) {
                                additional.setText(R.string.unspeakable_additional_two_constance);
                            } else {
                                additional.setText(R.string.unspeakable_additional_two);
                            }
                        }
                        break;
                    case 7:
                        sets.setText(R.string.phantom_sets);
                        setsImage.setImageResource(R.drawable.phantom_sets);
                        if (globalVariables.Jordan == 1 || globalVariables.Jordan == 4) {
                            locations.setText(R.string.phantom_locations_two);
                        } else {
                            locations.setText(R.string.phantom_locations_one);
                        }
                        locationPlacement.setVisibility(VISIBLE);
                        locationPlacement.setImageResource(R.drawable.phantom_locations);
                        setAside.setText(R.string.phantom_set_aside);
                        if (globalVariables.Doubt >= globalVariables.Conviction) {
                            if (globalVariables.Jordan == 1 || globalVariables.Jordan == 4) {
                                additional.setText(R.string.phantom_additional_one_jordan);
                            } else {
                                additional.setText(R.string.phantom_additional_one);
                            }
                        } else if (globalVariables.Conviction > globalVariables.Doubt) {
                            if (globalVariables.Jordan == 1 || globalVariables.Jordan == 4) {
                                additional.setText(R.string.phantom_additional_two_jordan);
                            } else {
                                additional.setText(R.string.phantom_additional_two);
                            }
                        }
                        break;
                    case 8:
                        sets.setText(R.string.pallid_sets);
                        setsImage.setImageResource(R.drawable.pallid_sets);
                        if (globalVariables.Nigel == 0 || globalVariables.Nigel == 3) {
                            locations.setText(R.string.pallid_locations_one);
                        } else {
                            locations.setText(R.string.pallid_locations_two);
                        }
                        setAside.setText(R.string.pallid_set_aside);
                        if (globalVariables.Ishimaru == 1 || globalVariables.Ishimaru == 4) {
                            additional.setText(R.string.pallid_additional);
                        } else {
                            String pallidAdditional = getResources().getString(R.string.no_additional);
                            pallidAdditional += "\n";
                            additional.setText(pallidAdditional);
                        }
                        rulesLayout.setVisibility(VISIBLE);
                        rulesHeading.setText(R.string.pallid_rules_heading);
                        rules.setText(R.string.pallid_rules);
                        break;
                    case 9:
                        sets.setText(R.string.black_stars_sets);
                        setsImage.setImageResource(R.drawable.black_stars_sets);
                        setsTwo.setVisibility(VISIBLE);
                        setsTwo.setText(R.string.black_stars_sets_two);
                        setsTwoImage.setVisibility(VISIBLE);
                        setsTwoImage.setImageResource(R.drawable.black_stars_sets_two);
                        setsThree.setVisibility(VISIBLE);
                        setsThree.setText(R.string.black_stars_sets_three);
                        locations.setText(R.string.black_stars_locations);
                        locationPlacement.setVisibility(VISIBLE);
                        locationPlacement.setImageResource(R.drawable.black_stars_locations);
                        setAside.setText(R.string.black_stars_set_aside);
                        if (globalVariables.Ashleigh == 1 || globalVariables.Ashleigh == 4) {
                            additionalTwo.setVisibility(VISIBLE);
                            additionalTwo.setText(R.string.black_stars_additional);
                            additional.setText(R.string.black_stars_additional_ashleigh);
                        } else {
                            additional.setText(R.string.black_stars_additional);
                        }
                        rulesLayout.setVisibility(VISIBLE);
                        rulesHeading.setText(R.string.black_stars_rules_heading);
                        rules.setText(R.string.black_stars_rules);
                        break;
                    case 10:
                        sets.setText(R.string.dim_sets);
                        setsImage.setImageResource(R.drawable.dim_sets);
                        if (globalVariables.Path == 1) {
                            locations.setText(R.string.dim_locations_below);
                        } else if (globalVariables.Path == 2) {
                            locations.setText(R.string.dim_locations_above);
                        }
                        locationPlacement.setVisibility(VISIBLE);
                        locationPlacement.setImageResource(R.drawable.dim_locations);
                        setAside.setText(R.string.dim_set_aside);
                        StringBuilder dimAdditionalBuilder = new StringBuilder();
                        if ((globalVariables.Doubt + globalVariables.Conviction) <= 5) {
                            dimAdditionalBuilder.append(getString(R.string.dim_act_one));
                        } else if (globalVariables.Doubt > globalVariables.Conviction) {
                            dimAdditionalBuilder.append(getString(R.string.dim_act_two));
                        } else if (globalVariables.Conviction > globalVariables.Doubt) {
                            dimAdditionalBuilder.append(getString(R.string.dim_act_three));
                        } else if (globalVariables.Doubt == globalVariables.Conviction) {
                            dimAdditionalBuilder.append(getString(R.string.dim_act_four));
                        }
                        if (globalVariables.ChasingStranger <= 2) {
                            dimAdditionalBuilder.append(getString(R.string.dim_doom_one));
                        } else if (globalVariables.ChasingStranger >= 3 && globalVariables.ChasingStranger <= 5) {
                            dimAdditionalBuilder.append(getString(R.string.dim_doom_two));
                        } else if (globalVariables.ChasingStranger >= 6 && globalVariables.ChasingStranger <= 8) {
                            dimAdditionalBuilder.append(getString(R.string.dim_doom_three));
                        }
                        dimAdditionalBuilder.append(getString(R.string.dim_additional));
                        String dimAdditional = dimAdditionalBuilder.toString();
                        additional.setText(dimAdditional);
                        rulesLayout.setVisibility(VISIBLE);
                        rulesHeading.setText(R.string.dim_rules_heading);
                        rules.setText(R.string.dim_rules);
                        break;
                }
                break;
            case 4:
                switch (globalVariables.CurrentScenario) {
                    case 1:
                        sets.setText(R.string.untamed_sets);
                        setsImage.setImageResource(R.drawable.untamed_sets);
                        locations.setText(R.string.untamed_locations);
                        setAside.setText(R.string.untamed_set_aside);
                        setAsideImage.setVisibility(VISIBLE);
                        setAsideImage.setImageResource(R.drawable.untamed_set_aside);
                        additional.setText(R.string.untamed_additional);
                        boolean untamedLeader = false;
                        for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                            switch (globalVariables.Investigators.get(i).Name) {
                                case Investigator.URSULA_DOWNS:
                                case Investigator.LEO_ANDERSON:
                                    untamedLeader = true;
                                    break;
                            }
                        }
                        if (untamedLeader) {
                            additionalTwo.setVisibility(VISIBLE);
                            additionalTwo.setText(R.string.untamed_additional_lead);
                        }
                        break;
                    case 6:
                        sets.setText(R.string.eztli_sets);
                        setsImage.setImageResource(R.drawable.eztli_sets);
                        locations.setText(R.string.eztli_locations);
                        setAside.setText(R.string.eztli_set_aside);
                        additional.setText(R.string.eztli_additional);
                        boolean eztliLeader = false;
                        for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                            switch (globalVariables.Investigators.get(i).Name) {
                                case Investigator.URSULA_DOWNS:
                                case Investigator.LEO_ANDERSON:
                                    eztliLeader = true;
                                    break;
                            }
                        }
                        StringBuilder eztliBuilder = new StringBuilder();
                        if(globalVariables.Eztli > 0){
                            eztliBuilder.append(getString(R.string.eztli_additional_place));
                            eztliBuilder.append(" ");
                            eztliBuilder.append(Integer.toString(globalVariables.Eztli));
                            eztliBuilder.append(" ");
                            if(globalVariables.Eztli == 1){
                                eztliBuilder.append(getString(R.string.eztli_additional_doom_single));
                            }
                            else {
                                eztliBuilder.append(getString(R.string.eztli_additional_doom_multiple));
                            }
                        }
                        if (eztliLeader) {
                            eztliBuilder.append(getString(R.string.eztli_additional_lead));
                        }
                        if(globalVariables.LowRations > 0){
                            eztliBuilder.append(Integer.toString(globalVariables.LowRations));
                            eztliBuilder.append(" ");
                            if(globalVariables.LowRations == 1) {
                                eztliBuilder.append(getString(R.string.eztli_low_rations_single_investigator));
                            }
                            else {
                                eztliBuilder.append(getString(R.string.eztli_low_rations_multiple_investigators));
                            }
                        }
                        if(eztliBuilder.length() > 0){
                            additionalTwo.setVisibility(VISIBLE);
                            String eztliAdditional = eztliBuilder.toString().trim();
                            eztliAdditional = eztliAdditional + "\n";
                            additionalTwo.setText(eztliAdditional);
                        }
                        break;
                    case 8:
                        sets.setText(R.string.threads_sets);
                        setsImage.setImageResource(R.drawable.threads_sets);
                        locations.setText(R.string.threads_locations);
                        locationPlacement.setVisibility(VISIBLE);
                        locationPlacement.setImageResource(R.drawable.threads_locations);
                        setAside.setText(R.string.threads_set_aside);
                        if(globalVariables.Custody == 2){
                            if(globalVariables.IchtacasTale == 1){
                                additional.setText(R.string.threads_additional_three);
                            } else {
                                additional.setText(R.string.threads_additional_four);
                            }
                        } else {
                            if(globalVariables.IchtacasTale == 1){
                                additional.setText(R.string.threads_additional_one);
                            } else {
                                additional.setText(R.string.threads_additional_four);
                            }
                        }
                        rulesLayout.setVisibility(VISIBLE);
                        rulesHeading.setText(R.string.threads_rules_heading);
                        rules.setText(R.string.threads_rules);
                        break;
                }
        }
        if (globalVariables.CurrentScenario > 100) {
            switch (globalVariables.CurrentScenario) {
                case 101:
                    sets.setText(R.string.rougarou_sets);
                    setsImage.setImageResource(R.drawable.rougarou_sets);
                    setAside.setText(R.string.rougarou_set_aside);
                    setAsideImage.setVisibility(VISIBLE);
                    setAsideImage.setImageResource(R.drawable.rougarou_set_aside);
                    setAsideTwo.setVisibility(VISIBLE);
                    setAsideTwo.setText(R.string.rougarou_set_aside_two);
                    locations.setText(R.string.rougarou_locations);
                    additional.setText(R.string.no_additional);
                    break;
                case 102:
                    sets.setText(R.string.carnevale_sets);
                    setsImage.setImageResource(R.drawable.carnevale_sets);
                    setAside.setText(R.string.carnevale_set_aside);
                    locations.setText(R.string.carnevale_locations);
                    additional.setText(R.string.carnevale_additional);
                    break;
            }
        }


        // Set buttons
        Button log = findViewById(R.id.campaign_log_button);
        log.setTypeface(teutonic);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScenarioSetupActivity.this, CampaignLogActivity.class);
                startActivity(intent);
            }
        });

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
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScenarioSetupActivity.this, ScenarioMainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        // For standalone scenarios, instead advance to the chaos bag
        if (globalVariables.CurrentCampaign == 999) {
            log.setVisibility(GONE);
            continueButton.setText(R.string.chaos_bag);
            continueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ScenarioSetupActivity.this, ChaosBagActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}
