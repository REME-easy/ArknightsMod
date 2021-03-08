package ArknightsMod.Operators.Snipers.Adnachiel;

import ArknightsMod.Cards.Operator.AbstractOperatorCard;
import ArknightsMod.Cards.Operator.Snipers.AdnachCard;
import ArknightsMod.Operators.AbstractOperator;
import basemod.ReflectionHacks;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Adnachiel extends AbstractOperator {
    private static final String ID = "Arknights_Adnachiel";
    private static final String ATLAS = "Operators/Adnach/adnach.atlas";
    private static final String JSON = "Operators/Adnach/adnach.json";
    private static final int MAX_HP = 10;
    private static final int ATK = 4;
    private static final int COOLDOWN = 2;

    private static final int RESUMMON_TIME = 3;
    private static final int LEVEL = 3;

    public Adnachiel(float hb_x, float hb_y){
        super(ID, ATLAS, JSON, ATK, COOLDOWN, MAX_HP, RESUMMON_TIME, LEVEL, OperatorType.SNIPER, hb_x, hb_y);

        AnimationState.TrackEntry e = this.state.setAnimation(0, "Start", false);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.state.addAnimation(0, "Idle", true, 0.0F);

    }

    public Adnachiel(){
        this(0.0F, 0.0F);
    }

    @Override
    public AbstractOperator makeCopy() {
        return new Adnachiel();
    }

    @Override
    public AbstractOperatorCard getOperatorCard() {
        AbstractOperatorCard card = new AdnachCard();
        card.skillindex = skillindex;
        card.currentSkill = currentBattleSkill;
        return card;
    }

    @Override
    public AbstractCreature getAttackTarget() {
        if (super.getAttackTarget() != null) return super.getAttackTarget();
        AbstractMonster m1 = AbstractDungeon.getMonsters().monsters.get(0);
        int d2 = (int)ReflectionHacks.getPrivate(m1, AbstractMonster.class, "intentDmg") * (int)ReflectionHacks.getPrivate(m1, AbstractMonster.class, "intentMultiAmt");
        for(AbstractMonster m : AbstractDungeon.getMonsters().monsters){
            int d1 = (int)ReflectionHacks.getPrivate(m, AbstractMonster.class, "intentDmg") * (int)ReflectionHacks.getPrivate(m, AbstractMonster.class, "intentMultiAmt");
            if(d1 > d2) {
                m1 = m;
                d2 = d1;
            }
        }
        return m1;
    }

    @Override
    public void playStartSfx() {
        CardCrawlGame.sound.play("adnach_start");
    }

    @Override
    public void playSkillSfx() {
        CardCrawlGame.sound.play("adnach_skill");
    }
}
