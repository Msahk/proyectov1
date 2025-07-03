document.getElementById("password").addEventListener("input", function() {
    const password = this.value;

    const patronMayuscula = /[A-Z]/;
    const patronMinuscula = /[a-z]/;
    const patronNumero = /[0-9]/;
    const patronSimbolo = /[!@#$%^&*(),.?"{}><_-]/;

    updateValidation(
        "mayuscula",
        patronMayuscula.test(password)
    );
    updateValidation(
        "minuscula",
        patronMinuscula.test(password)
    );
    updateValidation(
        "numero",
        patronNumero.test(password)
    );
    updateValidation(
        "simbolo",
        patronSimbolo.test(password)
    );
});

function updateValidation(elementId, isValid) {
    const element = document.getElementById(elementId);
    const icon = element.querySelector("i");

    if (isValid) {
        element.classList.remove("invalid");
        element.classList.add("valid");
    } else {
        element.classList.remove("valid");
        element.classList.add("invalid");
    }
}