const userId = 2;

fetchCartForUser();
async function fetchCartForUser() {
    try {
        const response = await fetch(`http://localhost:8080/api/cart/fetch/${userId}`);

        const existingCartItems = document.getElementById('cartItems');
        existingCartItems.innerHTML = '';

        if (!response.ok) {
            existingCartItems.innerHTML = "<p>No cart for this user!!</p>";
            document.getElementById('userName').textContent = "";
            document.getElementById('total').textContent = "";
            return;
        }

        const cart = await response.json();

        console.log("Cart -> ", cart);

        document.getElementById('userName').textContent = cart.userName;
        document.getElementById('total').textContent = cart.totalPrice;

        cart.cartItemDTOS.forEach(cartItem => {
            const li = document.createElement('li');

            const span = document.createElement('span');
            span.textContent = `${cartItem.productName} - Rs ${cartItem.productPrice}`;

            const removeBtn = document.createElement('button');
            removeBtn.textContent = 'Remove';

            li.style.cursor="pointer";
            li.addEventListener("click", () => {
                window.location.href = `productpage.html?productId=${cartItem.productId}`;
            });

            removeBtn.addEventListener('click', () => {
                removeCartItem(cart.cartId, cartItem.productId);
            });

            li.appendChild(span);
            li.appendChild(removeBtn);

            existingCartItems.appendChild(li);

            // window.location.href="cartdetailpage.html";
        })

    } catch (error) {
        console.error("Error -> ", error);
        alert('Failed to load cart!!');
    }
}

async function removeCartItem(userCartId, productId) {

    const cartItemRemoveReqDTO = {
        userCartId : userCartId,
        productId : productId
    };

    try {
        const response = await fetch('http://localhost:8080/api/cart/cartItem/remove',{
            method: "POST",
            headers: {
                "Content-Type" : "application/json"
            },
            body: JSON.stringify(cartItemRemoveReqDTO)
        });

        const result = await response.json();
        console.log('result -> ', result);
        alert(result.message);

        location.reload();

    } catch (error) {
        console.error("Error -> ", error);
        alert('Failed to remove cart item!!');
    }
}