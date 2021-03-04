package ArknightsMod.Cards.Attack;

import ArknightsMod.Actions.SwordRainAction;
import ArknightsMod.Cards.AbstractArkCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static ArknightsMod.Patches.CardColorEnum.ARK_NIGHTS;
import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK;

public class SwordRain extends AbstractArkCard {
    private static final String ID = "Arknights_SwordRain";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String IMG_PATH = "ArkImg/card/attack/SwordRain.png";
    private static final int COST = 0;
    private static final int ATTACK_DMG = 3;
    private static final int UPGRADE_ATTACK_DMG = 1;
    private static final int MAGIC_NUM = 4;

    public SwordRain() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                ATTACK, ARK_NIGHTS,
                CardRarity.SPECIAL, CardTarget.ALL_ENEMY);
        this.damage = this.baseDamage = ATTACK_DMG;
        this.magicNumber = this.baseMagicNumber = MAGIC_NUM;
        this.isMultiDamage = true;
        this.setDisplayRarity(CardRarity.RARE);
        this.exhaust = true;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new SwordRainAction(this));
//        AbstractOperator o = OperatorGroup.OperatorsFields.operators.get(AbstractDungeon.player).getOperator("Arknights_Texas");
//        if(o != null){
//            o.playSkillAnim();
//            o.playIdleAnim();
//        }
//        this.addToBot(new GainEnergyAction(2));
//        this.addToBot(new VFXAction(new SwordRainEffect()));
//        this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
//        this.addToBot(new VFXAction(new SwordRainEffect()));
//        this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
//        Iterator var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
//        AbstractMonster mo;
//        while(var3.hasNext()) {
//            mo = (AbstractMonster)var3.next();
//            this.addToBot(new ApplyPowerAction(mo, p, new StrengthPower(mo, -this.magicNumber), -this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
//            if (!mo.hasPower("Artifact")) {
//                this.addToBot(new ApplyPowerAction(mo, p, new GainStrengthPower(mo, this.magicNumber), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
//            }
//        }
    }


    @Override
    public AbstractCard makeCopy() {
        return new SwordRain();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_ATTACK_DMG);
        }
    }
}
