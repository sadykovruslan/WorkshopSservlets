package org.example.report;

import jakarta.servlet.AsyncContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(value = "/big-servlet", asyncSupported = true)
public class BigReportServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long start = System.currentTimeMillis();

//        resp.getWriter().println(new String(new byte[100_000_000]));

        AsyncContext asyncContext = req.startAsync();
        ServletOutputStream os = resp.getOutputStream();
        os.setWriteListener(new WriteListener() {
            byte[] result = new byte[100_000_000];
            int offset = 0;
            @Override
            public void onWritePossible() throws IOException {
                while (os.isReady()){
                    if(offset >= result.length){
                        asyncContext.complete();
                        return;
                    }
                    os.write(result, offset, Math.min(1024, result.length - offset));
                    offset += 1024;
                }

            }

            @Override
            public void onError(Throwable throwable) {
                asyncContext.complete();

            }
        });

        req.getServletContext().log (String.format("[BigReportServlet] Thread %s %d", Thread.currentThread().getName(),  System.currentTimeMillis()-start));
    }
}
