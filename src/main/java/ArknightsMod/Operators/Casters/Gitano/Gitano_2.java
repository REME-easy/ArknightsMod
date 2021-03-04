package ArknightsMod.Operators.Casters.Gitano;

import ArknightsMod.Helper.ArknightsImageMaster;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Powers.Operator.CantAttackPower;
import ArknightsMod.Vfx.LoopEffect.OperatorSpellingRedEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class Gitano_2 extends AbstractSkill {
    private static final boolean IS_AUTOMATIC = false;
    private static final int MAX_SKILLPOINT = 45;
    private static final int ORIGINAL_SKILLPOINT = 25;
    private static final int SKILLTIMES = 6;
    private static final SkillType SKILL_TYPE = SkillType.NATURAL;

    public Gitano_2(){
        super(IS_AUTOMATIC, MAX_SKILLPOINT, ORIGINAL_SKILLPOINT, SKILLTIMES, SKILL_TYPE);
    }

    @Override
    public void ActiveEffect() {
        CardCrawlGame.sound.play("atkboost");
    }

    @Override
    public void EndEffect() {
        this.addToBot(new ApplyPowerAction(owner, owner, new CantAttackPower(owner, 1, true)));
    }

    @Override
    public AbstractGameEffect skillEffect() {
        return new OperatorSpellingRedEffect(this.owner);
    }

    @Override
    public TextureAtlas.AtlasRegion getTexture() {
        return ArknightsImageMaster.GITANO_2;
    }

    @Override
    public float onAttack(float dmg) {
        return dmg * 2;
    }

    @Override
    public void afterAttack() {
        owner.changeSkillPoints(-1);
    }
}
