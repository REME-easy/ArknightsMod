package ArknightsMod.Operators.Guards.Lappland;

import ArknightsMod.Helper.ArknightsImageMaster;
import ArknightsMod.Helper.GeneralHelper;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Vfx.LoopEffect.LapplandWolfSoulEffect;
import ArknightsMod.Vfx.LoopEffect.OperatorSpellingGreyEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class Lappland_2 extends AbstractSkill {
    private static final boolean IS_AUTOMATIC = true;
    private static final int MAX_SKILLPOINT = 8;
    private static final int ORIGINAL_SKILLPOINT = 0;
    private static final int SKILLTIMES = 8;
    private static final SkillType SKILL_TYPE = SkillType.ATTACK;

    private AbstractGameEffect blackWolf;
    private AbstractGameEffect whiteWolf;

    public Lappland_2(){
        super(IS_AUTOMATIC, MAX_SKILLPOINT, ORIGINAL_SKILLPOINT, SKILLTIMES, SKILL_TYPE);
    }

    @Override
    public void ActiveEffect() {
//        this.owner.damageType = DamageType.THORNS;
//        this.addToBot(new ApplyPowerAction(this.owner, this.owner, new MagicAttackPower(this.owner)));
        CardCrawlGame.sound.play("atkboost");
        this.owner.ChangeDamageType(DamageType.THORNS);
        this.owner.attackTargets += 1;
        blackWolf = new LapplandWolfSoulEffect(this.owner.drawX, this.owner.drawY + 50.0F, true);
        whiteWolf = new LapplandWolfSoulEffect(this.owner.drawX, this.owner.drawY + 50.0F, false);
        GeneralHelper.addEffect(blackWolf);
        GeneralHelper.addEffect(whiteWolf);
    }

    @Override
    public void EndEffect() {
//        this.owner.damageType = DamageType.NORMAL;
//        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, MagicAttackPower.POWER_ID));
        this.owner.ChangeDamageType(DamageType.NORMAL);
        this.owner.attackTargets -= 1;
        if(blackWolf != null) {
            blackWolf.isDone = true;
            blackWolf = null;
        }
        if(whiteWolf != null) {
            whiteWolf.isDone = true;
            whiteWolf = null;
        }
    }

    @Override
    public void onUseEnergy(int amt) {
        this.owner.changeSkillPoints(-amt);
    }

    @Override
    public AbstractGameEffect skillEffect() {
        return new OperatorSpellingGreyEffect(this.owner);
    }

    @Override
    public TextureAtlas.AtlasRegion getTexture() {
        return ArknightsImageMaster.LAPPLAND_2;
    }

    @Override
    public int getAttackTimes(int times) {
        return times;
    }

    @Override
    public float onAttack(float dmg) {
        return dmg + 6;
    }
}
