package com.game.startscreen;

import com.game.startscreen.menu.MenuManager;

import core.resource.audio.AudioManager;
import game.engine.Game;
import game.engine.RenderContext;
import game.object.base.GameObject;
import game.scenes.Scene;

public class StartScene extends Scene
{
	GameObject root;
	
	public StartScene()
	{
		super("Start Scene");
		root = new GameObject("Root");
	}

	@Override
	public void update(float deltaTime)
	{
		root.update(deltaTime);
	}

	@Override
	public void render(RenderContext renderContext)
	{	
		root.draw(renderContext);
	}

	@Override
	public void activate(Game game)
	{
		root.setGameRef(game);
		
		GameObject menu = new GameObject("Menu");
		root.addChild(menu);
		
		menu.addComponent(new Background("./res/bitmaps/", "SomeGame.bmp"));
		menu.addComponent(new MenuManager());
		
		AudioManager.clipVolume = ((5 / 10.0f) * 40.0f) - 34;
		AudioManager.streamVolume = ((5 / 10.0f) * 40.0f) - 34;

		root.start();
	}

	@Override
	public void deactivate(Game game)
	{	
		root.stop();
	}
}
