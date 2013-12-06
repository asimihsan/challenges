import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

class State {
    boolean waitingForHeader = true;
    int lengthOfDataRemaining;
    byte[] restOfData;

    @Override
    public String toString() {
        return String.format("{waitingForHeader: %s, lengthOfDataRemaining: %s}",
            waitingForHeader, lengthOfDataRemaining);
    }
}

class BytePlaying {
    static byte[] concat(byte[] first, byte[] second) {
        byte[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, first, first.length, second.length);
        return result;
    }

    static void processData(byte[] data, State state) {
        System.out.println(state);
        if (state.restOfData != null)
            data = concat(state.restOfData, data);
        if (state.waitingForHeader) {
            if (data.length < 4) {
                state.restOfData = data;                
                return;
            } else {
                state.waitingForHeader = false;
                byte[] header = {data[0], data[1], data[2], data[3]};
                ByteBuffer bb = ByteBuffer.wrap(header);
                state.lengthOfDataRemaining = bb.getInt();
                data = Arrays.copyOfRange(data, 4, data.length);
            }
        }
        if (data.length < state.lengthOfDataRemaining) {
            state.restOfData = data;
        } else {
            ByteBuffer message = ByteBuffer.wrap(data);
            System.out.println(message);    
        }
    }

    public static void main(String[] args) {
        byte[] data;
        State state = new State();

        data = new byte[] {0, 0, 0, 5, 1, 2, 3};
        processData(data, state);
        data = new byte[] {4, 5};
        processData(data, state);

        /*
        byte[] array = {0, 0, 0, 5};
        ByteBuffer bb = ByteBuffer.wrap(array);
        bb.order(ByteOrder.BIG_ENDIAN);  // default
        System.out.println(bb.getInt());

        array = new byte[] {5, 0, 0, 0};
        bb = ByteBuffer.wrap(array);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        System.out.println(bb.getInt());
        */
    }
}
