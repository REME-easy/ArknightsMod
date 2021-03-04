package ArknightsMod.Operators.Casters.Steward;

import ArknightsMod.Helper.ArknightsImageMaster;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Vfx.LoopEffect.OperatorSpellingRedEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class Steward_1 extends AbstractSkill {
    private static final boolean IS_AUTOMATIC = true;
    private static final int MAX_SKILLPOINT = 3;
    private static final int ORIGINAL_SKILLPOINT = 0;
    private static final int SKILLTIMES = 1;
    private static final SkillType SKILL_TYPE = SkillType.ATTACK;

    public Steward_1(){
        super(IS_AUTOMATIC, MAX_SKILLPOINT, ORIGINAL_SKILLPOINT, SKILLTIMES, SKILL_TYPE);
    }

    @Override
    public void ActiveEffect() {
        CardCrawlGame.sound.play("atkboost");
        owner.addAttack(4);
    }

    @Override
    public void EndEffect() {
        owner.addAttack(-4);
    }

    @Override
    public AbstractGameEffect skillEffect() {
        return new OperatorSpellingRedEffect(this.owner);
    }

    @Override
    public TextureAtlas.AtlasRegion getTexture() {
        return ArknightsImageMaster.POWERSTRIKE_1;
    }

    @Override
    public void afterAttack() {
        super.afterAttack();
        this.owner.changeSkillPoints(-1);
    }
}
