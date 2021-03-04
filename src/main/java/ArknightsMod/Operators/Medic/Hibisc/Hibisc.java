package ArknightsMod.Operators.Medic.Hibisc;

import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Cards.Operator.Medic.HibiscCard;
import ArknightsMod.Operators.AbstractOperator;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

public class Hibisc extends AbstractOperator {
    private static final String ID = "Arknights_Hibisc";
    private static final String ATLAS = "Operators/Hibisc/hibisc.atlas";
    private static final String JSON = "Operators/Hibisc/hibisc.json";
    private static final int MAX_HP = 12;
    private static final int ATK = 4;
    private static final int COOLDOWN = 3;
    private static final int RESUMMON_TIME = 3;
    private static final int LEVEL = 3;

    public Hibisc(float hb_x, float hb_y){
        super(ID, ATLAS, JSON, ATK, COOLDOWN, MAX_HP, RESUMMON_TIME, LEVEL, OperatorType.MEDIC, hb_x, hb_y);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Start", false);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.state.addAnimation(0, "Idle", true, 0.0F);
    }

    public Hibisc(){
        this(0.0F, 0.0F);
    }

    @Override
    public AbstractOperator makeCopy() {
        return new Hibisc();
    }

    @Override
    public AbstractOperatorCard getOperatorCard() {
        AbstractOperatorCard card = new HibiscCard();
        card.skillindex = skillindex;
        card.currentSkill = currentBattleSkill;
        return card;
    }

    @Override
    public void UseWhenSummoned() {
        super.UseWhenSummoned();
        for(AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            this.addToBot(new ApplyPowerAction(m, this, new WeakPower(m, 1, false)));
        }
    }

    @Override
    public void playStartSfx() {
        CardCrawlGame.sound.play("hibisc_start");
    }

    @Override
    public void playSkillSfx() {
        CardCrawlGame.sound.play("hibisc_skill");
    }
}
