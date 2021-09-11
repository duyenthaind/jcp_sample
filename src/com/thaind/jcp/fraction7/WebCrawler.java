package com.thaind.jcp.fraction7;

import com.thaind.annotation.GuardedBy;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author duyenthai
 */
public abstract class WebCrawler {
    private volatile TrackingExecutor executor;
    @GuardedBy("this")
    private final Set<URL> urlsToCrawl = new HashSet<>();

    private final ConcurrentMap<URL, Boolean> seen = new ConcurrentHashMap<>();
    private static final long TIMEOUT = 500L;
    private static final TimeUnit TIME_UNIT = TimeUnit.MILLISECONDS;

    public WebCrawler(URL startUrl) {
        urlsToCrawl.add(startUrl);
    }

    public synchronized void start() {
        executor = new TrackingExecutor(Executors.newCachedThreadPool());
        for (URL url : urlsToCrawl) {
            submitCrawlTask(url);
        }
        urlsToCrawl.clear();
    }

    public synchronized void stop() throws InterruptedException {
        try {
            saveUnCrawled(executor.shutdownNow());
            if (executor.awaitTermination(TIMEOUT, TIME_UNIT)) {
                saveUnCrawled(executor.getCancelledTask());
            }
        } finally {
            executor = null;
        }
    }

    protected abstract List<URL> processPage(URL url);

    private void saveUnCrawled(List<Runnable> unCrawled) {
        for (Runnable task : unCrawled) {
            urlsToCrawl.add(((CrawTask) task).getPage());
        }
    }

    private void submitCrawlTask(URL url) {
        executor.execute(new CrawTask(url));
    }

    private class CrawTask implements Runnable {

        private final URL url;

        public CrawTask(URL url) {
            this.url = url;
        }

        boolean alreadyCrawled() {
            return seen.putIfAbsent(url, true) != null;
        }

        void markCrawled() {
            seen.remove(url);
            System.out.printf("marking %s uncrawled %n", url);
        }

        @Override
        public void run() {
            for (URL index : processPage(url)) {
                if (Thread.currentThread().isInterrupted()) {
                    submitCrawlTask(index);
                }
            }
        }

        public URL getPage() {
            return url;
        }
    }
}
