package ArknightsMod.Actions;

import ArknightsMod.Operators.AbstractOperator;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class OperatorsChangeAnimationAction extends AbstractGameAction {
    private AbstractOperator operator;
    private String anim;
    private boolean isLoop;
    private float delay;
    public OperatorsChangeAnimationAction(AbstractOperator operator, String anim, boolean isLoop, float delay){
        this.operator = operator;
        this.anim = anim;
        this.delay = this.duration = delay;
        this.isLoop = isLoop;
    }

    public void update(){
        if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.clearPostCombatActions();
            this.isDone = true;
        } else {
            if(this.delay == this.duration){
                this.operator.state.setAnimation(0, anim, isLoop);
                this.operator.playIdleAnim();
            }
            this.tickDuration();
        }
    }
}
