package ArknightsMod.Vfx.Common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class SvashBlurWaveAdditiveEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private float speed;
    private float speedStart;
    private float speedTarget;
    private float stallTimer;
    private TextureAtlas.AtlasRegion img;
    private float flipper;
    private float alpha;

    public SvashBlurWaveAdditiveEffect(float x, float y, Color color, float chosenSpeed, float alpha) {
        this.img = ImageMaster.BLUR_WAVE;
        this.stallTimer = MathUtils.random(0.0F, 0.3F);
        this.rotation = MathUtils.random(-90.0F, 90.0F);
        this.scale = MathUtils.random(0.5F, 0.9F);
        this.x = x - (float)this.img.packedWidth / 2.0F;
        this.y = y - (float)this.img.packedHeight / 2.0F;
        this.duration = 1.0F;
        this.color = color;
        this.renderBehind = MathUtils.randomBoolean();
        this.speedStart = chosenSpeed;
        this.speedTarget = 0.0F;
        this.speed = this.speedStart;
        this.flipper = 270.0F;
        this.alpha = alpha;
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
            this.speed = Interpolation.fade.apply(this.speedStart, this.speedTarget, 1.0F - this.duration);
            this.x += tmp.x;
            this.y += tmp.y;
            this.scale *= 1.0F + Gdx.graphics.getDeltaTime() * 2.0F;
            this.duration -= Gdx.graphics.getDeltaTime();
            if (this.duration < 0.0F) {
                this.isDone = true;
            } else if (this.duration > 1.5F) {
                this.color.a = Interpolation.fade.apply(0.0F, alpha, (2.0F - this.duration) * 2.0F);
            } else if (this.duration < 0.5F) {
                this.color.a = Interpolation.fade.apply(0.0F, alpha, this.duration * 2.0F);
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
