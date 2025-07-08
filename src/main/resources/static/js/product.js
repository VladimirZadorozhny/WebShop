"use strict";

import{getElemById, setTextInElem, removeContent, showElem} from "./utils.js";

let shopcart = {};
if (sessionStorage.getItem("shopcart"))
    shopcart = JSON.parse(sessionStorage.getItem("shopcart"));

const productInfo = JSON.parse(sessionStorage.getItem("productInfo"));

if (productInfo) {
const response  = await fetch(`products/${productInfo.productId}`);

    if (response.ok) {
        const product = await response.json();
        document.title = product.productname;
        setTextInElem("productName", product.productname);
        const image = getElemById("productImage");
        image.src = `/images/${productInfo.groupName}.png`;
        image.alt = image.title = product.productname;
        showElem("productInfo");
        setTextInElem("name", product.productname);
        setTextInElem("price", product.price);
        setTextInElem("barcode", product.barcode);
        const key = String(product.id);
        if (product.available) {
            if (shopcart[key]) {
                getElemById("shopcart").disabled = true;
            }
        } else {
            getElemById("wishlist").disabled = false;
            getElemById("shopcart").disabled = true;
            if (shopcart[key]) {
                delete shopcart[key];
                sessionStorage.setItem("shopcart", JSON.stringify(shopcart));
            }
        }

        getElemById("shopcart").onclick = (event) => {
            if (!shopcart[key]) {
                shopcart[key] = product;
                sessionStorage.setItem("shopcart", JSON.stringify(shopcart));
                event.target.disabled = true;
            }
        }




    } else {
        alert("Product not found!");
    }

} else {
    alert("No products have been saved!");
}

