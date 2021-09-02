package com.nayki;

import io.smallrye.common.annotation.Blocking;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;

@ApplicationScoped
public class AnalysisRequestProcessor {

    private final static Logger LOG = Logger.getLogger(AnalysisRequestProcessor.class);

    @Incoming("requests")
    @Blocking
    public void process(String analysisId) throws InterruptedException {
        LOG.info("Analysis Id: " + analysisId);
    }


}
