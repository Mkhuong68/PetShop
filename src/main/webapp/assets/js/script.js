/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function banCustomerAccount(id) {
    let reason = prompt("Enter reason for banning:");
    if (!reason || reason.trim() === "") {
        alert("You must enter a reason!");
        return;
    }

    let form = document.createElement("form");
    form.method = "POST";
    form.action = "/SWP_PetShop/BanCustomerAccountController"; // ✅ Gọi đúng Servlet

    let idField = document.createElement("input");
    idField.type = "hidden";
    idField.name = "id";
    idField.value = id;
    form.appendChild(idField);

    let reasonField = document.createElement("input");
    reasonField.type = "hidden";
    reasonField.name = "reason";
    reasonField.value = reason;
    form.appendChild(reasonField);

    document.body.appendChild(form);
    form.submit();
}
