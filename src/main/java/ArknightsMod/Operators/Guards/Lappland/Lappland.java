package ArknightsMod.Operators.Guards.Lappland;

import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Cards.Operator.Guards.LapplandCard;
import ArknightsMod.Operators.AbstractOperator;
import ArknightsMod.Powers.Operator.SilencePower;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class Lappland extends AbstractOperator {
    private static final String NAME_U = "Lappland";
    private static final String NAME_L = "lappland";
    private static final String ID = "Arknights_" + NAME_U;
    private static final String ATLAS = "Operators/" + NAME_U + "/" + NAME_L + ".atlas";
    private static final String JSON = "Operators/" + NAME_U + "/" + NAME_L + ".json";
    private static final int MAX_HP = 23;
    private static final int ATK = 7;
    private static final int COOLDOWN = 3;
    private static final int DEF = 3;
    private static final int RESUMMON_TIME = 3;
    private static final int LEVEL = 5;

    public Lappland(float hb_x, float hb_y){
        super(ID, ATLAS, JSON, ATK, COOLDOWN, MAX_HP, DEF, RESUMMON_TIME, LEVEL, OperatorType.GUARD, hb_x, hb_y);

        AnimationState.TrackEntry e = this.state.setAnimation(0, "Start", false);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.state.addAnimation(0, "Idle", true, 0.0F);

    }

    public Lappland(){
        this(0.0F, 0.0F);
    }

    @Override
    public AbstractOperator makeCopy() {
        return new Lappland();
    }

    @Override
    public float UseWhenAttack(AbstractCreature t, AbstractCreature s, float amt) {
        if(!t.isDeadOrEscaped()) {
            this.addToBot(new ApplyPowerAction(t, this, new SilencePower(t)));
        }
        return amt;
    }

    @Override
    public AbstractOperatorCard getOperatorCard() {
        AbstractOperatorCard card = new LapplandCard();
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
