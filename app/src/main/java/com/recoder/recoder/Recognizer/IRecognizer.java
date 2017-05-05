package com.recoder.recoder.Recognizer;

import java.io.File;
import java.io.IOException;

/**
 * Created by Роман on 21.03.2017.
 */

public interface IRecognizer {
    public GoogleResponse getRecognizedDataForAmr(File amrFile) throws IOException;

    public GoogleResponse getRecognizedDataForAmr(File amrFile, int maxResults) throws IOException;

    public GoogleResponse getRecognizedDataForAmr(File amrFile, int maxResults, int sampleRate) throws IOException;
}
