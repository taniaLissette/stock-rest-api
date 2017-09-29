package com.skyfenko.web.constants;

/**
 * Hold all the URIs used by this app
 *
 * @author Stanislav Kyfenko
 */
public interface URIConstants {

    interface Api {
        String STOCKS = "/api/stocks";
    }

    interface Flow {
        String LOGIN = "/login";
        String DASHBOARD = "/dashboard";
        String LOGOUT = "/logout";
        String ACCESS_DENIED = "/403";
    }
}
