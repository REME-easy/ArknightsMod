package ArknightsMod.Operators.Guards.Silverash;

import ArknightsMod.Helper.ArknightsImageMaster;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Vfx.Common.SvashShockWaveEffect;
import ArknightsMod.Vfx.LoopEffect.OperatorSpellingBlueEffect;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class Svash_3 extends AbstractSkill {
    private static final boolean IS_AUTOMATIC = false;
    private static final int MAX_SKILLPOINT = 40;
    private static final int ORIGINAL_SKILLPOINT = 30;
    private static final int SKILLTIMES = 20;
    private static final SkillType SKILL_TYPE = SkillType.NATURAL;

    public Svash_3(){
        super(IS_AUTOMATIC, MAX_SKILLPOINT, ORIGINAL_SKILLPOINT, SKILLTIMES, SKILL_TYPE);
    }

    @Override
    public void ActiveEffect() {
        CardCrawlGame.sound.play("atkboost");
        owner.attackToAll = true;
        this.owner.attackEffect = AbstractGameAction.AttackEffect.LIGHTNING;
    }

    @Override
    public void EndEffect() {
        owner.attackToAll = false;
        this.owner.attackEffect = AbstractGameAction.AttackEffect.SLASH_DIAGONAL;
    }

    @Override
    public AbstractGameEffect skillEffect() {
        return new OperatorSpellingBlueEffect(this.owner);
    }

    @Override
    public TextureAtlas.AtlasRegion getTexture() {
        return ArknightsImageMaster.SVRASH_3;
    }

    @Override
    public String getSkillAnim() {
        return "Skill";
    }

    @Override
    public void onUseEnergy(int amt) {
        owner.changeSkillPoints(-amt);
    }

    @Override
    public float onAttack(float dmg) {
        return dmg * 2.4F;
    }

    @Override
    public void afterAttack() {
        super.afterAttack();
        this.addToBot(new VFXAction(new SvashShockWaveEffect(owner.hb.cX, owner.hb.cY, Color.WHITE.cpy())));
    }

    @Override
    public float onDamage(float dmg) {
        return dmg * 1.7F;
    }
}
