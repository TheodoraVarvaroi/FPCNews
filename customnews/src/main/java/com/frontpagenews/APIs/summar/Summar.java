package com.frontpagenews.APIs.summar;

import com.frontpagenews.APIs.summar.summarizer.DocumentSummarizer;
import com.frontpagenews.APIs.summar.summarizer.KeywordExtractor;
import com.frontpagenews.APIs.summar.summarizer.SentencePreprocessor;
import com.frontpagenews.APIs.summar.summarizer.SentenceSegmenter;

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
    public Summar(String content)
    {
        SentenceSegmenter seg = new SentenceSegmenter();
        SentencePreprocessor prep = new SentencePreprocessor();
        summarizer = new DocumentSummarizer(seg, prep);
        extractor = new KeywordExtractor(seg, prep);
        int percentage=99;
        do{
        summary = summarizer.summarize(content, percentage);
            percentage=percentage-3;
        }while(summary.length()>400&&percentage>10);
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
