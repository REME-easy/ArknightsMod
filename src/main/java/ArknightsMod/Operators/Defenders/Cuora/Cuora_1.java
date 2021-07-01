package ArknightsMod.Operators.Defenders.Cuora;

import ArknightsMod.Helper.ArknightsImageMaster;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Powers.Operator.SolidnessPower;
import ArknightsMod.Vfx.LoopEffect.OperatorSpellingRedEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class Cuora_1 extends AbstractSkill {
    private static final boolean IS_AUTOMATIC = true;
    private static final int MAX_SKILLPOINT = 19;
    private static final int ORIGINAL_SKILLPOINT = 8;
    private static final int SKILLTIMES = 1;
    private static final SkillType SKILL_TYPE = SkillType.NATURAL;

    public Cuora_1(){
        super(IS_AUTOMATIC, MAX_SKILLPOINT, ORIGINAL_SKILLPOINT, SKILLTIMES, SKILL_TYPE);
    }

    @Override
    public void ActiveEffect() {
        CardCrawlGame.sound.play("defboost");
        addToBot(new ApplyPowerAction(owner, owner, new SolidnessPower(owner, 1)));
        owner.changeSkillPoints(-1);
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
        return ArknightsImageMaster.DEF_UP_2;
    }
}
