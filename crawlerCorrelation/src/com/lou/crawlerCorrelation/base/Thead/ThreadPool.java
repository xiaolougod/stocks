package com.lou.crawlerCorrelation.base.Thead;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.sleepycat.je.rep.utilint.ServiceDispatcher.ExecutingService;

/**
 * @author loukangwei
 * @typename ThreadPool
 * @desc JDK1.5以后可启动callable 线程池
 */
public class ThreadPool implements Callable<String> {

	private URL url;

	public ThreadPool(URL url) {
		this.url = url;
	}

	@Override
	public String call() throws Exception {
		String content = null;
		// download content
		return content;
	}

	public static void main(String[] args) {
		// 并发线程数量
		int threadnum = 4;
		// 创建线程池
		ExecutorService es = Executors.newFixedThreadPool(threadnum);

		Set<Future<String>> set = new HashSet<Future<String>>();
		List<URL> urls = new ArrayList<URL>();
		for (final URL url : urls) {
			ThreadPool task = new ThreadPool(url);
			// 提交下载任务
			Future<String> future = es.submit(task);
			set.add(future);
		}
		// 通过future对象取得结果
		for (Future<String> future : set) {
			try {
				String content = future.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {

				e.printStackTrace();
			}
			// 处理下载网页的结果代码
			
		}
	}

}
