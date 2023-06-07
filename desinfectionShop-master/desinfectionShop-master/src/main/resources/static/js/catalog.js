const selectEl = document.getElementById('sortSelector');

const setUpStartSortOption = () => {

    const currSortValue = getCurrSortOptionValue();
    for (var i = 0; i < selectEl.options.length; ++i) {
        if (selectEl.options[i].value === currSortValue) {
            selectEl.options[i].setAttribute('selected', 'selected');
        }
    }
    $('select').niceSelect('update');
}

const getCurrSortOptionValue = () => {
    if (window.location.href.includes('sortValue')) {
        let startIndex = window.location.href.indexOf('sortValue=');
        let endIndex = window.location.href.indexOf('&', startIndex + 9);
        if (endIndex === -1) {
            endIndex = window.location.href.length;
        }

        return window.location.href.substring(startIndex + 10, endIndex);
    }
    return 'popularDESC';
}

const redirectOnSelectedSortOption = () => {
    const currSortOptionValue = getCurrSortOptionValue();
    const selectedOptionValue = selectEl.options[selectEl.selectedIndex].value;

    const urlHasParams = window.location.href.includes('?');
    const urlHasSortValueParam = window.location.href.includes('sortValue');

    if (selectedOptionValue !== currSortOptionValue) {
        if (!urlHasParams && !urlHasSortValueParam) {
            window.location.href = window.location.href + '?' + 'sortValue=' + selectedOptionValue;
        } else {
            if (urlHasParams && !urlHasSortValueParam) {
                window.location.href = window.location.href + '&sortValue=' + selectedOptionValue;
            } else {
                window.location.href = window.location.href.replace(currSortOptionValue, selectedOptionValue);
            }
        }
    }
}

const doItemNameFocus = item => {
    item.querySelector("#itemName").style.color = "#009CF0";
}

const doItemNameUnFocus = item => {
    item.querySelector("#itemName").style.color = "#000000";
}
