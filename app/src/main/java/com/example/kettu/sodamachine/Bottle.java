package com.example.kettu.sodamachine;

public class Bottle {
        private String name;
        private String manufacturer;
        private double total_energy;
        private double size;
        private double price;

        public Bottle(){
            name = "Pepsi Max";
            manufacturer = "Pepsi";
            size = 0.5;
            price = 1.80;
        }

        public Bottle(String gname, String gmanuf, double gsize, double gprice){
            name = gname;
            manufacturer = gmanuf;
            size = gsize;
            price = gprice;
        }

        public String getName(){
            return name;
        }

        public String getManufacturer(){
            return manufacturer;
        }

        public double getEnergy(){
            return total_energy;
        }

        public double getSize(){
            return size;
        }
        public double getPrice(){
            return price;
        }

        @Override
        public String toString() {
            String bottle = this.name + "  " + this.size + "l  " + this.price + "€";
            return bottle;
        }
    }
