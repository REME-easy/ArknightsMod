package ArknightsMod.Operators.Defenders.Liskarm;

import ArknightsMod.Actions.AddSkillPointAction;
import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Cards.Operator.Defenders.LiskarmCard;
import ArknightsMod.Helper.GeneralHelper;
import ArknightsMod.Operators.AbstractOperator;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class Liskarm extends AbstractOperator {
    private static final String NAME_U = "Liskarm";
    private static final String NAME_L = "liskarm";
    private static final String ID = "Arknights_" + NAME_U;
    private static final String ATLAS = "Operators/" + NAME_U + "/" + NAME_L + ".atlas";
    private static final String JSON = "Operators/" + NAME_U + "/" + NAME_L + ".json";
    private static final int MAX_HP = 32;
    private static final int ATK = 4;
    private static final int COOLDOWN = 3;
    private static final int DEF = 5;
    private static final int RESUMMON_TIME = 3;
    private static final int LEVEL = 5;

    public Liskarm(float hb_x, float hb_y){
        super(ID, ATLAS, JSON, ATK, COOLDOWN, MAX_HP, DEF, RESUMMON_TIME, LEVEL, OperatorType.DEFENDER, hb_x, hb_y);

        AnimationState.TrackEntry e = this.state.setAnimation(0, "Start", false);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.state.addAnimation(0, "Idle", true, 0.0F);

    }

    public Liskarm(){
        this(0.0F, 0.0F);
    }

    @Override
    public AbstractOperator makeCopy() {
        return new Liskarm();
    }

    @Override
    public AbstractOperatorCard getOperatorCard() {
        AbstractOperatorCard card = new LiskarmCard();
        card.skillindex = skillindex;
        card.currentSkill = currentBattleSkill;
        return card;
    }

    @Override
    protected float onDamage(DamageInfo info, int amt) {
        float ct = super.onDamage(info, amt);
        AbstractOperator o1 = this;
        this.addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if(info.type != DamageInfo.DamageType.HP_LOSS && amt > 0) {
                    this.addToBot(new AddSkillPointAction(1, o1));
                    AbstractOperator o = GeneralHelper.getRandomOperatorHasSkillExceptSelf(o1);
                    if(o != null) {
                        this.addToBot(new AddSkillPointAction(1, o));
                    }
                }
                this.isDone = true;
            }
        });

        return (int)ct;
    }

    @Override
    public void playStartSfx() {
        CardCrawlGame.sound.play( NAME_L + "_start");
    }

    @Override
    public void playSkillSfx() { CardCrawlGame.sound.play(NAME_L + "_skill"); }

    public String playAttackAnim() {
        return "Attack_Loop";
    }

}
