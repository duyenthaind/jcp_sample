package com.thaind.jcp.safe_escape;

public class SafeListener {
    private final EventListener listener;

    private SafeListener() {
        listener = new EventListener() {
            @Override
            public void onEvent(Event e) {
                doSomething(e);
            }
        };
    }

    public static SafeListener newInstance(EventSource source){
        SafeListener safeListener = new SafeListener();
        source.registerListener(safeListener.listener);
        return safeListener;

    }

    private void doSomething(Event e) {
    }

    private interface EventSource {
        public void registerListener(EventListener e);
    }

    public interface EventListener {
        public void onEvent(Event e);
    }

    private interface Event {
    }
}
