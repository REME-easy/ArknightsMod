package ArknightsMod.Cards.Operator.Snipers;

import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Operators.AbstractOperator;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Operators.Snipers.Angel.Angel;
import ArknightsMod.Operators.Snipers.Angel.Angel_1;
import ArknightsMod.Operators.Snipers.Angel.Angel_2;
import ArknightsMod.Operators.Snipers.Angel.Angel_3;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import java.util.ArrayList;

import static ArknightsMod.Patches.CardColorEnum.ARK_NIGHTS;


public class AngelCard extends AbstractOperatorCard {
    private static final String ID = "Arknights_Angel";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final String IMG_PATH = "ArkImg/card/operator/AngelCard.png";
    private static final int COST = 2;
    public AngelCard(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, EXTENDED_DESCRIPTION,
                AbstractCard.CardType.POWER, ARK_NIGHTS,
                CardRarity.RARE, AbstractCard.CardTarget.NONE);
        this.setStat();
        this.initSkillHitbox();
    }

    @Override
    public ArrayList<AbstractSkill> getSkills() {
        ArrayList<AbstractSkill> tmp = new ArrayList<>();
        tmp.add(new Angel_1());
        tmp.add(new Angel_2());
        tmp.add(new Angel_3());
        return tmp;
    }

    @Override
    public AbstractOperator getOperator() {
        return new Angel();
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }
}