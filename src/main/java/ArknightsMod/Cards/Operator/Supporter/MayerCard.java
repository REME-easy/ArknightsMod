package ArknightsMod.Cards.Operator.Supporter;

import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Cards.Operator.Token.MotterCard;
import ArknightsMod.Operators.AbstractOperator;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Operators.Supporter.Mayer.Mayer;
import ArknightsMod.Operators.Supporter.Mayer.Mayer_1;
import ArknightsMod.Operators.Supporter.Mayer.Mayer_2;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import java.util.ArrayList;

import static ArknightsMod.Patches.CardColorEnum.ARK_NIGHTS;


public class MayerCard extends AbstractOperatorCard {
    private static final String NAME_U = "Mayer";
    private static final String ID = "Arknights_" + NAME_U;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final String IMG_PATH = "ArkImg/card/operator/" + NAME_U + "Card.png";
    private static final int COST = 1;
    public MayerCard(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, EXTENDED_DESCRIPTION,
                CardType.POWER, ARK_NIGHTS,
                CardRarity.RARE, CardTarget.NONE);
        this.setStat();
        this.initSkillHitbox();
        this.cardsToPreview = new MotterCard();
    }

    @Override
    public ArrayList<AbstractSkill> getSkills() {
        ArrayList<AbstractSkill> tmp = new ArrayList<>();
        tmp.add(new Mayer_1());
        tmp.add(new Mayer_2());
        return tmp;
    }

    @Override
    public AbstractOperator getOperator() {
        return new Mayer();
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }
}