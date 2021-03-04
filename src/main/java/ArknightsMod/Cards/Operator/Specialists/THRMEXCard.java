package ArknightsMod.Cards.Operator.Specialists;

import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Operators.AbstractOperator;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Operators.Skills.Empty;
import ArknightsMod.Operators.Specialists.THRMEX.THRMEX;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import java.util.ArrayList;

import static ArknightsMod.Patches.CardColorEnum.ARK_NIGHTS;


public class THRMEXCard extends AbstractOperatorCard {
    private static final String ID = "Arknights_ThermalEX";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final String IMG_PATH = "ArkImg/card/operator/ThermalEXCard.png";
    private static final int COST = 0;
    public THRMEXCard(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, EXTENDED_DESCRIPTION,
                CardType.POWER, ARK_NIGHTS,
                CardRarity.COMMON, CardTarget.NONE);
        this.setStat();
    }

    public AbstractCard makeCopy(){
        return new THRMEXCard();
    }

    @Override
    public AbstractOperator getOperator() {
        return new THRMEX();
    }

    @Override
    public ArrayList<AbstractSkill> getSkills() {
        ArrayList<AbstractSkill> tmp = new ArrayList<>();
        tmp.add(new Empty());
        return tmp;
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }
}