package com.blogspot.babenkodmitry.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by dmitrybabenko on 3/18/16.
 */

enum MoveDirection {
    STAND,
    MOVE_FORWARD,
    MOVE_BACKWARD
}
enum RotateDirection {
    STAND,
    ROTATE_BODY_LEFT,
    ROTATE_BODY_RIGHT
}

public abstract class BaseTank {
    protected final int TIME_TO_SHOT = 40;
    private int currTimeToShot;

    protected final float MAX_SPEED = 2.0f;
    protected final float SPEED_UP_STEP = 0.1f;
    protected final float SPEED_DOWN_STEP = 0.2f;
    protected float currentSpeed;

    private MoveDirection moveDirection;
    private RotateDirection rotateDirection;

    protected Vector2 position;
    protected Vector2 moveVector;
    protected float angle;
    protected float moveSpeed;
    protected float rotateSpeed;
    protected float scale;

    protected int textureWidth;
    protected int textureHeight;
    protected float textureWidthDiv2;
    protected float textureHeightDiv2;

    protected AmmoManager ammoManager;
    protected float ammoDistance;
    private boolean _isAlive;
    protected int hp;

    public BaseTank(AmmoManager ammoManager) {
        currentSpeed = 0.0f;
        moveDirection = MoveDirection.STAND;
        rotateDirection = RotateDirection.STAND;
        this.ammoManager = ammoManager;
        moveVector = new Vector2();
        _isAlive = true;
        hp = 100;
        currTimeToShot = 0;
    }

    public void setTextureSize(int width, int height) {
        this.textureWidth = width;
        this.textureHeight = height;
        this.textureWidthDiv2 = textureWidth * 0.5f;
        this.textureHeightDiv2 = textureHeight * 0.5f;
        ammoDistance = textureWidthDiv2 + 10.0f;
    }

    public void update() {
        if(position.x > Gdx.graphics.getWidth()) position.x = -textureWidth;
        if(position.x < -textureWidth) position.x = Gdx.graphics.getWidth();

        if(position.y > Gdx.graphics.getHeight()) position.y = -textureHeight;
        if(position.y < -textureHeight) position.y = Gdx.graphics.getHeight();

        //rotate
        if (rotateDirection != RotateDirection.STAND) {
            if(rotateDirection == RotateDirection.ROTATE_BODY_LEFT)
                angle += rotateSpeed;
            else
                angle -= rotateSpeed;
        }

        //move
        if (moveDirection != MoveDirection.STAND) {
            if (moveDirection == MoveDirection.MOVE_FORWARD) {
                if(currentSpeed < MAX_SPEED)
                    currentSpeed += SPEED_UP_STEP;
            } else {
                if(currentSpeed > -MAX_SPEED)
                    currentSpeed -= SPEED_UP_STEP;
            }
        } else { //снижаем скорость
            if (currentSpeed > 0.0f) {
                if (currentSpeed > SPEED_DOWN_STEP) currentSpeed -= SPEED_DOWN_STEP;
                else currentSpeed = 0.0f;
            } else {
                if (currentSpeed < -SPEED_DOWN_STEP) currentSpeed += SPEED_DOWN_STEP;
                else currentSpeed = 0.0f;
            }
        }

        if (currentSpeed != 0.0f || rotateDirection != RotateDirection.STAND) {
            moveVector.set(currentSpeed, 0.0f);
            moveVector.rotate(angle);
            moveVector.scl(moveSpeed);//масштабирование в зависимости от скорости
            position.add(moveVector);
        }

        //time to shot
        if (currTimeToShot > 0) {
            currTimeToShot--;
        }
    }
    public abstract void draw(SpriteBatch batch);

    public void rotate(RotateDirection direction) {
        rotateDirection = direction;
    }

    public void move(MoveDirection direction) {
        moveDirection = direction;
    }

    public void makeShot() {
        if (currTimeToShot == 0) {
            Vector2 ammoPos = position.cpy();
            ammoPos.add(textureHeightDiv2 / 2, textureHeightDiv2);
            ammoPos.add(new Vector2(ammoDistance, 0.0f).rotate(angle));//вынесли снаряд за танк
            ammoManager.makeShot(ammoPos, angle);
            currTimeToShot = TIME_TO_SHOT;
        }
    }

    public boolean isAlive() {
        return _isAlive;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void damage(int value) {
        hp -= value; // Из здоровья танка вычитается урон
        if (hp <= 0) { // Если здоровье меньше 0
            destroy(); // уничтожаем танк
        }
    }

    public void destroy() {
        hp = 0;
        _isAlive = false;
    }

    public void impulse(Vector2 imp) {
        position.add(imp);
    }
}
