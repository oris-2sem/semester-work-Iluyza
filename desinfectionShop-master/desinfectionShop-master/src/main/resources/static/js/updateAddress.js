const form = document.getElementById("updateAddressForm")
const address = document.getElementById("address")
const button = document.getElementById("account-btn")
let oldVal = address.value;


const sendUpdateRequest = function () {
    const formData = new FormData(form);
    const data = Object.fromEntries(formData);
    console.log("note = " + data)
    if (address.value !== oldVal) {
        fetch('/profile', {
            method: 'PATCH',
            credentials: "same-origin",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        }).then(res => {
            console.log("send", form)
            button.textContent = 'Редактировать'
            $('#address').prop('readonly', true)
                .prop('disabled', true)
                .css('color', '#5a6268')
                .css('border', 'none')
                .css('font-weight', '300');
            oldVal = address.value;
            res.ok;
        });
    } else {
        button.textContent = 'Редактировать'
        $('#address').prop('readonly', true)
            .prop('disabled', true)
            .css('color', '#5a6268')
            .css('border', 'none')
            .css('font-weight', '300');
        throw stop()
    }
}

form.addEventListener('submit', event => {
    event.preventDefault();
    console.log("listener")
    if (button.textContent === 'Сохранить') {
        sendUpdateRequest();
    }
    if (button.textContent === 'Редактировать') {
        console.log("updating")
        button.textContent = 'Сохранить';
        $('#address').removeAttr('readonly')
            .removeAttr('disabled')
            .css('color', 'black')
            .css('font-weight', '500')
            .css('border-bottom', 'solid 1px')
            .css('border-color', '#d2d4d2');
            // .css('border-radius', '10px');
    }
});