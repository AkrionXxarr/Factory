package com.game.level1;

import core.math.vector.Vector3f;
import core.resource.audio.AudioClip;
import core.resource.audio.AudioManager;
import core.resource.graphics.Bitmap;
import core.utility.Converter;
import game.component.base.GameComponent;
import game.engine.RenderContext;
import game.object.base.GameObject;

public class Box extends GameComponent
{
	public static class Lid
	{
		public boolean isOpen = true;
		public int order = 0;
		public Vector3f openOff, closedOff;
		public Bitmap open, closed;
	}
	
	Bitmap boxBack, boxFront;
	
	public Lid leftLid = new Lid();
	public Lid rightLid = new Lid();
	public Lid frontLid = new Lid();
	public Lid backLid = new Lid();
	
	int order = 0;
	
	boolean gotLabel = false;
	boolean gotTape = false;
	
	Vector3f labelPos;
	Vector3f tapeStartPos;
	Vector3f tapeEndPos;
	Vector3f optLabelPos = new Vector3f(65, 37, 0);
	Vector3f optTapeStart = new Vector3f(26, 12, 0);
	Vector3f optTapeEnd = new Vector3f(122, 19, 0);
	
	AudioClip lidShut = new AudioClip("./res/sfx/", "BoxLidShut.wav");

	public Box(String name)
	{
		super(name);
		
		boxFront = new Bitmap("./res/bitmaps/box", "BoxFront.bmp");
		boxBack = new Bitmap("./res/bitmaps/box", "BoxBack.bmp");
		
		leftLid.open = new Bitmap("./res/bitmaps/box", "LeftLid_Open.bmp");
		leftLid.closed = new Bitmap("./res/bitmaps/box", "LeftLid_Closed.bmp");
		
		rightLid.open = new Bitmap("./res/bitmaps/box", "RightLid_Open.bmp");
		rightLid.closed = new Bitmap("./res/bitmaps/box", "RightLid_Closed.bmp");
		
		frontLid.open = new Bitmap("./res/bitmaps/box", "FrontLid_Open.bmp");
		frontLid.closed = new Bitmap("./res/bitmaps/box", "FrontLid_Closed.bmp");
		
		backLid.open = new Bitmap("./res/bitmaps/box", "backLid_Open.bmp");
		backLid.closed = new Bitmap("./res/bitmaps/box", "backLid_Closed.bmp");
		
		boxFront.useColorMask(Converter.RGBToInt(255, 0, 255));
		boxBack.useColorMask(Converter.RGBToInt(255, 0, 255));
		leftLid.open.useColorMask(Converter.RGBToInt(255, 0, 255));
		leftLid.closed.useColorMask(Converter.RGBToInt(255, 0, 255));
		rightLid.open.useColorMask(Converter.RGBToInt(255, 0, 255));
		rightLid.closed.useColorMask(Converter.RGBToInt(255, 0, 255));
		frontLid.open.useColorMask(Converter.RGBToInt(255, 0, 255));
		frontLid.closed.useColorMask(Converter.RGBToInt(255, 0, 255));
		backLid.open.useColorMask(Converter.RGBToInt(255, 0, 255));
		backLid.closed.useColorMask(Converter.RGBToInt(255, 0, 255));
	}

	@Override
	public void onAdd()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRemove()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void start()
	{
		leftLid.openOff = new Vector3f(-39, -12, 20);
		rightLid.openOff = new Vector3f(107, -2, 21);
		frontLid.openOff = new Vector3f(-28, 12, 21);
		backLid.openOff = new Vector3f(40, -14, 20);
		
		leftLid.closedOff = new Vector3f(2, 1, 13);
		rightLid.closedOff = new Vector3f(64, 5, 13);
		frontLid.closedOff = new Vector3f(2, 11, 13);
		backLid.closedOff = new Vector3f(24, 1, 13);
	}

	@Override
	public void stop()
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
		Vector3f v = parent.world.pos;
		
		renderContext.draw(boxBack, Converter.RGBToInt(165, 129, 82), new Vector3f(v.x, v.y, 10));
		renderContext.draw(boxFront, Converter.RGBToInt(165, 129, 82), new Vector3f(v.x, v.y + 6, 12));
		
		if (leftLid.isOpen)
			renderContext.draw(leftLid.open, Converter.RGBToInt(165, 129, 82), v.add(leftLid.openOff));
		else
			renderContext.draw(leftLid.closed, Converter.RGBToInt(165, 129, 82), v.add(leftLid.closedOff));
		
		if (rightLid.isOpen)
			renderContext.draw(rightLid.open, Converter.RGBToInt(165, 129, 82), v.add(rightLid.openOff));
		else
			renderContext.draw(rightLid.closed, Converter.RGBToInt(165, 129, 82), v.add(rightLid.closedOff));
		
