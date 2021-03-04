package ArknightsMod.Operators.Casters.Haze;

import ArknightsMod.Actions.ChangeMaxHpNotPercentAction;
import ArknightsMod.Helper.ArknightsImageMaster;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Vfx.LoopEffect.OperatorSpellingRedEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class Haze_2 extends AbstractSkill {
    private static final boolean IS_AUTOMATIC = false;
    private static final int MAX_SKILLPOINT = 15;
    private static final int ORIGINAL_SKILLPOINT = 0;
    private static final int SKILLTIMES = 12;
    private static final SkillType SKILL_TYPE = SkillType.NATURAL;

    private int amt;

    public Haze_2(){
        super(IS_AUTOMATIC, MAX_SKILLPOINT, ORIGINAL_SKILLPOINT, SKILLTIMES, SKILL_TYPE);
    }

    @Override
    public void ActiveEffect() {
        CardCrawlGame.sound.play("atkboost");
        owner.addAttack(3);
        owner.changeMaxAttackCoolDown(-1);
        this.amt = (int)(owner.maxHealth * 0.75F);
        this.addToBot(new ChangeMaxHpNotPercentAction(owner, -amt, false));
    }

    @Override
    public void EndEffect() {
        owner.addAttack(-3);
        owner.changeMaxAttackCoolDown(1);
        this.addToBot(new ChangeMaxHpNotPercentAction(owner, amt, false));
    }

    @Override
    public AbstractGameEffect skillEffect() {
        return new OperatorSpellingRedEffect(this.owner);
    }

    @Override
    public TextureAtlas.AtlasRegion getTexture() {
        return ArknightsImageMaster.HAZE_2;
    }

    @Override
    public void onUseEnergy(int amt) {
        this.owner.changeSkillPoints(-amt);
    }

    @Override
    public float onAttack(float dmg) {
        return dmg;
    }

    @Override
    public void afterAttack() {
        super.afterAttack();
    }

    @Override
    public String getSkillAnim() {
        return "Attack";
    }
}
