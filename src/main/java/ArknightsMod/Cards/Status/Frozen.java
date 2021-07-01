package ArknightsMod.Cards.Status;

import ArknightsMod.Helper.GeneralHelper;
import ArknightsMod.Monsters.AbstractEnemy;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class Frozen extends CustomCard {
    private static final String ID = "Arknights_Frozen";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String IMG_PATH = "ArkImg/card/status/Frozen.png";
    private static final int COST = 1;
    public Frozen() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.STATUS, CardColor.COLORLESS,
                CardRarity.SPECIAL, CardTarget.NONE);
        this.exhaust = true;
    }

    @Override
    public void upgrade() {

    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        this.dontTriggerOnUseCard = true;
        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if (this.dontTriggerOnUseCard) {
            this.addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    flash();
                    for (AbstractMonster m : GeneralHelper.monsters()) {
                        if (m instanceof AbstractEnemy && ((AbstractEnemy) m).enemyTags.contains(AbstractEnemy.EnemyTag.YETI)) {
                            this.addToBot(new ApplyPowerAction(m, m, new StrengthPower(m, 1)));
                            this.addToBot(new ApplyPowerAction(m, m, new LoseStrengthPower(m, 1)));
                        }
                    }
                    isDone = true;
                }
            });
        }
    }
}
