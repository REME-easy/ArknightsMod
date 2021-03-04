package ArknightsMod.Operators.Guards.Silverash;

import ArknightsMod.Helper.ArknightsImageMaster;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Powers.Operator.RulesOfSurvivalPower;
import ArknightsMod.Vfx.LoopEffect.OperatorSpellingBlueEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class Svash_2 extends AbstractSkill {
    private static final boolean IS_AUTOMATIC = false;
    private static final int MAX_SKILLPOINT = 1;
    private static final int ORIGINAL_SKILLPOINT = 0;
    private static final int SKILLTIMES = 1;
    private static final SkillType SKILL_TYPE = SkillType.NATURAL;

    public Svash_2(){
        super(IS_AUTOMATIC, MAX_SKILLPOINT, ORIGINAL_SKILLPOINT, SKILLTIMES, SKILL_TYPE);
    }

    @Override
    public void ActiveEffect() {
        owner.changeSkillPoints(-1);
        if(!owner.hasPower("Arknights_RulesOfSurvivalPower")){
            owner.changeMaxAttackCoolDown(1);
            this.addToBot(new ApplyPowerAction(owner, owner, new RulesOfSurvivalPower(owner)));
        }else{
            owner.changeMaxAttackCoolDown(-1);
            this.addToBot(new RemoveSpecificPowerAction(owner, owner, "Arknights_RulesOfSurvivalPower"));
        }
    }

    @Override
    public void EndEffect() {

    }

    @Override
    public AbstractGameEffect skillEffect() {
        return new OperatorSpellingBlueEffect(this.owner);
    }

    @Override
    public TextureAtlas.AtlasRegion getTexture() {
        return ArknightsImageMaster.SVRASH_2;
    }
}
