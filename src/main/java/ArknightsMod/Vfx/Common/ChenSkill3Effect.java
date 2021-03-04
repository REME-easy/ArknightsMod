package ArknightsMod.Vfx.Common;

import ArknightsMod.Helper.GeneralHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class ChenSkill3Effect extends AbstractGameEffect {
    private final int index;
    private final boolean isFinal;
    private float x;
    private float y;
    private float sX;
    private float sY;
    private float tX;
    private float tY;
    private float scaleX;
    private float scaleY;
    private float targetScale;
    private Color color2;

    public ChenSkill3Effect(float x, float y, int index, boolean isFinal) {
        this.index = index;
        this.isFinal = isFinal;
        this.rotation = isFinal ? 0.0F : MathUtils.random(0.0F, 360.0F);
        float dX = (float) (this.x + Math.cos(this.rotation / 2 * Math.PI) * 300.0F);
        float dY = (float) (this.y + Math.sin(this.rotation / 2 * Math.PI) * 300.0F);
        this.x = x - 64.0F - dX / 2.0F * Settings.scale;
        this.y = y - 64.0F - dY / 2.0F * Settings.scale;
        this.sX = this.x;
        this.sY = this.y;
        this.tX = this.x + dX / 2.0F * Settings.scale;
        this.tY = this.y + dY / 2.0F * Settings.scale;
        this.color = Color.RED.cpy();
        this.color2 = Color.RED.cpy();
        this.color.a = 0.0F;
        this.startingDuration = 0.4F;
        this.duration = this.startingDuration;
        this.targetScale = 2.0F;
        this.scaleX = 0.01F;
        this.scaleY = 0.01F;
        GeneralHelper.addEffect(new Chen3Effect(MathUtils.random(0, 4), x, y, rotation));

    }

    public void update() {
        if(duration == startingDuration) {
            CardCrawlGame.sound.play("chen_3");
            if(isFinal)
                CardCrawlGame.sound.play("chen_2");
        }
        if (this.duration > this.startingDuration / 2.0F) {
            this.color.a = Interpolation.exp10In.apply(1.0F, 0.0F, (this.duration - this.startingDuration / 2.0F) / (this.startingDuration / 2.0F));
            this.scaleX = Interpolation.exp10In.apply(this.targetScale, 0.1F, (this.duration - this.startingDuration / 2.0F) / (this.startingDuration / 2.0F));
            this.scaleY = this.scaleX;
            this.x = Interpolation.fade.apply(this.tX, this.sX, (this.duration - this.startingDuration / 2.0F) / (this.startingDuration / 2.0F));
            this.y = Interpolation.fade.apply(this.tY, this.sY, (this.duration - this.startingDuration / 2.0F) / (this.startingDuration / 2.0F));
        } else {
            this.scaleX = Interpolation.pow2In.apply(0.5F, this.targetScale, this.duration / (this.startingDuration / 2.0F));
            this.color.a = Interpolation.pow5In.apply(0.0F, 1.0F, this.duration / (this.startingDuration / 2.0F));
        }

        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color2);
        sb.draw(ImageMaster.ANIMATED_SLASH_VFX, this.x, this.y, 64.0F, 64.0F, 128.0F, 128.0F, this.scaleX * 0.4F * MathUtils.random(0.95F, 1.05F) * Settings.scale, this.scaleY * 0.7F * MathUtils.random(0.95F, 1.05F) * Settings.scale, this.rotation, 0, 0, 128, 128, false, false);
        sb.setColor(this.color);
        sb.draw(ImageMaster.ANIMATED_SLASH_VFX, this.x, this.y, 64.0F, 64.0F, 128.0F, 128.0F, this.scaleX * 0.7F * MathUtils.random(0.95F, 1.05F) * Settings.scale, this.scaleY * MathUtils.random(0.95F, 1.05F) * Settings.scale, this.rotation, 0, 0, 128, 128, false, false);
    }

    public void dispose() {
    }
}
