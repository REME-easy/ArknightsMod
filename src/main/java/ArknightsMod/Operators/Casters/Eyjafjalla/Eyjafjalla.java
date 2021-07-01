package ArknightsMod.Operators.Casters.Eyjafjalla;

import ArknightsMod.Actions.AddSkillPointAction;
import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Cards.Operator.Casters.EyjafjallaCard;
import ArknightsMod.Character.OperatorGroup;
import ArknightsMod.Operators.AbstractOperator;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class Eyjafjalla extends AbstractOperator {
    private static final String ID = "Arknights_Eyjafjalla";
    private static final String ATLAS = "Operators/Eyjafjalla/mgoat.atlas";
    private static final String JSON = "Operators/Eyjafjalla/mgoat.json";
    private static final int MAX_HP = 17;
    private static final int ATK = 6;
    private static final int COOLDOWN = 3;
    private static final int DEF = 1;

    private static final int RESUMMON_TIME = 3;
    private static final int LEVEL = 6;

    public Eyjafjalla(float hb_x, float hb_y){
        super(ID, ATLAS, JSON, ATK, COOLDOWN, MAX_HP, DEF, RESUMMON_TIME, LEVEL, OperatorType.CASTER, hb_x, hb_y);
        this.attackEffect = AbstractGameAction.AttackEffect.FIRE;
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Start", false);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.state.addAnimation(0, "Idle", true, 0.0F);

    }

    public Eyjafjalla(){
        this(0.0F, 0.0F);
    }

    @Override
    public AbstractOperator makeCopy() {
        return new Eyjafjalla();
    }

    @Override
    public AbstractOperatorCard getOperatorCard() {
        AbstractOperatorCard card = new EyjafjallaCard();
        card.skillindex = skillindex;
        card.currentSkill = currentBattleSkill;
        return card;
    }

    @Override
    public void UseWhenSummoned() {
        super.UseWhenSummoned();
        for(AbstractOperator o: OperatorGroup.GetOperators()){
            if(o.operatorType == OperatorType.CASTER){
//                this.addToBot(new ApplyPowerAction(o, this, new StrengthPower(o, 1), 1));
                o.addAttack(1);
            }
        }
        this.addToBot(new AddSkillPointAction(AbstractDungeon.monsterHpRng.random(3, 8), this));
    }

    @Override
    public void OtherOperatorEnter(AbstractOperator o) {
        super.OtherOperatorEnter(o);
        if(o.operatorType == OperatorType.CASTER && o != this){
//            this.addToBot(new ApplyPowerAction(o, this, new StrengthPower(o, 1), 1));
            o.addAttack(1);
        }
    }

    @Override
    public void UseWhenEscape() {
        super.UseWhenEscape();
        for(AbstractOperator o: OperatorGroup.GetOperators()){
            if(o.operatorType == OperatorType.CASTER){
//                this.addToBot(new ApplyPowerAction(o, this, new StrengthPower(o, -1), -1));
                o.addAttack(-1);
            }
        }
    }

    @Override
    public void playIdleAnim() {
        if(currentBattleSkill != null && currentBattleSkill instanceof Eyjafjalla_3 && currentBattleSkill.isSpelling){
            this.state.addAnimation(0, "Skill_Loop", true, 0.0F);
        }else{
            this.state.addAnimation(0, "Idle", true, 0.0F);
        }
    }

    @Override
    public void playStartSfx() {
        CardCrawlGame.sound.play("amgoat_start");
    }

    @Override
    public void playSkillSfx() {
        CardCrawlGame.sound.play("amgoat_skill");
    }
}
