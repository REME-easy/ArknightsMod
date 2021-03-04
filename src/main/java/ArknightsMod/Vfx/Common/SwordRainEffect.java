package ArknightsMod.Vfx.Common;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class SwordRainEffect extends AbstractGameEffect {
    public SwordRainEffect() {
    }

    public void update() {
        int i ;
        float x, y;
        for(i = AbstractDungeon.getMonsters().monsters.size() ; i > 0; --i) {
            AbstractMonster m = AbstractDungeon.getMonsters().monsters.get(i - 1);
            if(!m.isDeadOrEscaped() && !m.isDead) {
                x = m.drawX;
                y = m.drawY + 900.0F;
                AbstractDungeon.effectsQueue.add(new FlyingSwordEffect(x, y, 270.0F, false));
            }
        }
        this.isDone = true;

    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}
