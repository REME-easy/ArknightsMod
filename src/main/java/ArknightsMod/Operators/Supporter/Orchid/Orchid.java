package ArknightsMod.Operators.Supporter.Orchid;

import ArknightsMod.Actions.PlzWaitAction;
import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Cards.Operator.Supporter.OrchidCard;
import ArknightsMod.Helper.GeneralHelper;
import ArknightsMod.Operators.AbstractOperator;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class Orchid extends AbstractOperator {
    private static final String ID = "Arknights_Orchid";
    private static final String ATLAS = "Operators/Orchid/orchid.atlas";
    private static final String JSON = "Operators/Orchid/orchid.json";
    private static final int MAX_HP = 9;
    private static final int ATK = 4;
    private static final int COOLDOWN = 3;
    private static final int DEF = 1;
    private static final int RESUMMON_TIME = 3;
    private static final int LEVEL = 3;

    public Orchid(float hb_x, float hb_y){
        super(ID, ATLAS, JSON, ATK, COOLDOWN, MAX_HP, DEF, RESUMMON_TIME, LEVEL, OperatorType.SUPPORTER, hb_x, hb_y);
        this.attackEffect = AbstractGameAction.AttackEffect.FIRE;
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Start", false);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.state.addAnimation(0, "Idle", true, 0.0F);
    }

    public Orchid(){
        this(0.0F, 0.0F);
    }

    @Override
    public AbstractOperator makeCopy() {
        return new Orchid();
    }

    @Override
    public float UseWhenAttack(AbstractCreature t, AbstractCreature s, float amt) {
        if(s == this){
            GeneralHelper.tempLoseStrength(t, s, 1);
        }
        return amt;
    }

    @Override
    public void UseWhenSummoned() {
        super.UseWhenSummoned();
        this.addToBot(new PlzWaitAction(0.25F));
        this.addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                Attack();
                isDone = true;
            }
        });
    }

    @Override
    public AbstractOperatorCard getOperatorCard() {
        AbstractOperatorCard card = new OrchidCard();
        card.skillindex = skillindex;
        card.currentSkill = currentBattleSkill;
        return card;
    }

    @Override
    public void playStartSfx() {
        CardCrawlGame.sound.play("orchid_start");
    }

    @Override
    public void playSkillSfx() {
        CardCrawlGame.sound.play("orchid_skill");
    }
}
