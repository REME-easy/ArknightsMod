package ArknightsMod.Operators.Vanguards.Yato;

import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Cards.Operator.Vanguards.YatoCard;
import ArknightsMod.Operators.AbstractOperator;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class Yato extends AbstractOperator {
    private static final String ID = "Arknights_Yato";
    private static final String ATLAS = "Operators/Yato/yato.atlas";
    private static final String JSON = "Operators/Yato/yato.json";
    private static final int MAX_HP = 9;
    private static final int ATK = 3;
    private static final int COOLDOWN = 2;
    private static final int DEF = 2;
    private static final int RESUMMON_TIME = 1;
    private static final int LEVEL = 2;

    public Yato(float hb_x, float hb_y){
        super(ID, ATLAS, JSON, ATK, COOLDOWN, MAX_HP, DEF, RESUMMON_TIME, LEVEL, OperatorType.VANGUARD, hb_x, hb_y);

        AnimationState.TrackEntry e = this.state.setAnimation(0, "Start", false);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.state.addAnimation(0, "Idle", true, 0.0F);

    }

    public Yato(){
        this(0.0F, 0.0F);
    }

    @Override
    public AbstractOperator makeCopy() {
        return new Yato();
    }

    @Override
    public AbstractOperatorCard getOperatorCard() {
        AbstractOperatorCard card = new YatoCard();
        card.skillindex = skillindex;
        card.currentSkill = currentBattleSkill;
        return card;
    }

    @Override
    public void playStartSfx() {
        CardCrawlGame.sound.play("yato_start");
    }
}
