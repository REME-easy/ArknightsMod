package ArknightsMod.Cards;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar;
import javassist.CtBehavior;

public abstract class AbstractArkCard extends CustomCard {

    public AbstractArkCard(String ID, String NAME, String IMG_PATH, int COST, String DESCRIPTION, CardType TYPE, CardColor COLOR, CardRarity RARITY, CardTarget TARGET){
        super(ID,NAME,IMG_PATH,COST,DESCRIPTION,TYPE,COLOR,RARITY,TARGET);
    }

    @Override
    public abstract void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster);

    @Override
    public abstract void upgrade();

    @Override
    public AbstractCard makeCopy() {
        return super.makeCopy();
    }

    public void receiveScreenTargetCreature(AbstractCreature source, AbstractCreature target) { }

    public void receiveScreenTargetHitbox(Hitbox hitbox) { }

    public void receiveEnd() { }

    @SpirePatch(
            cls = "basemod.patches.com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar.ColorTabBarFix$Render",
            method = "Insert",
            paramtypez = {ColorTabBar.class, SpriteBatch.class, float.class, ColorTabBar.CurrentTab.class},
            optional = true
    )
    public static class ColorTabPatch {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"tabName", "playerClass"}
        )
        public static void Insert(ColorTabBar _instance, SpriteBatch sb, float y, ColorTabBar.CurrentTab curTab, @ByRef String[] tabName, AbstractPlayer.PlayerClass playerClass) {
            tabName[0] = tabName[0].replace("Ark_nights", getName());
        }
    }

    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(FontHelper.class, "renderFontCentered");
            int[] loc = LineFinder.findAllInOrder(ctMethodToPatch, methodCallMatcher);
            return new int[]{loc[0]};
        }
    }

    private static String getName(){
        return CardCrawlGame.languagePack.getUIString("ARKNIGHTS_NAME").TEXT[0];
    }
}
