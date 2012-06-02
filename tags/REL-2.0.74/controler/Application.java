/**
 * 
 */
package controler;

/**
 * @author dalet
 *
 */
public abstract class Application {
	private static boolean isDone = false;
	private boolean dummy = false;
	protected abstract void init();
	protected abstract void idle();
	protected abstract void cleanup();
	protected static void setDone(){
		isDone = true;
	}
	private boolean done(){
		return isDone; 
	}
	public void run(){
		init();
		//if(!done()){
			idle();
		//}
		//cleanup();
			//to remove warnings
			if(dummy){
				done();
			}
	}
}
