package ArknightsMod.Operators.Supporter.Mayer;

import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Cards.Operator.Supporter.MayerCard;
import ArknightsMod.Cards.Operator.Token.MotterCard;
import ArknightsMod.Operators.AbstractOperator;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class Mayer extends AbstractOperator {
    private static final String NAME_U = "Mayer";
    private static final String NAME_L = "mayer";
    private static final String ID = "Arknights_" + NAME_U;
    private static final String ATLAS = "Operators/" + NAME_U + "/" + NAME_L + ".atlas";
    private static final String JSON = "Operators/" + NAME_U + "/" + NAME_L + ".json";
    private static final int MAX_HP = 10;
    private static final int ATK = 4;
    private static final int COOLDOWN = 3;
    private static final int DEF = 1;
    private static final int RESUMMON_TIME = 3;
    private static final int LEVEL = 5;

    public Mayer(float hb_x, float hb_y){
        super(ID, ATLAS, JSON, ATK, COOLDOWN, MAX_HP, DEF, RESUMMON_TIME, LEVEL, OperatorType.SUPPORTER, hb_x, hb_y);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Start", false);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.state.addAnimation(0, "Idle", true, 0.0F);
    }

    public Mayer(){
        this(0.0F, 0.0F);
    }

    @Override
    public AbstractOperator makeCopy() {
        return new Mayer();
    }

    @Override
    public AbstractOperatorCard getOperatorCard() {
        AbstractOperatorCard card = new MayerCard();
        card.skillindex = skillindex;
        card.currentSkill = currentBattleSkill;
        return card;
    }

    @Override
    public void UseWhenSummoned() {
        super.UseWhenSummoned();
        this.addToBot(new MakeTempCardInHandAction(new MotterCard(), 3));
    }

    @Override
    public void playStartSfx() {
        CardCrawlGame.sound.play( NAME_L + "_start");
    }

    @Override
    public void playSkillSfx() { CardCrawlGame.sound.play(NAME_L + "_skill"); }
}
