package ArknightsMod.Operators.Guards.Chen;

import ArknightsMod.Actions.AddSkillPointAction;
import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Cards.Operator.Guards.ChenCard;
import ArknightsMod.Character.OperatorGroup;
import ArknightsMod.Operators.AbstractOperator;
import ArknightsMod.Operators.Skills.AbstractSkill.SkillType;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class Chen extends AbstractOperator {
    private static final String NAME_U = "Chen";
    private static final String NAME_L = "chen";
    private static final String ID = "Arknights_" + NAME_U;
    private static final String ATLAS = "Operators/" + NAME_U + "/" + NAME_L + ".atlas";
    private static final String JSON = "Operators/" + NAME_U + "/" + NAME_L + ".json";
    private static final int MAX_HP = 28;
    private static final int ATK = 6;
    private static final int COOLDOWN = 3;

    private static final int RESUMMON_TIME = 3;
    private static final int LEVEL = 6;

    public Chen(float hb_x, float hb_y){
        super(ID, ATLAS, JSON, ATK, COOLDOWN, MAX_HP, RESUMMON_TIME, LEVEL, OperatorType.GUARD, hb_x, hb_y);
        this.attackTimes = 2;
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Start", false);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.state.addAnimation(0, "Idle", true, 0.0F);

    }

    public Chen(){
        this(0.0F, 0.0F);
    }

    @Override
    public AbstractOperator makeCopy() {
        return new Chen();
    }

    @Override
    public AbstractOperatorCard getOperatorCard() {
        AbstractOperatorCard card = new ChenCard();
        card.skillindex = skillindex;
        card.currentSkill = currentBattleSkill;
        return card;
    }

    @Override
    public void EndOfTurnAction() {
        super.EndOfTurnAction();
        for(AbstractOperator o : OperatorGroup.GetOperators()) {
            if(o.currentBattleSkill != null && (o.currentBattleSkill.skillType == SkillType.HIT || o.currentBattleSkill.skillType == SkillType.ATTACK))
                this.addToBot(new AddSkillPointAction(2, o));
        }
    }

    @Override
    public String playAttackAnim() {
        return "Attack";
    }

    @Override
    public void playStartSfx() {
        CardCrawlGame.sound.play( NAME_L + "_start");
    }

    @Override
    public void playSkillSfx() {
        if(this.currentBattleSkill instanceof Chen_1)
            CardCrawlGame.sound.play( NAME_L + "_skill_1");
    }
}
