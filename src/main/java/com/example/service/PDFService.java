package com.example.service;

import com.example.service.utils.PDFReader;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;

@RequiredArgsConstructor
@ApplicationScoped
public class PDFService {

  public String getTextFragmentFromPDF() throws IOException {
    ClassLoader classLoader = getClass().getClassLoader();
    PDDocument document = PDDocument.load(classLoader.getResourceAsStream("document.pdf"));
    PDFReader pdfReader = new PDFReader("Überschrift 1", "Überschrift 2");
    pdfReader.getText(document);
    String textFragment = pdfReader.getExtractedText();
    document.close();

    return textFragment;
  }
}
