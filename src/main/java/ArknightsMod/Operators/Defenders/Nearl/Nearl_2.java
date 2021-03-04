package ArknightsMod.Operators.Defenders.Nearl;

import ArknightsMod.Helper.ArknightsImageMaster;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Vfx.LoopEffect.OperatorSpellingGreenEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class Nearl_2 extends AbstractSkill {
    private static final boolean IS_AUTOMATIC = false;
    private static final int MAX_SKILLPOINT = 24;
    private static final int ORIGINAL_SKILLPOINT = 15;
    private static final int SKILLTIMES = 20;
    private static final SkillType SKILL_TYPE = SkillType.NATURAL;

    public Nearl_2(){
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
        return ArknightsImageMaster.NEARL_2;
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
    public void onUseEnergy(int amt) {
        super.onUseEnergy(amt);
        owner.changeSkillPoints(-amt);
    }
}
