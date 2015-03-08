
import org.easythread.annotation.Threads;
import org.easythread.core.EasyThread;

/**
 * 一个简单的实例,一只猫和一只狗隔一段叫一声
 * 
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
