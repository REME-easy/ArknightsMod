package ArknightsMod.Cards.Operator.Defenders;

import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Operators.AbstractOperator;
import ArknightsMod.Operators.Defenders.Cuora.Cuora;
import ArknightsMod.Operators.Defenders.Cuora.Cuora_1;
import ArknightsMod.Operators.Defenders.Cuora.Cuora_2;
import ArknightsMod.Operators.Skills.AbstractSkill;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import java.util.ArrayList;

import static ArknightsMod.Patches.CardColorEnum.ARK_NIGHTS;


public class CuoraCard extends AbstractOperatorCard {
    private static final String NAME_U = "Cuora";
    private static final String ID = "Arknights_" + NAME_U;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final String IMG_PATH = "ArkImg/card/operator/" + NAME_U + "Card.png";
    private static final int COST = 3;
    public CuoraCard(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, EXTENDED_DESCRIPTION,
                CardType.POWER, ARK_NIGHTS,
                CardRarity.UNCOMMON, CardTarget.NONE);
        this.setStat();
        this.initSkillHitbox();
    }

    @Override
    public AbstractOperator getOperator() {
        return new Cuora();
    }

    @Override
    public ArrayList<AbstractSkill> getSkills() {
        ArrayList<AbstractSkill> tmp = new ArrayList<>();
        tmp.add(new Cuora_1());
        tmp.add(new Cuora_2());
        return tmp;
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }
}