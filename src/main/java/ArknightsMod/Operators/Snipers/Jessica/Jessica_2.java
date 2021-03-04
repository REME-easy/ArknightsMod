package ArknightsMod.Operators.Snipers.Jessica;

import ArknightsMod.Helper.ArknightsImageMaster;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Powers.Operator.CamouflagePower;
import ArknightsMod.Vfx.LoopEffect.OperatorSpellingRedEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class Jessica_2 extends AbstractSkill {
    private static final boolean IS_AUTOMATIC = false;
    private static final int MAX_SKILLPOINT = 22;
    private static final int ORIGINAL_SKILLPOINT = 7;
    private static final int SKILLTIMES = 2;
    private static final SkillType SKILL_TYPE = SkillType.NATURAL;

    public Jessica_2(){
        super(IS_AUTOMATIC, MAX_SKILLPOINT, ORIGINAL_SKILLPOINT, SKILLTIMES, SKILL_TYPE);
    }

    @Override
    public void ActiveEffect() {
        CardCrawlGame.sound.play("atkboost");
        owner.addAttack(2);
        this.addToBot(new ApplyPowerAction(this.owner, this.owner, new CamouflagePower(this.owner, 2)));
    }

    @Override
    public void EndEffect() {
        owner.addAttack(-2);
    }

    @Override
    public AbstractGameEffect skillEffect() {
        return new OperatorSpellingRedEffect(this.owner);
    }

    @Override
    public TextureAtlas.AtlasRegion getTexture() {
        return ArknightsImageMaster.JESSICA_2;
    }

    @Override
    public void onEndOfTurn() {
        super.onEndOfTurn();
        owner.changeSkillPoints(-1);
    }
}
