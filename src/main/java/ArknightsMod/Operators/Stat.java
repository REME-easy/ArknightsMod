package ArknightsMod.Operators;

import ArknightsMod.Operators.StatModifier.StatModifierType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

public class Stat {
    public float baseValue;
    protected float minValue;
    protected float maxValue;
    protected float value;
    protected float lastBaseValue;
    protected boolean isModified;
    public int modifedState;
    public StatModifierType type;

    public ArrayList<StatModifier> statModifiers;

    protected static Comparator<StatModifier> statModifierComparator = StatModifier::compareTo;

    public Stat(float baseValue, float minValue, float maxValue) {
        this.baseValue = baseValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.type = StatModifierType.FLAT;
        this.statModifiers = new ArrayList<>();
    }

    public Stat(float baseValue, StatModifierType type) {
        this(baseValue, 0.0F, Float.MAX_VALUE);
        this.type = type;
    }

    public Stat(float baseValue) {
        this(baseValue, 0.0F, Float.MAX_VALUE);
    }

    public float getValue() {
        if(isModified || baseValue != lastBaseValue) {
            lastBaseValue = baseValue;
            value = calculateFinalValue();
            isModified = false;
        }
        return value;
    }

    public void reset() {
        reset(this.baseValue);
    }

    public void reset(float baseValue) {
        statModifiers.clear();
        this.baseValue = baseValue;
    }

    public StatModifier makeModifier() {
        return new StatModifier(getValue(), type);
    }

    public void addModifier(StatModifier mod) {
        isModified = true;
        statModifiers.add(mod);
        statModifiers.sort(statModifierComparator);
    }

    public void addAllModifiers(Collection<StatModifier> mods) {
        isModified = true;
        statModifiers.addAll(mods);
        statModifiers.sort(statModifierComparator);
    }

    public boolean removeModifier(StatModifier mod) {
        if(statModifiers.remove(mod)) {
            isModified = true;
            return true;
        }
        return false;
    }

    public boolean removeAllModifiersFromSource(Object source) {
        boolean didRemove = false;
        for (int i = statModifiers.size() - 1; i > 0; i--) {
            if(statModifiers.get(i).source == source) {
                didRemove = true;
                isModified = true;
                statModifiers.remove(i);
            }
        }
        return didRemove;
    }

    protected float calculateFinalValue() {
        float val = baseValue;
        float sum = 0.0F;
        for (int i = 0; i < statModifiers.size(); i++) {
            StatModifier mod = statModifiers.get(i);
            switch (mod.type) {
                case FLAT:
                    val += mod.value;
                    break;
                case ADD:
                    sum += mod.value;
                    if(i + 1 >= statModifiers.size() || statModifiers.get(i + 1).type != StatModifierType.ADD) {
                        val *= 1 + sum;
                        sum = 0.0F;
                    }
                    break;
                case MULT:
                    val *= 1 + mod.value;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + mod.type);
            }
        }

        if(val < minValue) val = minValue;
        if(val > maxValue) val = maxValue;

        modifedState = Float.compare(val, baseValue);

        return (float)Math.round(val);
    }


}
