package ArknightsMod.Operators.Guards.Skadi;

import ArknightsMod.Actions.ChangeMaxHpNotPercentAction;
import ArknightsMod.Helper.ArknightsImageMaster;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Powers.Operator.SolidnessPower;
import ArknightsMod.Vfx.LoopEffect.OperatorSpellingRedEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class Skadi_3 extends AbstractSkill {
    private static final boolean IS_AUTOMATIC = false;
    private static final int MAX_SKILLPOINT = 45;
    private static final int ORIGINAL_SKILLPOINT = 30;
    private static final int SKILLTIMES = 20;
    private static final SkillType SKILL_TYPE = SkillType.NATURAL;

    public Skadi_3(){
        super(IS_AUTOMATIC, MAX_SKILLPOINT, ORIGINAL_SKILLPOINT, SKILLTIMES, SKILL_TYPE);
    }

    @Override
    public void ActiveEffect() {
        CardCrawlGame.sound.play("atkboost");
        owner.addAttack(owner.Atk);
        addToBot(new ChangeMaxHpNotPercentAction(owner, owner.maxHealth, false));
        addToBot(new ApplyPowerAction(owner, owner, new SolidnessPower(owner, 3)));
    }

    @Override
    public void EndEffect() {
        owner.addAttack(-owner.Atk / 2);
        addToBot(new ChangeMaxHpNotPercentAction(owner, -owner.maxHealth / 2, false));
        addToBot(new ReducePowerAction(owner, owner, SolidnessPower.POWER_ID, 3));
    }

    @Override
    public AbstractGameEffect skillEffect() {
        return new OperatorSpellingRedEffect(this.owner);
    }

    @Override
    public TextureAtlas.AtlasRegion getTexture() {
        return ArknightsImageMaster.SKADI_3;
    }
}
