
$(document).ready(function() {
    // Відправляємо AJAX-запит на REST контролер
    $.ajax({
        url: '/api/strategies?amount=' + amount,
        type: 'GET',
        success: function(data) {
            var strategiesHtml = "";

            data.forEach(function(strategy) {
                strategiesHtml += '<div class="button-row"><form action="/withdraw/process" method="post">' +
                    '<input type="hidden" name="amount" value="' + amount + '">' +
                    '<input type="hidden" name="type" value="' + strategy + '">' +
                    '<button class="button" type="submit">' + strategy + '</button></form></div>';
            });

            $('#strategiesContainer').html(strategiesHtml);
        }
    });
});
//
// $(document).ready(function () {
//     $.ajax({
//         url: "/api/atms",
//         type: "GET",
//         dataType: "json",
//         success: function (data) {
//             var tableBody = $("#usersTableBody");
//             tableBody.empty(); // Очищаємо таблицю перед заповненням новими даними
//
//             // Додаємо рядки з даними в таблицю
//             for (var i = 0; i < data.length; i++) {
//                 var user = data[i];
//                 var row = "<tr><td>" + user.availability + "</td><td>" + user.location + "</td></tr>";
//                 tableBody.append(row);
//             }
//         },
//         error: function (error) {
//             console.log(error);
//         }
//     });
// });
