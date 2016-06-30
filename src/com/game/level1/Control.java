package com.game.level1;

import java.awt.Point;
import java.awt.event.MouseEvent;

import core.event.Event;
import core.event.EventManager;
import core.input.InputEvent;
import core.math.vector.Vector2f;
import core.math.vector.Vector3f;
import core.resource.audio.AudioClip;
import core.resource.audio.AudioManager;
import core.resource.graphics.Bitmap;
import core.utility.Converter;
import game.component.base.GameComponent;
import game.component.graphics.SpriteRenderer;
import game.engine.RenderContext;
import game.object.base.GameObject;

public class Control extends GameComponent implements Event.Listener
{
	private static class Switch
	{
		public static Bitmap up, down;
		public static AudioClip downSfx;
		//public boolean isUp = true;
		GameObject obj;
		
		public Switch(GameObject obj)
		{
			this.obj = obj;
		}
	}
	
	private static class Button
	{
		public static Bitmap pressed, unpressed;
		public static AudioClip pressedSfx, unpressedSfx;
		public boolean isPressed = false;
		public GameObject obj;
		
		public Button(GameObject obj)
		{
			this.obj = obj;
		}
	}
	
	private static class BoxLight
	{
		public static Bitmap on, off;
		//public boolean isOn = false;
		public GameObject obj;
		
		public BoxLight(GameObject obj)
		{
			this.obj = obj;
		}
		
		public void turnOn()
		{
			((SpriteRenderer)obj.findComponent(SpriteRenderer.class)).sprite = BoxLight.on;
			//this.isOn = true;
		}
		
		public void turnOff()
		{
			((SpriteRenderer)obj.findComponent(SpriteRenderer.class)).sprite = BoxLight.off;
			//this.isOn = false;
		}
	}
	
	private static class StatusLight
	{
		public static Bitmap on, off;
		public Bitmap label;
		public boolean isOn = false;
		public GameObject obj;
		
		public StatusLight(GameObject obj)
		{
			this.obj = obj;
		}
		
		public void turnOn()
		{
			((SpriteRenderer)obj.findComponent(SpriteRenderer.class)).sprite = StatusLight.on;
			this.isOn = true;
		}
		
		public void turnOff()
		{
			((SpriteRenderer)obj.findComponent(SpriteRenderer.class)).sprite = StatusLight.off;
			this.isOn = false;
		}
	}
	
	private static class Tape
	{
		public static Bitmap bmp;
		public static AudioClip pickUp, setDown, place;
		public GameObject obj;
		
		public GameObject tapeLine;
		public LineRenderer lineRenderer;
		
		public Vector2f start = new Vector2f(0, 0), end = new Vector2f(0, 0);
		public boolean dragging = false;
		
		float placeAudioTime = 0.05f;
		float elapsedTime = 0;
		
		public Tape(GameObject obj)
		{
			this.obj = obj;
		}
	}
	
	private static class Label
	{
		public static Bitmap bmp;
		public static AudioClip pickUp, setDown, place;
		public GameObject obj;
		
		public Label(GameObject obj)
		{
			this.obj = obj;
		}
	}
	
	Factory factory;
	Box boxInPlay;
	
	Bitmap panel;
	
	AudioClip error;
	AudioClip good;
	
	GameObject controlPanel;
	
	Switch leftSwitch, rightSwitch, frontSwitch, backSwitch;
	Button button;
	BoxLight boxLight;
	StatusLight closedLight, tapedLight, addressLight;
	Tape tape;
	Label label;
	
	boolean tapeFollowingMouse = false;
	boolean labelFollowingMouse = false;

