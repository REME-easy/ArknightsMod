package ArknightsMod.Operators.Casters.Gitano;

import ArknightsMod.Helper.ArknightsImageMaster;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Vfx.LoopEffect.OperatorSpellingRedEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class Gitano_1 extends AbstractSkill {
    private static final boolean IS_AUTOMATIC = false;
    private static final int MAX_SKILLPOINT = 20;
    private static final int ORIGINAL_SKILLPOINT = 1;
    private static final int SKILLTIMES = 12;
    private static final SkillType SKILL_TYPE = SkillType.NATURAL;

    public Gitano_1(){
        super(IS_AUTOMATIC, MAX_SKILLPOINT, ORIGINAL_SKILLPOINT, SKILLTIMES, SKILL_TYPE);
    }

    @Override
    public void ActiveEffect() {
        CardCrawlGame.sound.play("atkboost");
        this.owner.changeMaxAttackCoolDown(-1);
    }

    @Override
    public void EndEffect() {
        this.owner.changeMaxAttackCoolDown(1);
    }

    @Override
    public AbstractGameEffect skillEffect() {
        return new OperatorSpellingRedEffect(this.owner);
    }

    @Override
    public TextureAtlas.AtlasRegion getTexture() {
        return ArknightsImageMaster.MATK_UP_2;
    }

    @Override
    public void onUseEnergy(int amt) {
        this.owner.changeSkillPoints(-amt);
    }

    @Override
    public float onAttack(float dmg) {
        return dmg;
    }
}
