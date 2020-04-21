package com.github.nijian.jkeel.concept;

public final class Context<M extends Manager> {

    private M manager;

    private User user;

    /**
     * private construction
     */
    public Context(M manager, User user) {
        this.manager = manager;
        this.user = user;
    }

    public M getManager() {
        return manager;
    }

    public User getUser() {
        return user;
    }
}
