package ArknightsMod.Operators.Casters.Durin;

import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Cards.Operator.Casters.DurinCard;
import ArknightsMod.Operators.AbstractOperator;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class Durin extends AbstractOperator {
    private static final String ID = "Arknights_Durin";
    private static final String ATLAS = "Operators/Durin/durin.atlas";
    private static final String JSON = "Operators/Durin/durin.json";
    private static final int MAX_HP = 10;
    private static final int ATK = 4;
    private static final int COOLDOWN = 3;
    private static final int DEF = 1;

    private static final int RESUMMON_TIME = 3;
    private static final int LEVEL = 2;

    public Durin(float hb_x, float hb_y){
        super(ID, ATLAS, JSON, ATK, COOLDOWN, MAX_HP, DEF, RESUMMON_TIME, LEVEL, OperatorType.CASTER, hb_x, hb_y);
        this.attackEffect = AbstractGameAction.AttackEffect.FIRE;
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Start", false);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.state.addAnimation(0, "Idle", true, 0.0F);
    }

    public Durin(){
        this(0.0F, 0.0F);
    }

    @Override
    public void UseWhenSummoned() {
        super.UseWhenSummoned();
        this.addToBot(new DrawCardAction(1));
    }

    @Override
    public AbstractOperator makeCopy() {
        return new Durin();
    }

    @Override
    public AbstractOperatorCard getOperatorCard() {
        AbstractOperatorCard card = new DurinCard();
        card.skillindex = skillindex;
        card.currentSkill = currentBattleSkill;
        return card;
    }

    @Override
    public void playStartSfx() {
        CardCrawlGame.sound.play("durin_start");
    }
}
