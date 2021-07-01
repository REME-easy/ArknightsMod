package ArknightsMod.Cards.Operator.Snipers.HandOperators;

import ArknightsMod.Actions.PlzWaitAction;
import ArknightsMod.Helper.GeneralHelper;
import ArknightsMod.Patches.OperatorTakeDamagePatch;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FireWatchHandCard extends AbstractHandOperatorCard {
    private static final String NAME_U = "Firewatch";
    private static final String NAME_L = "firewatch";
    private static final String ID = "Arknights_Hand" + NAME_U;
    private static final String ATLAS = "Operators/" + NAME_U + "/" + NAME_L + ".atlas";
    private static final String JSON = "Operators/" + NAME_U + "/" + NAME_L + ".json";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final int COST = 1;
    public FireWatchHandCard(int skillIndex) {
        super(ID, NAME, COST, DESCRIPTION,
                CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        loadAnimation(ATLAS, JSON, 2.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Start", false);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.state.addAnimation(0, "Idle", true, 0.0F);
        this.selfRetain = true;
        this.skillIndex = skillIndex;
        this.damage = this.baseDamage = 14;
        this.magicNumber = this.baseMagicNumber = 6;
        if(this.skillIndex >= 0) {
            this.block = this.baseBlock = 7;
            if(skillIndex == 1) {
                costForTurn = cost = 2;
            }
            this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[skillIndex];
            this.initializeDescription();
        }

    }

    public FireWatchHandCard() {
        this(-1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new PlzWaitAction(0.5F));
        addToBot(new DamageAction(m, new DamageInfo(p, damage + (m.intent.name().contains("ATTACK") ? 0 : magicNumber)), AttackEffect.LIGHTNING));
        if(skillIndex == 0) {
            if(OperatorTakeDamagePatch.getTarget())
                addToBot(new GainBlockAction(OperatorTakeDamagePatch.target, block));
            else
                addToBot(new GainBlockAction(p, block));
            state.setAnimation(0, "Attack", false);
            state.addAnimation(0, "Idle", false, 0.0F);
            CardCrawlGame.sound.play( NAME_L + "_skill_1");
        }else if(skillIndex == 1) {
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    for(AbstractMonster m1 : GeneralHelper.monsters()) {
                        addToBot(new DamageAction(m1, new DamageInfo(p, m.lastDamageTaken / 2), AttackEffect.LIGHTNING));
                    }
                    this.isDone = true;
                }
            });
            state.setAnimation(0, "Skill", false);
            state.addAnimation(0, "Idle", false, 0.0F);
            CardCrawlGame.sound.play( NAME_L + "_skill_2");
        }else {
            state.setAnimation(0, "Attack", false);
            state.addAnimation(0, "Idle", false, 0.0F);
        }

    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);
            this.upgradeBlock(2);
        }
    }


}
