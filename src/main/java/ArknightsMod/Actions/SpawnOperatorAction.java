package ArknightsMod.Actions;

import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Character.OperatorGroup;
import ArknightsMod.Helper.GeneralHelper;
import ArknightsMod.Operators.AbstractOperator;
import ArknightsMod.Screens.TargetArrowScreen;
import ArknightsMod.Utils.OperatorPosition;
import ArknightsMod.Utils.TargetArrow;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SpawnOperatorAction extends AbstractGameAction implements TargetArrowScreen.TargetArrowScreenSubscriber {
    private static final Logger logger = LogManager.getLogger(SpawnOperatorAction.class);
    private AbstractOperator operator;
    private AbstractOperatorCard card;
    private static final String[] TEXT = CardCrawlGame.languagePack.getUIString("ARKNIGHTS_SELECT").TEXT;

    static {
//        float x = 0.0F;
//        float y = 0.0F;
//        boolean gotPos = false;
//        if(OperatorGroup.GetOperators().size() <= 7) {
//            Vector2 p = POINTS[7 - OperatorGroup.GetOperators().size()];
//            if(!occupied[operator.blockNum] || !occupied[operator.blockNum + 4]) {
//                if(occupied[operator.blockNum]) {
//                    p = POINTS[operator.blockNum + 4];
//                    occupied[operator.blockNum + 4] = true;
//                } else {
//                    p = POINTS[operator.blockNum];
//                    occupied[operator.blockNum] = true;
//                }
//                gotPos = true;
//            }
//
//            x = p.x;
//            y = p.y;
//        }
//
//        if(!gotPos){
//            x = AbstractDungeon.player.hb.x + (operator.blockNum - 1) * 150.0F - 50.0F;
//            y = AbstractDungeon.player.hb.y + MathUtils.random(-200.0F, 200.0F);
//        }
    }

    public SpawnOperatorAction(AbstractOperator operator, AbstractOperatorCard card){
        this.operator = operator;
        this.card = card;
        this.duration = DEFAULT_DURATION;
        TargetArrowScreen.register(this);
    }

    public void update(){
        if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.clearPostCombatActions();
            this.isDone = true;
        } else {
            if(this.duration == DEFAULT_DURATION) {

                if(OperatorGroup.GetOperators().size() <= OperatorGroup.maxSize) {
                    OperatorGroup.showHitboxes();
                    TargetArrowScreen.Inst.open(TEXT[3], TargetArrow.TargetType.Hitbox);
                }else {
                    GeneralHelper.addEffect(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, TEXT[4], true));
                    this.isDone = true;
                }

                this.tickDuration();
            }

            if(TargetArrowScreen.Inst.isActive && !TargetArrow.isActive) {
                this.isDone = true;
            }

        }
    }

    @Override
    public void receiveScreenTargetCreature(AbstractCreature source, AbstractCreature target) {

    }

    @Override
    public void receiveScreenTargetHitbox(Hitbox hb) {
        CardCrawlGame.sound.play("char_set");
        float x = hb.cX;
        float y = hb.y;
        if(hb instanceof OperatorPosition) {
            this.operator.index = ((OperatorPosition) hb).index;
        }
        OperatorGroup.OperatorsFields.operators.get(AbstractDungeon.player).addOperator(this.operator);
        if(card.currentSkill != null) {
            this.operator.skills = card.skills;
            this.operator.currentBattleSkill = card.currentSkill;
            this.operator.skillindex = card.skillindex;
        }
        this.operator.ChangeDamageType(this.operator.damageType);
        this.operator.getSkillOwner();
        this.operator.movePosition(x, y);
        this.operator.playStartAnim();
        this.operator.showHealthBar();
        this.operator.showSkillBar();
        this.operator.skillBarUpdatedEvent();
        this.operator.healthBarUpdatedEvent();
        this.operator.UseWhenSummoned();
        logger.info("召唤了" + this.operator.name);
        TargetArrowScreen.Inst.close();
        OperatorGroup.hideHitboxes();
        this.isDone = true;
    }

    @Override
    public void receiveEnd() {
        this.isDone = true;
    }
}