	public Control(String name, Factory factory)
	{
		super(name);
		
		this.factory = factory;
		
		//
		// Bitmaps
		//
		panel = new Bitmap("./res/bitmaps/controls/", "Panel.bmp");
		
		BoxLight.off = new Bitmap("./res/bitmaps/controls/", "LightOff.bmp");
		BoxLight.on = new Bitmap("./res/bitmaps/controls/", "LightOn.bmp");
		StatusLight.off = new Bitmap("./res/bitmaps/controls/", "StatusLightOff.bmp");
		StatusLight.on = new Bitmap("./res/bitmaps/controls/", "StatusLightOn.bmp");
		Button.unpressed = new Bitmap("./res/bitmaps/controls/", "ButtonUnpressed.bmp");
		Button.pressed = new Bitmap("./res/bitmaps/controls/", "ButtonPressed.bmp");
		Switch.up = new Bitmap("./res/bitmaps/controls/", "SwitchUp.bmp");
		Switch.down = new Bitmap("./res/bitmaps/controls/", "SwitchDown.bmp");
		Tape.bmp = new Bitmap("./res/bitmaps/controls/", "Tape.bmp");
		Label.bmp = new Bitmap("./res/bitmaps/controls/", "Label.bmp");
		
		BoxLight.off.useColorMask(Converter.RGBToInt(255, 0, 255));
		BoxLight.on.useColorMask(Converter.RGBToInt(255, 0, 255));
		StatusLight.off.useColorMask(Converter.RGBToInt(255, 0, 255));
		StatusLight.on.useColorMask(Converter.RGBToInt(255, 0, 255));
		Button.unpressed.useColorMask(Converter.RGBToInt(255, 0, 255));
		Button.pressed.useColorMask(Converter.RGBToInt(255, 0, 255));
		Switch.up.useColorMask(Converter.RGBToInt(255, 0, 255));
		Switch.down.useColorMask(Converter.RGBToInt(255, 0, 255));
		Tape.bmp.useColorMask(Converter.RGBToInt(255, 0, 255));
		Label.bmp.useColorMask(Converter.RGBToInt(255, 0, 255));
		
		
		//
		// Sound Effects
		//
		//Switch.upSfx = new AudioClip("./res/sfx/", "SwitchUp.wav");
		Switch.downSfx = new AudioClip("./res/sfx/", "SwitchFlip.wav");
		Button.pressedSfx = new AudioClip("./res/sfx/", "ButtonPress.wav");
		Button.unpressedSfx = new AudioClip("./res/sfx/", "ButtonRelease.wav");
		Tape.pickUp = new AudioClip("./res/sfx/", "TapePickUp.wav");
		Tape.setDown = new AudioClip("./res/sfx/", "TapeSetDown.wav");
		Tape.place = new AudioClip("./res/sfx/", "TapePlace.wav");
		Label.pickUp = new AudioClip("./res/sfx/", "LabelPickUp.wav");
		Label.setDown = new AudioClip("./res/sfx/", "LabelSetDown.wav");
		Label.place = new AudioClip("./res/sfx/", "LabelPlace.wav");
		error = new AudioClip("./res/sfx/", "Error.wav");
		good = new AudioClip("./res/sfx/", "Good.wav");
		
		
		//
		// Game Objects
		//
		controlPanel = new GameObject("Control Panel");
		
		boxLight = new BoxLight(new GameObject("Box Light"));
		closedLight = new StatusLight(new GameObject("Closed Light"));
		tapedLight = new StatusLight(new GameObject("Taped Light"));
		addressLight = new StatusLight(new GameObject("Address Light"));
		
		closedLight.label = new Bitmap("./res/bitmaps/controls/", "BoxClosedStatus.bmp");
		tapedLight.label = new Bitmap("./res/bitmaps/controls/", "BoxTapedStatus.bmp");
		addressLight.label = new Bitmap("./res/bitmaps/controls/", "BoxAddressStatus.bmp");
		
		leftSwitch = new Switch(new GameObject("Left Switch"));
		rightSwitch = new Switch(new GameObject("Right Switch"));;
		frontSwitch = new Switch(new GameObject("Front Switch"));;
		backSwitch = new Switch(new GameObject("Back Switch"));;
		
		button = new Button(new GameObject("Button"));
		
		tape = new Tape(new GameObject("Tape Roll"));
		label = new Label(new GameObject("Label"));
		
		controlPanel.addComponent(new SpriteRenderer("Control Panel", panel));
		
		boxLight.obj.addComponent(new SpriteRenderer("Light", BoxLight.off));
		
		closedLight.obj.addComponent(new SpriteRenderer("Label", closedLight.label));
		tapedLight.obj.addComponent(new SpriteRenderer("Label", tapedLight.label));
		addressLight.obj.addComponent(new SpriteRenderer("Label", addressLight.label));
		
		closedLight.obj.addComponent(new SpriteRenderer("Light", StatusLight.off, new Vector3f(41, 0, 0)));
		tapedLight.obj.addComponent(new SpriteRenderer("Light", StatusLight.off, new Vector3f(41, 0, 0)));
		addressLight.obj.addComponent(new SpriteRenderer("Light", StatusLight.off, new Vector3f(41, 0, 0)));
		
		leftSwitch.obj.addComponent(new SpriteRenderer("Left Switch", Switch.up));
		rightSwitch.obj.addComponent(new SpriteRenderer("Right Switch", Switch.up));
		frontSwitch.obj.addComponent(new SpriteRenderer("Front Switch", Switch.up));
		backSwitch.obj.addComponent(new SpriteRenderer("Back Switch", Switch.up));
		
		button.obj.addComponent(new SpriteRenderer("Button", Button.unpressed));
		
		tape.obj.addComponent(new SpriteRenderer("Tape", Tape.bmp));
		label.obj.addComponent(new SpriteRenderer("Label", Label.bmp));
	}

