package com.game.startscreen.menu;

import com.game.startscreen.menu.MenuManager.Menus;

import core.resource.audio.AudioManager;
import game.engine.RenderContext;

public class AboutMenu implements BaseMenu
{
	MenuManager manager;
	
	String menuBack = "Back";
	String credits = "\nDesigned by\n" +
					 "Stephen Bloomquist\n" +
					 "On the Akkle Engine";
	
	public AboutMenu(MenuManager manager)
	{
		this.manager = manager;
	}

	@Override
	public void activate()
	{
		String str = menuBack + "\n" + credits;
		
		manager.messageBox.clearMessage();
		manager.messageBox.setMessage(str);
	}

	@Override
	public void deactivate()
	{
		// TODO Auto-generated method stub
		
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
		AudioManager.play(manager.selector.selSfx);
		manager.changeMenu(Menus.MAIN);
	}

	@Override
	public void left(int num)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void right(int num)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public int numberOfMenuItems()
	{
		// TODO Auto-generated method stub
		return 1;
	}	
}