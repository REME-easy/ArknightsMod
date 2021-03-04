package ArknightsMod.Operators.Specialists.THRMEX;

import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Cards.Operator.Specialists.THRMEXCard;
import ArknightsMod.Operators.AbstractOperator;
import ArknightsMod.Powers.Operator.AbstractArkPower;
import ArknightsMod.Powers.Operator.DelayedDetonationPower;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.Iterator;

public class THRMEX extends AbstractOperator {
    private static final String ID = "Arknights_ThermalEX";
    private static final String ATLAS = "Operators/ThermalEX/therex.atlas";
    private static final String JSON = "Operators/ThermalEX/therex.json";
    private static final int MAX_HP = 14;
    private static final int ATK = 3;
    private static final int COOLDOWN = 1;
    private static final int RESUMMON_TIME = 4;
    private static final int LEVEL = 1;

    public THRMEX(float hb_x, float hb_y){
        super(ID, ATLAS, JSON, ATK, COOLDOWN, MAX_HP, RESUMMON_TIME, LEVEL, OperatorType.SPECIALIST, hb_x, hb_y);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Start", false);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.state.addAnimation(0, "Idle", true, 0.0F);
        this.canAttack = false;
    }

    public THRMEX(){
        this(0.0F, 0.0F);
    }

    @Override
    public void UseWhenSummoned() {
        super.UseWhenSummoned();
        this.addToBot(new ApplyPowerAction(this, this, new DelayedDetonationPower(this)));
    }

    @Override
    public void Attack() {
        Iterator var5 = this.powers.iterator();
        AbstractPower p;
        while(var5.hasNext()) {
            p = (AbstractPower) var5.next();
            if(p instanceof AbstractArkPower){
                ((AbstractArkPower) p).onOperatorAttack();
            }
        }
    }

    @Override
    public AbstractOperator makeCopy() {
        return new THRMEX();
    }

    @Override
    public AbstractOperatorCard getOperatorCard() {
        AbstractOperatorCard card = new THRMEXCard();
        card.skillindex = skillindex;
        card.currentSkill = currentBattleSkill;
        return card;
    }

    @Override
    public String playAttackAnim() {
        return null;
    }

    @Override
    public void playDeathAnim() {
    }

    @Override
    public void playStartSfx() {
        CardCrawlGame.sound.play("THRMEX_start");
    }

    @Override
    public void playSkillSfx() {
        CardCrawlGame.sound.play("THRMEX_skill");
    }
}
