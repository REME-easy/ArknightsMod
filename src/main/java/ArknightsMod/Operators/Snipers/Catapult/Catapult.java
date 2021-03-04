package ArknightsMod.Operators.Snipers.Catapult;

import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Cards.Operator.Snipers.CatapultCard;
import ArknightsMod.Operators.AbstractOperator;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class Catapult extends AbstractOperator {
    private static final String ID = "Arknights_Catapult";
    private static final String ATLAS = "Operators/Catap/catap.atlas";
    private static final String JSON = "Operators/Catap/catap.json";
    private static final int MAX_HP = 11;
    private static final int ATK = 6;
    private static final int COOLDOWN = 5;

    private static final int RESUMMON_TIME = 3;
    private static final int LEVEL = 3;

    public Catapult(float hb_x, float hb_y){
        super(ID, ATLAS, JSON, ATK, COOLDOWN, MAX_HP, RESUMMON_TIME, LEVEL, OperatorType.SNIPER, hb_x, hb_y);
        this.attackToAll = true;
        this.attackEffect = AbstractGameAction.AttackEffect.FIRE;
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Start", false);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.state.addAnimation(0, "Idle", true, 0.0F);

    }

    public Catapult(){
        this(0.0F, 0.0F);
    }

    @Override
    public AbstractOperator makeCopy() {
        return new Catapult();
    }

    @Override
    public AbstractOperatorCard getOperatorCard() {
        AbstractOperatorCard card = new CatapultCard();
        card.skillindex = skillindex;
        card.currentSkill = currentBattleSkill;
        return card;
    }

    @Override
    public void playStartSfx() {
        CardCrawlGame.sound.play("catap_start");
    }

    @Override
    public void playSkillSfx() {
        CardCrawlGame.sound.play("catap_skill");
    }
}
