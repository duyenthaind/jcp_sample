package com.thaind.jcp.future_executors;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class PreLoader {

    ProductInfo loadProductInfo() throws DataLoadException{
        return null;
    }

    private final FutureTask<ProductInfo> future = new FutureTask<>(new Callable<ProductInfo>(){
        public ProductInfo call() throws Exception {
            return loadProductInfo();
        };
    });

    private final Thread thread = new Thread(future);

    public void start(){
        thread.start();
    }

    public ProductInfo get() throws DataLoadException, InterruptedException, ExecutionException{
        try{
            return future.get();
        } catch(ExecutionException exception){
            Throwable cause = exception.getCause();
            if(cause instanceof DataLoadException){
                throw (DataLoadException) cause;
            } else {
                throw LaunderThrowable.launderThrowable(cause);
            }
        }
    }
}

interface ProductInfo{
}

class DataLoadException extends Exception{
}
