package com.revature.projectZero.pages;

import com.revature.projectZero.util.PageRouter;
import java.io.BufferedReader;

/**
 * This is an abstract class that holds a few essential things which all display Pages share.
 * All pages extend this class, and all of them have a call to super to initialize the name,
 * route, reader, and router, as it is an Overridden Constructor in the capable hands of the display
 * page.
 */

public abstract class Page {

    protected String name;
    protected String route;
    protected BufferedReader reader;
    protected PageRouter router;

    public Page(String name, String route, BufferedReader reader, PageRouter router) {
        this.name = name;
        this.route = route;
        this.reader = reader;
        this.router = router;
    }

    public String getRoute() {
        return route;
    }

    public abstract void render() throws Exception;
}
