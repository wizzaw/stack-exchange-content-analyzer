package com.nayki.analyzer.processor;

import com.nayki.analyzer.exceptions.NotFoundInCacheException;
import com.nayki.analyzer.models.Analysis;
import com.nayki.analyzer.utils.CacheUtils;
import com.nayki.analyzer.utils.ContentParser;
import com.nayki.analyzer.utils.FileUtils;
import io.smallrye.common.annotation.Blocking;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;

@ApplicationScoped
public class AnalysisRequestProcessor {

    private final static Logger LOG = Logger.getLogger(AnalysisRequestProcessor.class);

    @Inject
    FileUtils fileUtils;

    @Inject
    CacheUtils cacheUtils;

    @Inject
    ContentParser contentParser;

    @Incoming("requests")
    @Blocking
    public void process(String analysisId) throws InterruptedException {
        LOG.info("Analysis Id: " + analysisId);

        try {
            final Analysis analysis = cacheUtils.get(analysisId, Analysis.class);
            final String url = analysis.getUrl();
            fileUtils.downloadFromURL(url);
            final String filePath = url.substring(url.lastIndexOf("/") + 1);
            contentParser.parse(filePath, analysis);
        } catch (IOException ex) {

        } catch (NotFoundInCacheException ex) {

        }
    }


}
