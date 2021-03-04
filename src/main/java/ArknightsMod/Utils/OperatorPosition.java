package ArknightsMod.Utils;

import ArknightsMod.Helper.ArknightsImageMaster;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;

public class OperatorPosition extends Hitbox {
    private float alpha = 0.0F;
    public float targetAlpha = 1.0F;

    public int index = -1;
    private boolean isOccupied = false;

    private Color color = new Color(1.0F, 1.0F, 1.0F, 1.0F);

    public OperatorPosition(float x, float y) {
        super(x, y);
    }

    public void show() {
        this.alpha = 0.0F;
        this.targetAlpha = 1.0F;
        this.isOccupied = false;
    }

    public void hide() {
        this.targetAlpha = 0.0F;
        this.isOccupied = true;
    }

    @Override
    public void update() {
        if(!this.isOccupied) {
            super.update();
            if(alpha != targetAlpha) {
                alpha = MathUtils.lerp(alpha, targetAlpha, Gdx.graphics.getDeltaTime() * 5.0F);
                if(Settings.FAST_MODE) alpha = MathUtils.lerp(alpha, targetAlpha, Gdx.graphics.getDeltaTime() * 5.0F);
            }
        }

    }

    @Override
    public void render(SpriteBatch sb) {
        if(!this.isOccupied) {
            super.render(sb);
            color.a = alpha;
            sb.setColor(color);
            sb.draw(ArknightsImageMaster.OPERATOR_POSITION, x - 46.0F * Settings.scale, y + (1.0F - alpha) * 50.0F * Settings.scale, 226.0F * Settings.scale, 100.0F * Settings.scale);
        }
    }
}
