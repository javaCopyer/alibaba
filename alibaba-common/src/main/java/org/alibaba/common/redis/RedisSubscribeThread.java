package org.alibaba.common.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import javax.management.NotificationEmitter;

import redis.clients.jedis.JedisPubSub;

public class RedisSubscribeThread extends Thread {
	
	private final Subscriber subscriber = new Subscriber();
	private static final String channel = "test";
	public ServiceThread st = new ServiceThread();
	
	public RedisSubscribeThread() {
		System.out.println("业务处理线程启动");
		st.start();
	}

	@Override
	public void run() {
		//subscribe方法会阻塞，直到调用了取消订阅的方法才会往下执行代码
		RedisUtil.subscribe(subscriber, channel);
	}
	
	
	public class Subscriber extends JedisPubSub {
		ServiceThread st = new ServiceThread();
		@Override
		public void onMessage(String channel, String message) { //收到消息调用
			st.addQueue(message);
			System.out.println("消息接收成功，业务线程处理");
//			new Thread() {
//				@Override
//				public void run() {
//					try {
//						Thread.sleep(3000);
//						System.out.println(this.getName() + ":接收信息：" + message);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//			}.start();
		}

		@Override
		public void onSubscribe(String channel, int subscribedChannels) { //订阅时调用
			System.out.println("订阅频道：" + channel);
		}

		@Override
		public void onUnsubscribe(String channel, int subscribedChannels) { //取消订阅时调用
			System.out.println("取消订阅频道：" + channel);
		}
		
	}
}

class ServiceThread extends Thread {
	private static BlockingQueue<String> messageQueue = new LinkedBlockingDeque<String>();
	private static final boolean START = false;
	//无任务处理时线程等待
	private final Object notify = new Object();
	//默认线程等待时间
	private static final long WAIT_TIME = 3000; 

	private void wait_thread(long wait_time) {
		synchronized (notify) {
			try {
				notify.wait(wait_time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void addQueue(String message) {
		messageQueue.add(message);
		synchronized (notify) {
			notify.notifyAll();
		}
	}
	
	private synchronized void consumeLog() throws InterruptedException {
		List<String> list = new ArrayList<String>();
		messageQueue.drainTo(list);
		Thread.sleep(3000);
		System.out.println("任务队列长度：" + list.size());
		for (String string : list) {
			System.out.println("接收消息：" + string);
		}
	}
	@Override
	public void run() {
		try {
			while (!START) {
				if (!messageQueue.isEmpty()) {
					System.out.println("处理业务中=====");
					consumeLog();
				} else {
					System.out.println("线程wait中=====");
					wait_thread(WAIT_TIME);
				}
				
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
}
