package com.game.level1;

import core.math.vector.Vector3f;
import game.engine.Game;
import game.engine.RenderContext;
import game.object.base.GameObject;
import game.scenes.Scene;

public class FirstLevel extends Scene
{
	GameObject root = new GameObject("Root");

	public FirstLevel()
	{
		super("First Level");
		// TODO Auto-generated constructor stub
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
		
		GameObject factoryObj = new GameObject("Factory");
		factoryObj.local.pos = new Vector3f(0, 0, 0);
		
		root.addChild(factoryObj);
		
		factoryObj.addComponent(new Factory());

		root.addComponent(new Control("Control", factoryObj.findComponent(Factory.class)));
		
		root.start();
	}

	@Override
	public void deactivate(Game game)
	{
		root.stop();
	}

}
