package ArknightsMod.Vfx.LoopEffect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import static java.lang.Math.*;

public class LapplandWolfSoulEffect extends AbstractGameEffect {
    private float cX;
    private float cY;
    private boolean isBlack;

    private float a;
    private Vector2 p;

    private float scaleX;

    private static final float B = 50.0F;

    private static AtlasRegion blackWolfImg = new AtlasRegion(ImageMaster.loadImage("ArkImg/vfx/langtou_02.png"), 0, 0, 128, 128);
    private static AtlasRegion whiteWolfImg = new AtlasRegion(ImageMaster.loadImage("ArkImg/vfx/langtou_01.png"), 0, 0, 128, 128);

    public LapplandWolfSoulEffect(float x, float y, boolean isBlack) {
        this.cX = x;
        this.cY = y;
        this.isBlack = isBlack;
        this.a = 270.0F;
        this.scaleX = 1.0F;
    }

    private float getR(float a) {
        return (float) (B / sqrt(1f - 0.75f * pow(cos(a), 2)));
    }

    public void update() {
        this.a += Gdx.graphics.getDeltaTime() * 2.0F;
        if(this.a >= 360.0F) this.a -= 360.0F;
        float r = getR(this.a);

        this.p.x = r * (float) cos(this.a);
        this.p.y = r * (float)Math.sin(this.a);
        this.p.rotate(45.0F);

        if(this.isBlack)
            this.renderBehind = this.p.y > 0.0F;
        else
            this.renderBehind = this.p.y < 0.0F;
        this.scaleX = this.p.y / -50.0F;
        if(this.scaleX == 0.0F) this.scaleX = 0.01F;
    }

    public void render(SpriteBatch sb) {
        if(isBlack)
            sb.draw(blackWolfImg, this.cX + p.x, this.cY + p.y, 0, 0, 128, 128, 0.8F * scaleX, 0.8F, 0.0F);
        else
            sb.draw(whiteWolfImg, this.cX - p.x, this.cY - p.y, 0, 0, 128, 128, 0.8F * -scaleX, 0.8F, 0.0F);
    }

    public void dispose() {
    }
}
