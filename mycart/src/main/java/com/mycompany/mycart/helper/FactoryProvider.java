/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mycart.helper;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
/**
 *
 * @author cpedd
 */
public class FactoryProvider {
    private static SessionFactory factory;
    
    public static SessionFactory getFactory(){
        // This is actually a design pattern called singleton design pattern.
        // Meaning that factory variable will only be allocated a value once.
        // Subsequent times, the same value of factory will be returned
        try{   
            if(factory==null){
                factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
            }
        } 
        catch(Exception e){
            e.printStackTrace();
        }
        return factory;
    }
}
