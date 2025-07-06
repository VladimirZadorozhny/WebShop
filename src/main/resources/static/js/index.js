"use strict";

import{getElemById, setTextInElem} from "./utils.js";

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
        link.onclick = async () => {}
    }

} else {
    alert(responseProdgroup.text);
}