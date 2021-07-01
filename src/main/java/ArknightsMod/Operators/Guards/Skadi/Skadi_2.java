package ArknightsMod.Operators.Guards.Skadi;

import ArknightsMod.Helper.ArknightsImageMaster;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Vfx.LoopEffect.OperatorSpellingRedEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class Skadi_2 extends AbstractSkill {
    private static final boolean IS_AUTOMATIC = true;
    private static final int MAX_SKILLPOINT = 1;
    private static final int ORIGINAL_SKILLPOINT = 0;
    private static final int SKILLTIMES = 2;
    private static final SkillType SKILL_TYPE = SkillType.PASSIVE;

    public Skadi_2(){
        super(IS_AUTOMATIC, MAX_SKILLPOINT, ORIGINAL_SKILLPOINT, SKILLTIMES, SKILL_TYPE);
    }

    @Override
    public void useWhenSummoned() {
        this.owner.activeSkill();
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
        return ArknightsImageMaster.SKADI_2;
    }

    @Override
    public int getAttackTimes(int times) {
        return times;
    }

    @Override
    public float onAttack(float dmg) {
        return dmg * 2.2F;
    }

    @Override
    public void onEndOfTurn() {
        super.onEndOfTurn();
        owner.changeSkillPoints(-1);
    }
}
