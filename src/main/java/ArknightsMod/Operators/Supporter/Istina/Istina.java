package ArknightsMod.Operators.Supporter.Istina;

import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Cards.Operator.Supporter.IstinaCard;
import ArknightsMod.Operators.AbstractOperator;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class Istina extends AbstractOperator {
    private static final String NAME_U = "Istina";
    private static final String NAME_L = "istina";
    private static final String ID = "Arknights_" + NAME_U;
    private static final String ATLAS = "Operators/" + NAME_U + "/" + NAME_L + ".atlas";
    private static final String JSON = "Operators/" + NAME_U + "/" + NAME_L + ".json";
    private static final int MAX_HP = 13;
    private static final int ATK = 5;
    private static final int COOLDOWN = 3;
    private static final int DEF = 1;
    private static final int RESUMMON_TIME = 3;
    private static final int LEVEL = 5;

    public Istina(float hb_x, float hb_y){
        super(ID, ATLAS, JSON, ATK, COOLDOWN, MAX_HP, DEF, RESUMMON_TIME, LEVEL, OperatorType.SUPPORTER, hb_x, hb_y);
        this.attackEffect = AbstractGameAction.AttackEffect.FIRE;
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Start", false);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.state.addAnimation(0, "Idle", true, 0.0F);
    }

    public Istina(){
        this(0.0F, 0.0F);
    }

    @Override
    public AbstractOperator makeCopy() {
        return new Istina();
    }

    @Override
    public float UseWhenAttack(AbstractCreature t, AbstractCreature s, float amt) {
        if(s == this){
            addToBot(new DrawCardAction(1));
        }
        return amt;
    }

    @Override
    public AbstractOperatorCard getOperatorCard() {
        AbstractOperatorCard card = new IstinaCard();
        card.skillindex = skillindex;
        card.currentSkill = currentBattleSkill;
        return card;
    }

    @Override
    public void playIdleAnim() {
        if(currentBattleSkill != null && currentBattleSkill instanceof Istina_2 && currentBattleSkill.isSpelling){
            this.state.addAnimation(0, "Skill_Loop", true, 0.0F);
        }else{
            this.state.addAnimation(0, "Idle", true, 0.0F);
        }
    }

    @Override
    public void playStartSfx() {
        CardCrawlGame.sound.play( NAME_L + "_start");
    }

    @Override
    public void playSkillSfx() { CardCrawlGame.sound.play(NAME_L + "_skill"); }
}
