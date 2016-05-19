package com.blogspot.babenkodmitry.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by dmitrybabenko on 3/18/16.
 */
enum BotAction {
    MOVE_FORWARD,
    MOVE_BACKWARD,
    ROTATE_BODY_LEFT,
    ROTATE_BODY_RIGHT,
    SHOT,
    STAND
}

public class BotTank extends BaseTank {
    private static Texture myTexture;
    private BotAction currentAction;
    private float timeToAction;

    public BotTank(AmmoManager ammoManager, Vector2 position) {
        super(ammoManager);
        if(myTexture == null) {
            myTexture = new Texture("TANK2.tga");
            myTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
        currentAction = BotAction.STAND;
        setTextureSize(myTexture.getWidth(), myTexture.getHeight());
        this.position = position;
        this.angle = 270;
        rotateSpeed = 4.0f;
        moveSpeed = 2f;
        scale = 1.0f;//0.7f + (MainClass.rand.nextFloat()*0.3f);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(myTexture, position.x, position.y, textureWidthDiv2, textureHeightDiv2,
                textureWidth, textureHeight, scale, scale, angle, 0, 0, textureWidth, textureHeight, false, false);
    }

    public void update() {
        super.update();

        if (timeToAction > 0.0f) {
            timeToAction -= Gdx.graphics.getDeltaTime();
        } else {
            currentAction = BotAction.values()[MainClass.rand.nextInt(BotAction.values().length)];

            switch (currentAction) {
                case MOVE_FORWARD:
                    timeToAction = (MainClass.rand.nextFloat()*3+2);
                    move(MoveDirection.MOVE_FORWARD);
                    rotate(RotateDirection.STAND);
                    break;

                case MOVE_BACKWARD:
                    timeToAction = (MainClass.rand.nextFloat()*0.2f+0.2f);
                    move(MoveDirection.MOVE_BACKWARD);
                    rotate(RotateDirection.STAND);
                    break;

                case ROTATE_BODY_RIGHT:
                    timeToAction = (MainClass.rand.nextFloat()*0.5f+0.1f);
                    rotate(RotateDirection.ROTATE_BODY_LEFT);
                    break;

                case ROTATE_BODY_LEFT:
                    timeToAction = (MainClass.rand.nextFloat()*0.5f+0.1f);
                    rotate(RotateDirection.ROTATE_BODY_RIGHT);
                    break;

                case SHOT:
                    makeShot();
                    break;

                case STAND:
                default:
                    timeToAction = (MainClass.rand.nextFloat()*1.0f+0.2f);
                    move(MoveDirection.STAND);
                    rotate(RotateDirection.STAND);
                    break;
            }
        }
    }
}
