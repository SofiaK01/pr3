$(document).ready(function () {
    let spans = document.getElementById('spans').getElementsByTagName('span');//returns node-list of spans
    let id = spans[0].innerText;
    let url = "request/awaiting";
    fetch(url)
        .then((resp) => resp.json())
        .then(function (data) {
            manage(data);
        })
        .catch(function (error) {
            console.log(error);
        });

});


function manage(userParam) {
    var bt = document.getElementById('makeAdmin');
    if (userParam==true) {
        bt.disabled = true;
    } else {
        bt.disabled = false;
    }
}

document.addEventListener('click', function (event) {
    event.preventDefault()
    if ($(event.target).hasClass('makeAbtn')) {

        let spans = document.getElementById('spans').getElementsByTagName('span');//returns node-list of spans
        let id = spans[0].innerText;
        let url = "email/admin-request/"+id;

        editModalButton(url);
        location.reload();
    }
    if ($(event.target).hasClass('logout')) {
        logout();
    }
});

function editModalButton(url) {
    fetch(url, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json;charset=utf-8"
        },
    })
}


function logout() {
    document.location.replace("/logout");
}