	@Override
	public void onAdd()
	{
		EventManager.register(this, InputEvent.Mouse.moved);
		EventManager.register(this, InputEvent.Mouse.dragged);
		EventManager.register(this, InputEvent.Mouse.released);
		EventManager.register(this, InputEvent.Mouse.pressed);
	}

	@Override
	public void onRemove()
	{
		EventManager.unregister(this, InputEvent.Mouse.moved);
		EventManager.unregister(this, InputEvent.Mouse.dragged);
		EventManager.unregister(this, InputEvent.Mouse.released);
		EventManager.unregister(this, InputEvent.Mouse.pressed);
	}

	@Override
	public void start()
	{
		parent.addChild(controlPanel);
		parent.addChild(tape.obj);
		//parent.addChild(label.obj);
		
		controlPanel.addChild(boxLight.obj);
		controlPanel.addChild(closedLight.obj);
		controlPanel.addChild(tapedLight.obj);
		controlPanel.addChild(addressLight.obj);
		
		controlPanel.addChild(leftSwitch.obj);
		controlPanel.addChild(rightSwitch.obj);
		controlPanel.addChild(frontSwitch.obj);
		controlPanel.addChild(backSwitch.obj);
		
		controlPanel.addChild(button.obj);
		
		controlPanel.local.pos = new Vector3f(180, 221, 50);
		
		boxLight.obj.local.pos = new Vector3f(99, -15, 1);
		closedLight.obj.local.pos = new Vector3f(147, 0, 1);
		tapedLight.obj.local.pos = new Vector3f(147, 18, 1);
		addressLight.obj.local.pos = new Vector3f(147, 36, 1);
		
		leftSwitch.obj.local.pos = new Vector3f(15, 30, 1);
		rightSwitch.obj.local.pos = new Vector3f(65, 30, 1);
		frontSwitch.obj.local.pos = new Vector3f(40, 5, 1);
		backSwitch.obj.local.pos = new Vector3f(40, 55, 1);
		
		button.obj.local.pos = new Vector3f(105, 10, 1);
		
		tape.obj.local.pos = new Vector3f(5, 200, 60);
		//label.obj.local.pos = new Vector3f(5, 250, 60);
	}

