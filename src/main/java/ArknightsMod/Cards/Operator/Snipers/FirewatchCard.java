package ArknightsMod.Cards.Operator.Snipers;

import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Cards.Operator.Snipers.HandOperators.FireWatchHandCard;
import ArknightsMod.Operators.AbstractOperator.OperatorType;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Operators.Snipers.Firewatch.Firewatch_1;
import ArknightsMod.Operators.Snipers.Firewatch.Firewatch_2;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static ArknightsMod.Patches.CardColorEnum.ARK_NIGHTS;


public class FirewatchCard extends AbstractOperatorCard {
    private static final String NAME_U = "Firewatch";
    private static final String NAME_L = "firewatch";
    private static final String ID = "Arknights_" + NAME_U;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final String IMG_PATH = "ArkImg/card/operator/" + NAME_U + "Card.png";
    private static final int COST = 2;
    public FirewatchCard(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, EXTENDED_DESCRIPTION,
                CardType.POWER, ARK_NIGHTS,
                CardRarity.RARE, CardTarget.NONE);
        this.level = 5;
        this.operatorType = OperatorType.SNIPER;
        this.initSkillHitbox();
        this.cardsToPreview = new FireWatchHandCard();
    }

    @Override
    public ArrayList<AbstractSkill> getSkills() {
        ArrayList<AbstractSkill> tmp = new ArrayList<>();
        tmp.add(new Firewatch_1());
        tmp.add(new Firewatch_2());
        return tmp;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard c = new FireWatchHandCard(skillindex);
        this.addToBot(new MakeTempCardInHandAction(c));
        CardCrawlGame.sound.play( NAME_L + "_start");
        for(AbstractCard c1 : AbstractDungeon.player.masterDeck.group) {
            if(c1 instanceof AbstractOperatorCard) {
                ((AbstractOperatorCard) c1).onSpawnOperatorCardInDeck(c);
            }
        }
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void render(SpriteBatch sb) {
        if (!Settings.hideCards) {
            this.render(sb, false, false);
        }
    }
}