package com.example.service.utils;

import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.io.IOException;
import java.util.List;

public class PDFReader extends PDFTextStripper {
  private final String startPattern;
  private final String endPattern;
  private final StringBuilder extractedText = new StringBuilder();
  private boolean withinRange = false;
  private boolean foundStart = false;

  public PDFReader(String startPattern, String endPattern) throws IOException {
    this.startPattern = startPattern;
    this.endPattern = endPattern;
  }

  @Override
  protected void writeString(String text, List<TextPosition> textPositions) throws IOException {
    if (text.contains(endPattern)) {
      withinRange = false;
    }

    if (withinRange && foundStart) {
      extractedText.append(text);
    }

    if (text.contains(startPattern)) {
      withinRange = true;
      foundStart = true;
    }
  }

  public String getExtractedText() {
    return extractedText.toString();
  }
}
