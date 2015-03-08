可以很方便地在一个类里面写多个线程的代码
#Download
[EasyThread v1.0](https://github.com/sahfu/EasyThread/releases/tag/1.0)
#Example
### 一个简单的例子
```java
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
```
###Other Example -- LinkedBlockingQueue Performance Test
```java
	public class LinkedBlockingQueueTest {
		private long start;
		private long end;

		private static LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>();

		@ThreadBefore
		public void before2() {
			start = System.currentTimeMillis();
		}

		@ThreadAfter
		public void after() {
			end = System.currentTimeMillis();
			System.out.print(Thread.currentThread().getName());
			System.out.println(" total run time : " + (end - start));
		}

		@Threads(num = 2)
		public void producer() {
			Random random = new Random();

			for (int i = 0; i < 100000; i++) {
				queue.add(i);
			}
		}

		@Threads(num = 2)
		public void consumer() throws Exception {
			try {
				// wait for producer
				Thread.sleep(50L);
			} catch (InterruptedException e) {
			}
			while (!queue.isEmpty()) {
				queue.poll();
			}
		}

		public static void main(String[] args) throws InstantiationException,
				IllegalAccessException, InterruptedException {
			EasyThread.run(LinkedBlockingQueueTest.class);
		}
	}
```
