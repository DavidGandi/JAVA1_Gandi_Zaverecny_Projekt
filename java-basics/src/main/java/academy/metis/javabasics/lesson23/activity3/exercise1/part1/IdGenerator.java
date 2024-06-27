package academy.metis.javabasics.lesson23.activity3.exercise1.part1;

import java.io.*;

public class IdGenerator {
    private final String idCounterFileName;
    private Long counter;
    public IdGenerator(String idCounterFileName)  {
        this.idCounterFileName = idCounterFileName;
        this.counter = 0L;
        loadCounterFromFile();
    }
    public Long generateID()  {
        counter++;
        saveCounterToFile();
        return counter;
    }
    private void loadCounterFromFile()  {
        try (BufferedReader reader = new BufferedReader(new FileReader(idCounterFileName))) {
            String line = reader.readLine();
            counter = Long.parseLong(line);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    private void saveCounterToFile()  {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(idCounterFileName))) {
            writer.write(Long.toString(counter));
            writer.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}