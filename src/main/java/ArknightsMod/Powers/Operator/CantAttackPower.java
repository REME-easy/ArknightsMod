package ArknightsMod.Powers.Operator;

import ArknightsMod.Operators.AbstractOperator;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class CantAttackPower extends AbstractPower {
    public static final String POWER_ID = "Arknights_CantAttackPower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    private boolean playAnim;

    private boolean justApplied = true;


    public CantAttackPower(AbstractCreature owner, int amt, boolean playAnim) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amt;
        this.playAnim = playAnim;
        this.type = PowerType.DEBUFF;
        this.updateDescription();
        this.loadRegion("attackBurn");
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    public void atEndOfRound() {
        if (this.justApplied) {
            this.justApplied = false;
        } else if (this.amount <= 0) {
            this.flash();
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        } else {
            this.addToBot(new ReducePowerAction(this.owner, this.owner, this, 1));
        }
    }

    @Override
    public void onInitialApplication() {
        if(playAnim && owner instanceof AbstractOperator) {
            ((AbstractOperator) owner).playDeathAnim();
        }
    }

    @Override
    public void onRemove() {
        if(playAnim && owner instanceof AbstractOperator) {
            ((AbstractOperator) owner).playIdleAnim();
        }
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
