package ArknightsMod.Powers.Counter;

import ArknightsMod.Actions.AddCountAction;
import ArknightsMod.Operators.AbstractOperator;
import ArknightsMod.Powers.Operator.AbstractArkPower;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public abstract class AbstractCounterPower extends AbstractArkPower {
    public int currentAmount;
    public int maxAmount;
    public boolean isAcitve;

    private static Color RedColor = Color.RED.cpy();
    private static Color GreenColor = Color.GREEN.cpy();

    public AbstractCounterPower(AbstractOperator owner) {
        this.owner = owner;
        this.isAcitve = false;
        String path128 = "ArkImg/powers/counter84.png";
        String path48 = "ArkImg/powers/counter32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
    }

    void addCount(int i) {
        this.addToBot(new AddCountAction(this, i));
    }

    public abstract void activePower();

    @Override
    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        if(amount - currentAmount == 1)
            c = RedColor;
        else
            c = GreenColor;
        super.renderAmount(sb, x, y, c);
        FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, this.currentAmount + "/" + this.maxAmount, x, y + 17.0F * Settings.scale, this.fontScale, c);
    }
}
