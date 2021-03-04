package ArknightsMod.Operators.Medic.Ansel;

import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Cards.Operator.Medic.AnselCard;
import ArknightsMod.Operators.AbstractOperator;
import ArknightsMod.Powers.Counter.AnselCounter;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class Ansel extends AbstractOperator {
    private static final String ID = "Arknights_Ansel";
    private static final String ATLAS = "Operators/Ansel/ansel.atlas";
    private static final String JSON = "Operators/Ansel/ansel.json";
    private static final int MAX_HP = 11;
    private static final int ATK = 5;
    private static final int COOLDOWN = 3;
    private static final int RESUMMON_TIME = 3;
    private static final int LEVEL = 3;

    public Ansel(float hb_x, float hb_y){
        super(ID, ATLAS, JSON, ATK, COOLDOWN, MAX_HP, RESUMMON_TIME, LEVEL, OperatorType.MEDIC, hb_x, hb_y);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Start", false);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.state.addAnimation(0, "Idle", true, 0.0F);
    }

    public Ansel(){
        this(0.0F, 0.0F);
    }

    @Override
    public AbstractOperator makeCopy() {
        return new Ansel();
    }

    @Override
    public AbstractOperatorCard getOperatorCard() {
        AbstractOperatorCard card = new AnselCard();
        card.skillindex = skillindex;
        card.currentSkill = currentBattleSkill;
        return card;
    }

    @Override
    public void UseWhenSummoned() {
        super.UseWhenSummoned();
        this.addToBot(new ApplyPowerAction(this, this, new AnselCounter(this)));
    }

    //    @Override
//    public float UseWhenAttack(AbstractCreature t, AbstractCreature s, float amt) {
//        count++;
//        if(count == 3){
//            this.attackTimes++;
//        }
//        if(count == 4){
//            this.attackTimes--;
//            count = 1;
//        }
//        return super.UseWhenAttack(t, s, amt);
//    }

    @Override
    public void playStartSfx() {
        CardCrawlGame.sound.play("ansel_start");
    }

    @Override
    public void playSkillSfx() {
        CardCrawlGame.sound.play("ansel_skill");
    }
}
