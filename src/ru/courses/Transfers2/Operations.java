package ru.courses.Transfers2;

public enum Operations {
    PLUS(1) {
        @Override
        public int apply(int x, int y) {
            return x + y;
        }
    },
    MINUS(2) {
        @Override
        public int apply(int x, int y) {
            return x - y;
        }
    },
    MULTIPLY(3) {
        @Override
        public int apply(int x, int y) {
            return x * y;
        }
    },
    DIVIDE(4) {
        @Override
        public int apply(int x, int y) {
            if (y == 0) return 0;
            return x / y;
        }
    };


    final int code;

    Operations(int code) {
        this.code = code;
    }

    public static Operations fromCode(int code) {
        for (Operations op : Operations.values()) {
            if (op.code == code) {
                return op;
            }
        }
        throw new IllegalArgumentException("Неизвестный код операции: " + code);
    }

    public abstract int apply(int x, int y);
}
