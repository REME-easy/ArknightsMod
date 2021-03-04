package ArknightsMod.Vfx.Common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class Chen2Effect extends AbstractGameEffect {

    private float x;
    private float y;
    private AtlasRegion img;

    public Chen2Effect(float x, float y) {
        this.img = ImageMaster.VERTICAL_IMPACT;
        this.scale = Settings.scale * 2.0F;
        this.x = x - (float)this.img.packedWidth / 2.0F * scale;
        this.y = y - (float)this.img.packedHeight / 2.0F * scale;
        this.startingDuration = 0.3F;
        this.duration = 1.0F;
        this.rotation = MathUtils.random(260.0F, 270.0F);
        this.color = Color.RED.cpy();
        this.renderBehind = false;
    }

    @Override
    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }

        if (this.duration > 0.8F) {
            this.color.a = Interpolation.fade.apply(0.8F, 0.0F, (this.duration - 0.8F) * 5.0F);
        } else if(this.duration < 0.2F){
            this.color.a = Interpolation.fade.apply(0.0F, 0.8F, this.duration * 5.0F);
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.setBlendFunction(770, 1);
        sb.draw(this.img, this.x + MathUtils.random(-10.0F, 10.0F) * Settings.scale, this.y, (float)this.img.packedWidth / 2.0F, 0.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale * 0.3F, this.scale * 0.8F, this.rotation - 18.0F);
        sb.draw(this.img, this.x + MathUtils.random(-10.0F, 10.0F) * Settings.scale, this.y, (float)this.img.packedWidth / 2.0F, 0.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale * 0.3F, this.scale * 0.8F, this.rotation + MathUtils.random(12.0F, 18.0F));
        sb.draw(this.img, this.x + MathUtils.random(-10.0F, 10.0F) * Settings.scale, this.y, (float)this.img.packedWidth / 2.0F, 0.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale * 0.4F, this.scale * 0.5F, this.rotation - MathUtils.random(-10.0F, 14.0F));
        sb.draw(this.img, this.x + MathUtils.random(-10.0F, 10.0F) * Settings.scale, this.y, (float)this.img.packedWidth / 2.0F, 0.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale * 0.7F, this.scale * 0.9F, this.rotation + MathUtils.random(20.0F, 28.0F));
        sb.draw(this.img, this.x + MathUtils.random(-10.0F, 10.0F) * Settings.scale, this.y, (float)this.img.packedWidth / 2.0F, 0.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale * 1.5F, this.scale * MathUtils.random(1.4F, 1.6F), this.rotation);
        Color c = Color.BLACK.cpy();
        c.a = this.color.a;
        sb.setColor(c);
        sb.draw(this.img, this.x + MathUtils.random(-10.0F, 10.0F) * Settings.scale, this.y, (float)this.img.packedWidth / 2.0F, 0.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale, this.scale * MathUtils.random(0.8F, 1.2F), this.rotation);
        sb.draw(this.img, this.x + MathUtils.random(-10.0F, 10.0F) * Settings.scale, this.y, (float)this.img.packedWidth / 2.0F, 0.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale, this.scale * MathUtils.random(0.4F, 0.6F), this.rotation);
        sb.draw(this.img, this.x + MathUtils.random(-10.0F, 10.0F) * Settings.scale, this.y, (float)this.img.packedWidth / 2.0F, 0.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale * 0.5F, this.scale * 0.7F, this.rotation + MathUtils.random(20.0F, 28.0F));
        sb.setBlendFunction(770, 771);
    }

    @Override
    public void dispose() {

    }
}
