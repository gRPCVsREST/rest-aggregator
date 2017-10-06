package org.grpcvsrest.raggr.datasource;


import java.util.concurrent.atomic.AtomicInteger;

public class Datastream {

    private final Datasource datasource;

    private volatile AtomicInteger lastContentId = new AtomicInteger(0);
    private volatile boolean done;

    public Datastream(Datasource datasource) {
        this.datasource = datasource;
    }

    public Content next() {
        if (done) {
            return null;
        }

        int contentId = lastContentId.incrementAndGet();

        Content result = datasource.fetch(contentId);
        if (result == null) {
            done = true;
        }
        return result;
    }



}
