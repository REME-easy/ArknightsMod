package ArknightsMod.Powers.Operator;

import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ResummonPower extends AbstractPower {
    private static final String POWER_ID = "Arknights_ResummonPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static int postfix = 0;
    private boolean justApplied = true;

    private AbstractOperatorCard card;
    public ResummonPower(AbstractCreature owner, int amount, AbstractOperatorCard card){
        this.ID = POWER_ID + postfix++;
        this.name =NAME;
        this.owner = owner;
        this.amount = amount;
        this.card = card;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;
        String path128 = "ArkImg/powers/Resummon84.png";
        String path48 = "ArkImg/powers/Resummon32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.updateDescription();
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
    public void onRemove() {
        super.onRemove();
        this.addToBot(new MakeTempCardInDiscardAction(card.makeCopy(), 1));
    }

    public void updateDescription(){
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.card.name + DESCRIPTIONS[2];
    }
}
