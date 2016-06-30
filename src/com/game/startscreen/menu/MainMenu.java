package com.game.startscreen.menu;

import com.game.level1.FirstLevel;
import com.game.startscreen.menu.MenuManager.Menus;

import core.resource.audio.AudioManager;
import game.engine.RenderContext;
import game.scenes.test.TestScene3;

public class MainMenu implements BaseMenu
{
	MenuManager manager;
	
	String menuStr = 
			"New Game\n" +
			"Load Game\n" +
			"Options\n" +
			"About\n" +
			"Quit";
	
	public MainMenu(MenuManager manager)
	{
		this.manager = manager;
	}

	@Override
	public void activate()
	{
		manager.messageBox.setMessage(menuStr);
	}

	@Override
	public void deactivate()
	{
		manager.messageBox.clearMessage();
	}

	@Override
	public void update(float deltaTime)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(RenderContext renderContext)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void select(int num)
	{
		switch (num)
		{
		case 0:
			manager.getGameRef().setNextScene(new FirstLevel());
			break;
		case 1:
			AudioManager.play(manager.selector.selSfx);
			//manager.changeMenu(Menus.LOAD);
			manager.getGameRef().setNextScene(new TestScene3());
			break;
		case 2:
			AudioManager.play(manager.selector.selSfx);
			manager.changeMenu(Menus.OPTIONS);
			break;
		case 3:
			AudioManager.play(manager.selector.selSfx);
			manager.changeMenu(Menus.ABOUT);
			break;
		case 4:
			manager.getGameRef().stop();
			break;
		}
	}

	@Override
	public void left(int num)
	{
	}

	@Override
	public void right(int num)
	{
	}

	@Override
	public int numberOfMenuItems()
	{
		return 5;
	}	
}
