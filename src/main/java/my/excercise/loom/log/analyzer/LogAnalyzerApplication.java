package my.excercise.loom.log.analyzer;

import my.excercise.loom.log.analyzer.data.LogAnalysisResult;
import my.excercise.loom.log.analyzer.service.LogAnalyzer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogAnalyzerApplication {
    public static void main(String... args) throws IOException, URISyntaxException {
        Path inputFilePath;
        if (args.length == 0  || args[0].isEmpty()) {
            inputFilePath = Paths.get(LogAnalyzerApplication.class.getClassLoader().getResource("demo.log").toURI());
        } else {
            inputFilePath = Paths.get(args[0]);
        }

        try (Stream<String> logStream = Files.lines(inputFilePath)) {
            LogAnalyzer analyzer = new LogAnalyzer();
            LogAnalysisResult analysisResult = analyzer.analyse(logStream.collect(Collectors.toList()));

            analysisResult.getLinePatterns().forEach((key1, value1) -> {
                System.out.println("=====");
                value1.getEntries().forEach(System.out::println);
                System.out.println("The changing word was: " + value1.getChangingWords());
                System.out.println("\n\n");
            });
        }

    }
}
