package ArknightsMod.Operators.Vanguards.Texas;

import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Cards.Operator.Vanguards.TexasCard;
import ArknightsMod.Operators.AbstractOperator;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class Texas extends AbstractOperator {
    private static final String ID = "Arknights_Texas";
    private static final String ATLAS = "Operators/Texas/texas.atlas";
    private static final String JSON = "Operators/Texas/texas.json";
    private static final int MAX_HP = 19;
    private static final int ATK = 5;
    private static final int COOLDOWN = 2;
    private static final int DEF = 3;
    private static final int RESUMMON_TIME = 3;
    private static final int LEVEL = 5;

    public Texas(float hb_x, float hb_y){
        super(ID, ATLAS, JSON, ATK, COOLDOWN, MAX_HP, DEF, RESUMMON_TIME,LEVEL, OperatorType.VANGUARD, hb_x, hb_y);

        AnimationState.TrackEntry e = this.state.setAnimation(0, "Start", false);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.state.addAnimation(0, "Idle", true, 0.0F);

    }

    public Texas(){
        this(0.0F, 0.0F);
    }

    @Override
    public AbstractOperator makeCopy() {
        return new Texas();
    }

    @Override
    public AbstractOperatorCard getOperatorCard() {
        AbstractOperatorCard card = new TexasCard();
        card.skillindex = skillindex;
        card.currentSkill = currentBattleSkill;
        return card;
    }

    @Override
    public void playStartSfx() {
        CardCrawlGame.sound.play("texas_start");
    }

    @Override
    public void playSkillSfx() {
        CardCrawlGame.sound.play("texas_skill");
    }

    public String playAttackAnim() {
        return "Attack_Loop";
    }
}
