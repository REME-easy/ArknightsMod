package ArknightsMod.Operators.Guards.Silverash;

import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Cards.Operator.Guards.SilverashCard;
import ArknightsMod.Operators.AbstractOperator;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class Silverash extends AbstractOperator {
    private static final String ID = "Arknights_SilverAsh";
    private static final String ATLAS = "Operators/Silverash/svash.atlas";
    private static final String JSON = "Operators/Silverash/svash.json";
    private static final int MAX_HP = 24;
    private static final int ATK = 9;
    private static final int COOLDOWN = 3;
    private static final int DEF = 4;
    private static final int RESUMMON_TIME = 3;
    private static final int LEVEL = 6;

    public Silverash(float hb_x, float hb_y){
        super(ID, ATLAS, JSON, ATK, COOLDOWN, MAX_HP, DEF, RESUMMON_TIME, LEVEL, OperatorType.GUARD, hb_x, hb_y);

        AnimationState.TrackEntry e = this.state.setAnimation(0, "Start", false);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.state.addAnimation(0, "Idle", true, 0.0F);

    }

    public Silverash(){
        this(0.0F, 0.0F);
    }

    @Override
    public AbstractOperator makeCopy() {
        return new Silverash();
    }

    @Override
    public AbstractOperatorCard getOperatorCard() {
        AbstractOperatorCard card = new SilverashCard();
        card.skillindex = skillindex;
        card.currentSkill = currentBattleSkill;
        return card;
    }

    @Override
    public String playAttackAnim() {
        return "Combat";
    }

    @Override
    public void playStartSfx() {
        CardCrawlGame.sound.play("svash_start");
    }

    @Override
    public void playSkillSfx() {
        CardCrawlGame.sound.play("svash_skill");
    }
}
