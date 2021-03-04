package ArknightsMod.Operators.Medic.Hibisc;

import ArknightsMod.Helper.ArknightsImageMaster;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Vfx.LoopEffect.OperatorSpellingGreenEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class Hibisc_1 extends AbstractSkill {
    private static final boolean IS_AUTOMATIC = false;
    private static final int MAX_SKILLPOINT = 15;
    private static final int ORIGINAL_SKILLPOINT = 0;
    private static final int SKILLTIMES = 2;
    private static final SkillType SKILL_TYPE = SkillType.NATURAL;

    public Hibisc_1(){
        super(IS_AUTOMATIC, MAX_SKILLPOINT, ORIGINAL_SKILLPOINT, SKILLTIMES, SKILL_TYPE);
    }

    @Override
    public void ActiveEffect() {
        //this.addToBot(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, 3), 3));
        CardCrawlGame.sound.play("healboost");
        owner.addAttack(3);

    }

    @Override
    public void EndEffect() {
        //this.addToBot(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, -3), -3));
        owner.addAttack(-3);

    }

    @Override
    public AbstractGameEffect skillEffect() {
        return new OperatorSpellingGreenEffect(this.owner);
    }

    @Override
    public TextureAtlas.AtlasRegion getTexture() {
        return ArknightsImageMaster.HEAL_UP_1;
    }

    @Override
    public void onEndOfTurn() {
        super.onEndOfTurn();
        owner.changeSkillPoints(-1);
    }
}
