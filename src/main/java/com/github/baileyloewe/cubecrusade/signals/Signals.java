package com.github.baileyloewe.cubecrusade.signals;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class Signals {
    private Signals() {
    }

    public static class BaseSignal<C> {
        protected final Map<Object, C> subscribers = new ConcurrentHashMap<>();

        public void disconnect(Object owner) {
            subscribers.remove(owner);
        }

        public void clear() {
            subscribers.clear();
        }
    }

    public static class Signal<T> extends BaseSignal<Consumer<T>> {

        public void connect(Object owner, Consumer<T> callback) {
            subscribers.put(owner, callback);
        }

        public void emit(T data) {
            for (ConcurrentHashMap.Entry<Object, Consumer<T>> entry : subscribers.entrySet()) {
                entry.getValue().accept(data);
            }
        }
    }

    public static class EventSignal extends BaseSignal<Runnable> {

        public void connect(Object owner, Runnable callback) {
            subscribers.put(owner, callback);
        }

        public void emit() {
            for (ConcurrentHashMap.Entry<Object, Runnable> subscriber : subscribers.entrySet()) {
                subscriber.getValue().run();
            }
        }
    }
}

