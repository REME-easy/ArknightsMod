package ArknightsMod.Character;

import ArknightsMod.Monsters.AbstractEnemy;
import ArknightsMod.Operators.AbstractOperator;
import ArknightsMod.Operators.Skills.AbstractSkill;
import ArknightsMod.Screens.TargetArrowScreen;
import ArknightsMod.Utils.OperatorPosition;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.buttons.PeekButton;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.combat.BattleStartEffect;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;

public class OperatorGroup {
    private static final Logger logger = LogManager.getLogger(OperatorGroup.class.getName());
    public ArrayList<AbstractOperator> operators = new ArrayList<>();
    private AbstractOperator hoveredOperator;

    public static AbstractMonster lastMonsterAttack;

    public static int maxSize = 8;

    public static ArrayList<OperatorPosition> hitboxes = new ArrayList<>();

    private static final Vector2[] POINTS = new Vector2[] {
            new Vector2(115, 525), new Vector2(320,525), new Vector2(550,525),
            new Vector2(760, 525), new Vector2(75, 295), new Vector2(280, 295),
            new Vector2(600, 295), new Vector2(810, 295)
    };

    public OperatorGroup(ArrayList<AbstractOperator> input) {
        this.hoveredOperator = null;
        this.operators.addAll(input);
    }

    public OperatorGroup(){
        this.hoveredOperator = null;
    }

//    public void addOperator(int newIndex, AbstractOperator o) {
//        if (newIndex < 0) {
//            newIndex = 0;
//        }
//
//        this.operators.add(newIndex, o);
//    }

    public static void showHitboxes() {
        hitboxes.clear();
        float oX = AbstractDungeon.player.drawX - Settings.WIDTH * 0.25F;
        float oY = AbstractDungeon.player.drawY - AbstractDungeon.floorY;
        for (int i = 0; i < maxSize; i++) {
            hitboxes.add(new OperatorPosition(135.0F * Settings.scale, 100.0F * Settings.scale));
            OperatorPosition hb = hitboxes.get(i);
            hb.translate(POINTS[i].x * Settings.scale + oX, POINTS[i].y * Settings.scale + oY);
            hb.show();
            for(AbstractOperator o : GetOperators()) {
                if(o.index == i) {
                    hb.hide();
                }
            }
            hb.index = i;
        }
    }

    public static void hideHitboxes() {
        for (OperatorPosition hitbox : hitboxes) {
            hitbox.hide();
        }
        hitboxes.clear();
    }

    public void addOperator(AbstractOperator o) {
        this.operators.add(o);
    }

    public static ArrayList<AbstractOperator> GetOperators(){
        return OperatorsFields.operators.get(AbstractDungeon.player).operators;
    }

    public static AbstractOperator GetOperatorByIndex(int i) {
        for (AbstractOperator o : GetOperators()) {
            if(o.index == i) return o;
        }
        return null;
    }


    private void usePreBattleAction() {
        if (!AbstractDungeon.loading_post_combat) {
            for(AbstractOperator o:this.operators){
                o.usePreBattleAction();
            }
        }
        lastMonsterAttack = null;
    }

    private void atEndOfTurn(){
        for(AbstractOperator o:this.operators){
            o.EndOfTurnAction();
        }
    }

    private void atStartOfTurn(){
        for(AbstractOperator o:this.operators){
            if (!o.hasPower("Barricade")) {
                o.loseBlock();
            }
            o.StartOfTurnAction();
        }
    }

    public void onUseCard(AbstractCard card){
        if(operators.size() > 0){
            for(AbstractOperator o:operators){
                if(o.currentBattleSkill != null && o.currentBattleSkill.isSpelling){
                    o.currentBattleSkill.onUseCard(card);
                }
            }
        }
    }

//    public boolean areAllDead(){
//        Iterator var1 = this.operators.iterator();
//
//        AbstractOperator m;
//        do {
//            if (!var1.hasNext()) {
//                return true;
//            }
//
//            m = (AbstractOperator) var1.next();
//        } while(m.isDead);
//
//        return false;
//    }

    public static AbstractOperator GetOperatorByID(String id){
        Iterator var2 = GetOperators().iterator();

        AbstractOperator o;
        do {
            if (!var2.hasNext()) {
                return null;
            }

            o = (AbstractOperator)var2.next();
        } while(!o.id.equals(id));

        return o;
    }

//    public AbstractOperator getRandomOperator(){
//        if(operators.size() > 0){
//            return operators.get(AbstractDungeon.cardRandomRng.random(0, operators.size() - 1));
//        }
//        return null;
//    }

