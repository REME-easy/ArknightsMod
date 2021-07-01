package ArknightsMod.Screens;

import ArknightsMod.Helper.ArknightsTipHelper;
import ArknightsMod.Utils.CustomScrollBar;
import basemod.BaseMod;
import basemod.abstracts.CustomSavable;
import basemod.interfaces.ISubscriber;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.google.gson.reflect.TypeToken;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.mainMenu.ScrollBarListener;
import com.megacrit.cardcrawl.ui.buttons.GridSelectConfirmButton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.overlayMenu;

public class RegroupScreen implements CustomSavable<ArrayList<String>>, ISubscriber {
    private static final Logger logger = LogManager.getLogger(RegroupScreen.class);
    private static final int CARDS_PER_LINE = 4;
    private static final float NORMAL_SCALE = 0.6F;
    private static final float HOVERED_SCALE = 1.0F;
    private static final float TRACK_W = 54.0F * Settings.scale;
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString("ARKNIGHTS_CAMPFIRE").TEXT;
    private static AbstractCard draggingCard = null;
    private static AbstractCard hoveredCard = null;
    private static int draggingStartScreen = -1;
    private static boolean isClosed = false;
    private static GridSelectConfirmButton confirmButton = new GridSelectConfirmButton(TEXT[2]);
    public static RegroupScreen Inst = new RegroupScreen();
    public static ArrayList<ListScreen> screens = new ArrayList<>();

    public static ArrayList<String> collectedOperators = new ArrayList<>();
    private static ArrayList<ScreenListener> listeners = new ArrayList<>();

    public static void subscribe(ScreenListener listener) {
        listeners.add(listener);
    }

    public static void unSubscribe(ScreenListener listener) {
        listeners.remove(listener);
    }

    private RegroupScreen() {
        BaseMod.subscribe(this);
        BaseMod.addSaveField("Arknights_Collect", this);
    }

    public static ArrayList<AbstractCard> getCollectGroup() {
        ArrayList<AbstractCard> cards = new ArrayList<>();
        for(String s : collectedOperators) {
            AbstractCard c = CardLibrary.getCopy(s);
            if(c != null) cards.add(c);
        }
        return cards;
    }

    public void open(ArrayList<AbstractCard> left, ArrayList<AbstractCard> right) {
        screens.add(new ListScreen(left, 0.0F));
        screens.add(new ListScreen(right, Settings.WIDTH * 0.5F));
        confirmButton.isDisabled = false;
        isClosed = false;
        overlayMenu.proceedButton.hide();
        overlayMenu.cancelButton.hide();
        overlayMenu.hideCombatPanels();
        confirmButton.hideInstantly();
        confirmButton.show();
    }

    private void close() {
        for(int i = listeners.size() - 1; i >= 0; i--) {
            ScreenListener l = listeners.get(i);
            l.receiveClose();
        }
        for(ListScreen l : screens) {
            l.close();
        }
        screens.clear();
        draggingCard = null;
        hoveredCard = null;
    }

    public void update() {
        if(isClosed) {
            close();
            isClosed = false;
            return;
        }
        hoveredCard = null;
        for(ListScreen l : screens) {
            l.update();
        }

        if (hoveredCard != null) {
            if(InputHelper.justClickedLeft) {
                draggingCard = hoveredCard;
                draggingCard.targetDrawScale = HOVERED_SCALE;
                draggingCard.beginGlowing();
                draggingStartScreen = MouseHoveredScreen();
                this.calculateScrollBounds();
            }
            hoveredCard.targetDrawScale = HOVERED_SCALE;
            if(InputHelper.justReleasedClickRight) {
                CardCrawlGame.cardPopup.open(hoveredCard);
            }
        }

        if(InputHelper.justReleasedClickLeft && draggingCard != null) {
            screens.get(draggingStartScreen).cards.remove(draggingCard);
            screens.get(MouseHoveredScreen()).cards.add(draggingCard);
            draggingCard.stopGlowing();
            draggingCard = null;
            this.calculateScrollBounds();
        }

        confirmButton.update();
        if(confirmButton.hb.clicked) {
            confirmButton.hb.clicked = false;
            isClosed = true;
        }

    }

    private void calculateScrollBounds() {
        for(ListScreen l : screens) {
            l.calculateScrollBounds();
        }
    }

    public void render(SpriteBatch sb) {
        for(ListScreen l : screens) {
            l.render(sb);
        }
        if(hoveredCard != null) {
            hoveredCard.renderHoverShadow(sb);
            hoveredCard.render(sb);
            hoveredCard.renderCardTip(sb);
        }else if(draggingCard != null) {
            draggingCard.renderHoverShadow(sb);
            draggingCard.render(sb);
            draggingCard.renderCardTip(sb);
            draggingCard.target_x = InputHelper.mX;
            draggingCard.target_y = InputHelper.mY;
        }
        confirmButton.render(sb);
        ArknightsTipHelper.renderViewTip(sb, TEXT[3], Settings.WIDTH / 4.0F, 96.0F * Settings.scale, Settings.CREAM_COLOR);
        ArknightsTipHelper.renderViewTip(sb, TEXT[4], Settings.WIDTH * 0.75F, 96.0F * Settings.scale, Settings.CREAM_COLOR);
    }

