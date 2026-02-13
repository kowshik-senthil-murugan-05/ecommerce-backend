const params = new URLSearchParams(window.location.search);
const orderId = params.get("orderId");
console.log('orderid -> ', orderId);

loadOrderDetail();
async function loadOrderDetail() {
    try {
        const response = await fetch(`http://localhost:8080/api/user/order/fetch/${orderId}`);
        const orderObj = await response.json();

        const order = orderObj.savedObject;
        console.log('order -> ', order);

        document.getElementById('productName').textContent = order.productName;
        document.getElementById('qty').textContent = order.quantity;
        document.getElementById('productPrice').textContent = order.productPrice;
        document.getElementById('totalAmt').textContent = order.totalAmount;
        document.getElementById('seller').textContent = order.sellerName;
        document.getElementById('orderDate').textContent = order.orderDate;
        document.getElementById('status').textContent = order.currentOrderStatus;

        const existingOrderStatusHistory = document.getElementById('orderStatusHistory');
        existingOrderStatusHistory.innerHTML = '';

        order.orderStatusHistoryDTOS.forEach(os => {
            const li = document.createElement('li');
            li.textContent = `${os.orderStatus} - ${os.statusTime}`;

            existingOrderStatusHistory.appendChild(li);
        });
    } catch (error) {
         console.error("Error fetching Order:", error);
        alert("Failed to load order!!");
    }
}

document.getElementById('changeOrderStatus').addEventListener('click', updateOrderStatus);

async function updateOrderStatus() {
    const orderStatus = document.getElementById('orderStatus').value;

    const orderStatusUpdateDTO = {
        orderId : orderId,
        orderStatus : orderStatus
    };

    console.log("Order status dto -> ", orderStatusUpdateDTO);

    try {
        const response = await fetch('http://localhost:8080/api/admin/order/status/update', {
            method: "POST",
            headers: {
                "Content-Type" : "application/json"
            },
            body: JSON.stringify(orderStatusUpdateDTO)
        });

        const result = await response.json();
        console.log("Result - ", result);
        alert(result.message);

        location.reload();
    } catch (error) {
        console.error("Error - ", error);
        alert(error.message);
    }
}