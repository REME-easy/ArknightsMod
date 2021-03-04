package ArknightsMod.Cards.Operator.Snipers;

import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Operators.AbstractOperator;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Operators.Snipers.Kroos.Kroos;
import ArknightsMod.Operators.Snipers.Kroos.Kroos_1;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import java.util.ArrayList;

import static ArknightsMod.Patches.CardColorEnum.ARK_NIGHTS;


public class KroosCard extends AbstractOperatorCard {
    private static final String ID = "Arknights_Kroos";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final String IMG_PATH = "ArkImg/card/operator/KroosCard.png";
    private static final int COST = 1;
    public KroosCard(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, EXTENDED_DESCRIPTION,
                CardType.POWER, ARK_NIGHTS,
                CardRarity.UNCOMMON, CardTarget.NONE);
        this.setStat();
        this.initSkillHitbox();
    }

    @Override
    public ArrayList<AbstractSkill> getSkills() {
        ArrayList<AbstractSkill> tmp = new ArrayList<>();
        tmp.add(new Kroos_1());
        return tmp;
    }

    @Override
    public AbstractOperator getOperator() {
        return new Kroos();
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }
}