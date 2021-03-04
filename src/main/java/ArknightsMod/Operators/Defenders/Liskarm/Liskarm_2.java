package ArknightsMod.Operators.Defenders.Liskarm;

import ArknightsMod.Helper.ArknightsImageMaster;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Vfx.LoopEffect.OperatorSpellingRedEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class Liskarm_2 extends AbstractSkill {
    private static final boolean IS_AUTOMATIC = false;
    private static final int MAX_SKILLPOINT = 19;
    private static final int ORIGINAL_SKILLPOINT = 0;
    private static final int SKILLTIMES = 4;
    private static final SkillType SKILL_TYPE = SkillType.HIT;

    public Liskarm_2(){
        super(IS_AUTOMATIC, MAX_SKILLPOINT, ORIGINAL_SKILLPOINT, SKILLTIMES, SKILL_TYPE);
    }

    @Override
    public void ActiveEffect() {
        CardCrawlGame.sound.play("atkboost");
        this.owner.changeMaxAttackCoolDown(1);
        this.owner.attackTimes -= 1;
    }

    @Override
    public void EndEffect() {
        this.owner.changeMaxAttackCoolDown(-1);
        this.owner.attackTimes += 1;
    }

    @Override
    public AbstractGameEffect skillEffect() {
        return new OperatorSpellingRedEffect(this.owner);
    }

    @Override
    public TextureAtlas.AtlasRegion getTexture() {
        return ArknightsImageMaster.LISKARM_2;
    }

    @Override
    public String getSkillAnim() {
        return "Skill";
    }

    @Override
    public int getAttackTimes(int times) {
        return times;
    }

    @Override
    public float onAttack(float dmg) {
        return dmg;
    }

    @Override
    public void afterAttack() {
        super.afterAttack();

        if(AbstractDungeon.player.maxOrbs <= 0) {
            this.addToBot(new IncreaseMaxOrbAction(1));
        }
        for (int i = 0; i < 3; i++) {
            AbstractOrb orb = new Lightning();
            orb.passiveAmount = this.owner.getAttackToTarget();
            this.addToBot(new ChannelAction(new Lightning()));
        }
    }
}
