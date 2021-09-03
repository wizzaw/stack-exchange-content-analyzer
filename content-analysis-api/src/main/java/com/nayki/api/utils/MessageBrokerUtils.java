package com.nayki.api.utils;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.inject.Singleton;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Singleton
public class MessageBrokerUtils {

    @Channel("analysis-requests")
    Emitter<String> analysisRequestEmitter;

    @Produces(MediaType.TEXT_PLAIN)
    public String send(String analysisId) {
        analysisRequestEmitter.send(analysisId);
        return analysisId;
    }
}
