"use strict";
import {getElemById, setTextInElem, removeContent, showElem} from "./utils.js";

const shopCartItems = JSON.parse(sessionStorage.getItem("shopcart"));
const cartTable = getElemById("cartBody");
let sum = 0;
for (const [key,item] of Object.entries(shopCartItems)) {
    const tr = cartTable.insertRow();
    tr.insertCell().textContent = item.productname;
    tr.insertCell().textContent = item.price;
    const removeLink = document.createElement("a");
    removeLink.href = "#";
    removeLink.textContent = "X";
    removeLink.onclick = () => {
        // const totalAmount = cartTable.rows[cartTable.rows.length - 1].cells[2];
        const totalAmountCell = cartTable.querySelector("tr:last-child td:last-child");
        totalAmountCell.textContent = (Number(totalAmountCell.textContent) - item.price).toFixed(2);
        delete shopCartItems[key];
        sessionStorage.setItem("shopcart", JSON.stringify(shopCartItems));
        tr.remove();

    }
    tr.insertCell().append(removeLink);
    sum += item.price;
}

const tr = cartTable.insertRow();
tr.insertCell().textContent = "Total amount";
tr.insertCell();
tr.insertCell().textContent = sum.toFixed(2);