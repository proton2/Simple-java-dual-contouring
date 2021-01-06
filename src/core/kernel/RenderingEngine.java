package core.kernel;

import core.configs.Default;
import simpleDC.DcWrapper;

/**
 * 
 * @author oreon3D
 * The RenderingEngine manages the render calls of all 3D entities
 * with shadow rendering and post processing effects
 *
 */
public class RenderingEngine {
	
	private Window window;

	private DcWrapper dcWrapper;
	
	public RenderingEngine()
	{
		window = Window.getInstance();
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
