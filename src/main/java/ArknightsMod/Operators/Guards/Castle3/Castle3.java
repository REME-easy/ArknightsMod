package ArknightsMod.Operators.Guards.Castle3;

import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Cards.Operator.Guards.Castle3Card;
import ArknightsMod.Character.OperatorGroup;
import ArknightsMod.Operators.AbstractOperator;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class Castle3 extends AbstractOperator {
    private static final String NAME_U = "Castle3";
    private static final String NAME_L = "castle3";
    private static final String ID = "Arknights_" + NAME_U;
    private static final String ATLAS = "Operators/" + NAME_U + "/" + NAME_L + ".atlas";
    private static final String JSON = "Operators/" + NAME_U + "/" + NAME_L + ".json";
    private static final int MAX_HP = 11;
    private static final int ATK = 3;
    private static final int COOLDOWN = 3;
    private static final int DEF = 1;
    private static final int RESUMMON_TIME = 4;
    private static final int LEVEL = 1;

    public Castle3(float hb_x, float hb_y){
        super(ID, ATLAS, JSON, ATK, COOLDOWN, MAX_HP, DEF, RESUMMON_TIME,LEVEL, OperatorType.GUARD, hb_x, hb_y);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Start", false);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.state.addAnimation(0, "Idle", true, 0.0F);
    }

    public Castle3(){
        this(0.0F, 0.0F);
    }

    @Override
    public AbstractOperator makeCopy() {
        return new Castle3();
    }

    @Override
    public void UseWhenSummoned() {
        super.UseWhenSummoned();
        for(AbstractOperator o : OperatorGroup.GetOperators()) {
            addToBot(new GainBlockAction(o, 2));
            addToBot(new ApplyPowerAction(o, this, new StrengthPower(o, 1)));
        }
    }

    @Override
    public AbstractOperatorCard getOperatorCard() {
        AbstractOperatorCard card = new Castle3Card();
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
