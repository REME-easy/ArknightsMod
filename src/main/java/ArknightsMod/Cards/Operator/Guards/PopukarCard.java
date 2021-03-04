package ArknightsMod.Cards.Operator.Guards;

import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Operators.AbstractOperator;
import ArknightsMod.Operators.Guards.Popukar.Popukar;
import ArknightsMod.Operators.Guards.Popukar.Popukar_1;
import ArknightsMod.Operators.Skills.AbstractSkill;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import java.util.ArrayList;

import static ArknightsMod.Patches.CardColorEnum.ARK_NIGHTS;


public class PopukarCard extends AbstractOperatorCard {
    private static final String ID = "Arknights_Popukar";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final String IMG_PATH = "ArkImg/card/operator/PopukarCard.png";
    private static final int COST = 2;
    public PopukarCard(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, EXTENDED_DESCRIPTION,
                CardType.POWER, ARK_NIGHTS,
                CardRarity.UNCOMMON, CardTarget.NONE);
        this.setStat();
        this.initSkillHitbox();
    }

    @Override
    public ArrayList<AbstractSkill> getSkills() {
        ArrayList<AbstractSkill> tmp = new ArrayList<>();
        tmp.add(new Popukar_1());
        return tmp;
    }

    @Override
    public AbstractOperator getOperator() {
        return new Popukar();
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }
}