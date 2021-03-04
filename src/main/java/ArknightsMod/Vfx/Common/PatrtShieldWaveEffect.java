package ArknightsMod.Vfx.Common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class PatrtShieldWaveEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private float speed;
    private float speedStart;
    private float speedTarget;
    private float stallTimer;
    private TextureAtlas.AtlasRegion img;
    private float flipper;

    public PatrtShieldWaveEffect(float x, float y) {
        this.img = ImageMaster.BLUR_WAVE;
        this.stallTimer = MathUtils.random(0.0F, 0.3F);
        this.rotation = MathUtils.random(360.0F);
        this.scale = MathUtils.random(0.2F, 0.3F);
        this.x = x - (float)this.img.packedWidth / 2.0F;
        this.y = y - (float)this.img.packedHeight / 2.0F;
        this.duration = 0.1F;
        this.color = new Color(MathUtils.random(0.8F, 1.0F), MathUtils.random(0.8F, 1.0F), MathUtils.random(0.2F, 0.4F), 0.01F);
        this.renderBehind = MathUtils.randomBoolean();
        this.speedStart = 1000.0F * Settings.scale;
        this.speedTarget = 2000.0F * Settings.scale;
        this.speed = this.speedStart;
        this.flipper = 270.0F;
        color.g -= MathUtils.random(0.1F);
        color.b -= MathUtils.random(0.2F);
        color.a = 0.0F;
    }

    public void update() {
        this.stallTimer -= Gdx.graphics.getDeltaTime();
        if (this.stallTimer < 0.0F) {
            Vector2 tmp = new Vector2(MathUtils.cosDeg(this.rotation), MathUtils.sinDeg(this.rotation));
            tmp.x *= this.speed * Gdx.graphics.getDeltaTime();
            tmp.y *= this.speed * Gdx.graphics.getDeltaTime();
            this.speed = Interpolation.fade.apply(this.speedStart, this.speedTarget, 1.0F - this.duration * 10.0F);
            this.x += tmp.x;
            this.y += tmp.y;
            this.scale *= 1.0F + Gdx.graphics.getDeltaTime() * 2.0F;
            this.duration -= Gdx.graphics.getDeltaTime();
            if (this.duration < 0.0F) {
                this.isDone = true;
            } else if (this.duration > 0.08F) {
                this.color.a = Interpolation.fade.apply(0.0F, 0.7F, (2.0F - this.duration) * 2.0F);
            } else if (this.duration < 0.02F) {
                this.color.a = Interpolation.fade.apply(0.0F, 0.7F, this.duration * 2.0F);
            }
        }

    }

    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 1);
        sb.setColor(this.color);
        sb.draw(this.img, this.x, this.y, (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale + MathUtils.random(-0.08F, 0.08F), this.scale + MathUtils.random(-0.08F, 0.08F), this.rotation + this.flipper + MathUtils.random(-3.0F, 3.0F));
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }
}
