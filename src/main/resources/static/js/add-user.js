$(document).ready(function () {

    data = {
      name : "robin",
      password: "robin",
      username : "robin"
    };

    $.ajax({
        url: "http://localhost:8080/admin/users/add",
        type : 'POST',
        dataType : 'JSON',
        data : data,
        contentType:'application/json',
        success : function (response, status, jqXHR) {
            alert();
        }
    });
});