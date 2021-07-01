package ArknightsMod.Operators.Supporter.Sora;

import ArknightsMod.Actions.OperatorHealAction;
import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Cards.Operator.Supporter.SoraCard;
import ArknightsMod.Character.OperatorGroup;
import ArknightsMod.Operators.AbstractOperator;
import ArknightsMod.Powers.Operator.CantAttackPower;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class Sora extends AbstractOperator {
    private static final String NAME_U = "Sora";
    private static final String NAME_L = "sora";
    private static final String ID = "Arknights_" + NAME_U;
    private static final String ATLAS = "Operators/" + NAME_U + "/" + NAME_L + ".atlas";
    private static final String JSON = "Operators/" + NAME_U + "/" + NAME_L + ".json";
    private static final int MAX_HP = 13;
    private static final int ATK = 4;
    private static final int COOLDOWN = 3;
    private static final int DEF = 2;
    private static final int RESUMMON_TIME = 3;
    private static final int LEVEL = 5;

    public Sora(float hb_x, float hb_y){
        super(ID, ATLAS, JSON, ATK, COOLDOWN, MAX_HP, DEF, RESUMMON_TIME, LEVEL, OperatorType.SUPPORTER, hb_x, hb_y);
        this.showTarget = false;
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Start", false);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.state.addAnimation(0, "Idle", true, 0.0F);
    }

    public Sora(){
        this(0.0F, 0.0F);
    }

    @Override
    public AbstractOperator makeCopy() {
        return new Sora();
    }

    @Override
    public void Attack() {
        if(!this.hasPower(CantAttackPower.POWER_ID)) {
            for(AbstractOperator o : OperatorGroup.GetOperators()) {
                addToBot(new OperatorHealAction(o, this, 1));
            }
        }
    }

    @Override
    public AbstractOperatorCard getOperatorCard() {
        AbstractOperatorCard card = new SoraCard();
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
