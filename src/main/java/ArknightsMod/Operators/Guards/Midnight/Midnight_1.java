package ArknightsMod.Operators.Guards.Midnight;

import ArknightsMod.Helper.ArknightsImageMaster;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Vfx.LoopEffect.OperatorSpellingRedEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class Midnight_1 extends AbstractSkill {
    private static final boolean IS_AUTOMATIC = false;
    private static final int MAX_SKILLPOINT = 31;
    private static final int ORIGINAL_SKILLPOINT = 17;
    private static final int SKILLTIMES = 8;
    private static final SkillType SKILL_TYPE = SkillType.NATURAL;

    public Midnight_1(){
        super(IS_AUTOMATIC, MAX_SKILLPOINT, ORIGINAL_SKILLPOINT, SKILLTIMES, SKILL_TYPE);
    }

    @Override
    public void ActiveEffect() {
//        this.owner.damageType = DamageType.THORNS;
        CardCrawlGame.sound.play("atkboost");
        this.owner.ChangeDamageType(DamageType.THORNS);
        this.owner.attackEffect = AbstractGameAction.AttackEffect.LIGHTNING;
        //this.addToBot(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, 3), 3));
        owner.addAttack(5);


    }

    @Override
    public void EndEffect() {
//        this.owner.damageType = DamageType.NORMAL;
        this.owner.ChangeDamageType(DamageType.NORMAL);
        this.owner.attackEffect = AbstractGameAction.AttackEffect.SLASH_DIAGONAL;
        //this.addToBot(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, -3), -3));
        owner.addAttack(-5);

    }

    @Override
    public AbstractGameEffect skillEffect() {
        return new OperatorSpellingRedEffect(this.owner);
    }

    @Override
    public TextureAtlas.AtlasRegion getTexture() {
        return ArknightsImageMaster.MIDNIGHT_1;
    }

    @Override
    public String getSkillAnim() {
        return "Attack_Loop";
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
