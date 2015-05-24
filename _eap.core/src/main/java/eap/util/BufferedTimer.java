package eap.util;

import java.util.Timer;
import java.util.TimerTask;

/**
 * <p> Title: </p>
 * <p> Description: </p>
 * @作者 chiknin@gmail.com
 * @创建时间 
 * @版本 1.00
 * @修改记录
 * <pre>
 * 版本	   修改人		 修改时间		 修改内容描述
 * ----------------------------------------
 * 
 * ----------------------------------------
 * </pre>
 */
public class BufferedTimer {
	
	private Timer timer;
	private TimerTask preTask;
	
	public BufferedTimer(String name, boolean isDaemon) {
		this.timer = new Timer(name, isDaemon);
	}
	
	public void schedule(TimerTask task, long delay) {
		if (preTask != null) {
			preTask.cancel();
		}
		
		timer.schedule(task, delay);
		preTask = task;
	}
	
	public void cannel() {
		if (preTask != null) {
			preTask.cancel();
		}
		timer.cancel();
	}
	
	public static void main(String[] args) throws Exception {
		TimerTask tt = new TimerTask() {
			@Override
			public void run() {
				System.out.println("2");
			}
		};
		
		BufferedTimer bt = new BufferedTimer("umRefreshTask", false);
		for (int i = 0; i < 100; i++) {
			bt.schedule(new TimerTask() {
				@Override
				public void run() {
					System.out.println("2");
				}
			}, 1000);
		}
	}
}
