package ArknightsMod.Operators.Guards.Popukar;

import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Cards.Operator.Guards.PopukarCard;
import ArknightsMod.Operators.AbstractOperator;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class Popukar extends AbstractOperator {
    private static final String ID = "Arknights_Popukar";
    private static final String ATLAS = "Operators/Popukar/popukar.atlas";
    private static final String JSON = "Operators/Popukar/popukar.json";
    private static final int MAX_HP = 18;
    private static final int ATK = 5;
    private static final int COOLDOWN = 3;
    private static final int DEF = 2;
    private static final int RESUMMON_TIME = 3;
    private static final int LEVEL = 3;

    public Popukar(float hb_x, float hb_y){
        super(ID, ATLAS, JSON, ATK, COOLDOWN, MAX_HP, DEF, RESUMMON_TIME, LEVEL, OperatorType.GUARD, hb_x, hb_y);
        this.attackTargets = 2;
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Start", false);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.state.addAnimation(0, "Idle", true, 0.0F);

    }

    public Popukar(){
        this(0.0F, 0.0F);
    }

    @Override
    public AbstractOperator makeCopy() {
        return new Popukar();
    }

    @Override
    public AbstractOperatorCard getOperatorCard() {
        AbstractOperatorCard card = new PopukarCard();
        card.skillindex = skillindex;
        card.currentSkill = currentBattleSkill;
        return card;
    }

    @Override
    public float UseWhenAttack(AbstractCreature t, AbstractCreature s, float amt) {
        return super.UseWhenAttack(t, s, amt);
    }

    @Override
    public String playAttackAnim() {
        return "Attack";
    }

    @Override
    public void playStartSfx() {
        CardCrawlGame.sound.play("papukar_start");
    }

    @Override
    public void playSkillSfx() {
        CardCrawlGame.sound.play("papukar_skill");
    }
}
