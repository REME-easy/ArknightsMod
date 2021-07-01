package ArknightsMod.Operators.Vanguards.Zima;

import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Cards.Operator.Vanguards.ZimaCard;
import ArknightsMod.Operators.AbstractOperator;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class Zima extends AbstractOperator {
    private static final String NAME_U = "Zima";
    private static final String NAME_L = "zima";
    private static final String ID = "Arknights_" + NAME_U;
    private static final String ATLAS = "Operators/" + NAME_U + "/" + NAME_L + ".atlas";
    private static final String JSON = "Operators/" + NAME_U + "/" + NAME_L + ".json";
    private static final int MAX_HP = 20;
    private static final int ATK = 4;
    private static final int COOLDOWN = 2;
    private static final int DEF = 4;
    private static final int RESUMMON_TIME = 3;
    private static final int LEVEL = 5;

    public Zima(float hb_x, float hb_y){
        super(ID, ATLAS, JSON, ATK, COOLDOWN, MAX_HP, DEF, RESUMMON_TIME,LEVEL, OperatorType.VANGUARD, hb_x, hb_y);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Start", false);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.state.addAnimation(0, "Idle", true, 0.0F);
    }

    public Zima(){
        this(0.0F, 0.0F);
    }

    @Override
    public AbstractOperator makeCopy() {
        return new Zima();
    }


    @Override
    public AbstractOperatorCard getOperatorCard() {
        AbstractOperatorCard card = new ZimaCard();
        card.skillindex = skillindex;
        card.currentSkill = currentBattleSkill;
        return card;
    }

    @Override
    public void playStartSfx() {
        CardCrawlGame.sound.play( NAME_L + "_start");
    }

    @Override
    public void playSkillSfx() { CardCrawlGame.sound.play(NAME_L + "_skill"); }
}
