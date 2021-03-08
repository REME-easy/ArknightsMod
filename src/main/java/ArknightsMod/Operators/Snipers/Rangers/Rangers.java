package ArknightsMod.Operators.Snipers.Rangers;

import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Cards.Operator.Snipers.RangersCard;
import ArknightsMod.Operators.AbstractOperator;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FlightPower;

public class Rangers extends AbstractOperator {
    private static final String ID = "Arknights_Rangers";
    private static final String ATLAS = "Operators/Rangers/ranger.atlas";
    private static final String JSON = "Operators/Rangers/ranger.json";
    private static final int MAX_HP = 8;
    private static final int ATK = 3;
    private static final int COOLDOWN = 2;

    private static final int RESUMMON_TIME = 2;
    private static final int LEVEL = 2;

    public Rangers(float hb_x, float hb_y){
        super(ID, ATLAS, JSON, ATK, COOLDOWN, MAX_HP, RESUMMON_TIME, LEVEL, OperatorType.SNIPER, hb_x, hb_y);

        AnimationState.TrackEntry e = this.state.setAnimation(0, "Start", false);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.state.addAnimation(0, "Idle", true, 0.0F);

    }

    public Rangers(){
        this(0.0F, 0.0F);
    }

    @Override
    public void UseWhenSummoned() {
        super.UseWhenSummoned();
    }

    @Override
    public float UseWhenAttack(AbstractCreature t, AbstractCreature s, float amt) {
        if(s == this && t.hasPower(FlightPower.POWER_ID)) {
            return amt * 2;
        }
        return super.UseWhenAttack(t, s, amt);
    }

    @Override
    public AbstractCreature getAttackTarget() {
        if (super.getAttackTarget() != null) return super.getAttackTarget();
        for(AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if(m.hasPower(FlightPower.POWER_ID)) {
                return m;
            }
        }
        return super.getAttackTarget();
    }

    @Override
    public AbstractOperator makeCopy() {
        return new Rangers();
    }

    @Override
    public AbstractOperatorCard getOperatorCard() {
        AbstractOperatorCard card = new RangersCard();
        card.skillindex = skillindex;
        card.currentSkill = currentBattleSkill;
        return card;
    }

    @Override
    public void playStartSfx() {
        CardCrawlGame.sound.play("ranger_start");
    }

    @Override
    public void playSkillSfx() {
        //CardCrawlGame.sound.play("ranger_skill");
    }
}