		if (frontLid.isOpen)
			renderContext.draw(frontLid.open, Converter.RGBToInt(165, 129, 82), v.add(frontLid.openOff));
		else
			renderContext.draw(frontLid.closed, Converter.RGBToInt(165, 129, 82), v.add(frontLid.closedOff));
		
		if (backLid.isOpen)
			renderContext.draw(backLid.open, Converter.RGBToInt(165, 129, 82), v.add(backLid.openOff));
		else
			renderContext.draw(backLid.closed, Converter.RGBToInt(165, 129, 82), v.add(backLid.closedOff));	
		
		//renderContext.drawPoint(Converter.RGBToInt(255, 255, 255), optTapeStart.x + parent.world.pos.x,  optTapeStart.y + parent.world.pos.y, 50);
		//renderContext.drawPoint(Converter.RGBToInt(255, 255, 255), optTapeEnd.x + parent.world.pos.x,  optTapeEnd.y + parent.world.pos.y, 50);
		//renderContext.drawPoint(Converter.RGBToInt(255, 255, 255), optLabelPos.x + parent.world.pos.x,  optLabelPos.y + parent.world.pos.y, 50);
	}
	
	public boolean addLabel(GameObject go)
	{
		Vector3f goPos = go.world.pos;
		Vector3f diff = goPos.sub(parent.world.pos);
		Vector3f finalPos = new Vector3f(diff.x + 0.5f, diff.y + 0.5f, 13);
		
		if (finalPos.x > 2 && finalPos.y > 26 && finalPos.x < 75 && finalPos.y < 73 + 6)
		{
			go.local.pos = finalPos;
			go.getParent().removeChild(go);
			this.parent.addChild(go);
			
			gotLabel = true;
			labelPos = new Vector3f(finalPos.x, finalPos.y, 0);
			
			return true;
		}

		return false;
	}
	
	public void addTape(GameObject tape)
	{
		// This is sloppy
		LineRenderer lr = tape.findComponent(LineRenderer.class);
		
		if (lr != null)
		{
			lr.getParent().local.pos.z = 20;
			gotTape = true;
			tapeStartPos = new Vector3f(lr.start.local.pos.x, lr.start.local.pos.y, 0);
			tapeEndPos = new Vector3f(lr.end.local.pos.x, lr.end.local.pos.y, 0);
		}
		else
		{
			throw new RuntimeException("Line renderer is null!");
		}
	}
	
	public void closeLeft()
	{
		if (leftLid.isOpen && order < 4)
		{
			AudioManager.play(lidShut);
			leftLid.closedOff.z += order;
			leftLid.isOpen = false;
			leftLid.order = order;
			order++;
		}
	}
	
	public void closeRight()
	{
		if (rightLid.isOpen && order < 4)
		{
			AudioManager.play(lidShut);
			rightLid.closedOff.z += order;
			rightLid.isOpen = false;
			rightLid.order = order;
			order++;
		}
	}
	
	public void closeFront()
	{
		if (frontLid.isOpen && order < 4)
		{
			AudioManager.play(lidShut);
			frontLid.closedOff.z += order;
			frontLid.isOpen = false;
			frontLid.order = order;
			order++;
		}
	}
	
	public void closeBack()
	{
		if (backLid.isOpen && order < 4)
		{
			AudioManager.play(lidShut);
			backLid.closedOff.z += order;
			backLid.isOpen = false;
			backLid.order = order;
			order++;
		}
	}
	
	public float generateScore()
	{
		if (!readyForOutbound())
			throw new RuntimeException("Generating score on a box that's not ready");
		
		float maxScore = 1000;
		
		float lidScore = -500;
		
		if (frontLid.order > 1 && backLid.order > 1)
			lidScore = 0;
		
		float labelScore = -labelPos.sub(optLabelPos).squaredMagnitude();
		float tapeStartScore = -tapeStartPos.sub(optTapeStart).squaredMagnitude();
		float tapeEndScore = -tapeEndPos.sub(optTapeEnd).squaredMagnitude();
		
		maxScore += lidScore + labelScore + tapeStartScore + tapeEndScore;
			
		return maxScore;
	}
	
	public boolean allClosed()
	{
		return !leftLid.isOpen && !rightLid.isOpen && !frontLid.isOpen && !backLid.isOpen;
	}
	
	public boolean isValidTapePos(Vector3f world)
	{
		Vector3f v = world.sub(parent.world.pos);
		
		if (v.x > 18 && v.y > 1 && v.x < 142 && v.y < 30)
			return true;
		
		return false;
	}
	
	public boolean isLabeled()
	{
		return gotLabel;
	}
	
	public boolean isTaped()
	{
		return gotTape;
	}
	
	public boolean readyForOutbound()
	{
		return allClosed() && isLabeled() && isTaped();
	}
}
