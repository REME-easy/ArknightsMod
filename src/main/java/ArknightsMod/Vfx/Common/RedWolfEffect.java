package ArknightsMod.Vfx.Common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class RedWolfEffect extends AbstractGameEffect {
    private float x;
    private float y;

    private Color color;
    private float scale;


    private static final TextureAtlas.AtlasRegion img = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("ArkImg/vfx/red_2.png"), 0, 0, 128, 128);

    public RedWolfEffect(float x, float y) {
        this.x = x;
        this.y = y;
        this.color = new Color(1.0F, 1.0F, 1.0F, 1.0F);
        this.duration = 1.0F;
        this.scale = 2.0F *  Settings.scale;
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration > 0.3F) {
            this.scale = Interpolation.exp5Out.apply(2.0F * Settings.scale, 4.0F * Settings.scale, 1.0F - this.duration);
        } else {
            this.color.a = Interpolation.fade.apply(1.0F, 0.0F, 1.0F - this.duration);
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
            sb.draw(img, x - (float)img.packedWidth / 2.0F, y - (float)img.packedHeight / 2.0F, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, (float)img.packedWidth, (float)img.packedHeight, this.scale, this.scale, 0.0F);
    }

    public void dispose() {
    }


}
