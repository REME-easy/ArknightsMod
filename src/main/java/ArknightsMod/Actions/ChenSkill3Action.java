package ArknightsMod.Actions;

import ArknightsMod.Helper.GeneralHelper;
import ArknightsMod.Monsters.AbstractEnemy;
import ArknightsMod.Operators.AbstractOperator;
import ArknightsMod.Vfx.Common.ChenSkill3Effect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ChenSkill3Action extends AbstractGameAction {
    private int index;
    private boolean isFinal;
    private AbstractOperator source;
    private boolean acted = false;

    public ChenSkill3Action(int index, boolean isFinal, AbstractOperator source) {
        this.index = index;
        this.isFinal = isFinal;
        this.source = source;
        this.duration = isFinal ? 0.4F : 0.1F;
    }

    @Override
    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.isDone = true;
        } else {
            if (this.duration <= 0.1F && !acted) {
                acted = true;
                AbstractMonster m = GeneralHelper.getRandomMonsterSafe();
                if(m == null) {
                    isFinal = true;
                }else {
                    m.damage(new DamageInfo(source, source.getAttackToTarget() * 3));
                    GeneralHelper.addEffect(new ChenSkill3Effect(m.drawX, m.drawY, index, isFinal));
                }

                if(isFinal) {
                    if(m instanceof AbstractEnemy) ((AbstractEnemy) m).addAttackCoolDown(-8);
                    this.isDone = true;
                    source.currentBattleSkill.EndEffect();
                }
            }
            this.tickDuration();
        }
    }
}
