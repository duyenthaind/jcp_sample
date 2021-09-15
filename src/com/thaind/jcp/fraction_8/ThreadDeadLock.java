package com.thaind.jcp.fraction_8;

import com.thaind.annotation.UnReliable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author duyenthai
 */
@UnReliable
public class ThreadDeadLock {
    ExecutorService executorService = Executors.newSingleThreadExecutor();

    private final String BODY_TEXT = "<p></p>";

    public class LoadFileTask implements Callable<String> {
        private final String fileName;

        public LoadFileTask(String fileName) {
            this.fileName = fileName;
        }

        @Override
        public String call() throws Exception {
            // read the file here
            return "";
        }
    }

    public class RenderPageTask implements Callable<String> {

        @Override
        public String call() throws Exception {
            Future<String> header, footer;
            header = executorService.submit(new LoadFileTask("header.html"));
            footer = executorService.submit(new LoadFileTask("footer.html"));
            String page = renderBody();
            /*Will deadlock -- task waiting for result of subtask*/
            return header.get() + page + footer.get();
        }

        private String renderBody() {
            // render body of the page here
            return BODY_TEXT;
        }
    }
}
