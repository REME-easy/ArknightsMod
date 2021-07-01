package ArknightsMod.Operators.Snipers.Kroos;

import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Cards.Operator.Snipers.KroosCard;
import ArknightsMod.Operators.AbstractOperator;
import ArknightsMod.Powers.Counter.KroosCounter;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class Kroos extends AbstractOperator {
    private static final String ID = "Arknights_Kroos";
    private static final String ATLAS = "Operators/Kroos/kroos.atlas";
    private static final String JSON = "Operators/Kroos/kroos.json";
    private static final int MAX_HP = 10;
    private static final int ATK = 4;
    private static final int COOLDOWN = 2;
    private static final int DEF = 1;
    private static final int RESUMMON_TIME = 3;
    private static final int LEVEL = 3;

    public Kroos(float hb_x, float hb_y){
        super(ID, ATLAS, JSON, ATK, COOLDOWN, MAX_HP, DEF, RESUMMON_TIME, LEVEL, OperatorType.SNIPER, hb_x, hb_y);

        AnimationState.TrackEntry e = this.state.setAnimation(0, "Start", false);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.state.addAnimation(0, "Idle", true, 0.0F);

    }

    public Kroos(){
        this(0.0F, 0.0F);
    }

    @Override
    public AbstractOperator makeCopy() {
        return new Kroos();
    }

    @Override
    public void UseWhenSummoned() {
        super.UseWhenSummoned();
        this.addToBot(new ApplyPowerAction(this, this, new KroosCounter(this)));
    }

    @Override
    public AbstractOperatorCard getOperatorCard() {
        AbstractOperatorCard card = new KroosCard();
        card.skillindex = skillindex;
        card.currentSkill = currentBattleSkill;
        return card;
    }

    @Override
    public void playStartSfx() {
        CardCrawlGame.sound.play("kroos_start");
    }

    @Override
    public void playSkillSfx() { CardCrawlGame.sound.play("kroos_skill"); }
}
