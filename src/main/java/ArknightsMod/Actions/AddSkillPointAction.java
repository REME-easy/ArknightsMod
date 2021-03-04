package ArknightsMod.Actions;

import ArknightsMod.Operators.AbstractOperator;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.TextAboveCreatureEffect;

public class AddSkillPointAction extends AbstractGameAction {
    private int sp;
    private AbstractOperator operator;

    public AddSkillPointAction(int sp, AbstractOperator operator) {
        this.sp = sp;
        this.operator = operator;
        this.duration = 0.1F;
    }

    @Override
    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.isDone = true;
        } else {
            if (this.duration == 0.1F) {
                AbstractDungeon.effectList.add(new TextAboveCreatureEffect(operator.hb.cX - operator.animX, operator.hb.cY + operator.hb.height / 2.0F, "SP+".concat(Integer.toString(sp)), Color.CYAN.cpy()));
                this.operator.changeSkillPoints(sp);
            }
            this.isDone = true;
        }
    }
}
