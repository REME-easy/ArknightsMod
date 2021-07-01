package ArknightsMod.Actions;

import ArknightsMod.Character.OperatorGroup;
import ArknightsMod.Helper.GeneralHelper;
import ArknightsMod.Operators.AbstractOperator;
import ArknightsMod.Powers.Operator.AbstractArkPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

import static java.lang.Math.min;

public class OperatorsDamageAction extends AbstractGameAction {
    private static final Logger logger = LogManager.getLogger(OperatorsDamageAction.class.getName());

    private AbstractOperator operator;
    private String anim;
    private boolean toAll;

    public OperatorsDamageAction(AbstractOperator operator, String anim, boolean toAll){
        this.operator = operator;
        this.anim = anim;
        this.toAll = toAll;
    }

    public OperatorsDamageAction(AbstractOperator operator, String anim){
        this(operator, anim, false);
    }

    public OperatorsDamageAction(AbstractOperator operator){
        this(operator, null);
    }

    public void update(){
        if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.clearPostCombatActions();
            this.isDone = true;
        } else {
            int i;
            int times = operator.attackTimes;
            if(operator.currentBattleSkill != null && this.operator.currentBattleSkill.isSpelling){
                times = operator.currentBattleSkill.getAttackTimes(times);
            }

            if(times > 0) {
                if(operator.attackTargets == 1) {
                    if(operator.isHealer){
                        //logger.info("isHealer");
                        if(anim != null && operator.canPlayAttack){
                                this.addToBot(new AbstractGameAction() {
                                    @Override
                                    public void update() {
                                        if(operator.playAttackBeginAnim() != null)
                                            operator.state.addAnimation(0, operator.playAttackBeginAnim(), false, 0.0F);
                                        operator.state.addAnimation(0, anim, false, 0.0F);
                                        if(operator.playAttackEndAnim() != null)
                                            operator.state.addAnimation(0, operator.playAttackEndAnim(), false, 0.0F);
                                        operator.playIdleAnim();
                                    isDone = true;
                                    }
                                });
                            //logger.info("OperatorsChangeAnimationAction");
                        }
                        for(i = 0 ; i < times ; i++){
                            AbstractOperator o = GeneralHelper.getRandomOperatorToHeal();
                            //logger.info("getRandomOperatorToHeal:" + (o == null ? "null" : o.name));

                            if(o != null){
                                this.addToBot(new OperatorHealAction(o, operator));
                                //logger.info("OperatorHealAction：" + o.name);
                            }else {
                                float amt = 0;
                                    amt = operator.getAttackToTarget();

                                for(AbstractOperator o1 : OperatorGroup.GetOperators()){
                                    amt = o1.UseWhenHeal(target, operator, amt);
                                }
                                this.addToBot(new HealAction(AbstractDungeon.player, operator, (int)amt));
                            }
                        }
                        if(operator.currentBattleSkill != null && operator.currentBattleSkill.isSpelling) {
                            operator.currentBattleSkill.afterAttack();
                        }
                        this.addToBot(new WaitAction(0.1F));
                    }else if(operator.canAttack){
                        if(anim != null && operator.canPlayAttack){
                            this.addToBot(new OperatorsChangeAnimationAction(operator, anim, false, 0.0F));
                        }
                        for(i = 0 ; i < times ; i++){
                            if(operator.isMelee){
                                this.addToBot(new useFastAttackAnimAction(operator));
                            }
                            if(toAll) {
                                int[] tmp = this.operator.getAttackToAll();
                                this.addToBot(new DamageAllEnemiesAction(operator, tmp, DamageInfo.DamageType.NORMAL, operator.attackEffect, true));
                            } else
                                this.addToBot(new operatorDamageAssistAction(operator));
                        }
                        if(operator.currentBattleSkill != null && operator.currentBattleSkill.isSpelling) {
                            operator.currentBattleSkill.afterAttack();
                        }
                        this.addToBot(new WaitAction(0.1F));
                    }
                }else if(operator.attackTargets > 1) {
                    int amt = operator.attackTargets;
                    if(operator.isHealer){

                        int a = 0;
                        for(AbstractOperator o : OperatorGroup.GetOperators()) if (!o.isDeadOrEscaped()) a++;

                        amt = min(a, amt);
                        ArrayList<AbstractOperator> tmp = new ArrayList<>();
                        while (tmp.size() <= amt) {
                            AbstractOperator o = GeneralHelper.getRandomOperatorToHeal();
                            boolean dupe = false;
                            for(AbstractOperator o1 : tmp)
                                if (o == o1) {
                                    dupe = true;
                                    break;
                                }
                            if(!dupe) tmp.add(o);
                        }

                        if(anim != null) this.addToBot(new OperatorsChangeAnimationAction(operator, anim, false, 0.0F));

                        for(i = 0 ; i < times ; i++)
                            for (AbstractOperator o : tmp)
                                this.addToBot(new OperatorHealAction(o, operator));

                        if(operator.currentBattleSkill != null)
                            operator.currentBattleSkill.afterAttack();

                        this.addToBot(new WaitAction(0.1F));
                    }else if(operator.canAttack){
                        amt = min(amt, GeneralHelper.aliveMonstersAmount());

                        if(anim != null){
                            this.addToBot(new OperatorsChangeAnimationAction(operator, anim, false, 0.0F));
                        }

                        for(i = 0 ; i < times ; i++){
                            if(operator.isMelee) this.addToBot(new useFastAttackAnimAction(operator));

                            this.addToBot(new operatorDamageAssistAction(operator, amt));
                        }
                        if(operator.currentBattleSkill != null) {
                            operator.currentBattleSkill.afterAttack();
                        }
                        this.addToBot(new WaitAction(0.1F));
                    }
                }
            }

            for(AbstractPower p : this.operator.powers) {
                if(p instanceof AbstractArkPower) {
                    ((AbstractArkPower) p).afterOperatorAttack();
                }
            }
            operator.AfterAttack();

            this.isDone = true;
        }
    }
}
