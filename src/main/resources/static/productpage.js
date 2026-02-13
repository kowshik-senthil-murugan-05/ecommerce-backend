const params = new URLSearchParams(window.location.search);
const productId = params.get("productId");

loadProductDetails(productId);
async function loadProductDetails(productId){
    try {
        const response = await fetch(`http://localhost:8080/api/user/product/fetch/${productId}`);
        const productObj = await response.json();

        const product = productObj.savedObject;
        console.log('Product -> ', product);

        document.getElementById('category').textContent = product.category;
        document.getElementById('productName').textContent = product.productName;
        document.getElementById('description').textContent = product.description;
        document.getElementById('price').textContent = product.productPrice;
        document.getElementById('seller').textContent = product.seller;
    } catch (error) {
        console.error("Error fetching product:", error);
        alert("Failed to load product!!");
    }
}

document.getElementById('cart').addEventListener('click', addToCart);

async function addToCart() {
    const productQty = document.getElementById('qty').value;

    if(productQty <= 0)
    {
        alert('Select valid quantity!!');
        return;
    }

    const addToCartRequestDTO = {
        productId : productId,
        productQuantity : productQty
    };

    try{
        const response = await fetch("http://localhost:8080/api/cart/product/addToCart/1",{
            method: "POST",
            headers: {
                "Content-Type" : "application/json"
            },
            body: JSON.stringify(addToCartRequestDTO)
        });

        const result = await response.json();
        console.log('Response -> ' , result);
        alert(result.message);
    }catch (error) {
        console.error("Error:", error);
        alert("Failed to add to cart!!");
      }
}


document.getElementById('buy').addEventListener('click', placeOrder);
async function placeOrder() {
    const productQty = document.getElementById('qty').value;

    if(productQty <= 0)
    {
        alert('Select valid quantity!!');
        return;
    }

    const orderReqDTO = {
        addressId : 1,
        productId : productId,
        productQuantity : productQty
        // orderItemRequestDTOS : [
        //     {
        //         productId : productId,
        //         productQuantity : productQty
        //     }
        // ]
    };

    try {
        const response = await fetch("http://localhost:8080/api/user/order/place/1", {
            method: "POST",
            headers: {
                "Content-type" : "application/json"
            },
            body: JSON.stringify(orderReqDTO)
        });

        const result = await response.json();
        console.log("result -> ", result);
        alert(result.message);

        window.location.href="homepage.html";
    } catch (error) {
        console.error("Error:", error);
        alert("Failed to place order!!");
    }
}

document.getElementById('cart').addEventListener('click', addToCart);

async function addToCart() {
    const productQty = document.getElementById('qty').value;

    if(productQty <= 0)
    {
        alert('Select valid quantity!!');
        return;
    }

    const addToCartRequestDTO  = {
        productId : productId,
        productQuantity : productQty
    };

    let userId = 2;

    try {
        const response = await fetch(`http://localhost:8080/api/cart/product/addToCart/${userId}`,{
            method: "POST",
            headers: {
                "Content-Type" : "application/json"
            },
            body: JSON.stringify(addToCartRequestDTO)
        });
        const result = await response.json();

        console.log("Result -> ", result);
        alert(result.message);

        window.location.href = 'homepage.html';
    } catch (error) {
        console.error("Error -> ", error);
        alert('Failed to add product to cart!!');
    }
}