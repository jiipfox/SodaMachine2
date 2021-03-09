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
        String ret = "";
        if (amount > 0.0) {
            money += amount;
            ret = "Klink! Added " + amount + "€";
        }
        return ret;
    }
    
    public String buyBottle(int bottle_index) {
        String bottle_details;
        String ret_val = "";
        double price;
        double customer_balance;
        int bottles = bottlesArray.size();


        System.out.println("Bottles = " + bottles + ", buy index = " + bottle_index);

        if (bottlesArray.isEmpty()){
            return "Bottle dispenser is empty!";
        }

        price = bottlesArray.get(bottle_index).getPrice(); // always take the get the latest
        customer_balance = money;
        if (Double.compare(price,customer_balance) > 0){
            return "Add money first!";
        }

        bottle_details = bottlesArray.get(bottle_index).getName();

        //bottles = bottles - 1; // ???
        bottlesArray.remove(bottle_index);
        money -= price;
        ret_val = "KACHUNK! " + bottle_details + " came out of the dispenser!";

        System.out.println("Succesfull!");
        return ret_val;
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
