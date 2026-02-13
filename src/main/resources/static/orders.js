const userId = 1;

fetchOrders();

async function fetchOrders() {
    try {
        const response = await fetch(`http://localhost:8080/api/user/order/myOrders/${userId}`);
        const ordersPage = await response.json();

        const orders = ordersPage.list;

        const existingOrders = document.getElementById('orders');
        existingOrders.innerHTML = '';

        if(!orders || orders.length == 0)
        {
            existingOrders.innerHTML = "<p>No orders found!!</p>";
            return;
        }

        orders.forEach(order => {
            const li = document.createElement('li');
            li.textContent = order.productName;

            li.style.cursor="pointer";
            li.addEventListener("click", () => {
                window.location.href = `orderdetailpage.html?orderId=${order.orderId}`;
            });

            existingOrders.appendChild(li);
        });
    } catch (error) {
        console.error("Error : ", error);
        alert("Failed to fetch orders!!");
    }
}
