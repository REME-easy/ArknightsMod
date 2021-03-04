package ArknightsMod.Powers.Counter;

import ArknightsMod.Operators.AbstractOperator;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class MidnightCounter extends AbstractCounterPower {
    private static final String POWER_ID = "Arknights_MidnightCounter";
    private static final PowerStrings powerStrings;
    private static final String NAME;
    private static final String[] DESCRIPTIONS;
    public MidnightCounter(AbstractOperator owner) {
        super(owner);
        this.ID = POWER_ID;
        this.name = NAME;
        this.maxAmount = 3;
        this.currentAmount = 0;
        this.type = PowerType.BUFF;
        this.updateDescription();

    }

    @Override
    public void onOperatorAttack() {
        super.onOperatorAttack();
        this.addCount(1);
    }

    @Override
    public void activePower() {
        if(this.isAcitve) {
            if(owner instanceof AbstractOperator) {
                ((AbstractOperator) owner).addAttack(1);
            }
            this.isAcitve = false;
        }
        //this.addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, 1)));

    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.currentAmount + DESCRIPTIONS[1];
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
