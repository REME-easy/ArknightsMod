package ArknightsMod.Operators.Defenders.Cuora;

import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Cards.Operator.Defenders.CuoraCard;
import ArknightsMod.Operators.AbstractOperator;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class Cuora extends AbstractOperator {
    private static final String NAME_U = "Cuora";
    private static final String NAME_L = "cuora";
    private static final String ID = "Arknights_" + NAME_U;
    private static final String ATLAS = "Operators/" + NAME_U + "/" + NAME_L + ".atlas";
    private static final String JSON = "Operators/" + NAME_U + "/" + NAME_L + ".json";
    private static final int MAX_HP = 29;
    private static final int ATK = 3;
    private static final int COOLDOWN = 3;
    private static final int DEF = 6;
    private static final int RESUMMON_TIME = 3;
    private static final int LEVEL = 4;

    public Cuora(float hb_x, float hb_y){
        super(ID, ATLAS, JSON, ATK, COOLDOWN, MAX_HP, DEF, RESUMMON_TIME, LEVEL, OperatorType.DEFENDER, hb_x, hb_y);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Start", false);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.state.addAnimation(0, "Idle", true, 0.0F);
    }

    public Cuora(){
        this(0.0F, 0.0F);
    }

    @Override
    public AbstractOperator makeCopy() {
        return new Cuora();
    }

    @Override
    public AbstractOperatorCard getOperatorCard() {
        AbstractOperatorCard card = new CuoraCard();
        card.skillindex = skillindex;
        card.currentSkill = currentBattleSkill;
        return card;
    }

    @Override
    public void playIdleAnim() {
        if(currentBattleSkill != null && currentBattleSkill instanceof Cuora_2 && currentBattleSkill.isSpelling){
            this.state.addAnimation(0, "Skill_Loop", true, 0.0F);
        }else{
            this.state.addAnimation(0, "Idle", true, 0.0F);
        }
    }

    @Override
    public void playStartSfx() {
        CardCrawlGame.sound.play( NAME_L + "_start");
    }

    @Override
    public void playSkillSfx() { CardCrawlGame.sound.play(NAME_L + "_skill"); }

}
