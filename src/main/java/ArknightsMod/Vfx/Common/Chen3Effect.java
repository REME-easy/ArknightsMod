package ArknightsMod.Vfx.Common;

import ArknightsMod.Helper.ArknightsImageMaster;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class Chen3Effect extends AbstractGameEffect {
    private float x;
    private float y;
    private float sX;
    private float sY;
    private float tX;
    private float tY;
    private TextureAtlas.AtlasRegion img;

    public Chen3Effect(int index, float x, float y, float angle) {
        this.img = ArknightsImageMaster.CHEN_SKILL[index];
        float dX = (float) (150.0F * Settings.scale * Math.cos(angle / 2 * Math.PI));
        float dY = (float) (150.0F * Settings.scale * Math.sin(angle / 2 * Math.PI));
        this.x = x - 64.0F - dX / 2.0F * Settings.scale;
        this.y = y - 64.0F - dY / 2.0F * Settings.scale;
        this.sX = this.x;
        this.sY = this.y;
        this.tX = this.x + dX / 2.0F * Settings.scale;
        this.tY = this.y + dY / 2.0F * Settings.scale;
        this.color = Color.WHITE.cpy();
        this.color.a = 0.0F;
        this.startingDuration = 0.4F;
        this.duration = this.startingDuration;
        this.rotation = -135.0F;
        this.rotation = angle;
    }

    public void update() {
        if (this.duration > this.startingDuration / 2.0F) {
            this.color.a = Interpolation.exp10In.apply(0.8F, 0.0F, (this.duration - this.startingDuration / 2.0F) / (this.startingDuration / 2.0F));
            this.x = Interpolation.fade.apply(this.tX, this.sX, (this.duration - this.startingDuration / 2.0F) / (this.startingDuration / 2.0F));
            this.y = Interpolation.fade.apply(this.tY, this.sY, (this.duration - this.startingDuration / 2.0F) / (this.startingDuration / 2.0F));
        } else {
            this.color.a = Interpolation.pow5In.apply(0.0F, 0.8F, this.duration / (this.startingDuration / 2.0F));
        }

        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.setBlendFunction(770, 1);
        sb.draw(img, x - img.originalWidth / 4.0F, y - img.originalHeight / 4.0F, img.originalWidth / 4.0F, img.originalHeight / 4.0F, img.originalWidth, img.originalHeight, Settings.scale * 0.5F, Settings.scale * 0.5F, rotation - 90.0F);
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }
}
