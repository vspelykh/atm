/**
 * This script initializes and handles AJAX requests to the API endpoints.
 * It populates the UI with retrieved data and handles errors.
 */

// Execute when the document is ready
$(document).ready(function () {

    // AJAX request to fetch available strategies
    $.ajax({
        url: '/api/strategies?amount=' + amount,
        type: 'GET',
        success: function (data) {
            var strategiesHtml = "";

            // Generate HTML for strategies
            data.forEach(function (strategy) {
                strategiesHtml += '<div class="button-row"><form action="/withdraw/process" method="post">' +
                    '<input type="hidden" name="amount" value="' + amount + '">' +
                    '<input type="hidden" name="type" value="' + strategy + '">' +
                    '<button class="button" type="submit">' + strategy + '</button></form></div>';
            });

            // Populate strategies container with generated HTML
            $('#strategiesContainer').html(strategiesHtml);
        },
        error: function (xhr) {
            var resultContainer = $('#strategiesContainer');

            if (xhr.status === 400) {
                resultContainer.empty();
                resultContainer.append(JSON.parse(xhr.responseText).detail);
            } else {
                // Handle other errors
            }
        }
    });
});
$(document).ready(function () {

    // AJAX request to fetch accounts
    $.ajax({
        url: '/api/accounts',
        type: 'GET',
        success: function (data) {
            var accountsHtml = "";
            accountsHtml += '<form action="/transfer/process" method="post">'
            accountsHtml += ' <label for="accountTo">Choose account:</label>\n' +
                '    <select id="accountTo" name="accountTo">'
            data.forEach(function (account) {
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

// AJAX request to perform a transfer
$(document).ready(function () {
    $.ajax({
        url: '/api/transfer',
        type: 'POST',
        data: JSON.stringify({accountTo: accountTo, amount: amount}),
        contentType: 'application/json',
        success: function (response) {
            console.log(amount);
            var resultContainer = $('#transferResultContainer');

            resultContainer.empty();

            // Display transfer information
            var transferInfo = 'Transfer: ' + response.amount + ' grn, to: ' + response.destinationAccount.accountNumber;
            var transferElem = $('<div>').text(transferInfo);
            resultContainer.append(transferElem);
        },
        error: function (xhr, textStatus, errorThrown) {
            var resultContainer = $('#transferResultContainer');
            resultContainer.empty();

            // Handle different types of errors
            if (xhr.status === 400) {
                resultContainer.empty();
                resultContainer.append(JSON.parse(xhr.responseText).detail);
            } else {
                // Handle other errors
            }
        }
    });
});

// AJAX request to perform a withdrawal
$(document).ready(function () {
    $.ajax({
        url: '/api/withdraw',
        type: 'POST',
        data: JSON.stringify({amount: amount, type: type}),
        contentType: 'application/json',
        success: function (data) {
            console.log(amount);
            var resultContainer = $('#withdrawResultContainer');

            resultContainer.empty();

            // Display withdrawal information
            data.forEach(function (banknote) {
                var banknoteInfo = 'Banknote: ' + banknote.denomination + ' grn, quantity: ' + banknote.quantity;
                var banknoteElement = $('<div>').text(banknoteInfo);
                resultContainer.append(banknoteElement);
            });
        },
        error: function (xhr) {
            var resultContainer = $('#withdrawResultContainer');

            if (xhr.status === 400) {
                resultContainer.empty();
                resultContainer.append(JSON.parse(xhr.responseText).detail);
            } else {
                // Handle other errors
            }
        }
    });
});

// AJAX request to fetch account balance
$(document).ready(function () {
    $.ajax({
        url: '/api/balance',
        method: 'GET',
        headers: {
            'Accept': 'application/json'
        },
        success: function (data) {
            var balanceContainer = $('#balanceContainer');

            // Display account balance
            balanceContainer.text('Balance: ' + data.toFixed(2)).css('font-size', '300%');
        },
        error: function (xhr, status, error) {
            console.error('Error fetching balance:', error);
        }
    });
});