package princeton.calc;

/**
 *  Compilation:  javac Vector.java
 *  Execution:    java Vector
 *
 *  Implementation of a vector of real numbers.
 *
 *  This class is implemented to be immutable: once the client program
 *  initialize a Vector, it cannot change any of its fields
 *  (N or data[i]) either directly or indirectly. Immutability is a
 *  very desirable feature of a data type.
 *
 *
 *  % java Vector
 *  x        =  (1.0, 2.0, 3.0, 4.0)
 *  y        =  (5.0, 2.0, 4.0, 1.0)
 *  x + y    =  (6.0, 4.0, 7.0, 5.0)
 *  10x      =  (10.0, 20.0, 30.0, 40.0)
 *  |x|      =  5.477225575051661
 *  <x, y>   =  25.0
 *  |x - y|  =  5.0990195135927845
 *
 *  Note that java.util.Vector is an unrelated Java library class.
 *
 **/

public class Vector { 

    private final int length;         // length of the vector
    private double[] components;       // array of vector's components

    // create the zero vector of length N
    public Vector(int length) {
        this.length = length;
        this.components = new double[length];
    }

    public Vector(double... data) {
        length = data.length;

        // defensive copy so that client can't alter our copy of data[]
        this.components = new double[length];
        for (int i = 0; i < length; i++)
            this.components[i] = data[i];
    }
    
    // return the length of the vector
    public int length() {
        return length;
    }

    // return the inner product of this Vector a and b
    public double dot(Vector that) {
        if (this.length() != that.length()) throw new RuntimeException("Dimensions don't agree");
        double sum = 0.0;
        for (int i = 0; i < length; i++)
            sum = sum + (this.components[i] * that.components[i]);
        return sum;
    }

    // return the Euclidean norm of this Vector
    public double magnitude() {
        return Math.sqrt(this.dot(this));
    }

    // return the Euclidean distance between this and that
    public double distanceTo(Vector that) {
        if (this.length() != that.length()) throw new RuntimeException("Dimensions don't agree");
        return this.minus(that).magnitude();
    }

    // return this + that
    public Vector plus(Vector that) {
        if (this.length() != that.length()) throw new RuntimeException("Dimensions don't agree");
        Vector c = new Vector(length);
        for (int i = 0; i < length; i++)
            c.components[i] = this.components[i] + that.components[i];
        return c;
    }

    // return this - that
    public Vector minus(Vector that) {
        if (this.length() != that.length()) throw new RuntimeException("Dimensions don't agree");
        Vector c = new Vector(length);
        for (int i = 0; i < length; i++)
            c.components[i] = this.components[i] - that.components[i];
        return c;
    }

    // return the corresponding coordinate
    public double cartesian(int i) {
        return components[i];
    }

    // create and return a new object whose value is (this * factor)
    public Vector times(double factor) {
        Vector c = new Vector(length);
        for (int i = 0; i < length; i++)
            c.components[i] = factor * components[i];
        return c;
    }


    // return the corresponding unit vector
    public Vector direction() {
        if (this.magnitude() == 0.0) throw new RuntimeException("Zero-vector has no direction");
        return this.times(1.0 / this.magnitude());
    }

    // return a string representation of the vector
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("(");
        for (int i = 0; i < length; i++) {
            s.append(components[i]);
            if (i < length-1) s.append(", ");
        }
        s.append(")");
        return s.toString();
    }
    
    public static Vector getNormal(Vector v) {
		return new Vector(-v.components[1], v.components[0]);
    }
    
    public Vector getNormal() {
		return new Vector(-this.components[1], this.components[0]);
    	
    }

    // test client
    public static void main(String[] args) {
        double[] xdata = { 1.0, 2.0, 3.0, 4.0 };
        double[] ydata = { 5.0, 2.0, 4.0, 1.0 };

        Vector x = new Vector(xdata);
        Vector y = new Vector(ydata);

        System.out.println("x        =  " + x);
        System.out.println("y        =  " + y);
        System.out.println("x + y    =  " + x.plus(y));
        System.out.println("10x      =  " + x.times(10.0));
        System.out.println("|x|      =  " + x.magnitude());
        System.out.println("<x, y>   =  " + x.dot(y));
        System.out.println("|x - y|  =  " + x.minus(y).magnitude());
    }
}
