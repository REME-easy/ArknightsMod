package ArknightsMod.Operators.Casters.Lava;

import ArknightsMod.Actions.AddSkillPointAction;
import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Cards.Operator.Casters.LavaCard;
import ArknightsMod.Character.OperatorGroup;
import ArknightsMod.Operators.AbstractOperator;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class Lava extends AbstractOperator {
    private static final String ID = "Arknights_Lava";
    private static final String ATLAS = "Operators/Lava/lava.atlas";
    private static final String JSON = "Operators/Lava/lava.json";
    private static final int MAX_HP = 10;
    private static final int ATK = 5;
    private static final int COOLDOWN = 4;
    private static final int DEF = 1;
    private static final int RESUMMON_TIME = 3;
    private static final int LEVEL = 3;

    public Lava(float hb_x, float hb_y){
        super(ID, ATLAS, JSON, ATK, COOLDOWN, MAX_HP, DEF, RESUMMON_TIME, LEVEL, OperatorType.CASTER, hb_x, hb_y);
        this.attackToAll = true;
        this.attackEffect = AbstractGameAction.AttackEffect.FIRE;
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Start", false);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.state.addAnimation(0, "Idle", true, 0.0F);
    }

    public Lava(){
        this(0.0F, 0.0F);
    }

    @Override
    public AbstractOperator makeCopy() {
        return new Lava();
    }

    @Override
    public AbstractOperatorCard getOperatorCard() {
        AbstractOperatorCard card = new LavaCard();
        card.skillindex = skillindex;
        card.currentSkill = currentBattleSkill;
        return card;
    }

    @Override
    public void UseWhenSummoned() {
        super.UseWhenSummoned();
        for(AbstractOperator o : OperatorGroup.GetOperators()) {
            this.addToBot(new AddSkillPointAction(5, o));
        }
    }

    @Override
    public void playStartSfx() {
        CardCrawlGame.sound.play("lava_start");
    }

    @Override
    public void playSkillSfx() {
        CardCrawlGame.sound.play("lava_skill");
    }
}
