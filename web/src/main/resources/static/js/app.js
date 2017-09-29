csrfValue = $('#csrf').val();

$.ajaxPrefilter(function (options, originalOptions, jqXHR) {
    jqXHR.setRequestHeader('X-CSRF-Token', csrfValue);
});

$('#logout').click(function () {
    $.ajax({
        type: 'POST',
        url: '/logout',
        success: function (data) {
            window.location.href = "/login";
        }
    });
});

// table
$(function ($) {
    var $modal = $('#editor-modal'),
        $editor = $('#editor'),
        $editorTitle = $('#editor-title'),
        ft = FooTable.init('#showcase-example-1', {
            columns: [
                {
                    "name": "id",
                    "title": "ID",
                    "breakpoints": "xs sm",
                    "type": "number",
                    "style": {"width": 80, "maxWidth": 80}
                },
                {"name": "name", "title": "Name"},
                {"name": "currentPrice", "title": "Current Price"},
                {"name": "lastUpdate", "title": "Last modified date"}
            ],
            rows: $.get("/api/stocks/"),
            editing: {
                addRow: function () {
                    $modal.removeData('row');
                    $editor[0].reset();
                    $editorTitle.text('Add a new row');
                    $modal.modal('show');
                },
                editRow: function (row) {
                    var values = row.val();
                    $editor.find('#id').val(values.id);
                    var nameField = $editor.find('#name');
                    nameField.val(values.name);
                    nameField.prop('disabled', true);
                    $editor.find('#currentPrice').val(values.currentPrice);
                    $editor.find('#lastUpdate').val(values.lastUpdate);
                    $modal.data('row', row);
                    $editorTitle.text('Edit row #' + values.id);
                    $modal.modal('show');
                },
                deleteRow: function (row) {
                    if (confirm('Are you sure you want to delete the row?')) {
                        deleteStock(row.val().id, row);
                    }
                }
            }
        });

    $editor.on('submit', function (e) {
        if (this.checkValidity && !this.checkValidity()) return;
        e.preventDefault();
        var row = $modal.data('row'),
            values = {
                id: $editor.find('#id').val(),
                name: $editor.find('#name').val(),
                currentPrice: $editor.find('#currentPrice').val(),
                lastUpdate: $editor.find('#lastUpdate').val()
            };

        if (row instanceof FooTable.Row) {
            editStock(values.id, values.currentPrice, row);
        } else {
            saveStock(values, ft.rows);
        }
        $modal.modal('hide');
    });
});

function editStock(id, currentPrice, row) {
    $.ajax({
        type: 'PUT',
        url: '/api/stocks/' + id,
        data: JSON.stringify({
            'currentPrice': currentPrice
        }),
        dataType: "json",
        contentType: "application/json",
        success: function (data) {
            values = {
                id: data.id,
                name: data.name,
                currentPrice: data.currentPrice,
                lastUpdate: data.lastUpdate
            };
            row.val(values);
        },
        error: function (data) {
            alert("error : " + data.error);
        }
    });
}

function deleteStock(id, row) {
    $.ajax({
        type: 'DELETE',
        url: '/api/stocks/' + id,
        success: function (data) {
            row.delete();
        },
        error: function (data) {
            alert("error : " + data.error);
        }
    });
}

function saveStock(values, rows) {
    $.ajax({
        type: 'POST',
        url: '/api/stocks/',
        data: JSON.stringify(values),
        dataType: "json",
        contentType: "application/json",
        success: function (data) {
            response = {
                id: data.id,
                name: data.name,
                currentPrice: data.currentPrice,
                lastUpdate: data.lastUpdate
            };
            rows.add(response);
        },
        error: function (data) {
            alert("error : " + data.error);
        }
    });
}







