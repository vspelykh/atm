    $(document).ready(function() {
    $.ajax({
        url: "/api/atms",
        type: "GET",
        dataType: "json",
        success: function(data) {
            var tableBody = $("#usersTableBody");
            tableBody.empty(); // Очищаємо таблицю перед заповненням новими даними

            // Додаємо рядки з даними в таблицю
            for (var i = 0; i < data.length; i++) {
                var user = data[i];
                var row = "<tr><td>" + user.availability + "</td><td>" + user.location + "</td></tr>";
                tableBody.append(row);
            }
        },
        error: function(error) {
            console.log(error);
        }
    });
});
