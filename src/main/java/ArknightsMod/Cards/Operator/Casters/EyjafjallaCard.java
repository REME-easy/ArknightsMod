package ArknightsMod.Cards.Operator.Casters;

import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Operators.AbstractOperator;
import ArknightsMod.Operators.Casters.Eyjafjalla.Eyjafjalla;
import ArknightsMod.Operators.Casters.Eyjafjalla.Eyjafjalla_1;
import ArknightsMod.Operators.Casters.Eyjafjalla.Eyjafjalla_2;
import ArknightsMod.Operators.Casters.Eyjafjalla.Eyjafjalla_3;
import ArknightsMod.Operators.Skills.AbstractSkill;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import java.util.ArrayList;

import static ArknightsMod.Patches.CardColorEnum.ARK_NIGHTS;


public class EyjafjallaCard extends AbstractOperatorCard {
    private static final String ID = "Arknights_Eyjafjalla";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final String IMG_PATH = "ArkImg/card/operator/EyjafjallaCard.png";
    private static final int COST = 3;
    public EyjafjallaCard(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, EXTENDED_DESCRIPTION,
                CardType.POWER, ARK_NIGHTS,
                CardRarity.RARE, CardTarget.NONE);
        this.setStat();
        this.initSkillHitbox();
    }

    @Override
    public ArrayList<AbstractSkill> getSkills() {
        ArrayList<AbstractSkill> tmp = new ArrayList<>();
        tmp.add(new Eyjafjalla_1());
        tmp.add(new Eyjafjalla_2());
        tmp.add(new Eyjafjalla_3());
        return tmp;
    }

    @Override
    public AbstractOperator getOperator() {
        return new Eyjafjalla();
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }
}