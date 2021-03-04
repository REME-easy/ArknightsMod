package ArknightsMod.Cards.Operator.Snipers;

import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Operators.AbstractOperator;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Operators.Snipers.Bluepoison.Bluepoison;
import ArknightsMod.Operators.Snipers.Bluepoison.Bluepoison_1;
import ArknightsMod.Operators.Snipers.Bluepoison.Bluepoison_2;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import java.util.ArrayList;

import static ArknightsMod.Patches.CardColorEnum.ARK_NIGHTS;


public class BluepCard extends AbstractOperatorCard {
    private static final String NAME_U = "Bluep";
    private static final String ID = "Arknights_" + NAME_U;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final String IMG_PATH = "ArkImg/card/operator/" + NAME_U + "Card.png";
    private static final int COST = 1;
    public BluepCard(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, EXTENDED_DESCRIPTION,
                CardType.POWER, ARK_NIGHTS,
                CardRarity.RARE, CardTarget.NONE);
        this.setStat();
        this.initSkillHitbox();
    }

    @Override
    public ArrayList<AbstractSkill> getSkills() {
        ArrayList<AbstractSkill> tmp = new ArrayList<>();
        tmp.add(new Bluepoison_1());
        tmp.add(new Bluepoison_2());
        return tmp;
    }

    @Override
    public AbstractOperator getOperator() {
        return new Bluepoison();
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }
}