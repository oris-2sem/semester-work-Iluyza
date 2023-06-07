const form = document.getElementById('signup-form');
const email = document.getElementById("email");
const phoneNumber = document.getElementById("phoneNumber");
const firstName = document.getElementById("firstName");
const password = document.getElementById("password");
const password2 = document.getElementById("password2");
const agreeTerm = document.getElementById("agree-term");

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

email.addEventListener('focusin', ev => validateEmail(email.value.trim()));
email.addEventListener('focusout', ev => validateEmail(email.value.trim()));

phoneNumber.addEventListener('focusin', ev => validatePhoneNumber(phoneNumber.value.trim()));
phoneNumber.addEventListener('focusout', ev => validatePhoneNumber(phoneNumber.value.trim()));

firstName.addEventListener('focusin', ev => validateName(firstName.value.trim()));
firstName.addEventListener('focusout', ev => validateName(firstName.value.trim()));

password.addEventListener('focusin', ev => validatePasswords(password.value.trim(), password2.value.trim()));
password.addEventListener('focusout', ev => validatePasswords(password.value.trim(), password2.value.trim()));

password2.addEventListener('focusin', ev => validatePasswords(password.value.trim(), password2.value.trim()));
password2.addEventListener('focusout', ev => validatePasswords(password.value.trim(), password2.value.trim()));

agreeTerm.addEventListener('click', ev => validateAgreeTerm(agreeTerm.checked));

form.addEventListener('submit', event => {
    event.preventDefault();
    const isValidEmail = validateEmail(email.value.trim());
    const isValidNumber = validatePhoneNumber(phoneNumber.value.trim());
    const isValidFirstName = validateName(firstName.value.trim());
    const isValidPassword = validatePasswords(password.value.trim(), password2.value.trim());
    const isValidAgreeTerm = validateAgreeTerm(agreeTerm.checked);

    if(isValidEmail && isValidNumber && isValidFirstName && isValidPassword && isValidAgreeTerm)
    {
        sendRegistrationRequest();
    }

});

function sendRegistrationRequest() {
    const formData = new FormData(form);
    const data = Object.fromEntries(formData);

    fetch('/auth/register', {
        method: 'POST',
        credentials: "same-origin",
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    }).then(res => {
        if (res.ok) {
            email.parentElement.querySelector('.error').style.color = "#09c372";
            setSuccess(email, 'Аккаунт успешно создан');
            setTimeout(() => window.location.href = '/auth/login', 2000)
        } else {
            res.text().then(function (text) {
                email.parentElement.querySelector('.error').style.color = "#ff3860";
                setError(email, text)
            });
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

function validateEmail(emailValue) {
    const emailRegex = /^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;

    if (emailValue === '') {
        setError(email, 'Email не может быть пустым');
    } else {
        if (emailValue.match(emailRegex)) {
            setSuccess(email, '');
            return true;
        }
        setError(email, 'Неправильный формат email');
    }
    return false;
}

function validatePhoneNumber(phoneNumberValue) {
    const phoneRegex = /(\+7|8)[- _]*\(?[- _]*(\d{3}[- _]*\)?([- _]*\d){7}|\d\d[- _]*\d\d[- _]*\)?([- _]*\d){6})/g;

    if (phoneNumberValue === '') {
        setError(phoneNumber, 'Номер телефона не может быть пустым');
    } else {
        if (phoneNumberValue.match(phoneRegex)) {
            setSuccess(phoneNumber, '');
            return true;
        }
        setError(phoneNumber, 'Неправильный формат номера')
    }

    return false;
}

function validateName (firstNameValue){
    if (firstNameValue === '') {
        setError(firstName, 'Имя не может быть пустым');
    } else {
        if (firstNameValue.length < 40) {
            setSuccess(firstName, '');
            return true;
        }
        setError(firstName, 'Слишком длинное имя')
    }

    return false;
}

function validatePasswords(passwordValue, password2Value) {
    const passwordRegex = /[a-zA-Z0-9!@#$%^&*]{7,255}$/;

    if (passwordValue.match(passwordRegex)) {
        if (passwordValue === password2Value) {
            setSuccess(password, '');
            setSuccess(password2, '');
            return true;
        } else {
            setError(password, 'Пароли не совпадают');
            setError(password2, '');
        }

    } else {
        setError(password, 'Пароль слишком короткий');
        setError(password2, '');
    }

    return false;
}

function validateAgreeTerm(isAgreed) {
    if (isAgreed) {
        setSuccess(agreeTerm, '')
        return true;
    }

    setError(agreeTerm, 'Требуется согласие');
    return false;
}