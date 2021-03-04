package ArknightsMod.Vfx.UI;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class ShowTeamEffect extends AbstractGameEffect {

    private static String PREFIX = CardCrawlGame.languagePack.getUIString("ARKNIGHTS_OPERATION").TEXT[0];
    private String name;

    public ShowTeamEffect(String name) {
        this.color = Settings.GOLD_COLOR.cpy();
        this.name = name;
        this.duration = 5.0F;
    }
    //TODO
    // 存档问题

    @Override
    public void update() {
        super.update();
        if(this.duration < 2.0F) {
            this.color.a = Interpolation.exp10.apply(0.0F, 1.0F, this.duration / 2.0F);
        }else if(this.duration > 3.0F) {
            this.color.a = Interpolation.exp10.apply(0.0F, 1.0F, 2.5F - this.duration / 2.0F);
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        FontHelper.renderFontCentered(sb, FontHelper.dungeonTitleFont, PREFIX + name, (float) Settings.WIDTH * 0.5F, (float)Settings.HEIGHT * (1.1F - 0.2F * this.color.a), this.color, 0.4F);
    }

    @Override
    public void dispose() {

    }
}
