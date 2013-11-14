import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;
import java.util.Arrays;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Set;
import java.util.HashSet;

class FileLineReader implements Iterable<String> {
    private Path path;

    FileLineReader(String filePath) {
        FileSystem fs = FileSystems.getDefault();
        path = fs.getPath(filePath);        
    }

    @Override public Iterator<String> iterator() {
        return new Iterator<String>() {
            private String nextLine;
            private BufferedReader br;
            {
                try {
                    br = Files.newBufferedReader(path, StandardCharsets.UTF_8);
                    nextLine = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace(System.err);
                    nextLine = null;
                }
            }
            
            public boolean hasNext() {
                if (nextLine == null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace(System.err);
                    }
                }
                return (nextLine != null);
            }
            public String next() {
                String returnValue = nextLine;
                try {
                    nextLine = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace(System.err);
                    nextLine = null;
                }
                return returnValue;
            }
            public void remove() {
                throw new UnsupportedOperationException("Can't remove!");
            }
        };
    }
}

public class CustomerLog {
    public static void main(String[] args) {
        Map<String, Date> customers = new HashMap<String, Date>();
        Set<String> repeatCustomers = new HashSet<String>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        for (String line : new FileLineReader("customer.log")) {
            String[] tokens = line.split(" : ");
            String timestamp = tokens[0];
            Date date;
            try {
                date = format.parse(timestamp);
            } catch (ParseException e) {
                e.printStackTrace(System.err);
                break;
            }
            String customerId = tokens[1];
            if (!(customers.containsKey(customerId)))
                customers.put(customerId, date);
            long diff = customers.get(customerId).getTime() - date.getTime();
            long diffDays = diff / (1000 * 60 * 60 * 24);
            if (diffDays >= 1)
                repeatCustomers.add(customerId);
        }
        System.out.println(repeatCustomers);
    }
}

