// Get modal elements
const addProgramModal = document.getElementById('addProgramModal');
const closeBtn = document.querySelector('#addProgramModal .close-button');
const deleteConfirmModal = document.getElementById('deleteConfirmModal');
const deleteConfirmBtn = document.getElementById('confirmDeleteBtn');
const deleteCancelBtn = document.getElementById('cancelDeleteBtn');
let programIdToDelete = null;

// Function to open modal
function openModal() {
addProgramModal.style.display = 'block';
}

// Function to close modal
function closeModal() {
addProgramModal.style.display = 'none';
// Clear form data when closing
document.getElementById('addProgramForm').reset();
}

// Function to open delete confirmation modal
function openDeleteConfirmModal(programId) {
programIdToDelete = programId;
deleteConfirmModal.style.display = 'block';
}

// Function to close delete confirmation modal
function closeDeleteConfirmModal() {
deleteConfirmModal.style.display = 'none';
programIdToDelete = null;
}

// Add click event to the Add Program button
document.querySelector('.add-program-link').addEventListener('click', function(e) {
e.preventDefault();
openModal();
});

// Handle form submission
document.querySelector("#addProgramModal form").addEventListener("submit", async function (event) {
event.preventDefault();

// Validate form data
const programName = document.getElementById("programName").value.trim();
const programDescription = document.getElementById("programDescription").value.trim();

// Validation checks
if (!programName) {
  alert("Program Name is required!");
  return;
}

if (!programDescription) {
  alert("Program Description is required!");
  return;
}

// Prepare form data
const formData = {
  programName: programName,
  programDescription: programDescription
};

try {
  // Send POST request
  const response = await fetch("/api/programs", {
      method: "POST",
      headers: {
          "Content-Type": "application/json"
      },
      body: JSON.stringify(formData)
  });

  if (!response.ok) {
      const errorData = await response.json().catch(() => null);
      throw new Error(errorData?.message || "Failed to add program");
  }

  const data = await response.json();
  
  // Success handling
  alert("Program added successfully!");
  closeModal();
  window.location.reload();
} catch (error) {
  console.error("Error:", error);
  alert(`Failed to add program: ${error.message}`);
}
});

// Close modal when clicking outside
window.addEventListener('click', function(e) {
if (e.target === addProgramModal) {
  closeModal();
}
if (e.target === deleteConfirmModal) {
  closeDeleteConfirmModal();
}
});

// Close modal using the button
closeBtn.addEventListener('click', closeModal);
deleteCancelBtn.addEventListener('click', closeDeleteConfirmModal);

// Handle program deletion confirmation
deleteConfirmBtn.addEventListener('click', async function () {
if (!programIdToDelete) return;

try {
  const response = await fetch(`/api/programs/${programIdToDelete}`, {
      method: "DELETE"
  });

  if (!response.ok) {
      const errorData = await response.json().catch(() => null);
      throw new Error(errorData?.message || "Failed to delete program");
  }

  alert("Program deleted successfully!");
  closeDeleteConfirmModal();
  window.location.reload();
} catch (error) {
  console.error("Error:", error);
  alert(`Failed to delete program: ${error.message}`);
}
});

// Add click event listener to delete links
document.querySelectorAll('.delete-link').forEach(link => {
link.addEventListener('click', function (e) {
  e.preventDefault();
  const programId = this.getAttribute('data-program-id');
  openDeleteConfirmModal(programId);
});
});


// Get modal element
const updateModal = document.getElementById('updateProgramModal');

// Add click event to all update links
document.querySelectorAll('.update-link').forEach(link => {
link.addEventListener('click', async function(e) {
  e.preventDefault();
  const programId = this.getAttribute('data-program-id');
  await fetchAndPopulateProgram(programId);
});
});

