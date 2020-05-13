new fullpage('#fullpage', {
    autoScrolling: true,
    navigation: true
});

const navRight = document.querySelector('#fp-nav');
console.log(navRight.children)

let buttons = document.querySelectorAll(".cartButton");


buttons.forEach((button) => {
    button.addEventListener('click',function () {
        const productID = parseInt(this.dataset.id);
        const theData = {productID : productID};
        fetch('add-to-cart/',{
            method:'POST',
            body: productID
        })
            .then(function () {
                location.reload();
            })
    })
});


document.querySelectorAll(".cart-btn-plus").forEach((button) =>{
    button.addEventListener("click",function () {
        const id = parseInt(this.dataset.id);
        const quantity = parseInt(document.getElementById('quantity-'+id).innerHTML);
        changeCart(id,quantity+1);
    })
});

document.querySelectorAll(".cart-btn-").forEach((button)=>{
    button.addEventListener("click",function () {
        const id = parseInt(this.dataset.id);
        const quantity = parseInt(document.getElementById('quantity-'+id).innerHTML);
        changeCart(id,quantity-1);
    })
});

document.querySelectorAll(".cart-btn-remove").forEach((button)=>{
    button.addEventListener("click",function () {
        const id = parseInt(this.dataset.id);
        const quantity = parseInt(document.getElementById('quantity-'+id).innerHTML);
        changeCart(id,0);
    })
});

function changeCart(id,changeTo) {
    console.log("Meghívva");
    const row = document.getElementById('cartItem-'+id);
    const quantity = document.getElementById('quantity-'+id);
    const price = document.getElementById('price-'+id);

    quantity.innerHTML = changeTo;
    price.innerHTML = '$' +parseInt(row.dataset.price) * changeTo;

    if (parseInt(quantity.innerHTML) == 0){
        row.remove();
    }

    let data = {
        productID : id,
        newValue : changeTo
    };

    fetch('change-cart/',{
        method:'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    }).then(response => console.log(response));
}


