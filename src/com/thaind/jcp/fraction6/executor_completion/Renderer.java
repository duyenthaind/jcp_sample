package com.thaind.jcp.fraction6.executor_completion;

import java.util.List;
import java.util.concurrent.*;

public abstract class Renderer {
    private final ExecutorService EXECUTOR;

    public Renderer(ExecutorService executorService) {
        this.EXECUTOR = executorService;
    }

    public void renderPage(CharSequence source) {
        final List<ImageInfo> infoList = scanForImageInfo(source);
        CompletionService<ImageData> completionService = new ExecutorCompletionService<>(EXECUTOR);
        for (final ImageInfo imageInfo : infoList) {
            completionService.submit(new Callable<ImageData>() {
                @Override
                public ImageData call() throws Exception {
                    return imageInfo.downloadPage();
                }
            });
            renderText(source);
            try {
                for (int index = -1; ++index < infoList.size(); ) {
                    Future<ImageData> future = completionService.take();
                    ImageData imageData = future.get();
                    renderImage(imageData);
                }
            } catch (Exception ex) {
                System.err.println("Message: " + ex.getMessage() + ",trace: " + ex);
            }
        }
    }

    interface ImageData {
    }

    interface ImageInfo {
        ImageData downloadPage();
    }

    abstract void renderText(CharSequence charSequence);

    abstract List<ImageInfo> scanForImageInfo(CharSequence charSequence);

    abstract void renderImage(ImageData imageData);
}
