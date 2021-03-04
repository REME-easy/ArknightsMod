package ArknightsMod.Actions;

import ArknightsMod.Character.OperatorGroup;
import ArknightsMod.Operators.AbstractOperator;
import ArknightsMod.Vfx.Common.SwordRainEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.Iterator;

public class SwordRainAction extends AbstractGameAction {
    private AbstractCard card;

    public SwordRainAction(AbstractCard card) {
        this.duration = DEFAULT_DURATION;
        this.card = card;
    }

    @Override
    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.isDone = true;
        } else {
            if (this.duration == DEFAULT_DURATION) {
                AbstractOperator o = OperatorGroup.GetOperatorByID("Arknights_Texas");
                if(o != null){
                    o.playSkillAnim();
                    o.playIdleAnim();
                    this.addToBot(new GainEnergyEffectAction(o, 2));
                }
                AbstractPlayer p = AbstractDungeon.player;
                this.addToBot(new GainEnergyAction(2));
                this.addToBot(new VFXAction(new SwordRainEffect()));
                this.addToBot(new DamageAllEnemiesAction(p, card.multiDamage, card.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
                this.addToBot(new VFXAction(new SwordRainEffect()));
                this.addToBot(new DamageAllEnemiesAction(p, card.multiDamage, card.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
                Iterator var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
                AbstractMonster mo;
                while(var3.hasNext()) {
                    mo = (AbstractMonster)var3.next();
                    this.addToBot(new ApplyPowerAction(mo, p, new StrengthPower(mo, -card.magicNumber), -card.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
                    if (!mo.hasPower("Artifact")) {
                        this.addToBot(new ApplyPowerAction(mo, p, new GainStrengthPower(mo, card.magicNumber), card.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
                    }
                }

            }
            this.tickDuration();
        }
    }
}
