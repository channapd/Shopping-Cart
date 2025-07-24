/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


//alert("loaded");
//alert("Testing");

function add_to_cart(pid,pname,price){
    let cart=localStorage.getItem("cart");
    if(cart == null){
        //no cart
        let products=[];
        let product={productId:pid,productName:pname,productQuantity:1,productPrice:price};
        products.push(product);
        localStorage.setItem("cart",JSON.stringify(products));
 //       console.log("Product is added for the first time");
        showToast("Item is added to cart");
    }
    else{
        //cart is present
        let pcart=JSON.parse(cart);
        
        let oldProduct=pcart.find((item)=>item.productId == pid);
        if(oldProduct){
            //we have to increase the quantity
            oldProduct.productQuantity=oldProduct.productQuantity+1;
            pcart.map((item)=>{
                if(item.productId==oldProduct.productId){
                    item.productQuantity=oldProduct.productQuantity;
                }
                    
            });
            localStorage.setItem("cart",JSON.stringify(pcart));
            console.log("Product Quantity is increased");
            showToast(oldProduct.productName + " Quantity is increased , Quantity = " +oldProduct.productQuantity);
            
        }
        else{
            //we have to add the product
            let product={productId:pid,productName:pname,productQuantity:1,productPrice:price};
            pcart.push(product);
            localStorage.setItem("cart",JSON.stringify(pcart));
            console.log("Product is added");
            showToast("Product is added to cart");
        }
    }
    updateCart();
}

//update cart
function updateCart(){
    let cartString=localStorage.getItem("cart");
    let cart=JSON.parse(cartString);
    if(cart==null || cart.length==0){
        console.log("Cart is empty");
        $(".cart-items").html("(0)");
        $(".cart-body").html("<h3>Cart does not have any items</h3>");
        $(".checkout-btn").attr('disabled',true);
    }
    else{
        //there is something in cart to show
        console.log(cart);
        $(".cart-items").html(`( ${cart.length} )`);
        let table=`

            <table class='table'>
            <thead class='thead-light'>
                <tr>
                <th>Item Name</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>Total Price</th>
                <th>Action</th>
        
        
                </tr>

            </thread>



            `;
              
             let totalPrice=0;
             cart.map((item)=>{
                 
                 table+=`
                 
                    <tr>
                        <td>${item.productName}</td>
                        <td>${item.productPrice}</td>
                        <td>${item.productQuantity}</td>
                        <td>${item.productQuantity*item.productPrice}</td>
                        <td> <button onclick='deleteItemFromCart(${item.productId})' class='btn btn-danger btn-sm'>Remove</button> </td>
                        
                    </tr>
                 
                 
                        `;
                        
                        totalPrice+=item.productPrice * item.productQuantity;
                        
                        
                 
             });
                
        table=table+`
                <tr><td colspan='5' class='text-right font-weight-bold m-5'>Total Price: ${totalPrice}</td></tr>

            </table>`;
        $(".cart-body").html(table);
        $(".checkout-btn").attr('disabled',false);
                
    }
}
//delete item
function deleteItemFromCart(pid) {
   let cart = JSON.parse(localStorage.getItem('cart'));
   let index = cart.findIndex((item) => item.productId === pid);

   if (index !== -1) {
       if (cart[index].productQuantity > 1) {
           // If quantity is more than 1, decrement it by 1
           cart[index].productQuantity--;
       } else {
           // If quantity is 1, remove the item from the cart
           cart.splice(index, 1);
       }

       localStorage.setItem('cart', JSON.stringify(cart));
       updateCart();
       showToast("Item is removed from cart");
   } else {
       showToast("Item not found in cart");
   }
}


$(document).ready(function(){
    updateCart();
});

function showToast(content) {
$("#toast").addClass("display");
$("#toast").html(content);
setTimeout(() => {
$("#toast").removeClass("display"); }, 2000);
}


function goToCheckout(){
    
  window.location.href="checkout.jsp";
}
    
  
