
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

$(document).ready(function() {
    $.ajax({
        url: '/api/accounts',
        type: 'GET',
        success: function(data) {
            var accountsHtml = "";
            accountsHtml += '<form action="/transfer/process" method="post">'
            accountsHtml += ' <label for="accountTo">Choose account:</label>\n' +
                '    <select id="accountTo" name="accountTo">'
            data.forEach(function(account) {
                accountsHtml += '<option value="' + account + '">' + account + '</option>';
            });
            accountsHtml += '</select>'
            accountsHtml += '<label for="amount">Enter the amount:</label>\n' +
                '    <input type="number" id="amount" name="amount" step="0.01" required>\n' +
                '\n' +
                '    <button type="submit">Perform</button>\n' +
                '</form>'
            $('#accountsContainer').html(accountsHtml);
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
