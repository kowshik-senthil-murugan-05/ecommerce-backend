
export function displayProducts(products, targetElementId)
{
    const existingList = document.getElementById(targetElementId);
    existingList.innerHTML = '';

    if(!products || products.length == 0)
    {
        existingList.innerHTML = "<p>No products found!!</p>";
        return;
    }

        products.forEach(product => {
            const li = document.createElement('li');
            li.textContent = product.productName;

            li.style.cursor="pointer";
            li.addEventListener("click", () => {
                window.location.href = `productpage.html?productId=${product.id}`;
            });


            existingList.appendChild(li);
        });
}