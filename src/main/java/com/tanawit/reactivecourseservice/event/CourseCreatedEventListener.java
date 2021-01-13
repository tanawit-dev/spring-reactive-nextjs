package com.tanawit.reactivecourseservice.event;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import reactor.core.publisher.FluxSink;

@Component
public class CourseCreatedEventListener
        implements ApplicationListener<CourseCreatedEvent>, Consumer<FluxSink<CourseCreatedEvent>> {

    private final Executor executor;
    private final BlockingQueue<CourseCreatedEvent> queue = new LinkedBlockingQueue<>();

    public CourseCreatedEventListener(Executor executor) {
        this.executor = executor;
    }

    @Override
    public void accept(FluxSink<CourseCreatedEvent> sink) {
        executor.execute(() -> {
            while (true) {
                try {
                    CourseCreatedEvent event = queue.take();
                    sink.next(event);
                } catch (InterruptedException e) {
                    ReflectionUtils.rethrowRuntimeException(e);
                }
            }
        });
    }

    @Override
    public void onApplicationEvent(CourseCreatedEvent event) {
        queue.offer(event);
    }

}
