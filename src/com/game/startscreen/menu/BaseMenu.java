package com.game.startscreen.menu;

import game.engine.RenderContext;

public interface BaseMenu
{
	public void activate();
	public void deactivate();
	
	public void update(float deltaTime);
	public void draw(RenderContext renderContext);
	
	public void select(int num);
	public void left(int num);
	public void right(int num);
	
	public int numberOfMenuItems();
}