    public void update() {
        boolean tmp = AbstractDungeon.player.hoveredCard == null;

        Iterator var1 = this.operators.iterator();
        AbstractOperator m;
        while(var1.hasNext()) {
            m = (AbstractOperator)var1.next();
            m.update();
            if(m.isDead) {
                var1.remove();

            }
        }
        if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.DEATH) {
            this.hoveredOperator = null;
            var1 = this.operators.iterator();

            label43:
            while(true) {
                do {
                    do {
                        if (!var1.hasNext()) {
                            break label43;
                        }

                        m = (AbstractOperator)var1.next();
                    } while(m.isDying);

                    m.hb.update();
                    m.healthHb.update();
                    m.skillHb.update();
                    m.skillReadyHb.update();
                    if(tmp) m.showAttackCoolDown(-1);
                } while(!m.hb.hovered && !m.healthHb.hovered);

                if (!AbstractDungeon.player.isDraggingCard) {
                    this.hoveredOperator = m;
                    break;
                }
            }

            if (this.hoveredOperator == null) {
                AbstractDungeon.player.hoverEnemyWaitTimer = -1.0F;
            }
        } else {
            this.hoveredOperator = null;
        }
        if(TargetArrowScreen.Inst.isActive) {
            for (OperatorPosition p : hitboxes) {
                p.update();
            }
        }
    }

    private void updateAnimations() {

        for (AbstractOperator m : this.operators) {
            m.updatePowers();
        }

    }

    public void render(SpriteBatch sb) {
        if (this.hoveredOperator != null && !this.hoveredOperator.isDead && (!AbstractDungeon.isScreenUp || PeekButton.isPeeking)) {
            this.hoveredOperator.renderTip();
        }

        if(TargetArrowScreen.Inst.isActive) {
            for(OperatorPosition p : hitboxes) {
                p.render(sb);
            }
        }


        for (AbstractOperator m : this.operators) {
            m.render(sb);
        }
    }

    public void renderReticle(SpriteBatch sb) {

        for (AbstractOperator m : this.operators) {
            if (!m.isDying) {
                m.renderReticle(sb);
            }
        }

    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "<class>"
    )
    public static class OperatorsFields {
        public static SpireField<OperatorGroup> operators = new SpireField(() -> {
            return new OperatorGroup();
        });

        public OperatorsFields() {
        }
    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "update"
    )
    public static class OperatorUpdatePatch{
        public OperatorUpdatePatch(){}

        public static void Postfix(AbstractPlayer _inst){
            (OperatorsFields.operators.get(_inst)).update();
            (OperatorsFields.operators.get(_inst)).updateAnimations();
        }
    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "render"
    )
    public static class OperatorRenderFrontPatch{
        public OperatorRenderFrontPatch(){}

        public static void Postfix(AbstractPlayer _inst, SpriteBatch sb){
            OperatorsFields.operators.get(_inst).render(sb);
        }
    }

