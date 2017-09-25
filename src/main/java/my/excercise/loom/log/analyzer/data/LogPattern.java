package my.excercise.loom.log.analyzer.data;

import java.util.List;
import java.util.Set;

public class LogPattern {
    private Set<String> changingWords;
    private List<String> entries;

    public Set<String> getChangingWords() {
        return changingWords;
    }

    public LogPattern setChangingWords(Set<String> changingWords) {
        this.changingWords = changingWords;
        return this;
    }

    public List<String> getEntries() {
        return entries;
    }

    public LogPattern setEntries(List<String> entries) {
        this.entries = entries;
        return this;
    }

}
