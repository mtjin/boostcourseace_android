package com.example.boostcourseaceproject4.otto;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

public class CommentBusProvider {
    public static final Bus bus = new Bus(ThreadEnforcer.ANY);

    public static Bus getInstance(){
        return bus;
    }

    public CommentBusProvider() {

    }
}
