$(document).ready(function () {
    restartAllUsers();
    $('.AddBtn').on('click', function (event) {
        let user = {
            email: $("#email").val(),
            pts: $("#pts").val(),
            nickname: $("#nickname").val(),
            password: $("#password").val(),
            roles: getRole("#selectRole")

        }
        fetch("user/${user.id}", {
            method: "POST",
            headers: {
                "Content-Type": "application/json;charset=utf-8"
            },
            body: JSON.stringify(user)
        }).then(() => openTabById('nav-home'))
            .then(() => restartAllUsers());
        $('input').val('');
    });
});

function createTableRow(u) {
    let userRole = "";
    let arr = [];
    for (let i = 0; i < u.roles.length; i++) {
        userRole += " " + u.roles[i].name;
        arr.push(u.roles[i].name);
    }
    return `<tr id="user_table_row">
            <td>${u.id}</td>
            <td>${u.nickname}</td>
            <td>${u.email}</td>
            <td>${u.pts}</td>
            <td>${u.password}</td>
            <td>${userRole}</td>
            <td>
            <a  href="/user/${u.id}" idUser="${u.id}" rolesUser = "${arr}" ptsUser="${u.pts}" emailUser="${u.email}" passwordUser="${u.password}" nicknameUser="${u.username}"class="btn btn-info eBtn" >Edit</a>
            </td>
            <td>
            <a  href="/user/${u.id}" class="btn btn-danger delBtn">Delete</a>
            </td>
        </tr>`;
}


function restartAllUsers() {
    let UserTableBody = $("#user_table_body")

    UserTableBody.children().remove();

    fetch("user/all")
        .then((response) => {
            response.json().then(data => data.forEach(function (item, i, data) {
                let TableRow = createTableRow(item);
                UserTableBody.append(TableRow);
            }));
        }).catch(error => {
        console.log(error);
    });
}

function getRole(address) {
    let data = [];
    $(address).find("option:selected").each(function () {
        data.push({id: $(this).val(), name: $(this).attr("name"), authority: $(this).attr("name")})
    });
    return data;
}


document.addEventListener('click', function (event) {
    event.preventDefault()
    if ($(event.target).hasClass('delBtn')) {
        let href = $(event.target).attr("href");
        delModalButton(href)
    }


    if ($(event.target).hasClass('eBtn')) {
        let href = $(event.target).attr("href");
        $(".editUser #exampleModal").modal();

        $(".editUser #id").val($(event.target).attr("idUser"));
        $(".editUser #emailEdit").val($(event.target).attr("emailUser"));
        $(".editUser #ptsEdit").val($(event.target).attr("ptsUser"));
        $(".editUser #nicknameEdit").val($(event.target).attr("nicknameUser"));

        if (!($(event.target).attr("rolesUser").includes("ROLE_ADMIN"))) {
            $(".editUser #selectRoleEdit").val("4");
        }
    }
    if ($(event.target).hasClass('editButton')) {
        let user = {
            id: $('#id').val(),
            email: $('#emailEdit').val(),
            nickname: $('#nicknameEdit').val(),
            password: $('#passwordEdit').val(),
            pts: $('#ptsEdit').val(),
            roles: getRole("#selectRoleEdit")
        }
        editModalButton(user)
    }

    if ($(event.target).hasClass('logout')) {
        logout();
    }
    if ($(event.target).hasClass('btnUserTable')) {
        userTable();
    }

});

function delModalButton(href) {
    fetch(href, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json;charset=utf-8"
        }
    }).then(() => restartAllUsers());
}

function editModalButton(user) {
    fetch("/user/${user.id}", {
        method: "PUT",
        headers: {
            "Content-Type": "application/json;charset=utf-8"
        },
        body: JSON.stringify(user)
    }).then(function (response) {
        $('input').val('');
        $('.editUser #exampleModal').modal('hide');
        restartAllUsers();
    })
}

function openTabById(tab) {
    $('.nav-tabs a[href="#' + tab + '"]').tab('show');
}

function logout() {
    document.location.replace("/logout");
}

function userTable() {
    document.location.replace("http://localhost:8080/users");
}

