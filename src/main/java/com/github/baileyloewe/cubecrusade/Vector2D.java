package com.github.baileyloewe.cubecrusade;

public class Vector2D {
    public float x, y;

    public Vector2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D normalize() {
        float magnitude = (float) Math.sqrt(x * x + y * y);
        if (magnitude > 0) {
            return new Vector2D(x / magnitude, y / magnitude);
        }
        return this;
    }

    public Vector2D add(Vector2D vector) {
        return new Vector2D(x + vector.x, y + vector.y);
    }

    public Vector2D scale(float scale) {
        return new Vector2D(x * scale, y * scale);
    }

    public Vector2D subtract(Vector2D vector) {
        return new Vector2D(x - vector.x, y - vector.y);
    }
}