    private static int MouseHoveredScreen() {
        return InputHelper.mX > Settings.WIDTH / 2.0F ? 1 : 0;
    }

    @Override
    public ArrayList<String> onSave() {
        return collectedOperators;
    }

    @Override
    public void onLoad(ArrayList<String> strings) {
        if(strings != null) {
            collectedOperators.clear();
            collectedOperators.addAll(strings);
            logger.info(strings);
            logger.info(collectedOperators);
        }
    }

    public Type savedType()
    {
        return new TypeToken<ArrayList<String>>(){}.getType();
    }


    public interface ScreenListener {
        void receiveClose();
    }

    public class ListScreen implements ScrollBarListener {
        private CustomScrollBar scrollBar;
        private float drawStartX;
        private float drawStartY;
        private float padX;
        private float padY;
        private float scrollLowerBound;
        private float scrollUpperBound;
        private float currentDiffY;
        private Hitbox hb;
        public ArrayList<AbstractCard> cards = new ArrayList<>();

        ListScreen(ArrayList<AbstractCard> cards, float offSetX) {
            this.scrollLowerBound = -Settings.DEFAULT_SCROLL_LIMIT;
            this.scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT;
            this.cards.addAll(cards);
            this.scrollBar = new CustomScrollBar(this, offSetX + Settings.WIDTH / 2.0F - TRACK_W / 2.0F, Settings.HEIGHT / 2.0F, Settings.HEIGHT - 256.0F * Settings.scale);
            this.hb = new Hitbox(offSetX, 0.0F, Settings.WIDTH / 2.0F, Settings.HEIGHT);
            currentDiffY = 0.0F;
            drawStartX = AbstractCard.IMG_WIDTH * 0.375F + offSetX;
            drawStartY = Settings.HEIGHT * 0.75F;
            padX = AbstractCard.IMG_WIDTH * 0.5F + Settings.CARD_VIEW_PAD_X * 1.8F;
            padY =  AbstractCard.IMG_HEIGHT * 0.5F + Settings.CARD_VIEW_PAD_Y * 2.0F;
            this.calculateScrollBounds();
        }

        void close() {
            cards.clear();
        }

        private void calculateScrollBounds() {
            int scrollTmp;
            if(cards.size() > 10) {
                scrollTmp = cards.size() / CARDS_PER_LINE - 2;
                if(cards.size() % CARDS_PER_LINE != 0) ++scrollTmp;
                this.scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT + scrollTmp * padY;
            } else {
                this.scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT;
            }
        }

        public void update() {
            boolean isDraggingScrollBar;
            isDraggingScrollBar = scrollBar.update(draggingCard == null);
            if(hb.hovered) {
                if (!isDraggingScrollBar) {
                    this.updateScrolling();
                }
            }
            updateCardPositionAndHover();
            hb.update();
        }

        void updateScrolling() {
            boolean isDraggingScrollBar = scrollBar.update(draggingCard == null);
            if (!isDraggingScrollBar) {
                if (InputHelper.scrolledDown) {
                    currentDiffY += Settings.SCROLL_SPEED;
                } else if (InputHelper.scrolledUp) {
                    currentDiffY -= Settings.SCROLL_SPEED;
                }
            }
            this.resetScrolling();
            this.updateBarPosition();
        }

        private void resetScrolling() {
            if (this.currentDiffY < this.scrollLowerBound) {
                this.currentDiffY = MathHelper.scrollSnapLerpSpeed(this.currentDiffY, this.scrollLowerBound);
            } else if (this.currentDiffY > this.scrollUpperBound) {
                this.currentDiffY = MathHelper.scrollSnapLerpSpeed(this.currentDiffY, this.scrollUpperBound);
            }
        }

        private void updateBarPosition() {
            float percent = MathHelper.percentFromValueBetween(scrollLowerBound, scrollUpperBound, currentDiffY);
            scrollBar.parentScrolledToPercent(percent);
        }

        void updateCardPositionAndHover() {
            int lineNum = 0;
            for(int j = 0; j < cards.size(); j++) {
                int mod = j % CARDS_PER_LINE;
                if (mod == 0 && j != 0) {
                    ++lineNum;
                }
                AbstractCard c = cards.get(j);
                if (draggingCard == null && c.hb.hovered) hoveredCard = c;
                if(c != draggingCard) {
                    if(c != hoveredCard) {
                        c.targetDrawScale = NORMAL_SCALE;
                    }
                    c.target_x = drawStartX + mod * padX;
                    c.target_y = drawStartY + currentDiffY - lineNum * padY;
                }
                c.fadingOut = false;
                c.update();
                c.updateHoverLogic();
            }
        }

        public void render(SpriteBatch sb) {
            hb.render(sb);
            scrollBar.render(sb);
            for(AbstractCard c : cards) {
                if(c != draggingCard) {
                    c.render(sb);
                }
            }
        }

        @Override
        public void scrolledUsingBar(float newPercent) {
            this.currentDiffY = MathHelper.valueFromPercentBetween(this.scrollLowerBound, this.scrollUpperBound, newPercent);
            this.updateBarPosition();
        }
    }

}
