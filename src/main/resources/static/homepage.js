document.getElementById('search').addEventListener("click", searchProducts);

async function searchProducts() {
    const keyword = document.getElementById("product").value;

    if(!keyword)
    {
        alert('Enter search keyword');
        return;
    }

     window.location.href = `product.html?keyword=${encodeURIComponent(keyword)}`;
}

document.getElementById("orders").addEventListener("click", () => {
    window.location.href=`orders.html`;
});

document.getElementById('cart').addEventListener('click', () => {
    window.location.href = 'cartdetailpage.html';
})