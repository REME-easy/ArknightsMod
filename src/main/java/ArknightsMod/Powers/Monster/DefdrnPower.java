package ArknightsMod.Powers.Monster;

import ArknightsMod.Helper.GeneralHelper;
import ArknightsMod.Powers.Operator.AbstractArkPower;
import ArknightsMod.Powers.Operator.SolidnessPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DefdrnPower extends AbstractArkPower {
    public static final String POWER_ID = "Arknights_DefdrnPower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public DefdrnPower(AbstractCreature owner, int Amt) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = Amt;
        this.updateDescription();
        this.loadRegion("blur");
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        for(AbstractMonster m : GeneralHelper.monsters()) {
            if(!m.isDeadOrEscaped()) {
                this.addToBot(new ApplyPowerAction(m, this.owner, new SolidnessPower(m, 2)));
            }
        }
    }

    @Override
    public void onSpawnMonster(AbstractMonster m) {
        super.onSpawnMonster(m);
        if(!m.isDeadOrEscaped()) {
            this.addToBot(new ApplyPowerAction(m, this.owner, new SolidnessPower(m, 2)));
        }
    }

    @Override
    public void onDeath() {
        super.onDeath();
        for(AbstractMonster m : GeneralHelper.monsters()) {
            if(!m.isDeadOrEscaped() && m.hasPower(SolidnessPower.POWER_ID)) {
                this.addToBot(new ReducePowerAction(m, this.owner, SolidnessPower.POWER_ID, 2));
            }
        }
    }

    @Override
    public void onRemove() {
        super.onRemove();
        for(AbstractMonster m : GeneralHelper.monsters()) {
            if(!m.isDeadOrEscaped() && m.hasPower(SolidnessPower.POWER_ID)) {
                this.addToBot(new ReducePowerAction(m, this.owner, SolidnessPower.POWER_ID, 2));
            }
        }
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
