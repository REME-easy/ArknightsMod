package ArknightsMod.Cards.Operator.Casters;

import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Cards.Skill.Divination;
import ArknightsMod.Operators.AbstractOperator;
import ArknightsMod.Operators.Casters.Gitano.Gitano;
import ArknightsMod.Operators.Casters.Gitano.Gitano_1;
import ArknightsMod.Operators.Casters.Gitano.Gitano_2;
import ArknightsMod.Operators.Skills.AbstractSkill;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import java.util.ArrayList;

import static ArknightsMod.Patches.CardColorEnum.ARK_NIGHTS;


public class GitanoCard extends AbstractOperatorCard {
    private static final String NAME_U = "Gitano";
    private static final String ID = "Arknights_" + NAME_U;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final String IMG_PATH = "ArkImg/card/operator/" + NAME_U + "Card.png";
    private static final int COST = 4;
    public GitanoCard(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, EXTENDED_DESCRIPTION,
                CardType.POWER, ARK_NIGHTS,
                CardRarity.UNCOMMON, CardTarget.NONE);
        this.setStat();
        this.initSkillHitbox();
        this.cardsToPreview = new Divination();
    }

    @Override
    public ArrayList<AbstractSkill> getSkills() {
        ArrayList<AbstractSkill> tmp = new ArrayList<>();
        tmp.add(new Gitano_1());
        tmp.add(new Gitano_2());
        return tmp;
    }

    @Override
    public AbstractOperator getOperator() {
        return new Gitano();
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }
}