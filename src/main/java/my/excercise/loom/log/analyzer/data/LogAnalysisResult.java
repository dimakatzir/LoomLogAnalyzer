package my.excercise.loom.log.analyzer.data;

import java.util.HashMap;
import java.util.Map;

public class LogAnalysisResult {
    private Map<String, LogPattern> linePatterns = new HashMap<>();

    public LogAnalysisResult(Map<String, LogPattern> linePatterns) {
        this.linePatterns = linePatterns;
    }

    public Map<String, LogPattern> getLinePatterns() {
        return linePatterns;
    }
}
