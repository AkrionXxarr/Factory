package com.game.level1;

import core.math.vector.Vector2f;
import core.math.vector.Vector3f;
import core.utility.Converter;
import game.component.base.GameComponent;
import game.engine.RenderContext;
import game.object.base.GameObject;

public class LineRenderer extends GameComponent
{
	public GameObject start = new GameObject("line start");
	public GameObject end = new GameObject("line end");
	public Vector3f lineStart = new Vector3f(0, 0, 0);
	public Vector3f lineEnd = new Vector3f(0, 0, 0);

	public LineRenderer(String name)
	{
		super(name);
		// TODO Auto-generated constructor stub
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
		this.parent.addChild(start);
		this.parent.addChild(end);
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
		//Vector3f v1 = lineStart.add(parent.local.pos);
		//Vector3f v2 = lineEnd.add(parent.local.pos);
		
		renderContext.drawLine(
				Converter.RGBToInt(176, 154, 0), 
				new Vector2f(start.world.pos.x, start.world.pos.y), 
				new Vector2f(end.world.pos.x, end.world.pos.y), parent.world.pos.z);
		
		renderContext.drawLine(
				Converter.RGBToInt(176, 154, 0), 
				new Vector2f(start.world.pos.x + 4, start.world.pos.y - 2), 
				new Vector2f(end.world.pos.x + 4, end.world.pos.y - 2), parent.world.pos.z);
		
		renderContext.drawLine(
				Converter.RGBToInt(176, 154, 0), 
				new Vector2f(start.world.pos.x - 4, start.world.pos.y + 2), 
				new Vector2f(end.world.pos.x - 4, end.world.pos.y + 2), parent.world.pos.z);
		
		renderContext.drawLine(
				Converter.RGBToInt(176, 154, 0), 
				new Vector2f(start.world.pos.x + 2, start.world.pos.y - 1), 
				new Vector2f(end.world.pos.x + 2, end.world.pos.y - 1), parent.world.pos.z);
		
		renderContext.drawLine(
				Converter.RGBToInt(176, 154, 0), 
				new Vector2f(start.world.pos.x - 2, start.world.pos.y + 1), 
				new Vector2f(end.world.pos.x - 2, end.world.pos.y + 1), parent.world.pos.z);
	}
	
	public void setStartPos(Vector3f worldPos)
	{
		Vector3f diff = worldPos.sub(parent.world.pos);//parent.world.pos.sub(worldPos);
		start.local.pos = new Vector3f(diff.x, diff.y, parent.world.pos.z);
	}

	public void setEndPos(Vector3f worldPos)
	{
		Vector3f diff = worldPos.sub(parent.world.pos);
		end.local.pos = new Vector3f(diff.x, diff.y, parent.world.pos.z);
	}
}
