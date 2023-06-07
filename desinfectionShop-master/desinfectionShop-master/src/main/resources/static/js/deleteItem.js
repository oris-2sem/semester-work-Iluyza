const form = document.getElementById("deleteButton")

const getItemId = () => {
    return window.location.href.substring(window.location.href.indexOf('item/') + 5);
}



form.addEventListener('submit', e => {
    e.preventDefault();
    deleteData();
})


function deleteData() {
    return fetch('/admin/item/' + getItemId(), {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(res => {
            window.location.href = '/catalog/page/1'
        });
}