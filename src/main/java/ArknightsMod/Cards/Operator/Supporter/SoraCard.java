package ArknightsMod.Cards.Operator.Supporter;

import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Operators.AbstractOperator;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Operators.Supporter.Sora.Sora;
import ArknightsMod.Operators.Supporter.Sora.Sora_1;
import ArknightsMod.Operators.Supporter.Sora.Sora_2;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import java.util.ArrayList;

import static ArknightsMod.Patches.CardColorEnum.ARK_NIGHTS;


public class SoraCard extends AbstractOperatorCard {
    private static final String NAME_U = "Sora";
    private static final String ID = "Arknights_" + NAME_U;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final String IMG_PATH = "ArkImg/card/operator/" + NAME_U + "Card.png";
    private static final int COST = 1;
    public SoraCard(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, EXTENDED_DESCRIPTION,
                CardType.POWER, ARK_NIGHTS,
                CardRarity.RARE, CardTarget.NONE);
        this.setStat();
        this.initSkillHitbox();
    }

    @Override
    public ArrayList<AbstractSkill> getSkills() {
        ArrayList<AbstractSkill> tmp = new ArrayList<>();
        tmp.add(new Sora_1());
        tmp.add(new Sora_2());
        return tmp;
    }

    @Override
    public AbstractOperator getOperator() {
        return new Sora();
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }
}