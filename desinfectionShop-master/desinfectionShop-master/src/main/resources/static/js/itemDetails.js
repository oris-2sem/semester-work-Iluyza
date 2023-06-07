const counterStack = document.getElementById('counterStack');
const addItemToOrderButton = document.getElementById('addItemToOrderButton');
let addItemToOrderButtonIsActive = false;

const itemCost = parseFloat(document.getElementById('itemCost').textContent.split(' ')[0]);
const amountToBuyCounter = document.getElementById('amountToOrder');
const decButton = document.getElementsByClassName('dec qtybtn')[0];
const incButton = document.getElementsByClassName('inc qtybtn')[0];

const currOrderAmountMain = document.getElementById('currOrderAmount-main');
const currOrderCostMain = document.getElementById('currOrderCost-main');
const currOrderAmountMobile = document.getElementById('currOrderAmount-main');
const currOrderCostMobile = document.getElementById('currOrderCost-main');
let currOrderAmount = parseInt(currOrderAmountMain.textContent);
let currOrderCost = parseFloat(currOrderCostMain.textContent.split());


{
    if (parseInt(amountToBuyCounter.value) !== 0) {
        changeStyleToActiveOrder();
    } else {
        changeStyleToInactiveOrder();
    }
}

addItemToOrderButton.addEventListener('click', e => {
    e.preventDefault();

    if (!addItemToOrderButtonIsActive) {
        changeStyleToActiveOrder();

        sendAddingItemRequest();
        sleep(800);
    } else {
        window.location.href = '/cart';
    }
})

decButton.addEventListener('click', e => {
    e.preventDefault();

    amountToBuyCounter.style.color = "#6f6f6f";
    if (parseInt(amountToBuyCounter.value) <= 1) {
        changeStyleToInactiveOrder();
    }

    sendRemovingItemRequest();
    sleep(800);
})

incButton.addEventListener('click', e => {
    e.preventDefault();

    sendAddingItemRequest();
    sleep(800);
})

function sendAddingItemRequest() {
    fetch(window.location.href, {
        method: 'PUT',
        credentials: "same-origin",
    }).then(res => {
        if (res.ok) {
            amountToBuyCounter.value = parseInt(amountToBuyCounter.value) + 1;

            currOrderCost = currOrderCost + itemCost;
            currOrderAmount = currOrderAmount + 1;
            refreshCurrOrderInfo();
        } else {
            amountToBuyCounter.style.color = "#dd2222";
        }
    });
}

function sendRemovingItemRequest() {
    fetch(window.location.href, {
        method: 'DELETE',
        credentials: "same-origin",
    }).then(res => {
        if (res.ok) {
            amountToBuyCounter.value = parseInt(amountToBuyCounter.value) - 1;

            currOrderCost = currOrderCost + itemCost;
            currOrderAmount = currOrderAmount - 1;
            refreshCurrOrderInfo();
        }
    });
}

function refreshCurrOrderInfo() {
    currOrderAmountMain.innerText = currOrderAmount;
    currOrderAmountMobile.innerText = currOrderAmount;
    currOrderCostMain.innerText = currOrderCost + ' ₽';
    currOrderCostMobile.innerText = currOrderCost + ' ₽';
}

function sleep(milliseconds) {
    const date = Date.now();
    let currentDate = null;
    do {
        currentDate = Date.now();
    } while (currentDate - date < milliseconds);
}

function changeStyleToActiveOrder() {
    addItemToOrderButton.innerText = 'Перейти в корзину';
    addItemToOrderButton.style.background = '#0b76ef';
    counterStack.style.display = 'inline-block';
    addItemToOrderButtonIsActive = true;
}

function changeStyleToInactiveOrder() {
    addItemToOrderButton.innerText = 'Добавить в корзину';
    addItemToOrderButton.style.background = '#7fad39';
    counterStack.style.display = 'none';
    addItemToOrderButtonIsActive = false;
}
