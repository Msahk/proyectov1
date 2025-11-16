const body = document.querySelector("body"),
    sidebar = body.querySelector(".sidebar"),
    toggle = body.querySelector(".toggle"),
    searchBtn = body.querySelector(".search-box"),
    modeSwitch = body.querySelector(".toggle-switch"),
    modeText = body.querySelector(".mode-text");

    toggle.addEventListener("click" , () =>{
        sidebar.classList.toggle("close");

        if(sidebar.classList.contains("close")){
            localStorage.setItem('menu', 'close');
        } else {
            localStorage.setItem('menu', 'open');
        }
    });


    modeSwitch.addEventListener("click" , () =>{
        body.classList.toggle("dark");
        

        if(body.classList.contains("dark")){
            modeText.innerText = "Claro";
            localStorage.setItem('dark-mode', 'true');
        } else {
            modeText.innerText = "Oscuro";
            localStorage.setItem('dark-mode', 'false');
        }
    });


if(localStorage.getItem('dark-mode') === 'true'){
	document.body.classList.add('dark');
} else {
	document.body.classList.remove('dark');
}

if(localStorage.getItem('menu') === 'close'){
    sidebar.classList.add('close');
} else {
    sidebar.classList.remove('close');
}