package ArknightsMod.Operators.Defenders.NoirCorne;

import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Cards.Operator.Defenders.NoirCorneCard;
import ArknightsMod.Operators.AbstractOperator;
import ArknightsMod.Powers.Operator.SolidnessPower;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class NoirCorne extends AbstractOperator {
    private static final String ID = "Arknights_NoirCorne";
    private static final String ATLAS = "Operators/NoirCorne/noirc.atlas";
    private static final String JSON = "Operators/NoirCorne/noirc.json";
    private static final int MAX_HP = 17;
    private static final int ATK = 2;
    private static final int COOLDOWN = 2;

    private static final int RESUMMON_TIME = 1;
    private static final int LEVEL = 2;

    public NoirCorne(float hb_x, float hb_y){
        super(ID, ATLAS, JSON, ATK, COOLDOWN, MAX_HP, RESUMMON_TIME, LEVEL, OperatorType.DEFENDER, hb_x, hb_y);

        AnimationState.TrackEntry e = this.state.setAnimation(0, "Start", false);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.state.addAnimation(0, "Idle", true, 0.0F);

    }

    public NoirCorne(){
        this(0.0F, 0.0F);
    }

    @Override
    public void UseWhenSummoned() {
        super.UseWhenSummoned();
        this.addToBot(new ApplyPowerAction(this, this, new SolidnessPower(this, 2), 2));
    }

    @Override
    public AbstractOperator makeCopy() {
        return new NoirCorne();
    }

    @Override
    public AbstractOperatorCard getOperatorCard() {
        AbstractOperatorCard card = new NoirCorneCard();
        card.skillindex = skillindex;
        card.currentSkill = currentBattleSkill;
        return card;
    }

    @Override
    public void playStartSfx() {
        CardCrawlGame.sound.play("noircorne_start");
    }
}
