package ArknightsMod.Cards.Operator.Guards;

import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Operators.AbstractOperator;
import ArknightsMod.Operators.Guards.Skadi.Skadi;
import ArknightsMod.Operators.Guards.Skadi.Skadi_1;
import ArknightsMod.Operators.Guards.Skadi.Skadi_2;
import ArknightsMod.Operators.Guards.Skadi.Skadi_3;
import ArknightsMod.Operators.Skills.AbstractSkill;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.ArrayList;

import static ArknightsMod.Patches.CardColorEnum.ARK_NIGHTS;

public class SkadiCard extends AbstractOperatorCard {
    private static final String NAME_U = "Skadi";
    public static final String ID = "Arknights_" + NAME_U;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final String IMG_PATH = "ArkImg/card/operator/" + NAME_U + "Card.png";
    private static final int COST = 2;
    public SkadiCard(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, EXTENDED_DESCRIPTION,
                CardType.POWER, ARK_NIGHTS,
                CardRarity.RARE, CardTarget.NONE);
        this.setStat();
        this.initSkillHitbox();
        this.misc = 0;
    }

    @Override
    public ArrayList<AbstractSkill> getSkills() {
        ArrayList<AbstractSkill> tmp = new ArrayList<>();
        tmp.add(new Skadi_1());
        tmp.add(new Skadi_2());
        tmp.add(new Skadi_3());
        return tmp;
    }

    @Override
    public void onSpawnOperatorInDeck(AbstractOperator o) {
        super.onSpawnOperatorInDeck(o);
        if(o.id.equals(Skadi.ID))
            addToBot(new ApplyPowerAction(o, o, new StrengthPower(o, misc + 1)));
        else
            addToBot(new ApplyPowerAction(o, o, new StrengthPower(o, 1)));
    }

    @Override
    public void onSpawnOperatorCardInDeck(AbstractCard card) {
        super.onSpawnOperatorCardInDeck(card);
        card.baseDamage += 1;
    }

    @Override
    public AbstractOperator getOperator() {
        return new Skadi();
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }
}