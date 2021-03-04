package ArknightsMod.Operators.Specialists.Gravel;

import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Cards.Operator.Specialists.GravelCard;
import ArknightsMod.Operators.AbstractOperator;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class Gravel extends AbstractOperator {
    private static final String NAME_U = "Gravel";
    private static final String NAME_L = "gravel";
    private static final String ID = "Arknights_" + NAME_U;
    private static final String ATLAS = "Operators/" + NAME_U + "/" + NAME_L + ".atlas";
    private static final String JSON = "Operators/" + NAME_U + "/" + NAME_L + ".json";
    private static final int MAX_HP = 12;
    private static final int ATK = 4;
    private static final int COOLDOWN = 2;
    private static final int RESUMMON_TIME = 1;
    private static final int LEVEL = 4;

    public Gravel(float hb_x, float hb_y){
        super(ID, ATLAS, JSON, ATK, COOLDOWN, MAX_HP, RESUMMON_TIME,LEVEL, OperatorType.SPECIALIST, hb_x, hb_y);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Start", false);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.state.addAnimation(0, "Idle", true, 0.0F);

    }

    public Gravel(){
        this(0.0F, 0.0F);
    }

    @Override
    public AbstractOperator makeCopy() {
        return new Gravel();
    }

    @Override
    public AbstractOperatorCard getOperatorCard() {
        AbstractOperatorCard card = new GravelCard();
        card.skillindex = skillindex;
        card.currentSkill = currentBattleSkill;
        return card;
    }

    @Override
    public void playStartSfx() {
        if(MathUtils.randomBoolean()) {
            CardCrawlGame.sound.play(NAME_L + "_start_1");
        }else {
            CardCrawlGame.sound.play(NAME_L + "_start_2");
        }
    }
}
