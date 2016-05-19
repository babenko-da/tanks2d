package com.blogspot.babenkodmitry.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by dmitrybabenko on 4/1/16.
 */
public class Ammo {
    //graphics
    private static Texture texture;
    private static int textureWidth;
    private static float textureWidthDiv2;
    private static int textureHeight;
    private static float textureHeightDiv2;

    //physics
    Vector2 position;
    Vector2 moveVector;
    float angle;
    float moveSpeed = 8.0f;

    //logic
    private boolean isAlive;
    private int power;

    public Ammo() {
        if (texture == null) {
            texture = new Texture("Ammo.png");
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

            textureWidth = texture.getWidth();
            textureWidthDiv2 = textureWidth * 0.5f;

            textureHeight = texture.getHeight();
            textureHeightDiv2 = textureHeight * 0.5f;
        }
        moveVector = new Vector2();
        power = 70;
        this.isAlive = false;
    }

    public Ammo(Vector2 position, float angle, boolean isAlive) {
        this();
        this.position = position;
        this.angle = angle;
        this.isAlive = isAlive;
    }

    public void setAmmoAlive(Vector2 position, float angle) {
        this.position = position;
        this.position.add(0.0f, -textureHeightDiv2);//сдвигаем снаряд на середину танка
        this.angle = angle;

        moveVector.set(moveSpeed, 0.0f);
        moveVector.rotate(angle);
//        moveVector.scl(moveSpeed, 0.0f);

        this.isAlive = true;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void destroy() {
        this.isAlive = false;
    }

    public void update() {
//        if (isAlive) {
            position.add(moveVector);
//        }
    }

    public void draw(SpriteBatch batch) {
//        if (isAlive) {
            batch.draw(texture, position.x, position.y, textureWidthDiv2, textureHeightDiv2,
                    textureWidth, textureHeight, 1.0f, 1.0f, angle, 0, 0, textureWidth, textureHeight, false, false);
//        }
    }

    public int getPower() {
        return power;
    }
}
