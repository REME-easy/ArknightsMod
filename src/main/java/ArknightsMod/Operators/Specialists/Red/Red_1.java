package ArknightsMod.Operators.Specialists.Red;

import ArknightsMod.Helper.ArknightsImageMaster;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Vfx.LoopEffect.OperatorSpellingRedEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class Red_1 extends AbstractSkill {
    private static final boolean IS_AUTOMATIC = true;
    private static final int MAX_SKILLPOINT = 1;
    private static final int ORIGINAL_SKILLPOINT = 0;
    private static final int SKILLTIMES = 3;
    private static final SkillType SKILL_TYPE = SkillType.PASSIVE;

    public Red_1(){
        super(IS_AUTOMATIC, MAX_SKILLPOINT, ORIGINAL_SKILLPOINT, SKILLTIMES, SKILL_TYPE);
    }

    @Override
    public void useWhenSummoned() {
        this.owner.activeSkill();
    }

    @Override
    public void ActiveEffect() {
        this.addToBot(new GainBlockAction(this.owner, this.owner, 10));
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
        return ArknightsImageMaster.RED_1;
    }

    @Override
    public int getAttackTimes(int times) {
        return times;
    }

    @Override
    public float onAttack(float dmg) {
        return dmg + 3;
    }

    @Override
    public void afterAttack() {
        super.afterAttack();
        this.owner.changeSkillPoints(-1);
    }
}
