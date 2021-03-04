package ArknightsMod.Operators.Snipers.Angel;

import ArknightsMod.Actions.PlzWaitAction;
import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Cards.Operator.Snipers.AngelCard;
import ArknightsMod.Operators.AbstractOperator;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class Angel extends AbstractOperator {
    private static final String ID = "Arknights_Angel";
    private static final String ATLAS = "Operators/Angel/angel.atlas";
    private static final String JSON = "Operators/Angel/angel.json";
    private static final int MAX_HP = 18;
    private static final int ATK = 6;
    private static final int COOLDOWN = 2;

    private static final int RESUMMON_TIME = 3;
    private static final int LEVEL = 6;

    public Angel(float hb_x, float hb_y){
        super(ID, ATLAS, JSON, ATK, COOLDOWN, MAX_HP, RESUMMON_TIME, LEVEL, OperatorType.SNIPER, hb_x, hb_y);

        AnimationState.TrackEntry e = this.state.setAnimation(0, "Start", false);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.state.addAnimation(0, "Idle", true, 0.0F);

    }

    public Angel(){
        this(0.0F, 0.0F);
    }

    @Override
    public void UseWhenSummoned() {
        super.UseWhenSummoned();
        this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 1)));
        this.addToBot(new GainBlockAction(AbstractDungeon.player, 5));
        this.addToBot(new PlzWaitAction(1.0F));
        this.addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                Attack();
                isDone = true;
            }
        });
    }

    @Override
    public AbstractOperator makeCopy() {
        return new Angel();
    }

    @Override
    public AbstractOperatorCard getOperatorCard() {
        AbstractOperatorCard card = new AngelCard();
        card.skillindex = skillindex;
        card.currentSkill = currentBattleSkill;
        return card;
    }

    @Override
    public void playStartSfx() {
        CardCrawlGame.sound.play("angel_start");
    }

    @Override
    public void playSkillSfx() {
        CardCrawlGame.sound.play("angel_skill");
    }
}
