package academy.metis.javabasics.lesson18.activity3.exercise1.part1;

import java.io.*;

public class IdGenerator {
    private final String idCounterFileName;
    private Long counter;
    public IdGenerator(String idCounterFileName) throws IOException {
        this.idCounterFileName = idCounterFileName;
        this.counter = 0L;
        loadCounterFromFile();
    }
    public Long generateID() throws IOException {
        counter++;
        saveCounterToFile();
        return counter;
    }
    private void loadCounterFromFile() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(idCounterFileName));
        String line = reader.readLine();
        counter = Long.parseLong(line);

    }
    private void saveCounterToFile() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(idCounterFileName));
        writer.write(Long.toString(counter));
        writer.flush();
    }
}