	@Override
	public void stop()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float deltaTime)
	{
		if (boxInPlay == null)
		{
			if (factory.play != null)
			{
				boxInPlay = (Box)factory.play.findComponent(Box.class);
				parent.removeChild(label.obj);
				parent.addChild(label.obj);
				
				label.obj.local.pos = new Vector3f(5, 250, 60);

				boxLight.turnOn();
			}
		}
		else
		{	
			if (tape.dragging)
			{
				tape.elapsedTime += deltaTime;
				if (tape.elapsedTime >= tape.placeAudioTime)
				{
					tape.elapsedTime = 0;
					AudioManager.play(Tape.place);
				}
			}
			
			if (factory.play == null)
			{
				boxInPlay = null;
				
				boxLight.turnOff();
			}
		}
	}

	@Override
	public void draw(RenderContext renderContext)
	{
		// TODO Auto-generated method stub
		
	}
	
	// Not the greatest code ever~
	public void flipSwitch(GameObject go, boolean flipDown)
	{
		SpriteRenderer sr = go.findComponent(SpriteRenderer.class);
		
		if (flipDown)
		{
			if (sr.sprite == Switch.down)
				return;
			
			sr.sprite = Switch.down;
			AudioManager.play(Switch.downSfx);
			
			if (go == leftSwitch.obj)
			{
				boxInPlay.closeLeft();
			}
			else if (go == rightSwitch.obj)
			{
				boxInPlay.closeRight();
			}
			else if (go == frontSwitch.obj)
			{
				boxInPlay.closeFront();
			}
			else if (go == backSwitch.obj)
			{
				boxInPlay.closeBack();
			}
		}
		else
		{
			sr.sprite = Switch.up;
			//AudioManager.play(switchUpSfx);
		}
	}
	
	public GameObject getClicked(Point p)
	{
		GameObject go = null;
		p.x /= 2;
		p.y /= 2;
		
		if (checkBounds(p, tape.obj, Tape.bmp))
		{
			go = tape.obj;
		}
		else if (checkBounds(p, label.obj, Label.bmp))
		{
			go = label.obj;
		}
		else if (checkBounds(p, leftSwitch.obj, Switch.up))
		{
			go = leftSwitch.obj;
		}
		else if (checkBounds(p, rightSwitch.obj, Switch.up))
		{
			go = rightSwitch.obj;
		}
		else if (checkBounds(p, frontSwitch.obj, Switch.up))
		{
			go = frontSwitch.obj;
		}
		else if (checkBounds(p, backSwitch.obj, Switch.up))
		{
			go = backSwitch.obj;
		}
		else if (checkBounds(p, button.obj, Button.unpressed))
		{
			go = button.obj;
		}
		
		return go;
	}
	
	private boolean checkBounds(Point p, GameObject go, Bitmap bmp)
	{
		return (p.x > go.world.pos.x
			 && p.y > go.world.pos.y
			 && p.x < go.world.pos.x + bmp.getWidth()
			 && p.y < go.world.pos.y + bmp.getHeight());
	}

	@Override
	public void handleEvent(Event event)
	{
		if (event.eventType.id == InputEvent.Mouse.moved.id || event.eventType.id == InputEvent.Mouse.dragged.id)
		{
			InputEvent.Mouse.Data data = event.getData();
			
			if (tapeFollowingMouse)
			{
				tape.obj.local.pos.x = (data.e.getPoint().x / 2) - 5;
				tape.obj.local.pos.y = (data.e.getPoint().y / 2) - 5;
				
				if (tape.dragging)
				{
					tape.end.x = data.e.getPoint().x / 2 + 0.5f;
					tape.end.y = data.e.getPoint().y / 2 + 0.5f;
					
					//Vector2f v = tape.end;
					
					tape.lineRenderer.setEndPos(new Vector3f(tape.end));
				}
			}
			else if (labelFollowingMouse)
			{
				label.obj.local.pos.x = (data.e.getPoint().x / 2) - (Label.bmp.getWidth() / 2);
				label.obj.local.pos.y = (data.e.getPoint().y / 2) - (Label.bmp.getHeight() / 2);
			}
		}
		else if (event.eventType.id == InputEvent.Mouse.released.id)
		{
			InputEvent.Mouse.Data data = event.getData();
			
			if (button.isPressed)
			{
				AudioManager.play(Button.unpressedSfx);
				((SpriteRenderer)button.obj.findComponent(SpriteRenderer.class)).sprite = Button.unpressed;
				button.isPressed = false;
			}
			else if (tapeFollowingMouse)
			{
				if (tape.dragging)
				{
					tape.end.x = data.e.getPoint().x / 2;
					tape.end.y = data.e.getPoint().y / 2;
					
					if (boxInPlay.isValidTapePos(new Vector3f(tape.end)))
					{
						tape.dragging = false;
						tapedLight.turnOn();
						boxInPlay.addTape(tape.tapeLine);
						tape.tapeLine = null;
						tape.lineRenderer = null;
					}
					else
					{
						tape.dragging = false;
						boxInPlay.getParent().removeChild(tape.tapeLine);
						tape.tapeLine = null;
						tape.lineRenderer = null;
						AudioManager.play(error);
					}
				}
			}
		}
		else if (event.eventType.id == InputEvent.Mouse.pressed.id)
		{
			InputEvent.Mouse.Data data = event.getData();
			
			if (boxInPlay != null)
			{			
				if (labelFollowingMouse)
				{
					if (data.e.getButton() == MouseEvent.BUTTON1)
					{
						if (boxInPlay.addLabel(label.obj))
						{
							labelFollowingMouse = false;
							addressLight.turnOn();
							AudioManager.play(Label.place);
						}
						else
						{
							AudioManager.play(error);
						}
					}
					else if (data.e.getButton() == MouseEvent.BUTTON3)
					{
						labelFollowingMouse = false;
						label.obj.local.pos = new Vector3f(5, 250, 60);
						AudioManager.play(Label.setDown);
					}
					return;
				}
				else if (tapeFollowingMouse)
				{
					if (data.e.getButton() == MouseEvent.BUTTON1)
					{
						if (!tape.dragging)
						{
							if (boxInPlay.allClosed() && !boxInPlay.isTaped())
							{
								tape.start.x = data.e.getPoint().x / 2;
								tape.start.y = data.e.getPoint().y / 2;
								tape.end.x = tape.start.x;
								tape.end.y = tape.start.y;
								
								if (boxInPlay.isValidTapePos(new Vector3f(tape.start)))
								{
									tape.dragging = true;
									tape.lineRenderer = new LineRenderer("LineRenderer");
									
									tape.tapeLine = new GameObject("TapeLine");
									tape.tapeLine.addComponent(tape.lineRenderer);
									tape.tapeLine.local.pos = new Vector3f(0, 0, 60);
									
									boxInPlay.getParent().addChild(tape.tapeLine);
									
									tape.lineRenderer.setStartPos(new Vector3f(tape.start));
									tape.lineRenderer.setEndPos(new Vector3f(tape.end));
								}
								else
								{
									AudioManager.play(error);
								}
							}
							else
							{
								AudioManager.play(error);
							}
						}
					}
					else if (data.e.getButton() == MouseEvent.BUTTON3)
					{
						tapeFollowingMouse = false;
						tape.obj.local.pos = new Vector3f(5, 200, 60);
						AudioManager.play(Tape.setDown);
					}
					return;
				}
				
				
				GameObject go = getClicked(data.e.getPoint());
				if (go == leftSwitch.obj || go == rightSwitch.obj || go == frontSwitch.obj || go == backSwitch.obj)
				{
					flipSwitch(go, true);
					
					if (boxInPlay.allClosed() && !closedLight.isOn)
						closedLight.turnOn();
				}
				else if (go == button.obj)
				{
					AudioManager.play(Button.pressedSfx);
					((SpriteRenderer)button.obj.findComponent(SpriteRenderer.class)).sprite = Button.pressed;
					button.isPressed = true;
					
					if (boxInPlay.readyForOutbound())
					{
						factory.createBox();
						
						flipSwitch(leftSwitch.obj, false);
						flipSwitch(rightSwitch.obj, false);
						flipSwitch(frontSwitch.obj, false);
						flipSwitch(backSwitch.obj, false);
						
						closedLight.turnOff();
						tapedLight.turnOff();
						addressLight.turnOff();
					}
					else
					{
						AudioManager.play(error);
					}
				}
				else if (go == tape.obj)
				{
					if (!labelFollowingMouse)
					{
						if (data.e.getButton() == MouseEvent.BUTTON1)
						{
							tapeFollowingMouse = true;
							AudioManager.play(Tape.pickUp);
							tape.obj.local.pos.x = (data.e.getPoint().x / 2) - 5;
							tape.obj.local.pos.y = (data.e.getPoint().y / 2) - 5;
						}
					}
				}
				else if (go == label.obj)
				{
					if (!tapeFollowingMouse)
					{
						if (data.e.getButton() == MouseEvent.BUTTON1)
						{
							labelFollowingMouse = true;
							AudioManager.play(Label.pickUp);
							label.obj.local.pos.x = (data.e.getPoint().x / 2) - (Label.bmp.getWidth() / 2);
							label.obj.local.pos.y = (data.e.getPoint().y / 2) - (Label.bmp.getHeight() / 2);
						}
					}
				}
			}
		}
	}

	@Override
	public String getName()
	{
		return "Control Listener";
	}

}
