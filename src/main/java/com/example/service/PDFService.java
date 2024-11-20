package com.example.service;

import com.example.service.utils.PDFReader;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@RequiredArgsConstructor
@ApplicationScoped
public class PDFService {
  private static final Map<String, String> patterns;

  static {
    Map<String, String> map = new LinkedHashMap<>();
    map.put("[601_EV_start]", "[601_EV_end]");
    map.put("[601_cb1_start]", "[601_cb1_end]");
    map.put("[601_cb2_start]", "[601_cb2_end]");
    patterns = Collections.unmodifiableMap(map);
  }

  private final Map<String, String> translations = Map.of(
          "[601_EV_start]", "Eilvermerke",
          "[601_cb1_start]", "Checkbox 1",
          "[601_cb2_start]", "Checkbox 2"
  );
  public String getTextFragmentFromPDF() throws IOException {
    ClassLoader classLoader = getClass().getClassLoader();
    PDDocument document = PDDocument.load(classLoader.getResourceAsStream("document.pdf"));
    StringBuilder extraction = new StringBuilder();
    String extractedText;

    for (var entry : patterns.entrySet()) {
      PDFReader pdfReader = new PDFReader(entry.getKey(), entry.getValue());
      pdfReader.getText(document);
      extractedText = resolve(entry.getKey(), pdfReader.getExtractedText().replace("\n\n", "\n"));
      extraction.append(extraction.isEmpty() ? extractedText : "\n" + extractedText);
    }
    document.close();

    return extraction.toString();
  }

  private String resolve(String key, String text) {
    return translations.containsKey(key) ? translations.get(key) + ": " + text : text;
  }
}
