package ArknightsMod.Cards.Operator.Guards;

import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Operators.AbstractOperator;
import ArknightsMod.Operators.Guards.Silverash.Silverash;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Operators.Guards.Silverash.Svash_1;
import ArknightsMod.Operators.Guards.Silverash.Svash_2;
import ArknightsMod.Operators.Guards.Silverash.Svash_3;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import java.util.ArrayList;

import static ArknightsMod.Patches.CardColorEnum.ARK_NIGHTS;


public class SilverashCard extends AbstractOperatorCard {
    private static final String ID = "Arknights_SilverAsh";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final String IMG_PATH = "ArkImg/card/operator/SvashCard.png";
    private static final int COST = 3;
    public SilverashCard(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, EXTENDED_DESCRIPTION,
                CardType.POWER, ARK_NIGHTS,
                CardRarity.RARE, CardTarget.NONE);
        this.setStat();
        this.initSkillHitbox();
    }

    @Override
    public ArrayList<AbstractSkill> getSkills() {
        ArrayList<AbstractSkill> tmp = new ArrayList<>();
        tmp.add(new Svash_1());
        tmp.add(new Svash_2());
        tmp.add(new Svash_3());
        return tmp;
    }

    @Override
    public void onUseCardInDeck(AbstractCard card) {
        if(card instanceof AbstractOperatorCard){
            this.addToBot(new DrawCardAction(1));
        }
    }

    @Override
    public AbstractOperator getOperator() {
        return new Silverash();
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }
}