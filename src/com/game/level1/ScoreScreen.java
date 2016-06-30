package com.game.level1;

import core.math.vector.Vector3f;
import core.utility.Converter;
import game.component.graphics.BasicBackground;
import game.component.utility.MessageBox;
import game.component.utility.MessageBox.FONT_TYPE;
import game.engine.Game;
import game.engine.RenderContext;
import game.object.base.GameObject;
import game.scenes.Scene;

public class ScoreScreen extends Scene
{
	GameObject root = new GameObject("Root");
	int score;

	public ScoreScreen(int score)
	{
		super("Score Screen");
		this.score = score;
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
		
		GameObject scoreboard = new GameObject("Scoreboard");
		root.addChild(scoreboard);
		
		
		MessageBox scoreBox = new MessageBox(
				"Score Box", 
				new Vector3f(10, 10, 1), 
				game.getDisplay().getScreen().getWidth() - 20,
				100, 
				0, 
				1, 
				Converter.RGBToInt(255, 255, 255),
				FONT_TYPE.FONT_16x16);
		
		scoreboard.addComponent(new BasicBackground(
				"Background",
				0, 
				0, 
				game.getDisplay().getScreen().getWidth(), 
				game.getDisplay().getScreen().getHeight(),
				0,
				0,
				0,
				0));
		scoreboard.addComponent(scoreBox);
		
		root.start();
		
		scoreBox.setMessage("Your score:\n" + score);
	}

	@Override
	public void deactivate(Game game)
	{
		root.stop();
	}

}
