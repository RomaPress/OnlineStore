function addProduct(current) {
    var id = current.value;
    var amount = document.getElementById("amount" + id).value;
       $.ajax({
        type: 'post',
        url: '/onlineStore/catalog',
        dataType: 'json',
        data: { id: id, amount: amount},
        scriptCharset: "utf-8"
    });
}

// $('#demo').pagination({
//     dataSource: products,
//     pageSize: 5,
//     pageRange: null,
//     showPageNumbers: true,
//     callback: function(data, pagination) {
//     // template method of yourself
//     var html = template(data);
//     dataContainer.html(html);
// }
// })