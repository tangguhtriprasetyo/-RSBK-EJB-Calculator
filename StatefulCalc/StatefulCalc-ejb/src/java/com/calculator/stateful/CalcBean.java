/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.calculator.stateful;

import javax.ejb.Stateful;

/**
 *
 * @author Tangguh
 */
@Stateful
public class CalcBean implements CalcBeanLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")private double total = 0;
    private double total = 0;
    private int count = 0;
    private double pastTotal = 0;

    @Override
    public double add(double value) {
        pastTotal = total;
        total = total + value;
        count++;
        return total;
    }
    
    
    @Override
    public double min(double value) {
        pastTotal = total;
        total = total - value;
        count++;
        return total;
    }

    
    
    @Override
    public double bagi(double value) {
        pastTotal = total;
        total = total / value;
        count++;
        return total;
    }

    
    
    @Override
    public double kali(double value) {
        pastTotal = total;
        total = total * value;
        count++;
        return total;
    }


    @Override
    public double operasi() {
        return pastTotal;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public double getTotal() {
        return total;
    }
    
    @Override
    public double remove() {
        total = 0;
        return total;
    }
    
    @Override
    public int removeCount() {
        count = 0;
        return count;
    }
    
    
}
