package com.base;

import com.game.startscreen.StartScene;

import game.engine.Game;

public class Main
{
	public static void main(String[] args) 
	{
		Game game = new Game(480, 320, 2);
		
		game.setActiveScene(new StartScene());
		game.initialize();
	}
}
