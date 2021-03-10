package com.sd.clientinfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;

@WebServlet("/info")
public class Info extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        InfoBean bean = new InfoBean();
        bean.method = req.getMethod();
        bean.requestURL = req.getRequestURL().toString();
        bean.requestURI = req.getRequestURI();
        bean.queryString = req.getQueryString();
        bean.remoteHost = req.getRemoteHost();
        bean.remoteAddr = req.getRemoteAddr();
        bean.remotePort = req.getRemotePort();
        bean.localAddr = req.getLocalAddr();
        bean.localName = req.getLocalName();
        bean.localPort = req.getLocalPort();
        bean.headers = new HashMap<>();
        Enumeration<String> headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            bean.headers.put(headerName, req.getHeader(headerName));
        }
        bean.body = IOUtils.toString(req.getInputStream(), Charset.defaultCharset());
        String json = gson.toJson(bean);
        resp.getWriter().write(json);
        Mixin.wLock.lock();
        try (OutputStream out = new BufferedOutputStream(new FileOutputStream(Result.class.getResource("/result.json").getPath()))) {
            out.write(json.getBytes());
        } finally {
            Mixin.wLock.unlock();
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
