package ArknightsMod.Operators.Casters.Haze;

import ArknightsMod.Helper.ArknightsImageMaster;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Vfx.LoopEffect.OperatorSpellingRedEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class Haze_1 extends AbstractSkill {
    private static final boolean IS_AUTOMATIC = false;
    private static final int MAX_SKILLPOINT = 16;
    private static final int ORIGINAL_SKILLPOINT = 9;
    private static final int SKILLTIMES = 12;
    private static final SkillType SKILL_TYPE = SkillType.NATURAL;

    public Haze_1(){
        super(IS_AUTOMATIC, MAX_SKILLPOINT, ORIGINAL_SKILLPOINT, SKILLTIMES, SKILL_TYPE);
    }

    @Override
    public void ActiveEffect() {
        CardCrawlGame.sound.play("atkboost");
        owner.addAttack(3);
    }

    @Override
    public void EndEffect() {
        owner.addAttack(-3);
    }

    @Override
    public AbstractGameEffect skillEffect() {
        return new OperatorSpellingRedEffect(this.owner);
    }

    @Override
    public TextureAtlas.AtlasRegion getTexture() {
        return ArknightsImageMaster.ATK_UP_2;
    }

    @Override
    public void onUseEnergy(int amt) {
        this.owner.changeSkillPoints(-amt);
    }

    @Override
    public String getSkillAnim() {
        return "Attack_Loop";
    }

}
