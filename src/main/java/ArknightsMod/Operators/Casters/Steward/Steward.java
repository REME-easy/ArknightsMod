package ArknightsMod.Operators.Casters.Steward;

import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Cards.Operator.Casters.StewardCard;
import ArknightsMod.Operators.AbstractOperator;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Steward extends AbstractOperator {
    private static final String ID = "Arknights_Steward";
    private static final String ATLAS = "Operators/Steward/steward.atlas";
    private static final String JSON = "Operators/Steward/steward.json";
    private static final int MAX_HP = 11;
    private static final int ATK = 5;
    private static final int COOLDOWN = 3;
    private static final int DEF = 1;
    private static final int RESUMMON_TIME = 3;
    private static final int LEVEL = 3;

    public Steward(float hb_x, float hb_y){
        super(ID, ATLAS, JSON, ATK, COOLDOWN, MAX_HP, DEF, RESUMMON_TIME, LEVEL, OperatorType.CASTER, hb_x, hb_y);
        this.attackEffect = AbstractGameAction.AttackEffect.FIRE;
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Start", false);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.state.addAnimation(0, "Idle", true, 0.0F);
    }

    public Steward(){
        this(0.0F, 0.0F);
    }

    @Override
    public AbstractOperator makeCopy() {
        return new Steward();
    }

    @Override
    public AbstractOperatorCard getOperatorCard() {
        AbstractOperatorCard card = new StewardCard();
        card.skillindex = skillindex;
        card.currentSkill = currentBattleSkill;
        return card;
    }

    @Override
    public AbstractCreature getAttackTarget() {
        if (super.getAttackTarget() != null) return super.getAttackTarget();
        AbstractMonster m = null;
        for(AbstractMonster m1 : AbstractDungeon.getMonsters().monsters){
            if(m == null){
                m = m1;
            }
            if(m1.currentHealth > m.currentHealth && !m1.isDeadOrEscaped()){
                m = m1;
            }
        }
        return m;
    }

    @Override
    public void playStartSfx() {
        CardCrawlGame.sound.play("steward_start");
    }

    @Override
    public void playSkillSfx() {
        CardCrawlGame.sound.play("steward_skill");
    }
}
