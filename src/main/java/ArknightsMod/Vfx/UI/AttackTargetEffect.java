package ArknightsMod.Vfx.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class AttackTargetEffect extends AbstractGameEffect {

    private Vector2 from;
    private Vector2 to;
    private Color color = new Color(0.0F, 1.0F, 1.0F, 0.75F);

    private Vector2 controlPoint;
    private Vector2[] points = new Vector2[40];
    private float arrowScaleTimer = 0.0F;

    public AttackTargetEffect() {
        from = new Vector2();
        to = new Vector2();
        for (int i = 0; i < points.length; i++) points[i] = new Vector2();
    }

    public void set(float tx, float ty, float sx, float sy) {
        from.set(sx, sy);
        to.set(tx, ty);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(SpriteBatch sb) {
        float x = to.x;
        float y = to.y;
        controlPoint = new Vector2(x - (x - from.x) / 4.0F, y + (y - from.y - 40.0F * Settings.scale) / 2.0F);
        float arrowScale;

        arrowScaleTimer += Gdx.graphics.getDeltaTime();
        if (arrowScaleTimer > 1.0F) {
            arrowScaleTimer = 1.0F;
        }
        arrowScale = Interpolation.elasticOut.apply(Settings.scale, Settings.scale * 1.2F, arrowScaleTimer);
        sb.setColor(color);

        Vector2 tmp = new Vector2(controlPoint.x - x, controlPoint.y - y);
        tmp.nor();
        drawCurvedLine(sb, new Vector2(from.x, from.y - 40.0F * Settings.scale), new Vector2(x, y), controlPoint);
        sb.draw(ImageMaster.TARGET_UI_ARROW, x - 32.0F, y - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, arrowScale, arrowScale, tmp.angle() + 90.0F, 0, 0, 256, 256, false, false);

    }

    private void drawCurvedLine(SpriteBatch sb, Vector2 start, Vector2 end, Vector2 control) {
        float radius = 16.0F * Settings.scale;

        for(int i = 0; i < this.points.length - 1; ++i) {
            this.points[i] = Bezier.quadratic(this.points[i], (float)i / 40.0F, start, control, end, new Vector2());
            Vector2 tmp;
            float angle;
            if (i != 0) {
                tmp = new Vector2(this.points[i - 1].x - this.points[i].x, this.points[i - 1].y - this.points[i].y);
                angle = tmp.nor().angle() + 90.0F;
            } else {
                tmp = new Vector2(controlPoint.x - this.points[i].x, controlPoint.y - this.points[i].y);
                angle = tmp.nor().angle() + 270.0F;
            }

            sb.draw(ImageMaster.TARGET_UI_CIRCLE, this.points[i].x - 16.0F, this.points[i].y - 16.0F, 16.0F, 16.0F, 32.0F, 32.0F, radius / 18.0F, radius / 18.0F, angle, 0, 0, 128, 128, false, false);
        }

    }

    @Override
    public void dispose() {

    }
}
