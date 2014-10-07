/*******************************************************************************
 * Copyright 2014 Rafael Garcia Moreno.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.bladecoder.engine.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.bladecoder.engine.model.World;
import com.bladecoder.engine.ui.UI.Screens;
import com.bladecoder.engine.util.DPIUtils;

public class MenuScreen implements BladeScreen {

	public static final String BACK_COMMAND = "back";
	public static final String QUIT_COMMAND = "quit";
	public static final String RELOAD_COMMAND = "reload";
	public static final String HELP_COMMAND = "help";
	public static final String CREDITS_COMMAND = "credits";

	private static final float MARGIN = DPIUtils.UI_SPACE;

	private UI ui;

	private Stage stage;

	private float buttonSize;

	public MenuScreen() {

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(delta);
		stage.draw();

		ui.getBatch().setProjectionMatrix(stage.getViewport().getCamera().combined);
		ui.getBatch().begin();
		ui.getPointer().draw(ui.getBatch(), stage.getViewport());
		ui.getBatch().end();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void dispose() {
		stage.dispose();
		stage = null;
	}

	@Override
	public void show() {
		// int wWidth =
		// EngineAssetManager.getInstance().getResolution().portraitWidth;
		// int wHeight =
		// EngineAssetManager.getInstance().getResolution().portraitHeight;
		//
		// stage = new Stage(new ExtendViewport(wWidth, wHeight/2));

		stage = new Stage(new ScreenViewport());

		buttonSize = DPIUtils.getPrefButtonSize() * 2f;

		Table table = new Table();
		table.setFillParent(true);
		table.center();

		table.addListener(new InputListener() {
			@Override
			public boolean keyUp(InputEvent event, int keycode) {
				if (keycode == Input.Keys.ESCAPE || keycode == Input.Keys.BACK) {
					if (World.getInstance().getCurrentScene() == null)
						World.getInstance().load();

					ui.setCurrentScreen(Screens.SCENE_SCREEN);
				}
				
				return true;
			}
		});

		stage.setKeyboardFocus(table);

		ImageButton back = new ImageButton(new TextureRegionDrawable(ui.getUIAtlas().findRegion(BACK_COMMAND)));

		back.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				if (World.getInstance().getCurrentScene() == null)
					World.getInstance().load();

				ui.setCurrentScreen(Screens.SCENE_SCREEN);
			}
		});

		table.add(back).pad(MARGIN);
		back.getImageCell().minSize(buttonSize, buttonSize);
		back.getImageCell().maxSize(buttonSize, buttonSize);

		ImageButton reload = new ImageButton(new TextureRegionDrawable(ui.getUIAtlas().findRegion(RELOAD_COMMAND)));
		reload.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				World.getInstance().newGame();
				ui.setCurrentScreen(Screens.SCENE_SCREEN);
			}
		});

		table.add(reload).pad(MARGIN);
		reload.getImageCell().minSize(buttonSize, buttonSize);
		reload.getImageCell().maxSize(buttonSize, buttonSize);

		ImageButton help = new ImageButton(new TextureRegionDrawable(ui.getUIAtlas().findRegion(HELP_COMMAND)));
		help.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				ui.setCurrentScreen(Screens.HELP_SCREEN);
			}
		});

		table.add(help).pad(MARGIN);
		help.getImageCell().minSize(buttonSize, buttonSize);
		help.getImageCell().maxSize(buttonSize, buttonSize);

		ImageButton credits = new ImageButton(new TextureRegionDrawable(ui.getUIAtlas().findRegion(CREDITS_COMMAND)));
		credits.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				ui.setCurrentScreen(Screens.CREDIT_SCREEN);
			}
		});

		table.add(credits).pad(MARGIN);
		credits.getImageCell().minSize(buttonSize, buttonSize);
		credits.getImageCell().maxSize(buttonSize, buttonSize);

		ImageButton quit = new ImageButton(new TextureRegionDrawable(ui.getUIAtlas().findRegion(QUIT_COMMAND)));
		quit.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});

		table.add(quit).pad(MARGIN);
		quit.getImageCell().minSize(buttonSize, buttonSize);
		quit.getImageCell().maxSize(buttonSize, buttonSize);
		table.pack();

		stage.addActor(table);

		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void setUI(UI ui) {
		this.ui = ui;
	}
}