//    @SpirePatch(
//            clz = AbstractRoom.class,
//            method = "render",
//            paramtypez = {SpriteBatch.class}
//    )
//    public static class OperatorRenderPatch {
//        public OperatorRenderPatch() {}
//
//        @SpireInsertPatch(
//                rloc = 13
//        )
//        public static void Insert(AbstractRoom _inst, SpriteBatch sb) {
//            OperatorsFields.operators.get(AbstractDungeon.player).render(sb);
//        }
//    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "applyStartOfTurnRelics"
    )
    public static class OperatorStartOfTurnPatch{
        public OperatorStartOfTurnPatch(){}

        public static void Postfix(AbstractPlayer _inst){
            (OperatorsFields.operators.get(_inst)).atStartOfTurn();
        }
    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "applyStartOfCombatLogic"
    )
    public static class OperatorStartOfBattlePatch{
        public OperatorStartOfBattlePatch(){}

        public static void Postfix(AbstractPlayer _inst){
            (OperatorsFields.operators.get(_inst)).usePreBattleAction();
        }
    }

    @SpirePatch(
            clz = AbstractRoom.class,
            method = "applyEndOfTurnRelics"
    )
    public static class OperatorEndOfTurnPatch{
        public OperatorEndOfTurnPatch(){}

        public static void Postfix(AbstractRoom _inst){
            (OperatorsFields.operators.get(AbstractDungeon.player)).atEndOfTurn();
        }
    }

    @SpirePatch(
            clz = BattleStartEffect.class,
            method = "update"
    )
    public static class ShowSkillBarPatch{
        public ShowSkillBarPatch(){}

        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void Insert(BattleStartEffect _inst){
            OperatorGroup tmp = OperatorsFields.operators.get(AbstractDungeon.player);
            if(tmp.operators.size() > 0){
                for(AbstractOperator o:tmp.operators){
                    o.showSkillBar();
                    o.showHealthBar();
                    o.skillBarRevivedEvent();
                    o.healthBarRevivedEvent();
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            private Locator() {
            }

            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "showHealthBar");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    @SpirePatch(
            clz = EnergyManager.class,
            method = "use"
    )
    public static class GainSkillPointPatch{
        public GainSkillPointPatch(){}

        public static void Postfix(EnergyManager _inst, int e){
            OperatorGroup tmp = OperatorGroup.OperatorsFields.operators.get(AbstractDungeon.player);
            if(tmp.operators != null){
                for(AbstractOperator o:tmp.operators){
                    if(o.currentBattleSkill !=null && o.currentBattleSkill.isSpelling) {
                        o.currentBattleSkill.onUseEnergy(e);
                    }
                    if(o.currentBattleSkill != null && !o.currentBattleSkill.isSpelling && o.currentBattleSkill.skillType == AbstractSkill.SkillType.NATURAL){
                        o.changeSkillPoints(e);
                    }
                    o.addAttackCoolDown(e);

                }
            }

            for(AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                if(m instanceof AbstractEnemy && !m.isDeadOrEscaped() &&!m.isDead) {
                    ((AbstractEnemy) m).addAttackCoolDown(e);
                }
            }
        }
    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "updateInput"
    )
    public static class AttackHelperPatch {
        public AttackHelperPatch() {}

        @SpireInsertPatch(
                locator = AttackHelperPatch.Locator.class
        )
        public static void Insert(AbstractPlayer _inst){
            if(_inst.hoveredCard.costForTurn > 0) {
                for(AbstractOperator o : OperatorGroup.GetOperators()) {
                    o.showAttackCoolDown(_inst.hoveredCard.costForTurn);
                }
                for(AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                    if(m instanceof AbstractEnemy) {
                        ((AbstractEnemy) m).showAttackCoolDown(_inst.hoveredCard.costForTurn);
                    }
                }
            }else if(_inst.hoveredCard.costForTurn == -1) {
                for(AbstractOperator o : OperatorGroup.GetOperators()) {
                    o.showAttackCoolDown(EnergyPanel.getCurrentEnergy());
                }
                for(AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                    if(m instanceof AbstractEnemy) {
                        ((AbstractEnemy) m).showAttackCoolDown(EnergyPanel.getCurrentEnergy());
                    }
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            private Locator() {
            }

            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractCard.class, "flash");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "manuallySelectCard"
    )
    public static class AttackHelperPatch2 {
        public AttackHelperPatch2() {}

        public static void Postfix(AbstractPlayer _inst, AbstractCard card) {
            if(card.costForTurn > 0) {
                for(AbstractOperator o : OperatorGroup.GetOperators()) {
                    o.showAttackCoolDown(card.costForTurn);
                }
                for(AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                    if(m instanceof AbstractEnemy) {
                        ((AbstractEnemy) m).showAttackCoolDown(_inst.hoveredCard.costForTurn);
                    }
                }
            }else if(card.costForTurn == -1) {
                for(AbstractOperator o : OperatorGroup.GetOperators()) {
                    o.showAttackCoolDown(EnergyPanel.getCurrentEnergy());
                }
                for(AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                    if(m instanceof AbstractEnemy) {
                        ((AbstractEnemy) m).showAttackCoolDown(EnergyPanel.getCurrentEnergy());
                    }
                }
            }
        }
    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "useCard"
    )
    public static class GetLastMonsterAttackPatch {
        public GetLastMonsterAttackPatch() {}

        public static void Postfix(AbstractPlayer _inst, AbstractCard c, AbstractMonster monster, int energyOnUse) {
            if(c.type == AbstractCard.CardType.ATTACK && monster != null) {
                lastMonsterAttack = monster;
                logger.info("上一个攻击的怪物：" + lastMonsterAttack.name);
            }
        }
    }
}
