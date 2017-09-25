package my.excercise.loom.log.analyzer.data;

public class LineCombination {
    private final String combinedLine;
    private final String excludedWord;

    public LineCombination(String combinedLine, String excludedWord) {

        this.combinedLine = combinedLine;
        this.excludedWord = excludedWord;
    }

    public String getCombinedLine() {
        return combinedLine;
    }

    public String getExcludedWord() {
        return excludedWord;
    }
}
