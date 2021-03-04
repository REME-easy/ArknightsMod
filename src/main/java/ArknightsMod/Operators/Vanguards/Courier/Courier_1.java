package ArknightsMod.Operators.Vanguards.Courier;

import ArknightsMod.Actions.GainEnergyEffectAction;
import ArknightsMod.Helper.ArknightsImageMaster;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Vfx.LoopEffect.OperatorSpellingRedEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class Courier_1 extends AbstractSkill {
    private static final boolean IS_AUTOMATIC = true;
    private static final int MAX_SKILLPOINT = 11;
    private static final int ORIGINAL_SKILLPOINT = 6;
    private static final int SKILLTIMES = 1;
    private static final SkillType SKILL_TYPE = SkillType.NATURAL;

    public Courier_1(){
        super(IS_AUTOMATIC, MAX_SKILLPOINT, ORIGINAL_SKILLPOINT, SKILLTIMES, SKILL_TYPE);
    }

    @Override
    public void ActiveEffect() {
        this.addToBot(new GainEnergyEffectAction(this.owner, 2));
        this.addToBot(new GainEnergyAction(2));
        this.owner.changeSkillPoints(-1);
    }

    @Override
    public void EndEffect() {

    }

    @Override
    public AbstractGameEffect skillEffect() {
        return new OperatorSpellingRedEffect(this.owner);
    }

    @Override
    public TextureAtlas.AtlasRegion getTexture() {
        return ArknightsImageMaster.CHARGE_COST_2;
    }
}
