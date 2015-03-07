package org.test;

import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

import org.easythread.core.EasyThread;
import org.easythread.core.ThreadAfter;
import org.easythread.core.ThreadBefore;
import org.easythread.core.Threads;

/**
 * 测试LinkedBlockingQueue 多生产者多消费者的性能
 * @author cong
 *
 */
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
