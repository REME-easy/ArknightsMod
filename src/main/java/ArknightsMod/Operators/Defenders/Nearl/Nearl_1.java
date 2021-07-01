package ArknightsMod.Operators.Defenders.Nearl;

import ArknightsMod.Actions.OperatorHealAction;
import ArknightsMod.Helper.ArknightsImageMaster;
import ArknightsMod.Operators.AbstractOperator;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Vfx.LoopEffect.OperatorSpellingGreenEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class Nearl_1 extends AbstractSkill {
    private static final boolean IS_AUTOMATIC = true;
    private static final int MAX_SKILLPOINT = 4;
    private static final int ORIGINAL_SKILLPOINT = 0;
    private static final int SKILLTIMES = 1;
    private static final SkillType SKILL_TYPE = SkillType.NATURAL;

    public Nearl_1(){
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
        return new OperatorSpellingGreenEffect(this.owner);
    }

    @Override
    public TextureAtlas.AtlasRegion getTexture() {
        return ArknightsImageMaster.NEARL_1;
    }

    @Override
    public float onAttack(float dmg) {
        return dmg * 1.5F;
    }

    @Override
    public void onOtherOperatorDamage(AbstractOperator operator) {
        super.onOtherOperatorDamage(operator);
        if(isSpelling && !operator.isDeadOrEscaped() && operator.currentHealth <= operator.maxHealth / 2) {
            owner.state.addAnimation(0, "Skill", false, 0.0F);
            this.addToBot(new OperatorHealAction(operator, owner));
            this.addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    owner.changeSkillPoints(-1);
                    isDone = true;
                }
            });
        }
    }
}
