/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.learn.mycart.dao;

import com.learn.mycart.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class UserDao {
    
    private SessionFactory factory;

    public UserDao(SessionFactory factory) {
        this.factory = factory;
    }
    
    //get user by email and password
    public User getUserByEmailAndPassword(String email, String password)
    {
        User user=null;
        
        try{
            
            String query="from com.learn.mycart.entities.User where userEmail=:e AND userPassword=:p";
            Session session=this.factory.openSession();
            Query q=session.createQuery(query);
            
            q.setParameter("e", email);
            q.setParameter("p", password);
            
            user=(User)q.uniqueResult();
            
            session.close();
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return user;
    }
}
