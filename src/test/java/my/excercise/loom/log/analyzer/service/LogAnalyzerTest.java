package my.excercise.loom.log.analyzer.service;

import my.excercise.loom.log.analyzer.data.LineCombination;
import my.excercise.loom.log.analyzer.data.LogAnalysisResult;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LogAnalyzerTest {

    private static final List<String> INPUT_LINES = Arrays.asList(
            "01-01-2012 19:45:00 Naomi is getting into the car",
            "01-01-2012 20:12:39 James is passing a restaurant",
            "01-01-2012 20:12:39 Naomi is passing a restaurant",
            "02-01-2012 09:13:15 George is getting into the car",
            "02-01-2012 10:05:15 Car with Naomi arrived at restaurant and left",
            "02-01-2012 09:15:12 Intersection camera registered a car with Naomi",
            "02-01-2012 10:00:32 Intersection camera registered a car with George",
            "02-01-2012 10:03:32 Inspector went for launch",
            "02-01-2012 10:05:15 Car with Naomi arrived at restaurant",
            "02-01-2012 10:07:32 Intersection camera registered a car with James",
            "02-01-2012 10:10:15 Car with George arrived at restaurant",
            "02-01-2012 10:14:00 George is eating at a diner",
            "03-01-2012 10:14:00 Naomi is eating at a diner");

    private LogAnalyzer analyzer = new LogAnalyzer();

    @Test
    public void testAnalyze() {
        LogAnalysisResult result = analyzer.analyse(INPUT_LINES);

        Set<String> refSetNaomiGeorge = new HashSet<>(Arrays.asList("Naomi", "George"));
        Set<String> refSetNaomiGeorgeJames = new HashSet<>(Arrays.asList("Naomi", "George", "James"));
        Set<String> refSetNaomiJames = new HashSet<>(Arrays.asList("Naomi", "James"));

        assertEquals(5, result.getLinePatterns().entrySet().size());
        assertEquals(refSetNaomiGeorge, result.getLinePatterns().get("is getting into the car").getChangingWords());
        assertEquals(refSetNaomiJames, result.getLinePatterns().get("is passing a restaurant").getChangingWords());
        assertEquals(refSetNaomiGeorgeJames, result.getLinePatterns().get("Intersection camera registered a car with").getChangingWords());
        assertEquals(refSetNaomiGeorge, result.getLinePatterns().get("Car with arrived at restaurant").getChangingWords());
        assertEquals(refSetNaomiGeorge, result.getLinePatterns().get("is eating at a diner").getChangingWords());
    }

    @Test
    public void getLineCombinations() throws Exception {
        String[] refInput = {"a", "b", "c", "d", "e", "f", "z", "y"};
        List<LineCombination> lineCombinations = analyzer.getLineCombinations(refInput, 3).collect(Collectors.toList());

        assertEquals(5, lineCombinations.size());
        Stream.of("e f z y", "d f z y", "d e z y", "d e f y", "d e f z")
                .forEach(comb -> assertTrue("No match for " + comb,
                        lineCombinations.stream().anyMatch(l -> l.getCombinedLine().equals(comb))));
    }
}