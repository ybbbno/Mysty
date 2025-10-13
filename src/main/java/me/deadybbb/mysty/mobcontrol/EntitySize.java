package me.deadybbb.mysty.mobcontrol;

public record EntitySize(double x, double y, double z) {
    public double x() { return x; }
    public double y() { return y; }
    public double z() { return z; }
}
