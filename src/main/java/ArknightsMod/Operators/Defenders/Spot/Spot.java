package ArknightsMod.Operators.Defenders.Spot;

import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Cards.Operator.Defenders.SpotCard;
import ArknightsMod.Operators.AbstractOperator;
import ArknightsMod.Powers.Operator.SolidnessPower;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class Spot extends AbstractOperator {
    private static final String ID = "Arknights_Spot";
    private static final String ATLAS = "Operators/Spot/spot.atlas";
    private static final String JSON = "Operators/Spot/spot.json";
    private static final int MAX_HP = 18;
    private static final int ATK = 3;
    private static final int COOLDOWN = 2;
    private static final int RESUMMON_TIME = 3;
    private static final int LEVEL = 3;

    public Spot(float hb_x, float hb_y){
        super(ID, ATLAS, JSON, ATK, COOLDOWN, MAX_HP, RESUMMON_TIME, LEVEL, OperatorType.DEFENDER, hb_x, hb_y);

        AnimationState.TrackEntry e = this.state.setAnimation(0, "Start", false);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.state.addAnimation(0, "Idle", true, 0.0F);

    }

    public Spot(){
        this(0.0F, 0.0F);
    }

    @Override
    public AbstractOperator makeCopy() {
        return new Spot();
    }

    @Override
    public AbstractOperatorCard getOperatorCard() {
        AbstractOperatorCard card = new SpotCard();
        card.skillindex = skillindex;
        card.currentSkill = currentBattleSkill;
        return card;
    }

    @Override
    public void UseWhenSummoned() {
        super.UseWhenSummoned();
        this.addToBot(new ApplyPowerAction(this, this, new SolidnessPower(this, 1)));
    }

    @Override
    public float UseWhenHeal(AbstractCreature t, AbstractCreature s, float amt) {
        if(s == this){
            this.addToBot(new GainBlockAction(t, s, 4, true));
        }
        return amt;
    }

    @Override
    public void playStartSfx() {
        CardCrawlGame.sound.play("spot_start");
    }

    @Override
    public void playSkillSfx() {
        CardCrawlGame.sound.play("spot_skill");
    }
}
