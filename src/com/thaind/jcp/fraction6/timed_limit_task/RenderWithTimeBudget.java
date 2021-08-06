package com.thaind.jcp.fraction6.timed_limit_task;

import java.util.concurrent.*;

public class RenderWithTimeBudget {
    private static final Ad DEFAULT_AD = new Ad();
    private static final long TIME_BUDGET = 10000L;
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();

    Page renderPageWithAd() throws InterruptedException {
        long endNanos = System.nanoTime() + TIME_BUDGET;
        System.out.println("CurrentNano: " + System.nanoTime() + ", EndNano: " + endNanos);
        Future<Ad> future = EXECUTOR_SERVICE.submit(new FetchAdTask());
        Page page = renderPageBody();
        Ad ad;
        try{
            long timeLeft = endNanos - System.nanoTime();
            System.out.println("Time left: " + timeLeft);
            ad = future.get(timeLeft, TimeUnit.NANOSECONDS);
        } catch(ExecutionException executionException){
            ad = DEFAULT_AD;
        } catch(TimeoutException timeoutException){
            ad = DEFAULT_AD;
            future.cancel(true);
        }
        page.setAd(ad);
        return page;
    }

    Page renderPageBody(){
        return new Page();
    }

    public void stop(){
        System.out.println("Stopping all thread then exit program!!!");
        EXECUTOR_SERVICE.shutdown();
        System.exit(0);
    }

    static class Ad{
        private String content;

        public Ad(){

        }

        public Ad(String content){
            this.content = content;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    static class Page{
        private Ad ad;
        public void setAd(Ad ad){
            this.ad = ad;
        }

        public Ad getAd(){
            return this.ad;
        }
    }

    static class FetchAdTask implements Callable<Ad>{

        @Override
        public Ad call() throws Exception {
            System.out.println("Executing task submitted!!!");
            return new Ad("Fetched!");
        }
    }

    public static void main(String[] args) {
        RenderWithTimeBudget renderWithTimeBudget = new RenderWithTimeBudget();
        try{
            Ad adFetched = renderWithTimeBudget.renderPageWithAd().getAd();
            System.out.println(adFetched.getContent());
        } catch(Exception ex){
            System.err.println(ex);
        }
        renderWithTimeBudget.stop();
    }
}

