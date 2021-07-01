package ArknightsMod.Cards.Operator.Snipers.HandOperators;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static ArknightsMod.Patches.CardColorEnum.ARK_NIGHTS;

public abstract class AbstractHandOperatorCard extends CustomCard {
    public TextureAtlas atlas;
    protected Skeleton skeleton;
    public AnimationState state;
    private static SkeletonMeshRenderer sr;
    public int skillIndex = -1;

    public AbstractHandOperatorCard(String ID, String NAME, int COST, String DESCRIPTION, CardType TYPE, CardRarity RARITY, CardTarget TARGET) {
        super(ID, NAME, "ArkImg/card/EmptyCard.png", COST, DESCRIPTION, TYPE, ARK_NIGHTS, CardRarity.SPECIAL, TARGET);
        this.setDisplayRarity(RARITY);
        sr = new SkeletonMeshRenderer();
        sr.setPremultipliedAlpha(true);
    }

    protected void loadAnimation(String atlasUrl, String skeletonUrl, float scale) {
        this.atlas = new TextureAtlas(Gdx.files.internal(atlasUrl));
        SkeletonJson json = new SkeletonJson(this.atlas);
        json.setScale(Settings.renderScale / scale);
        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal(skeletonUrl));
        this.skeleton = new Skeleton(skeletonData);
        this.skeleton.setColor(Color.WHITE);
        AnimationStateData stateData = new AnimationStateData(skeletonData);
        this.state = new AnimationState(stateData);
    }

    @Override
    public abstract void use(AbstractPlayer p, AbstractMonster m);

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return this.cardPlayable(m) && this.hasEnoughEnergy();
    }

    @Override
    public abstract void upgrade();

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        if (this.atlas != null) {
            this.state.update(Gdx.graphics.getDeltaTime());
            this.state.apply(this.skeleton);
            this.skeleton.updateWorldTransform();
            this.skeleton.setPosition(this.current_x, this.current_y);
            sb.end();
            CardCrawlGame.psb.begin();
            sr.draw(CardCrawlGame.psb, this.skeleton);
            CardCrawlGame.psb.end();
            sb.begin();
            sb.setBlendFunction(770, 771);
            this.hb.render(sb);
        }
    }
}