// Function to fetch program data and populate the form
async function fetchAndPopulateProgram(programId) {
try {
  // Show loading state
  updateModal.style.display = 'block';
  const form = document.getElementById('updateProgramForm');
  form.innerHTML += '<div id="loadingMessage">Loading program data...</div>';

  // Fetch program data from API
  const response = await fetch(`/api/programs/${programId}`);
  
  // Remove loading message
  const loadingMessage = document.getElementById('loadingMessage');
  if (loadingMessage) loadingMessage.remove();

  if (!response.ok) {
      throw new Error('Failed to fetch program data');
  }

  const programData = await response.json();

  // Populate form fields
  document.getElementById('updateProgramId').value = programData.programId;
  document.getElementById('updateProgramName').value = programData.programName;
  document.getElementById('updateProgramDescription').value = programData.programDescription;

  // Handle multi-select for courses
  const courseSelect = document.getElementById('updateCourseNames');
  if (courseSelect && programData.courseNames) {
      Array.from(courseSelect.options).forEach(option => {
          option.selected = programData.courseNames.includes(option.value);
      });
  }

  // Handle multi-select for students
  const studentSelect = document.getElementById('updateStudentNames');
  if (studentSelect && programData.studentNames) {
      Array.from(studentSelect.options).forEach(option => {
          option.selected = programData.studentNames.includes(option.value);
      });
  }

} catch (error) {
  console.error('Error:', error);
  alert('Failed to load program data');
  closeUpdateModal();
}
}

// Handle form submission
document.getElementById('updateProgramForm').addEventListener('submit', async function(e) {
e.preventDefault();
const programId = document.getElementById('updateProgramId').value;

const formData = {
  programId: programId,
  programName: document.getElementById('updateProgramName').value,
  programDescription: document.getElementById('updateProgramDescription').value,
  courseNames: Array.from(document.getElementById('updateCourseNames').selectedOptions).map(opt => opt.value),
  studentNames: Array.from(document.getElementById('updateStudentNames').selectedOptions).map(opt => opt.value)
};

try {
  const response = await fetch(`/api/programs/${programId}`, {
      method: 'PUT',
      headers: {
          'Content-Type': 'application/json'
      },
      body: JSON.stringify(formData)
  });

  if (!response.ok) {
      throw new Error('Failed to update program');
  }

  alert('Program updated successfully');
  closeUpdateModal();
  window.location.reload(); // Refresh the page to see changes
} catch (error) {
  console.error('Error:', error);
  alert('Failed to update program');
}
});

// Function to close modal
function closeUpdateModal() {
updateModal.style.display = 'none';
}

// Close modal when clicking outside
window.addEventListener('click', function(e) {
if (e.target === updateModal) {
  closeUpdateModal();
}
});






// Function to open view modal with program details
async function openViewModal(programId) {
const viewModal = document.getElementById('viewProgramModal');
const viewProgramName = document.getElementById('viewProgramName');
const viewProgramDescription = document.getElementById('viewProgramDescription');
const viewProgramCourses = document.getElementById('viewProgramCourses');
const viewProgramStudents = document.getElementById('viewProgramStudents');

try {
  const response = await fetch(`/api/programs/${programId}`);
  if (!response.ok) {
      throw new Error('Failed to fetch program details');
  }

  const program = await response.json();
  viewProgramName.innerText = program.programName;
  viewProgramDescription.innerText = program.programDescription;
  viewProgramCourses.innerText = program.courseNames.join(', ');
  viewProgramStudents.innerText = program.studentNames.join(', ');

  viewModal.style.display = 'block';
} catch (error) {
  alert(`Error fetching program details: ${error.message}`);
}
}

// Close the view modal
function closeViewModal() {
document.getElementById('viewProgramModal').style.display = 'none';
}

// Add click event for view links
document.querySelectorAll('.view-link').forEach(link => {
link.addEventListener('click', function (e) {
  e.preventDefault();
  const programId = this.getAttribute('data-program-id');
  openViewModal(programId);
});
});

// Add click event to close button
document.getElementById('viewCloseBtn').addEventListener('click', closeViewModal);

// Close modal when clicking outside the modal content
window.addEventListener('click', function (e) {
const viewModal = document.getElementById('viewProgramModal');
if (e.target === viewModal) {
  closeViewModal();
}
});





