"use strict";

export function getElemById(id) {
    return document.getElementById(id);
}

export function setTextInElem(id, text) {
    getElemById(id).textContent = text;
}

export function showElem(id) {
    getElemById(id).hidden = false;
}

export function hideElem(id) {
    getElemById(id).hidden = true;
}

export function removeContent(element) {
    element.innerHTML = "";
}

export async function getPrincipal() {
    if (sessionStorage.getItem("user"))
        return sessionStorage.getItem("user");

    const response = await fetch("principal");
    if (response.ok) {
        const user = await response.text();
        sessionStorage.setItem("user", user);
        return user;
    } else {
        alert(response.text);
        return null;
    }
}