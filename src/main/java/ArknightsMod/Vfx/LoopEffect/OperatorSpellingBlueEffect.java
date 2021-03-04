package ArknightsMod.Vfx.LoopEffect;

import ArknightsMod.Operators.AbstractOperator;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class OperatorSpellingBlueEffect extends AbstractGameEffect {
    AbstractOperator creature;
    float x;
    float y;

    public OperatorSpellingBlueEffect(AbstractOperator creature) {
        this.creature = creature;
        this.x = creature.hb.cX;
        this.y = creature.hb.cY;
    }

    public void update() {

        int i;
        for(i = 0; i < 5; ++i) {
            AbstractDungeon.effectsQueue.add(new OperatorParticleBlueEffect(this.x, this.y));
        }
        for(i = 0; i < 2; ++i) {
            AbstractDungeon.effectsQueue.add(new ExhaustEmberBlueEffect(this.x, this.y));
        }
        this.isDone = true;


    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}
