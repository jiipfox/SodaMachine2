package com.example.kettu.sodamachine;

import java.util.ArrayList;
import java.text.DecimalFormat;
//import org.decimal4j.DoubleRounder;

public class BottleDispenser {

    private static BottleDispenser singleDispenseri = new BottleDispenser();

    public static BottleDispenser getInstance(){
        return singleDispenseri;
    }

    private int bottles;
    private ArrayList<Bottle> bottlesArray;
    private double money;
    
    public BottleDispenser() {
        bottles = 5;
        money = 0.0;

        // Initialize the array
        bottlesArray = new ArrayList(0);
    }
    
    public String addMoney(double amount) {
        money += amount;
        String ret = "Klink! Added " + amount + "€";
        return ret;
    }
    
    public String buyBottle(int bottle_index) {
        if (bottlesArray.isEmpty()){
            return "Bottle dispenser is empty!";
        }

        double d1 = bottlesArray.get(bottle_index).getPrice(); // always take the get the latest
        double d2 = money;
        if (Double.compare(d1,d2) > 0){
            return "Add money first!";
        }

        String bottle_buy = bottlesArray.get(bottle_index).getName();
        double price = bottlesArray.get(bottle_index).getPrice();

        bottles -= 1;
        bottlesArray.remove(bottle_index);
        money -= price;
        String ret = "KACHUNK! " + bottle_buy + " came out of the dispenser!";
        return ret;
    }
    
    public String returnMoney() {
        DecimalFormat df = new DecimalFormat("0.00");
        String ret = "Klink klink. Money came out! You got " + df.format(money) + "€ back";
        money = 0.0;
        return ret;
    }

    public void addBottle(String name, String manuf, double size, double price){
        bottlesArray.add(new Bottle(name, manuf, size, price));
    }

    public ArrayList<Bottle> getBottles(){
        return bottlesArray;
    }

}
