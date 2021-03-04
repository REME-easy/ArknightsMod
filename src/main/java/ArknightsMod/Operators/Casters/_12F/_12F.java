package ArknightsMod.Operators.Casters._12F;

import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Cards.Operator.Casters._12FCard;
import ArknightsMod.Operators.AbstractOperator;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class _12F extends AbstractOperator {
    private static final String ID = "Arknights_12F";
    private static final String ATLAS = "Operators/12F/12f.atlas";
    private static final String JSON = "Operators/12F/12f.json";
    private static final int MAX_HP = 13;
    private static final int ATK = 4;
    private static final int COOLDOWN = 4;

    private static final int RESUMMON_TIME = 3;
    private static final int LEVEL = 2;

    public _12F(float hb_x, float hb_y){
        super(ID, ATLAS, JSON, ATK, COOLDOWN, MAX_HP, RESUMMON_TIME, LEVEL, OperatorType.CASTER, hb_x, hb_y);
        this.attackToAll = true;
        this.attackEffect = AbstractGameAction.AttackEffect.FIRE;
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Start", false);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.state.addAnimation(0, "Idle", true, 0.0F);
    }

    public _12F(){
        this(0.0F, 0.0F);
    }

    @Override
    public AbstractOperator makeCopy() {
        return new _12F();
    }

    @Override
    public AbstractOperatorCard getOperatorCard() {
        AbstractOperatorCard card = new _12FCard();
        card.skillindex = skillindex;
        card.currentSkill = currentBattleSkill;
        return card;
    }

    @Override
    public void UseWhenSummoned() {
        super.UseWhenSummoned();
        this.addToBot(new GainBlockAction(this, 10));
    }

    @Override
    public void playStartSfx() {
        CardCrawlGame.sound.play("12F_start");
    }
}
