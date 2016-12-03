package mobileappchallenge.peach;

/**
 * Created by Teribane on 4/30/2016.
 */
public class IngrObject {
    public String name;
    public double amount;
    public String mUnit;

    IngrObject (String a, double b, String c) {
        name = a;
        amount = b;
        mUnit = c;
    }

    IngrObject (String a, double b) {
        name = a;
        amount = b;
        mUnit = null;
    }


}

