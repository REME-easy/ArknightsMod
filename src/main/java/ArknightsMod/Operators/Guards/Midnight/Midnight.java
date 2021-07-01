package ArknightsMod.Operators.Guards.Midnight;

import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Cards.Operator.Guards.MidnightCard;
import ArknightsMod.Operators.AbstractOperator;
import ArknightsMod.Powers.Counter.MidnightCounter;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class Midnight extends AbstractOperator {
    private static final String ID = "Arknights_Midnight";
    private static final String ATLAS = "Operators/Midnight/midnight.atlas";
    private static final String JSON = "Operators/Midnight/midnight.json";
    private static final int MAX_HP = 16;
    private static final int ATK = 5;
    private static final int COOLDOWN = 3;
    private static final int DEF = 3;
    private static final int RESUMMON_TIME = 3;
    private static final int LEVEL = 3;

    public Midnight(float hb_x, float hb_y){
        super(ID, ATLAS, JSON, ATK, COOLDOWN, MAX_HP, DEF, RESUMMON_TIME, LEVEL, OperatorType.GUARD, hb_x, hb_y);

        AnimationState.TrackEntry e = this.state.setAnimation(0, "Start", false);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.state.addAnimation(0, "Idle", true, 0.0F);

    }

    public Midnight(){
        this(0.0F, 0.0F);
    }

    @Override
    public AbstractOperator makeCopy() {
        return new Midnight();
    }

    @Override
    public AbstractOperatorCard getOperatorCard() {
        AbstractOperatorCard card = new MidnightCard();
        card.skillindex = skillindex;
        card.currentSkill = currentBattleSkill;
        return card;
    }

    @Override
    public void UseWhenSummoned() {
        super.UseWhenSummoned();
        this.addToBot(new ApplyPowerAction(this, this, new MidnightCounter(this)));
    }

//    @Override
//    public float UseWhenAttack(AbstractCreature t, AbstractCreature s, float amt) {
//        count++;
//        if(count == 3){
//            this.addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 1), 1));
//            count = 0;
//        }
//        return super.UseWhenAttack(t, s, amt);
//    }

    @Override
    public String playAttackAnim() {
        return "Combat";
    }

    @Override
    public void playStartSfx() {
        CardCrawlGame.sound.play("midn_start");
    }

    @Override
    public void playSkillSfx() {
        CardCrawlGame.sound.play("midn_skill");
    }
}
