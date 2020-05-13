new fullpage('#fullpage', {
    autoScrolling: true,
    navigation: true,
    afterRender: () => {
        TweenMax.staggerFrom('.welcomeMessage', .4, {
            opacity: 0,
            delay: .2,
            y: -40,
            ease: Expo.ease
        })
    },
    onLeave: (origin, destination, direction) => {
        if (destination.index > 0 && destination.index < 8) {
            const currentSection = destination.item;
            if (destination.index % 2 === 0) {
                productLeftAnimation(currentSection)
            } else {
                productRightAnimation(currentSection)
            }
        }
    }
});

const productLeftAnimation = (section) => {
    if (!section.classList.contains('animated')) {
        const pageContent = section.childNodes[0].childNodes[1].childNodes[1];
        const productImage = pageContent.children[0];
        const productText = pageContent.children[1];
        TweenMax.staggerFrom(productText, 0.4, {
            opacity: 0,
            delay: 0.65,
            x: 40,
            ease: Expo.ease
        });
        TweenMax.staggerFrom(productImage, 0.45, {
            opacity: 0,
            delay: 0.75,
            x: -40,
            ease: Expo.ease
        });
        section.classList.add('animated')
    }
}

const productRightAnimation = (section) => {
    if (!section.classList.contains('animated')) {
        const pageContent = section.childNodes[0].childNodes[1].childNodes[1];
        const productImage = pageContent.children[1];
        const productText = pageContent.children[0];
        TweenMax.staggerFrom(productImage, 0.45, {
            opacity: 0,
            delay: 0.75,
            x: 40,
            ease: Expo.ease
        });
        TweenMax.staggerFrom(productText, 0.4, {
            opacity: 0,
            delay: 0.65,
            x: -40,
            ease: Expo.ease
        });
        section.classList.add('animated')
    }
}


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


