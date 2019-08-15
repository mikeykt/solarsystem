package com.myfirstgdx.magicsolarsystem;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.TimeUtils;

public class main extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Body sun;
	long lastTime, time;
	Stage stage;
	TextButton button;
	TextButton.TextButtonStyle textButtonStyle;
	BitmapFont font;
	Skin skin;
	TextureAtlas buttonAtlas;
	boolean paused = false;

	public long randomLong(){
		return 1000000000 + (long) (Math.random() * (2147483647 - 1000000000));
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		sun = new Body(new Vector2(Constants.centerX, Constants.centerY), "sun", 0);
		Body mercury = new Body("mercury", 100, sun, randomLong());
		Body earth = new Body("earth", 200, sun, randomLong());
		Body venus = new Body("venus", 42, earth, randomLong());



		lastTime = TimeUtils.millis();
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		font = new BitmapFont();
		skin = new Skin();
		buttonAtlas = new TextureAtlas(Constants.uiPath + "button.atlas");
		skin.addRegions(buttonAtlas);
		textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.font = font;
		textButtonStyle.up = skin.getDrawable("button_up");
		textButtonStyle.down = skin.getDrawable("button_down");
		button = new TextButton("", textButtonStyle);
		button.addListener( new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				paused=!paused;
			}
		});
		stage.addActor(button);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(64/255f, 44/255f, 22/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		long delta = TimeUtils.nanoTime() - lastTime;
		if (!paused)
		{
			time += delta;
			sun.draw(batch, time);
		}
		else{
			sun.draw(batch,time);
		}
		lastTime = TimeUtils.nanoTime();
		batch.end();
		stage.draw();
	}

	@Override
	public void dispose () {
		batch.dispose();
		sun.dispose();
	}
}
