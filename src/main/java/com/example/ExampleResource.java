package com.example;

import io.quarkus.vertx.web.ReactiveRoutes;
import io.quarkus.vertx.web.Route;
import io.smallrye.mutiny.Multi;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.annotations.SseElementType;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.Duration;


@Slf4j
@Path("")
public class ExampleResource {

    @GET
    @Path("/serialized")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @SseElementType(MediaType.APPLICATION_JSON)
    public Multi<SearchResult> getSrStream() {
        log.info("serialized run");
        return Multi.createFrom().ticks().every(Duration.ofSeconds(2))
                .onItem().transform(n -> new SearchResult(n.intValue()));
    }


    @GET
    @Path("/simple")
    @Produces(MediaType.TEXT_PLAIN)
    public Multi<String> getStringStreamSimple() {
        log.info("string simple run");
        return Multi.createFrom().ticks().every(Duration.ofSeconds(1))
                .transform().byTakingFirstItems(5)
                .onItem().transform(n -> "String simple result " + n);
    }

    @GET
    @Path("/string")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @SseElementType(MediaType.TEXT_PLAIN)
    public Multi<String> getStringStream() {
        log.info("string run");
        return Multi.createFrom().ticks().every(Duration.ofSeconds(1)).transform().byTakingFirstItems(5)
                .onItem().transform(n -> "String result " + n);

//        return Multi.createFrom().emitter(emitter -> {
//            IntStream.range(1, 10).forEach(i -> emitter.emit("Value: " + i));
//            emitter.complete();
//        });
    }


    @Route(path = "/routed", methods = HttpMethod.GET)
    public Multi<SearchResult> getSrStreamRouted(RoutingContext context) {
        log.info("routed run");
        return ReactiveRoutes.asEventStream(Multi.createFrom().ticks().every(Duration.ofSeconds(1))
                .onItem().transform(n -> new SearchResult(n.intValue())));
    }

}