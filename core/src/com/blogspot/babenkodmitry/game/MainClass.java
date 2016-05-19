package com.blogspot.babenkodmitry.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class MainClass extends ApplicationAdapter {
    static Random rand = new Random();

	SpriteBatch batch;
    private Texture texGround;

	final int TANKS_COUNT = 8;
	BaseTank[] tanks = new BaseTank[TANKS_COUNT];

    AmmoManager ammoManager;

	@Override
	public void create () {
        ammoManager = new AmmoManager();
		batch = new SpriteBatch();
        texGround = new Texture("ground.png");
		for (int i = 0; i < TANKS_COUNT-1; i++) {
			tanks[i] = new BotTank(ammoManager, new Vector2(rand.nextInt(800), rand.nextInt(600)));
		}
        tanks[TANKS_COUNT-1] = new PlayerTank(ammoManager, new Vector2(rand.nextInt(800), rand.nextInt(600)));
	}

	@Override
	public void render() {
		update();
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

        //bg
        batch.draw(texGround, 0, 0);

        //tanks
		for (int i = 0; i < TANKS_COUNT; i++) {
            if (tanks[i].isAlive())
			    tanks[i].draw(batch);
		}

        //ammo
        ammoManager.draw(batch);

		batch.end();
	}

	public void update() {
        //tanks
		for (int i = 0; i < TANKS_COUNT; i++) {
            if (tanks[i].isAlive())
                tanks[i].update();
		}

        //Проверка столкновений танков
        for (int i = 0; i < TANKS_COUNT; i++) {
            {
                if (tanks[i].isAlive()) {
                    for (int j = i + 1; j < TANKS_COUNT; j++) { // бежим по возможным танкам
                        if (tanks[j].isAlive()) {
                            Vector2 vlen = tanks[i].getPosition().cpy().sub(tanks[j].getPosition()); // находим вектор асстояния между ними
                            float flen = vlen.len(); // считаем расстояние(длину вектора)
                            if (flen < 32) { // если расстояние между танками меньше 32 пикселей
                                tanks[i].damage(1); // повреждаем танк 1
                                tanks[j].damage(1); // повреждаем танк 2
                                tanks[i].impulse(vlen.cpy().nor().scl(4.0f));  // придаем танку 1 импульс, и чуть откидываем
                                tanks[j].impulse(vlen.cpy().nor().scl(-4.0f)); // придаем танку 2 импульс, и чуть откидываем
                            }
                        }
                    }
                }
            }
        }

        //ammo
        ammoManager.update(tanks);
	}
}
