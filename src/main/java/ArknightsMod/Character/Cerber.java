package ArknightsMod.Character;

import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Strike_Blue;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;

import java.util.ArrayList;

import static ArknightsMod.Patches.CardColorEnum.ARK_NIGHTS;
import static ArknightsMod.Patches.PlayerClassEnum.ARKNIGHTS_CERBER;


public class Cerber extends CustomPlayer {
    private static final int ENERGY_PER_TURN = 3;
    private static final String MY_CHARACTER_SHOULDER_2 = "ArkImg/char/shoulder2.png";
    private static final String MY_CHARACTER_SHOULDER_1 = "ArkImg/char/shoulder.png";
    private static final String MY_CHARACTER_CORPSE = "ArkImg/char/corpse.png";
    private static final float[] LAYER_SPEED = new float[]{-40.0F, -32.0F, 20.0F, -20.0F, 0.0F, -10.0F, -8.0F, 5.0F, -5.0F, 0.0F};
    private static final String[] ORB_TEXTURES = new String[]{"ArkImg/UI/orb/layer5.png", "ArkImg/UI/orb/layer4.png", "ArkImg/UI/orb/layer3.png", "ArkImg/UI/orb/layer2.png", "ArkImg/UI/orb/layer1.png", "ArkImg/UI/orb/layer6.png", "ArkImg/UI/orb/layer5d.png", "ArkImg/UI/orb/layer4d.png", "ArkImg/UI/orb/layer3d.png", "ArkImg/UI/orb/layer2d.png", "ArkImg/UI/orb/layer1d.png"};
    private static final String atlasURL = "ArkImg/char/cerber.atlas";
    private static final String JsonURL = "ArkImg/char/cerber.json";
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString("Cerber");

    private static final int STARTING_HP = 70;
    private static final int MAX_HP = 70;
    private static final int STARTING_GOLD = 99;
    private static final int ORB_SLOTS = 0;
    private static final int HAND_SIZE = 5;

    /*TODO
    *   行囊流
    *   源石技艺流
    *   火刀流
    *   -
    *   关键词：
    *   行囊 储存卡牌的卡牌。
    *   法术 打出后保存，回合结束时合成一样法术
    *   源石技艺 往法术中添加一些效果
    *
    * */

    public Cerber(String name) {
        super(name, ARKNIGHTS_CERBER, ORB_TEXTURES, "ArkImg/UI/orb/vfx.png", LAYER_SPEED, null, null);

        this.dialogX = (this.drawX + 100.0F * Settings.scale);
        this.dialogY = (this.drawY + 150.0F * Settings.scale);

        this.initializeClass(null, MY_CHARACTER_SHOULDER_2,
                MY_CHARACTER_SHOULDER_1,
                MY_CHARACTER_CORPSE,
                this.getLoadout(), 0.0F, 0.0F, 150.0F, 150.0F, new EnergyManager(ENERGY_PER_TURN));
        this.loadAnimation(atlasURL, JsonURL, 2.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }

    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add("Defend_B");
        return retVal;
    }

    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add("Art of War");
        return retVal;
    }

    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(characterStrings.NAMES[0], characterStrings.TEXT[0],
                STARTING_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, HAND_SIZE,
                this, this.getStartingRelics(), this.getStartingDeck(), false);
    }

    @Override
    public String getTitle(PlayerClass playerClass) {
        return characterStrings.NAMES[0];
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return ARK_NIGHTS;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new Strike_Blue();
    }

    @Override
    public Color getCardTrailColor() {
        return getColor(220.0f,220.0f,220.0f);
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 5;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontBlue;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("ATTACK_HEAVY", MathUtils.random(-0.1F, 0.1F));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_HEAVY";
    }

    @Override
    public String getLocalizedCharacterName() {
        return characterStrings.NAMES[0];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new Cerber(this.name);
    }

    @Override
    public String getSpireHeartText() {
        return characterStrings.TEXT[1];
    }

    @Override
    public Color getSlashAttackColor() {
        return getColor(150.0F,150.0F,150.0F);
    }

    @Override
    public String getVampireText() {
        return Vampires.DESCRIPTIONS[0];
    }

    @Override
    public Color getCardRenderColor() {
        return getColor(150.0F,150.0F,150.0F);
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL, AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL};
    }

    private static Color getColor(float r, float g, float b) {
        return new Color(r / 255.0F, g / 255.0F, b / 255.0F, 1.0F);
    }

}
