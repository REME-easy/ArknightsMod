package ArknightsMod.Actions;

import ArknightsMod.Character.OperatorGroup;
import ArknightsMod.Operators.AbstractOperator;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;

public class OperatorHealAction extends AbstractGameAction {
    private float amt;
    public OperatorHealAction(AbstractCreature target, AbstractCreature source) {
        this.startDuration = this.duration;
        if (Settings.FAST_MODE) {
            this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        }
        this.actionType = ActionType.HEAL;
        amt = 0;
        if(source instanceof AbstractOperator) {
            amt = ((AbstractOperator) source).getAttackToTarget();
        }
        for(AbstractOperator o : OperatorGroup.GetOperators()){
            amt = o.UseWhenHeal(target, source, amt);
        }
        this.target = target;
    }

    public OperatorHealAction(AbstractCreature target, AbstractCreature source, float amt) {
        this.startDuration = this.duration;
        if (Settings.FAST_MODE) {
            this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        }
        this.actionType = ActionType.HEAL;
        this.amt = amt;

        for(AbstractOperator o : OperatorGroup.GetOperators()){
            amt = o.UseWhenHeal(target, source, amt);
        }
        this.target = target;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            this.target.heal((int)amt);
            this.isDone = true;
        }
    }
}
