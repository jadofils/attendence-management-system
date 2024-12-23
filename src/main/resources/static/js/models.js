   // Get modal elements
   const addStudentModal = document.getElementById('addStudentModal');
   const studentModal = document.getElementById('studentModal');
   const studentDetails = document.getElementById('studentDetails');
   const deleteModal = document.getElementById('deleteModal');
   let selectedStudentId = null;
   
   // Function to open Add Student modal
   function openModal() {
       addStudentModal.style.display = 'block';
   }
   
   // Function to close Add Student modal
   function closeModal() {
       addStudentModal.style.display = 'none';
   }
   
   // Function to open specific modal by ID
   function openModalById(modalId) {
       document.getElementById(modalId).style.display = 'block';
   }
   
   // Function to close specific modal by ID
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
       if (event.target === addStudentModal) {
           closeModal();
       }
       if (event.target === studentModal) {
           closeModalById('studentModal');
       }
       if (event.target === deleteModal) {
           closeModalById('deleteModal');
       }
   };
   
   // Close modal when clicking close button (×)
   document.querySelectorAll('.close-button').forEach(button => {
       button.addEventListener('click', function() {
           const modalId = this.closest('.modal').id;
           if (modalId === 'addStudentModal') {
               closeModal();
           } else {
               closeModalById(modalId);
           }
       });
   });