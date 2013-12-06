import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class EmailAddress {
    private String original;
    private String stripped;

    EmailAddress(String original) {
        this.original = original;
        stripped = createStripped(original);
    }

    private static String createStripped(String original) {
        StringBuilder output = new StringBuilder();
        boolean startIgnoring = false;
        boolean afterAtSign = false;
        for (int i = 0; i < original.length(); i++) {
            char c = original.charAt(i);
            switch(c) {
                case '.':
                    if (!afterAtSign) continue;
                    output.append(c);
                    break;                    
                case '+':
                    startIgnoring = true;
                    continue;
                case '@':
                    startIgnoring = false;
                    afterAtSign = true;
                    output.append(c);
                    break;
                default:
                    if (startIgnoring) continue;
                    output.append(c);
                    break;
            }
        }
        return output.toString();
    }

    @Override
    public String toString() {
        return String.format("{original=%s, stripped=%s}", original, stripped);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmailAddress)) return false;
        EmailAddress email = (EmailAddress)o;
        return stripped.equals(email.stripped);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * stripped.hashCode();
        return result;
    }
}

abstract class MappedField {
    private Map<String, String> mappings;
    private String original;
    private String mapped;    

    MappedField(String original, Map<String, String> mappings) {
        this.original = original;
        this.mappings = mappings;
        mapped = createMapped(original);
    }

    private String createMapped(String original) {
        String output = original;
        for (Map.Entry<String, String> entry : mappings.entrySet())
            output = output.replaceAll(entry.getKey(), entry.getValue());
        return output;
    }

    @Override
    public String toString() {
        return String.format("{original=%s, mapped=%s}", original, mapped);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MappedField)) return false;
        MappedField m = (MappedField)o;
        return mapped.equals(m.mapped);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + mapped.hashCode();
        return result;
    }
}

class StreetAddress extends MappedField {
    static final private Map<String, String> mappings = new HashMap<>();
    static {
        mappings.put("street", "st.");
        mappings.put("road", "rd.");
    }

    StreetAddress(String original) {
        super(original, mappings);
    }
}

class State extends MappedField {
    static final private Map<String, String> mappings = new HashMap<>();
    static {
        mappings.put("illinois", "il");
        mappings.put("california", "ca");
        mappings.put("new york", "ny");
    }

    State(String original) {
        super(original, mappings);
    }
}

class Record implements Comparable<Record> {
    public String orderId;
    public String dealId;
    public EmailAddress emailAddress;
    public StreetAddress streetAddress;
    public String city;
    public State state;
    public String zipCode;
    public String creditCard; 

    Record(String line) {
        String[] elems = line.split(",");
        orderId = elems[0];
        dealId = elems[1];
        emailAddress = new EmailAddress(elems[2]);
        streetAddress = new StreetAddress(elems[3]);
        city = elems[4];
        state = new State(elems[5]);
        zipCode = elems[6];
        creditCard = elems[7];
    }

    @Override
    public String toString() { return orderId; }

    public String fullString() {
        return String.format("{orderId=%s, dealId=%s, emailAddress=%s, streetAddress=%s, city=%s, state=%s, zipCode=%s, creditCard=%s}",
                             orderId, dealId, emailAddress, streetAddress, city, state, zipCode, creditCard);
    }

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
    Field[] same;
    Field[] diff;
    Rule(String[] same, String[] diff) {
        try {
            this.same = new Field[same.length];
            for (int i = 0; i < same.length; i++)
                this.same[i] = Record.class.getField(same[i]);
            this.diff = new Field[diff.length];
            for (int i = 0; i < diff.length; i++)
                this.diff[i] = Record.class.getField(diff[i]);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Constructor couldn't find field!", e);
        }
    }

    public int hashCode(Record r) {
        int returnValue = 17;
        try {
            for (Field field : same) {
                returnValue = 31 * returnValue + field.get(r).hashCode();
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Rule couldn't access field!", e);
        }
        return returnValue;
    }

    public boolean areFraudulent(Record r1, Record r2) {
        try {
            for (Field field : same) {
                if (!( field.get(r1).equals(field.get(r2)) ))
                    return false;
            }
            for (Field field : diff) {
                if ( field.get(r1).equals(field.get(r2)) )
                    return false;
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Rule couldn't access field!", e);
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
        Integer temp = totalRecords / 5;
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
        //List<Record> records = new ArrayList<>();
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
                //records.add(record);
                for (FraudDetector f : fs)
                    f.addRecord(record);
            }

            Set<Record> frauds = new HashSet<Record>();
            for (FraudDetector f : fs)
                frauds.addAll(f.getFraudulentRecords());
            List<Integer> result = new ArrayList<>(frauds.size());
            for (Record r : frauds)
                result.add(new Integer(r.orderId));
            Collections.sort(result);
            for (int i = 0; i < result.size() - 1; i++)
                System.out.print(String.format("%s,", result.get(i)));
            System.out.println(result.get(result.size() - 1));

        } catch (IOException e) {
            throw new RuntimeException("couldn't process file", e);
        }

        //System.out.println(records.get(1580).fullString());
        //System.out.println(records.get(9735).fullString());
    }
}
