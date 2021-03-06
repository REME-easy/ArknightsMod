package ArknightsMod.Operators.Supporter.Istina;

import ArknightsMod.Helper.ArknightsImageMaster;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Vfx.LoopEffect.OperatorSpellingRedEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class Istina_2 extends AbstractSkill {
    private static final boolean IS_AUTOMATIC = false;
    private static final int MAX_SKILLPOINT = 25;
    private static final int ORIGINAL_SKILLPOINT = 10;
    private static final int SKILLTIMES = 13;
    private static final SkillType SKILL_TYPE = SkillType.NATURAL;

    public Istina_2(){
        super(IS_AUTOMATIC, MAX_SKILLPOINT, ORIGINAL_SKILLPOINT, SKILLTIMES, SKILL_TYPE);
    }

    @Override
    public void ActiveEffect() {
        CardCrawlGame.sound.play("atkboost");
        this.owner.attackTargets += 2;
        owner.state.setAnimation(0, "Skill_Start", false);
        owner.playIdleAnim();
    }

    @Override
    public void EndEffect() {
        this.owner.changeMaxAttackCoolDown(1);
        this.owner.attackTargets -= 2;
        owner.state.setAnimation(0, "Skill_End", false);
        owner.playIdleAnim();
    }

    @Override
    public AbstractGameEffect skillEffect() {
        return new OperatorSpellingRedEffect(this.owner);
    }

    @Override
    public TextureAtlas.AtlasRegion getTexture() {
        return ArknightsImageMaster.ISTINA_2;
    }

    @Override
    public void onUseEnergy(int amt) {
        owner.changeSkillPoints(-amt);
    }
}
