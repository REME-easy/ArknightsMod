package ArknightsMod.Actions;

import ArknightsMod.Helper.GeneralHelper;
import ArknightsMod.Operators.AbstractOperator;
import ArknightsMod.Vfx.Common.GainEnergyEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class GainEnergyEffectAction extends AbstractGameAction {
    private AbstractOperator target;
    private int amt;

    public GainEnergyEffectAction(AbstractOperator target, int amt) {
        this.amt = amt;
        this.duration = DEFAULT_DURATION;
        this.target = target;
    }

    @Override
    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.isDone = true;
        } else {
            if (this.duration == DEFAULT_DURATION) {
                for (int i = 0; i < amt; i++) {
                    GeneralHelper.addEffect(new GainEnergyEffect(target));
                }

            }
            this.tickDuration();
        }
    }
}
