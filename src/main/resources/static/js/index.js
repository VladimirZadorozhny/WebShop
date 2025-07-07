"use strict";

import{getElemById, setTextInElem, removeContent, showElem} from "./utils.js";

const response = await fetch("principal");
if (response.ok) {
    const userName = await response.text();
    document.getElementById("userInfo").innerText = userName;
} else {
    alert(response.text);
}

document.getElementById("loginButton").onclick = () => {
    window.location.href = "login.html";
}

const responseProdgroup = await fetch("prodgroups");
if (responseProdgroup.ok) {
    const groups = await responseProdgroup.json();
    const groupsList = getElemById("groupsList");
    for (const group of groups) {
        const li = document.createElement("li");
        const link = document.createElement("a");
        link.textContent = group.groupname;
        li.append(link);
        groupsList.append(li);
        link.onclick = async () => {
            for (const elem of document.querySelectorAll(".active")) {
                elem.classList.remove("active");
            }
            link.classList.add("active");
            await showProducts(group.id, group.groupname);
        }
    }

} else {
    alert(responseProdgroup.text);
}

async function showProducts(groupId, groupName) {
    const response = await fetch(`prodgroups/${groupId}/products`);
    if (response.ok) {
        const products = await response.json();
        const productsList = getElemById("allProducts");
        removeContent(productsList);
        getElemById("selectedGroup").innerText = groupName;
        showElem("selectedGroup");

        for (const product of products) {
            const img = document.createElement("img");
            img.src = `/images/${groupName}.png`;
            img.alt = img.title = product.productname;
            const link = document.createElement("a");
            link.href = "product.html";
            link.appendChild(img);
            productsList.appendChild(link);
            link.onclick = () => sessionStorage.setItem("productId", product.id);
        }


    } else {
        alert(response.text);
    }
}