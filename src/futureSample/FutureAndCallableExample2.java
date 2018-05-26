package futureSample;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureAndCallableExample2 {
    private List<Callable<String>> callables = new ArrayList<>();
    private List<Future<String>> futures = new ArrayList<>();
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, InterruptedException, ExecutionException {
        FutureAndCallableExample2.class.newInstance().go();
    }
	public void go() throws InterruptedException, ExecutionException{
		long star = System.currentTimeMillis();
		String[] strs = new String[]{"AAA","BBB","CCC","DDD","EEE","FFF","GGG","III"};
		ExecutorService executorService = Executors.newFixedThreadPool(strs.length);//how many threads depend on number of parameter
		for(String str: strs){
			callables.add(new ParameterCallable2(str));
		}
        System.out.println("Do something else while callable is getting executed");

        futures = executorService.invokeAll(callables);
        
	    for(Future<String> future: futures){
	    	String result = future.get();
	    	System.out.println(result);
	    }
	    
        System.out.println("Process end,"+(System.currentTimeMillis()-star));
        executorService.shutdown();
	}
}

class ParameterCallable2 implements Callable<String>{
	private final String param;
	public ParameterCallable2(String param) {
		this.param = param;
	}
	@Override
	public String call() throws Exception {
		Thread.sleep(100);
        return param;
	}
}