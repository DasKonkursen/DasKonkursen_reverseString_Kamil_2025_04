package com.lhsystems;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class StringReverser {
    public String reverse(String input) {
        final var directioryName = "calculation" + ThreadLocalRandom.current().nextInt();
        try {
            Files.createDirectories(Path.of(directioryName));
        } catch (Exception e) {
        }

        final var toSave = extractEmojis(input);

        final List<Thread> threads = new ArrayList<>();

        for (int k = toSave.size(), i = 0; i < k  ; i++) {
            final int finalI = i;
            final var thread = new Thread(() -> {
                try (final var writer = new OutputStreamWriter(new FileOutputStream(directioryName + "/output-" + ThreadLocalRandom.current().nextInt()), StandardCharsets.UTF_8)) {
                    Thread.sleep(finalI * 100L);
                    writer.write(toSave.removeFirst());
                } catch (Exception e) {}
            });
            threads.add(thread);
            thread.start();
        }

        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        var result = "";
        while(areFilesPresent(directioryName)) {
            result += readAndDeleteNewestFile(directioryName);
        }

        try {
            Files.delete(Path.of(directioryName));
        } catch (Exception e) {
        }

        return result;
    }

    private String readAndDeleteNewestFile(final String directoryName) {
        try {
            Optional<Path> newestFile = Files.list(Path.of(directoryName))
                .filter(Files::isRegularFile)
                .max(Comparator.comparingLong(p -> p.toFile().lastModified()));

            if (newestFile.isPresent()) {
                byte[] bytes = Files.readAllBytes(newestFile.get());
                Files.delete(newestFile.get());
                return new String(bytes, "UTF-8");
            }
        } catch (IOException e) {
        }
        return "";
    }

    private boolean areFilesPresent(String directoryName) {
        try {
            return Files.list(Path.of(directoryName))
                .filter(Files::isRegularFile)
                .findAny()
                .isPresent();
        } catch (IOException e) {
            return false;
        }
    }

    private List<String> extractEmojis(final String text) {
        List<String> emojis = new ArrayList<>();
        BreakIterator iterator = BreakIterator.getCharacterInstance(Locale.ROOT);
        iterator.setText(text);
        int start = iterator.first();
        for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator.next()) {
            emojis.add(text.substring(start, end));
        }
        return emojis;
    }
}
