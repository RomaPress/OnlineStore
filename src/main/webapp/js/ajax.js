function addProduct(current) {
    var id = current.value;
    var amount = document.getElementById("amount" + id).value;
    $.ajax({
        type: 'post',
        url: '/onlineStore/catalog',
        dataType: 'json',
        data: {id: id, amount: amount},
        scriptCharset: "utf-8"
    });
}

function checkPassword() {
    var password = document.getElementById('password').value;
    var firstName = document.getElementById('firstName').value;
    var lastName = document.getElementById('lastName').value;
    var phoneNumber = document.getElementById('phoneNumber').value;
    var city = document.getElementById('city').value;
    var postOffice = document.getElementById('postOffice').value;
    $.ajax({
        type: 'post',
        url: '/onlineStore/profile',
        dataType: 'json',
        data: {
            password: password,
            firstName: firstName,
            lastName: lastName,
            phoneNumber: phoneNumber,
            city: city,
            postOffice: postOffice
        },
        scriptCharset: "utf-8",
        success: function (isCorrect) {
            if (!isCorrect) {
                $("#passwordError").show();
            } else {
                $("#passwordError").hide();
            }
        },
        error: function () {
            document.getElementById('popUp').style.display = 'none';
        }
    });
}


$('#demo').pagination({

    dataSource: products,
    callback: function (data, pagination) {
        console.log(data);
        console.log(pagination);
        // template method of yourself

        var html = "";

        $.each(data.products, function (i, obj) {
                html += '<div class="col-3 my__col product">' +
                    '<div class="catalog__img">' +
                    '<img src="${pageContext.request.contextPath}/img/' + obj.id + '.jpg" alt="img" width="175px" height="175px">' +
                    '</div>' +
                    '<p class="name__product"><b>' + obj.name + ' </b>' + obj.price + '</p>' +
                    '<p>' + obj.amount + ' на складе</p>' +
            '<div class="form_input_number">' +
            '<button class="my__catalogBtn minus" type="button"  onclick="this.nextElementSibling.stepDown()"> <b>-</b></button>' +
            '<input class="my__inputN" type="number" readonly id="amount' + obj.id + '" min="1"  max="' + obj.amount + '"/>' +
            '<button class="my__catalogBtn plus" type="button" onclick="this.previousElementSibling.stepUp()"><b>+</b></button> <br>' +
            '<button class="my__catalogBtn btn" type="button" value="' + obj.id + '" onclick=addProduct(this)>В корзину</button>' +
            ' </div> </div>';
    });
        demo.html(html);

}
});