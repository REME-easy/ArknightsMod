package ArknightsMod.Operators.Medic.Ansel;

import ArknightsMod.Helper.ArknightsImageMaster;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Vfx.LoopEffect.OperatorSpellingGreenEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class Ansel_1 extends AbstractSkill {
    private static final boolean IS_AUTOMATIC = false;
    private static final int MAX_SKILLPOINT = 16;
    private static final int ORIGINAL_SKILLPOINT = 5;
    private static final int SKILLTIMES = 5;
    private static final SkillType SKILL_TYPE = SkillType.NATURAL;

    public Ansel_1(){
        super(IS_AUTOMATIC, MAX_SKILLPOINT, ORIGINAL_SKILLPOINT, SKILLTIMES, SKILL_TYPE);
    }

    @Override
    public void ActiveEffect() {
        CardCrawlGame.sound.play("healboost");
        this.owner.changeMaxAttackCoolDown(-1);
    }

    @Override
    public void EndEffect() {
        this.owner.changeMaxAttackCoolDown(1);
    }

    @Override
    public AbstractGameEffect skillEffect() {
        return new OperatorSpellingGreenEffect(this.owner);
    }

    @Override
    public TextureAtlas.AtlasRegion getTexture() {
        return ArknightsImageMaster.ANSEL_1;
    }

    @Override
    public void afterAttack() {
        super.afterAttack();
        this.owner.changeSkillPoints(-1);
    }
}
