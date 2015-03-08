
import org.easythread.core.EasyThread;
import org.easythread.core.Threads;

/**
 * 一个简单的实例
 * @author cong
 *
 */
public class SimpleExample { 
	
	@Threads
	public void dog() throws InterruptedException {
		for (int i = 0; i < 5; i++) {
			System.out.println("dog bark");
			Thread.sleep(400L);
		}
	}

	@Threads
	public void cat() throws InterruptedException {
		for (int i = 0; i < 5; i++) {
			System.out.println("cat mew");
			Thread.sleep(400L);
		}
	}
	public static void main(String[] args) {
		EasyThread.run(SimpleExample.class);
	}
} 
