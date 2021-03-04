package ArknightsMod.Operators.Casters.Eyjafjalla;

import ArknightsMod.Actions.EyjaFireBallAction;
import ArknightsMod.Helper.ArknightsImageMaster;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Vfx.LoopEffect.OperatorSpellingRedEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class Eyjafjalla_3 extends AbstractSkill {
    private static final boolean IS_AUTOMATIC = false;
    private static final int MAX_SKILLPOINT = 40;
    private static final int ORIGINAL_SKILLPOINT = 25;
    private static final int SKILLTIMES = 1;
    private static final SkillType SKILL_TYPE = SkillType.NATURAL;

    public Eyjafjalla_3(){
        super(IS_AUTOMATIC, MAX_SKILLPOINT, ORIGINAL_SKILLPOINT, SKILLTIMES, SKILL_TYPE);
    }

    @Override
    public void ActiveEffect() {
        CardCrawlGame.sound.play("atkboost");
        owner.canAttack = false;
        owner.canPlayStart = false;
        owner.state.setAnimation(0, "Skill_Start", false);
        owner.playIdleAnim();
    }

    @Override
    public void EndEffect() {
        owner.canAttack = true;
        owner.canPlayStart = true;
        owner.state.setAnimation(0, "Skill_End", false);
        owner.playIdleAnim();
    }

    @Override
    public AbstractGameEffect skillEffect() {
        return new OperatorSpellingRedEffect(this.owner);
    }

    @Override
    public TextureAtlas.AtlasRegion getTexture() {
        return ArknightsImageMaster.EYJAFJALLA_3;
    }

    @Override
    public void onEndOfTurn() {
        super.onEndOfTurn();
        if(isSpelling) owner.changeSkillPoints(-1);
    }

    @Override
    public void onUseCard(AbstractCard card) {
        super.onUseCard(card);
        this.addToBot(new EyjaFireBallAction(owner));
        int[] atk = this.owner.getAttackToAll();
        this.addToBot(new DamageAllEnemiesAction(owner, atk, DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE, true));

    }

    @Override
    public int getAttackTimes(int times) {
        return times;
    }

    @Override
    public float onAttack(float dmg) {
        return dmg * 1.8F;
    }

    @Override
    public float onDamage(float dmg) {
        return dmg;
    }
}
