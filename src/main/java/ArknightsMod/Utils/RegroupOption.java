package ArknightsMod.Utils;

import ArknightsMod.Helper.ArknightsImageMaster;
import ArknightsMod.Vfx.UI.RegroupEffect;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;

import java.util.ArrayList;

public class RegroupOption extends AbstractCampfireOption {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ARKNIGHTS_CAMPFIRE");
    private static final String[] text = uiStrings.TEXT;

    private RegroupOption() {
        this.label = text[0];
        this.usable = true;
        this.description = text[1];
        this.img = ArknightsImageMaster.REMOVE_OPTION;
    }

    @Override
    public void useOption() {
        AbstractDungeon.effectList.add(new RegroupEffect());
    }


    @SpirePatch(
            clz = CampfireUI.class,
            method = "initializeButtons"
    )
    public static class CardDupeFix {
        @SpireInsertPatch(
                rloc = 36,
                localvars = {"buttons"}
        )
        public static void Insert(CampfireUI _inst, ArrayList<AbstractCampfireOption> buttons) {
            buttons.add(new RegroupOption());
        }
    }
}

