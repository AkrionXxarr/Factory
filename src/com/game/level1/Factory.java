package com.game.level1;

import core.input.InputEvent;
import core.math.vector.Vector2f;
import core.math.vector.Vector3f;
import core.resource.graphics.Bitmap;
import core.utility.Converter;
import core.event.Event;
import core.event.EventManager;
import game.component.base.GameComponent;
import game.component.utility.MessageBox;
import game.component.utility.MessageBox.FONT_TYPE;
import game.engine.RenderContext;
import game.object.base.GameObject;

public class Factory extends GameComponent implements Event.Listener
{
	Vector2f startPos, playPos, endPos;
	
	GameObject inbound, play, outbound;
	Box box;
	
	MessageBox scoreBox;
	MessageBox timerBox;
	
	int timeInSeconds = 30;
	float elapsedTime = 0;
	
	Bitmap background;
	Bitmap factInboundFront, factInboundBack;
	Bitmap factOutbound;
	Bitmap factSide;
	Bitmap factMiddle;
	
	Vector3f factInboundFrontOff;
	Vector3f factInboundBackOff;
	Vector3f factOutboundOff;
	Vector3f factFrontSideOff;
	Vector3f factBackSideOff;
	Vector3f factMiddleOff;
	
	int totalScore = 0;

	public Factory()
	{
		super("Factory");
		
		//startPos = new Vector2f(-70, 20);
		//endPos = new Vector2f(450, 60);
		
		startPos = new Vector2f(-70, 40);
		endPos = new Vector2f(410, 81);
		playPos = startPos.lerp(endPos, 0.5f);
		
		scoreBox = new MessageBox("Score Box", new Vector3f(5, 5, 100), 200, 100, 0, 1, Converter.RGBToInt(0, 255, 0), FONT_TYPE.FONT_8x8);
		timerBox = new MessageBox("Time Box", new Vector3f(5, 20, 100), 200, 100, 0, 1, Converter.RGBToInt(255, 128, 0), FONT_TYPE.FONT_8x8);
		
		background = new Bitmap("./res/bitmaps/factory", "Background.bmp");
		
		factInboundFront = new Bitmap("./res/bitmaps/factory", "InboundFront.bmp");
		factInboundBack = new Bitmap("./res/bitmaps/factory", "InboundBack.bmp");
		
		factOutbound = new Bitmap("./res/bitmaps/factory", "Outbound.bmp");
		
		factSide = new Bitmap("./res/bitmaps/factory", "ConveyerSide.bmp");
		factMiddle = new Bitmap("./res/bitmaps/factory", "ConveyerMiddle.bmp");
		
		factInboundFront.useColorMask(Converter.RGBToInt(255, 0, 255));
		factInboundBack.useColorMask(Converter.RGBToInt(255, 0, 255));
		factOutbound.useColorMask(Converter.RGBToInt(255, 0, 255));
		factSide.useColorMask(Converter.RGBToInt(255, 0, 255));
		factMiddle.useColorMask(Converter.RGBToInt(255, 0, 255));
	}

	@Override
	public void onAdd()
	{
		EventManager.register(this, InputEvent.Key.released);
	}

	@Override
	public void onRemove()
	{
		EventManager.unregister(this, InputEvent.Key.released);
	}

	@Override
	public void start()
	{
		parent.addComponent(scoreBox);
		parent.addComponent(timerBox);
		
		factInboundFrontOff = new Vector3f(-2, -2, 25);
		factInboundBackOff = new Vector3f(-2, 2, 7);
		factOutboundOff = new Vector3f(399, 30, 25);
		factFrontSideOff = new Vector3f(-2, 142, 25);
		factBackSideOff = new Vector3f(-2, 105, 8);
		factMiddleOff = new Vector3f(-2, 112, 9);
		
		scoreBox.setMessage("Score: " + Integer.toString(totalScore));
		timerBox.setMessage("Time: " + Integer.toString(timeInSeconds));
		createBox();
	}

	@Override
	public void stop()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float deltaTime)
	{
		elapsedTime += deltaTime;
		
		if (elapsedTime >= 1.0f)
		{
			elapsedTime -= 1;
			
			if (timeInSeconds > 0)
			{
				timeInSeconds--;
				
				timerBox.clearMessage();
				timerBox.setMessage("Time: " + Integer.toString(timeInSeconds));
			}
		}
		
		if (inbound != null)
		{
			inbound.local.pos = inbound.local.pos.add(playPos.sub(startPos).normalized().mul(deltaTime * 450.0f));
			
			//System.out.println(inbound.local.pos.toString());
			if (inbound.local.pos.x >= playPos.x)
			{
				//inbound.local.pos.x = playPos.x;
				//inbound.local.pos.y = playPos.y;
				//System.out.println("!!!" + inbound.local.pos.toString());
				play = inbound;
				inbound = null;
			}
		}
		
		if (outbound != null)
		{
			outbound.local.pos = outbound.local.pos.add(endPos.sub(playPos).normalized().mul(deltaTime * 450.0f));

			if (outbound.local.pos.x >= endPos.x)
			{
				//outbound.local.pos.x = endPos.x;
				//outbound.local.pos.y = endPos.y;
				
				removeBox();
			}
		}
	}

	@Override
	public void draw(RenderContext renderContext)
	{
		renderContext.draw(background, Converter.RGBToInt(255,255,255), new Vector3f(0, 0, -50));
		
		renderContext.draw(factInboundFront, Converter.RGBToInt(210, 210, 255), parent.local.pos.add(factInboundFrontOff));
		renderContext.draw(factInboundBack, Converter.RGBToInt(210, 210, 255), parent.local.pos.add(factInboundBackOff));
		
		renderContext.draw(factOutbound, Converter.RGBToInt(255, 210, 210), parent.local.pos.add(factOutboundOff));
		
		renderContext.draw(factSide, Converter.RGBToInt(255, 255, 100), parent.local.pos.add(factFrontSideOff));
		renderContext.draw(factSide, Converter.RGBToInt(255, 255, 100), parent.local.pos.add(factBackSideOff));
		renderContext.draw(factMiddle, Converter.RGBToInt(20, 20, 20), parent.local.pos.add(factMiddleOff));
	}

	public void createBox()
	{
		if (inbound == null && outbound == null)
		{
			if (play != null)
			{
				if (outbound != null)
					throw new RuntimeException("play trying to move to outbound while outbound still exists");
				
				outbound = play;
				play = null;
			}
			
			if (timeInSeconds > 0)
			{
				inbound = new GameObject("BoxObj");
				inbound.addComponent(new Box("Box"));
				parent.addChild(inbound);
				inbound.local.pos.x = startPos.x;
				inbound.local.pos.y = startPos.y;
			}
		}
	}
	
	public void removeBox()
	{
		totalScore += ((Box)outbound.findComponent(Box.class)).generateScore();
		parent.removeChild(outbound);
		outbound = null;
		
		scoreBox.clearMessage();
		scoreBox.setMessage("Score: " + Integer.toString(totalScore));
		
		if (timeInSeconds <= 0)
		{
			game.setNextScene(new ScoreScreen(totalScore));
		}
	}

	@Override
	public void handleEvent(Event event)
	{
	}

	@Override
	public String getName()
	{
		return null;
	}
}
