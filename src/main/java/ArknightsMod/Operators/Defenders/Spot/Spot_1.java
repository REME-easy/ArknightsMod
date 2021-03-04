package ArknightsMod.Operators.Defenders.Spot;

import ArknightsMod.Helper.ArknightsImageMaster;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Vfx.LoopEffect.OperatorSpellingGreenEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class Spot_1 extends AbstractSkill {
    private static final boolean IS_AUTOMATIC = false;
    private static final int MAX_SKILLPOINT = 20;
    private static final int ORIGINAL_SKILLPOINT = 10;
    private static final int SKILLTIMES = 4;
    private static final SkillType SKILL_TYPE = SkillType.NATURAL;

    public Spot_1(){
        super(IS_AUTOMATIC, MAX_SKILLPOINT, ORIGINAL_SKILLPOINT, SKILLTIMES, SKILL_TYPE);
    }

    @Override
    public void ActiveEffect() {
        CardCrawlGame.sound.play("healboost");
        this.owner.isHealer = true;
        //this.addToBot(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, 2), 2));
        owner.addAttack(2);

    }

    @Override
    public void EndEffect() {
        this.owner.isHealer = false;
        //this.addToBot(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, -2), -2));
        owner.addAttack(-2);

    }

    @Override
    public AbstractGameEffect skillEffect() {
        return new OperatorSpellingGreenEffect(this.owner);
    }

    @Override
    public TextureAtlas.AtlasRegion getTexture() {
        return ArknightsImageMaster.SPOT_1;
    }

    @Override
    public String getSkillAnim() {
        return "Skill";
    }

    @Override
    public float onAttack(float dmg) {
        return dmg;
    }

    @Override
    public void afterAttack() {
        super.afterAttack();
        this.owner.changeSkillPoints(-1);
    }
}
