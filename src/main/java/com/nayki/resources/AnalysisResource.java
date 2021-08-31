package com.nayki.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nayki.exceptions.DeleteFromCacheException;
import com.nayki.exceptions.NotFoundInCacheException;
import com.nayki.models.Analysis;
import com.nayki.models.AnalysisDetail;
import com.nayki.models.AnalysisSummary;
import com.nayki.services.AnalysisService;
import com.nayki.services.DetailService;
import com.nayki.services.SummaryService;
import com.nayki.utils.SerializationUtil;
import com.nayki.utils.StringUtils;
import com.nayki.utils.URLUtils;
import org.jboss.logging.Logger;
import org.jboss.resteasy.specimpl.ResponseBuilderImpl;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("analyses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AnalysisResource {

    private final static Logger LOG = Logger.getLogger(AnalysisResource.class);

    @Inject
    AnalysisService analysisService;

    @Inject
    SummaryService summaryService;

    @Inject
    DetailService detailService;


    @POST
    public Response create(AnalysisRequestBody body) {

        if (StringUtils.isBlank(body.url)) {
            return buildResponse(Response.Status.BAD_REQUEST, "{\"message\": \"Validation failed. URL cannot be empty\"}");
        }

        if (!URLUtils.validate(body.url)) {
            return buildResponse(Response.Status.BAD_REQUEST, "{\"message\": \"Validation failed. Invalid URL was provided\"}");
        }

        try {
            final String id = analysisService.createAnalysis(body.url);
            return buildResponse(Response.Status.ACCEPTED, new SerializationUtil().toJson(new AnalysisResponseBody(id)));

        } catch (JsonProcessingException e) {
            return buildResponse(Response.Status.INTERNAL_SERVER_ERROR, "{\"message\": \"Creation of the analysis was failed.\"}");
        } catch (NotFoundException e) {
            return buildResponse(Response.Status.INTERNAL_SERVER_ERROR, String.format("{\"message\": \"%s\"}", e.getMessage()));
        }
    }


    @GET
    @Path("analyses/{id}/summary")
    public Response getSummary(@PathParam("id") String id) {
        Response response;
        if (StringUtils.isBlank(id)) {
            response = buildResponse(Response.Status.BAD_REQUEST, "{\"message\": \"Validation failed. Id cannot be empty\"}");
            return response;
        }

        try {
            final Analysis analysis = analysisService.get(id);
            final AnalysisSummary summary = summaryService.get(analysis.getSummaryId());
            response = buildResponse(Response.Status.ACCEPTED, new SerializationUtil().toJson(summary));
        } catch (JsonProcessingException e) {
            LOG.error(String.format("Error was occurred while creating response body in the get summary endpoint call; The analysis id: %s", id));
            response = buildResponse(Response.Status.INTERNAL_SERVER_ERROR, "{\"message\": \"Error was occurred while creating response body\"}");
        } catch (NotFoundInCacheException e) {
            response = buildResponse(Response.Status.NOT_FOUND, String.format("{\"message\": \"%s\"}", e.getMessage()));
        }
        return response;

    }

    @GET
    @Path("analyses/{id}/detail")
    public Response getDetail(@PathParam("id") String id) {
        Response response;
        if (StringUtils.isBlank(id)) {
            response = buildResponse(Response.Status.BAD_REQUEST, "{\"message\": \"Validation failed. Id cannot be empty\"}");
            return response;
        }

        try {
            final Analysis analysis = analysisService.get(id);
            final AnalysisDetail detail = detailService.get(analysis.getSummaryId());
            response = buildResponse(Response.Status.ACCEPTED, new SerializationUtil().toJson(detail));
        } catch (JsonProcessingException e) {
            LOG.error(String.format("Error was occurred while creating response body in the get detail endpoint call; The analysis id: %s", id));
            response = buildResponse(Response.Status.INTERNAL_SERVER_ERROR, "{\"message\": \"Error was occurred while creating response body\"}");
        } catch (NotFoundInCacheException e) {
            response = buildResponse(Response.Status.NOT_FOUND, String.format("{\"message\": \"%s\"}", e.getMessage()));
        }

        return response;
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") String id) throws JsonProcessingException {
        Response response;
        if (StringUtils.isBlank(id)) {
            return buildResponse(Response.Status.BAD_REQUEST, "{\"message\": \"Validation failed. Id cannot be empty\"}");
        }

        try {
            final Analysis analysis = analysisService.get(id);
            analysisService.delete(analysis.getId());
            summaryService.delete(analysis.getSummaryId());
            detailService.delete(analysis.getDetailId());
        } catch (NotFoundInCacheException e) {
            return buildResponse(Response.Status.NOT_FOUND, String.format("{\"message\": \"%s\"}", e.getMessage()));
        } catch (DeleteFromCacheException e) {
            return buildResponse(Response.Status.INTERNAL_SERVER_ERROR, String.format("{\"message\": \"%s\"}", e.getMessage()));
        }

        return buildResponse(Response.Status.ACCEPTED, "");
    }

    private Response buildResponse(Response.Status status, String value) {
        return new ResponseBuilderImpl()
                .status(status)
                .entity(value)
                .build();
    }

    static class AnalysisRequestBody {
        String url;

        public AnalysisRequestBody() {
        }

        public AnalysisRequestBody(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    static class AnalysisResponseBody {
        String id;

        public AnalysisResponseBody() {
        }

        public AnalysisResponseBody(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
