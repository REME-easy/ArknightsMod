package ArknightsMod.Operators.Casters.Haze;

import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Cards.Operator.Casters.HazeCard;
import ArknightsMod.Operators.AbstractOperator;
import ArknightsMod.Powers.Operator.FirmPower;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class Haze extends AbstractOperator {
    private static final String NAME_U = "Haze";
    private static final String NAME_L = "haze";
    private static final String ID = "Arknights_" + NAME_U;
    private static final String ATLAS = "Operators/" + NAME_U + "/" + NAME_L + ".atlas";
    private static final String JSON = "Operators/" + NAME_U + "/" + NAME_L + ".json";
    private static final int MAX_HP = 14;
    private static final int ATK = 6;
    private static final int COOLDOWN = 3;
    private static final int DEF = 1;
    private static final int RESUMMON_TIME = 3;
    private static final int LEVEL = 4;

    public Haze(float hb_x, float hb_y){
        super(ID, ATLAS, JSON, ATK, COOLDOWN, MAX_HP, DEF, RESUMMON_TIME, LEVEL, OperatorType.CASTER, hb_x, hb_y);
        this.attackEffect = AbstractGameAction.AttackEffect.FIRE;
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Start", false);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.state.addAnimation(0, "Idle", true, 0.0F);
    }

    public Haze(){
        this(0.0F, 0.0F);
    }

    @Override
    public AbstractOperator makeCopy() {
        return new Haze();
    }

    @Override
    public AbstractOperatorCard getOperatorCard() {
        AbstractOperatorCard card = new HazeCard();
        card.skillindex = skillindex;
        card.currentSkill = currentBattleSkill;
        return card;
    }

    @Override
    public void AfterAttack() {
        super.AfterAttack();
        if(this.lastTarget != null) {
            this.addToBot(new ApplyPowerAction(lastTarget, this, new FirmPower(lastTarget, -1)));
        }
    }

    @Override
    public void playStartSfx() {
        CardCrawlGame.sound.play( NAME_L + "_start");
    }

    @Override
    public void playSkillSfx() { CardCrawlGame.sound.play(NAME_L + "_skill"); }

    public String playAttackAnim() {
        return "Attack_Loop";
    }

}
