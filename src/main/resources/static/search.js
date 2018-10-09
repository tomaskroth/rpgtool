$(document).ready(function(){
    $('#thetable').DataTable();
    $("#generate").click(function(event) {
        var data = buildJsonData();

        $.ajax({
                type: "POST",
                url: "/api/v1/store",
                data: data,
                contentType: "application/json"
          }).then(function(data) {
             populateTable(data);
          });
    });
});

function buildJsonData() {
    var data = $("#form").serializeArray();
    var indexed_array = {};
     $.map(data, function(n, i){
            indexed_array[n['name']] = n['value'];
        });
    return JSON.stringify(indexed_array);

}

function populateTable(data) {
    console.log(data);

    var actualData;

    var table = $('#thetable').DataTable();
    table.clear();
    for(var i = 0; i < data.length; i++) {
        table.row.add([
            data[i].quantity,
            data[i].name,
            data[i].rarity,
            data[i].price
        ]).draw();
    }
}