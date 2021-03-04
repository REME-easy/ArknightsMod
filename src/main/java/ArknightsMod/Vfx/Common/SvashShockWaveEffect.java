package ArknightsMod.Vfx.Common;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class SvashShockWaveEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private Color color;

    public SvashShockWaveEffect(float x, float y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public void update() {
        int i, num = 30;
        float speed;
        while(num > 0){
            i = 0;
            while(i < 40) {
                speed = MathUtils.random(500.0F + num * 50.0F, 800.0F + num * 50.0F) * Settings.scale;
                AbstractDungeon.effectsQueue.add(new SvashBlurWaveAdditiveEffect(this.x, this.y, this.color.cpy(), speed, 0.4F + num * 0.01F));
                ++i;
            }
            num--;
        }
        CardCrawlGame.sound.playA("svash_3", 0.0F);
        this.isDone = true;
    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }


}
