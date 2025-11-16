document.addEventListener("DOMContentLoaded", () => {
    const wrapper = document.querySelector(".wrapper");
    const table = document.querySelector("table");
    const columns = document.querySelectorAll("table thead th");
    const tbody = table.querySelector("tbody");

    let activeIndex = null;
    let currentPage = 1;
    const rowsPerPage = 10; // cantidad de registros por página

    const initResize = (index) => {
        if (!columns[index] || !wrapper)
            return;
        activeIndex = index;
        document.body.style.cursor = "col-resize";
        window.addEventListener("mousemove", resize);
        window.addEventListener("mouseup", stopResize);
    };

    const resize = (e) => {
        const column = columns[activeIndex];
        if (!column || !wrapper)
            return;
        const nextWidth = e.clientX - column.offsetLeft - wrapper.offsetLeft - 24;
        if (nextWidth > 50)
            column.style.width = `${nextWidth}px`;
    };

    const stopResize = () => {
        document.body.style.cursor = "default";
        window.removeEventListener("mousemove", resize);
        window.removeEventListener("mouseup", stopResize);
    };

    const sortTable = (index) => {
        const rows = Array.from(tbody.querySelectorAll("tr"));
        const currentDir = columns[index].dataset.sort || "asc";
        const getCellValue = (row, idx) => row.children[idx].innerText.trim().toLowerCase();
        const isNumeric = rows.every((row) => !isNaN(parseFloat(getCellValue(row, index))) && isFinite(getCellValue(row, index)));
        const isDate = rows.every((row) => !isNaN(Date.parse(getCellValue(row, index))));

        rows.sort((a, b) => {
            let A = getCellValue(a, index);
            let B = getCellValue(b, index);
            if (isNumeric) {
                A = parseFloat(A) || 0;
                B = parseFloat(B) || 0;
            } else if (isDate) {
                A = new Date(A);
                B = new Date(B);
            }
            if (A < B)
                return currentDir === "asc" ? -1 : 1;
            if (A > B)
                return currentDir === "asc" ? 1 : -1;
            return 0;
        });

        columns.forEach((col) => (col.dataset.sort = ""));
        columns[index].dataset.sort = currentDir === "asc" ? "desc" : "asc";
        rows.forEach((row) => tbody.appendChild(row));
        showPage(currentPage);
    };

    const paginate = () => {
        const totalRows = tbody.querySelectorAll("tr:not([data-hidden='true'])").length;
        const totalPages = Math.ceil(totalRows / rowsPerPage);
        let pagination = document.querySelector(".pagination");
        if (!pagination) {
            pagination = document.createElement("div");
            pagination.className = "pagination";
            pagination.style.textAlign = "center";
            pagination.style.marginTop = "10px";
            wrapper.parentNode.appendChild(pagination);
        }
        pagination.innerHTML = "";
        const prevBtn = document.createElement("button");
        prevBtn.textContent = "Anterior";
        prevBtn.disabled = currentPage === 1;
        prevBtn.onclick = () => changePage(currentPage - 1);
        const nextBtn = document.createElement("button");
        nextBtn.textContent = "Siguiente";
        nextBtn.disabled = currentPage === totalPages;
        nextBtn.onclick = () => changePage(currentPage + 1);
        const info = document.createElement("span");
        info.textContent = ` Página ${currentPage} de ${totalPages} `;
        info.style.margin = "0 10px";
        pagination.appendChild(prevBtn);
        pagination.appendChild(info);
        pagination.appendChild(nextBtn);
    };

    const changePage = (page) => {
        const totalRows = tbody.querySelectorAll("tr:not([data-hidden='true'])").length;
        const totalPages = Math.ceil(totalRows / rowsPerPage);
        if (page < 1 || page > totalPages)
            return;
        currentPage = page;
        showPage(page);
    };

    const showPage = (page) => {
        const rows = Array.from(tbody.querySelectorAll("tr"));
        const visibleRows = rows.filter(r => r.getAttribute("data-hidden") !== "true");
        const start = (page - 1) * rowsPerPage;
        const end = start + rowsPerPage;
        visibleRows.forEach((row, i) => row.style.display = i >= start && i < end ? "" : "none");
        rows.filter(r => r.getAttribute("data-hidden") === "true").forEach(r => r.style.display = "none");
        paginate();
    };

    const createFilters = () => {
        const headerRow = table.querySelector("thead tr");
        const filterRow = document.createElement("tr");

        columns.forEach((th, index) => {
            const filterCell = document.createElement("th");
            if (index < columns.length - 1) {
                const input = document.createElement("input");
                input.type = "text";
                input.placeholder = "Filtrar...";
                input.style.width = "90%";
                input.style.padding = "3px 4px";
                input.style.fontSize = "12px";
                input.style.background = "transparent";
                input.style.border = "none";
                input.style.borderBottom = "1px solid #ccc";
                input.style.outline = "none";
                input.style.color = "#333";
                input.style.transition = "border-color 0.2s ease";
                input.addEventListener("focus", () => input.style.borderBottom = "1px solid #666");
                input.addEventListener("blur", () => input.style.borderBottom = "1px solid #ccc");
                input.dataset.colIndex = index;
                input.addEventListener("keyup", filterTable);
                filterCell.appendChild(input);
            }
            filterRow.appendChild(filterCell);
        });

        headerRow.parentNode.appendChild(filterRow);
    };

    const filterTable = () => {
        const filters = Array.from(document.querySelectorAll("thead input"));
        const rows = Array.from(tbody.querySelectorAll("tr"));
        rows.forEach((row) => {
            const cells = row.querySelectorAll("td");
            const match = filters.every((input, i) => {
                if (!input.value)
                    return true;
                const cellValue = cells[i]?.innerText.toLowerCase() || "";
                return cellValue.includes(input.value.toLowerCase());
            });
            row.setAttribute("data-hidden", match ? "false" : "true");
        });
        showPage(1);
    };

    columns.forEach((th, index) => {
        th.addEventListener("click", () => sortTable(index));
        const span = th.querySelector(".draggable");
        if (span)
            span.addEventListener("mousedown", (e) => {
                e.stopPropagation();
                initResize(index);
            });
    });

    createFilters();
    showPage(currentPage);
    window.initResize = initResize;
});
