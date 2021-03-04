package ArknightsMod.Cards.Operator;

import ArknightsMod.Actions.SpawnOperatorAction;
import ArknightsMod.Cards.AbstractArkCard;
import ArknightsMod.Character.OperatorGroup;
import ArknightsMod.Helper.ArknightsImageMaster;
import ArknightsMod.Operators.AbstractOperator;
import ArknightsMod.Operators.Skills.AbstractSkill;
import basemod.BaseMod;
import basemod.abstracts.CustomSavable;
import basemod.helpers.TooltipInfo;
import basemod.interfaces.ISubscriber;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.google.gson.reflect.TypeToken;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class AbstractOperatorCard extends AbstractArkCard implements CustomSavable<Integer>, ISubscriber {
    public ArrayList<String> tips = new ArrayList<>();
    public ArrayList<AbstractSkill> skills = new ArrayList<>();
    public AbstractSkill currentSkill;
    public int level;
    private int maxHealth;
    private int atk;
    private int atkSpeed;
    private int resummonTime;
    public AbstractOperator.OperatorType operatorType;

    public int skillindex = 0;

    private static final String ALREADY_HAVE_MSG = CardCrawlGame.languagePack.getUIString("ARKNIGHTS_SELECT").TEXT[5];
    private static final String FULL_SUMMON_MSG = CardCrawlGame.languagePack.getUIString("ARKNIGHTS_SELECT").TEXT[4];

    private static AtlasRegion[] levelImgs;
    private static HashMap<AbstractOperator.OperatorType, AtlasRegion> typeImgs;

    public static void init() {
        levelImgs = new AtlasRegion[] {
                ArknightsImageMaster.STAR_1, ArknightsImageMaster.STAR_2, ArknightsImageMaster.STAR_3,
                ArknightsImageMaster.STAR_4, ArknightsImageMaster.STAR_5, ArknightsImageMaster.STAR_6,
        };
        typeImgs = new HashMap<>();
        typeImgs.put(AbstractOperator.OperatorType.SPECIALIST, ArknightsImageMaster.TYPE_SPECIALIST);
        typeImgs.put(AbstractOperator.OperatorType.GUARD, ArknightsImageMaster.TYPE_GUARD);
        typeImgs.put(AbstractOperator.OperatorType.MEDIC, ArknightsImageMaster.TYPE_MEDIC);
        typeImgs.put(AbstractOperator.OperatorType.VANGUARD, ArknightsImageMaster.TYPE_VANGUARD);
        typeImgs.put(AbstractOperator.OperatorType.SNIPER, ArknightsImageMaster.TYPE_SNIPER);
        typeImgs.put(AbstractOperator.OperatorType.SUPPORTER, ArknightsImageMaster.TYPE_SUPPORTER);
        typeImgs.put(AbstractOperator.OperatorType.CASTER, ArknightsImageMaster.TYPE_CASTER);
        typeImgs.put(AbstractOperator.OperatorType.DEFENDER, ArknightsImageMaster.TYPE_DEFENDER);
    }

    public AbstractOperatorCard(String ID,String NAME,String IMG_PATH,int COST,String DESCRIPTION,String[] EXTENDED_DESCRIPTION,CardType CARDTYPE,CardColor CARDCOLOR,CardRarity CARDRARITY,CardTarget CARDTARGET){
        super(ID,NAME,IMG_PATH,COST,DESCRIPTION,CARDTYPE,CARDCOLOR,CARDRARITY,CARDTARGET);
        Collections.addAll(tips, EXTENDED_DESCRIPTION);
        BaseMod.subscribe(this);
        BaseMod.addSaveField("skillindex" + this.uuid, this);
    }

    protected void setStat(){
        AbstractOperator operator = this.getOperator();
        level = operator.level;
        maxHealth = operator.maxHealth;
        atk = operator.Atk;
        atkSpeed = operator.attackCoolDown;
        resummonTime = operator.resummonTime;
        operatorType = operator.operatorType;
    }

    protected void initSkillHitbox(){
        skills = getSkills();
        if(skillindex < skills.size())
            currentSkill = skills.get(skillindex);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        this.addToBot(new SpawnOperatorAction(getOperator().makeCopy(), this));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if(OperatorGroup.GetOperatorByID(this.cardID) != null) {
            this.cantUseMessage = ALREADY_HAVE_MSG;
            return false;
        }else if(OperatorGroup.GetOperators().size() >= OperatorGroup.maxSize) {
            this.cantUseMessage = FULL_SUMMON_MSG;
            return false;
        }else {
            return super.canUse(p, m);
        }

    }

    public void onUseCardInDeck(AbstractCard card){}

    public void onBattleStartInDeck(AbstractRoom room){
        for(AbstractSkill s : skills) {
            s.init();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        AbstractOperatorCard tmp = (AbstractOperatorCard) super.makeCopy();
        tmp.skillindex = skillindex;
        tmp.currentSkill = currentSkill;
        return tmp;
    }

    public AbstractOperator getOperator(){
        return null;
    }

    public ArrayList<AbstractSkill> getSkills(){
        return null;
    }

    @Override
    public void upgrade() {}

    @Override
    public Integer onSave() {
        return this.skillindex;
    }

    @Override
    public void onLoad(Integer arg0) {
        if(arg0 != null && skills.size() > 0){
            skillindex = arg0;
            if(skillindex < skills.size())
                this.currentSkill = skills.get(skillindex);
        }

    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        if(skills.size() > 0) {
            ArrayList<TooltipInfo> tips = new ArrayList<>();
            tips.add(new TooltipInfo(this.tips.get(2 * this.skillindex), this.tips.get(2 * this.skillindex + 1)));
            return tips;
        }else
            return null;
    }

    @Override
    public Type savedType()
    {
        return new TypeToken<Integer>(){}.getType();
    }

    @Override
    public void render(SpriteBatch sb, boolean selected) {
        super.render(sb, selected);
        sb.setColor(Color.WHITE);
        if(currentSkill != null){
            renderSkill(sb, currentSkill.getTexture());
        }
        float tmp = Settings.scale * this.drawScale;
        float offsetX = -108 - 12.0F * Settings.scale;
        float offsetY = 24.0F * Settings.scale;

        renderHelper(sb, levelImgs[this.level - 1], 1.0F * Settings.scale);
        renderHelper(sb, typeImgs.get(this.operatorType));

        FontHelper.renderRotatedText(sb, FontHelper.blockInfoFont, Integer.toString(maxHealth), this.current_x, this.current_y, offsetX * tmp, (97 + offsetY) * tmp, this.angle, false, Color.WHITE);
        FontHelper.renderRotatedText(sb, FontHelper.blockInfoFont, Integer.toString(atk), this.current_x, this.current_y, offsetX * tmp, (69 + offsetY) * tmp, this.angle, false, Color.WHITE);
        FontHelper.renderRotatedText(sb, FontHelper.blockInfoFont, Integer.toString(atkSpeed), this.current_x, this.current_y, offsetX * tmp, (41 + offsetY) * tmp, this.angle, false, Color.WHITE);
        FontHelper.renderRotatedText(sb, FontHelper.blockInfoFont, Integer.toString(resummonTime), this.current_x, this.current_y, offsetX * tmp, (13 + offsetY) * tmp, this.angle, false, Color.WHITE);
    }

//    private void renderStar(SpriteBatch sb){
////        renderHelper(sb, levelImgs[this.level - 1], this.current_x + 300.0F * Settings.scale * this.drawScale, this.current_y);
//    }
//
//    private void renderType(SpriteBatch sb){
//        //renderHelper(sb, typeImgs.get(this.operatorType), 115.0F * Settings.scale, 175.0F * Settings.scale, 1.0F);
//    }

    private void renderHelper(SpriteBatch sb, AtlasRegion img) {
        sb.draw(img, this.current_x + img.offsetX - (float)img.originalWidth / 2.0F, this.current_y + img.offsetY - (float)img.originalHeight / 2.0F, (float)img.originalWidth / 2.0F - img.offsetX, (float)img.originalHeight / 2.0F - img.offsetY, (float)img.packedWidth, (float)img.packedHeight, this.drawScale * Settings.scale, this.drawScale * Settings.scale, this.angle);
    }

    private void renderHelper(SpriteBatch sb, AtlasRegion img, float scale) {
        sb.draw(img, this.current_x + img.offsetX - (float)img.originalWidth / 2.0F, this.current_y + img.offsetY - (float)img.originalHeight / 2.0F, (float)img.originalWidth / 2.0F - img.offsetX, (float)img.originalHeight / 2.0F - img.offsetY, (float)img.packedWidth, (float)img.packedHeight, this.drawScale * Settings.scale * scale, this.drawScale * Settings.scale * scale, this.angle);
    }

    private void renderSkill(SpriteBatch sb, AtlasRegion img) {
        sb.draw(img, this.current_x + 104.0F, this.current_y + 84.0F, -104.0F, -84.0F, 64.0F, 64.0F, this.drawScale * Settings.scale, this.drawScale * Settings.scale, this.angle);
    }

//    public static float tx = 0.0F;
//    public static float ty = 0.0F;
//
//    public static void test(float x, float y) {
//        tx = x;
//        ty = y;
//    }
}
