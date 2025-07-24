/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.learn.mycart.servlets;

import com.learn.mycart.dao.CategoryDao;
import com.learn.mycart.dao.ProductDao;
import com.learn.mycart.entities.Category;
import com.learn.mycart.entities.Product;
import com.learn.mycart.helper.FactoryProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;
import jakarta.servlet.ServletContext;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;



/**
 *
 * @author cpedd
 */
@MultipartConfig
public class ProductOperationServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            
            
            
            String op = request.getParameter("operation");
            
            if(op.trim().equals("addcategory")){
                
                String title = request.getParameter("catTitle");
                String description = request.getParameter("catDescription");
                
                Category category = new Category();
                category.setCategoryTitle(title);
                category.setCategoryDescription(description);
                
                CategoryDao categoryDao = new CategoryDao(FactoryProvider.getFactory());
                int catId = categoryDao.saveCategory(category);
                
                //out.println("Category Saved");
                HttpSession httpSession = request.getSession();
                httpSession.setAttribute("message", "Category added successfully");
                response.sendRedirect("admin.jsp");
                return;
            }
            else if(op.trim().equals("addproduct")){
                // Add product
                String pName = request.getParameter("pName");
                String pDesc = request.getParameter("pDesc");
                int pPrice = parseInt(request.getParameter("pPrice"));
                int pDiscount = parseInt(request.getParameter("pDiscount"));
                int pQuantity = parseInt(request.getParameter("pQuantity"));
                int catId = parseInt(request.getParameter("catId"));
                Part part = request.getPart("pPic");
                
                Product p = new Product();
                p.setpName(pName);
                p.setpDesc(pDesc);
                p.setpPrice(pPrice);
                p.setpDiscount(pDiscount);
                p.setpQuantity(pQuantity);
                p.setpPhoto(part.getSubmittedFileName());
                
                
                CategoryDao cdao = new CategoryDao(FactoryProvider.getFactory());
                Category category = cdao.getCategoryById(catId);
                p.setCategory(category);
                
                ProductDao pdao = new ProductDao(FactoryProvider.getFactory());
                pdao.saveProduct(p);
                //out.println("Product save success...");
                
                //String path = request.getContextPath();
                ServletContext servletContext = getServletContext();
                String path = servletContext.getRealPath("img") + File.separator + "products" + File.separator + part.getSubmittedFileName();
                System.out.println(path);
                
                
                try {
                FileOutputStream fos = new FileOutputStream(path);
                InputStream is = part.getInputStream();
                
                byte [] data = new byte[is.available()];
                
                is.read(data);
                
                fos.write(data);
                
                fos.close();
                
                } catch(Exception e){
                    e.printStackTrace();
                }
                
                HttpSession httpSession = request.getSession();
                httpSession.setAttribute("message", "Product added successfully");
                response.sendRedirect("admin.jsp");
                return;
            }
            
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
