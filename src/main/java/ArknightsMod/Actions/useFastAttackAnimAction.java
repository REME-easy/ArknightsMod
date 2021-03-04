package ArknightsMod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class useFastAttackAnimAction extends AbstractGameAction {
    private AbstractCreature target;

    public useFastAttackAnimAction(AbstractCreature target) {
        this.duration = 0.0F;
        this.target = target;
    }


    @Override
    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.isDone = true;
        } else {
            if (this.duration == 0.0F) {
                this.target.useFastAttackAnimation();
            }
            this.tickDuration();
        }
    }
}
