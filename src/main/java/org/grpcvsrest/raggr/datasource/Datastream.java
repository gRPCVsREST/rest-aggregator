package org.grpcvsrest.raggr.datasource;


import java.util.concurrent.atomic.AtomicInteger;

public class Datastream {

    private final Datasource datasource;

    private volatile AtomicInteger lastContentId = new AtomicInteger(0);
    private volatile boolean done = false;

    public Datastream(Datasource datasource) {
        this.datasource = datasource;
    }

    public boolean isDone() {
        return done;
    }

    public Content next() {
        if (done) {
            return null;
        }

        int contentId = lastContentId.incrementAndGet();

        Content result = datasource.fetch(contentId);
        if (result.getNextId() == null) {
            done = true;
        }
        return result;
    }



}
