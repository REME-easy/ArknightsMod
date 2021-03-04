package ArknightsMod.Actions;

import ArknightsMod.Operators.AbstractOperator;
import ArknightsMod.Vfx.Common.EyjaFireBallEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class EyjaFireBallAction extends AbstractGameAction {
    private static final float DURATION = 0.8F;
    private AbstractOperator operator;

    private ArrayList<EyjaFireBallEffect> effects = new ArrayList<>();

    public EyjaFireBallAction(AbstractOperator operator) {
        this.duration = DURATION;
        this.operator = operator;
    }

    @Override
    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.isDone = true;
        } else {
            if (this.duration == DURATION) {
                if(AbstractDungeon.getMonsters().monsters.size() > 0){
                    for(AbstractMonster m:AbstractDungeon.getMonsters().monsters){
                        if(m != null && !m.isDeadOrEscaped() && !m.isDead){
                            EyjaFireBallEffect e = new EyjaFireBallEffect(operator.drawX + 150.0F, operator.drawY + 175.0F, m.drawX, m.drawY);
                            effects.add(e);
                            AbstractDungeon.effectsQueue.add(e);
                        }
                    }
                }
                this.tickDuration();

            }

            for(EyjaFireBallEffect e : effects) {
                if(!e.isDone) return;
            }

            this.isDone = true;
        }
    }
}
