package ArknightsMod.Vfx.UI;

import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Helper.ArknightsImageMaster;
import ArknightsMod.Helper.OperatorHelper;
import ArknightsMod.Screens.RegroupScreen;
import ArknightsMod.Screens.RegroupScreen.ScreenListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;

public class RegroupEffect extends AbstractGameEffect implements ScreenListener {
    private boolean openedScreen = false;
    private Color screenColor;
    private Color bgColor;

    public RegroupEffect() {
        this.screenColor = AbstractDungeon.fadeColor.cpy();
        this.duration = 1.5F;
        this.screenColor.a = 0.0F;
        this.bgColor = new Color(1.0F, 1.0F, 1.0F, 0.6F);
        AbstractDungeon.overlayMenu.proceedButton.hide();
        RegroupScreen.subscribe(this);
    }

    @Override
    public void update() {
        if (!AbstractDungeon.isScreenUp) {
            this.duration -= Gdx.graphics.getDeltaTime();
            this.screenColor.a = Interpolation.fade.apply(1.0F, 0.0F, (this.duration - 1.0F) * 2.0F);
        }

        if (this.duration < 1.0F && !this.openedScreen) {
            this.openedScreen = true;
            RegroupScreen.Inst.open(OperatorHelper.operatorsInDeck(), RegroupScreen.getCollectGroup());
        }
        if(this.openedScreen && !AbstractDungeon.isScreenUp) {
            RegroupScreen.Inst.update();
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(this.screenColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
        if(this.openedScreen) {
            sb.setColor(bgColor);
            sb.draw(ArknightsImageMaster.REGROUP_BG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
            RegroupScreen.Inst.render(sb);
        }
    }

    @Override
    public void dispose() {

    }

    @Override
    public void receiveClose() {
        ArrayList<AbstractCard> deckList = RegroupScreen.screens.get(0).cards;
        ArrayList<AbstractCard> extraList = RegroupScreen.screens.get(1).cards;
        ArrayList<AbstractCard> tmp = AbstractDungeon.player.masterDeck.group;

        for(int i = tmp.size() - 1 ; i >= 0 ; i--) {
            AbstractCard c = tmp.get(i);
            if(c instanceof AbstractOperatorCard) {
                tmp.remove(c);
            }
        }
        RegroupScreen.collectedOperators.clear();

        tmp.addAll(deckList);
        for(AbstractCard c : extraList) {
            RegroupScreen.collectedOperators.add(c.cardID);
        }

        if (AbstractDungeon.getCurrRoom() instanceof RestRoom) {
            RestRoom r = (RestRoom)AbstractDungeon.getCurrRoom();
            r.campfireUI.reopen();
        }
        RegroupScreen.unSubscribe(this);
        this.isDone = true;
    }
}
