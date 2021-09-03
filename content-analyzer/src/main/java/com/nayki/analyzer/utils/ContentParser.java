package com.nayki.analyzer.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nayki.analyzer.exceptions.FileParseException;
import com.nayki.analyzer.models.*;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;

@Singleton
public class ContentParser {

    private final static Logger LOG = Logger.getLogger(ContentParser.class);
    public static final String ROW_ELEMENT = "row";
    public static final String ID_ATTRIBUTE = "Id";
    public static final String POST_TYPE_ID_ATTRIBUTE = "PostTypeId";
    public static final String SCORE_ATTRIBUTE = "Score";
    public static final String CREATION_DATE_ATTRIBUTE = "CreationDate";
    public static final String BODY_ATTRIBUTE = "Body";
    public static final String ACCEPTED_ANSWER_ID_ATTRIBUTE = "AcceptedAnswerId";
    public static final String PARENT_ID_ATTRIBUTE = "ParentId";

    @Inject
    CacheUtils cacheUtils;

    public ContentParser() {
    }

    public void parse(final String path, final Analysis analysis) throws FileNotFoundException, JsonProcessingException {
        try {
            LOG.debug(String.format("Parsing the file in path: %s for the analysis id: %s", path, analysis.getId()));

            AnalysisSummary summary = cacheUtils.get(analysis.getSummaryId(), AnalysisSummary.class);
            AnalysisDetail detail = cacheUtils.get(analysis.getDetailId(), AnalysisDetail.class);

            int totalScore = 0;
            int totalPostCount = 0;
            int uniquePostCount = 0;
            int totalAcceptedPosts = 0;
            LocalDateTime firstPost = null;
            LocalDateTime lastPost = null;
            long startTime = System.currentTimeMillis();

            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(path));
            while (reader.hasNext()) {
                XMLEvent nextEvent = reader.nextEvent();
                if (nextEvent.isStartElement()) {
                    final StartElement startElement = nextEvent.asStartElement();
                    if (startElement.getName().getLocalPart().equals(ROW_ELEMENT)) {
                        Attribute idAttribute = startElement.getAttributeByName(new QName(ID_ATTRIBUTE));
                        String id = idAttribute.getValue();
                        Attribute postTypeId = startElement.getAttributeByName(new QName(POST_TYPE_ID_ATTRIBUTE));
                        Attribute scoreAttribute = startElement.getAttributeByName(new QName(SCORE_ATTRIBUTE));
                        Attribute creationDateAttribute = startElement.getAttributeByName(new QName(CREATION_DATE_ATTRIBUTE));
                        //2016-01-12T23:05:23.980
                        LocalDateTime dateTime = LocalDateTime.parse(creationDateAttribute.getValue());

                        if (lastPost == null || dateTime.isAfter(lastPost)) {
                            lastPost = dateTime;
                        }

                        if (firstPost == null || dateTime.isBefore(firstPost)) {
                            firstPost = dateTime;
                        }

                        totalScore += Integer.parseInt(scoreAttribute.getValue());
                        totalPostCount++;
                        Attribute body = startElement.getAttributeByName(new QName(BODY_ATTRIBUTE));
                        if ("1".equals(postTypeId.getValue())) { //Questions
                            Post post = new Post();
                            post.setId(id);
                            post.setBody(body.getValue());
                            post.setScore(Integer.parseInt(scoreAttribute.getValue()));
                            detail.addPost(post);

                            Attribute acceptedAnswerId = startElement.getAttributeByName(new QName(ACCEPTED_ANSWER_ID_ATTRIBUTE));
                            if (acceptedAnswerId != null) {
                                totalAcceptedPosts++;
                            }
                        }

                        if ("2".equals(postTypeId.getValue())) { //Answer
                            Attribute parentIdAttribute = startElement.getAttributeByName(new QName(PARENT_ID_ATTRIBUTE));
                            detail.getPost(parentIdAttribute.getValue()).addChildBody(body.getValue());
                            uniquePostCount++;
                        }
                    }
                }
            }
            long endTime = System.currentTimeMillis();
            LOG.debug(String.format("Parsing the file in path: %s for the analysis id: %s: takes %s milliseconds", path, analysis.getId(), (endTime - startTime) / 1000));

            summary.setState(AnalysisState.FINISHED);
            summary.setDuration(endTime - startTime);
            summary.setAverageScore(totalScore / totalPostCount);
            summary.setTotalAcceptedPostCount(totalAcceptedPosts);
            summary.setLastPostDate(lastPost.toString());
            summary.setFirstPostDate(firstPost.toString());
            summary.setTotalPostCount(uniquePostCount);
            summary.setFailedSummary("");
            summary.setAnalyzeDate(LocalDateTime.now().toString());

            detail.setState(AnalysisState.FINISHED);
            detail.setFailedSummary("");

            cacheUtils.set(summary.getId(), summary);
            cacheUtils.set(detail.getId(), detail);

        } catch (XMLStreamException | FileNotFoundException e) {
            LOG.error(String.format("File with the name %s cannot be parsed properly for the analysis id: %s, error: %s", path, analysis.getId(), e));
            throw new FileParseException(path);
        }
//        }catch (JsonProcessingException exception){
//            LOG.error(String.format("Json processing for with the name %s cannot be parsed properly for the analysis id: %s", path, analysis.getId()));
//            throw new JsonProcessingExceptionInCache(path);
//        }
    }


}
