package ArknightsMod.Powers.Operator;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

public class SilencePower extends AbstractArkPower {
    public static final String POWER_ID = "Arknights_SilencePower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    private ArrayList<AbstractPower> powers;

    private boolean justApplied = true;

    public SilencePower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.powers = new ArrayList<>();
        this.type = PowerType.DEBUFF;
        this.updateDescription();
        this.loadRegion("attackBurn");
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
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
        super.onInitialApplication();
        for(AbstractPower p : this.owner.powers) {
            if(p.type == PowerType.BUFF) {
                this.powers.add(p);
                this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, p.ID));
            }
        }
    }

    @Override
    public void onApplyPower(AbstractPower p, AbstractCreature target, AbstractCreature source) {
        super.onApplyPower(p, target, source);
        if(target == this.owner) {
            if(p.type == PowerType.BUFF) {
                this.powers.add(p);
                this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, p.ID));
            }
        }
    }

    @Override
    public void onRemove() {
        super.onRemove();
        for(AbstractPower p : powers) {
            this.addToBot(new ApplyPowerAction(this.owner, this.owner, p));
        }
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
