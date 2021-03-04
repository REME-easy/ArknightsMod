package ArknightsMod.Operators.Snipers.Angel;

import ArknightsMod.Helper.ArknightsImageMaster;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Vfx.LoopEffect.OperatorSpellingRedEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class Angel_3 extends AbstractSkill {
    private static final boolean IS_AUTOMATIC = true;
    private static final int MAX_SKILLPOINT = 21;
    private static final int ORIGINAL_SKILLPOINT = 10;
    private static final int SKILLTIMES = 8;
    private static final SkillType SKILL_TYPE = SkillType.NATURAL;

    public Angel_3(){
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
        return ArknightsImageMaster.ANGEL_3;
    }

    @Override
    public int getAttackTimes(int times) {
        return times + 4;
    }

    @Override
    public void onUseEnergy(int amt) {
        this.owner.changeSkillPoints(-amt);
    }
}
