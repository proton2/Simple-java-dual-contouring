package core.kernel;

import core.configs.Default;
import simpleDC.DcWrapper;
import shaders.Skydome;

/**
 * 
 * @author oreon3D
 * The RenderingEngine manages the render calls of all 3D entities
 * with shadow rendering and post processing effects
 *
 */
public class RenderingEngine {
	
	private Window window;
	
	private Skydome skydome;

	private DcWrapper dcWrapper;
	
	public RenderingEngine()
	{
		window = Window.getInstance();
		skydome = new Skydome();
		dcWrapper = new DcWrapper();
	}
	
	public void init()
	{
		window.init();
	}

	public void render()
	{	
		Camera.getInstance().update();
		
		Default.clearScreen();
		
		skydome.render();

		dcWrapper.update();
		dcWrapper.render();
		
		// draw into OpenGL window
		window.render();
	}
	
	public void update(){}
	
	public void shutdown(){
		dcWrapper.shutDown();
	}
}
