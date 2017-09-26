package com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data;

public class Investigator {
    // Integer values for each investigator
    public static final int ROLAND_BANKS = 1;
    public static final int DAISY_WALKER = 2;
    public static final int SKIDS_OTOOLE = 3;
    public static final int AGNES_BAKER = 4;
    public static final int WENDY_ADAMS = 5;
    public static final int ZOEY_SAMARAS = 6;
    public static final int REX_MURPHY = 7;
    public static final int JENNY_BARNES = 8;
    public static final int JIM_CULVER = 9;
    public static final int ASHCAN_PETE = 10;
    public static final int MARK_HARRIGAN = 11;
    public static final int MINH_THI_PHAN = 12;
    public static final int SEFINA_ROUSSEAU = 13;
    public static final int AKACHI_ONYELE = 14;
    public static final int WILLIAM_YORICK = 15;
    public static final int LOLA_HAYES = 16;

    // Sets maximum health and sanity values for the various investigators (correspond to the names in the string array)
    private int[] health = {0,9,5,8,6,7,9,6,8,7,6,9,7,5,6,8,6};
    private int[] sanity = {0,5,9,6,8,7,6,9,7,8,5,5,7,9,8,6,6};

    public Investigator(int investigator, String name, String deckName, String deck){
        setupInvestigator(investigator);
        this.PlayerName = name;
        this.DeckName = deckName;
        this.Decklist = deck;
    }

    private void setupInvestigator(int investigator){
        this.Name = investigator;
        this.Health = health[Name];
        this.Sanity = sanity[Name];
        this.Status = 1;
        this.Damage = 0;
        this.Horror = 0;
        this.AvailableXP = 0;
    }

    // Basic attributes for all investigators
    public int Name;
    public int Health;
    public int Sanity;
    public int Status;          // 0 = not in use, 1 = in use, 2 = dead, 3 = saved
    public int Damage;
    public int Horror;
    public int AvailableXP;
    public String PlayerName;
    public String DeckName;
    public String Decklist;

    // Temp variables for when a change might be pending clicking the continue button
    public int TempXP = 0;
    public int TempStatus;      // 0 = normal, 1 = resigned, 2 = health, 3 = horror
    public int TempWeakness;    // 0 = not active, 1 = active

}
