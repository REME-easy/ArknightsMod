package ArknightsMod.Operators.Medic.Lancet2;

import ArknightsMod.Actions.OperatorHealAction;
import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Cards.Operator.Medic.Lancet2Card;
import ArknightsMod.Character.OperatorGroup;
import ArknightsMod.Operators.AbstractOperator;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class Lancet2 extends AbstractOperator {
    private static final String NAME_U = "Lancet2";
    private static final String NAME_L = "lancet2";
    private static final String ID = "Arknights_" + NAME_U;
    private static final String ATLAS = "Operators/" + NAME_U + "/" + NAME_L + ".atlas";
    private static final String JSON = "Operators/" + NAME_U + "/" + NAME_L + ".json";
    private static final int MAX_HP = 3;
    private static final int ATK = 2;
    private static final int COOLDOWN = 3;
    private static final int DEF = 0;
    private static final int RESUMMON_TIME = 4;
    private static final int LEVEL = 1;

    public Lancet2(float hb_x, float hb_y){
        super(ID, ATLAS, JSON, ATK, COOLDOWN, MAX_HP, DEF, RESUMMON_TIME, LEVEL, OperatorType.MEDIC, hb_x, hb_y);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Start", false);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.state.addAnimation(0, "Idle", true, 0.0F);
    }

    public Lancet2(){
        this(0.0F, 0.0F);
    }

    @Override
    public AbstractOperator makeCopy() {
        return new Lancet2();
    }

    @Override
    public AbstractOperatorCard getOperatorCard() {
        AbstractOperatorCard card = new Lancet2Card();
        card.skillindex = skillindex;
        card.currentSkill = currentBattleSkill;
        return card;
    }

    @Override
    public void UseWhenSummoned() {
        super.UseWhenSummoned();
        for(AbstractOperator o : OperatorGroup.GetOperators()) {
            if(o != this)
                addToBot(new OperatorHealAction(o, this, 3));
        }
        addToBot(new OperatorHealAction(AbstractDungeon.player, this, 3));
    }

    @Override
    public void playStartSfx() {
        CardCrawlGame.sound.play( NAME_L + "_start");
    }
}
