package org.raboss.dev.atlassian.jira.proman.api.rest;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.transaction.TransactionCallback;
import net.java.ao.Query;
import org.codehaus.jackson.annotate.*;
import org.raboss.dev.atlassian.jira.proman.api.EvaluationCriterion;
import org.raboss.dev.atlassian.jira.proman.api.EvaluationCriterionInterface;
import org.raboss.dev.atlassian.jira.proman.api.rest.hal.*;
import org.raboss.dev.atlassian.jira.proman.api.rest.hal.custom.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class EvaluationCriteriaLinkRelation extends org.raboss.dev.atlassian.jira.proman.api.rest.hal.custom.LinkRelation {
    @JsonUnwrapped
    public Pagination pagination;

    public EvaluationCriteriaLinkRelation setPagination(final Pagination pagination) {
        this.pagination = pagination;
        return this;
    }
}

/**
 * RESTful representation of evaluation criteria.
 * @version 1
 */
@Path("/evaluationcriteria")
public class EvaluationCriteria {
    static final public String RESTCONTEXTPATH = "/rest/proman/1.0/evaluationcriteria";

    @ComponentImport
    protected final ActiveObjects activeObjects;
    @Context
    private UriInfo uriInfo;
    @Context
    private HttpServletRequest httpRequest;

    /**
     * Hypertext Transfer Protocol (HTTP/1.1): Semantics and Content, 300 Multiple Choices
     * @see {@link http://tools.ietf.org/html/rfc7231#section-6.4.1}
      */
    static public final int HTTP_CODE_300_MULTIPLE_CHOICES = 300;

    static final private Logger log;
    static {
        log = LoggerFactory.getLogger(EvaluationCriteria.class);
    }

    @Inject
    public EvaluationCriteria(final ActiveObjects activeObjects) {
        log.debug("EvaluationCriteria() constructor start");
        this.activeObjects = activeObjects;
        log.debug("EvaluationCriteria() constructor done");
    }

