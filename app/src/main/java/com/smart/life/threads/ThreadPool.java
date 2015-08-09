package com.smart.life.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
	
	    private static ThreadPool s = null;
		
		private static ExecutorService service;
		
		public synchronized static ThreadPool getInstance(){
			
			if(s == null){
				s = new ThreadPool();
				//�̶������߳�
				int num = Runtime.getRuntime().availableProcessors();
				//˫��*2���ĺ�*4
				service = Executors.newFixedThreadPool(num*2);
			}
			return s;
		}

		/**
		 * ���̳߳�����ִ���ҵĴ�����������
		 * @param run
		 */
		public void addTask(Runnable run){
			service.execute(run);
		}
}
