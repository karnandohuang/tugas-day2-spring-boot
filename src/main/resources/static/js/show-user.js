$(document).ready(function () {
    $.ajax({
        url: "http://localhost:8080/users/list",
        type: 'GET',
        dataType: 'JSON',
        contentType: 'application/json',
        success: function (response, status, jqXHR) {
            console.log(response);
            $(response).each(function (index, value) {
                var record = "<tr class='row-select'>" +
                    "</td><td class='username'>" + value.username +
                    "</td><td class='name'>" + value.name +
                    "</td><td class='role'>" + value.role +
                    "</td><td class='p-1 text-center'>";

                $("#user-list").append(record);

            });

        }

    })
});