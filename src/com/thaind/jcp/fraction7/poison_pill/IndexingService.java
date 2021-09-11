package com.thaind.jcp.fraction7.poison_pill;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class IndexingService {
    private static final int CAPACITY = 100;
    private static final File POISON = new File("");
    private final IndexThread consumer = new IndexThread();
    private final CrawlerThread producer = new CrawlerThread();
    private final BlockingQueue<File> queue;
    private final FileFilter fileFilter;
    private final File root;

    public IndexingService(FileFilter fileFilter, File root) {
        this.queue = new LinkedBlockingQueue<>();
        this.root = root;
        this.fileFilter = pathname -> pathname.isDirectory() || fileFilter.accept(pathname);
    }

    class CrawlerThread extends Thread {
        @Override
        public void run() {
            try {
                crawl(root);
            } catch (Exception exception) {
                System.err.println(exception.toString());
            } finally {
                while (true) {
                    try {
                        queue.put(POISON);
                        break;
                    } catch (Exception ex) {
                        System.err.println(ex.toString());
                    }
                }
            }
        }

        private boolean alreadyIndexed(File file) {
            return false;
        }

        private void crawl(File root) throws InterruptedException {
            File[] entries = root.listFiles(fileFilter);
            if (entries != null) {
                for (File entry : entries) {
                    if (entry.isDirectory()) {
                        crawl(entry);
                    } else if (!alreadyIndexed(entry)) {
                        queue.put(entry);
                    }
                }
            }
        }
    }

    class IndexThread extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    File file = queue.take();
                    if (file == POISON) {
                        break;
                    } else {
                        indexFile(file);
                    }
                }
            } catch (Exception ex) {
            }

        }

        public void indexFile(File file) {
        }
    }

    public void start() {
        producer.start();
        consumer.start();
    }

    public void stop() {
        producer.interrupt();
    }

    public void awaitTermination() throws InterruptedException {
        consumer.join();
    }
}
