<%-- 
    Document   : index
    Created on : Mar 10, 2024, 12:04:06 AM
    Author     : GAGANA K G
--%>
<%@page import="com.learn.mycart.helper.Helper"%>
<%@page import="com.learn.mycart.entities.Category"%>
<%@page import="com.learn.mycart.dao.CategoryDao"%>
<%@page import="java.util.List"%>
<%@page import="com.learn.mycart.dao.ProductDao"%>
<%@page import="com.learn.mycart.entities.Product"%>
<%@page import="com.learn.mycart.helper.FactoryProvider"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>MyCart - Home</title>
   
        <%@include file="components/common_css_js.jsp"%>
    </head>
    <body>
        
        <%@include file="components/navbar.jsp" %>
        
        <div class="container-fluid">
        <div class="row mt-3 mx-2">
            
            <%
               String cat=request.getParameter("category");
               //out.println(cat);
               if(cat==null) cat="all";
               ProductDao dao = new ProductDao(FactoryProvider.getFactory());
               List<Product> list=null;
               if(cat.trim().equals("all"))
               {
                    list= dao.getAllProducts();
               }
               else{
                    int cid=Integer.parseInt(cat.trim());
                    list=dao.getAllProductsById(cid);
               }
               
               
               
               
               
               CategoryDao cdao = new CategoryDao(FactoryProvider.getFactory());
               List<Category> clist = cdao.getCategories();
            %>
            
            <!--show categories-->
            <div class="col-md-2">
                <div class="list-group mt-4">
                    <a href="index.jsp?category=all" class="list-group-item list-group-item-action <%= cat.equals("all") ? "active" : "" %>">
                        All Products
                    </a>
                    <% for(Category category:clist) { %>
                        <a href="index.jsp?category=<%= category.getCategoryId() %>" class="list-group-item list-group-item-action <%= cat.equals(String.valueOf(category.getCategoryId())) ? "active" : "" %>">
                            <%= category.getCategoryTitle() %>
                        </a>
                    <% } %>
                </div>
            </div>

            
            <!--show products-->
            <div class="col-md-10">
                
                <div class="row mt-4">
                    <div class="col-md-12">
                        <div class="card-columns">
                            
                            <%
                                for(Product p:list){
                                
                                
                            %>
                            
                            <!<!-- product card -->
                            <div class="card product-card" >
                                <div class="card-container text-center">
                                
                                    <img class="card-img-top m-2" src="img/products/<%= p.getpPhoto() %>" alt="Card image cap" style="max-height: 200px;max-width: 100%;width: auto;">
                                    
                                </div>
                                <div class="card-body">
                                        <h5 class="card-title">
                                            <%= p.getpName()%>
                                        </h5>

                                        <p class="card-text">
                                            <%= Helper.get10Words(p.getpDesc()) %>
                                        </p>
                                    </div>

                                        <div class="card-footer text-center">
                                            <button class="btn custom-bg text-white" onclick="add_to_cart(<%= p.getpId() %>,'<%= p.getpName() %>',<%= p.getPriceAfterDiscount() %>)">Add to cart</button>
                                            <button class="btn outline-success">&#8377; <%= p.getPriceAfterDiscount() %>/- <span class="text-secondary discount-label">&#8377; <%= p.getpPrice() %> <%= p.getpDiscount() %>% off</span></button>
                                        </div>
                                
                            </div>
                            
                            <%   }  
                                
                            if(list.size()==0){
                                out.println("<h3>No item in this category</h3>");
                            } 
                            
                            
                            %>
                            
                        </div>
                    </div>
                </div>
            </div>
        </div>
        </div>
        
        <%@include file="components/common_modals.jsp" %>
    </body>
</html>
