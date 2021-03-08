package ArknightsMod.Operators.Specialists.Red;

import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Cards.Operator.Specialists.RedCard;
import ArknightsMod.Character.OperatorGroup;
import ArknightsMod.Operators.AbstractOperator;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Red extends AbstractOperator {
    private static final String NAME_U = "Red";
    private static final String NAME_L = "red";
    private static final String ID = "Arknights_" + NAME_U;
    private static final String ATLAS = "Operators/" + NAME_U + "/" + NAME_L + ".atlas";
    private static final String JSON = "Operators/" + NAME_U + "/" + NAME_L + ".json";
    private static final int MAX_HP = 13;
    private static final int ATK = 5;
    private static final int COOLDOWN = 2;
    private static final int RESUMMON_TIME = 1;
    private static final int LEVEL = 5;

    public Red(float hb_x, float hb_y){
        super(ID, ATLAS, JSON, ATK, COOLDOWN, MAX_HP, RESUMMON_TIME,LEVEL, OperatorType.SPECIALIST, hb_x, hb_y);

        AnimationState.TrackEntry e = this.state.setAnimation(0, "Start", false);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.state.addAnimation(0, "Idle", true, 0.0F);

    }

    public Red(){
        this(0.0F, 0.0F);
    }

    @Override
    public AbstractOperator makeCopy() {
        return new Red();
    }

    @Override
    public AbstractOperatorCard getOperatorCard() {
        AbstractOperatorCard card = new RedCard();
        card.skillindex = skillindex;
        card.currentSkill = currentBattleSkill;
        return card;
    }

    @Override
    public AbstractCreature getAttackTarget() {
        AbstractMonster m = OperatorGroup.lastMonsterAttack;
        if(m != null && !m.isDeadOrEscaped() && !m.isDead)
            return m;
        else {
            if (super.getAttackTarget() != null) return super.getAttackTarget();
            return null;
        }
    }

    @Override
    public void playStartSfx() {
        if(MathUtils.randomBoolean()) {
            CardCrawlGame.sound.play(NAME_L + "_start_1");
        }else {
            CardCrawlGame.sound.play(NAME_L + "_start_2");
        }
    }
}
