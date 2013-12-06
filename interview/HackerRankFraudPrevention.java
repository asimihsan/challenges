import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Record implements Comparable<Record> {
    public String orderId;
    public String dealId;
    public String emailAddress;
    public String streetAddress;
    public String city;
    public String state;
    public String zipCode;
    public String creditCard; 

    Record(String line) {
        String[] elems = line.split(",");
        orderId = elems[0];
        dealId = elems[1];
        emailAddress = elems[2];
        streetAddress = elems[3];
        city = elems[4];
        state = elems[5];
        zipCode = elems[6];
        creditCard = elems[7];
    }

    @Override
    public String toString() { return orderId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Record)) return false;
        Record r = (Record)o;
        return orderId.equals(r.orderId);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + orderId.hashCode();
        return result;
    }

    @Override
    public int compareTo(Record r) {
        return orderId.compareTo(r.orderId);
    }
}

class Rule {
    String[] same;
    String[] diff;
    Rule(String[] same, String[] diff) {
        this.same = same;
        this.diff = diff;
    }

    public int hashCode(Record r) {
        int returnValue = 17;
        try {
            for (String name : same) {
                Field field = Record.class.getField(name);
                returnValue = 31 * returnValue + field.hashCode();
            }
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Rule couldn't find fields!", e);
        }
        return returnValue;
    }

    public boolean areFraudulent(Record r1, Record r2) {
        try {
            for (String name : same) {
                Field field = Record.class.getField(name);
                if (!( field.get(r1).equals(field.get(r2)) ))
                    return false;
            }
            for (String name : diff) {
                Field field = Record.class.getField(name);
                if ( field.get(r1).equals(field.get(r2)) )
                    return false;
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Rule couldn't find or access fields!", e);
        }
        return true;
    }
}

class FraudDetector {
    private Rule rule;
    private List<List<Record>> lookup;
    private int size;

    FraudDetector(Rule rule, int totalRecords) {
        this.rule = rule;
        Integer temp = totalRecords;
        BigInteger b = new BigInteger(temp.toString());
        size = Math.max(b.nextProbablePrime().intValue(), 11);
        lookup = new ArrayList<List<Record>>(size);
        for (int i = 0; i < size; i++)
            lookup.add(new ArrayList<Record>());
    }

    public void addRecord(Record record) {
        int bucket = (rule.hashCode(record) & 0x7fffffff) % size;
        lookup.get(bucket).add(record);
    }

    public Collection<Record> getFraudulentRecords() {
        Set<Record> fraudulentRecords = new HashSet<>();
        for (int i = 0; i < size; i++) {
            List<Record> bucket = lookup.get(i);
            for (int j = 0; j < bucket.size(); j++) {
                for (int k = j + 1; k < bucket.size(); k++) {
                    if (rule.areFraudulent(bucket.get(j), bucket.get(k))) {
                        fraudulentRecords.add(bucket.get(j));
                        fraudulentRecords.add(bucket.get(k));
                    }
                }
            }
        }
        return fraudulentRecords;
    }
}

class HackerRankFraudPrevention {
    public static void main(String[] args) {
        Rule rule1 = new Rule(new String[] {"emailAddress", "dealId"},
                              new String[] {"creditCard"});
        Rule rule2 = new Rule(new String[] {"streetAddress",
                                            "city",
                                            "state",
                                            "zipCode",
                                            "dealId"},
                              new String[] {"creditCard"});
        FraudDetector f1, f2;
        try (
            InputStreamReader ir = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(ir);
        ) {
            String line = null;
            Integer number = null;
            if ((line = br.readLine()) != null)
                number = new Integer(line);
            f1 = new FraudDetector(rule1, number);
            f2 = new FraudDetector(rule2, number);
            FraudDetector[] fs = {f1, f2};
            while ((line = br.readLine()) != null) {
                line = line.trim().toLowerCase();
                if (line.length() == 0)
                    continue;
                Record record = new Record(line);
                for (FraudDetector f : fs)
                    f.addRecord(record);
            }
            Set<Record> frauds = new HashSet<Record>();
            for (FraudDetector f : fs)
                frauds.addAll(f.getFraudulentRecords());
            List<Record> result = new ArrayList<>(frauds.size());
            result.addAll(frauds);
            Collections.sort(result);
            for (int i = 0; i < result.size() - 1; i++)
                System.out.print(String.format("%s,", result.get(i)));
            System.out.println(result.get(result.size() - 1));
        } catch (IOException e) {
            throw new RuntimeException("couldn't process file", e);
        }
    }
}
