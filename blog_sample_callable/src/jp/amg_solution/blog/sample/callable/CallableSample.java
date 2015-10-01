package jp.amg_solution.blog.sample.callable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableSample {

	public static void main(String[] args) {
		System.out.println("CallableSample start.");

		// Executorクラスの生成：引数にスレッド数を渡す。
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		// 各スレッドの処理を待つためのTaskリストを用意
		List<Future<Boolean>> submitTaskList = new ArrayList<Future<Boolean>>();

		for (int i=0; i<10; i++) {
			submitTaskList.add(executorService.submit(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					Date now = new Date();
					System.out.println(now + " [THREAD_ID] " + Thread.currentThread().getId());
					Thread.sleep(2000);
					return true;
				}
			}));
		}

		// スレッド処理を待つ
		try {
			for (Future<Boolean> future : submitTaskList) {
				if (future.get() == false) {
					throw new RuntimeException("スレッド処理エラー");
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		executorService.shutdown();
		System.out.println("CallableSample end.");
		return;
	}
}
