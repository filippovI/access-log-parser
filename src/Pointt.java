import java.util.ArrayList;
import java.util.List;

public class Pointt {
    private final int cordX;
    private final int cordY;
    private final int cordZ;
    private final List<PointCharacteristic> characteristics;

    private Pointt(PointtBuilder builder) {
        this.cordX = builder.cordX;
        this.cordY = builder.cordY;
        this.cordZ = builder.cordZ;
        this.characteristics = builder.characteristics;
    }

    public int getCordX() {
        return cordX;
    }

    public int getCordY() {
        return cordY;
    }

    public int getCordZ() {
        return cordZ;
    }

    public String getCharacteristics() {
        StringBuilder characteristics = new StringBuilder();
        for (PointCharacteristic i : this.characteristics) {
            characteristics.append(i.describe()).append("\n");
        }
        return characteristics.toString().replaceAll("\n$", "");
    }

    @Override
    public String toString() {
        return "Pointt{" +
                "cordX=" + cordX +
                ", cordY=" + cordY +
                ", cordZ=" + cordZ +
                ", characteristics=" + characteristics +
                '}';
    }

    public static class PointtBuilder {
        private final int cordX;
        private int cordY = 0;
        private int cordZ = 0;
        private final List<PointCharacteristic> characteristics = new ArrayList<>();

        public PointtBuilder(int cordX) {
            this.cordX = cordX;
        }

        public PointtBuilder setY(int cordY) {
            this.cordY = cordY;
            return this;
        }

        public PointtBuilder setZ(int cordZ) {
            this.cordZ = cordZ;
            return this;
        }

        public PointtBuilder setColor(String color) {
            this.characteristics.add(new ColorCharacteristic(color));
            return this;
        }

        public PointtBuilder setTime(String time) {
            this.characteristics.add(new TimeCharacteristic(time));
            return this;
        }


        public Pointt build() {
            return new Pointt(this);
        }
    }
}