    /**
     * Query an evaluation criterion by it's 'id'
     * @param id              The evaluation criterion to query for (non for all evaluation criteria)
     * @param ifNoneMatch     HTTP header 'If-None-Match' condition prevent retrieving any body if all evaluation criteria have the same hashCode()
     * @param ifModifiedSince HTTP header "If-Modified-Since" is set, only evaluation criteria that are newer are retrieved (NOT SUPPORTED)
     * @return Content of the evaluation criterion
     * @response.representation.200.doc OK returned if the 'id' exists
     * @response.representation.304.doc NOT_MODIFIED returned if the 'id' exists and ETag match If-None-Match single entity tag
     * @response.representation.403.doc FORBIDDEN returned if the currently authenticated user does not have permission to query the evaluation criterion
     * @response.representation.404.doc NOT_FOUND returned if the evaluation criterion does not exist or the currently authenticated user does not have permission to
     */
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getEvaluationCriterion(@PathParam("id") String id, @HeaderParam(HttpHeaders.IF_NONE_MATCH) final String ifNoneMatch, @HeaderParam(HttpHeaders.IF_MODIFIED_SINCE) final String ifModifiedSince) {
        log.debug("getEvaluationCriterion(): id={}", id);
        try {
            final EvaluationCriterion ec = new EvaluationCriterion(activeObjects.get(org.raboss.dev.atlassian.jira.proman.entity.EvaluationCriterion.class, Integer.valueOf(id)));
            log.debug("ifNoneMatch={}, if-none-match={}, id={}, name={}, weighting={}", ec.hashCode(), ifNoneMatch, ec.getName(), ec.getWeighting().toString());
            List<String> etaglist = HttpTool.parseEtagValue(ifNoneMatch);
            if (etaglist == null || (ifNoneMatch.contains("*") == false && ifNoneMatch.contains(String.valueOf(ec.hashCode())) == false) || (ifNoneMatch.contains("*") && ifModifiedSince != null)) {
                ec.setLinkRelation(new EvaluationCriteriaLinkRelation(){{
                    setSelfLinkRelation(new LinkObject(){{setHref(httpRequest.getRequestURI());}});
                }});
                return Response.ok(ec).header(HttpHeaders.ETAG, String.valueOf(ec.hashCode())).build();
            }
            return Response.status(Response.Status.NOT_MODIFIED).header(HttpHeaders.ETAG, String.format("\"%d\"", ec.hashCode())).build();
        }
        catch (Exception ex) {
            log.debug("getEvaluationCriterion() exception: {}", ex.toString());
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    /**
     * Query all evaluation criteria with pagination support
     * This implementation is based on the concept of HTTP/1.1 <pre>300 Multiple choices</pre> as described in
     * <ul>
     * <li>Hypertext Transfer Protocol (HTTP/1.1): Semantics and Content 300 Multiple Choices {@link http://tools.ietf.org/html/rfc7231#section-6.4.1}</li>
     * <li>Web linking Relation Type {@link http://tools.ietf.org/html/rfc5988#section-5.3}</li>
     * </ul>
     * Using the Link: HTTP header, you will get information about:
     * <ul>
     * <li><pre>URI;rel="start"</pre>: How to receiving the first elements</li>
     * <li><pre>URI;rel="previous"</pre>: How to get the previous elements</li>
     * <li><pre>URI;rel="next"</pre>: Where to get the next set of elements</li>
     * </ul>
     * @param limit  Reduce the number of max received elements (default 25)
     * @param offset Starting point for the requested elements
     * @return The first evaluation criteria, or those which are selected by offset/limit
     * @response.representation.200.doc OK returned if all evaluation criteria are part of the body
     * @response.representation.300.doc MULTIPLE_CHOICES returned if the result body does not represent all existing evaluation criteria (pagination support)
     * @response.representation.403.doc FORBIDDEN returned if the currently authenticated user does not have permission to query the evaluation criterion
     * @response.representation.404.doc NOT_FOUND returned if the evaluation criterion does not exist or the currently authenticated user does not have permission to
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getEvaluationCriterion(@HeaderParam(HttpHeaders.IF_NONE_MATCH) final String ifNoneMatch, @HeaderParam(HttpHeaders.IF_MODIFIED_SINCE) final String ifModifiedSince, @QueryParam("limit") final String limit, @QueryParam("offset") final String offset, @QueryParam("orderby") final List<String> orderby, @QueryParam("filter") final List<String> filter, @QueryParam("filterin") final List<String> filterin) {
        log.debug("getEvaluationCriterion(): all");
        final List<EvaluationCriterion> ecs = new ArrayList<>();
        final Integer dolimit = limit == null ? 25 : Integer.valueOf(limit);
        final Integer dooffset = offset == null ? 0 : Integer.valueOf(offset);
        UriBuilder uri = UriBuilder.fromUri(URI.create(httpRequest.getRequestURI()).getPath());
        UriBuilder urishort = uri.clone();
        final ArrayList<ElementSortInformation> orderbyinfo = new ArrayList<>();
        Query q = Query.select();
        String orderbyQuery;
        if (filter.size() != 0) {
            uri.queryParam("filter", filter.get(0));
            String whereClause = new String();
            ArrayList<Object> param = new ArrayList<>(2);
            for(final String f : filterin) {
                final String fieldname = f.toLowerCase();
                if ((fieldname.equals("name") || fieldname.equals("weighting") || fieldname.equals("comment") || fieldname.equals("typeofindex") || fieldname.equals("bignumberisbetter") || fieldname.equals("typeofindexdescription")) == false) {
                    continue;
                }
                if (whereClause.isEmpty() == false) {
                    whereClause += " OR ";
                }
                whereClause += String.format("UPPER(\"%s\") LIKE ?", f.toUpperCase());
                param.add("%" + filter.get(0).toUpperCase() + "%");
                uri.queryParam("filterin", f.toLowerCase());
            }
            log.debug("getEvaluationCriterion(): filter={} where={}", filter.get(0), whereClause);
            q.setWhereClause(whereClause);
            q.setWhereParams(param.toArray());
        }
        // Identify counts for pagination information before defining sort order
        // otherwise we run in SQL query issues while counting.
        final int max = activeObjects.count(org.raboss.dev.atlassian.jira.proman.entity.EvaluationCriterion.class);
        final int count = activeObjects.count(org.raboss.dev.atlassian.jira.proman.entity.EvaluationCriterion.class, q);
        if (orderby.size() == 0) {
            q.order("NAME ASC");
            orderbyinfo.add(new ElementSortInformation(){{setKey("name").setSortOrder(TypeOfSortOrder.ASC);}});
        }
        else {
            for (final String element: orderby) {
                int i = 0;
                String fieldname = "";
                ElementSortInformation.TypeOfSortOrder sortorder = ElementSortInformation.TypeOfSortOrder.ASC;
                for(String s : element.split("\\s+")) {
                    if (s.isEmpty())
                        continue;
                    if (i++ == 0) {
                        fieldname = s.toLowerCase();
                        if ((fieldname.equals("name") || fieldname.equals("weighting") || fieldname.equals("comment") || fieldname.equals("typeofindex") || fieldname.equals("bignumberisbetter") || fieldname.equals("typeofindexdescription")) == false) {
                            fieldname = "";
                            break;
                        }
                    }
                    else {
                        if (s.toUpperCase().equals("DESC"))
                            sortorder = ElementSortInformation.TypeOfSortOrder.DESC;
                        break;
                    }
                }
                if (fieldname.isEmpty() == false) {
                    uri.queryParam("orderby", fieldname + " " + sortorder.toString());
                    orderbyQuery = fieldname.toUpperCase() + (sortorder == ElementSortInformation.TypeOfSortOrder.ASC ? " ASC" : " DESC");
                    q.order(orderbyQuery);
                    log.debug("getEvaluationCriterion(): orderby={}", orderbyQuery);
                    final ElementSortInformation.TypeOfSortOrder finalSortorder = sortorder;
                    final String finalFieldname = fieldname;
                    orderbyinfo.add(new ElementSortInformation() {{
                        setKey(finalFieldname.toLowerCase()).setSortOrder(finalSortorder);
                    }});
                }
            }
        }
        try {
            for (final org.raboss.dev.atlassian.jira.proman.entity.EvaluationCriterion ecentity : activeObjects.find(org.raboss.dev.atlassian.jira.proman.entity.EvaluationCriterion.class, q.offset(dooffset).limit(dolimit))) {
                try {
                    EvaluationCriterion ec = new EvaluationCriterion(ecentity);
                    final String etag = String.valueOf(ec.hashCode());
                    ec.setETagResource(new ETagResourceObject(){{
                        setOpaqueTag(etag);
                    }});
                    final UriBuilder href = urishort.clone().segment(String.valueOf(ecentity.getID()));
                    ec.setLinkRelation(new EvaluationCriteriaLinkRelation(){{
                        setSelfLinkRelation(new LinkObject(){{setHref(href.build().toString());}});
                    }});
                    ecs.add(ec);
                }
                catch (java.lang.NullPointerException e) {
                    log.debug("getEvaluationCriterion(): ID={} invalid, delete now", ecentity.getID());
                    activeObjects.delete(ecentity);
                }
            }
            final UriBuilder hrefself = uri.clone().queryParam("offset", dooffset).queryParam("limit", dolimit);
            ResourceObject ro = new ResourceObject<>();
            // TODO: Change to URI, "raboss:evaluationcriteria" -- also in JavaScript
            ro.setResourceObjects(new HashMap<String, List<EvaluationCriterion>>(){{put("evaluationcriteria", ecs);}});
            EvaluationCriteriaLinkRelation lo = new EvaluationCriteriaLinkRelation();
            ro.setLinkRelation(lo);
            lo.setSelfLinkRelation(new LinkObject(){{setHref(hrefself.build().toString());}});
            Response.ResponseBuilder response = Response.ok(ro).header("Link", "<" + hrefself.build().toString() + ">;rel=\"self\"");
            if (ecs.isEmpty() == false || dooffset > 0) {
                final int maxpages = count / dolimit + (count % dolimit == 0 ? 0 : 1);
                log.debug("getEvaluationCriterion(): href={}", hrefself.build().toString());
                lo.setPagination(new Pagination(){{
                    setPages(new ArrayList<PaginationLinkObject>(){{
                        add(new PaginationLinkObject(){{
                            setOffset(dooffset).setLimit(dolimit).setCount(count).setMaximum(max).setOrderBy(orderbyinfo).setHref(hrefself.build().toString()).setName("raboss:self");
                        }});
                    }});
                }});
                // Offer the start link only if the previous link does not cover the 0 offset
                if (dooffset > dolimit) {
                    // rel="start"
                    final UriBuilder uristart = uri.clone().queryParam("limit", dolimit);
                    response.header("Link", "<" + uristart.build().toString() + ">;rel=\"start\"");
                    lo.setStartLinkRelation(new LinkObject() {{
                        setHref(uristart.build().toString());
                    }});
                }
                if (dooffset > 0) {
                    final UriBuilder uriprev = uri.clone().queryParam("limit", dolimit);
                    if (dooffset > dolimit)
                        uriprev.queryParam("offset", dooffset - dolimit);
                    response.status(HTTP_CODE_300_MULTIPLE_CHOICES).header("Link", "<" + uriprev.build().toString() + ">;rel=\"previous\"");
                    lo.setPreviousLinkRelation(new LinkObject(){{setHref(uriprev.build().toString());}});
                }
                if (count > dooffset + dolimit) {
                    final UriBuilder urinext = uri.clone().queryParam("offset", dooffset + dolimit).queryParam("limit", dolimit);
                    response.header("Link", "<" + urinext.build().toString() + ">;rel=\"next\"");
                    lo.setNextLinkRelation(new LinkObject() {{
                        setHref(urinext.build().toString());
                    }});
                    if (count > dooffset + dolimit * 2) {
                        final UriBuilder urilast = uri.clone().queryParam("offset", (maxpages - 1) * dolimit).queryParam("limit", dolimit);
                        response.header("Link", "<" + urilast.build().toString() + ">;rel=\"last\"");
                        lo.setLastLinkRelation(new LinkObject() {{
                            setHref(urilast.build().toString());
                        }});
                    }
                }
                for (EvaluationCriterionInterface ec : ecs) {
                    response.header(HttpHeaders.ETAG, String.format("\"%d\"", ec.hashCode()));
                }
            }
            return response.build();
        }
        catch (Exception ex) {
            log.debug("getEvaluationCriterion() exception: {}", ex.toString());
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    /**
     * Update an existing evaluation criterion by 'id'
     * @param id      Identifies an existing evaluation criterion to be updated
     * @param ifMatch HTTP header 'If-Match' condition prevent changing the current evaluation criterion, if the hashCode() differ
     * @param ec      Evaluation criterion data to be stored for the 'id'
     * @response.representation.200.doc OK returned if the 'id' updated successful, an the content changed; body contains the new content if 'id'
     * @response.representation.204.doc NO_CONTENT returned if the 'id' updated successful and the content of 'id' did not changed
     * @response.representation.403.doc FORBIDDEN returned if the currently authenticated user does not have permission to query the evaluation criterion
     * @response.representation.404.doc NOT_FOUND returned if the 'id' does not exist or the currently authenticated user does not have permission to
     * @response.representation.409.doc CONFLICT returned for a PUT that is unsuccessful due to a 3rd-party modification, with a list of differences between the attempted update and the current resource in the response body. (RFC 2616 Section 10.4.10)
     * @response.representation.412.doc PRECONDITION_FAILED returned for a PUT if non of the If-Match tags match the current 'id'
     */
    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response setEvaluationCriterion(@PathParam("id") final String id, @HeaderParam(HttpHeaders.IF_MATCH) final String ifMatch, final EvaluationCriterion ec) {
        return this.modifyEvaluationCriterion(id, ifMatch, ec);
    }

    /**
     * Delete an existing evaluation criterion by 'id'
     * @param id      Identifies an existing evaluation criterion to be deleted
     * @param ifMatch HTTP header 'If-Match' condition prevent deleting the current evaluation criterion, if the hashCode() differ
     * @response.representation.200.doc OK returned if the 'id' deleted successful
     * @response.representation.403.doc FORBIDDEN returned if the currently authenticated user does not have permission to query the evaluation criterion
     * @response.representation.404.doc NOT_FOUND returned if the 'id' does not exist or the currently authenticated user does not have permission to
     * @response.representation.412.doc PRECONDITION_FAILED returned for a DELETE if non of the If-Match tags match the current 'id'
     */
    @DELETE
    @Path("/{id}")
    public Response deleteEvaluationCriterion(@PathParam("id") final String id, @HeaderParam(HttpHeaders.IF_MATCH) final String ifMatch) {
        return this.modifyEvaluationCriterion(id, ifMatch, null);
    }

    /**
     * Helper function for PUT and DELETE which is in general the same from
     * processing perspective. Only the last action UPDATE/DELETE differ.
     * @param id      Identifies the evaluation criterion
     * @param ifMatch HTTP header 'If-Match' condition prevent updating/deleting the current evaluation criterion, if the hashCode() differ
     * @param ec      If null, an DELETE will be executed, otherwise an PUT.
     * @return HTTP response
     */
    private Response modifyEvaluationCriterion(final String id, final String ifMatch, final EvaluationCriterion ec) {
        try {
            return this.activeObjects.executeInTransaction(new TransactionCallback<Response>() {
                @Override
                public Response doInTransaction() {
                    org.raboss.dev.atlassian.jira.proman.entity.EvaluationCriterion ecentity = activeObjects.get(org.raboss.dev.atlassian.jira.proman.entity.EvaluationCriterion.class, Integer.valueOf(id));
                    if (ecentity != null) {
                        EvaluationCriterion targetec = new EvaluationCriterion(ecentity);
                        log.debug("modifyEvaluationCriterion(): delete={} If-Match={}, ETag={}", ec == null ? "delete" : "modify", ifMatch, targetec.hashCode());
                        List<String> etaglist = HttpTool.parseEtagValue(ifMatch);
                        if (etaglist == null || etaglist.contains("*") || etaglist.contains(String.valueOf(targetec.hashCode()))) {
                            if (ec == null) {
                                activeObjects.delete(ecentity);
                                return Response.status(Response.Status.OK).build();
                            }
                            else {
                                EvaluationCriterion.set(ec, ecentity);
                                ecentity.save();
                                return Response.status(Response.Status.NO_CONTENT).header(HttpHeaders.ETAG, String.valueOf(targetec.hashCode())).build();
                            }
                        }
                        return Response.status(Response.Status.PRECONDITION_FAILED).build();
                    } else {
                        log.debug("modifyEvaluationCriterion(): delete={} id={} does not exists", ec == null ? "delete" : "modify", id);
                        return Response.status(Response.Status.NOT_FOUND).build();
                    }
                }
            });
        } catch (Exception ex) {
            log.debug("modifyEvaluationCriterion() exception: {}", ex.toString());
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    /**
     * Create a new evaluation criterion
     * @param ecnew The new evaluation criterion to be created
     * @response.representation.200.doc OK returned if the evaluation criterion could be created with the new body
     * @response.representation.403.doc FORBIDDEN returned if the currently authenticated user does not have permission to query the evaluation criterion
     * @response.representation.409.doc CONFLICT returned for a POST that is unsuccessful due to a 3rd-party modification
     */
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createEvaluationCriterion(final EvaluationCriterion ecnew) {
        try {
            final String requestpath = URI.create(httpRequest.getRequestURI()).getPath();
            return activeObjects.executeInTransaction(new TransactionCallback<Response>() {
                @Override
                public Response doInTransaction() {
                    final org.raboss.dev.atlassian.jira.proman.entity.EvaluationCriterion ecentity = activeObjects.create(org.raboss.dev.atlassian.jira.proman.entity.EvaluationCriterion.class);
                    EvaluationCriterion.set(ecnew, ecentity);
                    ecentity.save();
                    EvaluationCriterion ecsaved = new EvaluationCriterion(ecentity);
                    ecsaved.setLinkRelation(new EvaluationCriteriaLinkRelation(){{
                        setSelfLinkRelation(new LinkObject(){{String.format("%s/%d", requestpath, ecentity.getID());}});
                    }});
                    return Response.ok(ecsaved).header(HttpHeaders.ETAG, String.valueOf(ecsaved.hashCode())).build();
                }
            });
        } catch (Exception ex) {
            log.debug("modifyEvaluationCriterion() exception: {}", ex.toString());
            return Response.status(Response.Status.CONFLICT).build();
        }
    }
}
