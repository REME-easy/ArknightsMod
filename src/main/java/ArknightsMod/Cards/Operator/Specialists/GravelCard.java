package ArknightsMod.Cards.Operator.Specialists;

import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Operators.AbstractOperator;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Operators.Specialists.Gravel.Gravel;
import ArknightsMod.Operators.Specialists.Gravel.Gravel_1;
import ArknightsMod.Operators.Specialists.Gravel.Gravel_2;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import java.util.ArrayList;

import static ArknightsMod.Patches.CardColorEnum.ARK_NIGHTS;


public class GravelCard extends AbstractOperatorCard {
    private static final String NAME_U = "Gravel";
    private static final String ID = "Arknights_" + NAME_U;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final String IMG_PATH = "ArkImg/card/operator/" + NAME_U + "Card.png";
    private static final int COST = 0;
    public GravelCard(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, EXTENDED_DESCRIPTION,
                CardType.POWER, ARK_NIGHTS,
                CardRarity.UNCOMMON, CardTarget.NONE);
        this.setStat();
        this.initSkillHitbox();
    }

    @Override
    public ArrayList<AbstractSkill> getSkills() {
        ArrayList<AbstractSkill> tmp = new ArrayList<>();
        tmp.add(new Gravel_1());
        tmp.add(new Gravel_2());
        return tmp;
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        this.flash();
        this.addToBot(new DrawCardAction(1));
    }

    @Override
    public AbstractOperator getOperator() {
        return new Gravel();
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }
}