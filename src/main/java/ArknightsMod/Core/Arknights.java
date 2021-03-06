package ArknightsMod.Core;

import ArknightsMod.Cards.Attack.SwordRain;
import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Cards.Operator.Token.MotterCard;
import ArknightsMod.Cards.Skill.Divination;
import ArknightsMod.Cards.Status.Frozen;
import ArknightsMod.Character.OperatorGroup;
import ArknightsMod.Helper.ArknightsImageMaster;
import ArknightsMod.Helper.GeneralHelper;
import ArknightsMod.Helper.OperatorHelper;
import ArknightsMod.Monsters.EnemyManager;
import ArknightsMod.Operators.AbstractOperator.OperatorType;
import ArknightsMod.Relics.BattleRecord;
import ArknightsMod.Screens.ArknighsTutorial;
import ArknightsMod.Utils.OperatorReward;
import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardSave;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import static ArknightsMod.Patches.CardColorEnum.ARK_NIGHTS;
import static ArknightsMod.Utils.OperatorReward.RewardItemEnum.OPERATOR;
import static com.megacrit.cardcrawl.core.Settings.language;


@SpireInitializer
public class Arknights implements  EditCharactersSubscriber,EditStringsSubscriber,EditCardsSubscriber,EditRelicsSubscriber,EditKeywordsSubscriber, PostDungeonInitializeSubscriber, PostInitializeSubscriber, PostBattleSubscriber, OnCardUseSubscriber, OnStartBattleSubscriber, StartActSubscriber{
    private static final Logger logger = LogManager.getLogger(Arknights.class);
    //private static final String MY_CHARACTER_BUTTON = "ArkImg/char/Arknights_Button.png";
    //private static final String MY_CHARACTER_PORTRAIT = "ArkImg/char/Arknights_Portrait.png";
    private static final String BG_ATTACK_512 = "ArkImg/512/bg_attack_arknights.png";
    private static final String BG_POWER_512 = "ArkImg/512/bg_power_arknights.png";
    private static final String BG_SKILL_512 = "ArkImg/512/bg_skill_arknights.png";
    private static final String small_orb = "ArkImg/512/card_default_gray_orb.png";
    private static final String BG_ATTACK_1024 = "ArkImg/1024/bg_attack_arknights.png";
    private static final String BG_POWER_1024 = "ArkImg/1024/bg_power_arknights.png";
    private static final String BG_SKILL_1024 = "ArkImg/1024/bg_skill_arknights.png";
    private static final String big_orb = "ArkImg/1024/card_default_gray_orb.png";
    private static final String energy_orb = "ArkImg/512/card_small_orb.png";
    private static final Color grey = getColor(220.0f,220.0f,220.0f);

    public static boolean[] activeTutorials = new boolean[]{true, true, true, true, true, true};

    //TODO:
    //  敌方
    //  干员（23/39）
    //  图标
    //  图集

    public Arknights() {
            BaseMod.subscribe(this);
            BaseMod.addColor(ARK_NIGHTS,grey,grey,grey,grey,grey,grey,grey,BG_ATTACK_512,BG_SKILL_512,BG_POWER_512,small_orb,BG_ATTACK_1024,BG_SKILL_1024,BG_POWER_1024,big_orb,energy_orb);
    }

    public static void initialize() {
        new Arknights();

        logger.info("===阿克乃次===");
        try{
            int i;
            Properties defaults = new Properties();
            for(i = 0; i < activeTutorials.length; ++i) {
                defaults.setProperty("activeTutorials" + i, "true");
            }
            SpireConfig config = new SpireConfig("ArknightsMod", "Common", defaults);
            for(i = 0; i < activeTutorials.length; ++i) {
                activeTutorials[i] = config.getBool("activeTutorials" + i);
            }
        }catch (IOException var4) {
            var4.printStackTrace();
        }



        logger.info("===扣扣打呦===");

    }

    @Override
    public void receiveEditCharacters() {
        //logger.info("===开始加载刻俄柏===");
        //BaseMod.addCharacter(new Cerber(CardCrawlGame.playerName),MY_CHARACTER_BUTTON,MY_CHARACTER_PORTRAIT,ARKNIGHTS_CERBER);
        //logger.info("===傻狗加载完了===");
    }
    @Override
    public void receiveEditStrings() {
        String lang = "eng";
        if (language == Settings.GameLanguage.ENG) {
            lang = "eng";
        } else if (language == Settings.GameLanguage.ZHS) {
            lang = "zh";
        }
        BaseMod.loadCustomStringsFile(RelicStrings.class, "localization/ArknightsRelics_" + lang + ".json");
        BaseMod.loadCustomStringsFile(CardStrings.class, "localization/ArknightsCards_" + lang + ".json");
        BaseMod.loadCustomStringsFile(PowerStrings.class, "localization/ArknightsPowers_" + lang + ".json");
        //BaseMod.loadCustomStringsFile(EventStrings.class, "localization/ArknightsEvent_" + lang + ".json");
        BaseMod.loadCustomStringsFile(MonsterStrings.class,"localization/ArknightsMonsters_"+ lang +".json");
        BaseMod.loadCustomStringsFile(OrbStrings.class,"localization/ArknightsOrbs_" + lang + ".json");
        BaseMod.loadCustomStringsFile(CharacterStrings.class, "localization/ArknightsChar_" + lang + ".json");
        BaseMod.loadCustomStringsFile(UIStrings.class, "localization/ArknightsUI_" + lang + ".json");
        BaseMod.loadCustomStringsFile(TutorialStrings.class, "localization/ArknightsTutorials_" + lang + ".json");
    }

