package ArknightsMod.Actions;

import ArknightsMod.Character.OperatorGroup;
import ArknightsMod.Operators.AbstractOperator;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class OperatorSuicideAction extends AbstractGameAction {
    private AbstractOperator m;

    public OperatorSuicideAction(AbstractOperator target) {
        this.duration = 0.2F;
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.m = target;
    }

    public void update() {
        if (this.duration == 0.2F) {
            this.m.currentHealth = 0;
            this.m.die();
            this.m.healthBarUpdatedEvent();
        }

        this.tickDuration();
    }
}
