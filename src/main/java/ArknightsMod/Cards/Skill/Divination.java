package ArknightsMod.Cards.Skill;

import ArknightsMod.Actions.ChangeMaxHpNotPercentAction;
import ArknightsMod.Actions.ChooseOneOperatorAction;
import ArknightsMod.Cards.AbstractArkCard;
import ArknightsMod.Character.OperatorGroup;
import ArknightsMod.Operators.AbstractOperator;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static ArknightsMod.Patches.CardColorEnum.ARK_NIGHTS;
import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL;

public class Divination extends AbstractArkCard {
    private static final String ID = "Arknights_Divination";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final String IMG_PATH = "ArkImg/card/skill/Divination.png";
    private static final int COST = 0;

    private static final int ATK = 2;
    private static final int TIME = 1;
    private static final int HP = 5;

    public Divination() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                SKILL, ARK_NIGHTS,
                CardRarity.SPECIAL, CardTarget.SELF);
        this.setDisplayRarity(CardRarity.UNCOMMON);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ChooseOneOperatorAction(this, 1));
    }

    @Override
    public void receiveScreenTargetCreature(AbstractCreature source, AbstractCreature target) {
        if(target instanceof AbstractOperator) {
            int result = AbstractDungeon.cardRandomRng.random(0, 2);
            switch (result) {
                case 0:
                    ((AbstractOperator) target).addAttack(ATK);
                    break;
                case 1:
                    ((AbstractOperator) target).Attack();
                    break;
                case 2:
                    this.addToBot(new ChangeMaxHpNotPercentAction(target, HP, true));
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + result);
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Divination();
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if(OperatorGroup.GetOperators().size() > 0)
            return true;
        else
            return super.canUse(p, m);
    }

    @Override
    public void upgrade() {
        if(!this.upgraded) {
            this.upgradeName();
            this.selfRetain = true;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
