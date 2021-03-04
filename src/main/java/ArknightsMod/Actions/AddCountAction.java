package ArknightsMod.Actions;

import ArknightsMod.Powers.Counter.AbstractCounterPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class AddCountAction extends AbstractGameAction {

    private AbstractCounterPower p;
    private int i;

    public AddCountAction(AbstractCounterPower p, int i) {
        this.p = p;
        this.i = i;
        this.duration = 0.1F;
    }

    @Override
    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.isDone = true;
        } else {
            if (this.duration == 0.1F) {
                this.p.currentAmount += i;
                if(this.p.currentAmount >= this.p.maxAmount) {
                    this.p.activePower();
                    this.p.isAcitve = true;
                    this.p.flash();
                    this.p.currentAmount = 0;
                }
                this.p.updateDescription();
            }
            this.isDone = true;
        }
    }
}
