package com.blogspot.babenkodmitry.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by dmitrybabenko on 3/18/16.
 */
public class PlayerTank extends BaseTank {
    private static Texture myTexture;

    public PlayerTank(AmmoManager ammoManager, Vector2 position) {
        super(ammoManager);
        if(myTexture == null) {
            myTexture = new Texture("TANK.tga");
            myTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
        setTextureSize(myTexture.getWidth(), myTexture.getHeight());
        this.position = position;
        this.angle = 0;
        rotateSpeed = 4.0f;
        moveSpeed = 2.0f;
        scale = 1.0f;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(myTexture, position.x, position.y, textureWidthDiv2, textureHeightDiv2,
                textureWidth, textureHeight, 1.0f, 1.0f, angle, 0, 0, textureWidth, textureHeight, false, false);
    }

    public void update() {
        super.update();

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            makeShot();
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            rotate(RotateDirection.ROTATE_BODY_LEFT);
        } else
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            rotate(RotateDirection.ROTATE_BODY_RIGHT);
        } else {
            rotate(RotateDirection.STAND);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            move(MoveDirection.MOVE_FORWARD);
        } else
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            move(MoveDirection.MOVE_BACKWARD);
        } else {
            move(MoveDirection.STAND);
        }
    }
}
