package com.game.startscreen.menu;

import com.game.startscreen.menu.MenuManager.Menus;

import core.resource.audio.AudioManager;
import game.engine.RenderContext;

public class OptionsMenu implements BaseMenu
{
	MenuManager manager;
	int volMax = 6, volMin = -20;
	int sfxVolume = 5;
	int musicVolume = 5;
	
	String menuBack = "Back";
	String menuSfxVol = "[";
	String menuMusicVol = "[";
	
	public OptionsMenu(MenuManager manager)
	{
		this.manager = manager;
	}

	@Override
	public void activate()
	{
		AudioManager.clipVolume = ((sfxVolume / 10.0f) * 36.0f) - 30;
		AudioManager.streamVolume = ((musicVolume / 10.0f) * 36.0f) - 30;
		buildMessage();
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
		if (num == 0)
		{
			AudioManager.play(manager.selector.selSfx);
			manager.changeMenu(Menus.MAIN);
		}
	}
	
	private void buildMessage()
	{
		String strSfx = menuSfxVol;
		String strMusic = menuMusicVol;
		
		for (int i = 0; i < 10; i++)
		{
			if (i < sfxVolume)
			{
				strSfx += "=";
			}
			else
			{
				strSfx += " ";
			}
			
			if (i < musicVolume)
			{
				strMusic += "=";
			}
			else
			{
				strMusic += " ";
			}
		}
		strSfx += "] Sfx Volume";
		strMusic += "] Music Volume";
		
		String str = menuBack + "\n" + strSfx + "\n" + strMusic;
		
		manager.messageBox.setMessage(str);
	}
	
	private void changeSfxVol(int amt)
	{
		sfxVolume += amt;
		
		if (sfxVolume < 0)
			sfxVolume = 0;
		else if (sfxVolume > 10)
			sfxVolume = 10;
		
		AudioManager.clipVolume = ((sfxVolume / 10.0f) * 50.0f) - 44;
		
		buildMessage();
	}
	
	private void changeMusicVol(int amt)
	{
		musicVolume += amt;
		
		if (musicVolume < 0)
			musicVolume = 0;
		else if (musicVolume > 10)
			musicVolume = 10;
		
		AudioManager.streamVolume = ((musicVolume / 10.0f) * 36.0f) - 30;
		
		buildMessage();
	}

	@Override
	public void left(int num)
	{
		if (num == 1)
		{
			changeSfxVol(-1);
			AudioManager.play(manager.selector.moveSfx);
		}
		else if (num == 2)
		{
			changeMusicVol(-1);
			AudioManager.play(manager.selector.moveSfx);
		}
	}

	@Override
	public void right(int num)
	{
		if (num == 1)
		{
			changeSfxVol(1);
			AudioManager.play(manager.selector.moveSfx);
		}
		else if (num == 2)
		{
			changeMusicVol(1);
			AudioManager.play(manager.selector.moveSfx);
		}
	}

	@Override
	public int numberOfMenuItems()
	{
		// TODO Auto-generated method stub
		return 3;
	}	
}