    @Override
    public void receiveEditCards() {
        OperatorHelper.addOperators();
        BaseMod.addCard(new SwordRain());
        BaseMod.addCard(new Divination());
        BaseMod.addCard(new MotterCard());
        BaseMod.addCard(new Frozen());
    }

    @Override
    public void receiveEditRelics(){
        BaseMod.addRelic(new BattleRecord(), RelicType.SHARED);
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String lang = "eng";
        if (language == Settings.GameLanguage.ZHS) {
            lang = "zh";
        }

        logger.info("===开始加载关键词===");
        String json = Gdx.files.internal("localization/ArknightsKeywords_" + lang + ".json").readString(String.valueOf(StandardCharsets.UTF_8));
        Keyword[] keywords = (gson.fromJson(json, Keyword[].class));
        if (keywords != null) {
            for(Keyword keyword : keywords) {
                BaseMod.addKeyword(keyword.NAMES[0], keyword.NAMES, keyword.DESCRIPTION);
            }
        }
        logger.info("===关键词加载完了===");

    }

    private void receiveEditMonsters(){
        EnemyManager.InitEnemies();
    }

    private void CreatePanel() throws IOException {
        SpireConfig spireConfig = new SpireConfig("ArknightsMod", "Common");
        ModPanel settingsPanel = new ModPanel();
        UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ARKNIGHTS_CONFIG");
        String[] TEXT = uiStrings.TEXT;
        ModLabeledToggleButton tutorialOpen;

        tutorialOpen = new ModLabeledToggleButton(TEXT[0], 500.0F, 550.0F, Settings.CREAM_COLOR, FontHelper.charDescFont, activeTutorials[0], settingsPanel, (label) -> {
        }, (button) -> {
            for(int i = 0; i < activeTutorials.length; ++i) {
                spireConfig.setBool("activeTutorials", activeTutorials[i] = button.enabled);
            }

            CardCrawlGame.mainMenuScreen.optionPanel.effects.clear();

            try {
                spireConfig.save();
            } catch (IOException var3) {
                var3.printStackTrace();
            }

        });
        settingsPanel.addUIElement(tutorialOpen);
        Texture badgeTexture = new Texture(Gdx.files.internal("ArkImg/UI/ArknightsMod.png"));
        BaseMod.registerModBadge(badgeTexture, "ArknightsMod", "REME", "have a nice time!", settingsPanel);
    }

    public static void saveData() throws IOException {
        int i;
        SpireConfig config = new SpireConfig("ArknightsMod", "Common");

        for(i = 0; i < activeTutorials.length; ++i) {
            config.setBool("activeTutorials" + i, activeTutorials[i]);
        }

        config.save();
    }

    @Override
    public void receivePostInitialize() {
        ArknightsImageMaster.initialize();
        receiveEditMonsters();
        BaseMod.registerCustomReward(OPERATOR,
                (rewardSave -> {
                    OperatorReward operatorReward = new OperatorReward(OperatorType.valueOf(rewardSave.id), rewardSave.amount);
                    if(operatorReward.index != 0) {
                        operatorReward.renderLink = true;
                    }
                    return operatorReward;
                }),
                (customReward -> {
                    if(customReward instanceof OperatorReward)
                        return new RewardSave(customReward.type.toString(), ((OperatorReward)customReward).type.toString(), ((OperatorReward) customReward).index, 0);
                    return new RewardSave(customReward.type.toString(), "");
                }));
        try {
            this.CreatePanel();
        } catch (IOException var3) {
            var3.printStackTrace();
        }
    }

    @Override
    public void receivePostDungeonInitialize(){
        obtain(AbstractDungeon.player, new BattleRecord(), false);
    }

    @Override
    public void receivePostBattle(AbstractRoom room){
        OperatorGroup tmp = OperatorGroup.OperatorsFields.operators.get(AbstractDungeon.player);
        tmp.operators.clear();

    }

    @Override
    public void receiveCardUsed(AbstractCard card){
        for(AbstractCard c:AbstractDungeon.player.masterDeck.group){
            if(c instanceof AbstractOperatorCard){
                ((AbstractOperatorCard) c).onUseCardInDeck(card);
            }
        }
        OperatorGroup.OperatorsFields.operators.get(AbstractDungeon.player).onUseCard(card);
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom room){
        for(AbstractCard c:AbstractDungeon.player.masterDeck.group){
            if(c instanceof AbstractOperatorCard){
                ((AbstractOperatorCard) c).onBattleStartInDeck(room);
            }
        }

        try {
            EnemyManager.OnBattleStart();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        if(activeTutorials[0]) {
            GeneralHelper.addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    AbstractDungeon.ftue = new ArknighsTutorial();
                    this.isDone = true;
                }
            });
        }
    }

    @Override
    public void receiveStartAct() {
        if(AbstractDungeon.actNum > 0)
            EnemyManager.OnActChanged();
    }

    public static boolean obtain(AbstractPlayer p, AbstractRelic r, boolean canDuplicate) {
        if (r == null) {
            return false;
        } else if (p.hasRelic(r.relicId) && !canDuplicate) {
            return false;
        } else {
            int slot = p.relics.size();
            r.makeCopy().instantObtain(p, slot, true);
            return true;
        }
    }

    private static Color getColor(float r, float g, float b) {
        return new Color(r / 255.0F, g / 255.0F, b / 255.0F, 1.0F);
    }

}