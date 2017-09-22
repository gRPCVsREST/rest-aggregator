package org.grpcvsrest.raggr.datasource;


public class Datastream {

    private final Datasource datasource;

    private volatile Integer lastContentId = 0;
    private volatile boolean done;

    public Datastream(Datasource datasource) {
        this.datasource = datasource;
    }

    public Content next() {
        if (done) {
            return null;
        }

        int contentId = ++lastContentId;

        Content result = datasource.fetch(contentId);
        if (result.getNextId() == null) {
            done = true;
        }
        return result;

    }


}
