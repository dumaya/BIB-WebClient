package dumaya.dev.BibWeb.service;

import dumaya.dev.BibWeb.modelAPI.Ouvrage;
import dumaya.dev.BibWeb.modelAPI.OuvrageRessource;
import feign.Headers;
import feign.Param;
import feign.RequestLine;


import java.util.List;

public interface OuvrageClient {
    @RequestLine("GET /{id}")
    OuvrageRessource findById(@Param("id") String id);

    @RequestLine("GET")
    List<OuvrageRessource> findAll();

    @RequestLine("POST")
    @Headers("Content-Type: application/json")
    void create(Ouvrage ouvrage);
}
