package ArknightsMod.Operators.Vanguards.Vigna;

import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Cards.Operator.Vanguards.VignaCard;
import ArknightsMod.Operators.AbstractOperator;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class Vigna extends AbstractOperator {
    private static final String NAME_U = "Vigna";
    private static final String NAME_L = "vigna";
    private static final String ID = "Arknights_" + NAME_U;
    private static final String ATLAS = "Operators/" + NAME_U + "/" + NAME_L + ".atlas";
    private static final String JSON = "Operators/" + NAME_U + "/" + NAME_L + ".json";
    private static final int MAX_HP = 18;
    private static final int ATK = 5;
    private static final int COOLDOWN = 2;
    private static final int DEF = 1;
    private static final int RESUMMON_TIME = 3;
    private static final int LEVEL = 4;

    public Vigna(float hb_x, float hb_y){
        super(ID, ATLAS, JSON, ATK, COOLDOWN, MAX_HP, DEF, RESUMMON_TIME,LEVEL, OperatorType.VANGUARD, hb_x, hb_y);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Start", false);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.state.addAnimation(0, "Idle", true, 0.0F);
    }

    public Vigna(){
        this(0.0F, 0.0F);
    }

    @Override
    public AbstractOperator makeCopy() {
        return new Vigna();
    }

    @Override
    public AbstractOperatorCard getOperatorCard() {
        AbstractOperatorCard card = new VignaCard();
        card.skillindex = skillindex;
        card.currentSkill = currentBattleSkill;
        return card;
    }

    @Override
    public void AfterAttack() {
        super.AfterAttack();
        this.addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if(lastTarget.isDeadOrEscaped() || lastTarget.isDead) {
                    this.addToBot(new GainEnergyAction(1));
                    this.addToBot(new ApplyPowerAction(Vigna.this, Vigna.this, new StrengthPower(Vigna.this, 1)));
                }
                isDone = true;
            }
        });
    }

    @Override
    public void playStartSfx() {
        CardCrawlGame.sound.play( NAME_L + "_start");
    }

    @Override
    public void playSkillSfx() { CardCrawlGame.sound.play(NAME_L + "_skill"); }
}
