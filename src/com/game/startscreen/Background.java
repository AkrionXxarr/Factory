package com.game.startscreen;

import core.math.vector.Vector3f;
import core.resource.graphics.Bitmap;
import core.utility.Converter;
import game.component.base.GameComponent;
import game.engine.RenderContext;

public class Background extends GameComponent
{
	Bitmap bitmap;
	
	public Background(String filePath, String fileName)
	{
		super("Background");
		
		bitmap = new Bitmap(filePath, fileName);
	}

	@Override
	public void onAdd()
	{
	}

	@Override
	public void onRemove()
	{
	}

	@Override
	public void start()
	{
	}

	@Override
	public void stop()
	{
	}

	@Override
	public void update(float deltaTime)
	{
	}

	@Override
	public void draw(RenderContext renderContext)
	{	
		renderContext.draw(bitmap, Converter.RGBToInt(255, 255, 255), new Vector3f(0, 0, -1000));
	}
}
