function searchTable() {
    const input = document.getElementById('searchInput');
    const filter = input.value.toUpperCase();
    const table = document.querySelector('.student-table tbody');
    const rows = table.getElementsByTagName('tr');

    for (let i = 0; i < rows.length; i++) {
        const cells = rows[i].getElementsByTagName('td');
        let found = false;
        for (let j = 0; j < cells.length; j++) {
            if (cells[j].innerText.toUpperCase().includes(filter)) {
                found = true;
                break;
            }
        }
        rows[i].style.display = found ? '' : 'none';
    }
}

function openModal() {
    document.getElementById('addStudentModal').style.display = 'block';
}

function closeModal() {
    document.getElementById('addStudentModal').style.display = 'none';
}

// Close modal when clicking outside of it
window.onclick = function (event) {
    const modal = document.getElementById('addStudentModal');
    if (event.target === modal) {
        modal.style.display = 'none';
    }
};

function searchDropdown(selectElement) {
    const filter = selectElement.value.toUpperCase();
    const options = selectElement.getElementsByTagName('option');

    for (let i = 0; i < options.length; i++) {
        const text = options[i].innerText || options[i].textContent;
        options[i].style.display = text.toUpperCase().includes(filter) ? '' : 'none';
    }
}

let rowsToShow = 2;

function loadMore() {
    const rows = document.querySelectorAll('.student-table tbody tr');
    const hiddenRows = Array.from(rows).slice(rowsToShow);

    if (hiddenRows.length > 0) {
        hiddenRows.slice(0, 5).forEach(row => row.style.display = '');
        rowsToShow += 5;
    } else {
        const loadMoreBtn = document.getElementById('loadMoreBtn');
        loadMoreBtn.innerText = 'No more rows to show';
        loadMoreBtn.disabled = true;
    }
}

// Initial hiding of rows beyond the first 10
document.addEventListener('DOMContentLoaded', () => {
    const rows = document.querySelectorAll('.student-table tbody tr');
    rows.forEach((row, index) => {
        if (index >= rowsToShow) {
            row.style.display = 'none';
        }
    });
});


document.addEventListener("DOMContentLoaded", function () {
    fetchUsers(); // Fetch users when the page loads
});

function fetchUsers() {
    fetch('/api/users') // Replace with your actual endpoint
        .then(response => response.json())
        .then(users => {
            const userDropdown = document.getElementById('userDropdown');
            userDropdown.innerHTML = '<option value="">-- Select a User --</option>'; // Reset dropdown
            users.forEach(user => {
                const option = document.createElement('option');
                option.value = user.userId;
                option.textContent = user.username;
                userDropdown.appendChild(option);
            });
        })
        .catch(error => console.error('Error fetching users:', error));
}



function resetDropdown(dropdownId) {
    const dropdown = document.getElementById(dropdownId);
    dropdown.innerHTML = `<option value="">-- Select a ${dropdownId.replace('Dropdown', '')} --</option>`;
    dropdown.disabled = true;
}

document.addEventListener("DOMContentLoaded", function () {
    fetchPrograms();
});

document.addEventListener("DOMContentLoaded", function () {
    fetchPrograms(); // Fetch programs on page load
    fetchCourses(); // Fetch courses on page load
});

function fetchPrograms() {
    fetch('/api/programs') // Replace with your actual API endpoint
        .then(response => {
            if (!response.ok) {
                throw new Error(`Error fetching programs: ${response.status}`);
            }
            return response.json();
        })
        .then(programs => {
            const programDropdown = document.getElementById('programDropdown');
            programDropdown.innerHTML = '<option value="">-- Select a Program --</option>'; // Reset dropdown

            programs.forEach(program => {
                const option = document.createElement('option');
                option.value = program.programId; // Use programId as the value
                option.textContent = program.programName; // Display programName in the dropdown
                programDropdown.appendChild(option);
            });
        })
        .catch(error => {
            console.error(error);
        });
}

function fetchCourses() {
    fetch('/api/courses') // Replace with your actual API endpoint
        .then(response => {
            if (!response.ok) {
                throw new Error(`Error fetching courses: ${response.status}`);
            }
            return response.json();
        })
        .then(courses => {
            const courseDropdown = document.getElementById('courseDropdown');
            courseDropdown.innerHTML = '<option value="">-- Select Courses --</option>'; // Reset dropdown

            courses.forEach(course => {
                const option = document.createElement('option');
                option.value = course.courseId; // Use courseId as the value
                option.textContent = course.courseName; // Display courseName in the dropdown
                courseDropdown.appendChild(option);
            });
        })
        .catch(error => {
            console.error(error);
        });
}



