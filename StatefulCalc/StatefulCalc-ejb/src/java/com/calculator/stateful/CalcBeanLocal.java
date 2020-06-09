/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.calculator.stateful;

import javax.ejb.Local;

/**
 *
 * @author Tangguh
 */
@Local
public interface CalcBeanLocal {
    
    public double add(double value);
    
    public double min(double value);
    
    public double bagi(double value);
    
    public double kali(double value);

    public double operasi();

    public int getCount();

    public double getTotal();
    
    public double remove();
    
    public int removeCount();
}
