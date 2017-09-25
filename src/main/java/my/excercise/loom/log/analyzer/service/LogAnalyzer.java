package my.excercise.loom.log.analyzer.service;

import my.excercise.loom.log.analyzer.data.LineCombination;
import my.excercise.loom.log.analyzer.data.LogAnalysisResult;
import my.excercise.loom.log.analyzer.data.LogPattern;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LogAnalyzer {
    private Map<String, LogPattern> repeatingPatterns = new HashMap<>();
    private List<String[]> unMatchedLines = new ArrayList<>();

    public LogAnalysisResult analyse(List<String> logInput) {
        logInput.stream()
                .map(line -> line.split(" "))
                .forEach(split -> {
                    if (!tryAddToExistingPattern(split))  {
                        if (!tryAddNewPattern(split)) {
                            tryAddToRawLines(split);
                        }
                    }
                });

        return new LogAnalysisResult(repeatingPatterns);
    }

    private void tryAddToRawLines(String[] split) {
        unMatchedLines.add(split);
    }

    private boolean tryAddToExistingPattern(String[] split) {
        LineCombination matchingCombination = getLineCombinations(split, 2)
                .filter(combination -> repeatingPatterns.get(combination.getCombinedLine()) != null)
                .findAny().orElse(null);

        if (matchingCombination == null) return false;

        repeatingPatterns.get(matchingCombination.getCombinedLine()).getChangingWords().add(matchingCombination.getExcludedWord());
        return true;
    }

    public boolean tryAddNewPattern(String[] split) {
        for (String[] candidate: unMatchedLines) {
            if (candidate.length != split.length) continue;

            List<Integer> diffs = IntStream.range(2, split.length).filter(i -> !split[i].equals(candidate[i])).boxed().collect(Collectors.toList());
            if (diffs.size() == 1) {
                HashSet<String> changingWords = new HashSet<>();
                Integer changingWordIndex = diffs.get(0);
                changingWords.add(split[changingWordIndex]);
                changingWords.add(candidate[changingWordIndex]);

                repeatingPatterns.put(
                        IntStream.range(2, split.length).filter(i -> i != changingWordIndex).mapToObj(i -> split[i]).collect(Collectors.joining(" ")),
                        new LogPattern()
                                .setChangingWords(changingWords)
                                .setEntries(new ArrayList<>(Arrays.asList(
                                        String.join(" ", candidate),
                                        String.join(" ", split)))));
                unMatchedLines.remove(candidate);
                return true;
            }
        }
        return false;
    }

    public Stream<LineCombination> getLineCombinations(String[] split, int starting) {
        return IntStream.range(starting, split.length)
                .mapToObj(wordIndex -> {
                    String combinedLine = IntStream.range(starting, split.length)
                            .filter(i -> i != wordIndex)
                            .mapToObj(i -> split[i])
                            .collect(Collectors.joining(" "));
                    return new LineCombination(combinedLine, split[wordIndex]);
                });
    }

}
