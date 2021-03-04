package ArknightsMod.Vfx.Common;

import ArknightsMod.Helper.ArknightsImageMaster;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class ChenLongEffect extends AbstractGameEffect {
    private final float sX;
    private final float sY;
    private float x;
    private float y;
    private float rotation;
    private Color color;
    private AtlasRegion img;

    private final float DISTANCE = 150.0F;

    public ChenLongEffect(float x, float y) {
        this.sX = x;
        this.sY = y;
        this.x = sX;
        this.y = sY - DISTANCE;
        this.color = Color.RED.cpy();
        this.startingDuration = this.duration = 3.0F;
    }

    public void update() {
        duration -= Gdx.graphics.getDeltaTime();
        if(duration > 2.0F) {
            color.a = Interpolation.exp10.apply(0.0F, 1.0F, 3.0F - duration);
            rotation = Interpolation.exp10In.apply(270.0F, 90.0F, 3.0F - duration);
        }else if(duration < 1.0F) {
            color.a = Interpolation.exp10.apply(0.0F, 1.0F, duration);
            rotation = Interpolation.exp10Out.apply(-90.0F, 90.0F, duration);
        }

        if(duration < 0.0F) {
            isDone = true;
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(color);
        for (int i = 0; i < 5; i++) {
            x = (float) (sX + Math.cos((rotation + 60.0F - i * 10.0F) / 2 * Math.PI) * DISTANCE);
            y = (float) (sY + Math.sin((rotation + 60.0F - i * 10.0F) / 2 * Math.PI) * DISTANCE);
            img = ArknightsImageMaster.CHEN_LONG[i];
            sb.draw(img, x - img.originalWidth / 4.0F, y - img.originalHeight / 4.0F, img.originalWidth / 4.0F, img.originalHeight / 4.0F, img.originalWidth, img.originalHeight, Settings.scale * 0.5F, Settings.scale * 0.5F, rotation - 90.0F);
        }
    }

    public void dispose() {
    }
}
