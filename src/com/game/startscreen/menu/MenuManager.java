package com.game.startscreen.menu;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

import core.event.Event;
import core.event.EventManager;
import core.input.InputEvent;
import core.math.vector.Vector2f;
import core.math.vector.Vector3f;
import core.resource.audio.AudioClip;
import core.resource.audio.AudioManager;
import core.resource.audio.AudioStream;
import core.resource.graphics.Bitmap;
import core.utility.Converter;
import game.component.base.GameComponent;
import game.component.utility.MessageBox;
import game.component.utility.MessageBox.FONT_TYPE;
import game.component.utility.RequireComponent;
import game.engine.RenderContext;

public class MenuManager extends GameComponent implements Event.Listener
{
	protected static class Selector
	{
		public ArrayList<Bitmap> sprite = new ArrayList<Bitmap>();
		public AudioClip moveSfx;
		public AudioClip selSfx;
		
		public Vector2f startPos = new Vector2f(0, 0);
		public Vector2f curPos = new Vector2f(0, 0);
		
		public int menuItem = 0;
		
		public int animDir = 1;
		public int animStep = 0;
		
		public float animRate = 0.075f;
		public float elapsedTime = 0;
		
		public Selector(ArrayList<Bitmap> sprite, AudioClip moveSfx, AudioClip selSfx)
		{
			this.sprite = sprite;
			this.moveSfx = moveSfx;
			this.selSfx = selSfx;
			
			for (int i = 0; i < this.sprite.size(); i++)
			{
				sprite.get(i).useColorMask(Converter.RGBToInt(255, 0, 255));
			}
		}
	}
	
	enum Menus
	{
		MAIN,
		LOAD,
		OPTIONS,
		ABOUT
	}
	
	AudioStream music;
	
	HashMap<Menus, BaseMenu> menus;
	protected MessageBox messageBox;
	RequireComponent reqComp;
	
	BaseMenu activeMenu;
	
	Selector selector;

	public MenuManager()
	{
		super("Menu Manager");
		
		ArrayList<Bitmap> selImg = new ArrayList<Bitmap>();
		selImg.add(new Bitmap("./res/bitmaps/", "MenuSelectorA.bmp"));
		selImg.add(new Bitmap("./res/bitmaps/", "MenuSelectorB.bmp"));
		selImg.add(new Bitmap("./res/bitmaps/", "MenuSelectorC.bmp"));
		selImg.add(new Bitmap("./res/bitmaps/", "MenuSelectorD.bmp"));
		selImg.add(new Bitmap("./res/bitmaps/", "MenuSelectorE.bmp"));
		selImg.add(new Bitmap("./res/bitmaps/", "MenuSelectorF.bmp"));
		selImg.add(new Bitmap("./res/bitmaps/", "MenuSelectorG.bmp"));
		
		selector = new Selector(
				selImg, 
				new AudioClip("./res/sfx/", "MenuMove.wav"),
				new AudioClip("./res/sfx/", "MenuSelect.wav"));
		
		menus = new HashMap<Menus, BaseMenu>();
	
		menus.put(Menus.MAIN, new MainMenu(this));
		menus.put(Menus.LOAD, new LoadMenu(this));
		menus.put(Menus.OPTIONS, new OptionsMenu(this));
		menus.put(Menus.ABOUT, new AboutMenu(this));
		
		activeMenu = menus.get(Menus.MAIN);
		
		messageBox = new MessageBox(
				"Menu box",
				new Vector3f(30, 180, 10),
				400,
				400,
				0,
				1,
				Converter.RGBToInt(200, 255, 255),
				FONT_TYPE.FONT_16x16);
	}

	@Override
	public void onAdd()
	{
		reqComp = new RequireComponent();
		
		parent.addComponent(messageBox);
		
		reqComp.add(parent, MessageBox.class);
		
		EventManager.register(this, InputEvent.Key.pressed);
	}

	@Override
	public void onRemove()
	{
		reqComp.release();
		
		EventManager.unregister(this, InputEvent.Key.pressed);
	}

	@Override
	public void start()
	{
		selector.startPos.x = 10;
		selector.startPos.y = 180;
		selector.curPos.x = selector.startPos.x;
		selector.curPos.y = selector.startPos.y;
		
		//AudioManager.play(music);
		activeMenu.activate();
	}

	@Override
	public void stop()
	{
	}

	@Override
	public void update(float deltaTime)
	{	
		selector.elapsedTime += deltaTime;
		
		if (selector.elapsedTime >= selector.animRate)
		{
			selector.elapsedTime = 0;
			
			selector.animStep += selector.animDir;
			
			if (selector.animStep + selector.animDir >= selector.sprite.size() || selector.animStep + selector.animDir < 0)
				selector.animDir = -selector.animDir;
		}
	}

	@Override
	public void draw(RenderContext renderContext)
	{
		renderContext.draw(selector.sprite.get(selector.animStep), Converter.RGBToInt(200, 255, 255), new Vector3f(selector.curPos.x, selector.curPos.y, 10));
	}
	
	protected void changeMenu(Menus menu)
	{
		activeMenu.deactivate();
		activeMenu = menus.get(menu);
		messageBox.clearMessage();
		resetSelector();
		activeMenu.activate();
	}
	
	private void select()
	{
		activeMenu.select(selector.menuItem);
	}
	
	private void left()
	{
		activeMenu.left(selector.menuItem);
	}
	
	private void right()
	{
		activeMenu.right(selector.menuItem);
	}
	
	private void resetSelector()
	{
		selector.menuItem = 0;
		
		selector.curPos.y = selector.startPos.y + messageBox.getYStep() * selector.menuItem;
	}
	
	private void moveSelector(int dir)
	{
		AudioManager.play(selector.moveSfx);
		
		selector.menuItem += dir;
		
		if (selector.menuItem >= activeMenu.numberOfMenuItems())
			selector.menuItem = 0;
		else if (selector.menuItem < 0)
			selector.menuItem = activeMenu.numberOfMenuItems() - 1;
		
		selector.curPos.y = selector.startPos.y + messageBox.getYStep() * selector.menuItem;
	}

	@Override
	public void handleEvent(Event event)
	{
		InputEvent.Key.Data data = event.getData();
		
		if (data.e.getKeyCode() == KeyEvent.VK_UP)
		{
			moveSelector(-1);
		}
		else if (data.e.getKeyCode() == KeyEvent.VK_W)
		{
			moveSelector(-1);
		}
		else if (data.e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			moveSelector(1);
		}
		else if (data.e.getKeyCode() == KeyEvent.VK_S)
		{
			moveSelector(1);
		}
		else if (data.e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			left();
		}
		else if (data.e.getKeyCode() == KeyEvent.VK_A)
		{
			left();
		}
		else if (data.e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			right();
		}
		else if (data.e.getKeyCode() == KeyEvent.VK_D)
		{
			right();
		}
		else if (data.e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			select();
		}
		else if (data.e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			select();
		}
	}

	@Override
	public String getName()
	{
		return name;
	}

}
