package ArknightsMod.Cards.Operator.Defenders;

import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Operators.AbstractOperator;
import ArknightsMod.Operators.Defenders.NoirCorne.NoirCorne;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Operators.Skills.Empty;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import java.util.ArrayList;

import static ArknightsMod.Patches.CardColorEnum.ARK_NIGHTS;


public class NoirCorneCard extends AbstractOperatorCard {
    private static final String ID = "Arknights_NoirCorne";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final String IMG_PATH = "ArkImg/card/operator/NoirCorneCard.png";
    private static final int COST = 1;
    public NoirCorneCard(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, EXTENDED_DESCRIPTION,
                CardType.POWER, ARK_NIGHTS,
                CardRarity.COMMON, CardTarget.NONE);
        this.setStat();
        this.initSkillHitbox();
    }

    @Override
    public AbstractOperator getOperator() {
        return new NoirCorne();
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