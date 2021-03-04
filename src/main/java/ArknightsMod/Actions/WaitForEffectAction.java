package ArknightsMod.Actions;

import ArknightsMod.Helper.GeneralHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class WaitForEffectAction extends AbstractGameAction {
    private AbstractGameEffect effect;
    private boolean firstFrame = true;

    public WaitForEffectAction(AbstractGameEffect effect) {
        this.effect = effect;
    }

    @Override
    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.isDone = true;
        } else {
            if(firstFrame) {
                GeneralHelper.addEffect(effect);
            }else {
                if(effect.isDone) this.isDone = true;
            }

        }
    }
}
