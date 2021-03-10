package com.sd.clientinfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

@WebServlet("/result")
public class Result extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Mixin.rLock.lock();
        try (InputStream in = new BufferedInputStream(Result.class.getResourceAsStream("/result.json"))) {
            int ch;
            while ((ch = in.read()) != -1) {
                resp.getWriter().write((char) ch);
            }
        } finally {
            Mixin.rLock.unlock();
        }
    }
}
