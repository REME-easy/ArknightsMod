package ArknightsMod.Operators.Snipers.Meteor;

import ArknightsMod.Helper.ArknightsImageMaster;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Vfx.LoopEffect.OperatorSpellingRedEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class Meteor_1 extends AbstractSkill {
    private static final boolean IS_AUTOMATIC = true;
    private static final int MAX_SKILLPOINT = 3;
    private static final int ORIGINAL_SKILLPOINT = 0;
    private static final int SKILLTIMES = 1;
    private static final SkillType SKILL_TYPE = SkillType.ATTACK;

    public Meteor_1(){
        super(IS_AUTOMATIC, MAX_SKILLPOINT, ORIGINAL_SKILLPOINT, SKILLTIMES, SKILL_TYPE);
    }

    @Override
    public void ActiveEffect() {
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
        return ArknightsImageMaster.METEOR_1;
    }

    @Override
    public void afterAttack() {
        super.afterAttack();
        if(owner.lastTarget != null)
            this.addToBot(new ApplyPowerAction(owner.lastTarget, owner.lastTarget, new VulnerablePower(owner.lastTarget, 1, false)));
        this.owner.changeSkillPoints(-1);
    }
}
