package ArknightsMod.Operators;

public class StatModifier implements Comparable<StatModifier> {
    public float value;
    public StatModifierType type;
    public int order;
    public Object source;

    public StatModifier(float value, StatModifierType type, int order, Object source) {
        this.value = value;
        this.type = type;
        this.order = order;
        this.source = source;
    }

    public StatModifier(float value, StatModifierType type, Object source) {
        this(value, type, getOrderByType(type), source);
    }

    public StatModifier(float value, StatModifierType type, int order) {
        this(value, type, order, null);
    }

    public StatModifier(float value, StatModifierType type) {
        this(value, type, getOrderByType(type), null);
    }

    private static int getOrderByType(StatModifierType type) {
        switch (type) {
            case FLAT:
                return 3;
            case ADD:
                return 6;
            case MULT:
                return 10;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    @Override
    public int compareTo(StatModifier o) {
        return Integer.compare(this.order, o.order);
    }

    public enum StatModifierType {
        FLAT,
        ADD,
        MULT,
    }
}

