const delay = 600;
const totalCost = document.getElementById('totalCost');

const doItemNameFocus = item => {
    item.querySelector("#itemName").style.color = "#000000";
}

const doItemNameUnFocus = item => {
    item.querySelector("#itemName").style.color = "#000000";
}

document.querySelectorAll('.pro-qty').forEach(item => {
    const itemLine = item.parentElement.parentElement.parentElement;
    const itemId = item.getAttribute('id').split('_')[1];
    const itemPrice = itemLine.getElementsByClassName('shoping__cart__price')[0];
    const counterClass = item.getElementsByTagName('input')[0];
    const decButton = item.getElementsByClassName('dec qtybtn')[0];
    const incButton = item.getElementsByClassName('inc qtybtn')[0];
    const totalPriceOfCurrItem = itemLine.getElementsByClassName('shoping__cart__total')[0];
    const removeItemButton = itemLine.getElementsByClassName('icon_close')[0];

    incButton.addEventListener('click', event => {
        sendAddRequest(counterClass, itemId, itemPrice, totalPriceOfCurrItem);
        console.log(totalPriceOfCurrItem)
        sleep(delay);
    });

    decButton.addEventListener('click', event => {
        sendDecRequest(counterClass, itemId, itemLine, itemPrice, totalPriceOfCurrItem);
        sleep(delay);
    });

    removeItemButton.addEventListener('click', evt => {
        sendFullRemoveItemRequest(counterClass, itemId, itemLine, itemPrice);
        sleep(delay);
    });
})

const sendAddRequest = (counterClass, itemId, itemPrice, totalPriceOfCurrItem) => {
    fetch('/item/' + itemId, {
        method: 'PUT',
        credentials: "same-origin",
    }).then(res => {
        if (res.ok) {
            counterClass.value = parseInt(counterClass.value) + 1;
            totalPriceOfCurrItem.textContent = parseFloat(totalPriceOfCurrItem.textContent) + parseFloat(itemPrice.textContent);
            totalCost.textContent = parseFloat(totalCost.textContent) + parseFloat(itemPrice.textContent);
        } else {
            counterClass.style.color = "#dd2222";
        }
    });
}

const sendDecRequest = (counterClass, itemId, itemLine, itemPrice, totalPriceOfCurrItem) => {
    fetch('/item/' + itemId, {
        method: 'DELETE',
        credentials: "same-origin",
    }).then(res => {
        if (res.ok) {
            counterClass.value = parseInt(counterClass.value) - 1;
            counterClass.style.color = "#6f6f6f";
            totalPriceOfCurrItem.textContent = parseFloat(totalPriceOfCurrItem.textContent) - parseFloat(itemPrice.textContent);
            totalCost.textContent = parseFloat(totalCost.textContent) - parseFloat(itemPrice.textContent);

            if (parseInt(counterClass.value) === 0) {
                itemLine.remove();
            }
        }
    });
}

const sendFullRemoveItemRequest = (counterClass, itemId, itemLine, itemPrice) => {
    fetch('/item/' + itemId + '/remove', {
        method: 'DELETE',
        credentials: "same-origin",
    }).then(res => {
        if (res.ok) {
            totalCost.textContent = parseFloat(totalCost.textContent) - parseFloat(itemPrice.textContent) * parseInt(counterClass.value);
            itemLine.remove();
        }
    });
}

function sleep(milliseconds) {
    const date = Date.now();
    let currentDate = null;
    do {
        currentDate = Date.now();
    } while (currentDate - date < milliseconds);
}