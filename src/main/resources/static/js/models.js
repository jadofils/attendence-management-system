// Get modal elements
const updateStudentModal = document.getElementById('updateStudentModal'); // Update Modal
const insertModal = document.getElementById('insertFormContainer'); // Insert Modal
const studentModal = document.getElementById('studentModal'); // View Modal
const deleteModal = document.getElementById('deleteModal'); // Delete Modal
const studentDetails = document.getElementById('studentDetails'); // View Details Container

let selectedStudentId = null; // For delete operation

// Open Insert Modal
function openInsertModal() {
    insertModal.style.display = 'block';
}

// Close Insert Modal
function closeInsertModal() {
    insertModal.style.display = 'none';
}

// Open Update Modal
function openUpdateModal(studentId) {
    updateStudentModal.style.display = 'block'; // Show update modal
}

// Close Update Modal
function closeUpdateModal() {
    updateStudentModal.style.display = 'none'; // Hide update modal
}

// Open Modal by ID
function openModalById(modalId) {
    document.getElementById(modalId).style.display = 'block';
}

// Close Modal by ID
function closeModalById(modalId) {
    document.getElementById(modalId).style.display = 'none';
    if (modalId === 'studentModal') {
        studentDetails.innerHTML = ''; // Clear content on close
    }
}

// View Student Details
document.querySelectorAll('.view-link').forEach(link => {
    link.addEventListener('click', function () {
        const studentId = this.getAttribute('data-student-id');
        fetch(`/api/students/${studentId}`)
            .then(response => response.json())
            .then(data => {
                studentDetails.innerHTML = `
                    <p><strong>ID:</strong> ${data.studentId}</p>
                    <p><strong>Name:</strong> ${data.firstName} ${data.lastName}</p>
                    <p><strong>Email:</strong> ${data.email}</p>
                    <p><strong>Phone:</strong> ${data.phoneNumber}</p>
                    <p><strong>Program:</strong> ${data.programName}</p>
                    <p><strong>Courses:</strong> ${data.courseNames.join(', ')}</p>
                `;
                openModalById('studentModal');
            })
            .catch(err => console.error('Error fetching student details:', err));
    });
});

// Delete Student Confirmation
document.querySelectorAll('.delete-link').forEach(link => {
    link.addEventListener('click', function () {
        selectedStudentId = this.getAttribute('data-student-id');
        openModalById('deleteModal');
    });
});

document.getElementById('confirmDelete').addEventListener('click', () => {
    fetch(`/api/students/delete/${selectedStudentId}`, { method: 'DELETE' })
        .then(response => {
            if (response.ok) {
                alert('Student deleted successfully!');
                location.reload();
            } else {
                alert('Failed to delete student.');
            }
        })
        .catch(err => console.error('Error deleting student:', err));
});

// Close modal when clicking outside
window.onclick = function(event) {
    if (event.target === updateStudentModal) { // Update Modal
        closeUpdateModal();
    }
    if (event.target === insertModal) { // Insert Modal
        closeInsertModal();
    }
    if (event.target === studentModal) { // View Modal
        closeModalById('studentModal');
    }
    if (event.target === deleteModal) { // Delete Modal
        closeModalById('deleteModal');
    }
};

// Close modal when clicking close button (×)
document.querySelectorAll('.close-button').forEach(button => {
    button.addEventListener('click', function () {
        const modalId = this.closest('.modal').id;
        if (modalId === 'updateStudentModal') { // Update Modal
            closeUpdateModal();
        } else if (modalId === 'insertFormContainer') { // Insert Modal
            closeInsertModal();
        } else {
            closeModalById(modalId);
        }
    });
});
