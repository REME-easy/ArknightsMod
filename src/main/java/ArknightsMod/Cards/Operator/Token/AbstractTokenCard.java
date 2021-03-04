package ArknightsMod.Cards.Operator.Token;

import ArknightsMod.Cards.AbstractArkCard;
import ArknightsMod.Character.OperatorGroup;
import ArknightsMod.Operators.AbstractOperator;
import ArknightsMod.Orbs.AbstractAnimatedOrb;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AbstractTokenCard extends AbstractArkCard {

    public AbstractTokenCard(String ID, String NAME, String IMG_PATH, int COST, String DESCRIPTION, CardType TYPE, CardColor COLOR, CardRarity RARITY, CardTarget TARGET) {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        AbstractAnimatedOrb orb = getOrb();
        this.addToBot(new ChannelAction(orb));
        for(AbstractOperator o : OperatorGroup.GetOperators()) {
            if(o.currentBattleSkill != null) o.currentBattleSkill.onChannelToken(orb);
        }
    }

    protected AbstractAnimatedOrb getOrb() {
        return null;
    }

    @Override
    public void upgrade() {

    }
}
