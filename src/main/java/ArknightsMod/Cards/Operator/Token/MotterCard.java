package ArknightsMod.Cards.Operator.Token;

import ArknightsMod.Orbs.AbstractAnimatedOrb;
import ArknightsMod.Orbs.Motter;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import static ArknightsMod.Patches.CardColorEnum.ARK_NIGHTS;

public class MotterCard extends AbstractTokenCard {
    private static final String NAME_U = "Motter";
    private static final String ID = "Arknights_" + NAME_U;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final String IMG_PATH = "ArkImg/card/skill/" + NAME_U + "Card.png";
    private static final int COST = 1;

    public MotterCard() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, ARK_NIGHTS,
                CardRarity.SPECIAL, CardTarget.NONE);
        this.exhaust = true;
        this.setDisplayRarity(CardRarity.UNCOMMON);
    }

    @Override
    protected AbstractAnimatedOrb getOrb() {
        return new Motter();
    }

    @Override
    public void upgrade() {
        if(!this.upgraded) {
            this.upgradeName();
            this.selfRetain = true;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
