package com.main.signals;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Signals {
    private Signals() {}

    public static class BaseSignal<C> {
        protected final List<C> subscribers = new ArrayList<>();

        public void disconnect(C callback) {
            subscribers.remove(callback);
        }

        public void clear() {
            subscribers.clear();
        }
    }

    public static class Signal<T> extends BaseSignal<Consumer<T>> {

        public void connect(Consumer<T> callback) {
            subscribers.add(callback);
        }

        public void emit(T data) {
            for (Consumer<T> subscriber : List.copyOf(subscribers)) {
                subscriber.accept(data);
            }
        }
    }

    public static class EventSignal extends BaseSignal<Runnable> {

        public void connect(Runnable callback) {
            subscribers.add(callback);
        }

        public void emit() {
            for (Runnable subscriber : List.copyOf(subscribers)) {
                subscriber.run();
            }
        }
    }


}

