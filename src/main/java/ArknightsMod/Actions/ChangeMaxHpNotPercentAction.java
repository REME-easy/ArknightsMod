package ArknightsMod.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;

public class ChangeMaxHpNotPercentAction extends AbstractGameAction {
    private boolean showEffect;

    public ChangeMaxHpNotPercentAction(AbstractCreature m, int amount, boolean showEffect) {
        if (Settings.FAST_MODE) {
            this.startDuration = Settings.ACTION_DUR_XFAST;
        } else {
            this.startDuration = Settings.ACTION_DUR_FAST;
        }

        this.duration = this.startDuration;
        this.showEffect = showEffect;
        this.amount = amount;
        this.target = m;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            if(amount > 0){
                target.increaseMaxHp(amount, this.showEffect);
            }else if(amount < 0){
                target.decreaseMaxHealth(amount);
            }
        }

        this.tickDuration();
    }
}
