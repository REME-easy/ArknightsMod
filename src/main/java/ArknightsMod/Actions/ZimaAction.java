package ArknightsMod.Actions;

import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Operators.AbstractOperator;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;

public class ZimaAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;

    public ZimaAction(AbstractCreature source) {
        this.setValues(AbstractDungeon.player, source, -1);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = 0.5F;
    }

    public void update() {
        if (this.duration == 0.5F) {
            int amt = 0;
            for(AbstractCard c : AbstractDungeon.player.drawPile.group) {
                if(c instanceof AbstractOperatorCard && ((AbstractOperatorCard) c).operatorType == AbstractOperator.OperatorType.VANGUARD) {
                    amt++;
                }
            }
            if(amt > 0) {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0] + amt + TEXT[1], amt, true, true);
            }else {
                isDone = true;
            }
            this.tickDuration();
        } else {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                if (!AbstractDungeon.handCardSelectScreen.selectedCards.group.isEmpty()) {

                    for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                        AbstractDungeon.player.hand.moveToDiscardPile(c);
                        GameActionManager.incrementDiscard(false);
                        c.triggerOnManualDiscard();
                    }

                    ArrayList<AbstractCard> cards = new ArrayList<>();
                    for(AbstractCard c : AbstractDungeon.player.drawPile.group) {
                        if(c instanceof AbstractOperatorCard && ((AbstractOperatorCard) c).operatorType == AbstractOperator.OperatorType.VANGUARD) {
                            cards.add(c);
                        }
                    }
                    
                    for(AbstractCard c : cards) {
                        if (AbstractDungeon.player.hand.size() >= BaseMod.MAX_HAND_SIZE) {
                            AbstractDungeon.player.drawPile.moveToDiscardPile(c);
                            AbstractDungeon.player.createHandIsFullDialog();
                        } else {
                            AbstractDungeon.player.drawPile.moveToHand(c, AbstractDungeon.player.drawPile);
                        }
                    }
                }

                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            }

            isDone = true;
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("ARKNIGHTS_ACTION");
        TEXT = uiStrings.TEXT;
    }
}
