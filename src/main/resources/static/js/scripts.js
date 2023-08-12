
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

$(document).ready(function() {
    $.ajax({
        url: '/api/balance',
        method: 'GET',
        headers: {
            'Accept': 'application/json'
        },
        success: function(data) {
            // Отримання контейнера для балансу
            var balanceContainer = $('#balanceContainer');

            // Зміна розміру шрифту втричі більшим
            balanceContainer.text('Balance: ' + data.toFixed(2)).css('font-size', '300%');
        },
        error: function(xhr, status, error) {
            console.error('Error fetching balance:', error);
        }
    });
});
