const form = document.getElementById('addItemForm');
const name = document.getElementById("name");
const cost = document.getElementById("cost");
const amount = document.getElementById("amount");
const country = document.getElementById("country");
const chemicalComposition = document.getElementById("chemicalComposition");
const description = document.getElementById("description");
const instruction = document.getElementById("instruction");
const capacity = document.getElementById("capacity");

const pests = [];

const setError = (element, message) => {
    const inputControl = element.parentElement;
    const errorDisplay = inputControl.querySelector('.error');

    errorDisplay.innerText = message;
    inputControl.classList.add('error');
    inputControl.classList.remove('success');
}

const setSuccess = (element, message) => {
    const inputControl = element.parentElement;
    const errorDisplay = inputControl.querySelector('.error');

    errorDisplay.innerText = message;
    inputControl.classList.add('success');
    inputControl.classList.remove('error');
}

name.addEventListener('focusin', ev => validateName(name.value.trim()));
name.addEventListener('focusout', ev => validateName(name.value.trim()));

cost.addEventListener('focusin', ev => validateCost(cost.value.trim()));
cost.addEventListener('focusout', ev => validateCost(cost.value.trim()));

amount.addEventListener('focusin', ev => validateAmount(amount.value.trim()));
amount.addEventListener('focusout', ev => validateAmount(amount.value.trim()));

country.addEventListener('focusin', ev => validateCountry(country.value.trim()));
country.addEventListener('focusout', ev => validateCountry(country.value.trim()));

chemicalComposition.addEventListener('focusin', ev => validateChemComp(chemicalComposition.value.trim()));
chemicalComposition.addEventListener('focusout', ev => validateChemComp(chemicalComposition.value.trim()));


description.addEventListener('focusin', ev => validateDescription(description.value.trim()));
description.addEventListener('focusout', ev => validateDescription(description.value.trim()));

capacity.addEventListener('focusin', ev => validateCapacity(capacity.value.trim()));
capacity.addEventListener('focusout', ev => validateCapacity(capacity.value.trim()));

instruction.addEventListener('focusin', ev => validateInstruction(instruction.value.trim()));
instruction.addEventListener('focusout', ev => validateInstruction(instruction.value.trim()));

// pests.addEventListener('click', ev => validatePestList(pests.checked));



const validateInputs = () => {
    const isValidName = validateName(name.value.trim())
    const isValidCost = validateCost(cost.value.trim());
    const isValidAmount = validateAmount(amount.value.trim());
    const isValidCountry = validateCountry(country.value.trim());
    const isValidChemComp = validateChemComp(chemicalComposition.value.trim());
    const isValidDescription = validateDescription(description.value.trim());
    const isValidInstruction = validateInstruction(instruction.value.trim());
    const isValidCapacity = validateCapacity(capacity.value.trim());

    return isValidCountry && isValidCost && isValidAmount
        && isValidName && isValidDescription && isValidChemComp && isValidInstruction && isValidCapacity;
}

console.log(validateInputs())


function validateName(nameValue) {

    const nameRegex = /[a-zA-Zа-яА-Я!\s]/

    if (nameValue === '') {
        setError(name, 'Имя не может быть пустым');
    } else if (nameValue.match(nameRegex)) {
        setSuccess(name, '');
        return true;
    }
    return false;
}

function validateCountry(countryValue) {

    const countryRegex = /[a-zA-Zа-яА-Я\s]/

    if (countryValue === '') {
        setError(country, 'Страна не может быть пустой');
    }
    if (countryValue.match(countryRegex)) {
        setSuccess(country, '');
        return true;
    }
    return false;
}

function validateDescription(descriptionValue) {

    const descRegex = /[a-zA-Zа-яА-Я,.!?\s]/

    if (descriptionValue === '') {
        setError(description, 'Описание не может быть пустым');
    }
    if (descriptionValue.match(descRegex)) {
        setSuccess(description, '');
        return true;
    }
    return false;
}

function validateInstruction(instructionValue) {

    const instRegex = /[a-zA-Zа-яА-Я,.!?\s]/

    if (instructionValue === '') {
        setError(instruction, 'Инструкция не может быть пустой');
    }
    if (instructionValue.match(instRegex)) {
        setSuccess(instruction, '');
        return true;
    }
    return false;
}

function validateChemComp(chemCompValue) {

    const chemCompRegex = /[a-zA-Zа-яА-Я,.!?\s]/

    if (chemCompValue === '') {
        setError(chemicalComposition, 'Химический состав не может быть пустым');
    }
    if (chemCompValue.match(chemCompRegex)) {
        setSuccess(chemicalComposition, '');
        return true;
    }
    return false;
}


function validateCost(costValue) {

    const costReg = /[\d]/

    if (costValue === '') {
        setError(cost, 'Цена не может отсутствовать');
    }
    if (parseFloat(costValue) < 0) {
        setError(cost, 'Формат цены указан не верно');
    }

    if (parseFloat(costValue) > 0 && costValue.match(costReg)) {
        setSuccess(cost, '');
        return true;
    }
    return false;
}

function validateCapacity(capacityValue) {

    const capReg = /[\d]/

    if (capacityValue === '') {
        setError(capacity, 'Объем не может отсутствовать');
    }
    if (parseInt(capacityValue) < 0) {
        setError(capacity, 'Формат объема указан не верно');
    }
    if (parseInt(capacityValue) > 0 && capacityValue.match(capReg)) {
        setSuccess(capacity, '');
        return true;
    }
    return false;
}

function validateAmount(amountValue) {

    const amountRegex = /[\d]/

    if (parseInt(amountValue) < 0) {
        setError(amount, 'Количество не может отрицательным');
    }
    if (amountValue === "") {
        setError(amount, 'Укажите количество');
    }
    if (amountValue > 0 && amountValue.match(amountRegex)) {
        setSuccess(amount, '');
        return true;
    }
    return false;
}

// function validatePestList(isChecked) {
//     if (isChecked) {
//         setSuccess(pests, '')
//         return true;
//     }
//
//     setError(pests, 'Необходимо указать вредителей');
//     return false;
// }





