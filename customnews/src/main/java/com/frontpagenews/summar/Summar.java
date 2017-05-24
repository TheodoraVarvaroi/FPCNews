package com.frontpagenews.summar;

import com.frontpagenews.summar.summarizer.DocumentSummarizer;
import com.frontpagenews.summar.summarizer.KeywordExtractor;
import com.frontpagenews.summar.summarizer.SentencePreprocessor;
import com.frontpagenews.summar.summarizer.SentenceSegmenter;

/**
 * Created by Gabriel on 5/24/2017.
 */

public class Summar {
    private String summary;
    private String keywords;
    DocumentSummarizer summarizer;
    KeywordExtractor extractor;
    public Summar()
    {
        SentenceSegmenter seg = new SentenceSegmenter();
        SentencePreprocessor prep = new SentencePreprocessor();
        summarizer = new DocumentSummarizer(seg, prep);
        extractor = new KeywordExtractor(seg, prep);
    }
    public Summar(String content ,int percentage)
    {
        SentenceSegmenter seg = new SentenceSegmenter();
        SentencePreprocessor prep = new SentencePreprocessor();
        summarizer = new DocumentSummarizer(seg, prep);
        extractor = new KeywordExtractor(seg, prep);
        summary = summarizer.summarize(content, percentage);
        keywords = extractor.extract(summary);
    }
    public String getSummary()
    {
        return summary;
    }
    public String getKeywords()
    {
        return keywords;
    }
}
