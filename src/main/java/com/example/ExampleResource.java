package com.example;

import com.example.service.PDFService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@Path("/hello")
@RequiredArgsConstructor
public class ExampleResource {
  private final PDFService pdfService;

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String hello() throws IOException {
    return pdfService.getTextFragmentFromPDF();
  }
}
