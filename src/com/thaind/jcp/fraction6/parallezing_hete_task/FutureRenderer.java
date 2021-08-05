package com.thaind.jcp.fraction6.parallezing_hete_task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public abstract class FutureRenderer {
    private final ExecutorService EXECUTOR = Executors.newFixedThreadPool(10);

    public void renderPage(CharSequence source) {
        final List<ImageInfo> listImageInfos = scanForImageInfo(source);
        Callable<List<ImageData>> task = new Callable<List<ImageData>>() {
            @Override
            public List<ImageData> call() throws Exception {
                List<ImageData> result = new ArrayList<>();
                listImageInfos.forEach(index -> result.add(index.downloadImage()));
                return result;
            }
        };

        Future<List<ImageData>> future = EXECUTOR.submit(task);
        renderText(source);

        try {
            List<ImageData> listImageData = future.get();
            listImageData.forEach(this::renderImage);
        } catch (Exception ex) {
            System.err.println(ex.toString());
            Thread.currentThread().interrupt();
            future.cancel(true);
        }
    }

    interface ImageData {

    }

    interface ImageInfo {
        ImageData downloadImage();
    }

    abstract void renderText(CharSequence sequence);

    abstract List<ImageInfo> scanForImageInfo(CharSequence sequence);

    abstract void renderImage(ImageData imageData);
}
