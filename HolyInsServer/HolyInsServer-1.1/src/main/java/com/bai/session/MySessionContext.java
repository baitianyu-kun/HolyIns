package com.bai.session;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

public class MySessionContext {
    private static MySessionContext instance;
    private HashMap<String,HttpSession> sessionMap;

    private MySessionContext() {
        sessionMap = new HashMap<String, HttpSession>();
    }

    public static MySessionContext getInstance() {
        if (instance == null) {
            instance = new MySessionContext();
        }
        return instance;
    }

    public void getAllSession(){
        sessionMap.forEach((k, v) -> System.err.println("key: " + k + " value:" + v));
    }

    public HashMap<String,HttpSession> returnAllSession(){
        return sessionMap;
    }

    public synchronized void addSession(HttpSession session) {
        if (session != null) {
            sessionMap.put(session.getId(), session);
        }
    }

    public synchronized void delSession(HttpSession session) {
        if (session != null) {
            sessionMap.remove(session.getId());
        }
    }

    public synchronized HttpSession getSession(String sessionID) {
        if (sessionID == null) {
            return null;
        }
        return sessionMap.get(sessionID);
    }

}
