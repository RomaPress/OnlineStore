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
