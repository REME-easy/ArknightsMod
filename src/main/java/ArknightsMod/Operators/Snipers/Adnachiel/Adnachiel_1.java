package ArknightsMod.Operators.Snipers.Adnachiel;

import ArknightsMod.Helper.ArknightsImageMaster;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Vfx.LoopEffect.OperatorSpellingRedEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class Adnachiel_1 extends AbstractSkill {
    private static final boolean IS_AUTOMATIC = false;
    private static final int MAX_SKILLPOINT = 20;
    private static final int ORIGINAL_SKILLPOINT = 0;
    private static final int SKILLTIMES = 4;
    private static final SkillType SKILL_TYPE = SkillType.NATURAL;

    public Adnachiel_1(){
        super(IS_AUTOMATIC, MAX_SKILLPOINT, ORIGINAL_SKILLPOINT, SKILLTIMES, SKILL_TYPE);
    }

    @Override
    public void ActiveEffect() {
        CardCrawlGame.sound.play("atkboost");
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
        return ArknightsImageMaster.ATK_UP_1;
    }

    @Override
    public float onAttack(float dmg) {
        return dmg + 2;
    }

    @Override
    public void afterAttack() {
        super.afterAttack();
        this.owner.changeSkillPoints(-1);
    }
}
