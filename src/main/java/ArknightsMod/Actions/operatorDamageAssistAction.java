package ArknightsMod.Actions;

import ArknightsMod.Helper.GeneralHelper;
import ArknightsMod.Operators.AbstractOperator;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

import static java.lang.Math.min;

public class operatorDamageAssistAction extends AbstractGameAction {
    private static final Logger logger = LogManager.getLogger(operatorDamageAssistAction.class.getName());
    private AbstractOperator operator;
    private int atk;
    private int targets;

    operatorDamageAssistAction(AbstractOperator operator){
        this(operator, 1);
    }

    operatorDamageAssistAction(AbstractOperator operator, int t) {
        this.operator = operator;
        atk = this.operator.getAttackToTarget();
        this.targets = t;
    }
    public void update(){
        if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.clearPostCombatActions();
            this.isDone = true;
        } else {
            if(targets == 1) {
                AbstractMonster target = operator.getAttackTarget();

                if(target == null){
                    target = GeneralHelper.getRandomMonsterSafe();
                }

                if(target != null){
                    atk = (int)operator.UseWhenAttack(target, operator, atk);
                    AbstractGameEffect effect = this.operator.playAttackBulletEffect(target);
                    if(effect != null) this.addToBot(new WaitForEffectAction(effect));
//                    target.damage(new DamageInfo(operator, atk, operator.damageType));
//                    AbstractDungeon.effectList.add(new FlashAtkImgEffect(target.hb.cX, target.hb.cY, this.attackEffect, false));
                    operator.lastTarget = target;
                    this.addToBot(new DamageAction(target, new DamageInfo(operator, atk, operator.damageType), operator.attackEffect));
                }
            }else if (targets > 1) {
                ArrayList<AbstractMonster> tmp = new ArrayList<>();
                int tmp2 = targets;
                while (tmp.size() < tmp2) {
                    AbstractMonster target = operator.getAttackTarget();
                    boolean dupe = false;
                    if(target == null || tmp.contains(target)) target = GeneralHelper.getRandomMonsterSafe();

                    for(AbstractMonster m : tmp) if (m == target) dupe = true;

                    if(!dupe) tmp.add(target);

                    tmp2 = min(tmp2, GeneralHelper.aliveMonstersAmount());
//                    logger.info(String.format("targets:%d,alive:%d,already:%d", targets, tmp2, tmp.size()));
                }

                for(AbstractMonster m : tmp) {
                    int a = atk;
                    a = (int)operator.UseWhenAttack(m, operator, a);
                    AbstractGameEffect effect = this.operator.playAttackBulletEffect(m);
                    if(effect != null) this.addToBot(new WaitForEffectAction(effect));
//                    m.damage(new DamageInfo(operator, atk, operator.damageType));
//                    AbstractDungeon.effectList.add(new FlashAtkImgEffect(m.hb.cX, m.hb.cY, this.attackEffect, false));
//                    operator.lastTarget = m;
                    this.addToBot(new DamageAction(m, new DamageInfo(operator, a, operator.damageType), operator.attackEffect));
                }
            }


            this.isDone = true;

        }
    }
}
