package com.blogspot.babenkodmitry.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by dmitrybabenko on 4/1/16.
 */
public class AmmoManager {
    private final int AMMO_COUNT = 1000;
    private Ammo [] ammo;


    public AmmoManager() {
        //инициализируем пули
        ammo = new Ammo[AMMO_COUNT];
        for (int i = 0; i < AMMO_COUNT; i++) {
            ammo[i] = new Ammo();
        }

    }

    public void makeShot(Vector2 position, float angle) {
        for (int i = 0; i < AMMO_COUNT; i++) {
            if(!ammo[i].isAlive()) {
                ammo[i].setAmmoAlive(position, angle);
                break;
            }
        }
    }

    public void update(BaseTank[] tanks) {
        Ammo currAmmo;
        for (int i = 0; i < AMMO_COUNT; i++) {
            currAmmo = ammo[i];
            if (currAmmo.isAlive()) {
                //проверка выхода за границу
                if (currAmmo.position.x < 0 || currAmmo.position.y < 0
                        || currAmmo.position.x > Gdx.graphics.getWidth()
                        || currAmmo.position.y > Gdx.graphics.getHeight()) {
                    currAmmo.destroy();
                } else {
                    currAmmo.update();
                }

                //Проверка столкновений с танком
                for (int t = 0; t < tanks.length; t++) {
                    if (tanks[t].isAlive()) {
                        Vector2 vlen = tanks[t].getPosition().cpy().sub(currAmmo.position); // находим вектор асстояния между ними
                        float flen = vlen.len(); // считаем расстояние(длину вектора)
                        if (flen < 20) { // если расстояние между танком и пулей меньше 20 пикс
                            tanks[t].damage(currAmmo.getPower());
                            currAmmo.destroy();
                            tanks[t].impulse(vlen.cpy().nor().scl(4.0f));
                        }
                    }
                }
            }
        }
    }

    public void draw(SpriteBatch batch) {
        for (int i = 0; i < AMMO_COUNT; i++) {
            if (ammo[i].isAlive()) {
                ammo[i].draw(batch);
            }
        }
    }
}
