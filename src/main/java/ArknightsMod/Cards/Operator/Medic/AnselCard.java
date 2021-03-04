package ArknightsMod.Cards.Operator.Medic;

import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Operators.AbstractOperator;
import ArknightsMod.Operators.Medic.Ansel.Ansel;
import ArknightsMod.Operators.Medic.Ansel.Ansel_1;
import ArknightsMod.Operators.Skills.AbstractSkill;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import java.util.ArrayList;

import static ArknightsMod.Patches.CardColorEnum.ARK_NIGHTS;


public class AnselCard extends AbstractOperatorCard {
    private static final String ID = "Arknights_Ansel";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final String IMG_PATH = "ArkImg/card/operator/AnselCard.png";
    private static final int COST = 2;
    public AnselCard(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, EXTENDED_DESCRIPTION,
                CardType.POWER, ARK_NIGHTS,
                CardRarity.UNCOMMON, CardTarget.NONE);
        this.setStat();
        this.initSkillHitbox();
    }

    @Override
    public ArrayList<AbstractSkill> getSkills() {
        ArrayList<AbstractSkill> tmp = new ArrayList<>();
        tmp.add(new Ansel_1());
        return tmp;
    }

    @Override
    public AbstractOperator getOperator() {
        return new Ansel();
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }
}