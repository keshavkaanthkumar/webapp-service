package com.neu.webapp;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CORSFilter implements Filter {

private final Logger LOGGER = LoggerFactory.getLogger(CORSFilter.class);

public CORSFilter() {
	LOGGER.info("Entered cors filter");
}

@Override

public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;
    ServletInputStream mServletInputStream = request.getInputStream();
    byte[] httpInData = new byte[request.getContentLength()];
    int retVal = -1;
    StringBuilder stringBuilderreq = new StringBuilder();

    while ((retVal = mServletInputStream.read(httpInData)) != -1) {
        for (int i = 0; i < retVal; i++) {
            stringBuilderreq.append(Character.toString((char) httpInData[i]));
        }
    }


    String url = ((HttpServletRequest)request).getRequestURL().toString();
    LOGGER.info("URL: "+ url);
    LOGGER.info("Request: "+ stringBuilderreq.toString());
    LOGGER.info("Response: "+ response.toString());
    response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
    response.setHeader("Access-Control-Allow-Credentials", "true");
    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE,PUT");
    response.setHeader("Access-Control-Max-Age", "3600");
    response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me, Authorization");

    chain.doFilter(req, res);
}

@Override
public void init(FilterConfig filterConfig) {
}

@Override
public void destroy() {
}

}
