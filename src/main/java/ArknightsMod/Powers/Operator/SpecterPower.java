package ArknightsMod.Powers.Operator;

import ArknightsMod.Operators.Guards.Specter.Specter;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.NoDrawPower;

public class SpecterPower extends AbstractArkPower {
    public static final String POWER_ID = "Arknights_SpecterPower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public SpecterPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.updateDescription();
        this.loadRegion("end_turn_death");
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public float onReceiveFatalDamage(float dmg) {
        if(owner.currentHealth - dmg < 1)
            return owner.currentHealth - 1;
        return super.onReceiveFatalDamage(dmg);
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        super.onCardDraw(card);
        AbstractPlayer p = AbstractDungeon.player;
        if(p.drawPile.isEmpty()) {
            this.addToNext(new ApplyPowerAction(p, p, new NoDrawPower(p)));
        }
        if(owner instanceof Specter) {
            ((Specter) owner).currentBattleSkill.EndEffect();
        }
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
